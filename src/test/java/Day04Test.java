import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day04.Day04Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04Test extends DayTest {

    public Day04Test() {
        super(new Day04Solution(), 4);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(2L, result.part1Result().result());
        assertEquals(4L, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(424L, result.part1Result().result());
        assertEquals(804L, result.part2Result().result());
    }

}
