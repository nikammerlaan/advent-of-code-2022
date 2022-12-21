package com.github.nikammerlaan.aoc.days.day21.monkeys;

public class LiteralMonkey extends Monkey {

    private final long value;

    public LiteralMonkey(long value) {
        super(false);

        this.value = value;
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
