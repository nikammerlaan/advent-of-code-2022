package com.github.nikammerlaan.aoc.days.day02;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;

import java.util.List;

import static com.github.nikammerlaan.aoc.days.day02.Day02Solution.Move.*;
import static com.github.nikammerlaan.aoc.days.day02.Day02Solution.Outcome.*;

public class Day02Solution extends AbstractDaySolution<List<Day02Solution.Round>> {

    @Override
    protected Object solvePart1(List<Round> input) {
        return input.stream()
            .mapToInt(round -> {
                var a = round.move();
                var b = switch(round.outcome()) {
                    case LOSE -> ROCK;
                    case DRAW -> PAPER;
                    case WIN  -> SCISSORS;
                };

                return getRoundScore(a, b);
            })
            .sum();
    }

    @Override
    protected Object solvePart2(List<Round> input) {
        return input.stream()
            .mapToInt(round -> {
                var a = round.move();
                var b = switch(round.outcome()) {
                    case LOSE -> a.strongAgainst();
                    case DRAW -> a;
                    case WIN  -> a.weakAgainst();
                };

                return getRoundScore(a, b);
            })
            .sum();
    }

    private int getRoundScore(Move a, Move b) {
        int score = b.getScore();

        if(a == b) {
            score += 3;
        } else if(b.strongAgainst() == a) {
            score += 6;
        }

        return score;
    }

    @Override
    protected List<Round> parseInput(String rawInput) {
        return rawInput.lines()
            .map(s -> {
                var parts = s.split(" ");
                var move = switch(parts[0]) {
                    case "A" -> ROCK;
                    case "B" -> PAPER;
                    case "C" -> SCISSORS;
                    default -> throw new IllegalStateException();
                };
                var outcome = switch(parts[1]) {
                    case "X" -> LOSE;
                    case "Y" -> DRAW;
                    case "Z" -> WIN;
                    default -> throw new IllegalStateException();
                };
                return new Round(move, outcome);
            })
            .toList();
    }

    record Round(Move move, Outcome outcome) {}
    enum Outcome { WIN, LOSE, DRAW }
    enum Move {

        ROCK(1),
        PAPER(2),
        SCISSORS(3);

        private final int score;

        Move(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public Move strongAgainst() {
            var values = Move.values();
            return values[(values.length + ordinal() - 1) % values.length];
        }

        public Move weakAgainst() {
            var values = Move.values();
            return values[(ordinal() + 1) % values.length];
        }

    }

}
