package com.github.nikammerlaan.aoc.days.day01;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;

import java.util.*;

public class Day01Solution extends AbstractDaySolution<List<Integer>> {

    @Override
    protected Object solvePart1(List<Integer> input) {
        return firstNSum(input, 1);
    }

    @Override
    protected Object solvePart2(List<Integer> input) {
        return firstNSum(input, 3);
    }

    private int firstNSum(List<Integer> nums, int n) {
        return nums.stream()
            .mapToInt(i -> i)
            .limit(n)
            .sum();
    }

    @Override
    protected List<Integer> parseInput(String rawInput) {
        return Arrays.stream(rawInput.split("\n\n"))
            .map(s -> s.lines()
                .mapToInt(Integer::parseInt)
                .sum()
            )
            .sorted(Comparator.reverseOrder())
            .toList();
    }

}
