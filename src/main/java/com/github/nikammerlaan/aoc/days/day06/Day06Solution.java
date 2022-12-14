package com.github.nikammerlaan.aoc.days.day06;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;

import java.util.HashMap;

public class Day06Solution extends AbstractDaySolution<int[]> {

    @Override
    protected Object solvePart1(int[] input) {
        return findFirst(input, 4);
    }

    @Override
    protected Object solvePart2(int[] input) {
        return findFirst(input, 14);
    }

    private int findFirst(int[] input, int n) {
        for(int i = 0; i < input.length; i++) {
            if(input[i] == n) {
                return i + 1;
            }
        }
        throw new IllegalStateException();
    }

    @Override
    protected int[] parseInput(String rawInput) {
        var lastSeenMap = new HashMap<Character, Integer>();
        var output = new int[rawInput.length()];

        for(int i = 0; i < rawInput.length(); i++) {
            var c = rawInput.charAt(i);
            var lengthSinceLast = i - (lastSeenMap.getOrDefault(c, -1) + 1);
            lastSeenMap.put(c, i);
            var previousLength = i > 0 ? output[i - 1] : 0;
            var length = Math.min(lengthSinceLast, previousLength) + 1;

            output[i] = length;
        }

        return output;
    }

}
