package co.vulpin.aoc.days.day07;

import co.vulpin.aoc.days.AbstractDaySolution;

import java.util.*;

public class Day07Solution extends AbstractDaySolution<Day07Solution.Directory> {

    private static final long DISK_SPACE = 70_000_000;
    private static final long UPDATE_SIZE = 30_000_000;
    private static final long DELETION_CANDIDATE_THRESHOLD = 100_000;

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
        var iterator = Arrays.stream(commands)
            .map(String::trim)
            .skip(1) // Skip leading empty split
            .skip(1) // Skip first cd / to get into parsing dir state
            .map(this::parseCommand)
            .iterator();
        return process(iterator);
    }

    private Command parseCommand(String raw) {
        var splits = raw.split("\n", 2);
        var output = splits.length == 2 ? splits[1] : null;
        splits = splits[0].split("\\s");
        var command = splits[0];
        var param = splits.length == 2 ? splits[1] : null;
        return new Command(
            command,
            param,
            output
        );
    }

    private Directory process(Iterator<Command> commandIterator) {
        String ls = null;
        var dirMap = new HashMap<String, Directory>();

        while(commandIterator.hasNext()) {
            var command = commandIterator.next();
            switch(command.command()) {
                case "cd" -> {
                    if(command.param().equals("..")) {
                        return buildDirectory(ls, dirMap);
                    } else {
                        dirMap.put(command.param(), process(commandIterator));
                    }
                }
                case "ls" -> ls = command.output();
            }
        }

        return buildDirectory(ls, dirMap);
    }

    private Directory buildDirectory(String ls, Map<String, Directory> dirMap) {
        var items = ls.lines()
            .map(rawItem -> {
                var parts = rawItem.split(" ");
                if(parts[0].equals("dir")) {
                    return dirMap.get(parts[1]);
                } else {
                    return new File(parts[1], Long.parseLong(parts[0]));
                }
            })
            .map(Item.class::cast)
            .toList();
        return new Directory(items);
    }

    interface Item {
        long size();
    }

    record Directory(List<Item> children, List<Directory> directoryChildren, long size) implements Item {

        public Directory(List<Item> children) {
            this(
                children,
                children.stream()
                    .filter(Directory.class::isInstance)
                    .map(Directory.class::cast)
                    .toList(),
                children.stream()
                    .mapToLong(Item::size)
                    .sum()
            );
        }

        public long part1(boolean includeSelf) {
            var result = 0;

            if(includeSelf && size <= DELETION_CANDIDATE_THRESHOLD) {
                result += size;
            }

            result += directoryChildren.stream()
                .mapToLong(dir -> dir.part1(true))
                .sum();

            return result;
        }

        public long part2(long targetSize) {
            long bestDirSize = Long.MAX_VALUE;

            if(size > targetSize) {
                bestDirSize = size;
            }

            for(var child : directoryChildren) {
                var bestDirSizeChild = child.part2(targetSize);
                if(bestDirSizeChild < bestDirSize) {
                    bestDirSize = bestDirSizeChild;
                }
            }

            return bestDirSize;
        }

    }

    record File(String name, long size) implements Item {}

    record Command(String command, String param, String output) {}

}
