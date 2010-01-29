package org.cx4a.rsense.ruby;

public class Frame {
    private RubyModule cbase;
    private IRubyObject self;
    private Block block;
    private Visibility visibility;
    private Frame prev;
    private Object tag;

    public Frame(RubyModule cbase, IRubyObject self, Block block, Visibility visibility, Frame prev) {
        this.cbase = cbase;
        this.self = self;
        this.block = block;
        this.visibility = visibility;
        this.prev = prev;
    }

    public RubyModule getModule() {
        return cbase;
    }

    public void setModule(RubyModule cbase) {
        this.cbase = cbase;
    }

    public IRubyObject getSelf() {
        return self;
    }

    public Block getBlock() {
        return block;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public Frame getPrevious() {
        return prev;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
