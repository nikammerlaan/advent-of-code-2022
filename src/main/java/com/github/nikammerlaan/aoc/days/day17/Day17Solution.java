package com.github.nikammerlaan.aoc.days.day17;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;

import java.util.*;
import java.util.stream.*;

public class Day17Solution extends AbstractDaySolution<String> {

    private final int[][] HORIZONTAL_LINE_SHAPE = new int[][] {
        { 1, 1, 1, 1 }
    };

    private final int[][] PLUS_SHAPE = new int[][] {
        { 0, 1, 0 },
        { 1, 1, 1 },
        { 0, 1, 0 }
    };

    private final int[][] BACKWARDS_L_SHAPE = new int[][] {
        { 1, 1, 1 },
        { 0, 0, 1 },
        { 0, 0, 1 }
    };

    private final int[][] VERTICAL_LINE_SHAPE = new int[][] {
        { 1 },
        { 1 },
        { 1 },
        { 1 }
    };

    private final int[][] SQUARE_SHAPE = new int[][]{
        { 1, 1 },
        { 1, 1 }
    };

    private final List<int[][]> SHAPES = List.of(
        HORIZONTAL_LINE_SHAPE,
        PLUS_SHAPE,
        BACKWARDS_L_SHAPE,
        VERTICAL_LINE_SHAPE,
        SQUARE_SHAPE
    );

    @Override
    protected Object solvePart1(String jets) {
        return solve(jets, 2022);
    }

    @Override
    protected Object solvePart2(String jets) {
        return solve(jets, 1_000_000_000_000L);
    }

    private long solve(String jets, long rockCount) {
        var shapeIndex = 0;
        var jetIndex = 0;
        var height = 0;

        var board = new boolean[10_000][7];

        record CacheValue(int rock, int height) {}
        var cache = new HashMap<String, CacheValue>();

        for(int i = 1; i <= rockCount; i++) {
            var shape = SHAPES.get(shapeIndex);
            shapeIndex++;
            shapeIndex %= SHAPES.size();

            var shapeRow = height + 3;
            var shapeCol = 2;

            while(true) {
                var jet = jets.charAt(jetIndex);
                jetIndex++;
                jetIndex %= jets.length();

                if(jet == '>' && canMoveRight(board, shape, shapeRow, shapeCol)) {
                    shapeCol++;
                } else if(jet == '<' && canMoveLeft(board, shape, shapeRow, shapeCol)) {
                    if(canMoveLeft(board, shape, shapeRow, shapeCol)) {
                        shapeCol--;
                    }
                }

                if(canMoveDown(board, shape, shapeRow, shapeCol)) {
                    shapeRow--;
                } else {
                    break;
                }
            }

            // Set new height
            height = Math.max(height, shapeRow + shape.length);

            // Write shape to board
            for(int x = 0; x < shape.length; x++) {
                for(int y = 0; y < shape[x].length; y++) {
                    if(shape[x][y] == 1) {
                        board[shapeRow + x][shapeCol + y] = true;
                    }
                }
            }

            // Handle cache of seen shapes. It takes the last n rows and writes them to a string. Then,
            // we can look that up and see if we've seen that string before. If we have, then we've (maybe) got a cycle
            var n = 1_000;
            if(height >= n) {
                var temp = height;
                var lastNRows = IntStream.range(0, n)
                    .mapToObj(row -> board[temp - row])
                    .map(arr -> IntStream.range(0, arr.length)
                        .mapToObj(col -> arr[col] ? "#" : ".")
                        .collect(Collectors.joining())
                    )
                    .collect(Collectors.joining(("\n")));
                if(cache.containsKey(lastNRows)) {
                    var cacheValue = cache.get(lastNRows);
                    var offsetRocks = cacheValue.rock;
                    var offsetHeight = cacheValue.height;
                    var cycleRocks = i - offsetRocks;
                    var cycleHeight = height - offsetHeight;
                    var remainingRocks = rockCount - i;
                    var result = height + remainingRocks / cycleRocks * cycleHeight;
                    remainingRocks %= cycleRocks;
                    var newStart = offsetRocks + remainingRocks;
                    return result + solve(jets, newStart) - offsetHeight;
                } else {
                    cache.put(lastNRows, new CacheValue(i, height));
                }
            }
        }

        return height;
    }

    private boolean canMoveDown(boolean[][] board, int[][] shape, int shapeRow, int shapeCol) {
        if(shapeRow == 0) {
            return false;
        }

        for(int x = 0; x < shape.length; x++) {
            for(int y = 0; y < shape[x].length; y++) {
                if(shape[x][y] == 1 && board[shapeRow + x - 1][shapeCol + y]) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean canMoveLeft(boolean[][] board, int[][] shape, int shapeRow, int shapeCol) {
        if(shapeCol > 0) {
            boolean canMoveLeft = true;

            for(int x = 0; x < shape.length; x++) {
                for(int y = 0; y < shape[x].length; y++) {
                    if(shape[x][y] == 1 && board[shapeRow + x][shapeCol + y - 1]) {
                        canMoveLeft = false;
                    }
                }
            }

            return canMoveLeft;
        } else {
            return false;
        }
    }

    private boolean canMoveRight(boolean[][] board, int[][] shape, int shapeRow, int shapeCol) {
        if(board[0].length > shapeCol + shape[0].length) {
            boolean canMoveRight = true;

            for(int x = 0; x < shape.length; x++) {
                for(int y = 0; y < shape[x].length; y++) {
                    if(shape[x][y] == 1 && board[shapeRow + x][shapeCol + y + 1]) {
                        canMoveRight = false;
                    }
                }
            }

            return canMoveRight;
        } else {
            return false;
        }
    }

    @Override
    protected String parseInput(String rawInput) {
        return rawInput;
    }

}
