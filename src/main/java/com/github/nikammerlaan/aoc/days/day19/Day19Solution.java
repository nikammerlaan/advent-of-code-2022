package com.github.nikammerlaan.aoc.days.day19;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;

import java.util.*;

import static com.github.nikammerlaan.aoc.misc.MathUtils.getTriangleNumber;

public class Day19Solution extends AbstractDaySolution<List<Day19Solution.Blueprint>> {

    @Override
    protected Object solvePart1(List<Blueprint> input) {
        return input.stream()
            .mapToInt(blueprint -> blueprint.id() * getMaxGeodeCount(blueprint, 24))
            .sum();
    }

    @Override
    protected Object solvePart2(List<Blueprint> input) {
        return input.stream()
            .limit(3)
            .mapToInt(blueprint -> getMaxGeodeCount(blueprint, 32))
            .reduce((a, b) -> a * b)
            .orElseThrow();
    }

    private int getMaxGeodeCount(Blueprint blueprint, int minutes) {
        var cache = new HashMap<State, Integer>();
        var defaultState = new State(1, 0, 0, 0, 0, 0, 0, 0, minutes);
        return getMaxGeodeCount(blueprint, defaultState, 0, cache);
    }

    private int getMaxGeodeCount(Blueprint blueprint, State state, int max, Map<State, Integer> cache) {
        if(state.remainingMinutes() == 0) {
            return state.geodeCount();
        }

        int maxGeodes = (state.remainingMinutes * state.geodeRobotCount + getTriangleNumber(state.remainingMinutes)) + state.geodeCount;
        if(max >= maxGeodes) {
            return 0;
        }

        if(cache.containsKey(state)) {
            return cache.get(state);
        }

        if(state.canBuild(blueprint.oreRobotCost)) {
            max = Math.max(max, getMaxGeodeCount(blueprint, state.buildOreRobot(blueprint.oreRobotCost), max, cache));
        }

        if(state.canBuild(blueprint.clayRobotCost)) {
            max = Math.max(max, getMaxGeodeCount(blueprint, state.buildClayRobot(blueprint.clayRobotCost), max, cache));
        }

        if(state.canBuild(blueprint.obsidianRobotCost)) {
            max = Math.max(max, getMaxGeodeCount(blueprint, state.buildObsidianRobot(blueprint.obsidianRobotCost), max, cache));
        }

        if(state.canBuild(blueprint.geodeRobotCost)) {
            max = Math.max(max, getMaxGeodeCount(blueprint, state.buildGeodeRobot(blueprint.geodeRobotCost), max, cache));
        }

        max = Math.max(max, getMaxGeodeCount(blueprint, state.doNothing(), max, cache));

        cache.put(state, max);

        return max;
    }

    @Override
    protected List<Blueprint> parseInput(String rawInput) {
        return rawInput.lines()
            .map(this::parseBlueprint)
            .toList();
    }

    private Blueprint parseBlueprint(String raw) {
        var parts = raw.split("[: ]+");
        var numbers = Arrays.stream(parts)
            .mapMultiToInt((s, consumer) -> {
                try {
                    consumer.accept(Integer.parseInt(s));
                } catch(Exception ignored) {
                }
            })
            .toArray();
        return new Blueprint(
            numbers[0],
            new RobotCost(numbers[1], 0,          0),
            new RobotCost(numbers[2], 0,          0),
            new RobotCost(numbers[3], numbers[4], 0),
            new RobotCost(numbers[5], 0,          numbers[6])
        );
    }

    record State(int oreRobotCount, int clayRobotCount, int obsidianRobotCount, int geodeRobotCount, int oreCount, int clayCount, int obsidianCount, int geodeCount, int remainingMinutes) {

        public State doNothing() {
            return new State(
                oreRobotCount,
                clayRobotCount,
                obsidianRobotCount,
                geodeRobotCount,
                oreCount + oreRobotCount,
                clayCount + clayRobotCount,
                obsidianCount + obsidianRobotCount,
                geodeCount + geodeRobotCount,
                remainingMinutes - 1
            );
        }

        public State buildOreRobot(RobotCost cost) {
            return new State(
                oreRobotCount + 1,
                clayRobotCount,
                obsidianRobotCount,
                geodeRobotCount,
                oreCount + oreRobotCount - cost.oreCost,
                clayCount + clayRobotCount - cost.clayCost,
                obsidianCount + obsidianRobotCount - cost.obsidianCost,
                geodeCount + geodeRobotCount,
                remainingMinutes - 1
            );
        }

        public State buildClayRobot(RobotCost cost) {
            return new State(
                oreRobotCount,
                clayRobotCount + 1,
                obsidianRobotCount,
                geodeRobotCount,
                oreCount + oreRobotCount - cost.oreCost,
                clayCount + clayRobotCount - cost.clayCost,
                obsidianCount + obsidianRobotCount - cost.obsidianCost,
                geodeCount + geodeRobotCount,
                remainingMinutes - 1
            );
        }

        public State buildObsidianRobot(RobotCost cost) {
            return new State(
                oreRobotCount,
                clayRobotCount,
                obsidianRobotCount + 1,
                geodeRobotCount,
                oreCount + oreRobotCount - cost.oreCost,
                clayCount + clayRobotCount - cost.clayCost,
                obsidianCount + obsidianRobotCount - cost.obsidianCost,
                geodeCount + geodeRobotCount,
                remainingMinutes - 1
            );
        }

        public State buildGeodeRobot(RobotCost cost) {
            return new State(
                oreRobotCount,
                clayRobotCount,
                obsidianRobotCount,
                geodeRobotCount + 1,
                oreCount + oreRobotCount - cost.oreCost,
                clayCount + clayRobotCount - cost.clayCost,
                obsidianCount + obsidianRobotCount - cost.obsidianCost,
                geodeCount + geodeRobotCount,
                remainingMinutes - 1
            );
        }

        public boolean canBuild(RobotCost cost) {
            return
                clayCount >= cost.clayCost &&
                oreCount >= cost.oreCost &&
                obsidianCount >= cost.obsidianCost;
        }

    }

    record Blueprint(int id, RobotCost oreRobotCost, RobotCost clayRobotCost, RobotCost obsidianRobotCost, RobotCost geodeRobotCost) {}
    record RobotCost(int oreCost, int clayCost, int obsidianCost) {}

}
