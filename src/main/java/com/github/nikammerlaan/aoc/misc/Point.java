package com.github.nikammerlaan.aoc.misc;

public record Point(
    int x,
    int y
) {

    public int getDistance(Point other) {
        return Math.abs(x - other.x()) + Math.abs(y - other.y());
    }

}
