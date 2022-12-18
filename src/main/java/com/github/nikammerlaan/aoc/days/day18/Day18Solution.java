package com.github.nikammerlaan.aoc.days.day18;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;
import com.github.nikammerlaan.aoc.misc.*;

import java.util.*;

public class Day18Solution extends AbstractDaySolution<List<Point3D>> {

    @Override
    protected Object solvePart1(List<Point3D> points) {
        var set = new HashSet<>(points);

        return points.stream()
            .flatMap(point -> point.getAdjacentPoints().stream())
            .filter(point -> !set.contains(point))
            .count();
    }

    @Override
    protected Object solvePart2(List<Point3D> points) {
        var outsideAccessiblePoints = getOutsideAccessiblePoints(points);
        
        return points.stream()
            .flatMap(point -> point.getAdjacentPoints().stream())
            .filter(outsideAccessiblePoints::contains)
            .count();
    }

    private Set<Point3D> getOutsideAccessiblePoints(List<Point3D> points) {
        var set = new HashSet<>(points);
        var bounds = getBounds(points);

        var start = new Point3D(bounds.x().start(), bounds.y().start(), bounds.z().start());
        var seen = new HashSet<Point3D>();
        var queue = new LinkedList<Point3D>();
        queue.add(start);

        while(!queue.isEmpty()) {
            var point = queue.poll();

            if(!seen.add(point)) {
                continue;
            }

            for(var adj : point.getAdjacentPoints()) {
                if(!set.contains(adj) && !seen.contains(adj) && bounds.contains(adj)) {
                    queue.add(adj);
                }
            }
        }

        return seen;
    }

    private Box3D getBounds(Collection<Point3D> points) {
        var xStats = points.stream().mapToInt(Point3D::x).summaryStatistics();
        var yStats = points.stream().mapToInt(Point3D::y).summaryStatistics();
        var zStats = points.stream().mapToInt(Point3D::z).summaryStatistics();

        // Adds 1 unit of wiggle room to all axes to allow for flow around the cubes
        return new Box3D(
            new Range(xStats.getMin() - 1, xStats.getMax() + 1),
            new Range(yStats.getMin() - 1, yStats.getMax() + 1),
            new Range(zStats.getMin() - 1, zStats.getMax() + 1)
        );
    }

    @Override
    protected List<Point3D> parseInput(String rawInput) {
        return rawInput.lines()
            .map(line -> {
                var parts = line.split(",");
                return new Point3D(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
                );
            })
            .toList();
    }

}
