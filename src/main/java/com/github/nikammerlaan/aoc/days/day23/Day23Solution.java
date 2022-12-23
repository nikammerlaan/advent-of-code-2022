package com.github.nikammerlaan.aoc.days.day23;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;
import com.github.nikammerlaan.aoc.misc.Point;

import java.util.*;

public class Day23Solution extends AbstractDaySolution<Set<Point>> {

    enum Direction { NORTH, SOUTH, WEST, EAST }

    @Override
    protected Object solvePart1(Set<Point> input) {
        var points = new HashSet<>(input);

        for(int i = 0; i < 10; i++) {
            processRound(points, i);
        }

        var xStats = points.stream()
            .mapToInt(Point::x)
            .summaryStatistics();
        var yStats = points.stream()
            .mapToInt(Point::y)
            .summaryStatistics();
        return (xStats.getMax() - xStats.getMin() + 1) * (yStats.getMax() - yStats.getMin() + 1) - points.size();
    }

    @Override
    protected Object solvePart2(Set<Point> input) {
        var points = new HashSet<>(input);

        for(int i = 0; true; i++) {
            var moved = processRound(points, i);
            if(moved == 0) {
                return i + 1;
            }
        }
    }

    private int processRound(Set<Point> points, int round) {
        var proposals = new HashMap<Point, Point>();
        var reverseProposalCounts = new HashMap<Point, Integer>();

        for(var point : points) {
            var x = point.x();
            var y = point.y();

            var nw = !points.contains(new Point(x - 1, y - 1));
            var n = !points.contains(new Point(x - 1, y));
            var ne = !points.contains(new Point(x - 1, y + 1));
            var e = !points.contains(new Point(x, y + 1));
            var se = !points.contains(new Point(x + 1, y + 1));
            var s = !points.contains(new Point(x + 1, y));
            var sw = !points.contains(new Point(x + 1, y - 1));
            var w = !points.contains(new Point(x, y - 1));

            if(nw && n && ne && e && se && s && sw && w) {
                continue;
            }

            var directions = Direction.values();
            for(int i = 0; i < directions.length; i++) {
                var dir = directions[(round + i) % directions.length];
                var proposal = switch(dir) {
                    case NORTH -> {
                        if(nw && n && ne) {
                            yield new Point(x - 1, y);
                        } else {
                            yield null;
                        }
                    }
                    case SOUTH -> {
                        if(sw && s && se) {
                            yield new Point(x + 1, y);
                        } else {
                            yield null;
                        }
                    }
                    case WEST -> {
                        if(sw && w && nw) {
                            yield new Point(x, y - 1);
                        } else {
                            yield null;
                        }
                    }
                    case EAST -> {
                        if(se && e && ne) {
                            yield new Point(x, y + 1);
                        } else {
                            yield null;
                        }
                    }
                };
                if(proposal != null) {
                    proposals.put(new Point(x, y), proposal);
                    reverseProposalCounts.put(proposal, reverseProposalCounts.getOrDefault(proposal, 0) + 1);
                    break;
                }
            }
        }

        int moved = 0;
        for(var from : proposals.keySet()) {
            var to = proposals.get(from);
            var count = reverseProposalCounts.get(to);
            if(count == 1) {
                points.remove(from);
                points.add(to);
                moved++;
            }
        }

        return moved;
    }

    @Override
    protected Set<Point> parseInput(String rawInput) {
        var lines = rawInput.split("\n");
        var points = new HashSet<Point>();

        for(int x = 0; x < lines.length; x++) {
            for(int y = 0; y < lines[x].length(); y++) {
                if(lines[x].charAt(y) == '#') {
                    points.add(new Point(x, y));
                }
            }
        }

        return points;
    }

}
