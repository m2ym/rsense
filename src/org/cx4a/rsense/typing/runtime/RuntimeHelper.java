package org.cx4a.rsense.typing.runtime;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collection;
import java.util.Iterator;
import java.util.Collections;
import java.util.Arrays;

import org.jruby.ast.Node;
import org.jruby.ast.ArgsNode;
import org.jruby.ast.ArgsNoArgNode;
import org.jruby.ast.ArgsPreOneArgNode;
import org.jruby.ast.ArgumentNode;
import org.jruby.ast.ListNode;
import org.jruby.ast.LocalVarNode;
import org.jruby.ast.LocalAsgnNode;
import org.jruby.ast.DAsgnNode;
import org.jruby.ast.ClassVarDeclNode;
import org.jruby.ast.ClassVarAsgnNode;
import org.jruby.ast.ClassVarNode;
import org.jruby.ast.InstAsgnNode;
import org.jruby.ast.InstVarNode;
import org.jruby.ast.ConstDeclNode;
import org.jruby.ast.GlobalAsgnNode;
import org.jruby.ast.GlobalVarNode;
import org.jruby.ast.MultipleAsgnNode;
import org.jruby.ast.Colon2Node;
import org.jruby.ast.Colon2ImplicitNode;
import org.jruby.ast.Colon3Node;
import org.jruby.ast.AssignableNode;
import org.jruby.ast.IterNode;
import org.jruby.ast.YieldNode;
import org.jruby.ast.ZeroArgNode;
import org.jruby.ast.MethodDefNode;
import org.jruby.ast.DefnNode;
import org.jruby.ast.BlockArgNode;
import org.jruby.ast.NodeType;
import org.jruby.ast.types.INameNode;
import org.jruby.parser.LocalStaticScope;
import org.jruby.lexer.yacc.ISourcePosition;
import org.jruby.lexer.yacc.SimpleSourcePosition;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.Context;
import org.cx4a.rsense.ruby.Scope;
import org.cx4a.rsense.ruby.DynamicScope;
import org.cx4a.rsense.ruby.LocalScope;
import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.ruby.Frame;
import org.cx4a.rsense.ruby.Visibility;
import org.cx4a.rsense.ruby.RubyModule;
import org.cx4a.rsense.ruby.RubyClass;
import org.cx4a.rsense.ruby.MetaClass;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.ruby.DynamicMethod;
import org.cx4a.rsense.util.Logger;
import org.cx4a.rsense.util.NodeDiff;
import org.cx4a.rsense.util.SourceLocation;
import org.cx4a.rsense.typing.TypeSet;
import org.cx4a.rsense.typing.Template;
import org.cx4a.rsense.typing.TemplateAttribute;
import org.cx4a.rsense.typing.Graph;
import org.cx4a.rsense.typing.runtime.TypeVarMap;
import org.cx4a.rsense.typing.runtime.AnnotationHelper;
import org.cx4a.rsense.typing.runtime.ClassTag;
import org.cx4a.rsense.typing.runtime.Method;
import org.cx4a.rsense.typing.annotation.TypeAnnotation;
import org.cx4a.rsense.typing.annotation.TypeExpression;
import org.cx4a.rsense.typing.annotation.TypeVariable;
import org.cx4a.rsense.typing.annotation.ClassType;
import org.cx4a.rsense.typing.annotation.MethodType;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.vertex.CallVertex;
import org.cx4a.rsense.typing.vertex.MultipleAsgnVertex;
import org.cx4a.rsense.typing.vertex.ToAryVertex;
import org.cx4a.rsense.typing.vertex.SplatVertex;
import org.cx4a.rsense.typing.vertex.SValueVertex;
import org.cx4a.rsense.typing.vertex.YieldVertex;
import org.cx4a.rsense.typing.vertex.PassThroughVertex;
import org.cx4a.rsense.typing.vertex.TypeVarVertex;

public class RuntimeHelper {
    private RuntimeHelper() {}

    public static Vertex assign(Graph graph, Node node) {
        return assign(graph, node);
    }

    public static Vertex assign(Graph graph, Node node, Vertex src) {
        switch (node.getNodeType()) {
        case LOCALASGNNODE:
            return localAssign(graph, (LocalAsgnNode) node, src);
        case DASGNNODE:
            return dynamicAssign(graph, (DAsgnNode) node, src);
        case INSTASGNNODE:
            return instanceAssign(graph, (InstAsgnNode) node, src);
        case CONSTDECLNODE:
            return constDeclaration(graph, (ConstDeclNode) node, src);
        case CLASSVARASGNNODE:
            return classVarAssign(graph, (ClassVarAsgnNode) node, src);
        case CLASSVARDECLNODE:
            return classVarDeclaration(graph, (ClassVarDeclNode) node, src);
        case ARGUMENTNODE:
        case RESTARG:
            return argumentAssign(graph, (ArgumentNode) node, src);
        }
        Logger.error("unknown assignable node: %s", node);
        return Vertex.EMPTY;
    }
    
    public static Vertex localAssign(Graph graph, LocalAsgnNode node) {
        return localAssign(graph, node, null);
    }

