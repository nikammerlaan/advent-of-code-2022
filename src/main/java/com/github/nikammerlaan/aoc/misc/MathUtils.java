package com.github.nikammerlaan.aoc.misc;

public class MathUtils {

    public static int getTriangleNumber(int value) {
        if(value < 0) {
            throw new IllegalArgumentException();
        }
        return (value * value + value) / 2;
    }

}
