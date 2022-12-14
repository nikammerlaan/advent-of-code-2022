package com.github.nikammerlaan.aoc.misc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class InputUtils {

    private static final String INPUT_PATH_FORMAT = "/com/github/nikammerlaan/aoc/inputs/day%02d/%s.txt";

    public static String getRealInput(int dayNumber) {
        return getInput(dayNumber, "real");
    }

    public static String getExampleInput(int dayNumber) {
        return getInput(dayNumber, "example");
    }

    public static String getInput(int dayNumber, String name) {
        try {
            var rawPath = String.format(INPUT_PATH_FORMAT, dayNumber, name);
            var stream = InputUtils.class.getResourceAsStream(rawPath);
            return new BufferedReader(new InputStreamReader(stream))
                .lines()
                .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
