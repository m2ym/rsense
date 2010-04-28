package org.cx4a.rsense.typing;

import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.Collections;
import java.util.Arrays;

import org.jruby.ast.AliasNode;
import org.jruby.ast.AndNode;
import org.jruby.ast.ArgsCatNode;
import org.jruby.ast.ArgsNode;
import org.jruby.ast.ArgsPushNode;
import org.jruby.ast.ArrayNode;
import org.jruby.ast.AttrAssignNode;
import org.jruby.ast.BackRefNode;
import org.jruby.ast.BeginNode;
import org.jruby.ast.BignumNode;
import org.jruby.ast.BlockArgNode;
import org.jruby.ast.BlockNode;
import org.jruby.ast.BlockPassNode;
import org.jruby.ast.BreakNode;
import org.jruby.ast.CallNode;
import org.jruby.ast.CaseNode;
import org.jruby.ast.ClassNode;
import org.jruby.ast.ClassVarAsgnNode;
import org.jruby.ast.ClassVarDeclNode;
import org.jruby.ast.ClassVarNode;
import org.jruby.ast.Colon2Node;
import org.jruby.ast.Colon3Node;
import org.jruby.ast.ConstDeclNode;
import org.jruby.ast.ConstNode;
import org.jruby.ast.DAsgnNode;
import org.jruby.ast.DRegexpNode;
import org.jruby.ast.DStrNode;
import org.jruby.ast.DSymbolNode;
import org.jruby.ast.DVarNode;
import org.jruby.ast.DXStrNode;
import org.jruby.ast.DefinedNode;
import org.jruby.ast.DefnNode;
import org.jruby.ast.DefsNode;
import org.jruby.ast.DotNode;
import org.jruby.ast.EncodingNode;
import org.jruby.ast.EnsureNode;
import org.jruby.ast.EvStrNode;
import org.jruby.ast.FCallNode;
import org.jruby.ast.FalseNode;
import org.jruby.ast.FixnumNode;
import org.jruby.ast.FlipNode;
import org.jruby.ast.FloatNode;
import org.jruby.ast.ForNode;
import org.jruby.ast.GlobalAsgnNode;
import org.jruby.ast.GlobalVarNode;
import org.jruby.ast.HashNode;
import org.jruby.ast.IfNode;
import org.jruby.ast.InstAsgnNode;
import org.jruby.ast.InstVarNode;
import org.jruby.ast.IterNode;
import org.jruby.ast.LocalAsgnNode;
import org.jruby.ast.LocalVarNode;
import org.jruby.ast.Match2Node;
import org.jruby.ast.Match3Node;
import org.jruby.ast.MatchNode;
import org.jruby.ast.ModuleNode;
import org.jruby.ast.MultipleAsgn19Node;
import org.jruby.ast.MultipleAsgnNode;
import org.jruby.ast.NewlineNode;
import org.jruby.ast.NextNode;
import org.jruby.ast.NilNode;
import org.jruby.ast.NotNode;
import org.jruby.ast.NthRefNode;
import org.jruby.ast.OpAsgnAndNode;
import org.jruby.ast.OpAsgnNode;
import org.jruby.ast.OpAsgnOrNode;
import org.jruby.ast.OpElementAsgnNode;
import org.jruby.ast.OrNode;
import org.jruby.ast.PostExeNode;
import org.jruby.ast.PreExeNode;
import org.jruby.ast.RedoNode;
import org.jruby.ast.RegexpNode;
import org.jruby.ast.RescueBodyNode;
import org.jruby.ast.RescueNode;
import org.jruby.ast.RestArgNode;
import org.jruby.ast.RetryNode;
import org.jruby.ast.ReturnNode;
import org.jruby.ast.RootNode;
import org.jruby.ast.SClassNode;
import org.jruby.ast.SValueNode;
import org.jruby.ast.SelfNode;
import org.jruby.ast.SplatNode;
import org.jruby.ast.StrNode;
import org.jruby.ast.SuperNode;
import org.jruby.ast.SymbolNode;
import org.jruby.ast.ToAryNode;
import org.jruby.ast.TrueNode;
import org.jruby.ast.UndefNode;
import org.jruby.ast.UntilNode;
import org.jruby.ast.VAliasNode;
import org.jruby.ast.VCallNode;
import org.jruby.ast.WhenNode;
import org.jruby.ast.WhileNode;
import org.jruby.ast.XStrNode;
import org.jruby.ast.YieldNode;
import org.jruby.ast.ZArrayNode;
import org.jruby.ast.ZSuperNode;
import org.jruby.ast.MethodDefNode;

import org.jruby.ast.NodeType;
import org.jruby.ast.Node;
import org.jruby.ast.ListNode;
import org.jruby.ast.AssignableNode;
import org.jruby.ast.ZeroArgNode;
import org.jruby.ast.types.INameNode;
import org.jruby.ast.visitor.NodeVisitor;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.Context;
import org.cx4a.rsense.ruby.RubyClass;
import org.cx4a.rsense.ruby.RubyModule;
import org.cx4a.rsense.ruby.MetaClass;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.ruby.RubyObject;
import org.cx4a.rsense.ruby.Visibility;
import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.ruby.Frame;
import org.cx4a.rsense.ruby.Scope;
import org.cx4a.rsense.ruby.LocalScope;
import org.cx4a.rsense.ruby.DynamicScope;
import org.cx4a.rsense.ruby.DynamicMethod;
import org.cx4a.rsense.util.Logger;
import org.cx4a.rsense.util.NodeDiff;
import org.cx4a.rsense.util.SourceLocation;
import org.cx4a.rsense.typing.runtime.ObjectAllocator;
import org.cx4a.rsense.typing.runtime.VertexHolder;
import org.cx4a.rsense.typing.runtime.Array;
import org.cx4a.rsense.typing.runtime.Hash;
import org.cx4a.rsense.typing.runtime.Method;
import org.cx4a.rsense.typing.runtime.DefaultMethod;
import org.cx4a.rsense.typing.runtime.AliasMethod;
import org.cx4a.rsense.typing.runtime.SpecialMethod;
import org.cx4a.rsense.typing.runtime.Proc;
import org.cx4a.rsense.typing.runtime.RuntimeHelper;
import org.cx4a.rsense.typing.runtime.AnnotationHelper;
import org.cx4a.rsense.typing.runtime.TypeVarMap;
import org.cx4a.rsense.typing.runtime.LoopTag;
import org.cx4a.rsense.typing.runtime.ClassTag;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.vertex.PassThroughVertex;
import org.cx4a.rsense.typing.vertex.TypeVarVertex;
import org.cx4a.rsense.typing.vertex.CallVertex;
import org.cx4a.rsense.typing.vertex.MultipleAsgnVertex;
import org.cx4a.rsense.typing.vertex.ToAryVertex;
import org.cx4a.rsense.typing.vertex.SplatVertex;
import org.cx4a.rsense.typing.vertex.SValueVertex;
import org.cx4a.rsense.typing.vertex.YieldVertex;
import org.cx4a.rsense.typing.annotation.TypeAnnotation;
import org.cx4a.rsense.typing.annotation.TypeVariable;
import org.cx4a.rsense.typing.annotation.ClassType;
import org.cx4a.rsense.typing.annotation.MethodType;

public class Graph implements NodeVisitor {
    public interface EventListener {
        public enum EventType { DEFINE, CLASS, MODULE, METHOD_MISSING }
        
        public static class Event {
            public final EventType type;

            // TODO divide into classes
            public final String name;
            public final Node node;
            public final Vertex vertex;
            
