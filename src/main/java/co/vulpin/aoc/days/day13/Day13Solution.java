package co.vulpin.aoc.days.day13;

import co.vulpin.aoc.days.AbstractDaySolution;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.*;


public class Day13Solution extends AbstractDaySolution<List<Day13Solution.Pair>> {

    private final ObjectMapper objectMapper;

    public Day13Solution() {
        this.objectMapper = new ObjectMapper();
    }

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
        try {
            return objectMapper.readValue(raw, Packet.class);
        } catch(JsonProcessingException e) {
            throw new RuntimeException(e);
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


}