    public static Vertex localAssign(Graph graph, LocalAsgnNode node, Vertex src) {
        Scope scope = graph.getRuntime().getContext().getCurrentScope();
        VertexHolder holder = (VertexHolder) scope.getValue(node.getName());
        if (src == null) {
            src = graph.createVertex(node.getValueNode());
        }
        if (holder == null) {
            holder = graph.createFreeVertexHolder();
            scope.setValue(node.getName(), holder);
        }
        graph.addEdgeAndPropagate(src, holder.getVertex());
        return src;
    }

    public static Vertex dynamicAssign(Graph graph, DAsgnNode node) {
        return dynamicAssign(graph, node, null);
    }

    public static Vertex dynamicAssign(Graph graph, DAsgnNode node, Vertex src) {
        Scope scope = graph.getRuntime().getContext().getCurrentScope();
        VertexHolder holder = (VertexHolder) scope.getValue(node.getName());
        if (src == null) {
            src = graph.createVertex(node.getValueNode());
        }
        if (holder == null) {
            holder = graph.createFreeVertexHolder();
            scope.setValue(node.getName(), holder);
        }
        graph.addEdgeAndPropagate(src, holder.getVertex());
        return src;
    }


    public static Vertex instanceAssign(Graph graph, InstAsgnNode node) {
        return instanceAssign(graph, node, null);
    }

    public static Vertex instanceAssign(Graph graph, InstAsgnNode node, Vertex src) {
        IRubyObject self = graph.getRuntime().getContext().getFrameSelf();
        VertexHolder holder = (VertexHolder) self.getInstVar(node.getName());
        if (src == null) {
            src = graph.createVertex(node.getValueNode());
        }
        if (holder == null) {
            holder = graph.createFreeVertexHolder();
            self.setInstVar(node.getName(), holder);
        }
        graph.addEdgeAndPropagate(src, holder.getVertex());
        return src;
    }


    public static Vertex instanceVariable(Graph graph, InstVarNode node) {
        IRubyObject self = graph.getRuntime().getContext().getFrameSelf();
        VertexHolder holder = (VertexHolder) self.getInstVar(node.getName());
        if (holder == null) {
            holder = graph.createFreeVertexHolder();
            self.setInstVar(node.getName(), holder);
        }
        return holder.getVertex();
    }

    public static Vertex classVarDeclaration(Graph graph, ClassVarDeclNode node) {
        return classVarDeclaration(graph, node, null);
    }
    
    public static Vertex classVarDeclaration(Graph graph, ClassVarDeclNode node, Vertex src) {
        RubyModule klass = graph.getRuntime().getContext().getFrameModule();
        if (src == null) {
            src = graph.createVertex(node.getValueNode());
        }
        VertexHolder holder = (VertexHolder) klass.getClassVar(node.getName());
        if (holder == null) {
            holder = graph.createFreeVertexHolder();
            klass.setClassVar(node.getName(), holder);
        }
        // Clear older types (performance issue)
        holder.getVertex().getTypeSet().clear();
        graph.addEdgeAndPropagate(src, holder.getVertex());
        return src;
    }

    public static Vertex classVarAssign(Graph graph, ClassVarAsgnNode node) {
        return classVarAssign(graph, node, null);
    }

    public static Vertex classVarAssign(Graph graph, ClassVarAsgnNode node, Vertex src) {
        RubyModule klass = graph.getRuntime().getContext().getFrameModule();
        if (src == null) {
            src = graph.createVertex(node.getValueNode());
        }
        VertexHolder holder = (VertexHolder) klass.getClassVar(node.getName());
        if (holder == null) {
            holder = graph.createFreeVertexHolder();
            klass.setClassVar(node.getName(), holder);
        }
        // Clear older types (performance issue)
        holder.getVertex().getTypeSet().clear();
        graph.addEdgeAndPropagate(src, holder.getVertex());
        return src;
    }

    public static Vertex classVariable(Graph graph, ClassVarNode node) {
        RubyModule klass = graph.getRuntime().getContext().getFrameModule();
        VertexHolder holder = (VertexHolder) klass.getClassVar(node.getName());
        if (holder == null) {
            holder = graph.createFreeVertexHolder();
            klass.setClassVar(node.getName(), holder);
        }
        return holder.getVertex();
    }

    public static Vertex constDeclaration(Graph graph, ConstDeclNode node) {
        return constDeclaration(graph, node, null);
    }
    
    public static Vertex constDeclaration(Graph graph, ConstDeclNode node, Vertex src) {
        RubyModule module = null;
        String name = null;
        INameNode constNode = (INameNode) node.getConstNode();
        if (constNode == null) {
            name = node.getName();
            module = graph.getRuntime().getContext().getFrameModule();
        } else if (constNode instanceof Colon2Node) {
            Node leftNode = ((Colon2Node) constNode).getLeftNode();

            Vertex v = graph.createVertex(leftNode);
            for (IRubyObject mod : v.getTypeSet()) {
                if (mod instanceof RubyModule) {
                    module = (RubyModule) mod;
                    break;
                }
            }
            name = constNode.getName();
        } else { // colon3
            module = graph.getRuntime().getObject();
            name = constNode.getName();
        }

        if (src == null) {
            src = graph.createVertex(node.getValueNode());
        }
        if (module != null && name != null) {
            module.setConstant(name, graph.createVertexHolder(src));
        }

        return src;
    }

    public static Vertex globalAssign(Graph graph, GlobalAsgnNode node) {
        return globalAssign(graph, node, null);
    }

