package com.github.nikammerlaan.aoc.misc;

public record Box3D(Range x, Range y, Range z, long volume) {

    public Box3D(Range x, Range y, Range z) {
        this(x, y, z, x.size() * y.size() * z.size());
    }

    public boolean contains(Point3D point) {
        return x.isInRange(point.x()) && y.isInRange(point.y()) && z.isInRange(point.z());
    }

}
