package co.vulpin.aoc.days.day07;

import co.vulpin.aoc.days.AbstractDaySolution;

import java.util.*;
import java.util.stream.LongStream;

public class Day07Solution extends AbstractDaySolution<Day07Solution.Directory> {

    private static final long DISK_SPACE = 70_000_000;
    private static final long UPDATE_SIZE = 30_000_000;

    @Override
    protected Object solvePart1(Directory input) {
        return input.part1(false);
    }

    @Override
    protected Object solvePart2(Directory input) {
        var freeSpace = DISK_SPACE - input.size();
        var neededSpace = UPDATE_SIZE - freeSpace;
        return input.part2(neededSpace);
    }

    @Override
    protected Directory parseInput(String rawInput) {
        var commands = rawInput.split("\\$");
        var iterator = Arrays.stream(commands).iterator();
        iterator.next(); // Skip leading empty split
        iterator.next(); // Skip first cd / to get into parsing dir state
        return process(iterator);
    }

    private Directory process(Iterator<String> commandIterator) {
        String ls = null;
        var dirMap = new HashMap<String, Directory>();

        while(commandIterator.hasNext()) {
            var command = commandIterator.next().trim();
            var split = command.split("\\s");
            switch(split[0]) {
                case "cd" -> {
                    if(split[1].equals("..")) {
                        var items = new ArrayList<Item>();
                        var rawItems = ls.split("\n");
                        for(int j = 1; j < rawItems.length; j++) {
                            var rawItem = rawItems[j];
                            var parts = rawItem.split(" ");
                            if(parts[0].equals("dir")) {
                                items.add(dirMap.get(parts[1]));
                            } else {
                                var file = new File(parts[1], Long.parseLong(parts[0]));
                                items.add(file);
                            }
                        }
                        return new Directory(items);
                    } else {
                        dirMap.put(split[1], process(commandIterator));
                    }
                }
                case "ls" -> ls = command;
            }
        }

        var items = new ArrayList<Item>();
        var rawItems = ls.split("\n");
        for(int j = 1; j < rawItems.length; j++) {
            var rawItem = rawItems[j];
            var parts = rawItem.split(" ");
            if(parts[0].equals("dir")) {
                items.add(dirMap.get(parts[1]));
            } else {
                var file = new File(parts[1], Long.parseLong(parts[0]));
                items.add(file);
            }
        }
        return new Directory(items);
    }

    interface Item {
        long size();
    }

    record Directory(List<Item> children, long size) implements Item {

        public Directory(List<Item> children) {
            this(
                children,
                children.stream()
                    .mapToLong(Item::size)
                    .sum()
            );
        }

        public long part1(boolean includeSelf) {
            var result = 0;

            if(includeSelf && size <= 100_000) {
                result += size;
            }

            result += children.stream()
                .filter(Directory.class::isInstance)
                .map(item -> (Directory) item)
                .mapToLong(dir -> dir.part1(true))
                .sum();

            return result;
        }

        public long part2(long target) {
            return LongStream.concat(
                LongStream.of(size),
                children.stream()
                    .filter(Directory.class::isInstance)
                    .map(item -> (Directory) item)
                    .mapToLong(dir -> dir.part2(target))
            ).filter(l -> l >= target).min().orElse(Long.MAX_VALUE);
        }

    }

    record File(String name, long size) implements Item {}

}
