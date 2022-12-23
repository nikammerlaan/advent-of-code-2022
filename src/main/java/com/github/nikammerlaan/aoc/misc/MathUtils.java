package com.github.nikammerlaan.aoc.misc;

import java.util.stream.IntStream;

public class MathUtils {

    public static int getTriangleNumber(int value) {
        if(value < 0) {
            throw new IllegalArgumentException();
        }
        return (value * value + value) / 2;
    }

    public static int max(int... values) {
        return IntStream.of(values).max().orElseThrow();
    }

    public static int getManhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

}
