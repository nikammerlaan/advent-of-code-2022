package com.github.nikammerlaan.aoc.data;

import java.time.Duration;

public record TimeResult<E>(
    E result,
    Duration duration
) {}
