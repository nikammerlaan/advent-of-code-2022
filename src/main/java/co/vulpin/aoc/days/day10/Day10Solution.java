package co.vulpin.aoc.days.day10;

import co.vulpin.aoc.days.AbstractDaySolution;

import java.util.List;
import java.util.function.Consumer;

public class Day10Solution extends AbstractDaySolution<List<Day10Solution.Instruction>> {

    @Override
    protected Object solvePart1(List<Instruction> input) {
        int result = 0;

        int x = 1;
        for(int i = 1; i < input.size(); i++) {
            if((i + 20) % 40 == 0) {
                result += x * i;
            }

            var instruction = input.get(i - 1);
            if(instruction instanceof AddxInstruction addx) {
                x += addx.amount();
            }
        }

        return result;
    }

    @Override
    protected Object solvePart2(List<Instruction> input) {
        var result = new StringBuilder();

        int x = 1;
        for(int i = 0; i < input.size(); i++) {
            if(i % 40 == 0) {
                result.append('\n');
            }

            if(Math.abs(i % 40 - x) <= 1) {
                result.append("#");
            } else {
                result.append(".");
            }

            var instruction = input.get(i);
            if(instruction instanceof AddxInstruction addx) {
                x += addx.amount();
            }
        }

        return result.toString();
    }

    @Override
    protected List<Instruction> parseInput(String rawInput) {
        return rawInput.lines()
            .map(raw -> {
                var parts = raw.split(" ");
                return switch(parts[0]) {
                    case "noop" -> new NoopInstruction();
                    case "addx" -> new AddxInstruction(Integer.parseInt(parts[1]));
                    default     -> throw new IllegalArgumentException();
                };
            })
            .mapMulti((Instruction instruction, Consumer<Instruction> consumer) -> {
                // To avoid weird multi cycle instructions, add a dummy noop instruction
                // before every addx instruction
                if(instruction instanceof AddxInstruction) {
                    consumer.accept(new NoopInstruction());
                }
                consumer.accept(instruction);
            })
            .toList();
    }

    interface Instruction {}
    record NoopInstruction() implements Instruction {}
    record AddxInstruction(int amount) implements Instruction {}

}