            public Event(EventType type, String name, Node node) {
                this.type = type;
                this.name = name;
                this.node = node;
                this.vertex = null;
            }

            public Event(EventType type, Vertex vertex) {
                this.type = type;
                this.name = null;
                this.node = null;
                this.vertex = vertex;
            }
        }
        
        public void update(Event event);
    }

    protected static class DummyCall {
        private MethodDefNode node;
        private Method newMethod;
        private Method oldMethod;
        private IRubyObject receiver;

        public DummyCall(MethodDefNode node, Method newMethod, Method oldMethod, IRubyObject receiver) {
            this.node = node;
            this.newMethod = newMethod;
            this.oldMethod = oldMethod;
            this.receiver = receiver;
        }

        public void force(Graph graph) {
            if (!newMethod.isTemplatesShared()) {
                Collection<TemplateAttribute> templateAttributes = oldMethod != null ? oldMethod.getTemplates().keySet() : null;
                if (templateAttributes != null && !templateAttributes.isEmpty()) {
                    RuntimeHelper.dummyCallForTemplates(graph, node, newMethod, templateAttributes);
                } else {
                    RuntimeHelper.dummyCall(graph, node, newMethod, receiver);
                }
            }
        }
    }

    protected Ruby runtime;
    protected Context context;
    protected Map<String, SpecialMethod> specialMethods;
    protected NodeDiff nodeDiff;
    protected Queue<DummyCall> dummyCallQueue = new LinkedList<DummyCall>();
    protected List<EventListener> eventListeners;

    public Graph() {
        this.runtime = new Ruby();
        this.runtime.setObjectAllocator(new ObjectAllocator());
        this.context = runtime.getContext();
        this.specialMethods = new HashMap<String, SpecialMethod>();
        this.eventListeners = new ArrayList<EventListener>();
        init();
    }

    public Ruby getRuntime() {
        return runtime;
    }

    public Map<String, SpecialMethod> getSpecialMethods() {
        return specialMethods;
    }

    public SpecialMethod getSpecialMethod(String name) {
        return specialMethods.get(name);
    }

    public void addSpecialMethod(String name, SpecialMethod method) {
        specialMethods.put(name, method);
    }

    public NodeDiff getNodeDiff() {
        return nodeDiff;
    }

    public void setNodeDiff(NodeDiff nodeDiff) {
        this.nodeDiff = nodeDiff;
    }

    public void addEventListener(EventListener eventListener) {
        eventListeners.add(eventListener);
    }

    public void removeEventListener(EventListener eventListener) {
        eventListeners.remove(eventListener);
    }

    public void notifyDefineEvent(Node node, Method method) {
        for (EventListener eventListener : eventListeners)
            eventListener.update(new EventListener.Event(EventListener.EventType.DEFINE,
                                                         method.toString(),
                                                         node));
    }

    public void notifyClassEvent(Node node, RubyModule klass) {
        for (EventListener eventListener : eventListeners)
            eventListener.update(new EventListener.Event(EventListener.EventType.CLASS,
                                                         klass.toString(),
                                                         node));
    }

    public void notifyModuleEvent(Node node, RubyModule module) {
        for (EventListener eventListener : eventListeners)
            eventListener.update(new EventListener.Event(EventListener.EventType.MODULE,
                                                         module.toString(),
                                                         node));
    }

    public void notifyMethodMissingEvent(CallVertex vertex) {
        for (EventListener eventListener : eventListeners)
            eventListener.update(new EventListener.Event(EventListener.EventType.METHOD_MISSING,
                                                         vertex));
    }

