package org.cx4a.rsense.ruby;

import java.util.Stack;

public class Context {
    private Ruby runtime;
    private Stack<Scope> scopes;
    private Frame frame;

    public Context(Ruby runtime) {
        this.runtime = runtime;
        this.scopes = new Stack<Scope>();
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

    public Frame pushFrame(RubyModule cbase, String name, IRubyObject self, Block block, Visibility visibility) {
        this.frame = new Frame(cbase, name, self, block, visibility, this.frame);
        return this.frame;
    }

    public Frame popFrame() {
        Frame frame = this.frame;
        this.frame = frame.getPrevious();
        return frame;
    }

    public void pushMain() {
        pushScope(new LocalScope(runtime.getObject()));
        pushFrame(runtime.getObject(), "main", runtime.getTopSelf(), null, Visibility.PRIVATE);
    }

    public void popMain() {
        popFrame();
        popScope();
    }

    public Scope getCurrentScope() {
        return scopes.peek();
    }

    public IRubyObject getConstant(String name) {
        return getCurrentScope().getModule().getConstant(name);
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
}
