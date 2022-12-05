package co.vulpin.aoc.days.day04;

import co.vulpin.aoc.days.AbstractDaySolution;
import co.vulpin.aoc.misc.Range;

import java.util.List;

public class Day04Solution extends AbstractDaySolution<List<Day04Solution.Pair>> {

    @Override
    protected Object solvePart1(List<Pair> input) {
        return input.stream()
            .filter(pair -> pair.a().contains(pair.b()) || pair.b().contains(pair.a()))
            .count();
    }

    @Override
    protected Object solvePart2(List<Pair> input) {
        return input.stream()
            .filter(pair -> pair.a().intersects(pair.b()))
            .count();
    }

    @Override
    protected List<Pair> parseInput(String rawInput) {
        return rawInput.lines()
            .map(this::parseLine)
            .toList();
    }

    private Pair parseLine(String s) {
        var parts = s.split(",");
        var a = parseRange(parts[0]);
        var b = parseRange(parts[1]);
        return new Pair(a, b);
    }

    private Range parseRange(String s) {
        var parts = s.split("-");
        return new Range(
            Integer.parseInt(parts[0]),
            Integer.parseInt(parts[1])
        );
    }

    record Pair(Range a, Range b) {}

}
