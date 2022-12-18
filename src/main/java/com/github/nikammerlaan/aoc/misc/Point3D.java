package com.github.nikammerlaan.aoc.misc;

import java.util.List;

public record Point3D(
    int x,
    int y,
    int z
) {

    public List<Point3D> getAdjacentPoints() {
        return List.of(
            new Point3D(x - 1, y, z),
            new Point3D(x + 1, y, z),
            new Point3D(x, y - 1, z),
            new Point3D(x, y + 1, z),
            new Point3D(x, y, z - 1),
            new Point3D(x, y, z + 1)
        );
    }

}
