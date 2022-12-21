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

    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

}
