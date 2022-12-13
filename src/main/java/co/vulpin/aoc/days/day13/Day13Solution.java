package co.vulpin.aoc.days.day13;

import co.vulpin.aoc.days.AbstractDaySolution;

import java.util.*;
import java.util.stream.*;


public class Day13Solution extends AbstractDaySolution<List<Day13Solution.Pair>> {

    @Override
    protected Object solvePart1(List<Pair> input) {
        return IntStream.range(0, input.size())
            .filter(i -> input.get(i).isCorrect())
            .map(i -> i + 1)
            .sum();
    }

    @Override
    protected Object solvePart2(List<Pair> input) {
        var packets = input.stream()
            .flatMap(pair -> Stream.of(pair.left(), pair.right()))
            .collect(Collectors.toCollection(ArrayList::new));

        var dividerPacket1 = new ListPacket(List.of(new IntPacket(2)));
        packets.add(dividerPacket1);

        var dividerPacket2 = new ListPacket(List.of(new IntPacket(6)));
        packets.add(dividerPacket2);

        packets.sort(Comparator.naturalOrder());

        return (packets.indexOf(dividerPacket1) + 1) * (packets.indexOf(dividerPacket2) + 1);
    }

    @Override
    protected List<Pair> parseInput(String rawInput) {
        return Arrays.stream(rawInput.split("\n\n"))
            .map(this::parsePair)
            .toList();
    }

    private Pair parsePair(String raw) {
        var lines = raw.split("\n");
        return new Pair(
            parsePacket(lines[0]),
            parsePacket(lines[1])
        );
    }

    private Packet parsePacket(String raw) {
        if(raw.startsWith("[")) {
            if(raw.length() == 2) {
                return new ListPacket(List.of());
            }

            var children = new ArrayList<Packet>();
            var depth = 0;
            var lastSplit = 1;
            for(int i = 1; i < raw.length() - 1; i++) {
                var c = raw.charAt(i);
                switch(c) {
                    case '[' -> depth++;
                    case ']' -> depth--;
                }
                if(c == ',' && depth == 0) {
                    var substring = raw.substring(lastSplit, i);
                    lastSplit = i + 1;
                    children.add(parsePacket(substring));
                }
            }
            var substring = raw.substring(lastSplit, raw.length() - 1);
            children.add(parsePacket(substring));
            return new ListPacket(children);
        } else {
            var value = Integer.parseInt(raw);
            return new IntPacket(value);
        }
    }

    record Pair(Packet left, Packet right) {

        boolean isCorrect() {
            return left.compareTo(right) < 0;
        }

        @Override
        public String toString() {
            return left.toString() + "\n" + right.toString();
        }

    }

    sealed interface Packet extends Comparable<Packet> permits IntPacket, ListPacket {}

    record ListPacket(List<Packet> children) implements Packet {

        @Override
        public String toString() {
            return children.toString();
        }

        @Override
        public int compareTo(Packet other) {
            var a = this;
            return switch(other) {
                case ListPacket b -> IntStream.range(0, Math.min(a.children().size(), b.children().size()))
                    .map(i -> {
                        var aChild = a.children().get(i);
                        var bChild = b.children().get(i);
                        return aChild.compareTo(bChild);
                    })
                    .filter(i -> i != 0)
                    .findFirst()
                    .orElse(Integer.compare(a.children().size(), b.children().size()));
                case IntPacket b -> {
                    var fakeListPacket = new ListPacket(List.of(b));
                    yield a.compareTo(fakeListPacket);
                }
            };
        }

    }

    record IntPacket(int value) implements Packet {

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @Override
        public int compareTo(Packet o) {
            return switch(o) {
                case IntPacket b -> Integer.compare(value, b.value());
                case ListPacket b -> {
                    var fakeListPacket = new ListPacket(List.of(this));
                    yield fakeListPacket.compareTo(b);
                }
            };
        }

    }

}
