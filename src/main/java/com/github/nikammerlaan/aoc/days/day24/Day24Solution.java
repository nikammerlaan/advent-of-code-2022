package com.github.nikammerlaan.aoc.days.day24;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;
import com.github.nikammerlaan.aoc.misc.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Day24Solution extends AbstractDaySolution<Day24Solution.Input> {

    private static final int SAFE_DEPTH = 2_000;

    record CacheKey(Point point, Point target, int minute) {}
    private final Map<CacheKey, Integer> cache;

    public Day24Solution() {
        this.cache = new ConcurrentHashMap<>();
    }

    @Override
    protected Object solvePart1(Input input) {
        var start = input.start;
        var goal = input.target;

        return solve(input, start, goal, 0, SAFE_DEPTH);
    }

    @Override
    protected Object solvePart2(Input input) {
        var start = input.start;
        var goal = input.target;

        int minute = 0;
        minute = solve(input, start, goal, minute, minute + SAFE_DEPTH);
        minute = solve(input, goal, start, minute, minute + SAFE_DEPTH);
        minute = solve(input, start, goal, minute, minute + SAFE_DEPTH);

        return minute;
    }

    private int solve(Input input, Point point, Point goal, int minute, int min) {
        var x = point.x();
        var y = point.y();

        var goalX = goal.x();
        var goalY = goal.y();

        if(!isFree(input, point, minute)) {
            return min;
        }

        var cacheKey = new CacheKey(point, goal, minute);
        if(cache.containsKey(cacheKey)) {
            return Math.min(min, cache.get(cacheKey));
        }

        if(minute >= min) {
            return min;
        }

        if(point.equals(goal)) {
            return minute;
        }

        var remainingTime = min - minute;
        var distance = MathUtils.getManhattanDistance(x, y, goalX, goalY);
        if(distance >= remainingTime) {
            return min;
        }

        var up = new Point(x - 1, y);
        var down = new Point(x + 1, y);
        var left = new Point(x, y - 1);
        var right = new Point(x, y + 1);

        if(x > goalX) {
            // Up
            min = solve(input,up, goal, minute + 1, min);
        } else {
            // Down
            min = solve(input ,down, goal, minute + 1, min);
        }

        if(y > goalY) {
            // Left
            min = solve(input, left, goal, minute + 1, min);
        } else {
            // Right
            min = solve(input, right, goal, minute + 1, min);
        }

        if(x > goalX) {
            // Down
            min = solve(input, down, goal, minute + 1, min);
        } else {
            // Up
            min = solve(input, up, goal, minute + 1, min);
        }

        if(y > goalY) {
            // Right
            min = solve(input, right, goal, minute + 1, min);
        } else {
            // Left
            min = solve(input, left, goal, minute + 1, min);
        }

        // Wait
        min = solve(input, point, goal, minute + 1, min);

        cache.put(cacheKey, min);

        return min;
    }

    private boolean isFree(Input input, Point point, int minute) {
        var board = input.board;

        var x = point.x();
        var y = point.y();

        if(point.equals(input.start()) || point.equals(input.target())) {
            return true;
        }

        // Out of bounds
        if(x < 0 || x >= board.length - 1) {
            return false;
        }

        if(board[x][y] == '#') {
            return false;
        }

        var xLen = board.length - 2;
        var yLen = board[0].length - 2;

        var dirX = x - 1;
        var dirY = y - 1;

        var upX = Math.floorMod(dirX + minute, xLen);
        if(input.upChars[upX][dirY]) {
            return false;
        }

        var downX = Math.floorMod(dirX - minute, xLen);
        if(input.downChars[downX][dirY]) {
            return false;
        }

        var leftY = Math.floorMod(dirY + minute, yLen);
        if(input.leftChars[dirX][leftY]) {
            return false;
        }

        var rightY = Math.floorMod(dirY - minute, yLen);
        if(input.rightChars[dirX][rightY]) {
            return false;
        }

        return true;
    }

    @Override
    protected Input parseInput(String rawInput) {
        var board = rawInput.lines()
            .map(String::toCharArray)
            .toArray(char[][]::new);

        var xLen = board.length;
        var yLen = board[0].length;

        var upChars = new boolean[xLen - 2][yLen - 2];
        var downChars = new boolean[xLen - 2][yLen - 2];
        var leftChars = new boolean[xLen - 2][yLen - 2];
        var rightChars = new boolean[xLen - 2][yLen - 2];

        for(int x = 1; x < xLen - 1; x++) {
            for(int y = 1; y < yLen - 1; y++) {
                switch(board[x][y]) {
                    case '^' -> upChars[x - 1][y - 1] = true;
                    case 'v' -> downChars[x - 1][y - 1] = true;
                    case '<' -> leftChars[x - 1][y - 1] = true;
                    case '>' -> rightChars[x - 1][y - 1] = true;
                }
            }
        }

        var start = new Point(0, 1);
        var target = new Point(xLen - 1, yLen - 2);

        return new Input(board, start, target, upChars, downChars, leftChars, rightChars);
    }

    record Input(char[][] board, Point start, Point target, boolean[][] upChars, boolean[][] downChars, boolean[][] leftChars, boolean[][] rightChars) {}

}
