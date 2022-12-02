import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day02.Day02Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02Test extends DayTest {

    public Day02Test() {
        super(new Day02Solution(), 2);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(15, result.part1Result().result());
        assertEquals(12, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(13005, result.part1Result().result());
        assertEquals(11373, result.part2Result().result());
    }

}
