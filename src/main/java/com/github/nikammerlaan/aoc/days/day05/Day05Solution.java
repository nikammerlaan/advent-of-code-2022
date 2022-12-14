package com.github.nikammerlaan.aoc.days.day05;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day05Solution extends AbstractDaySolution<Day05Solution.Input> {

    @Override
    protected Object solvePart1(Input input) {
        var board = input.board().stream()
            .map(LinkedList::new)
            .toList();

        for(var instruction : input.instructions()) {
            var from = board.get(instruction.from() - 1);
            var to = board.get(instruction.to() - 1);

            for(int i = 0; i < instruction.amount(); i++) {
                to.push(from.pop());
            }
        }

        return getTopCrates(board);
    }

    @Override
    protected Object solvePart2(Input input) {
        var board = input.board().stream()
            .map(LinkedList::new)
            .toList();

        for(var instruction : input.instructions()) {
            var from = board.get(instruction.from() - 1);
            var to = board.get(instruction.to() - 1);

            for(int i = 0; i < instruction.amount(); i++) {
                to.push(from.remove(instruction.amount() - i - 1));
            }
        }

        return getTopCrates(board);
    }

    private String getTopCrates(List<LinkedList<Character>> board) {
        return board.stream()
            .map(Deque::getFirst)
            .map(String::valueOf)
            .collect(Collectors.joining());
    }

    @Override
    protected Input parseInput(String rawInput) {
        var parts = rawInput.split("\n\n");

        var board = parseBoard(parts[0]);
        var instructions = parseInstructions(parts[1]);

        return new Input(board, instructions);
    }

    private List<List<Character>> parseBoard(String raw) {
        var lines = raw.split("\n");

        var columnCount = lines[lines.length - 1].length() / 4 + 1;
        return IntStream.range(0, columnCount)
            .mapToObj(i -> {
                var items = new LinkedList<Character>();

                int charColumn = i * 4 + 1;
                for(int j = 1; j < lines.length; j++) {
                    var line = lines[lines.length - j - 1];

                    var c = charColumn < line.length() ? line.charAt(charColumn) : ' ';
                    if(c != ' ') {
                        items.push(c);
                    }
                }

                return (List<Character>) items;
            })
            .toList();
    }

    private List<Instruction> parseInstructions(String raw) {
        return raw.lines()
            .map(s -> {
                var parts = s.split(" ");
                return new Instruction(
                    Integer.parseInt(parts[3]),
                    Integer.parseInt(parts[5]),
                    Integer.parseInt(parts[1])
                );
            })
            .toList();
    }
    
    record Input(List<List<Character>> board, List<Instruction> instructions) {}
    record Instruction(int from, int to, int amount) {}

}
