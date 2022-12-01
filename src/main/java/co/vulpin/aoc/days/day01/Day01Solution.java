package co.vulpin.aoc.days.day01;

import co.vulpin.aoc.days.AbstractDayParallelSolution;

import java.util.*;

public class Day01Solution extends AbstractDayParallelSolution<List<Integer>> {

    @Override
    protected Object solvePart1(List<Integer> input) {
        return input.stream()
            .mapToInt(i -> i)
            .max()
            .orElseThrow();
    }

    @Override
    protected Object solvePart2(List<Integer> input) {
        return input.stream()
            .sorted(Comparator.reverseOrder())
            .limit(3)
            .mapToInt(i -> i)
            .sum();
    }

    @Override
    protected List<Integer> parseInput(String rawInput) {
        return Arrays.stream(rawInput.split("\n\n"))
            .map(s -> s.lines()
                .mapToInt(Integer::parseInt)
                .sum()
            )
            .toList();
    }

}
