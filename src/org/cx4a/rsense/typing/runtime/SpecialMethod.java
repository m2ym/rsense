package org.cx4a.rsense.typing.runtime;

import java.util.Collection;
import java.util.List;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.typing.TypeSet;
import org.cx4a.rsense.typing.vertex.Vertex;

public abstract class SpecialMethod {
    public static class Result {
        private Result prev;
        private TypeSet accumulator;
        private TypeSet resultTypeSet;
        private boolean noReturn;
        private boolean callNextMethod;
        private boolean nextMethodChange;
        private String nextMethodName;
        private TypeSet nextMethodReceivers;
        private Block nextMethodBlock;
        private boolean nextMethodNoReturn;
        private boolean neverCallAgain;
        private boolean privateVisibility;

        public Result(Result prev, TypeSet accumulator) {
            this.prev = prev;
            this.accumulator = accumulator;
        }

        public Result setResultTypeSet(TypeSet resultTypeSet) {
            this.resultTypeSet = resultTypeSet;
            return this;
        }

        public Result setCallNextMethod(boolean flag) {
            this.callNextMethod = flag;
            return this;
        }

        public Result setNextMethodChange(boolean flag) {
            this.nextMethodChange = flag;
            return this;
        }

        public Result setNextMethodName(String name) {
            this.nextMethodName = name;
            return this;
        }

        public Result setNextMethodReceivers(TypeSet receivers) {
            this.nextMethodReceivers = receivers;
            return this;
        }

        public Result setNextMethodBlock(Block block) {
            this.nextMethodBlock = block;
            return this;
        }

        public Result setNextMethodNoReturn(boolean flag) {
            this.nextMethodNoReturn = flag;
            return this;
        }

        public Result setNeverCallAgain(boolean neverCallAgain) {
            this.neverCallAgain = neverCallAgain;
            return this;
        }

        public Result setPrivateVisibility(boolean privateVisibility) {
            this.privateVisibility = privateVisibility;
            return this;
        }

        public Result getPrevious() {
            return prev;
        }

        public TypeSet getAccumulator() {
            return accumulator;
        }

        public TypeSet getResultTypeSet() {
            return resultTypeSet;
        }

        public boolean isCallNextMethod() {
            return callNextMethod;
        }

        public boolean isNextMethodChange() {
            return nextMethodChange;
        }

        public String getNextMethodName() {
            return nextMethodName;
        }

        public TypeSet getNextMethodReceivers() {
            return nextMethodReceivers;
        }

        public Block getNextMethodBlock() {
            return nextMethodBlock;
        }

        public boolean isNextMethodNoReturn() {
            return nextMethodNoReturn;
        }

        public boolean isNeverCallAgain() {
            return neverCallAgain;
        }

        public boolean hasPrivateVisibility() {
            return privateVisibility;
        }
    }

    public abstract void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block block, Result result);
}