    public static Vertex globalAssign(Graph graph, GlobalAsgnNode node, Vertex src) {
        Ruby runtime = graph.getRuntime();
        VertexHolder holder = (VertexHolder) runtime.getGlobalVar(node.getName());
        if (src == null) {
            src = graph.createVertex(node.getValueNode());
        }
        if (holder == null) {
            holder = graph.createFreeVertexHolder();
            runtime.setGlobalVar(node.getName(), holder);
        }
        // Clear older types (performance issue)
        holder.getVertex().getTypeSet().clear();
        graph.addEdgeAndPropagate(src, holder.getVertex());
        return src;
    }

    public static Vertex globalVariable(Graph graph, GlobalVarNode node) {
        Ruby runtime = graph.getRuntime();
        VertexHolder holder = (VertexHolder) runtime.getGlobalVar(node.getName());
        if (holder == null) {
            holder = graph.createFreeVertexHolder();
            runtime.setGlobalVar(node.getName(), holder);
        }
        return holder.getVertex();
    }

    public static void aliasGlobalVaraibles(Graph graph, String newName, String oldName) {
        Ruby runtime = graph.getRuntime();
        VertexHolder holder = (VertexHolder) runtime.getGlobalVar(oldName);
        if (holder == null) {
            holder = graph.createFreeVertexHolder();
            runtime.setGlobalVar(oldName, holder);
        }
        runtime.setGlobalVar(newName, holder); // no propagation ?
    }

    public static void blockAssign(Graph graph, BlockArgNode node, Block block) {
        if (block == null)
            return;
        
        Scope scope = graph.getRuntime().getContext().getCurrentScope();
        VertexHolder holder = (VertexHolder) scope.getValue(node.getName());
        if (holder == null) {
            holder = graph.createFreeVertexHolder();
            scope.setValue(node.getName(), holder);
        }
        holder.getVertex().addType((Proc) block);
    }

    public static void argsAssign(Graph graph, ArgsNode argsNode, Vertex[] args, Block block) {
        Scope scope = graph.getRuntime().getContext().getCurrentScope();
        ListNode pre = argsNode.getPre();
        ListNode post = argsNode.getPost();
        int preCount = argsNode.getPreCount();
        int postCount = argsNode.getPostCount();
        if (preCount > 0) {
            int size = pre.size();
            for (int i = 0; i < size; i++) {
                Node next = pre.get(i);
                Vertex arg = i < args.length ? args[i] : Vertex.EMPTY;
                assign(graph, next, arg);
            }
        }
        if (postCount > 0) {
            int size = post.size();
            int argsLength = args.length;
            for (int i = 0; i < size; i++) {
                Node next = post.get(i);
                Vertex arg = argsLength - postCount + i < args.length
                    ? args[argsLength - postCount + i]
                    : Vertex.EMPTY;
                if (next instanceof AssignableNode) {
                    assign(graph, next, arg);
                } else {
                    //scope.setValue(((INameNode) next).getName(), arg);
                    Logger.fixme("argsAssign2");
                }
            }
        }

        int index = preCount;
        ListNode optArgs = argsNode.getOptArgs();
        int restArg = argsNode.getRestArg();
        Node restArgNode = argsNode.getRestArgNode();
        int givenArgsCount = preCount;
        if (optArgs != null) {
            int j = 0;
            for (int i = preCount; i < args.length - postCount && j < optArgs.size(); i++, j++) {
                assign(graph, optArgs.get(j), args[i]);
                givenArgsCount++;
            }
            for (int i = 0; j < optArgs.size(); i++, j++) {
                graph.createVertex(optArgs.get(j));
            }
        }
        if (restArg >= 0) {
            int sizeOfRestArg = args.length - postCount - givenArgsCount;
            if (sizeOfRestArg <= 0) {
                assign(graph, restArgNode, createArrayVertex(graph, null, null));
            } else {
                assign(graph, restArgNode, createArrayVertex(graph, null, args, givenArgsCount, sizeOfRestArg));
            }
        }
        if (argsNode.getBlock() != null) {
            blockAssign(graph, argsNode.getBlock(), block);
        }
    }

    public static Vertex argumentAssign(Graph graph, ArgumentNode node, Vertex src) {
        Scope scope = graph.getRuntime().getContext().getCurrentScope();
        VertexHolder holder = graph.createFreeVertexHolder();
        scope.setValue(node.getName(), holder);
        if (src != null) {
            graph.addEdgeAndUpdate(src, holder.getVertex());
        }
        return src;
    }

    public static Vertex multipleAssign(Graph graph, MultipleAsgnNode node) {
        return multipleAssign(graph, node, (Vertex) null);
    }
    
    public static Vertex multipleAssign(Graph graph, MultipleAsgnNode node, Vertex src) {
        if (src == null) {
            src = graph.createVertex(node.getValueNode());
        }
        MultipleAsgnVertex vertex = new MultipleAsgnVertex(node, src);
        graph.addEdgeAndPropagate(src, vertex);
        return src;
    }

