package com.github.nikammerlaan.aoc.days.day21.monkeys;

public class VariableMonkey extends Monkey {

    public VariableMonkey() {
        super(true);
    }

    @Override
    public long getValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean hasVariable() {
        return true;
    }

    @Override
    public String toString() {
        return "x";
    }

}
