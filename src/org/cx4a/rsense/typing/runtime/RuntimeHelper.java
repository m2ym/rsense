package org.cx4a.rsense.typing.runtime;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Collections;
import java.util.Arrays;

import org.jruby.ast.Node;
import org.jruby.ast.ArgsNode;
import org.jruby.ast.ArgumentNode;
import org.jruby.ast.ListNode;
import org.jruby.ast.LocalAsgnNode;
import org.jruby.ast.DAsgnNode;
import org.jruby.ast.InstAsgnNode;
import org.jruby.ast.MultipleAsgnNode;
import org.jruby.ast.ConstDeclNode;
import org.jruby.ast.Colon2Node;
import org.jruby.ast.Colon2ImplicitNode;
import org.jruby.ast.Colon3Node;
import org.jruby.ast.AssignableNode;
import org.jruby.ast.YieldNode;
import org.jruby.ast.ZeroArgNode;
import org.jruby.ast.NodeType;
import org.jruby.ast.types.INameNode;

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
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.util.Logger;
import org.cx4a.rsense.typing.TypeSet;
import org.cx4a.rsense.typing.Template;
import org.cx4a.rsense.typing.TemplateAttribute;
import org.cx4a.rsense.typing.Graph;
import org.cx4a.rsense.typing.runtime.TypeVarMap;
import org.cx4a.rsense.typing.runtime.AnnotationHelper;
import org.cx4a.rsense.typing.annotation.TypeAnnotation;
import org.cx4a.rsense.typing.annotation.TypeExpression;
import org.cx4a.rsense.typing.annotation.TypeVariable;
import org.cx4a.rsense.typing.annotation.ClassType;
import org.cx4a.rsense.typing.annotation.MethodType;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.vertex.ArrayVertex;
import org.cx4a.rsense.typing.vertex.CallVertex;
import org.cx4a.rsense.typing.vertex.MultipleAsgnVertex;
import org.cx4a.rsense.typing.vertex.ToAryVertex;
import org.cx4a.rsense.typing.vertex.YieldVertex;

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
        case CONSTDECLNODE:
            return constDeclaration(graph, (ConstDeclNode) node, src);
        case ARGUMENTNODE:
        case RESTARG:
            return argumentAssign(graph, (ArgumentNode) node, src);
        }
        Logger.error("Unknown assignable node");
        return Graph.NULL_VERTEX;
    }
    
    public static Vertex localAssign(Graph graph, LocalAsgnNode node) {
        return localAssign(graph, node, null);
    }

    public static Vertex localAssign(Graph graph, LocalAsgnNode node, Vertex src) {
        Scope scope = graph.getRuntime().getContext().getCurrentScope();
        VertexHolder holder = (VertexHolder) scope.getValue(node.getName());
        Vertex dest;
        if (src == null) {
            src = graph.createVertex(node.getValueNode());
        }
        if (holder == null) {
            dest = new Vertex();
            holder = new VertexHolder(graph.getRuntime(), dest);
            scope.setValue(node.getName(), holder);
        } else {
            dest = holder.getVertex();
        }
        graph.addEdgeAndPropagate(src, dest);
        return src;
    }

    public static Vertex dynamicAssign(Graph graph, DAsgnNode node) {
        return dynamicAssign(graph, node, null);
    }

    public static Vertex dynamicAssign(Graph graph, DAsgnNode node, Vertex src) {
        Scope scope = graph.getRuntime().getContext().getCurrentScope();
        VertexHolder holder = (VertexHolder) scope.getValue(node.getName());
        Vertex dest;
        if (src == null) {
            src = graph.createVertex(node.getValueNode());
        }
        if (holder == null) {
            holder = graph.createFreeVertexHolder();
            dest = holder.getVertex();
            scope.setValue(node.getName(), holder);
        } else {
            dest = holder.getVertex();
        }
        graph.addEdgeAndPropagate(src, dest);
        return src;
    }


    public static Vertex instanceAssign(Graph graph, InstAsgnNode node) {
        return instanceAssign(graph, node, null);
    }

    public static Vertex instanceAssign(Graph graph, InstAsgnNode node, Vertex src) {
        IRubyObject self = graph.getRuntime().getContext().getFrameSelf();
        VertexHolder holder = (VertexHolder) self.getInstVar(node.getName());
        Vertex dest;
        if (src == null) {
            src = graph.createVertex(node.getValueNode());
        }
        if (holder == null) {
            holder = graph.createFreeVertexHolder();
            dest = holder.getVertex();
            self.setInstVar(node.getName(), holder);
        } else {
            dest = holder.getVertex();
        }
        graph.addEdgeAndPropagate(src, dest);
        return src;
    }

    public static void argsAssign(Graph graph, ArgsNode argsNode, Vertex[] args) {
        Scope scope = graph.getRuntime().getContext().getCurrentScope();
        ListNode pre = argsNode.getPre();
        ListNode post = argsNode.getPost();
        int preCount = argsNode.getPreCount();
        int postCount = argsNode.getPostCount();
        if (preCount > 0) {
            int size = pre.size();
            for (int i = 0; i < size; i++) {
                Node next = pre.get(i);
                Vertex arg = i < args.length ? args[i] : Graph.NULL_VERTEX;
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
                    : Graph.NULL_VERTEX;
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
                assign(graph, restArgNode, graph.createFreeSingleTypeVertex(new Tuple(graph.getRuntime())));
            } else {
                Vertex v = graph.createFreeVertex();
                for (Tuple tuple : Tuple.generateTuples(graph.getRuntime(), args, givenArgsCount, sizeOfRestArg)) {
                    v.addType(tuple);
                }
                assign(graph, restArgNode, v);
            }
        }
        if (argsNode.getBlock() != null) {
            // FIXME
        }
    }

    public static Vertex argumentAssign(Graph graph, ArgumentNode node, Vertex src) {
        Scope scope = graph.getRuntime().getContext().getCurrentScope();
        VertexHolder holder = graph.createFreeVertexHolder();
        scope.setValue(node.getName(), holder);
        if (src != null) {
            graph.addEdgeAndCopyTypeSet(src, holder.getVertex());
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

    public static Vertex multipleAssign(Graph graph, MultipleAsgnNode node, Tuple tuple) {
        int valueLen = tuple.getLength();
        int varLen = node.getHeadNode() == null ? 0 : node.getHeadNode().size();

        int j = 0;
        for (; j < valueLen && j < varLen; j++) {
            assign(graph, node.getHeadNode().get(j), graph.createFreeSingleTypeVertex(tuple.get(j)));
        }

        Node argsNode = node.getArgsNode();
        if (argsNode != null) {
            if (argsNode.getNodeType() == NodeType.STARNODE) {
                // no check for '*'
            } else if (varLen < valueLen) {
                assign(graph, argsNode, graph.createFreeSingleTypeVertex(tuple.subTuple(varLen, valueLen - varLen)));
            } else {
                assign(graph, argsNode, graph.createFreeSingleTypeVertex(new Tuple(graph.getRuntime())));
            }
        }

        while (j < varLen) {
            assign(graph, node.getHeadNode().get(j++), Graph.NULL_VERTEX);
        }
        return graph.createFreeSingleTypeVertex(tuple);
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
            module.setConstant(name, src.singleType());
        }

        return src;
    }

    public static Vertex call(Graph graph, CallVertex vertex) {
        if (vertex.isApplicable()) {
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
                    if (!result.isCallNextMethod()) {
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
                for (TemplateAttribute attr : attrs) {
                    Vertex returnVertex = applyTemplateAttribute(graph, vertex, name, attr);
                    if (returnVertex != null && !noReturn) {
                        accumulator.addAll(returnVertex.getTypeSet());
                    }
                }
                if (result.isNextMethodChange()) {
                    name = result.getNextMethodName();
                    receivers = result.getNextMethodReceivers();
                    block = result.getNextMethodBlock();
                    noReturn = result.isNextMethodNoReturn();
                } else {
                    break;
                }
            }

            vertex.getTypeSet().addAll(accumulator);
        }
        return vertex;
    }

    private static Vertex applyTemplateAttribute(Graph graph, CallVertex vertex, String name, TemplateAttribute attr) {
        IRubyObject receiver = attr.getReceiver();
        RubyClass receiverType = receiver.getMetaClass();
        Method method = (Method) receiverType.searchMethod(name);
        if (method != null) {
            Template template = method.getTemplate(attr);
            if (template == null) {
                template = createTemplate(graph, vertex, method, attr);
            } else {
                AnnotationHelper.mergeAffectedMap(template, receiver);
                Logger.debug("Template reused: %s", name);
            }
            return template.getReturnVertex();
        }
        return null;
    }

    public static Template createTemplate(Graph graph, CallVertex vertex, Method method, TemplateAttribute attr) {
        IRubyObject receiver = attr.getReceiver();
        Vertex[] argVertices = new Vertex[attr.getArgs().length];
        for (int i = 0; i < argVertices.length; i++) {
            argVertices[i] = graph.createFreeSingleTypeVertex(attr.getArg(i));
        }

        Ruby runtime = graph.getRuntime();
        Context context = runtime.getContext();

        context.pushFrame(method.getModule(), receiver, attr.getBlock(), Visibility.PUBLIC);
        context.pushScope(new LocalScope());

        Template template = new Template(method, context.getCurrentFrame(), attr);
        method.addTemplate(attr, template);

        Vertex returnVertex = template.getReturnVertex();
        setFrameTemplate(context.getCurrentFrame(), template);

        boolean noReturn = AnnotationHelper.resolveMethodAnnotation(graph, template);
        
        argsAssign(graph, (ArgsNode) method.getArgsNode(), argVertices);

        if (method.getBodyNode() != null) {
            Vertex result = graph.createVertex(method.getBodyNode());
            if (result != null && !noReturn) {
                graph.addEdgeAndCopyTypeSet(result, returnVertex);
            }
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
                result.get(j).getArgs()[i] = v;
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

        // handle receiver polymorphic
        int resultSize = result.size();
        for (int i = 0; i < resultSize; i++) {
            TemplateAttribute base = result.get(i);
            Iterator<IRubyObject> ite = receivers.iterator();
            base.setReceiver(ite.next());
            base.setBlock(block);
            for (int j = 0; j < receivers.size() - 1; j++) {
                TemplateAttribute attr = new TemplateAttribute(base.getArgs());
                attr.setReceiver(ite.next());
                attr.setBlock(block);
                result.add(attr);
            }
        }

        // handle data polymorphic receivers
        resultSize = result.size();
        for (int i = 0; i < resultSize; i++) {
            TemplateAttribute base = result.get(i);
            IRubyObject receiver = base.getReceiver();
            
            TypeVarMap map = AnnotationHelper.getTypeVarMap(receiver);
            if (map != null) {
                // data polymorphic object
                TypeVarMap[] tuples = map.generateTuples();
                if (tuples.length > 0) {
                    base.setTypeVarMap(tuples[0]);
                    for (int j = 1; j < tuples.length; j++) {
                        TemplateAttribute attr = new TemplateAttribute(base.getArgs());
                        attr.setReceiver(base.getReceiver());
                        attr.setTypeVarMap(tuples[j]);
                        attr.setBlock(base.getBlock());
                        result.add(attr);
                    }
                }
            }
        }
        
        return result;
    }

    public static Vertex yield(Graph graph, Block block, IRubyObject arg, boolean expanded) {
        return yield(graph, block, Arrays.asList(arg), expanded);
    }
    
    public static Vertex yield(Graph graph, Block block, Collection<IRubyObject> args, boolean expanded) {
        Ruby runtime = graph.getRuntime();
        Context context = runtime.getContext();
        Frame frame = context.getCurrentFrame();
        Node varNode = block.getVarNode();
        boolean noargblock = false;
        MultipleAsgnNode masgn = null;
        int preCount = 0;
        boolean isRest = false;
        Node rest = null;
        ListNode pre = null;
        Vertex vertex = graph.createFreeVertex();
        Vertex returnVertex = getFrameTemplate(frame).getReturnVertex();

        if (varNode == null || varNode instanceof ZeroArgNode) {
            noargblock = true;
        } else if (varNode instanceof MultipleAsgnNode) {
            masgn = (MultipleAsgnNode) varNode;
            preCount = masgn.getPreCount();
            isRest = masgn.getRest() != null;
            rest = masgn.getRest();
            pre = masgn.getPre();
        }
        
        for (IRubyObject value : args) {
            pushLoopFrame(graph, block.getFrame(), returnVertex);
            context.pushScope(block.getScope());

            if (noargblock) {}
            else if (masgn != null) {
                Tuple tuple;
                if (!expanded) {
                    // FIXME
                    tuple = (Tuple) value;
                } else {
                    tuple = (Tuple) value;
                }
                multipleAssign(graph, masgn, tuple);
            } else {
                assign(graph, varNode, graph.createFreeSingleTypeVertex(value));
            }

            if (block.getBodyNode() != null) {
                Vertex v = graph.createVertex(block.getBodyNode());
                graph.addEdgeAndPropagate(v, vertex);
            }

            context.popScope();
            popLoopFrame(graph);
        }

        return vertex;
    }

    public static Vertex yield(Graph graph, YieldVertex vertex) {
        YieldNode node = (YieldNode) vertex.getNode();
        Block block = vertex.getBlock();
        Vertex returnVertex = yield(graph, block, vertex.getArgsVertex().getTypeSet(), node.getExpandArguments());
        if (returnVertex != null) {
            graph.addEdgeAndCopyTypeSet(returnVertex, vertex);
        }
        return vertex;
    }

    public static RubyModule getNamespace(Graph graph, Colon3Node node) {
        if (node instanceof Colon2ImplicitNode) {
            return graph.getRuntime().getContext().getFrameModule();
        } else if (node instanceof Colon2Node) {
            IRubyObject object = graph.createVertex(((Colon2Node) node).getLeftNode()).singleType();
            if (object instanceof RubyModule) {
                return (RubyModule) object;
            }
        } else if (node instanceof Colon3Node) {
            return graph.getRuntime().getObject();
        }
        Logger.fixme("Namespace unresolved");
        return null;
    }

    public static Template getFrameTemplate(Frame frame) {
        Object tag;
        while (true) {
            tag = frame.getTag();
            if (tag instanceof Template) {
                return (Template) tag;
            } else if (tag instanceof Vertex) {
            } else {
                return null;
            }
        }
    }

    public static void setFrameTemplate(Frame frame, Template template) {
        frame.setTag(template);
    }

    public static Vertex getFrameVertex(Frame frame) {
        Object tag = frame.getTag();
        if (tag instanceof Vertex) {
            return (Vertex) tag;
        } else {
            return null;
        }
    }

    public static void setFrameVertex(Frame frame, Vertex vertex) {
        frame.setTag(vertex);
    }

    public static void pushLoopFrame(Graph graph, Vertex vertex) {
        pushLoopFrame(graph, graph.getRuntime().getContext().getCurrentFrame(), vertex);
    }
    
    public static void pushLoopFrame(Graph graph, Frame prev, Vertex vertex) {
        Context context = graph.getRuntime().getContext();
        Frame frame = context.pushFrame(prev.getModule(), prev.getSelf(), prev.getBlock(), prev.getVisibility());
        setFrameVertex(frame, vertex);
    }

    public static void popLoopFrame(Graph graph) {
        graph.getRuntime().getContext().popFrame();
    }
}
