package com.github.nikammerlaan.aoc.days.day22;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;
import com.github.nikammerlaan.aoc.misc.MathUtils;
import com.github.nikammerlaan.aoc.misc.Point;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day22Solution extends AbstractDaySolution<Day22Solution.Input> {

    //
    // WARNING
    //
    // THIS CODE BARELY WORKS
    //

    @Override
    protected Object solvePart1(Input input) {
        var x = 1;
        var y = 1;
        var d = 0;

        var board = input.board();
        while(board[x][y] != '.') {
            y++;
        }

        for(var instruction : input.instructions()) {
            if(MathUtils.isInt(instruction)) {
                var point = getNextPosition(board, x, y, d, Integer.parseInt(instruction));
                x = point.x();
                y = point.y();
            } else if(instruction.equals("L")) {
                d = (d + 3) % 4;
            } else if(instruction.equals("R")) {
                d = (d + 1) % 4;
            }
        }

        return getScore(x, y, d);
    }



    @Override
    protected Object solvePart2(Input input) {
        var board = input.board();

        var faces = getFaces(board);

        var face = faces.get(0);

        var x = 0;
        var y = 0;
        var d = 0;

        while(face.board[x][y] == '#') {
            y++;
        }

        int i = 0;
        for(var instruction : input.instructions()) {
            if(MathUtils.isInt(instruction)) {
                var state = getNextState(faces, face, x, y, d, Integer.parseInt(instruction));
                face = state.face();
                x = state.x();
                y = state.y();
                d = state.d();
            } else if(instruction.equals("L")) {
                d = (d + 3) % 4;
            } else if(instruction.equals("R")) {
                d = (d + 1) % 4;
            }

            i++;
        }

        return getScore(face.x + x, face.y + y, d);
    }

    private int getScore(int x, int y, int d) {
        var xScore = 1_000 * x;
        var yScore = 4 * y;
        var dScore = d;
        return xScore + yScore + dScore;
    }

    private Point getNextPosition(char[][] board, int x, int y, int d, int amount) {
        while(amount > 0) {
            int newX = x;
            int newY = y;

            do {
                switch(d) {
                    case 0 -> newY++;
                    case 1 -> newX++;
                    case 2 -> newY--;
                    case 3 -> newX--;
                }

                newX = (newX + board.length) % board.length;
                newY = (newY + board[0].length) % board[0].length;
            } while(board[newX][newY] == ' ');

            if(board[newX][newY] == '#') {
                return new Point(x, y);
            } else {
                x = newX;
                y = newY;
                amount--;
            }
        }
        return new Point(x, y);
    }

    record State(Face face, int x, int y, int d) {}
    private List<Face> getFaces(char[][] board) {
        var sideLength = getSideLength(board);
        var seen = new boolean[board.length][board[0].length];

        var sides = new ArrayList<Face>();

        int[][] connections;

        // Manually specify connections between faces
        if(sideLength == 50) {
            connections = new int[][] {
                {1, 2, 3, 5}, // 0
                {4, 2, 0, 5}, // 1
                {1, 4, 3, 0}, // 2
                {4, 5, 0, 2}, // 3
                {1, 5, 3, 2}, // 4
                {4, 1, 0, 3}  // 5
            };
        } else if(sideLength == 4) {
            connections = new int[][] {
                { 5, 3, 2, 1 }, // 0
                { 2, 4, 5, 0 }, // 1
                { 3, 4, 1, 0 }, // 2
                { 5, 4, 2, 0 }, // 3
                { 5, 1, 2, 3 }, // 4
                { 0, 1, 4, 3 }  // 5
            };
        } else {
            throw new IllegalStateException("No hardcoded connections for this input");
        }

        for(int x = 0; x < board.length; x++) {
            for(int y = 0; y < board[x].length; y++) {
                if(board[x][y] != ' ' && !seen[x][y]) {
                    var side = new char[sideLength][sideLength];

                    for(int sideX = 0; sideX < sideLength; sideX++) {
                        for(int sideY = 0; sideY < sideLength; sideY++) {
                            side[sideX][sideY] = board[x + sideX][y + sideY];
                            seen[x + sideX][y + sideY] = true;
                        }
                    }

                    var num = sides.size();
                    sides.add(new Face(num, x, y, side, connections[num]));
                }
            }
        }

        return sides;
    }

    private State getNextState(List<Face> faces, Face face, int x, int y, int d, int amount) {
        var len = face.board.length;

        while(amount > 0) {
            var newFace = face;
            var newX = x;
            var newY = y;
            var newD = d;

            switch(d) {
                case 0 -> newY++;
                case 1 -> newX++;
                case 2 -> newY--;
                case 3 -> newX--;
            }

            if(isOutOfBounds(newFace.board, newX, newY)) {
                newFace = faces.get(face.connections[d]);
                var outgoingDirection = d;
                int incomingDirection = (newFace.getConnectingDirection(face.num) + 2) % 4;

                newD = incomingDirection;

                switch(outgoingDirection) {
                    case 0 -> {
                        switch(incomingDirection) {
                            case 0 -> {
                                newY = 0;
                            }
                            case 1 -> {
                                newX = 0;
                                newY = len - x - 1;
                            }
                            case 2 -> {
                                newX = len - x - 1;
                                newY = len - 1;
                            }
                            case 3 -> {
                                newX = len - 1;
                                newY = x;
                            }
                        }
                    }
                    case 1 -> {
                        switch(incomingDirection) {
                            case 0 -> {
                                // Broken
                                newX = 0;
                                newY = 0;
                            }
                            case 1 -> {
                                newX = 0;
                            }
                            case 2 -> {
                                newX = y;
                                newY = len - 1;
                            }
                            case 3 -> {
                                newX = len - 1;
                                newY = len - y - 1;
                            }
                        }
                    }
                    case 2 -> {
                        switch(incomingDirection) {
                            case 0 -> {
                                newX = len - x - 1;
                                newY = len - 1;
                            }
                            case 1 -> {
                                newX = 0;
                                newY = x;
                            }
                            case 2 -> {
                                newY = newFace.board.length - 1;
                            }
                            case 3 -> throw new IllegalStateException();
                        }
                    }
                    case 3 -> {
                        switch(incomingDirection) {
                            case 0 -> {
                                newX = y;
                                newY = 0;
                            }
                            case 1 -> throw new IllegalStateException();
                            case 2 -> throw new IllegalStateException();
                            case 3 -> {
                                newX = newFace.board.length - 1;
                            }
                        }
                    }
                }
            }

            if(face.num == 3 && newFace.num == 0) {
                newX = len - x - 1;
                newY = 0;
                d = (d + 2) % 4;
            }

            if(face.num == 0 && newFace.num == 3) {
                newX = len - x - 1;
                newY = 0;
            }

            if(newFace.board[newX][newY] == '#') {
                return new State(face, x, y, d);
            }

            face = newFace;
            x = newX;
            y = newY;
            d = newD;

            amount--;
        }

        return new State(face, x, y, d);
    }

    private int getSideLength(char[][] board) {
        var cubeSurfaceArea = (int) Arrays.stream(board)
            .flatMap(row -> IntStream.range(0, row.length)
                .mapToObj(i -> row[i])
            )
            .filter(c -> c == '#' || c == '.')
            .count();

        var cubeFaceSurfaceArea = cubeSurfaceArea / 6;
        return (int) Math.sqrt(cubeFaceSurfaceArea);
    }

    @Override
    protected Input parseInput(String rawInput) {
        var parts = rawInput.split("\n\n");
        var board = parseBoard(parts[0]);
        var instructions = parseInstructions(parts[1]);
        return new Input(board, instructions);
    }

    private boolean isOutOfBounds(char[][] board, int x, int y) {
        int xLength = board.length;
        var yLength = board[0].length;

        return !(x >= 0 && x < xLength && y >= 0 && y < yLength);
    }

    private char[][] parseBoard(String raw) {
        raw = "\n" + raw + "\n ";
        var lines = raw.split("\n");
        var maxWidth = Arrays.stream(lines)
            .mapToInt(String::length)
            .max()
            .orElseThrow();

        return Arrays.stream(lines)
            .map(s -> " " + s + " ".repeat(maxWidth - s.length()) + " ")
            .map(String::toCharArray)
            .toArray(char[][]::new);
    }

    private List<String> parseInstructions(String raw) {
        var pattern = Pattern.compile("(\\d+|L|R)");

        var instructions = new ArrayList<String>();

        var matcher = pattern.matcher(raw);
        while(matcher.find()) {
            instructions.add(matcher.group());
        }

        return instructions;
    }

    record Face(int num, int x, int y, char[][] board, int[] connections) {

        public int getConnectingDirection(int face) {
            for(int i = 0; i < connections.length; i++) {
                if(connections[i] == face) {
                    return i;
                }
            }
            throw new IllegalArgumentException("Specified face does not connect");
        }

    }
    record Input(char[][] board, List<String> instructions) {}

}
