package com.github.nikammerlaan.aoc.days.day25;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;

import java.util.List;

public class Day25Solution extends AbstractDaySolution<List<Long>> {

    @Override
    protected Object solvePart1(List<Long> input) {
        var sum = input.stream()
            .mapToLong(i -> i)
            .sum();
        return decimalToSnafu(sum);
    }

    @Override
    protected Object solvePart2(List<Long> input) {
        return null;
    }

    @Override
    protected List<Long> parseInput(String rawInput) {
        return rawInput.lines()
            .map(this::snafuToDecimal)
            .toList();
    }

    private String decimalToSnafu(long num) {
        var result = new StringBuilder();

        while(num > 0) {
            var digit = num % 5;
            num /= 5;
            if(digit <= 2) {
                result.append(digit);
            } else if(digit == 3){
                result.append('=');
                num++;
            } else if(digit == 4) {
                result.append('-');
                num++;
            }
        }

        return result.reverse().toString();
    }

    private long snafuToDecimal(String raw) {
        long result = 0;

        for(var digit : raw.toCharArray()) {
            result *= 5;
            result += switch(digit) {
                case '0' ->  0;
                case '1' ->  1;
                case '2' ->  2;
                case '-' -> -1;
                case '=' -> -2;
                default -> throw new IllegalStateException("Unknown char " + digit);
            };
        }

        return result;
    }

}
