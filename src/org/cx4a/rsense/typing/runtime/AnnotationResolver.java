package org.cx4a.rsense.typing.runtime;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.Context;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.ruby.RubyClass;
import org.cx4a.rsense.ruby.RubyModule;
import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.typing.Graph;
import org.cx4a.rsense.typing.Template;
import org.cx4a.rsense.typing.TemplateAttribute;
import org.cx4a.rsense.typing.TypeSet;
import org.cx4a.rsense.typing.runtime.Tuple;
import org.cx4a.rsense.typing.annotation.TypeExpression;
import org.cx4a.rsense.typing.annotation.TypeVariable;
import org.cx4a.rsense.typing.annotation.ClassType;
import org.cx4a.rsense.typing.annotation.MethodType;
import org.cx4a.rsense.typing.annotation.TypeIdentity;
import org.cx4a.rsense.typing.annotation.TypeUnion;
import org.cx4a.rsense.typing.annotation.TypeAny;
import org.cx4a.rsense.typing.annotation.TypeOptional;
import org.cx4a.rsense.typing.annotation.TypeTuple;
import org.cx4a.rsense.typing.annotation.TypeSplat;
import org.cx4a.rsense.typing.annotation.TypeApplication;
import org.cx4a.rsense.typing.annotation.TypeConstraint;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.util.Logger;

public class AnnotationResolver {
    public enum Result {
        UNRESOLVED, NORETURN, NOBODY,
    };
    
    private Graph graph;
    private Ruby runtime;
    private TypeVarMap env;

    public AnnotationResolver(Graph graph) {
        this.graph = graph;
        this.runtime = graph.getRuntime();
        env = new TypeVarMap();
    }

    public Result resolveMethodAnnotation(Template template) {
        Method method = template.getMethod();
        if (method.getAnnotations() != null) {
            ClassType classType = RuntimeHelper.getEnclosingClassAnnotation(method.getModule());
            TemplateAttribute attr = template.getAttribute();
            IRubyObject receiver = attr.getReceiver();
            Vertex returnVertex = template.getReturnVertex();
            IRubyObject[] args = attr.getArgs();

            for (MethodType type : method.getAnnotations()) {
                List<TypeVariable> types = type.getTypes();
                List<TypeConstraint> constraints = type.getConstraints();
                MethodType.Signature sig = type.getSignature();
                if (sig != null) {
                    TypeExpression argType = sig.getArgType();
                    MethodType.Block block = sig.getBlock();
                    TypeExpression returnType = sig.getReturnType();

                    env.clear();
                    if (types != null) {
                        for (TypeVariable var : types) {
                            env.put(var, graph.createFreeVertexHolder());
                        }
                    }
                    if (resolveClassConstraints(template, classType, receiver)
                        && resolveReceiverClassConstraints(template, classType, receiver)
                        && resolveMethodArg(template, classType, argType, receiver, args)
                        && resolveMethodBlock(template, classType, block, receiver)
                        && resolveMethodConstraints(template, classType, constraints, receiver)
                        && resolveMethodReturn(template, classType, returnType, receiver)) {
                        return (classType != null && classType.isNoBody()) ? Result.NOBODY : Result.NORETURN;
                    }
                }
            }

            Logger.warn("annotation unmatched: %s", template.getMethod().getName());
        }
        return Result.UNRESOLVED;
    }

    public boolean resolveClassConstraints(Template template, ClassType classType, IRubyObject receiver) {
        if (classType == null) { return true; }

        List<TypeConstraint> constraints = classType.getConstraints();
        if (constraints != null) {
            return resolveMethodConstraints(template, classType, constraints, receiver);
        }
        return true;
    }

    public boolean resolveReceiverClassConstraints(Template template, ClassType classType, IRubyObject receiver) {
        return receiver == null
            || resolveClassConstraints(template, RuntimeHelper.getClassAnnotation(receiver.getMetaClass()), receiver);
    }
    
