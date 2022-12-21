package com.github.nikammerlaan.aoc.days.day21.monkeys;

public abstract sealed class FunctionMonkey extends Monkey
    permits AdditionMonkey, SubtractionMonkey, MultiplicationMonkey, DivisionMonkey {

    private final Monkey monkeyA;
    private final Monkey monkeyB;
    private final boolean hasVariable;

    public FunctionMonkey(Monkey monkeyA, Monkey monkeyB) {
        super(monkeyA.hasVariable() || monkeyB.hasVariable());

        this.monkeyA = monkeyA;
        this.monkeyB = monkeyB;
        this.hasVariable = monkeyA.hasVariable() || monkeyB.hasVariable();
    }

    @Override
    public long getValue() {
        return getValue(
            monkeyA.getValue(),
            monkeyB.getValue()
        );
    }

    protected abstract long getValue(long a, long b);

    public Monkey getMonkeyA() {
        return monkeyA;
    }

    public Monkey getMonkeyB() {
        return monkeyB;
    }

    @Override
    protected boolean hasVariable() {
        return hasVariable;
    }

}
