package com.github.nikammerlaan.aoc.days.day24;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;
import com.github.nikammerlaan.aoc.misc.Point;

import java.util.*;

public class Day24Solution extends AbstractDaySolution<Day24Solution.Input> {

    public Day24Solution() {}

    @Override
    protected Object solvePart1(Input input) {
        return solve(input, input.start, input.target, 0);
    }

    @Override
    protected Object solvePart2(Input input) {
        int minute = 0;

        minute = solve(input, input.start, input.target, minute);
        minute = solve(input, input.target, input.start, minute);
        minute = solve(input, input.start, input.target, minute);

        return minute;
    }

    private int solve(Input input, Point start, Point target, int startMinute) {
        record State(Point point, int minute) {}

        var queue = new LinkedList<State>();
        queue.add(new State(start, startMinute));

        var seen = new HashSet<State>();

        while(!queue.isEmpty()) {
            var state = queue.poll();
            if(!seen.add(state)) {
                continue;
            }

            var point = state.point();
            var minute = state.minute();

            var x = point.x();
            var y = point.y();

            if(!isFree(input, point, minute)) {
                continue;
            }

            if(point.equals(target)) {
                return minute;
            }

            queue.add(new State(new Point(x - 1, y), minute + 1)); // up
            queue.add(new State(new Point(x + 1, y), minute + 1)); // down
            queue.add(new State(new Point(x, y - 1), minute + 1)); // left
            queue.add(new State(new Point(x, y + 1), minute + 1)); // right
            queue.add(new State(new Point(x, y),     minute + 1)); // wait
        }

        throw new IllegalStateException();
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