    public static void multipleAssign(Graph graph, MultipleAsgnNode node, IRubyObject object) {
        boolean isArray = object instanceof Array;
        Array array = null;
        Vertex element = Vertex.EMPTY;

        if (isArray) {
            array = (Array) object;
            if (array.isModified()) {
                return;
            }
        } else {
            TypeVarMap tvmap = getTypeVarMap(object);
            TypeVariable var = TypeVariable.valueOf("t");
            if (tvmap != null && tvmap.containsKey(var)) {
                element = tvmap.get(var);
            }
        }
        
        int valLen = array != null ? array.length() : 256; // magic number
        int varLen = node.getHeadNode() == null ? 0 : node.getHeadNode().size();

        int j = 0;
        for (; j < valLen && j < varLen; j++) {
            assign(graph, node.getHeadNode().get(j), array != null ? array.getElement(j) : element);
        }

        Node argsNode = node.getArgsNode();
        if (argsNode != null) {
            if (argsNode.getNodeType() == NodeType.STARNODE) {
                // no check for '*'
            } else if (varLen < valLen && array != null) {
                assign(graph, argsNode, createArrayVertex(graph, null, array.getElements(), varLen, valLen - varLen));
            } else {
                Vertex[] elements = null;
                if (element != null) {
                    elements = new Vertex[] { element };
                }
                assign(graph, argsNode, createArrayVertex(graph, null, elements));
            }
        }

        while (j < varLen) {
            assign(graph, node.getHeadNode().get(j++), element);
        }
    }

    public static Vertex[] setupCallArgs(Graph graph, Node argsNode) {
        List<Vertex> args = new ArrayList<Vertex>();
        if (argsNode != null) {
            switch (argsNode.getNodeType()) {
            case ARGSCATNODE:
            case SPLATNODE: {
                Vertex arrayVertex = graph.createVertex(argsNode);
                for (IRubyObject object : arrayVertex.getTypeSet()) {
                    if (object instanceof Array) {
                        Array array = (Array) object;
                        if (array.getElements() != null) {
                            for (Vertex element : array.getElements()) {
                                args.add(element);
                            }
                        }
                    }
                }
                break;
            }
            default:
                for (Node arg : argsNode.childNodes()) {
                    args.add(graph.createVertex(arg));
                }
            }
        }
        return args.isEmpty() ? null : args.toArray(new Vertex[0]);
    }

    public static Block setupCallBlock(Graph graph, Node iterNode) {
        Context context = graph.getRuntime().getContext();
        Block block = null;
        if (iterNode != null) {
            switch (iterNode.getNodeType()) {
            case ITERNODE: {
                IterNode inode = (IterNode) iterNode;
                DynamicScope scope = new DynamicScope(context.getCurrentScope().getModule(), context.getCurrentScope());
                block = new Proc(graph.getRuntime(), inode.getVarNode(), inode.getBodyNode(), context.getCurrentFrame(), scope);
                break;
            }
            case BLOCKPASSNODE:
                block = context.getFrameBlock();
                break;
            }
        }
        return block;
    }
        
    public static Vertex call(Graph graph, CallVertex vertex) {
        return call(graph, vertex, false);
    }

    public static Vertex callSuper(Graph graph, CallVertex vertex) {
        return call(graph, vertex, true);
    }
    
    private static Vertex call(Graph graph, CallVertex vertex, boolean callSuper) {
        if (vertex.isApplicable() && vertex.isChanged()) {
            vertex.markUnchanged();

            String name = vertex.getName();
            TypeSet receivers = vertex.getReceiverVertex().getTypeSet();
            Block block = vertex.getBlock();
            boolean noReturn = false;
            Vertex[] argVertices = vertex.getArgVertices();
            SpecialMethod.Result prevResult = null;
            TypeSet accumulator = new TypeSet();

            while (!receivers.isEmpty()) {
                SpecialMethod.Result result = new SpecialMethod.Result(prevResult, accumulator);
                prevResult = result;
                SpecialMethod spec = graph.getSpecialMethod(name);
                if (spec != null) {
                    spec.call(graph.getRuntime(), receivers, argVertices, block, result);
                    if (result.getResultTypeSet() != null) {
                        accumulator.addAll(result.getResultTypeSet());
                    }
                    if (result.hasPrivateVisibility()) {
                        vertex.setPrivateVisibility(true);
                    }
                    if (result.isNeverCallAgain()) {
                        vertex.cutout();
                    } else if (!result.isCallNextMethod()) {
                        break;
                    }
                }

                List<Collection<IRubyObject>> args = new ArrayList<Collection<IRubyObject>>();
                if (vertex.getArgVertices() != null) {
                    for (Vertex v : argVertices) {
                        args.add(v.getTypeSet());
                    }
                }

                List<TemplateAttribute> attrs = generateTemplateAttributes(receivers, args, block);
                boolean applied = false;
                for (TemplateAttribute attr : attrs) {
                    Vertex returnVertex = applyTemplateAttribute(graph, vertex, name, attr, callSuper);
                    if (returnVertex != null) {
                        applied = true;
                        if (!noReturn)
                            accumulator.addAll(returnVertex.getTypeSet());
                    }
                }
                if (!applied)
                    graph.notifyMethodMissingEvent(vertex);
                if (result.isNextMethodChange()) {
                    name = result.getNextMethodName();
                    receivers = result.getNextMethodReceivers();
                    block = result.getNextMethodBlock();
                    noReturn = result.isNextMethodNoReturn();
                } else {
                    break;
                }
            }

            vertex.addTypes(accumulator);
        }
        return vertex;
    }

