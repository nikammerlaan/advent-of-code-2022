package co.vulpin.aoc.days.day13;

import java.util.List;
import java.util.stream.IntStream;

record ListPacket(List<Packet> children) implements Packet {

    @Override
    public String toString() {
        return children.toString();
    }

    @Override
    public int compareTo(Packet other) {
        var a = this;
        return switch(other) {
            case ListPacket b -> IntStream.range(0, Math.min(a.children().size(), b.children().size()))
                .map(i -> {
                    var aChild = a.children().get(i);
                    var bChild = b.children().get(i);
                    return aChild.compareTo(bChild);
                })
                .filter(i -> i != 0)
                .findFirst()
                .orElse(Integer.compare(a.children().size(), b.children().size()));
            case IntPacket b -> {
                var fakeListPacket = new ListPacket(List.of(b));
                yield a.compareTo(fakeListPacket);
            }
        };
    }

}
