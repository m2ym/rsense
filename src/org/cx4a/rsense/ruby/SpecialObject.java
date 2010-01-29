package org.cx4a.rsense.ruby;

public class SpecialObject extends RubyObject {
    private String displayValue;

    public SpecialObject(Ruby runtime, RubyClass metaClass, String displayValue) {
        super(runtime, metaClass);
        this.displayValue = displayValue;
    }

    @Override
    public String toString() {
        return displayValue;
    }
}