    public static void methodPartialUpdate(Graph graph, MethodDefNode node, DynamicMethod newMethod, DynamicMethod oldMethod, IRubyObject receiver) {
        if (newMethod instanceof Method && oldMethod instanceof Method) {
            Method newmeth = (Method) newMethod;
            Method oldmeth = (Method) oldMethod;
            NodeDiff nodeDiff = graph.getNodeDiff();

            if (nodeDiff != null
                && nodeDiff.noDiff(node.getArgsNode(), oldmeth.getArgsNode())
                && nodeDiff.noDiff(node.getBodyNode(), oldmeth.getBodyNode())) { // XXX nested class, defn
                // FIXME annotation diff
                newmeth.shareTemplates(oldmeth);
            } else {
                Logger.debug(SourceLocation.of(node), "templates not shared: %s", newmeth);
            }
        }
    }

    public static void classPartialUpdate(Graph graph, RubyModule klass, Node bodyNode) {
        NodeDiff nodeDiff = graph.getNodeDiff();

        if (bodyNode != null) {
            ClassTag oldTag = getClassTag(klass);
            if (nodeDiff != null && oldTag != null) {
                List<Node> partialDiff = nodeDiff.diff(bodyNode, oldTag.getBodyNode());
                if (partialDiff != null) {
                    Logger.debug(SourceLocation.of(bodyNode), "class partial update: %s %s", klass, partialDiff.size());
                    for (Node dirty : partialDiff) {
                        graph.createVertex(dirty);
                    }
                } else {
                    graph.createVertex(bodyNode);
                }
            } else {
                graph.createVertex(bodyNode);
            }
        }
    }

    public static void dummyCall(Graph graph, MethodDefNode node, Method method, IRubyObject receiver) {
        if (node.getBodyNode() != null) {
            Context context = graph.getRuntime().getContext();
            context.pushFrame(context.getFrameModule(), node.getName(), receiver, null, Visibility.PUBLIC);
            context.pushScope(new LocalScope(method.getModule()));
            graph.createVertex(node.getBodyNode());
            context.popScope();
            context.popFrame();
        }
        Logger.debug(SourceLocation.of(node), "dummy call: %s", method);
    }

    public static void dummyCallForTemplates(Graph graph, MethodDefNode node, Method method, Collection<TemplateAttribute> templateAttributes) {
        String name = node.getName();
        for (TemplateAttribute attr : templateAttributes) {
            createTemplate(graph, null, name, method, attr);
        }
        Logger.debug(SourceLocation.of(node), "template dummy call: %s", method);
    }

    private static Vertex applyTemplateAttribute(Graph graph, CallVertex vertex, String name, TemplateAttribute attr, boolean callSuper) {
        IRubyObject receiver = attr.getReceiver();
        IRubyObject[] args = attr.getArgs();
        RubyClass receiverType = null;
        if (callSuper) {
            RubyModule module = graph.getRuntime().getContext().getFrameModule();
            if (module instanceof RubyClass) {
                RubyClass klass = (RubyClass) module;
                receiverType = klass.getSuperClass();
            } else {
                Logger.error("Cannot call super in module");
                return null;
            }
        } else if (receiver != null) {
            receiverType = receiver.getMetaClass();
        }
        if (receiverType != null) {
            Method method = (Method) receiverType.searchMethod(name);
            if (method != null
                && (method.getVisibility() != Visibility.PRIVATE
                    || vertex.hasPrivateVisibility())) {
                Template template = method.getTemplate(attr);
                if (template == null) {
                    template = createTemplate(graph, vertex, name, method, attr);
                    Logger.debug(SourceLocation.of(vertex), "template created: %s", method);
                } else {
                    template.reproduceSideEffect(graph, receiver, args, vertex.getBlock());
                    Logger.debug(SourceLocation.of(vertex), "template reused: %s", method);
                }
                return template.getReturnVertex();
            }
        }
        return null;
    }

    private static Template createTemplate(Graph graph, CallVertex vertex, String name, Method method, TemplateAttribute attr) {
        IRubyObject receiver = attr.getReceiver();
        IRubyObject[] args = attr.getArgs();
        Vertex[] argVertices = new Vertex[args.length];
        for (int i = 0; i < argVertices.length; i++) {
            argVertices[i] = graph.createFreeSingleTypeVertex(args[i]);
        }

        Ruby runtime = graph.getRuntime();
        Context context = runtime.getContext();

        Block block = attr.getBlock();
        Scope scope = new LocalScope(method.getModule());
        context.pushFrame(context.getFrameModule(), name, receiver, block, Visibility.PUBLIC);
        context.pushScope(scope);

        Template template = new Template(method, context.getCurrentFrame(), scope, attr);
        method.addTemplate(attr, template);

        Vertex returnVertex = template.getReturnVertex();
        setFrameTemplate(context.getCurrentFrame(), template);

        AnnotationResolver.Result result = AnnotationHelper.resolveMethodAnnotation(graph, template);
        if (result == AnnotationResolver.Result.UNRESOLVED) {
            Logger.warn(SourceLocation.of(vertex), "annotation unmatched: %s", method);
        }

        Vertex ret = method.call(graph, template, receiver, args, argVertices, block);
        if (ret != null && result != AnnotationResolver.Result.RESOLVED) {
            graph.addEdgeAndUpdate(ret, returnVertex);
        }

        context.popScope();
        context.popFrame();

        return template;
    }