    private void init() {
        addSpecialMethod("new", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    TypeSet accumulator = result.getAccumulator();
                    if (result.getPrevious() == null) {
                        TypeSet resultTypeSet = accumulator = new TypeSet();
                        TypeSet newReceivers = new TypeSet();
                        for (IRubyObject receiver : receivers) {
                            if (receiver instanceof RubyClass) {
                                RubyClass klass = (RubyClass) receiver;
                                if (klass.getMetaClass().searchMethod("new") == null) {
                                    if (klass == runtime.getProc()) {
                                        // Proc.new {}
                                        if (block instanceof Proc)
                                            resultTypeSet.add((Proc) block);
                                        else
                                            Logger.debug("Proc.new for no block is not supported yet");
                                    } else
                                        resultTypeSet.add(newInstanceOf((RubyClass) receiver));
                                } else {
                                    newReceivers.add(receiver);
                                }
                            }
                        }
                        if (!newReceivers.isEmpty()) {
                            result
                                .setResultTypeSet(resultTypeSet)
                                .setCallNextMethod(true)
                                .setNextMethodChange(true)
                                .setNextMethodName("new")
                                .setNextMethodReceivers(newReceivers)
                                .setNextMethodBlock(block);
                            return;
                        }
                    }
                    result
                        .setResultTypeSet(accumulator)
                        .setCallNextMethod(true)
                        .setNextMethodChange(true)
                        .setNextMethodName("initialize")
                        .setNextMethodReceivers(accumulator)
                        .setNextMethodBlock(block)
                        .setNextMethodNoReturn(true)
                        .setPrivateVisibility(true);
                }
            });

        addSpecialMethod("include", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    boolean included = false;
                    if (args != null) {
                        for (Vertex arg : args) {
                            for (IRubyObject receiver : receivers) {
                                // FIXME log message
                                if (!(receiver instanceof RubyModule)) {
                                    // FIXME toplevel
                                    receiver = receiver.getMetaClass();
                                }
                                if (receiver instanceof RubyModule) {
                                    RubyModule module = (RubyModule) receiver;
                                    for (IRubyObject target : arg.getTypeSet()) {
                                        if (target instanceof RubyModule) {
                                            module.includeModule((RubyModule) target);
                                            included = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!included) {
                        result.setCallNextMethod(true);
                    }
                }
            });

        addSpecialMethod("[]", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    Collection<IRubyObject> arg = null;
                    TypeSet ts = new TypeSet();
                    for (IRubyObject receiver : receivers) {
                        if (receiver instanceof Hash) {
                            if (args != null && args.length > 0) {
                                Hash hash = (Hash) receiver;
                                Object key = Hash.getRealKey(args[0].getNode());
                                if (!hash.isModified() && key != null) {
                                    Vertex v = hash.get(key);
                                    if (v != null) {
                                        ts.addAll(v.getTypeSet());
                                    }
                                }
                            }
                        } else if (receiver instanceof Array) {
                            if (args != null && args.length > 0) {
                                Array array = (Array) receiver;
                                Integer n = Vertex.getFixnum(args[0]);
                                if (!array.isModified() && n != null) {
                                    Vertex v = array.getElement(n);
                                    if (v != null) {
                                        ts.addAll(v.getTypeSet());
                                    }
                                }
                            }
                        } else if (receiver instanceof Proc) {
                            if (arg == null) {
                                if (args == null) {
                                    arg = Arrays.asList((IRubyObject) RuntimeHelper.createArray(Graph.this, new Vertex[0]));
                                } else if (args.length == 1) {
                                    arg = args[0].getTypeSet();
                                } else { arg = Arrays.asList((IRubyObject) RuntimeHelper.createArray(Graph.this, args));
                                }
                            }
                            Vertex returnVertex = createFreeVertex();
                            RuntimeHelper.yield(Graph.this, (Proc) receiver, arg, true, returnVertex);
                            ts.addAll(returnVertex.getTypeSet());
                        }
                    }

                    if (ts.isEmpty()) {
                        result.setCallNextMethod(true);
                    } else {
                        result.setResultTypeSet(ts);
                    }
                }
            });

        addSpecialMethod("private", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    RuntimeHelper.setMethodsVisibility(Graph.this, receivers, args, Visibility.PRIVATE);
                }
            });

        addSpecialMethod("protected", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    RuntimeHelper.setMethodsVisibility(Graph.this, receivers, args, Visibility.PROTECTED);
                }
            });

        addSpecialMethod("public", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    RuntimeHelper.setMethodsVisibility(Graph.this, receivers, args, Visibility.PUBLIC);
                }
            });

        addSpecialMethod("module_function", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    RuntimeHelper.setMethodsVisibility(Graph.this, receivers, args, Visibility.MODULE_FUNCTION);
                }
            });

        addSpecialMethod("attr", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    if (args != null && args.length > 0) {
                        RuntimeHelper.defineAttrs(Graph.this, receivers, new Vertex[] { args[0] }, true, args.length > 1);
                    }
                }
            });

        addSpecialMethod("attr_reader", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    RuntimeHelper.defineAttrs(Graph.this, receivers, args, true, false);
                }
            });

        addSpecialMethod("attr_writer", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    RuntimeHelper.defineAttrs(Graph.this, receivers, args, false, true);
                }
            });

        addSpecialMethod("attr_accessor", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    RuntimeHelper.defineAttrs(Graph.this, receivers, args, true, true);
                }
            });

        addSpecialMethod("alias_method", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    boolean callNextMethod = true;
                    if (args != null && args.length == 2) {
                        for (IRubyObject receiver : receivers) {
                            if (receiver instanceof RubyModule) {
                                callNextMethod = false;
                                String newName = Vertex.getStringOrSymbol(args[0]);
                                String oldName = Vertex.getStringOrSymbol(args[1]);
                                if (newName != null && oldName != null) {
                                    RubyModule module = (RubyModule) receiver;
                                    DynamicMethod method = module.getMethod(oldName);
                                    if (method instanceof Method)
                                        module.addMethod(newName, new AliasMethod(newName, (Method) method));
                                }
                            }
                        }
                    }
                    result.setCallNextMethod(callNextMethod);
                }
            });
        
        addSpecialMethod("unpack", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    if (args != null && args.length > 0) {
                        String template = Vertex.getString(args[0]);
                        if (template != null) {
                            TypeSet ts = new TypeSet();
                            for (IRubyObject object : receivers) {
                                if (object.isKindOf(runtime.getString())) {
                                    List<Vertex> elements = new ArrayList<Vertex>();
                                    for (char c : template.toCharArray()) {
                                        RubyClass type = null;
                                        switch (c) {
                                        case 'a': case 'A': case 'Z': case 'b':
                                        case 'B': case 'h': case 'H': case 'm':
                                        case 'M': case 'p': case 'P': case 'u':
                                        case 'U': 
                                            type = runtime.getString();
                                            break;
                                        case 'c': case 'C': case 's': case 'S':
                                        case 'i': case 'I': case 'l': case 'L':
                                        case 'q': case 'Q': case 'n': case 'N':
                                        case 'v': case 'V': case 'w': case 'x':
                                        case 'X':
                                            type = runtime.getInteger();
                                            break;
                                        case 'f': case 'd': case 'e': case 'E':
                                        case 'g': case 'G':
                                            type = runtime.getFloat();
                                            break;
                                        }
                                        if (type != null) {
                                            elements.add(createFreeSingleTypeVertex(newInstanceOf(type)));
                                        }
                                    }
                                    ts.add(RuntimeHelper.createArray(Graph.this, elements.toArray(new Vertex[0])));
                                }
                            }
                            if (ts.isEmpty()) {
                                result.setCallNextMethod(true);
                            } else {
                                result.setResultTypeSet(ts);
                            }
                        }
                    }
                }
            });

        addSpecialMethod("proc", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    TypeSet ts = new TypeSet();
                    for (IRubyObject receiver : receivers) {
                        DynamicMethod method = receiver.getMetaClass().searchMethod("proc");
                        if (method != null && method.getModule() == runtime.getKernel()) {
                            if (block instanceof Proc)
                                ts.add((Proc) block);
                            else
                                Logger.debug("proc for no block is not supported yet");
                        }
                    }
                    if (ts.isEmpty())
                        result.setCallNextMethod(true);
                    else
                        result.setResultTypeSet(ts);
                }
            });

        addSpecialMethod("lambda", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    TypeSet ts = new TypeSet();
                    for (IRubyObject receiver : receivers) {
                        DynamicMethod method = receiver.getMetaClass().searchMethod("lambda");
                        if (method != null && method.getModule() == runtime.getKernel()) {
                            if (block instanceof Proc)
                                ts.add((Proc) block);
                            else
                                Logger.debug("lambda for no block is not supported yet");
                        }
                    }
                    if (ts.isEmpty())
                        result.setCallNextMethod(true);
                    else
                        result.setResultTypeSet(ts);
                }
            });

        addSpecialMethod("call", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    TypeSet ts = new TypeSet();
                    Vertex argVertex = null;
                    for (IRubyObject receiver : receivers) {
                        if (receiver instanceof Proc) {
                            if (argVertex == null) {
                                TypeSet argts;
                                if (args == null) {
                                    argts = new TypeSet();
                                    argts.add(RuntimeHelper.createArray(Graph.this, new Vertex[0]));
                                } else if (args.length == 1) {
                                    argts = args[0].getTypeSet();
                                } else {
                                    argts = new TypeSet();
                                    argts.add(RuntimeHelper.createArray(Graph.this, args));
                                }
                                argVertex = createFreeVertex(argts);
                                argVertex.markUnchanged();
                            }
                            YieldVertex vertex = new YieldVertex(null,
                                                                 RuntimeHelper.getFrameTemplate(runtime.getContext().getCurrentFrame()),
                                                                 (Proc) receiver,
                                                                 argVertex,
                                                                 true);
                            RuntimeHelper.yield(Graph.this, vertex);
                            ts.addAll(vertex.getTypeSet());
                        }
                    }
                    if (ts.isEmpty()) {
                        result.setCallNextMethod(true);
                    } else {
                        result.setResultTypeSet(ts);
                    }
                }
            });

        RubyClass objectClass = runtime.getObject();
        RubyClass classClass = runtime.getClassClass();
        RubyClass procClass = runtime.getProc();
        
        objectClass.addMethod("class", new DefaultMethod(objectClass, "class", null, null, Visibility.PUBLIC, null) {
                public Vertex call(Graph graph, Template template, IRubyObject receiver, IRubyObject[] args, Vertex[] argVertices, Block block) {
                    return graph.createFreeSingleTypeVertex(receiver.getMetaClass());
                }
            });

        classClass.addMethod("superclass", new DefaultMethod(classClass, "superclass", null, null, Visibility.PUBLIC, null) {
                public Vertex call(Graph graph, Template template, IRubyObject receiver, IRubyObject[] args, Vertex[] argVertices, Block block) {
                    if (receiver instanceof RubyClass) {
                        return graph.createFreeSingleTypeVertex(((RubyClass) receiver).getSuperClass());
                    } else {
                        return null;
                    }
                }
            });
    }

    public void load(Node newAST) {
        load(newAST, null);
    }

    public void load(Node newAST, Node oldAST) {
        context.pushMain();

        List<Node> partialDiff = null;
        if (oldAST != null && nodeDiff != null) {
            partialDiff = nodeDiff.diff(newAST, oldAST);
            if (partialDiff != null) {
                Logger.debug("partial load: %s", partialDiff.size());
                for (Node dirty : partialDiff) {
                    createVertex(dirty);
                }
            }
        }
        if (partialDiff == null) {
            createVertex(newAST);
        }

        DummyCall entry;
        while ((entry = dummyCallQueue.poll()) != null) {
            entry.force(this);
        }

        context.popMain();
    }

    public Vertex createVertex(Node node) {
        return (Vertex) node.accept(this);
    }

    public Vertex createFreeVertex() {
        return new Vertex();
    }

    public Vertex createFreeVertex(TypeSet typeSet) {
        return new Vertex(null, typeSet);
    }

    public Vertex createFreeSingleTypeVertex(IRubyObject type) {
        Vertex vertex = createFreeVertex();
        vertex.addType(type);
        return vertex;
    }

    public VertexHolder createVertexHolder(Vertex vertex) {
        return new VertexHolder(runtime, vertex);
    }
    
    public VertexHolder createFreeVertexHolder() {
        return new VertexHolder(runtime, createFreeVertex());
    }

    public Vertex createEmptyVertex(Node node) {
        return new Vertex(node);
    }
    
    public Vertex createSingleTypeVertex(Node node, IRubyObject type) {
        Vertex vertex = new Vertex(node, 2);
        vertex.addType(type);
        return vertex;
    }

    public void addEdgeAndPropagate(Vertex src, Vertex dest) {
        src.addEdge(dest);
        propagate(src, dest);
    }

    public void addEdgeAndUpdate(Vertex src, Vertex dest) {
        src.addEdge(dest);
        dest.update(src);
    }

    private Propagation propagation;

    public boolean propagate(Vertex src, Vertex dest) {
        startPropagation();
        boolean result = dest.accept(propagation, src);
        endPropagation();
        return result;
    }

    public boolean propagateEdges(Vertex src) {
        startPropagation();
        boolean result = propagateEdges(propagation, src);
        endPropagation();
        return result;
    }

    public boolean propagateEdges(Propagation propagation, Vertex src) {
        // Edges may change during propagation
        int size = src.getEdges().size();
        for (int i = 0; i < size; i++) {
            Vertex edge = src.getEdges().get(i);
            if (!propagation.visit(edge, src)) {
                return false;
            }
        }
        return true;
    }

    public boolean propagateEdge(Vertex src, Vertex dest) {
        startPropagation();
        boolean result = propagateEdge(propagation, src, dest);
        endPropagation();
        return result;
    }

    public boolean propagateEdge(Propagation propagation, Vertex src, Vertex dest) {
        src.addEdge(dest);
        return dest.accept(propagation, src);
    }

    private void startPropagation() {
        if (propagation == null) {
            propagation = new Propagation(this);
        }
        propagation.retain();
    }

    private void endPropagation() {
        if (propagation.release()) {
            propagation = null;
        }
    }

    public IRubyObject newInstanceOf(RubyClass klass) {
        return runtime.newInstance(klass);
    }

    public Object visitAliasNode(AliasNode node) {
        RubyModule module = context.getFrameModule();
        DynamicMethod method = module.getMethod(node.getOldName());
        if (method instanceof Method)
            module.addMethod(node.getNewName(), new AliasMethod(node.getNewName(), (Method) method));
        return Vertex.EMPTY;
    }
    
    public Object visitAndNode(AndNode node) {
        Vertex vertex = createEmptyVertex(node);
        Vertex firstVertex = createVertex(node.getFirstNode());
        Vertex secondVertex = createVertex(node.getSecondNode());
        addEdgeAndUpdate(secondVertex, vertex);
        return vertex;
    }
    
    public Object visitArgsNode(ArgsNode node) {
        unsupportedNode(node);
        return Vertex.EMPTY;
    }
    
    public Object visitArgsCatNode(ArgsCatNode node) {
        Vertex vertex = createEmptyVertex(node);
        Vertex first = createVertex(node.getFirstNode());
        SplatVertex second = new SplatVertex(node, createVertex(node.getSecondNode()));
        RuntimeHelper.splatValue(this, second);
        for (IRubyObject a : first.getTypeSet()) {
            List<Vertex> elements = new ArrayList<Vertex>();
            if (a instanceof Array) {
                Array array = (Array) a;
                if (array.getElements() != null) {
                    elements.addAll(Arrays.asList(array.getElements()));
                }
            } else {
                elements.add(createFreeSingleTypeVertex(a));
            }
            for (IRubyObject b : second.getTypeSet()) {
                if (b instanceof Array) {
                    Array array = (Array) b;
                    if (array.getElements() != null) {
                        elements.addAll(Arrays.asList(array.getElements()));
                    }
                } else {
                    elements.add(createFreeSingleTypeVertex(a));
                }
            }
            vertex.addType(RuntimeHelper.createArray(this, elements.toArray(new Vertex[0])));
        }
        return vertex;
    }
    
    public Object visitArgsPushNode(ArgsPushNode node) {
        unsupportedNode(node);
        return Vertex.EMPTY;
    }
    
    public Object visitArrayNode(ArrayNode node) {
        return RuntimeHelper.createArrayVertex(this, node, RuntimeHelper.toVertices(this, node));
    }
    
    public Object visitAttrAssignNode(AttrAssignNode node) {
        Vertex receiverVertex = createVertex(node.getReceiverNode());
        Vertex[] argVertices = null;
        if (node.getArgsNode() != null) {
            List<Node> argNodes = node.getArgsNode().childNodes();
            argVertices = new Vertex[argNodes.size()];
            for (int i = 0; i < argVertices.length; i++) {
                argVertices[i] = createVertex(argNodes.get(i));
            }
        }

        CallVertex vertex = new CallVertex(node, receiverVertex, argVertices, null);
        return RuntimeHelper.call(this, vertex);
    }
    
    public Object visitBackRefNode(BackRefNode node) {
        return createSingleTypeVertex(node, newInstanceOf(runtime.getString()));
    }
    
    public Object visitBeginNode(BeginNode node) {
        return createVertex(node.getBodyNode());
    }
    
    public Object visitBignumNode(BignumNode node) {
        return createSingleTypeVertex(node, newInstanceOf(runtime.getBignum()));
    }
    
    public Object visitBlockArgNode(BlockArgNode node) {
        unsupportedNode(node);
        return Vertex.EMPTY;
    }
    
    public Object visitBlockNode(BlockNode node) {
        Object result = null;
        for (Node child : node.childNodes()) {
            result = createVertex(child);
        }
        return result;
    }
    
    public Object visitBlockPassNode(BlockPassNode node) {
        // Never reach here
        unsupportedNode(node);
        return Vertex.EMPTY;
    }
    
    public Object visitBreakNode(BreakNode node) {
        Frame frame = context.getCurrentFrame();
        LoopTag loopTag = RuntimeHelper.getFrameLoopTag(frame);
        if (loopTag != null) {
            Vertex vertex = createVertex(node.getValueNode());
            if (loopTag.getYieldVertex() != null)
                addEdgeAndPropagate(vertex, loopTag.getYieldVertex());
            else
                Logger.debug("no yield vertex");
            return vertex;
        }
        return Vertex.EMPTY;
    }
    
    public Object visitConstDeclNode(ConstDeclNode node) {
        return RuntimeHelper.constDeclaration(this, node);
    }
    
    public Object visitClassVarAsgnNode(ClassVarAsgnNode node) {
        return RuntimeHelper.classVarAssign(this, node);
    }
    
    public Object visitClassVarDeclNode(ClassVarDeclNode node) {
        return RuntimeHelper.classVarDeclaration(this, node);
    }
    
    public Object visitClassVarNode(ClassVarNode node) {
        return RuntimeHelper.classVariable(this, node);
    }
    
    public Object visitCallNode(CallNode node) {
        Vertex receiverVertex = createVertex(node.getReceiverNode());
        Vertex[] argVertices = RuntimeHelper.setupCallArgs(this, node.getArgsNode());
        Block block = RuntimeHelper.setupCallBlock(this, node.getIterNode());
        CallVertex vertex = new CallVertex(node, receiverVertex, argVertices, block);
        return RuntimeHelper.call(this, vertex);
    }
    
    public Object visitCaseNode(CaseNode node) {
        // FIXME eval ===
        Vertex vertex = createEmptyVertex(node);
        if (node.getCaseNode() != null) {
            createVertex(node.getCaseNode());
        }
        ListNode cases = node.getCases();
        if (cases != null) {
            for (int i = 0; i < cases.size(); i++) {
                WhenNode when = (WhenNode) cases.get(i);
                if (when.getBodyNode() != null) {
                    Vertex v = createVertex(when.getBodyNode());
                    addEdgeAndUpdate(v, vertex);
                }
            }
        }
        if (node.getElseNode() != null) {
            Vertex v = createVertex(node.getElseNode());
            addEdgeAndUpdate(v, vertex);
        }
        return vertex;
    }
    
    public Object visitClassNode(ClassNode node) {
        Colon3Node cpath = node.getCPath();
        String name = cpath.getName();
        RubyModule module = RuntimeHelper.getNamespace(this, cpath);
        if (module == null) {
            Logger.error(SourceLocation.of(node), "namespace unresolved: %s", cpath);
            return Vertex.EMPTY;
        }

        RubyClass superClass = null;
        if (node.getSuperNode() != null) {
            Vertex v = createVertex(node.getSuperNode());
            if (v != null) {
                IRubyObject superObj = v.singleType();
                if (superObj instanceof RubyClass) {
                    superClass = (RubyClass) superObj;
                }
            }
            if (superClass == null) {
                Logger.error("superclass not found: %s", cpath.getName());
            }
        }

        RubyModule klass = module.defineOrGetClassUnder(name, superClass, SourceLocation.of(node));

        if (klass != null) {
            context.pushFrame(klass, name, klass, null, Visibility.PUBLIC);
            context.pushScope(new LocalScope(klass));

            RuntimeHelper.classPartialUpdate(this, klass, node.getBodyNode());

            context.popScope();
            context.popFrame();

            RuntimeHelper.setClassTag(klass, node.getBodyNode(), AnnotationHelper.parseAnnotations(node.getCommentList(), node.getPosition().getStartLine()));

            notifyClassEvent(node, klass);
        }
        
        return Vertex.EMPTY;
    }
    
    public Object visitColon2Node(Colon2Node node) {
        RubyModule target = RuntimeHelper.getNamespace(this, node);
        if (target != null) {
            IRubyObject value = target.getConstant(node.getName());
            if (value instanceof VertexHolder) {
                return ((VertexHolder) value).getVertex();
            } else if (value != null) {
                return createSingleTypeVertex(node, value);
            } else {
                return Vertex.EMPTY;
            }
        } else {
            return Vertex.EMPTY;
        }
    }
    
    public Object visitColon3Node(Colon3Node node) {
        IRubyObject value = runtime.getObject().getConstant(node.getName());
        if (value instanceof VertexHolder) {
            return ((VertexHolder) value).getVertex();
        } else if (value != null) {
            return createSingleTypeVertex(node, value);
        } else {
            return Vertex.EMPTY;
        }
    }
    
    public Object visitConstNode(ConstNode node) {
        IRubyObject value = context.getConstant(node.getName());
        if (value instanceof VertexHolder) {
            return ((VertexHolder) value).getVertex();
        } else if (value != null) {
            return createSingleTypeVertex(node, value);
        } else {
            return Vertex.EMPTY;
        }
    }
    
    public Object visitDAsgnNode(DAsgnNode node) {
        return RuntimeHelper.dynamicAssign(this, node);
    }
    
    public Object visitDRegxNode(DRegexpNode node) {
        // FIXME eval
        return createSingleTypeVertex(node, newInstanceOf(runtime.getRegexp()));
    }
    
    public Object visitDStrNode(DStrNode node) {
        // FIXME eval
        return createSingleTypeVertex(node, newInstanceOf(runtime.getString()));
    }
    
    public Object visitDSymbolNode(DSymbolNode node) {
        // FIXME eval
        return createSingleTypeVertex(node, newInstanceOf(runtime.getSymbol()));
    }
    
    public Object visitDVarNode(DVarNode node) {
        VertexHolder holder = (VertexHolder) runtime.getContext().getCurrentScope().getValue(node.getName());
        return holder != null ? holder.getVertex() : Vertex.EMPTY;
    }
    
    public Object visitDXStrNode(DXStrNode node) {
        // FIXME eval `
        return createSingleTypeVertex(node, newInstanceOf(runtime.getString()));
    }
    
    public Object visitDefinedNode(DefinedNode node) {
        return createSingleTypeVertex(node, newInstanceOf(runtime.getString()));
    }
    
    public Object visitDefnNode(DefnNode node) {
        RubyModule cbase = context.getCurrentScope().getModule();
        RubyModule klass = context.getFrameModule();
        String name = node.getName();
        Node bodyNode = node.getBodyNode();
        Node argsNode = node.getArgsNode();
        Visibility visibility = context.getFrameVisibility();
        boolean moduleFunction = visibility == Visibility.MODULE_FUNCTION;
        if (name == "initialize" || name == "initialize_copy" || moduleFunction) {
            visibility = Visibility.PRIVATE;
        }

        Method oldMethod = (Method) klass.getMethod(name);
        Method newMethod = new DefaultMethod(cbase, name, bodyNode, argsNode, visibility, SourceLocation.of(node));
        klass.addMethod(name, newMethod);
        
        if (moduleFunction) {
            Method singletonMethod = new DefaultMethod(cbase, name, bodyNode, argsNode, visibility, SourceLocation.of(node));
            singletonMethod.setVisibility(Visibility.PUBLIC);
            klass.getSingletonClass().addMethod(name, singletonMethod);
        }

        IRubyObject receiver = newInstanceOf((klass instanceof RubyClass) ? (RubyClass) klass : runtime.getObject());
        
        RuntimeHelper.methodPartialUpdate(this, node, newMethod, oldMethod, receiver);
        RuntimeHelper.setMethodTag(newMethod, node, AnnotationHelper.parseAnnotations(node.getCommentList(), node.getPosition().getStartLine()));

        dummyCallQueue.offer(new DummyCall(node, newMethod, oldMethod, receiver));

        notifyDefineEvent(node, newMethod);

        return Vertex.EMPTY;
    }
    
    public Object visitDefsNode(DefsNode node) {
        Vertex receiverVertex = createVertex(node.getReceiverNode());
        if (receiverVertex.isEmpty()) {
            Logger.error(SourceLocation.of(node), "null receiver for defs: %s", node.getName());
            return Vertex.EMPTY;
        }

        RubyModule cbase = context.getCurrentScope().getModule();
        String name = node.getName();
        for (IRubyObject receiver : receiverVertex.getTypeSet()) {
            if (receiver instanceof RubyModule) {
                RubyClass rubyClass = receiver.getSingletonClass();
                Node bodyNode = node.getBodyNode();
                Node argsNode = node.getArgsNode();

                Method oldMethod = (Method) rubyClass.getMethod(name);
                Method newMethod = new DefaultMethod(cbase, name, bodyNode, argsNode, Visibility.PUBLIC, SourceLocation.of(node));
                rubyClass.addMethod(name, newMethod);

                RuntimeHelper.methodPartialUpdate(this, node, newMethod, oldMethod, receiver);
                RuntimeHelper.setMethodTag(newMethod, node, AnnotationHelper.parseAnnotations(node.getCommentList(), node.getPosition().getStartLine()));

                dummyCallQueue.offer(new DummyCall(node, newMethod, oldMethod, receiver));

                notifyDefineEvent(node, newMethod);
            } else
                Logger.warn(SourceLocation.of(node), "cannot define singleton method for individual object: %s", name);
        }

        return Vertex.EMPTY;
    }
    
    public Object visitDotNode(DotNode node) {
        // FIXME propagation
        IRubyObject range = newInstanceOf(runtime.getRange());
        Vertex beginVertex = createVertex(node.getBeginNode());
        Vertex endVertex = createVertex(node.getEndNode());
        TypeVarMap typeVarMap = RuntimeHelper.getTypeVarMap(range);
        if (typeVarMap != null && beginVertex != null && endVertex != null) {
            Vertex t = createFreeVertex();
            t.update(beginVertex);
            t.update(endVertex);
            typeVarMap.put(TypeVariable.valueOf("t"), t);
        }
        return createSingleTypeVertex(node, range);
    }
    
    public Object visitEncodingNode(EncodingNode node) {
        unsupportedNode(node);
        return Vertex.EMPTY;
    }
    
    public Object visitEnsureNode(EnsureNode node) {
        // FIXME
        if (node.getEnsureNode() != null) {
            createVertex(node.getEnsureNode());
        }
        return createVertex(node.getBodyNode());
    }
    
    public Object visitEvStrNode(EvStrNode node) {
        // never reach here
        unsupportedNode(node);
        return Vertex.EMPTY;
    }
    
    public Object visitFCallNode(FCallNode node) {
        Vertex[] argVertices = RuntimeHelper.setupCallArgs(this, node.getArgsNode());
        Block block = RuntimeHelper.setupCallBlock(this, node.getIterNode());
        CallVertex vertex = new CallVertex(node, createFreeSingleTypeVertex(context.getFrameSelf()), argVertices, block);
        vertex.setPrivateVisibility(true);
        return RuntimeHelper.call(this, vertex);
    }
    
    public Object visitFalseNode(FalseNode node) {
        return createSingleTypeVertex(node, runtime.getFalse());
    }
    
    public Object visitFixnumNode(FixnumNode node) {
        return createSingleTypeVertex(node, newInstanceOf(runtime.getFixnum()));
    }
    
    public Object visitFlipNode(FlipNode node) {
        // FIXME check booleans (new vertex)
        return createSingleTypeVertex(node, newInstanceOf(runtime.getBoolean()));
    }
    
    public Object visitFloatNode(FloatNode node) {
        return createSingleTypeVertex(node, newInstanceOf(runtime.getFloat()));
    }
    
    public Object visitForNode(ForNode node) {
        Vertex vertex = createEmptyVertex(node);
        Vertex receiverVertex = createVertex(node.getIterNode());
        Block block = new Proc(runtime, node.getVarNode(), node.getBodyNode(), context.getCurrentFrame(), context.getCurrentScope());
        CallVertex callVertex = new CallVertex(node, "each", receiverVertex, null, block);
        RuntimeHelper.call(this, callVertex);
        addEdgeAndUpdate(vertex, callVertex);
        return vertex;
    }
    
    public Object visitGlobalAsgnNode(GlobalAsgnNode node) {
        return RuntimeHelper.globalAssign(this, node);
    }
    
    public Object visitGlobalVarNode(GlobalVarNode node) {
        return RuntimeHelper.globalVariable(this, node);
    }
    
    public Object visitHashNode(HashNode node) {
        return RuntimeHelper.createHashVertex(this, node, RuntimeHelper.toVertices(this, node.getListNode()));
    }
    
    public Object visitInstAsgnNode(InstAsgnNode node) {
        return RuntimeHelper.instanceAssign(this, node);
    }
    
    public Object visitInstVarNode(InstVarNode node) {
        return RuntimeHelper.instanceVariable(this, node);
    }
    
    public Object visitIfNode(IfNode node) {
        Vertex vertex = createEmptyVertex(node);
        createVertex(node.getCondition());
        Vertex thenBodyVertex = null;
        if (node.getThenBody() != null) {
            thenBodyVertex = createVertex(node.getThenBody());
            addEdgeAndUpdate(thenBodyVertex, vertex);
        }
        Vertex elseBodyVertex = null;
        if (node.getElseBody() != null) {
            elseBodyVertex = createVertex(node.getElseBody());
            addEdgeAndUpdate(elseBodyVertex, vertex);

        }
        return vertex;
    }
    
    public Object visitIterNode(IterNode node) {
        unsupportedNode(node);
        return Vertex.EMPTY;
    }
    
    public Object visitLocalAsgnNode(LocalAsgnNode node) {
        return RuntimeHelper.localAssign(this, node);
    }
    
    public Object visitLocalVarNode(LocalVarNode node) {
        VertexHolder holder = (VertexHolder) runtime.getContext().getCurrentScope().getValue(node.getName());
        return holder != null ? holder.getVertex() : Vertex.EMPTY;
    }
    
    public Object visitMultipleAsgnNode(MultipleAsgnNode node) {
        return RuntimeHelper.multipleAssign(this, node);
    }
    
    public Object visitMultipleAsgnNode(MultipleAsgn19Node node) {
        unsupportedNode(node);
        return Vertex.EMPTY;
    }
    
    public Object visitMatch2Node(Match2Node node) {
        // FIXME speedup node
        return createSingleTypeVertex(node, newInstanceOf(runtime.getMatchData()));
    }
    
    public Object visitMatch3Node(Match3Node node) {
        // FIXME speedup node
        return createSingleTypeVertex(node, newInstanceOf(runtime.getMatchData()));
    }
    
    public Object visitMatchNode(MatchNode node) {
        // FIXME speedup node
        return createSingleTypeVertex(node, newInstanceOf(runtime.getMatchData()));
    }
    
    public Object visitModuleNode(ModuleNode node) {
        Colon3Node cpath = node.getCPath();
        String name = cpath.getName();
        RubyModule enclosingModule = RuntimeHelper.getNamespace(this, cpath);
        if (enclosingModule == null) {
            Logger.error(SourceLocation.of(node), "namespace unresolved: %s", name);
            return Vertex.EMPTY;
        }

        RubyModule module = enclosingModule.defineOrGetModuleUnder(name, SourceLocation.of(node));

        if (module != null) {
            context.pushFrame(module, name, module, null, Visibility.PUBLIC);
            context.pushScope(new LocalScope(module));

            RuntimeHelper.classPartialUpdate(this, module, node.getBodyNode());

            context.popScope();
            context.popFrame();
        
            RuntimeHelper.setClassTag(module, node.getBodyNode(), AnnotationHelper.parseAnnotations(node.getCommentList(), node.getPosition().getStartLine()));
        
            notifyModuleEvent(node, module);
        }
        
        return Vertex.EMPTY;
    }
    
    public Object visitNewlineNode(NewlineNode node) {
        return createVertex(node.getNextNode());
    }

    public Object visitNextNode(NextNode node) {
        Frame frame = context.getCurrentFrame();
        LoopTag loopTag = RuntimeHelper.getFrameLoopTag(frame);
        if (loopTag != null && loopTag.getYieldVertex() != null) {
            Vertex vertex = createVertex(node.getValueNode());
            if (loopTag.getYieldVertex() != null)
                addEdgeAndPropagate(vertex, loopTag.getYieldVertex());
            else
                Logger.debug("no yield vertex");
            return vertex;
        }
        return Vertex.EMPTY;
    }
    
    public Object visitNilNode(NilNode node) {
        return createSingleTypeVertex(node, runtime.getNil());
    }
    
    public Object visitNotNode(NotNode node) {
        createVertex(node.getConditionNode());
        return createSingleTypeVertex(node, newInstanceOf(runtime.getBoolean()));
    }
    
    public Object visitNthRefNode(NthRefNode node) {
        // FIXME
        return createSingleTypeVertex(node, newInstanceOf(runtime.getString()));
    }
    
    public Object visitOpElementAsgnNode(OpElementAsgnNode node) {
        String operator = node.getOperatorName();
        Vertex receiverVertex = createVertex(node.getReceiverNode());
        Vertex[] argVertices = null;
        if (node.getArgsNode() != null) {
            List<Node> argNodes = node.getArgsNode().childNodes();
            argVertices = new Vertex[argNodes.size()];
            for (int i = 0; i < argVertices.length; i++) {
                argVertices[i] = createVertex(argNodes.get(i));
            }
        }
        Vertex src = createVertex(node.getValueNode());
        Vertex value;

        if (operator.equals("||") || operator.equals("&&")) {
            // do nothing
            value = src;
        } else {
            CallVertex getter = new CallVertex(node.getReceiverNode(), "[]", receiverVertex, argVertices, null);
            CallVertex op = new CallVertex(node, operator, RuntimeHelper.call(this, getter), new Vertex[] {src}, null);
            value = RuntimeHelper.call(this, op);
        }
       
        Vertex[] expandedArgs = new Vertex[argVertices.length + 1];
        System.arraycopy(argVertices, 0, expandedArgs, 0, argVertices.length);
        expandedArgs[expandedArgs.length - 1] = value;
        CallVertex setter = new CallVertex(node, "[]=", receiverVertex, expandedArgs, null);
        return RuntimeHelper.call(this, setter);
    }
    
    public Object visitOpAsgnNode(OpAsgnNode node) {
        String operator = node.getOperatorName();
        String var = node.getVariableName();
        String varAsgn = node.getVariableNameAsgn();
        Vertex receiverVertex = createVertex(node.getReceiverNode());
        Vertex src = createVertex(node.getValueNode());
        Vertex value;
        if (operator.equals("||") || operator.equals("&&")) {
            // do nothing
            value = src;
        } else {
            CallVertex getter = new CallVertex(node.getValueNode(), var, receiverVertex, null, null);
            CallVertex op = new CallVertex(node.getValueNode(), operator, RuntimeHelper.call(this, getter), new Vertex[] {src}, null);
            value = RuntimeHelper.call(this, op);
        }
        
        CallVertex setter = new CallVertex(node.getValueNode(), varAsgn, receiverVertex, new Vertex[] {value}, null);
        return RuntimeHelper.call(this, setter);
    }
    
    public Object visitOpAsgnAndNode(OpAsgnAndNode node) {
        Vertex vertex = createEmptyVertex(node);
        Vertex firstVertex = createVertex(node.getFirstNode());
        Vertex secondVertex = createVertex(node.getSecondNode());
        addEdgeAndUpdate(firstVertex, vertex);
        addEdgeAndUpdate(secondVertex, vertex);
        return vertex;
    }
    
    public Object visitOpAsgnOrNode(OpAsgnOrNode node) {
        Vertex vertex = createEmptyVertex(node);
        Vertex firstVertex = createVertex(node.getFirstNode());
        Vertex secondVertex = createVertex(node.getSecondNode());
        addEdgeAndUpdate(firstVertex, vertex);
        addEdgeAndUpdate(secondVertex, vertex);
        return vertex;
    }
    
    public Object visitOrNode(OrNode node) {
        Vertex vertex = createEmptyVertex(node);
        Vertex firstVertex = createVertex(node.getFirstNode());
        Vertex secondVertex = createVertex(node.getSecondNode());
        addEdgeAndUpdate(firstVertex, vertex);
        addEdgeAndUpdate(secondVertex, vertex);
        return vertex;
    }
    
    public Object visitPreExeNode(PreExeNode node) {
        unsupportedNode(node);
        return Vertex.EMPTY;
    }
    
    public Object visitPostExeNode(PostExeNode node) {
        unsupportedNode(node);
        return Vertex.EMPTY;
    }
    
    public Object visitRedoNode(RedoNode node) {
        return Vertex.EMPTY;
    }
    
    public Object visitRegexpNode(RegexpNode node) {
        return createSingleTypeVertex(node, newInstanceOf(runtime.getRegexp()));
    }
    
    public Object visitRescueBodyNode(RescueBodyNode node) {
        if (node.getBodyNode() != null) {
            return createVertex(node.getBodyNode());
        }
        return Vertex.EMPTY;
    }
    
    public Object visitRescueNode(RescueNode node) {
        Vertex result = Vertex.EMPTY;
        if (node.getBodyNode() != null) {
            result = createVertex(node.getBodyNode());
        }
        if (node.getRescueNode() != null) {
            createVertex(node.getRescueNode());
        }
        if (node.getElseNode() != null) {
            result = createVertex(node.getElseNode());
        }
        return result;
    }
    
    public Object visitRestArgNode(RestArgNode node) {
        unsupportedNode(node);
        return Vertex.EMPTY;
    }
    
    public Object visitRetryNode(RetryNode node) {
        return Vertex.EMPTY;
    }
    
    public Object visitReturnNode(ReturnNode node) {
        Frame frame = context.getCurrentFrame();
        Template template = RuntimeHelper.getFrameTemplate(frame);
        if (template != null) {
            Vertex vertex = createVertex(node.getValueNode());
            addEdgeAndPropagate(vertex, template.getReturnVertex());
            return vertex;
        }
        return Vertex.EMPTY;
    }
    
    public Object visitRootNode(RootNode node) {
        return createVertex(node.getBodyNode());
    }
    
    public Object visitSClassNode(SClassNode node) {
        Vertex receiverVertex = createVertex(node.getReceiverNode());

        if (receiverVertex != null) {
            for (IRubyObject object : receiverVertex.getTypeSet()) {
                RubyClass klass = object.getMetaClass();
                if (klass.isSingleton()) {
                    MetaClass metaClass = (MetaClass) klass;
                    if (metaClass.getAttached() instanceof RubyModule) {
                        context.pushFrame(klass, "sclass", klass, null, Visibility.PUBLIC);
                        context.pushScope(new LocalScope((RubyModule) metaClass.getAttached()));

                        if (node.getBodyNode() != null) {
                            createVertex(node.getBodyNode());
                        }

                        context.popScope();
                        context.popFrame();
                    }
                    
                    return Vertex.EMPTY;
                } else {
                    Logger.warn(SourceLocation.of(node), "singleton class of objects is not supported.");
                }
            }
        }

        return Vertex.EMPTY;
    }
    
    public Object visitSelfNode(SelfNode node) {
        IRubyObject self = context.getFrameSelf();
        if (self == null) {
            Logger.error(SourceLocation.of(node), "self unresolved");
            return Vertex.EMPTY;
        } else
            return createSingleTypeVertex(node, self);
    }
    
    public Object visitSplatNode(SplatNode node) {
        Vertex valueVertex = createVertex(node.getValue());
        SplatVertex vertex = new SplatVertex(node, valueVertex);
        RuntimeHelper.splatValue(this, vertex);
        return vertex;
    }
    
    public Object visitStrNode(StrNode node) {
        return createSingleTypeVertex(node, newInstanceOf(runtime.getString()));
    }
    
    public Object visitSuperNode(SuperNode node) {
        Vertex receiverVertex = createFreeSingleTypeVertex(context.getFrameSelf());
        Vertex[] argVertices = null;
        if (node.getArgsNode() != null) {
            List<Node> argNodes = node.getArgsNode().childNodes();
            argVertices = new Vertex[argNodes.size()];
            for (int i = 0; i < argVertices.length; i++) {
                argVertices[i] = createVertex(argNodes.get(i));
            }
        }
        
        Block block = RuntimeHelper.setupCallBlock(this, node.getIterNode());
        CallVertex vertex = new CallVertex(node, context.getCurrentFrame().getName(), receiverVertex, argVertices, block);
        vertex.setPrivateVisibility(true);
        return RuntimeHelper.callSuper(this, vertex);
    }
    
    public Object visitSValueNode(SValueNode node) {
        SValueVertex vertex = new SValueVertex(node, createVertex(node.getValue()));
        RuntimeHelper.aValueSplat(this, vertex);
        return vertex;
    }
    
    public Object visitSymbolNode(SymbolNode node) {
        return createSingleTypeVertex(node, newInstanceOf(runtime.getSymbol()));
    }
    
    public Object visitToAryNode(ToAryNode node) {
        Vertex valueVertex = createVertex(node.getValue());
        ToAryVertex vertex = new ToAryVertex(node, valueVertex);
        RuntimeHelper.toAryValue(this, vertex);
        return vertex;
    }
    
    public Object visitTrueNode(TrueNode node) {
        return createSingleTypeVertex(node, runtime.getTrue());
    }
    
    public Object visitUndefNode(UndefNode node) {
        Logger.warn("undef is not supported yet.");
        return Vertex.EMPTY;
    }
    
    public Object visitUntilNode(UntilNode node) {
        Vertex vertex = createEmptyVertex(node);
        createVertex(node.getConditionNode());
        RuntimeHelper.pushLoopFrame(context, vertex, null);
        createVertex(node.getBodyNode());
        RuntimeHelper.popLoopFrame(context);
        return vertex;
    }
    
    public Object visitVAliasNode(VAliasNode node) {
        RuntimeHelper.aliasGlobalVaraibles(this, node.getNewName(), node.getOldName());
        return Vertex.EMPTY;
    }
    
    public Object visitVCallNode(VCallNode node) {
        CallVertex vertex = new CallVertex(node, createFreeSingleTypeVertex(context.getFrameSelf()), null, null);
        vertex.setPrivateVisibility(true);
        return RuntimeHelper.call(this, vertex);
    }
    
    public Object visitWhenNode(WhenNode node) {
        // never reach here
        unsupportedNode(node);
        return Vertex.EMPTY;
    }
    
    public Object visitWhileNode(WhileNode node) {
        Vertex vertex = createEmptyVertex(node);
        createVertex(node.getConditionNode());
        RuntimeHelper.pushLoopFrame(context, vertex, null);
        createVertex(node.getBodyNode());
        RuntimeHelper.popLoopFrame(context);
        return vertex;
    }
    
    public Object visitXStrNode(XStrNode node) {
        // FIXME eval `
        return createSingleTypeVertex(node, newInstanceOf(runtime.getString()));
    }
    
    public Object visitYieldNode(YieldNode node) {
        Vertex argsVertex = null;
        if (node.getArgsNode() != null) {
            argsVertex = createVertex(node.getArgsNode());
        }
        YieldVertex vertex = new YieldVertex(node, RuntimeHelper.getFrameTemplate(context.getCurrentFrame()), context.getFrameBlock(), argsVertex, node.getExpandArguments());
        return RuntimeHelper.yield(this, vertex);
    }
    
    public Object visitZArrayNode(ZArrayNode node) {
        return RuntimeHelper.createArrayVertex(this, node, null);
    }
    
    public Object visitZSuperNode(ZSuperNode node) {
        Template template = RuntimeHelper.getFrameTemplate(context.getCurrentFrame());
        if (template != null) {
            // FIXME more efficient way
            TemplateAttribute attr = template.getAttribute();
            IRubyObject[] args = attr.getArgs();

            Vertex receiverVertex = createFreeSingleTypeVertex(context.getFrameSelf());
            Vertex[] argVertices = new Vertex[args.length];
            for (int i = 0; i < args.length; i++) {
                argVertices[i] = createFreeSingleTypeVertex(args[i]);
            }
            
            Block block = RuntimeHelper.setupCallBlock(this, node.getIterNode());
            CallVertex vertex = new CallVertex(node, context.getCurrentFrame().getName(), receiverVertex, argVertices, block);
            vertex.setPrivateVisibility(true);
            return RuntimeHelper.callSuper(this, vertex);
        }
        return Vertex.EMPTY;
    }

    public boolean propagateVertex(Propagation propagation, Vertex dest, Vertex src) {
        // copy first to make sure variable assign can be revisited
        dest.update(src);

        if (propagation.checkVisited(dest)) {
            return true;
        }

        return propagateEdges(propagation, dest);
    }

    public boolean propagateCallVertex(Propagation propagation, CallVertex dest, Vertex src) {
        if (propagation.checkVisited(dest)) { return true; }

        RuntimeHelper.call(this, dest);
        return propagateEdges(propagation, dest);
    }

    public boolean propagateMultipleAsgnVertex(Propagation propagation, MultipleAsgnVertex dest, Vertex src) {
        if (propagation.checkVisited(dest)) { return true; }

        if (dest.isChanged()) {
            dest.markUnchanged();

            for (IRubyObject object : RuntimeHelper.arrayValue(propagation.getGraph(), dest.getValueVertex())) {
                RuntimeHelper.multipleAssign(this, (MultipleAsgnNode) dest.getNode(), object);
            }
        }

        // FIXME no need to propagate?
        return true;
    }

    public boolean propagateSplatVertex(Propagation propagation, SplatVertex dest, Vertex src) {
        if (propagation.checkVisited(dest)) { return true; }

        if (dest.isChanged()) {
            dest.markUnchanged();
            RuntimeHelper.splatValue(this, dest);
        }
        return propagateEdges(propagation, dest);
    }

    public boolean propagateToAryVertex(Propagation propagation, ToAryVertex dest, Vertex src) {
        if (propagation.checkVisited(dest)) { return true; }

        if (dest.isChanged()) {
            dest.markUnchanged();
            RuntimeHelper.toAryValue(this, dest);
        }
        return propagateEdges(propagation, dest);
    }

    public boolean propagateSValueVertex(Propagation propagation, SValueVertex dest, Vertex src) {
        if (propagation.checkVisited(dest)) { return true; }

        if (dest.isChanged()) {
            dest.markUnchanged();
            RuntimeHelper.aValueSplat(this, dest);
        }
        return propagateEdges(propagation, dest);
    }

    public boolean propagateYieldVertex(Propagation propagation, YieldVertex dest, Vertex src) {
        if (propagation.checkVisited(dest)) { return true; }

        RuntimeHelper.yield(this, dest);
        return propagateEdges(propagation, dest);
    }

    public boolean propagatePassThroughVertex(Propagation propagation, PassThroughVertex dest, Vertex src) {
        if (propagation.checkVisited(dest)) { return true; }

        return propagateEdges(propagation, dest);
    }
    
    public boolean propagateTypeVarVertex(Propagation propagation, TypeVarVertex dest, Vertex src) {
        if (propagation.checkVisited(dest)) { return true; }

        dest.getObject().setModified(true);
        dest.update(src);
        return propagateEdges(propagation, dest);
    }

    private void unsupportedNode(Node node) {
        Logger.fixme("unsupported node: %s", node);
    }
}
