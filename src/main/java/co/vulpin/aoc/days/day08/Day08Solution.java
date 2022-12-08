package co.vulpin.aoc.days.day08;

import co.vulpin.aoc.days.AbstractDaySolution;

public class Day08Solution extends AbstractDaySolution<int[][]> {

    @Override
    protected Object solvePart1(int[][] input) {
        var rows = input.length;
        var cols = input[0].length;

        var reachable = new boolean[rows][cols];

        for(int r = 0; r < rows; r++) {
            int max = Integer.MIN_VALUE;
            for(int c = 0; c < cols; c++) {
                var value = input[r][c];
                if(value > max) {
                    reachable[r][c] = true;
                    max = value;
                }
            }
        }

        for(int r = 0; r < rows; r++) {
            int max = Integer.MIN_VALUE;
            for(int c = cols - 1; c >= 0; c--) {
                var value = input[r][c];
                if(value > max) {
                    reachable[r][c] = true;
                    max = value;
                }
            }
        }

        for(int c = 0; c < cols; c++) {
            int max = Integer.MIN_VALUE;
            for(int r = 0; r < rows; r++) {
                var value = input[r][c];
                if(value > max) {
                    reachable[r][c] = true;
                    max = value;
                }
            }
        }

        for(int c = 0; c < cols; c++) {
            int max = Integer.MIN_VALUE;
            for(int r = rows - 1; r >= 0; r--) {
                var value = input[r][c];
                if(value > max) {
                    reachable[r][c] = true;
                    max = value;
                }
            }
        }

        int count = 0;
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                if(reachable[r][c]) {
                    count++;
                }
            }
        }

        return count;
    }

    @Override
    protected Object solvePart2(int[][] input) {
        var rows = input.length;
        var cols = input[0].length;

        int max = 0;
        for(int r = 1; r < rows - 1; r++) {
            for(int c = 1; c < cols - 1; c++) {
                max = Math.max(max, getScenicScore(input, r, c));
            }
        }
        return max;
    }

    private int getScenicScore(int[][] input, int r, int c) {
        var rows = input.length;
        var cols = input[0].length;

        var value = input[r][c];

        int upCount = 0;
        for(int i = 1; r - i >= 0; i++) {
            upCount++;
            if(input[r - i][c] >= value) {
                break;
            }
        }

        int downCount = 0;
        for(int i = 1; r + i < rows; i++) {
            downCount++;
            if(input[r + i][c] >= value) {
                break;
            }
        }

        int leftCount = 0;
        for(int i = 1; c - i >= 0; i++) {
            leftCount++;
            if(input[r][c - i] >= value) {
                break;
            }
        }

        int rightCount = 0;
        for(int i = 1; c + i < cols; i++) {
            rightCount++;
            if(input[r][c + i] >= value) {
                break;
            }
        }

        return downCount * upCount * leftCount * rightCount;
    }

    @Override
    protected int[][] parseInput(String rawInput) {
        return rawInput.lines()
            .map(line -> line.chars()
                .map(Character::getNumericValue)
                .toArray()
            )
            .toArray(int[][]::new);
    }

}