    private static List<TemplateAttribute> generateTemplateAttributes(Collection<IRubyObject> receivers, List<Collection<IRubyObject>> args, Block block) {
        int size = 1;
        for (Collection<IRubyObject> c : args) {
            size *= c.size();
        }
        if (size == 0) {
            return Collections.emptyList();
        } else if (size >= 128) {
            Logger.warn("too big application: %s", size);
            return Collections.emptyList();
        }

        int unit = size;
        List<TemplateAttribute> result = new ArrayList<TemplateAttribute>(size);
        for (int i = 0; i < size; i++) {
            result.add(new TemplateAttribute(new IRubyObject[args.size()]));
        }

        for (int i = 0; i < args.size(); i++) {
            Collection<IRubyObject> c = args.get(i);
            Iterator<IRubyObject> ite = c.iterator();
            int k = 0, n = c.size();
            int newUnit = unit / n;
            IRubyObject v = ite.next();
            for (int j = 0; j < size; j++) {
                result.get(j).setArg(i, v);
                if (++k == newUnit) {
                    k = 0;
                    if (!ite.hasNext()) {
                        ite = c.iterator();
                    }
                    v = ite.next();
                }
            }
            unit = newUnit;
        }

        // handle receiver polymorphic and data polymorphic
        int resultSize = result.size();
        for (int i = 0; i < resultSize; i++) {
            TemplateAttribute base = result.get(i);
            boolean first = true;
            for (IRubyObject recv : receivers) {
                base.setReceiver(recv);
                base.setBlock(block);
                if (recv instanceof PolymorphicObject) {
                    for (PolymorphicObject mrecv : ((PolymorphicObject) recv).generateMonomorphicObjects()) {
                        base.setReceiver(mrecv);
                        if (!first) {
                            result.add(base);
                        }
                        first = false;
                        base = base.clone();
                    }
                } else {
                    if (!first) {
                        result.add(base);
                    }
                    first = false;
                    base = base.clone();
                }
            }
        }

        // handle arguments data polymorphic
        for (int i = 0; i < args.size(); i++) {
            resultSize = result.size();
            for (int j = 0; j < resultSize; j++) {
                TemplateAttribute base = result.get(j);
                IRubyObject arg = base.getArg(i);
                if (arg instanceof PolymorphicObject) {
                    boolean first = true;
                    for (PolymorphicObject marg : ((PolymorphicObject) arg).generateMonomorphicObjects()) {
                        base.setArg(i, marg);
                        if (!first) {
                            result.add(base);
                        }
                        first = false;
                        base = base.clone();
                    }
                }
            }
        }

        return result;
    }

    public static Vertex yield(Graph graph, Block block, IRubyObject arg, boolean expanded, Vertex returnVertex) {
        return yield(graph, block, Arrays.asList(arg), expanded, returnVertex);
    }

    public static Vertex yield(Graph graph, Block block, IRubyObject[] args, boolean expanded, Vertex returnVertex) {
        return yield(graph, block, Arrays.asList(args), expanded, returnVertex);
    }
    
    public static Vertex yield(Graph graph, Block block, Collection<IRubyObject> args, boolean expanded, Vertex returnVertex) {
        if (block == null) {
            return Vertex.EMPTY;
        }
        Ruby runtime = graph.getRuntime();
        Context context = runtime.getContext();
        
        Node varNode = block.getVarNode();
        boolean noargblock = false;
        MultipleAsgnNode masgn = null;
        int preCount = 0;
        boolean isRest = false;
        Node rest = null;
        ListNode pre = null;
        Vertex vertex = graph.createFreeVertex();

        if (varNode == null || varNode instanceof ZeroArgNode) {
            noargblock = true;
        } else if (varNode instanceof MultipleAsgnNode) {
            masgn = (MultipleAsgnNode) varNode;
            preCount = masgn.getPreCount();
            isRest = masgn.getRest() != null;
            rest = masgn.getRest();
            pre = masgn.getPre();
        }
        
        if (args != null && !args.isEmpty()) {
            for (IRubyObject value : args) {
                pushLoopFrame(context, block.getFrame(), returnVertex, vertex);
                context.pushScope(block.getScope());

                if (noargblock) {}
                else if (masgn != null) {
                    Array array;
                    if (!expanded) {
                        // FIXME to_ary
                        if (value instanceof Array) {
                            array = (Array) value;
                        } else {
                            array = createArray(graph, new Vertex[] { graph.createFreeSingleTypeVertex(value) });
                        }
                    } else {
                        if (value instanceof Array) {
                            array = (Array) value;
                        } else {
                            array = createArray(graph, new Vertex[] { graph.createFreeSingleTypeVertex(value) });
                        }
                    }
                    multipleAssign(graph, masgn, array);
                } else {
                    assign(graph, varNode, graph.createFreeSingleTypeVertex(value));
                }

                if (block.getBodyNode() != null) {
                    Vertex v = graph.createVertex(block.getBodyNode());
                    graph.addEdgeAndPropagate(v, vertex);
                }

                context.popScope();
                popLoopFrame(context);
            }
        } else {
            pushLoopFrame(context, block.getFrame(), returnVertex, vertex);
            context.pushScope(block.getScope());

            if (block.getBodyNode() != null) {
                Vertex v = graph.createVertex(block.getBodyNode());
                graph.addEdgeAndPropagate(v, vertex);
            }
            
            context.popScope();
            popLoopFrame(context);
        }

        return vertex;
    }

