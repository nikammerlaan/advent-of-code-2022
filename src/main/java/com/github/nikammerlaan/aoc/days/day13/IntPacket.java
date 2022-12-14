package com.github.nikammerlaan.aoc.days.day13;

import java.util.List;

record IntPacket(int value) implements Packet {

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int compareTo(Packet o) {
        return switch(o) {
            case IntPacket b -> Integer.compare(value, b.value());
            case ListPacket b -> {
                var fakeListPacket = new ListPacket(List.of(this));
                yield fakeListPacket.compareTo(b);
            }
        };
    }

}
