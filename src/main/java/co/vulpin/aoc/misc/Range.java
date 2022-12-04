package co.vulpin.aoc.misc;

public record Range(int start, int end, long size) {

    public Range(int start, int end) {
        this(start, end, end - start + 1);
    }

    public boolean isInRange(int value) {
        return value >= start && value <= end;
    }

    public boolean intersects(Range other) {
        return isInRange(other.start()) || isInRange(other.end()) || other.isInRange(start()) || other.isInRange(end());
    }

    public boolean contains(Range other) {
        return isInRange(other.start()) && isInRange(other.end());
    }

}