    public static Vertex yield(Graph graph, YieldVertex vertex) {
        Vertex returnVertex = null;
        if (vertex.getTemplate() != null)
            returnVertex = vertex.getTemplate().getReturnVertex();

        // return immediately if no need to apply
        Proc block = (Proc) vertex.getBlock();
        Vertex argsVertex = vertex.getArgsVertex();
        if (block != null
            && block.isApplied(vertex)
            && (argsVertex == null
                || !argsVertex.isChanged())) {
            return vertex;
        }
        if (argsVertex != null)
            argsVertex.markUnchanged();

        if (block != null)
            block.recordYield(vertex);
        returnVertex = yield(graph, block, (argsVertex != null ? argsVertex.getTypeSet() : TypeSet.EMPTY), vertex.getExpandArguments(), returnVertex);
        if (returnVertex != null)
            graph.addEdgeAndUpdate(returnVertex, vertex);
        
        return vertex;
    }

    public static void splatValue(Graph graph, SplatVertex vertex) {
        vertex.addTypes(arrayValue(graph, vertex.getValueVertex()));
    }

    public static void toAryValue(Graph graph, ToAryVertex vertex) {
        vertex.addTypes(arrayValue(graph, vertex.getValueVertex()));
    }

    public static TypeSet arrayValue(Graph graph, Vertex vertex) {
        Ruby runtime = graph.getRuntime();
        TypeSet typeSet = new TypeSet();
        for (IRubyObject object : vertex.getTypeSet()) {
            if (object.isKindOf(runtime.getArray())) {
                typeSet.add(object);
            } else {
                CallVertex callVertex = new CallVertex(vertex.getNode(), "to_a", vertex, null, null);
                for (IRubyObject array : call(graph, callVertex).getTypeSet()) {
                    if (array.isKindOf(runtime.getArray())) {
                        typeSet.add(array);
                    } else {
                        Logger.warn("to_a should be return Array");
                    }
                }
            }
        }
        return typeSet;
    }

    public static void aValueSplat(Graph graph, SValueVertex vertex) {
        for (IRubyObject object : vertex.getValueVertex().getTypeSet()) {
            if (object.isKindOf(graph.getRuntime().getArray())) {
                if (object instanceof Array) {
                    Array array = (Array) object;
                    if (!array.isModified()) {
                        if (array.length() == 1) {
                            vertex.update(array.getElement(0));
                        } else {
                            vertex.addType(object);
                        }
                        continue;
                    }
                }
                TypeVariable var = TypeVariable.valueOf("t");
                TypeVarMap tvmap = getTypeVarMap(object);
                if (tvmap != null && tvmap.containsKey(var)) {
                    vertex.update(tvmap.get(var));
                }
            }
        }
    }

    public static RubyModule getNamespace(Graph graph, Colon3Node node) {
        if (node instanceof Colon2ImplicitNode) {
            return graph.getRuntime().getContext().getFrameModule();
        } else if (node instanceof Colon2Node) {
            Vertex left = graph.createVertex(((Colon2Node) node).getLeftNode());
            IRubyObject object = left.singleType();
            if (object instanceof RubyModule) {
                return (RubyModule) object;
            } else {
                return null;
            }
        } else if (node instanceof Colon3Node) {
            return graph.getRuntime().getObject();
        }
        Logger.fixme("Namespace unresolved: %s", node);
        return null;
    }

    public static Template getFrameTemplate(Frame frame) {
        Object tag;
        while (frame != null) {
            tag = frame.getTag();
            if (tag instanceof Template) {
                return (Template) tag;
            }
            frame = frame.getPrevious();
        }
        return null;
    }

    public static void setFrameTemplate(Frame frame, Template template) {
        frame.setTag(template);
    }

    public static LoopTag getFrameLoopTag(Frame frame) {
        Object tag = frame.getTag();
        if (tag instanceof LoopTag) {
            return (LoopTag) tag;
        } else {
            return null;
        }
    }

    public static void pushLoopFrame(Context context, Vertex returnVertex, Vertex yieldVertex) {
        pushLoopFrame(context, context.getCurrentFrame(), returnVertex, yieldVertex);
    }
    
    public static void pushLoopFrame(Context context, Frame prev, Vertex returnVertex, Vertex yieldVertex) {
        Frame frame = context.pushFrame(prev.getModule(), prev.getName(), prev.getSelf(), prev.getBlock(), prev.getVisibility());
        frame.setTag(new LoopTag(returnVertex, yieldVertex));
    }

    public static void popLoopFrame(Context context) {
        context.popFrame();
    }

    public static ClassTag getClassTag(RubyModule klass) {
        if (klass instanceof MetaClass) {
            MetaClass metaClass = (MetaClass) klass;
            if (metaClass.getAttached() instanceof RubyModule) {
                klass = (RubyModule) metaClass.getAttached();
            }
        }
        if (klass != null && klass.getTag() instanceof ClassTag) {
            return (ClassTag) klass.getTag();
        } else {
            return null;
        }
    }

    public static ClassType getClassAnnotation(RubyModule klass) {
        ClassTag tag = getClassTag(klass);
        return tag != null ? tag.getType() : null;
    }
    
