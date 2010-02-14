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
import org.cx4a.rsense.typing.runtime.InstanceFactory;
import org.cx4a.rsense.typing.runtime.VertexHolder;
import org.cx4a.rsense.typing.runtime.Array;
import org.cx4a.rsense.typing.runtime.Hash;
import org.cx4a.rsense.typing.runtime.Method;
import org.cx4a.rsense.typing.runtime.SpecialMethod;
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
import org.cx4a.rsense.typing.vertex.YieldVertex;
import org.cx4a.rsense.typing.annotation.TypeAnnotation;
import org.cx4a.rsense.typing.annotation.TypeVariable;
import org.cx4a.rsense.typing.annotation.ClassType;
import org.cx4a.rsense.typing.annotation.MethodType;

public class Graph implements NodeVisitor {
    public static final Vertex NULL_VERTEX = new Vertex();

    protected static class DelayedMethodPartialUpdate {
        private MethodDefNode node;
        private Method newMethod;
        private DynamicMethod oldMethod;
        private IRubyObject receiver;

        public DelayedMethodPartialUpdate(MethodDefNode node, Method newMethod, DynamicMethod oldMethod, IRubyObject receiver) {
            this.node = node;
            this.newMethod = newMethod;
            this.oldMethod = oldMethod;
            this.receiver = receiver;
        }

        public void force(Graph graph) {
            RuntimeHelper.methodPartialUpdate(graph, node, newMethod, oldMethod, receiver);
        }
    }

    protected Ruby runtime;
    protected Context context;
    protected InstanceFactory instanceFactory;
    protected Map<String, SpecialMethod> specialMethods;
    protected NodeDiff nodeDiff;
    protected Queue<DelayedMethodPartialUpdate> methodPartialUpdateQueue = new LinkedList<DelayedMethodPartialUpdate>();
    
    public Graph(Ruby runtime) {
        this.runtime = runtime;
        this.context = runtime.getContext();
        this.instanceFactory = new InstanceFactory(runtime);
        this.specialMethods = new HashMap<String, SpecialMethod>();
        initSpecialMethods();
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

    private void initSpecialMethods() {
        addSpecialMethod("new", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    TypeSet accumulator = result.getAccumulator();
                    if (result.getPrevious() == null) {
                        TypeSet resultTypeSet = accumulator = new TypeSet();
                        TypeSet newReceivers = new TypeSet();
                        for (IRubyObject receiver : receivers) {
                            if (receiver instanceof RubyClass) {
                                if (((RubyClass) receiver).getMetaClass().searchMethod("new") == null) {
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
                        .setNextMethodBlock(null)
                        .setNextMethodNoReturn(true);
                }
            });

        addSpecialMethod("include", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    boolean included = false;
                    if (args != null) {
                        for (Vertex arg : args) {
                            for (IRubyObject receiver : receivers) {
                                if (receiver instanceof RubyModule) {
                                    RubyModule module = (RubyModule) receiver;
                                    for (IRubyObject target : arg.getTypeSet()) {
                                        if (target instanceof RubyModule) {
                                            module.includeModule((RubyModule) target);
                                            included = true;
                                        } else {
                                            Logger.warn("Invalid include module");
                                        }
                                    }
                                } else {
                                    Logger.warn("Invalid include module");
                                }
                            }
                        }
                    }
                    result.setCallNextMethod(!included);
                }
            });

        addSpecialMethod("[]", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result) {
                    TypeSet typeSet = new TypeSet();
                    if (args != null && args.length > 0) {
                        for (IRubyObject receiver : receivers) {
                            if (receiver instanceof Hash) {
                                Hash hash = (Hash) receiver;
                                Object key = Hash.getRealKey(args[0].getNode());
                                if (!hash.isModified() && key != null) {
                                    Vertex v = hash.get(key);
                                    if (v != null) {
                                        typeSet.addAll(v.getTypeSet());
                                    }
                                }
                            } else if (receiver instanceof Array) {
                                Array array = (Array) receiver;
                                Integer n = Vertex.getFixnum(args[0]);
                                if (!array.isModified() && n != null) {
                                    Vertex v = array.getElement(n);
                                    if (v != null) {
                                        typeSet.addAll(v.getTypeSet());
                                    }
                                }
                            }
                        }
                    }

                    if (typeSet.isEmpty()) {
                        result.setCallNextMethod(true);
                    } else {
                        result.setResultTypeSet(typeSet);
                    }
                }
            });

