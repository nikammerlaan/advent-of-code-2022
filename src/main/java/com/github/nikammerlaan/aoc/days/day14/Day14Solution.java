package com.github.nikammerlaan.aoc.days.day14;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;
import com.github.nikammerlaan.aoc.misc.Point;

import java.util.*;

public class Day14Solution extends AbstractDaySolution<List<List<Point>>> {

    @Override
    protected Object solvePart1(List<List<Point>> input) {
        var grid = createGrid(input);

        var count = 0;
        while(dropSand(grid)) {
            count++;
        }

        return count;
    }

    @Override
    protected Object solvePart2(List<List<Point>> input) {
        var grid = createGrid(input);

        var floor = 0;
        for(int x = 0; x < grid.length; x++) {
            for(int y = floor; y < grid[x].length; y++) {
                if(grid[x][y]) {
                    floor = y;
                }
            }
        }
        floor += 2;

        for(int x = 0; x < grid.length; x++) {
            grid[x][floor] = true;
        }

        var count = 0;
        while(dropSand(grid)) {
            count++;
        }

        return count;
    }

    private boolean dropSand(boolean[][] grid) {
        int x = 500;
        int y = 0;

        if(grid[x][y]) {
            return false;
        }

        while(y < grid[x].length - 1) {
            if(!grid[x][y + 1]) { // down
                y++;
            } else if(!grid[x - 1][y + 1]) { // left
                x--;
                y++;
            } else if(!grid[x + 1][y + 1]) { // right
                x++;
                y++;
            } else {
                break;
            }
        }

        if(y == grid.length - 1) {
            return false;
        }

        grid[x][y] = true;

        return true;
    }

    private boolean[][] createGrid(List<List<Point>> input) {
        var grid = new boolean[1000][1000];
        for(var line : input) {
            var start = line.get(0);
            for(int i = 1; i < line.size(); i++) {
                var end = line.get(i);
                if(start.x() == end.x()) { // y changes
                    int startY = start.y();
                    int endY = end.y();
                    if(startY > endY) {
                        int temp = startY;
                        startY = endY;
                        endY = temp;
                    }
                    for(int y = startY; y <= endY; y++) {
                        grid[start.x()][y] = true;
                    }
                } else { // x changes
                    int startX = start.x();
                    int endX = end.x();
                    if(startX > endX) {
                        int temp = startX;
                        startX = endX;
                        endX = temp;
                    }
                    for(int x = startX; x <= endX; x++) {
                        grid[x][start.y()] = true;
                    }
                }

                start = end;
            }
        }
        return grid;
    }

    @Override
    protected List<List<Point>> parseInput(String rawInput) {
        return rawInput.lines()
            .map(this::parseLine)
            .toList();
    }

    private List<Point> parseLine(String raw) {
        return Arrays.stream(raw.split(" -> "))
            .map(this::parsePoint)
            .toList();
    }

    private Point parsePoint(String raw) {
        var parts = raw.split(",");
        return new Point(
            Integer.parseInt(parts[0]),
            Integer.parseInt(parts[1])
        );
    }

}
