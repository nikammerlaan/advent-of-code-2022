package co.vulpin.aoc.days.day11;

import co.vulpin.aoc.days.AbstractDaySolution;

import java.util.*;
import java.util.function.Function;

public class Day11Solution extends AbstractDaySolution<List<Day11Solution.Monkey>> {

    @Override
    protected Object solvePart1(List<Monkey> input) {
        return solve(input, 20, true);

    }

    @Override
    protected Object solvePart2(List<Monkey> input) {
        return solve(input, 10_000, false);
    }

    private long solve(List<Monkey> input, int roundCount, boolean divideBy3) {
        var items = new HashMap<Integer, LinkedList<Long>>();
        for(var monkey : input) {
            items.put(monkey.number(), new LinkedList<>(monkey.startingItems()));
        }

        // To keep the results manageable, we build a number that is a multiple
        // of every test. Then, we can modulus all the items by this number without
        // losing divisibility
        int compound = input.stream()
            .map(Monkey::test)
            .distinct()
            .reduce((a, b) -> a * b)
            .orElseThrow();

        var inspectionCounts = new long[input.size()];

        for(int i = 0; i < roundCount; i++) {
            for(var monkey : input) {
                var monkeyItems = items.get(monkey.number());
                while(!monkeyItems.isEmpty()) {
                    var item = monkeyItems.poll();
                    item = monkey.function().apply(item);
                    if(divideBy3) {
                        item /= 3;
                    }
                    item %= compound;
                    if(item % monkey.test() == 0) {
                        items.get(monkey.trueTestMonkey()).add(item);
                    } else {
                        items.get(monkey.falseTestMonkey()).add(item);
                    }
                    inspectionCounts[monkey.number()]++;
                }
            }
        }

        Arrays.sort(inspectionCounts);
        return inspectionCounts[inspectionCounts.length - 1] * inspectionCounts[inspectionCounts.length - 2];
    }

    @Override
    protected List<Monkey> parseInput(String rawInput) {
        return Arrays.stream(rawInput.split("\n\n"))
            .map(this::parseMonkey)
            .toList();
    }

    private Monkey parseMonkey(String input) {
        var lines = input.split("\n");
        var number = Integer.parseInt(lines[0].split("[ :]+")[1]);
        var items = Arrays.stream(lines[1].split(": ")[1].split(", "))
            .map(Long::parseLong)
            .toList();
        var operation = lines[2].split("\\s+");
        var operationOperator = operation[5];
        var operationRawParam = operation[6];
        Function<Long, Long> operationFunction = x -> {
            long param;
            if(operationRawParam.equals("old")) {
                param = x;
            } else {
                param = Integer.parseInt(operationRawParam);
            }
            return switch(operationOperator) {
                case "+" -> x + param;
                case "*" -> x * param;
                default -> throw new IllegalArgumentException();
            };
        };
        var test = Integer.parseInt(lines[3].split("\\s+")[4]);
        var trueTestMonkey = Integer.parseInt(lines[4].split("\\s+")[6]);
        var falseTestMonkey = Integer.parseInt(lines[5].split("\\s+")[6]);
        return new Monkey(number, items, operationFunction, test, falseTestMonkey, trueTestMonkey);
    }

    record Monkey(int number, List<Long> startingItems, Function<Long, Long> function, int test, int falseTestMonkey, int trueTestMonkey) {}

}
