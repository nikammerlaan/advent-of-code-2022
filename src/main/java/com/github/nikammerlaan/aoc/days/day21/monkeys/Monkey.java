package com.github.nikammerlaan.aoc.days.day21.monkeys;

public abstract class Monkey {

    private final boolean hasVariable;

    public Monkey(boolean hasVariable) {
        this.hasVariable = hasVariable;
    }

    public abstract long getValue();

    protected boolean hasVariable() {
        return hasVariable;
    }

}
