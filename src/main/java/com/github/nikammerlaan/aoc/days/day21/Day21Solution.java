package com.github.nikammerlaan.aoc.days.day21;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;
import com.github.nikammerlaan.aoc.days.day21.monkeys.*;
import com.github.nikammerlaan.aoc.misc.MathUtils;

import java.util.*;

public class Day21Solution extends AbstractDaySolution<Map<String, String>> {

    @Override
    protected Object solvePart1(Map<String, String> rawFunctions) {
        var monkey = parseMonkey(rawFunctions, "root");
        return monkey.getValue();
    }

    @Override
    protected Object solvePart2(Map<String, String> rawFunctions) {
        var copy = new HashMap<>(rawFunctions);
        copy.put("root", copy.get("root").replace("+", "="));
        copy.put("humn", "x");
        var monkey = parseMonkey(copy, "root");
        return monkey.getValue();
    }

    @Override
    protected Map<String, String> parseInput(String rawInput) {
        var map = new HashMap<String, String>();

        for(var line : rawInput.split("\n")) {
            var parts = line.split(": ");
            map.put(parts[0], parts[1]);
        }

        return map;
    }

    private Monkey parseMonkey(Map<String, String> rawFunctions, String functionName) {
        var raw = rawFunctions.get(functionName);

        if(raw.equals("x")) {
            return new VariableMonkey();
        }

        if(MathUtils.isLong(raw)) {
            return new LiteralMonkey(Long.parseLong(raw));
        } else {
            var parts = raw.split(" ");
            var a = parseMonkey(rawFunctions, parts[0]);
            var b = parseMonkey(rawFunctions, parts[2]);
            return switch(parts[1]) {
                case "+" -> new AdditionMonkey(a, b);
                case "-" -> new SubtractionMonkey(a, b);
                case "*" -> new MultiplicationMonkey(a, b);
                case "/" -> new DivisionMonkey(a, b);
                case "=" -> new EquationMonkey(a, b);
                default -> throw new IllegalArgumentException(parts[1]);
            };
        }
    }

}
