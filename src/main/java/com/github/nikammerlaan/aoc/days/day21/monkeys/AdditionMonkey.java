package com.github.nikammerlaan.aoc.days.day21.monkeys;

public final class AdditionMonkey extends FunctionMonkey {

    public AdditionMonkey(Monkey monkeyA, Monkey monkeyB) {
        super(monkeyA, monkeyB);
    }

    @Override
    protected long getValue(long a, long b) {
        return a + b;
    }

}