        addSpecialMethod("class", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block blcck, Result result) {
                    TypeSet ts = new TypeSet();
                    for (IRubyObject receiver : receivers) {
                        ts.add(receiver.getMetaClass());
                    }
                    result.setResultTypeSet(ts);
                }
            });

        addSpecialMethod("superclass", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block blcck, Result result) {
                    TypeSet ts = new TypeSet();
                    for (IRubyObject receiver : receivers) {
                        if (receiver instanceof RubyClass) {
                            ts.add(((RubyClass) receiver).getSuperClass());
                        }
                    }
                    result.setResultTypeSet(ts);
                }
            });

        addSpecialMethod("private", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block blcck, Result result) {
                    RuntimeHelper.setMethodsVisibility(Graph.this, receivers, args, Visibility.PRIVATE);
                }
            });

        addSpecialMethod("protected", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block blcck, Result result) {
                    RuntimeHelper.setMethodsVisibility(Graph.this, receivers, args, Visibility.PROTECTED);
                }
            });

        addSpecialMethod("public", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block blcck, Result result) {
                    RuntimeHelper.setMethodsVisibility(Graph.this, receivers, args, Visibility.PUBLIC);
                }
            });

        addSpecialMethod("attr", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block blcck, Result result) {
                    if (args != null && args.length > 0) {
                        RuntimeHelper.defineAttrs(Graph.this, receivers, new Vertex[] { args[0] }, true, args.length > 1);
                    }
                }
            });

        addSpecialMethod("attr_reader", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block blcck, Result result) {
                    RuntimeHelper.defineAttrs(Graph.this, receivers, args, true, false);
                }
            });

        addSpecialMethod("attr_writer", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block blcck, Result result) {
                    RuntimeHelper.defineAttrs(Graph.this, receivers, args, false, true);
                }
            });

        addSpecialMethod("attr_accessor", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block blcck, Result result) {
                    RuntimeHelper.defineAttrs(Graph.this, receivers, args, true, true);
                }
            });

        addSpecialMethod("module_function", new SpecialMethod() {
                public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block blcck, Result result) {
                    if (args != null && args.length > 0) {
                        for (IRubyObject receiver : receivers) {
                            if (receiver.isKindOf(runtime.getModule())) {
                                RubyModule module = (RubyModule) receiver;
                                for (Vertex arg : args) {
                                    String name = Vertex.getStringOrSymbol(arg);
                                    if (name != null) {
                                        DynamicMethod method = module.getMethod(name);
                                        if (method != null) {
                                            module.getSingletonClass().addMethod(name, method);
                                        }
                                    }
                                }
                            }
                        }
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
                for (Node dirty : partialDiff) {
                    createVertex(dirty);
                }
            }
        }
        if (partialDiff == null) {
            createVertex(newAST);
        }

        DelayedMethodPartialUpdate entry;
        while ((entry = methodPartialUpdateQueue.poll()) != null) {
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
        Vertex vertex = new Vertex(node);
        vertex.addType(type);
        return vertex;
    }

    public void addEdgeAndPropagate(Vertex src, Vertex dest) {
        src.addEdge(dest);
        propagate(src, dest);
    }

    public void addEdgeAndCopyTypeSet(Vertex src, Vertex dest) {
        src.addEdge(dest);
        dest.copyTypeSet(src);
    }

    private Propagation propagation;

    public boolean propagate(Vertex src, Vertex dest) {
        startPropagation();
        boolean result = dest.accept(propagation, src);
        endPropagation();
        return result;
    }

    public boolean propagateEdges(Vertex dest) {
        startPropagation();
        boolean result = propagateEdges(propagation, dest);
        endPropagation();
        return result;
    }

    public boolean propagateEdges(Propagation propagation, Vertex dest) {
        int size = dest.getEdges().size();
        for (int i = 0; i < size; i++) {
            Vertex edge = dest.getEdges().get(i);
            if (!edge.accept(propagation, dest)) {
                return false;
            }
        }
        return true;
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
        return instanceFactory.newInstance(klass);
    }

    public Object visitAliasNode(AliasNode node) {
        RubyModule module = context.getCurrentScope().getModule();
        DynamicMethod method = module.getMethod(node.getOldName());
        if (method != null) {
            module.addMethod(node.getNewName(), method);
        }
        return NULL_VERTEX;
    }
    
    public Object visitAndNode(AndNode node) {
        Vertex vertex = createEmptyVertex(node);
        Vertex firstVertex = createVertex(node.getFirstNode());
        Vertex secondVertex = createVertex(node.getSecondNode());
        addEdgeAndCopyTypeSet(secondVertex, vertex);
        return vertex;
    }
    
    public Object visitArgsNode(ArgsNode node) {
        throw new UnsupportedOperationException();
    }
    
    public Object visitArgsCatNode(ArgsCatNode node) {
        // FIXME
        Logger.fixme("argscat node is not implemented yet.");
        return NULL_VERTEX;
        //throw new UnsupportedOperationException();
    }
    
    public Object visitArgsPushNode(ArgsPushNode node) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }
    
    public Object visitBlockNode(BlockNode node) {
        Object result = null;
        for (Node child : node.childNodes()) {
            result = createVertex(child);
        }
        return result;
    }
    
    public Object visitBlockPassNode(BlockPassNode node) {
        throw new UnsupportedOperationException();
    }
    
    public Object visitBreakNode(BreakNode node) {
        Frame frame = context.getCurrentFrame();
        LoopTag loopTag = RuntimeHelper.getFrameLoopTag(frame);
        if (loopTag != null) {
            Vertex vertex = createVertex(node.getValueNode());
            addEdgeAndPropagate(vertex, loopTag.getReturnVertex());
            return vertex;
        }
        return NULL_VERTEX;
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
        Vertex[] argVertices = null;
        if (node.getArgsNode() != null) {
            List<Node> argNodes = node.getArgsNode().childNodes();
            argVertices = new Vertex[argNodes.size()];
            for (int i = 0; i < argVertices.length; i++) {
                argVertices[i] = createVertex(argNodes.get(i));
            }
        }
        
        Block block = null;
        if (node.getIterNode() instanceof IterNode) {
            IterNode iterNode = (IterNode) node.getIterNode();
            DynamicScope scope = new DynamicScope(context.getCurrentScope().getModule(), context.getCurrentScope());
            block = new Block(iterNode.getVarNode(), iterNode.getBodyNode(), context.getCurrentFrame(), scope);
        } else if (node.getIterNode() != null) {
            // FIXME
            Logger.debug("unknnown iternode: %s", node.getIterNode());
        }
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
                    addEdgeAndCopyTypeSet(v, vertex);
                }
            }
        }
        if (node.getElseNode() != null) {
            Vertex v = createVertex(node.getElseNode());
            addEdgeAndCopyTypeSet(v, vertex);
        }
        return vertex;
    }
    
    public Object visitClassNode(ClassNode node) {
        Colon3Node cpath = node.getCPath();
        String name = cpath.getName();
        RubyModule module = RuntimeHelper.getNamespace(this, cpath);
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
                Logger.error("Invalid superclass: %s", cpath.getName());
            }
        }

        RubyClass klass = module.defineOrGetClassUnder(name, superClass);

        context.pushFrame(klass, name, klass, null, Visibility.PUBLIC);
        context.pushScope(new LocalScope(klass));

        RuntimeHelper.classPartialUpdate(this, klass, node.getBodyNode());

        context.popScope();
        context.popFrame();

        RuntimeHelper.setClassTag(klass, node.getBodyNode(), AnnotationHelper.parseAnnotations(node.getCommentList(), node.getPosition().getStartLine()));
        
        return NULL_VERTEX;
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
                return NULL_VERTEX;
            }
        } else {
            return NULL_VERTEX;
        }
    }
    
    public Object visitColon3Node(Colon3Node node) {
        IRubyObject value = runtime.getObject().getConstant(node.getName());
        if (value instanceof VertexHolder) {
            return ((VertexHolder) value).getVertex();
        } else if (value != null) {
            return createSingleTypeVertex(node, value);
        } else {
            return NULL_VERTEX;
        }
    }
    
    public Object visitConstNode(ConstNode node) {
        IRubyObject value = context.getConstant(node.getName());
        if (value instanceof VertexHolder) {
            return ((VertexHolder) value).getVertex();
        } else if (value != null) {
            return createSingleTypeVertex(node, value);
        } else {
            return NULL_VERTEX;
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
        return holder != null ? holder.getVertex() : NULL_VERTEX;
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
        String name = node.getName();
        Node bodyNode = node.getBodyNode();
        Node argsNode = node.getArgsNode();
        Visibility visibility = context.getFrameVisibility();
        if (name == "initialize" || name == "initialize_copy" || visibility == Visibility.MODULE_FUNCTION) {
            visibility = Visibility.PRIVATE;
        }

        DynamicMethod oldMethod = cbase.getMethod(name);
        Method newMethod = new Method(cbase, name, bodyNode, argsNode, visibility, node.getPosition());
        cbase.addMethod(name, newMethod);

        IRubyObject receiver = newInstanceOf((cbase instanceof RubyClass) ? (RubyClass) cbase : runtime.getObject());
        
        methodPartialUpdateQueue.offer(new DelayedMethodPartialUpdate(node, newMethod, oldMethod, receiver));
        RuntimeHelper.setMethodTag(newMethod, node, AnnotationHelper.parseAnnotations(node.getCommentList(), node.getPosition().getStartLine()));

        return NULL_VERTEX;
    }
    
    public Object visitDefsNode(DefsNode node) {
        Vertex receiverVertex = createVertex(node.getReceiverNode());
        if (receiverVertex.isEmpty()) {
            Logger.error("Null receiver for defs");
            return NULL_VERTEX;
        }

        String name = node.getName();
        for (IRubyObject receiver : receiverVertex.getTypeSet()) {
            RubyClass rubyClass = receiver.getSingletonClass();
            Node bodyNode = node.getBodyNode();
            Node argsNode = node.getArgsNode();

            DynamicMethod oldMethod = rubyClass.getMethod(name);
            Method newMethod = new Method(context.getCurrentScope().getModule(), name, bodyNode, argsNode, Visibility.PUBLIC, node.getPosition());
            rubyClass.addMethod(name, newMethod);

            methodPartialUpdateQueue.offer(new DelayedMethodPartialUpdate(node, newMethod, oldMethod, receiver));
            RuntimeHelper.setMethodTag(newMethod, node, AnnotationHelper.parseAnnotations(node.getCommentList(), node.getPosition().getStartLine()));
        }

        return NULL_VERTEX;
    }
    
    public Object visitDotNode(DotNode node) {
        // FIXME propagation
/*
        IRubyObject range = newInstanceOf(runtime.getRange());
        Vertex beginVertex = createVertex(node.getBeginNode());
        Vertex endVertex = createVertex(node.getEndNode());
        TypeVarMap typeVarMap = RuntimeHelper.getTypeVarMap(range);
        if (typeVarMap != null && beginVertex != null && endVertex != null) {
            VertexHolder holder = createFreeVertexHolder();
            holder.getVertex().copyTypeSet(beginVertex);
            holder.getVertex().copyTypeSet(endVertex);
            typeVarMap.put(new TypeVariable("t"), holder);
        }
        return createSingleTypeVertex(node, range);
*/
        return null;
    }
    
    public Object visitEncodingNode(EncodingNode node) {
        throw new UnsupportedOperationException();
    }
    
    public Object visitEnsureNode(EnsureNode node) {
        // FIXME
        //throw new UnsupportedOperationException();
        return NULL_VERTEX;
    }
    
    public Object visitEvStrNode(EvStrNode node) {
        // never reach here
        throw new UnsupportedOperationException();
    }
    
    public Object visitFCallNode(FCallNode node) {
        Vertex[] argVertices = null;
        if (node.getArgsNode() != null) {
            List<Node> argNodes = node.getArgsNode().childNodes();
            argVertices = new Vertex[argNodes.size()];
            for (int i = 0; i < argVertices.length; i++) {
                argVertices[i] = createVertex(argNodes.get(i));
            }
        }
        
        Block block = null;
        if (node.getIterNode() != null) {
            switch (node.getIterNode().getNodeType()) {
            case ITERNODE: {
                IterNode iterNode = (IterNode) node.getIterNode();
                block = new Block(iterNode.getVarNode(), iterNode.getBodyNode(), context.getCurrentFrame(), context.getCurrentScope());
                break;
            }
            case BLOCKPASSNODE:
                block = context.getFrameBlock();
                break;
            }
        }
        CallVertex vertex = new CallVertex(node, createFreeSingleTypeVertex(context.getFrameSelf()), argVertices, block);
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
        Block block = new Block(node.getVarNode(), node.getBodyNode(), context.getCurrentFrame(), context.getCurrentScope());
        CallVertex callVertex = new CallVertex(node, "each", receiverVertex, null, block);
        RuntimeHelper.call(this, callVertex);
        addEdgeAndPropagate(callVertex, vertex);
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
            addEdgeAndCopyTypeSet(thenBodyVertex, vertex);
        }
        Vertex elseBodyVertex = null;
        if (node.getElseBody() != null) {
            elseBodyVertex = createVertex(node.getElseBody());
            addEdgeAndCopyTypeSet(elseBodyVertex, vertex);

        }
        return vertex;
    }
    
    public Object visitIterNode(IterNode node) {
        throw new UnsupportedOperationException();
    }
    
    public Object visitLocalAsgnNode(LocalAsgnNode node) {
        return RuntimeHelper.localAssign(this, node);
    }
    
    public Object visitLocalVarNode(LocalVarNode node) {
        VertexHolder holder = (VertexHolder) runtime.getContext().getCurrentScope().getValue(node.getName());
        return holder != null ? holder.getVertex() : NULL_VERTEX;
    }
    
    public Object visitMultipleAsgnNode(MultipleAsgnNode node) {
        return RuntimeHelper.multipleAssign(this, node);
    }
    
    public Object visitMultipleAsgnNode(MultipleAsgn19Node node) {
        throw new UnsupportedOperationException();
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
        RubyModule module = enclosingModule.defineOrGetModuleUnder(name);

        context.pushFrame(module, name, module, null, Visibility.PUBLIC);
        context.pushScope(new LocalScope(module));

        RuntimeHelper.classPartialUpdate(this, module, node.getBodyNode());

        context.popScope();
        context.popFrame();
        
        RuntimeHelper.setClassTag(module, node.getBodyNode(), AnnotationHelper.parseAnnotations(node.getCommentList(), node.getPosition().getStartLine()));

        return NULL_VERTEX;
    }
    
    public Object visitNewlineNode(NewlineNode node) {
        return createVertex(node.getNextNode());
    }

    public Object visitNextNode(NextNode node) {
        Frame frame = context.getCurrentFrame();
        LoopTag loopTag = RuntimeHelper.getFrameLoopTag(frame);
        if (loopTag != null && loopTag.getYieldVertex() != null) {
            Vertex vertex = createVertex(node.getValueNode());
            addEdgeAndPropagate(vertex, loopTag.getYieldVertex());
            return vertex;
        }
        return NULL_VERTEX;
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
        RuntimeHelper.call(this, setter);
        return src;
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
        RuntimeHelper.call(this, setter);
        return src;
    }
    
    public Object visitOpAsgnAndNode(OpAsgnAndNode node) {
        Vertex vertex = createEmptyVertex(node);
        Vertex firstVertex = createVertex(node.getFirstNode());
        Vertex secondVertex = createVertex(node.getSecondNode());
        addEdgeAndCopyTypeSet(firstVertex, vertex);
        addEdgeAndCopyTypeSet(secondVertex, vertex);
        return vertex;
    }
    
    public Object visitOpAsgnOrNode(OpAsgnOrNode node) {
        Vertex vertex = createEmptyVertex(node);
        Vertex firstVertex = createVertex(node.getFirstNode());
        Vertex secondVertex = createVertex(node.getSecondNode());
        addEdgeAndCopyTypeSet(firstVertex, vertex);
        addEdgeAndCopyTypeSet(secondVertex, vertex);
        return vertex;
    }
    
    public Object visitOrNode(OrNode node) {
        Vertex vertex = createEmptyVertex(node);
        Vertex firstVertex = createVertex(node.getFirstNode());
        Vertex secondVertex = createVertex(node.getSecondNode());
        addEdgeAndCopyTypeSet(firstVertex, vertex);
        addEdgeAndCopyTypeSet(secondVertex, vertex);
        return vertex;
    }
    
    public Object visitPreExeNode(PreExeNode node) {
        throw new UnsupportedOperationException();
    }
    
    public Object visitPostExeNode(PostExeNode node) {
        throw new UnsupportedOperationException();
    }
    
    public Object visitRedoNode(RedoNode node) {
        return NULL_VERTEX;
    }
    
    public Object visitRegexpNode(RegexpNode node) {
        return createSingleTypeVertex(node, newInstanceOf(runtime.getRegexp()));
    }
    
    public Object visitRescueBodyNode(RescueBodyNode node) {
        // FIXME
        //throw new UnsupportedOperationException();
        return NULL_VERTEX;
    }
    
    public Object visitRescueNode(RescueNode node) {
        // FIXME
        //throw new UnsupportedOperationException();
        return NULL_VERTEX;
    }
    
    public Object visitRestArgNode(RestArgNode node) {
        throw new UnsupportedOperationException();
    }
    
    public Object visitRetryNode(RetryNode node) {
        return NULL_VERTEX;
    }
    
    public Object visitReturnNode(ReturnNode node) {
        Frame frame = context.getCurrentFrame();
        Template template = RuntimeHelper.getFrameTemplate(frame);
        if (template != null) {
            Vertex vertex = createVertex(node.getValueNode());
            addEdgeAndPropagate(vertex, template.getReturnVertex());
            return vertex;
        }
        return NULL_VERTEX;
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
                    context.pushFrame(klass, "sclass", klass, null, Visibility.PUBLIC);
                    context.pushScope(new LocalScope((RubyModule) ((MetaClass) klass).getAttached()));

                    if (node.getBodyNode() != null) {
                        createVertex(node.getBodyNode());
                    }

                    context.popScope();
                    context.popFrame();
        
                    return NULL_VERTEX;
                } else {
                    Logger.warn(SourceLocation.of(node), "singleton class of objects is not supported.");
                }
            }
        }

        return NULL_VERTEX;
    }
    
    public Object visitSelfNode(SelfNode node) {
        return createSingleTypeVertex(node, context.getFrameSelf());
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
        
        Block block = null;
        if (node.getIterNode() != null) {
            IterNode iterNode = (IterNode) node.getIterNode();
            DynamicScope scope = new DynamicScope(context.getFrameModule(), context.getCurrentScope());
            block = new Block(iterNode.getVarNode(), iterNode.getBodyNode(), context.getCurrentFrame(), scope);
        } else {
            block = context.getFrameBlock();
        }
        CallVertex vertex = new CallVertex(node, context.getCurrentFrame().getName(), receiverVertex, argVertices, block);
        return RuntimeHelper.callSuper(this, vertex);
    }
    
    public Object visitSValueNode(SValueNode node) {
        throw new UnsupportedOperationException();
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
        //throw new UnsupportedOperationException();
        Logger.warn("undef is not supported yet.");
        return NULL_VERTEX;
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
        return NULL_VERTEX;
    }
    
    public Object visitVCallNode(VCallNode node) {
        CallVertex vertex = new CallVertex(node, createFreeSingleTypeVertex(context.getFrameSelf()), null, null);
        return RuntimeHelper.call(this, vertex);
    }
    
    public Object visitWhenNode(WhenNode node) {
        // never reach here
        throw new UnsupportedOperationException();
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
        YieldVertex vertex = new YieldVertex(node, context.getFrameBlock(), argsVertex);
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
            
            Block block = null;
            if (node.getIterNode() != null) {
                IterNode iterNode = (IterNode) node.getIterNode();
                DynamicScope scope = new DynamicScope(context.getFrameModule(), context.getCurrentScope());
                block = new Block(iterNode.getVarNode(), iterNode.getBodyNode(), context.getCurrentFrame(), scope);
            } else {
                block = context.getFrameBlock();
            }
            
            CallVertex vertex = new CallVertex(node, context.getCurrentFrame().getName(), receiverVertex, argVertices, block);
            return RuntimeHelper.callSuper(this, vertex);
        }
        return NULL_VERTEX;
    }

    public boolean propagateVertex(Propagation propagation, Vertex dest, Vertex src) {
        if (propagation.checkVisited(dest)
            // FIXME polymorphic objects update
            /*|| dest.getTypeSet().containsAll(src.getTypeSet())*/) {
            return true;
        }
        
        dest.copyTypeSet(src);
        return propagateEdges(propagation, dest);
    }

    public boolean propagateCallVertex(Propagation propagation, CallVertex dest, Vertex src) {
        if (propagation.checkVisited(dest)) { return true; }

        RuntimeHelper.call(this, dest);
        return propagateEdges(propagation, dest);
    }

    public boolean propagateMultipleAsgnVertex(Propagation propagation, MultipleAsgnVertex dest, Vertex src) {
        if (propagation.checkVisited(dest)) { return true; }

        Vertex valueVertex = dest.getValueVertex();
        for (IRubyObject object : RuntimeHelper.arrayValue(propagation.getGraph(), valueVertex)) {
            if (object instanceof Array) {
                RuntimeHelper.multipleAssign(this, (MultipleAsgnNode) dest.getNode(), (Array) object);
            }
        }
        return true;
    }

    public boolean propagateSplatVertex(Propagation propagation, SplatVertex dest, Vertex src) {
        if (propagation.checkVisited(dest)) { return true; }

        RuntimeHelper.splatValue(this, dest);
        return propagateEdges(propagation, dest);
    }

    public boolean propagateToAryVertex(Propagation propagation, ToAryVertex dest, Vertex src) {
        if (propagation.checkVisited(dest)) { return true; }

        RuntimeHelper.toAryValue(this, dest);
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
        dest.copyTypeSet(src);
        return propagateEdges(propagation, dest);
    }

}
