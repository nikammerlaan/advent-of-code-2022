package com.github.nikammerlaan.aoc.days.day03;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.SetUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Day03Solution extends AbstractDaySolution<List<Day03Solution.Rucksack>> {

    @Override
    protected Object solvePart1(List<Rucksack> input) {
        return input.stream()
            .map(rucksack -> List.of(rucksack.a(), rucksack.b()))
            .map(this::getCommonChar)
            .mapToInt(this::getPriority)
            .sum();
    }

    @Override
    protected Object solvePart2(List<Rucksack> input) {
        return ListUtils.partition(input, 3).stream()
            .map(rucksacks -> rucksacks.stream()
                .map(Rucksack::total)
                .toList()
            )
            .map(this::getCommonChar)
            .mapToInt(this::getPriority)
            .sum();
    }

    private int getPriority(char c) {
        if(Character.isUpperCase(c)) {
            return c - 'A' + 26 + 1;
        } else {
            return c - 'a' + 1;
        }
    }

    private char getCommonChar(Collection<Set<Character>> sets) {
        return sets.stream()
            .reduce(SetUtils::intersection)
            .orElseThrow()
            .iterator()
            .next();
    }

    @Override
    protected List<Rucksack> parseInput(String rawInput) {
        return rawInput.lines()
            .map(s -> {
                var aString = s.substring(0, s.length() / 2);
                var bString = s.substring(s.length() / 2);
                var a = aString.chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.toSet());
                var b = bString.chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.toSet());
                return new Rucksack(a, b);
            })
            .toList();
    }
    
    record Rucksack(Set<Character> a, Set<Character> b, Set<Character> total) {

        public Rucksack(Set<Character> a, Set<Character> b) {
            this(a, b, SetUtils.union(a, b));
        }

    }

}