    public boolean resolveMethodArg(Template template, ClassType classType, TypeExpression argType, IRubyObject receiver, IRubyObject[] args) {
        if (argType == null) {
            // arity check
            return args.length == 0;
        }

        switch (argType.getType()) {
        case TUPLE: {
            TypeTuple tuple = (TypeTuple) argType;
            List<TypeExpression> list = tuple.getList();
            if (!checkArity(list, args)) {
                //Logger.warn("Wrong number of argument");
                return false;
            }

            for (int i = 0; i < list.size(); i++) {
                argType = list.get(i);
                IRubyObject arg;
                switch (argType.getType()) {
                case SPLAT: {
                    int len = Math.max(args.length - i, 0);
                    IRubyObject[] elements = new IRubyObject[len];
                    if (len > 0) {
                        System.arraycopy(args, i, elements, 0, len);
                    }
                    arg = new Tuple(runtime, elements);
                    break;
                }
                default:
                    arg = i < args.length ? args[i] : runtime.getNil();
                }

                if (!resolveMethodArg(template, classType, argType, receiver, arg)) {
                    return false;
                }
            }

            return true;
        }
        default:
            if (!checkArity(Arrays.asList(argType), args)) {
                //Logger.warn("Wrong number of argument");
                return false;
            }
            IRubyObject arg = args.length > 0 ? args[0] : runtime.getNil();
            return resolveMethodArg(template, classType, argType, receiver, arg);
        }
    }

