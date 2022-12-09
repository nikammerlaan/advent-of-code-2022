package co.vulpin.aoc.days.day09;

import co.vulpin.aoc.days.AbstractDaySolution;
import co.vulpin.aoc.misc.Point;

import java.util.*;

import static co.vulpin.aoc.days.day09.Day09Solution.Direction.*;

public class Day09Solution extends AbstractDaySolution<List<Day09Solution.Direction>> {

    @Override
    protected Object solvePart1(List<Direction> instructions) {
        return solve(instructions, 2);
    }

    @Override
    protected Object solvePart2(List<Direction> instructions) {
        return solve(instructions, 10);
    }

    private int solve(List<Direction> instructions, int chainSize) {
        var chain = new Point[chainSize];
        Arrays.fill(chain, new Point(0, 0));

        var tailSeen = new HashSet<Point>();

        for(var instruction : instructions) {
            var head = chain[0];
            var headX = head.x();
            var headY = head.y();
            switch(instruction) {
                case UP    -> headY++;
                case DOWN  -> headY--;
                case LEFT  -> headX--;
                case RIGHT -> headX++;
            }
            chain[0] = new Point(headX, headY);

            for(int j = 1; j < chain.length; j++) {
                chain[j] = move(chain[j], chain[j - 1]);
            }

            tailSeen.add(chain[chain.length - 1]);
        }

        return tailSeen.size();
    }

    private Point move(Point a, Point b) {
        var xDiff = a.x() - b.x();
        var yDiff = a.y() - b.y();

        if(Math.abs(xDiff) <= 1 && Math.abs(yDiff) <= 1) {
            return a;
        } else {
            return new Point(
                a.x() - Integer.signum(xDiff),
                a.y() - Integer.signum(yDiff)
            );
        }
    }

    @Override
    protected List<Direction> parseInput(String rawInput) {
        return rawInput.lines()
            .flatMap(line -> {
                var parts = line.split(" ");
                var direction = switch(parts[0]) {
                    case "U" -> UP;
                    case "D" -> DOWN;
                    case "L" -> LEFT;
                    case "R" -> RIGHT;
                    default -> throw new IllegalArgumentException();
                };
                var amount = Integer.parseInt(parts[1]);
                return Collections.nCopies(amount, direction).stream();
            })
            .toList();
    }
    
    enum Direction { UP, DOWN, LEFT, RIGHT }

}
