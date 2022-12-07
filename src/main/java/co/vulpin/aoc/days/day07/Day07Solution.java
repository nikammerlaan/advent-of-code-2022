package co.vulpin.aoc.days.day07;

import co.vulpin.aoc.days.AbstractDaySolution;

import java.util.*;
import java.util.function.Consumer;
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
        var stack = new LinkedList<String>();
        var commands = rawInput.split("\\$");
        var directories = new HashMap<List<String>, Directory>();
        var lsHistory = new HashMap<List<String>, String>();

        Consumer<String> buildChildren = (ls) -> {
            var items = new ArrayList<Item>();
            var rawItems = ls.split("\n");
            for(int j = 1; j < rawItems.length; j++) {
                var rawItem = rawItems[j];
                var parts = rawItem.split(" ");
                if(parts[0].equals("dir")) {
                    var childPath = new ArrayList<>();
                    childPath.add(parts[1]);
                    childPath.addAll(stack);
                    var directory = directories.get(childPath);
                    if(directory != null){
                        items.add(directory);
                    } else {
                        return;
                    }
                } else {
                    var file = new File(parts[1], Long.parseLong(parts[0]));
                    items.add(file);
                }
            }
            directories.put(new ArrayList<>(stack), new Directory(stack.peek(), items));
        };

        for(int i = 1; i < commands.length; i++) {
            var command = commands[i].trim();
            var split = command.split("\\s");
            switch(split[0]) {
                case "cd" -> {
                    switch(split[1]) {
                        case "/" -> stack.clear();
                        case ".." -> stack.pop();
                        default -> stack.push(split[1]);
                    }
                }
                case "ls" -> lsHistory.put(new ArrayList<>(stack), command);
            }

            var ls = lsHistory.get(stack);
            if(ls != null) {
                buildChildren.accept(ls);
            }
        }

        while(!stack.isEmpty()) {
            stack.pop();
            buildChildren.accept(lsHistory.get(stack));
        }

        return directories.get(List.of());
    }

    interface Item {
        long size();
    }

    record Directory(String name, List<Item> children, long size) implements Item {

        public Directory(String name, List<Item> children) {
            this(
                name,
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
