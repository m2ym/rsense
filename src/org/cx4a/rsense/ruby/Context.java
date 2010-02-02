package org.cx4a.rsense.ruby;

import java.util.Stack;

public class Context {
    private Ruby runtime;
    private Stack<Scope> scopes;
    private Frame frame;

    public Context(Ruby runtime) {
        this.runtime = runtime;
        this.scopes = new Stack<Scope>();
        pushScope(new LocalScope(runtime.getObject()));
        pushFrame(runtime.getObject(), runtime.getTopSelf(), null, Visibility.PRIVATE);
    }

    public void pushScope(Scope scope) {
        scopes.push(scope);
    }

    public void popScope() {
        scopes.pop();
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public Frame pushFrame(RubyModule cbase, IRubyObject self, Block block, Visibility visibility) {
        this.frame = new Frame(cbase, self, block, visibility, this.frame);
        return this.frame;
    }

    public Frame popFrame() {
        Frame frame = this.frame;
        this.frame = frame.getPrevious();
        return frame;
    }

    public Scope getCurrentScope() {
        return scopes.peek();
    }

    public Frame getCurrentFrame() {
        return frame;
    }

    public IRubyObject getFrameSelf() {
        return getCurrentFrame().getSelf();
    }

    public RubyModule getFrameModule() {
        return getCurrentFrame().getModule();
    }

    public Block getFrameBlock() {
        return getCurrentFrame().getBlock();
    }

    public Visibility getFrameVisibility() {
        return getCurrentFrame().getVisibility();
    }

    public IRubyObject getConstant(String name) {
        return getFrameModule().getConstant(name);
    }
}
