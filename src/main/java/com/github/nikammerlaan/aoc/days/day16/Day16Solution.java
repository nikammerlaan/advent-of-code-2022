package com.github.nikammerlaan.aoc.days.day16;

import com.github.nikammerlaan.aoc.days.AbstractDaySolution;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16Solution extends AbstractDaySolution<Day16Solution.Input> {

    private static final String START = "AA";

    @Override
    protected Object solvePart1(Input input) {
        return solve(input.valves(), input.functioningValves(), input.shortestPaths(), START, 30);
    }

    @Override
    protected Object solvePart2(Input input) {
        var potentialValves = new ArrayList<>(input.functioningValves());

        return IntStream.range(0, (int) Math.pow(2, potentialValves.size()))
            .map(i -> {
                var potentialValvesA = new HashSet<String>();
                var potentialValvesB = new HashSet<String>();

                // Split up the potential valves by using the binary form of `i`.
                // If the jth digit is 1, it's added to A. Otherwise, it's added to B.
                int i0 = i;
                for(int j = 0; j < potentialValves.size(); j++) {
                    if((i0 & 1) == 1) {
                        potentialValvesA.add(potentialValves.get(j));
                    } else {
                        potentialValvesB.add(potentialValves.get(j));
                    }
                    i0 >>= 1;
                }

                var a = solve(input.valves(), potentialValvesA, input.shortestPaths(), START, 26);
                var b = solve(input.valves(), potentialValvesB, input.shortestPaths(), START, 26);

                return a + b;
            })
            .max()
            .orElseThrow();
    }

    private int solve(Map<String, Valve> valves,
                      Set<String> remainingValves,
                      ShortestPathAlgorithm<String, DefaultEdge> shortestPaths,
                      String currentValve,
                      int remainingMinutes) {
        if(remainingMinutes <= 0) {
            return 0;
        }

        var currentFlowRate = valves.get(currentValve).flowRate();
        if(remainingValves.contains(currentValve)) {
            var newRemainingValves = new HashSet<>(remainingValves);
            newRemainingValves.remove(currentValve);
            return (currentFlowRate * (remainingMinutes - 1)) + solve(valves, newRemainingValves, shortestPaths, currentValve, remainingMinutes - 1);
        }

        return remainingValves.stream()
            .mapToInt(valve -> {
                var shortestPath = (int) shortestPaths.getPathWeight(currentValve, valve);
                if(shortestPath > remainingMinutes) {
                    return 0;
                }
                return solve(valves, remainingValves, shortestPaths, valve, remainingMinutes - shortestPath);
            })
            .max()
            .orElse(0);
    }

    @Override
    protected Input parseInput(String rawInput) {
        var valves = rawInput.lines()
            .map(line -> {
                var parts = line.split("[ ;=]", 12);
                var name = parts[1];
                var flowRate = Integer.parseInt(parts[5]);
                var rawConnections = parts[11];
                var c = rawConnections.split(", ");
                Arrays.sort(c);
                return new Valve(name, flowRate, Arrays.asList(c));
            })
            .toList();
        var valvesByName = valves.stream()
            .collect(Collectors.toMap(Valve::name, Function.identity()));

        var functioningValves = valves.stream()
            .filter(valve -> valve.flowRate() > 0)
            .map(Valve::name)
            .collect(Collectors.toSet());

        var shortestPaths = calculateShortestPaths(valves);

        return new Input(valvesByName, shortestPaths, functioningValves);
    }

    private ShortestPathAlgorithm<String, DefaultEdge> calculateShortestPaths(List<Valve> valves) {
        var graph = createGraph(valves);
        return new FloydWarshallShortestPaths<>(graph);
    }

    private Graph<String, DefaultEdge> createGraph(List<Valve> valves) {
        var graph = new DefaultUndirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        for(var valve : valves) {
            graph.addVertex(valve.name());
        }
        for(var valve : valves) {
            for(var connection : valve.connections()) {
                graph.addEdge(valve.name(), connection);
            }
        }
        return graph;
    }

    record Input(
        Map<String, Valve> valves,
        ShortestPathAlgorithm<String, DefaultEdge> shortestPaths,
        Set<String> functioningValves
    ) {}
    record Valve(String name, int flowRate, List<String> connections) {}

}
