package com.github.nikammerlaan.aoc.days.day15;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;
import com.github.nikammerlaan.aoc.misc.Point;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

public class Day15Solution extends AbstractDaySolution<List<Day15Solution.Input>> {

    private static final Pattern PATTERN = Pattern.compile("x=(-?\\d+), y=(-?\\d+)");

    @Override
    protected Object solvePart1(List<Input> input) {
        var targetY = 2_000_000;

        var set = new HashSet<Integer>();

        for(var a : input) {
            var distance = a.sensorPoint().getDistance(a.beaconPoint());
            var yDifferenceTarget = Math.abs(a.sensorPoint().y() - targetY);

            for(int xOffset = -distance + yDifferenceTarget; xOffset <= distance - yDifferenceTarget; xOffset++) {
                var x = a.sensorPoint().x() + xOffset;
                set.add(x);
            }

            if(a.beaconPoint().y() == targetY) {
                set.remove(a.beaconPoint().x());
            }
        }

        return set.size();
    }

    @Override
    protected Object solvePart2(List<Input> input) {
        for(int x = 0; x < 4_000_000; x++) {
            for(int y = 0; y < 4_000_000; y++) {
                var point = new Point(x, y);
                int skip = 0;
                for(var line : input) {
                    var distance = point.getDistance(line.sensorPoint());
                    if(distance <= line.distance()) {
                        skip = Math.max(skip, line.distance() - distance);
                    }
                }
                if(skip > 0) {
                    y += skip;
                } else {
                    return getTuningFrequency(point);
                }
            }
        }

        throw new IllegalStateException();
    }

    private long getTuningFrequency(Point point) {
        return point.x() * 4_000_000L + point.y();
    }

    @Override
    protected List<Input> parseInput(String rawInput) {
        return rawInput.lines()
            .map(this::parseLine)
            .toList();
    }

    private Input parseLine(String raw) {
        var matcher = PATTERN.matcher(raw);
        matcher.find();
        var sensorPoint = new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        matcher.find();
        var beaconPoint = new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        return new Input(sensorPoint, beaconPoint);
    }

    record Input(Point sensorPoint, Point beaconPoint, int distance) {

        public Input(Point sensorPoint, Point beaconPoint) {
            this(sensorPoint, beaconPoint, sensorPoint.getDistance(beaconPoint));
        }

    }

}
