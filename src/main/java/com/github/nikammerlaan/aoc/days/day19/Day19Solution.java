package com.github.nikammerlaan.aoc.days.day19;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;

import java.util.*;

import static com.github.nikammerlaan.aoc.misc.MathUtils.*;

public class Day19Solution extends AbstractDaySolution<List<Day19Solution.Blueprint>> {

    @Override
    protected Object solvePart1(List<Blueprint> input) {
        return input.parallelStream()
            .mapToInt(blueprint -> blueprint.id() * getMaxGeodeCount(blueprint, 24))
            .sum();
    }

    @Override
    protected Object solvePart2(List<Blueprint> input) {
        return input.parallelStream()
            .limit(3)
            .mapToInt(blueprint -> getMaxGeodeCount(blueprint, 32))
            .reduce((a, b) -> a * b)
            .orElseThrow();
    }

    private int getMaxGeodeCount(Blueprint blueprint, int minutes) {
        var cache = new HashMap<State, Integer>();
        var state = new State(blueprint, 1, 0, 0, 0, 0, 0, 0, 0, minutes);
        return getMaxGeodeCount(state, 0, cache);
    }

    private int getMaxGeodeCount(State state, int max, Map<State, Integer> cache) {
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

        if(state.shouldBuildGeodeRobot()) {
            max = Math.max(max, getMaxGeodeCount(state.buildGeodeRobot(), max, cache));
        } else {
            if(state.shouldBuildObsidianRobot()) {
                max = Math.max(max, getMaxGeodeCount(state.buildObsidianRobot(), max, cache));
            }

            if(state.shouldBuildClayRobot()) {
                max = Math.max(max, getMaxGeodeCount(state.buildClayRobot(), max, cache));
            }

            if(state.shouldBuildOreRobot()) {
                max = Math.max(max, getMaxGeodeCount(state.buildOreRobot(), max, cache));
            }

            max = Math.max(max, getMaxGeodeCount(state.doNothing(), max, cache));
        }

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

    record State(
        Blueprint blueprint,
        int oreRobotCount,
        int clayRobotCount,
        int obsidianRobotCount,
        int geodeRobotCount,
        int oreCount,
        int clayCount,
        int obsidianCount,
        int geodeCount,
        int remainingMinutes
    ) {

        public State doNothing() {
            return new State(
                blueprint,
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

        public boolean shouldBuildOreRobot() {
            return canBuild(blueprint.oreRobotCost) && oreRobotCount < blueprint.maxCost.oreCost;
        }

        public State buildOreRobot() {
            var cost = blueprint.clayRobotCost;
            return new State(
                blueprint,
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

        public boolean shouldBuildClayRobot() {
            return canBuild(blueprint.clayRobotCost) && clayRobotCount < blueprint.maxCost.clayCost;
        }

        public State buildClayRobot() {
            var cost = blueprint.clayRobotCost;
            return new State(
                blueprint,
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

        public boolean shouldBuildObsidianRobot() {
            return canBuild(blueprint.obsidianRobotCost) && obsidianRobotCount < blueprint.maxCost.obsidianCost;
        }

        public State buildObsidianRobot() {
            var cost = blueprint.obsidianRobotCost;
            return new State(
                blueprint,
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

        public boolean shouldBuildGeodeRobot() {
            return canBuild(blueprint.geodeRobotCost);
        }

        public State buildGeodeRobot() {
            var cost = blueprint.geodeRobotCost;
            return new State(
                blueprint,
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

    record Blueprint(
        int id,
        RobotCost oreRobotCost,
        RobotCost clayRobotCost,
        RobotCost obsidianRobotCost,
        RobotCost geodeRobotCost,
        RobotCost maxCost
    ) {

        public Blueprint(
            int id,
            RobotCost oreRobotCost,
            RobotCost clayRobotCost,
            RobotCost obsidianRobotCost,
            RobotCost geodeRobotCost
        ) {
            this(
                id,
                oreRobotCost,
                clayRobotCost,
                obsidianRobotCost,
                geodeRobotCost,
                new RobotCost(
                    max(oreRobotCost.oreCost,      clayRobotCost.oreCost,      obsidianRobotCost.oreCost,      geodeRobotCost.oreCost),
                    max(oreRobotCost.clayCost,     clayRobotCost.clayCost,     obsidianRobotCost.clayCost,     geodeRobotCost.clayCost),
                    max(oreRobotCost.obsidianCost, clayRobotCost.obsidianCost, obsidianRobotCost.obsidianCost, geodeRobotCost.obsidianCost)
                )
            );
        }

    }

    record RobotCost(int oreCost, int clayCost, int obsidianCost) {}
    
}