    public static void setClassTag(RubyModule klass, Node node, List<TypeAnnotation> annotations) {
        if (getClassAnnotation(klass) == null) {
            ClassType type = null;
            for (TypeAnnotation annot : annotations) {
                if (annot instanceof ClassType) {
                    type = (ClassType) annot;
                    break;
                }
            }
            klass.setTag(new ClassTag(node, type));
        }
    }

    public static void setMethodTag(Method method, /*unused*/Node node, List<TypeAnnotation> annotations) {
        if (!annotations.isEmpty()) {
            List<MethodType> types = new ArrayList<MethodType>();
            for (TypeAnnotation annot : annotations) {
                if (annot instanceof MethodType) {
                    types.add((MethodType) annot);
                }
            }
            method.setAnnotations(types);
        }
    }

    public static ClassTag getEnclosingClassTag(RubyModule module) {
        ClassTag tag;
        while (module != null) {
            tag = getClassTag(module);
            if (tag != null) {
                return tag;
            }
            module = module.getParent();
        }
        return null;
    }

    public static ClassType getEnclosingClassAnnotation(RubyModule module) {
        ClassTag tag = getEnclosingClassTag(module);
        return tag != null ? tag.getType() : null;
    }

    public static TypeVarMap getTypeVarMap(IRubyObject object) {
        if (object.getTag() instanceof TypeVarMap) {
            return (TypeVarMap) object.getTag();
        } else {
            return null;
        }
    }

    public static Array createArray(Graph graph, Vertex[] elements) {
        Array array = new Array(graph.getRuntime(), elements);
        TypeVarVertex t = new TypeVarVertex(array);
        array.setTypeVarVertex(t);
        return array;
    }

    public static Vertex createArrayVertex(Graph graph, Node node, Vertex[] elements) {
        Array array = createArray(graph, elements);
        Vertex vertex = new PassThroughVertex(node);
        vertex.addType(array);
        array.getTypeVarVertex().addEdge(vertex);
        return vertex;
    }

    public static Vertex createArrayVertex(Graph graph, Node node, Vertex[] elements, int offset, int length) {
        Vertex[] newElements = new Vertex[length];
        System.arraycopy(elements, offset, newElements, 0, length);
        return createArrayVertex(graph, node, newElements);
    }

    public static Hash createHash(Graph graph, Vertex[] elements) {
        Hash hash = new Hash(graph.getRuntime(), elements);
        TypeVarVertex k = new TypeVarVertex(hash);
        TypeVarVertex v = new TypeVarVertex(hash);
        hash.setKeyTypeVarVertex(k);
        hash.setValueTypeVarVertex(v);
        return hash;
    }


    public static Vertex createHashVertex(Graph graph, Node node, Vertex[] elements) {
        Hash hash = createHash(graph, elements);
        Vertex vertex = new PassThroughVertex(node);
        vertex.addType(hash);
        hash.getKeyTypeVarVertex().addEdge(vertex);
        hash.getValueTypeVarVertex().addEdge(vertex);
        return vertex;
    }

    public static Vertex[] toVertices(Graph graph, ListNode node) {
        Vertex[] vertices = new Vertex[node.size()];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = graph.createVertex(node.get(i));
        }
        return vertices;
    }

    public static void setMethodsVisibility(Graph graph, TypeSet receivers, Vertex[] args, Visibility visibility) {
        if (args == null || args.length == 0) {
            // FIXME module check
            graph.getRuntime().getContext().getCurrentFrame().setVisibility(visibility);
        } else {
            for (IRubyObject receiver : receivers) {
                if (receiver.isKindOf(graph.getRuntime().getModule())) {
                    RubyModule module = (RubyModule) receiver;
                    for (Vertex arg : args) {
                        String name = Vertex.getStringOrSymbol(arg);
                        if (name != null) {
                            DynamicMethod method = module.getMethod(name);
                            if (method != null) {
                                method.setVisibility(visibility);
                                if (visibility == Visibility.MODULE_FUNCTION) {
                                    module.getSingletonClass().addMethod(name, method);
                                }
                            }
                        }                        
                    }
                }
            }
        }
    }

    public static void defineAttrs(Graph graph, TypeSet receivers, Vertex[] args, boolean reader, boolean writer) {
        if (args != null && args.length > 0) {
            for (IRubyObject receiver : receivers) {
                if (receiver.isKindOf(graph.getRuntime().getModule())) {
                    for (Vertex arg : args) {
                        String name = Vertex.getStringOrSymbol(arg);
                        if (name != null) {
                            if (reader) {
                                defineAttrReader(graph, name);
                            }
                            if (writer) {
                                defineAttrWriter(graph, name);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void defineAttrReader(Graph graph, String name) {
        ISourcePosition pos = new SimpleSourcePosition("(generated)", 0);
        graph.createVertex(new DefnNode(pos,
                                        new ArgumentNode(pos, name),
                                        new ArgsNoArgNode(pos),
                                        new LocalStaticScope(null),
                                        new InstVarNode(pos, "@" + name)));
    }

    public static void defineAttrWriter(Graph graph, String name) {
        ISourcePosition pos = new SimpleSourcePosition("(generated)", 0);
        graph.createVertex(new DefnNode(pos,
                                        new ArgumentNode(pos, name + "="),
                                        new ArgsPreOneArgNode(pos, new ListNode(null, new ArgumentNode(null, name))),
                                        new LocalStaticScope(null),
                                        new InstAsgnNode(pos, "@" + name, new LocalVarNode(null, 0, name))));
    }
}
