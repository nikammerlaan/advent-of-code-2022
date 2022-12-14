import com.github.nikammerlaan.aoc.data.Result;
import com.github.nikammerlaan.aoc.days.day11.Day11Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test extends DayTest {

    public Day11Test() {
        super(new Day11Solution(), 11);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(10605L, result.part1Result().result());
        assertEquals(2713310158L, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(117624L, result.part1Result().result());
        assertEquals(16792940265L, result.part2Result().result());
    }

}
