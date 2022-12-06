package co.vulpin.aoc.days.day06;

import co.vulpin.aoc.days.AbstractDaySolution;

public class Day06Solution extends AbstractDaySolution<String> {

    @Override
    protected Object solvePart1(String input) {
        return solve(input, 4);
    }

    @Override
    protected Object solvePart2(String input) {
        return solve(input, 14);
    }

    private int solve(String input, int length) {
        for(int i = 0; i < input.length(); i++) {
            var sub = input.substring(i, i + length);
            var uniqueLetters = sub.chars()
                .distinct()
                .count();
            if(uniqueLetters == length) {
                return i + length;
            }
        }
        throw new IllegalStateException();
    }

    @Override
    protected String parseInput(String rawInput) {
        return rawInput;
    }

}
