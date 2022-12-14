package com.github.nikammerlaan.aoc.days.day12;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;
import com.github.nikammerlaan.aoc.misc.Point;

import java.util.*;

public class Day12Solution extends AbstractDaySolution<char[][]> {

    @Override
    protected Object solvePart1(char[][] input) {
        return shortestPath(input, 'S');
    }

    @Override
    protected Object solvePart2(char[][] input) {
        return shortestPath(input, 'a');
    }

    private int shortestPath(char[][] input, char startChar) {
        int rows = input.length;
        int cols = input[0].length;

        boolean[][] explored = new boolean[rows][cols];
        int[][] distance = new int[rows][cols];
        for(var row : distance) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        var queue = new LinkedList<Point>();

        for(int x = 0; x < rows; x++) {
            for(int y = 0; y < cols; y++) {
                if(input[x][y] == startChar) {
                    distance[x][y] = 0;
                    queue.add(new Point(x, y));
                }
            }
        }

        while(!queue.isEmpty()) {
            var point = queue.pop();
            var x = point.x();
            var y = point.y();
            if(explored[x][y]) {
                continue;
            } else {
                explored[x][y] = true;
            }

            for(var p : getConnectedPoints(input, x, y)) {
                distance[p.x()][p.y()] = Math.min(distance[p.x()][p.y()], distance[x][y] + 1);
                if(!explored[p.x()][p.y()]) {
                    queue.add(p);
                }
            }
        }

        var point = findPoint(input, 'E');
        return distance[point.x()][point.y()];
    }

    private Point findPoint(char[][] input, char target) {
        int rows = input.length;
        int cols = input[0].length;
        for(int x = 0; x < rows; x++) {
            for(int y = 0; y < cols; y++) {
                if(input[x][y] == target)  {
                    return new Point(x, y);
                }
            }
        }
        throw new IllegalStateException();
    }

    private List<Point> getConnectedPoints(char[][] input, int x, int y) {
        var adj = getAdjacentPoints(input, x, y);
        return adj.stream()
            .filter(point -> canConnect(input[x][y], input[point.x()][point.y()]))
            .toList();
    }

    private List<Point> getAdjacentPoints(char[][] grid, int x, int y) {
        var output = new ArrayList<Point>();
        if(x > 0) {
            output.add(new Point(x - 1, y));
        }
        if(x < grid.length - 1) {
            output.add(new Point(x + 1, y));
        }
        if(y > 0) {
            output.add(new Point(x, y - 1));
        }
        if(y < grid[0].length - 1) {
            output.add(new Point(x, y + 1));
        }
        return output;
    }

    private boolean canConnect(char a, char b) {
        if(a == 'S') {
            a = 'a';
        }
        if(b == 'S') {
            b = 'a';
        }
        if(a == 'E') {
            a = 'z';
        }
        if(b == 'E') {
            b = 'z';
        }
        int diff = b - a;
        return diff <= 1;
    }

    @Override
    protected char[][] parseInput(String rawInput) {
        return rawInput.lines()
            .map(String::toCharArray)
            .toArray(char[][]::new);
    }

}
