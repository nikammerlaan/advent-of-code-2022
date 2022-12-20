package com.github.nikammerlaan.aoc.days.day20;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;

import java.util.*;
import java.util.stream.IntStream;

public class Day20Solution extends AbstractDaySolution<List<Integer>> {

    @Override
    protected Object solvePart1(List<Integer> input) {
        return solve(input, 1, 1);
    }

    @Override
    protected Object solvePart2(List<Integer> input) {
        return solve(input, 811589153, 10);
    }

    private long solve(List<Integer> input, long decryptionKey, int rounds) {
        var elements = input.stream()
            .map(i -> i * decryptionKey)
            .map(Element::new)
            .toList();

        var list = new ArrayList<>(elements);
        for(int i = 0; i < rounds; i++) {
            for(var element : elements) {
                var index = list.indexOf(element);

                list.remove(index);

                long newIndex = index;
                newIndex += element.value();
                newIndex %= list.size();
                newIndex += list.size();
                newIndex %= list.size();

                list.add((int) newIndex, element);
            }
        }

        var zeroElement = elements.stream()
            .filter(element -> element.value() == 0L)
            .findFirst()
            .orElseThrow();
        var zeroIndex = list.indexOf(zeroElement);
        return IntStream.of(1_000, 2_000, 3_000)
            .map(i -> (zeroIndex + i) % list.size())
            .mapToObj(list::get)
            .mapToLong(Element::value)
            .sum();
    }

    @Override
    protected List<Integer> parseInput(String rawInput) {
        return rawInput.lines()
            .map(Integer::parseInt)
            .toList();
    }

    record Element(long value) {

        // This explicitly overrides record's default equals/hashCode implementation with one that uses identity

        @Override
        public boolean equals(Object o) {
            return this == o;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this);
        }

    }

}