    public boolean resolveMethodArg(Template template, ClassType classType, TypeExpression argType, IRubyObject receiver, IRubyObject arg) {
        if (argType == null) { return true; }

        switch (argType.getType()) {
        case VARIABLE: {
            TypeVariable var = (TypeVariable) argType;
            TypeVarMap typeVarMap = RuntimeHelper.getTypeVarMap(receiver);
            if (classType != null
                && typeVarMap != null
                && classType.containsType(var)) {
                VertexHolder holder = (VertexHolder) typeVarMap.get(var);
                if (holder == null) {
                    holder = graph.createFreeVertexHolder();
                    typeVarMap.put(var, holder);
                    template.setAffectedVar(var, holder);
                }
                holder.getVertex().addType(arg);
                typeVarMap.setModified(true);
            } else {
                VertexHolder holder = (VertexHolder) env.get(var);
                if (holder == null) {
                    holder = graph.createFreeVertexHolder();
                    env.put(var, holder);
                }
                holder.getVertex().addType(arg);
            }
            return true;
        }
        case APPLICATION: {
            TypeApplication app = (TypeApplication) argType;
            List<TypeExpression> types = app.getTypes();
            IRubyObject ret = resolveIdentity(template, app.getIdentity());
            if (arg.getMetaClass() != ret) {
                return false;
            } else if (ret != null && ret instanceof RubyClass) {
                RubyClass klass = (RubyClass) ret;
                ClassType klassType = RuntimeHelper.getClassAnnotation(klass);
                TypeVarMap typeVarMap = RuntimeHelper.getTypeVarMap(arg);
                if (classType != null && typeVarMap != null) {
                    List<TypeVariable> vars = classType.getTypes();
                    for (int i = 0; i < vars.size(); i++) {
                        TypeVariable var = vars.get(i);
                        VertexHolder holder = (VertexHolder) typeVarMap.get(var);
                        if (i < types.size() && holder != null) {
                            TypeExpression expr = types.get(i);
                            for (IRubyObject a : holder.getVertex().getTypeSet()) {
                                if (!resolveMethodArg(template, klassType, expr, receiver, a)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        case SCOPED_IDENTITY:
        case ABSOLUTE_IDENTITY:
        case RELATIVE_IDENTITY: {
            IRubyObject guard = resolveIdentity(template, (TypeIdentity) argType);
            return (guard instanceof RubyClass) && arg.isKindOf((RubyClass) guard);
        }
        case UNION:
            for (TypeExpression expr : (TypeUnion) argType) {
                if (resolveMethodArg(template, classType, expr, receiver, arg)) {
                    return true;
                }
            }
            return false;
        case SPLAT: {
            TypeSplat splat = (TypeSplat) argType;
            TypeExpression expr = splat.getExpression();
            if (expr == null) {
                return true;
            } else if (expr.getType() == TypeExpression.Type.VARIABLE) {
                return resolveMethodArg(template, classType, expr, receiver, arg);
            } else if (arg instanceof Tuple) {
                Tuple tuple = (Tuple) arg;
                for (IRubyObject a : tuple.getElements()) {
                    if (!resolveMethodArg(template, classType, expr, receiver, a)) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        case OPTIONAL: {
            return arg.isNil() ? true : resolveMethodArg(template, classType, ((TypeOptional) argType).getExpression(), receiver, arg);
        }
        case ANY:
            return true;
        default:
            new Throwable().printStackTrace();
            Logger.error("Unknown arg type");
        }
        
        return false;
    }

    public boolean resolveMethodConstraints(Template template, ClassType classType, List<TypeConstraint> constraints, IRubyObject receiver) {
        if (constraints == null) { return true; }

        for (TypeConstraint cons : constraints) {
            if (cons.getType() == TypeExpression.Type.SUBTYPE_CONS) {
                TypeExpression lhs = cons.lhs();
                TypeExpression rhs = cons.rhs();
                TypeSet ts = processMethodReturn(template, classType, lhs, receiver);
                if (ts == null) {
                    return false;
                } else if (ts.isEmpty() && lhs.getType() == TypeExpression.Type.VARIABLE) {
                    // null can be any type
                    ts = processMethodReturn(template, classType, rhs, receiver);
                    for (IRubyObject arg : ts) {
                        if (!resolveMethodArg(template, classType, lhs, receiver, arg)) {
                            return false;
                        }
                    }
                    return true;
                }
                for (IRubyObject arg : ts) {
                    if (!resolveMethodArg(template, classType, rhs, receiver, arg)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public boolean resolveMethodBlock(Template template, ClassType classType, MethodType.Block blockType, IRubyObject receiver) {
        Block block = template.getAttribute().getBlock();

        if (block == null && blockType == null) {
            return true;
        } else if ((block != null) ^ (blockType != null)) {
            return false;
        }

        MethodType.Signature blockSig = blockType.getSignature();
        TypeExpression blockArgType = blockSig.getArgType();
        TypeExpression returnArgType = blockSig.getReturnType();
        TypeSet[] args = processMethodBlockArg(template, classType, blockArgType, receiver);
        Tuple[] tuples = Tuple.generateTuples(runtime, args);
        
        // FIXME checkArity

        boolean succeed = false;
        for (Tuple tuple : tuples) {
            Vertex returnVertex = RuntimeHelper.yield(graph, block, tuple.getLength() == 1 ? tuple.get(0) : tuple, true);
            if (returnVertex != null) {
                TypeSet ts = returnVertex.getTypeSet();
                for (IRubyObject arg : ts) {
                    if (resolveMethodArg(template, classType, returnArgType, receiver, arg)) {
                        succeed = true;
                    }
                }
            }
        }
        return succeed;
    }

    public TypeSet[] processMethodBlockArg(Template template, ClassType classType, TypeExpression argType, IRubyObject receiver) {
        if (argType == null) { Collections.<IRubyObject>emptyList(); }

        List<TypeSet> args = new ArrayList<TypeSet>();
        switch (argType.getType()) {
        case TUPLE: {
            TypeTuple tuple = (TypeTuple) argType;
            for (TypeExpression expr : tuple.getList()) {
                args.add(processMethodReturn(template, classType, expr, receiver));
            }
            break;
        }
        default:
            args.add(processMethodReturn(template, classType, argType, receiver));
        }

        return args.toArray(new TypeSet[0]);
    }
    
    public TypeSet processMethodReturn(Template template, ClassType classType, TypeExpression returnType, IRubyObject receiver) {
        if (returnType == null) { return TypeSet.EMPTY; }

        TypeSet result = new TypeSet();
        switch (returnType.getType()) {
        case VARIABLE: {
            TypeVariable var = (TypeVariable) returnType;
            if (var.getName().equals("self")) {
                result.add(receiver);
            } else if (var.getName().equals("nil")) {
                result.add(runtime.getNil());
            } else {
                VertexHolder holder = (VertexHolder) env.get(var);
                TypeVarMap typeVarMap = RuntimeHelper.getTypeVarMap(receiver);
                if (holder != null) {
                    result.addAll(holder.getVertex().getTypeSet());
                } else if (classType != null
                    && typeVarMap != null
                    && classType.containsType(var)) {
                    holder = (VertexHolder) typeVarMap.get(var);
                    if (holder != null) {
                        result.addAll(holder.getVertex().getTypeSet());
                    } else {
                        result.add(runtime.getNil());
                    }
                    typeVarMap.setModified(true);
                }
            }
            return result;
        }
        case APPLICATION: {
            TypeApplication app = (TypeApplication) returnType;
            List<TypeExpression> types = app.getTypes();
            IRubyObject ret = resolveIdentity(template, app.getIdentity());
            if (ret != null && ret instanceof RubyClass) {
                RubyClass klass = (RubyClass) ret;
                ret = graph.newInstanceOf(klass);
                ClassType klassType = RuntimeHelper.getClassAnnotation(klass);
                TypeVarMap typeVarMap = RuntimeHelper.getTypeVarMap(ret);
                if (klassType != null && typeVarMap != null) {
                    List<TypeVariable> vars = klassType.getTypes();
                    for (int i = 0; i < vars.size(); i++) {
                        TypeVariable var = vars.get(i);
                        if (i < types.size()) {
                            TypeExpression expr = types.get(i);
                            TypeSet ts = processMethodReturn(template, classType, expr, receiver);
                            VertexHolder holder = graph.createFreeVertexHolder();
                            holder.getVertex().getTypeSet().addAll(ts);
                            typeVarMap.put(var, holder);
                            typeVarMap.setModified(true);
                        }
                    }
                }
                result.add(ret);
            }
            return result;
        }
        case SCOPED_IDENTITY:
        case ABSOLUTE_IDENTITY:
        case RELATIVE_IDENTITY: {
            IRubyObject ret = resolveIdentity(template, (TypeIdentity) returnType);
            if (ret != null && ret instanceof RubyClass) {
                ret = graph.newInstanceOf((RubyClass) ret);
                result.add(ret);
            }
            return result;
        }
        case UNION:
            for (TypeExpression expr : (TypeUnion) returnType) {
                TypeSet ts = processMethodReturn(template, classType, expr, receiver);
                if (ts != null) {
                    result.addAll(ts);
                }
            }
            return result;
        case TUPLE: {
            TypeTuple tupleType = (TypeTuple) returnType;
            List<TypeExpression> exprs = tupleType.getList();
            if (exprs != null) {
                TypeSet[] args = new TypeSet[exprs.size()];
                for (int i = 0; i < exprs.size(); i++) {
                    TypeSet ts = processMethodReturn(template, classType, exprs.get(i), receiver);
                    if (ts == null) {
                        return result;
                    }
                    args[i] = ts;
                }
                result.addAll(Arrays.asList(Tuple.generateTuples(runtime, args)));
            }
            return result;
        }
        case ANY:
        case OPTIONAL:
        case SPLAT:
            return result;
        default:
            Logger.error("Unknown return type");
        }
        return null;
        
    }
    
    public boolean resolveMethodReturn(Template template, ClassType classType, TypeExpression returnType, IRubyObject receiver) {
        if (returnType == null) { return true; }

        TypeSet ts = processMethodReturn(template, classType, returnType, receiver);
        if (ts == null) {
            return false;
        }

        template.getReturnVertex().getTypeSet().addAll(ts);
        return true;
    }

    public IRubyObject resolveIdentity(Template template, TypeIdentity ident) {
        return resolveIdentity(template, ident, template.getFrame().getModule());
    }

    public IRubyObject resolveIdentity(Template template, TypeIdentity ident, RubyModule module) {
        if (ident.getType() == TypeExpression.Type.ABSOLUTE_IDENTITY) {
            return resolveIdentity(template, ident.getPath(), runtime.getObject());
        }

        IRubyObject value = module.getConstant(ident.getName());
        if (value instanceof VertexHolder) {
            module = null;
            for (IRubyObject t : ((VertexHolder) value).getVertex().getTypeSet()) {
                if (t instanceof RubyModule) {
                    module = (RubyModule) t;
                    break;
                }
            }
        } else if (value instanceof RubyModule) {
            module = (RubyModule) value;
        } else {
            return value;
        }

        if (module != null && ident.getType() == TypeExpression.Type.SCOPED_IDENTITY) {
            return resolveIdentity(template, ident.getPath(), module);
        } else {
            return module;
        }
    }

    public boolean checkArity(List<TypeExpression> list, IRubyObject[] args) {
        int preCount = 0;
        int postCount = 0;

        for (int i = 0; i < list.size(); i++) {
            TypeExpression argType = list.get(i);
            switch (argType.getType()) {
            case SPLAT:
                return true;
            case OPTIONAL:
                postCount++;
                break;
            default:
                preCount++;
                if (args.length <= i) {
                    return false;
                }
            }
        }

        return preCount + postCount >= args.length;
    }
}
