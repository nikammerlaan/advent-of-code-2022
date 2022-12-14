import com.github.nikammerlaan.aoc.data.Result;
import com.github.nikammerlaan.aoc.days.day08.Day08Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08Test extends DayTest {

    public Day08Test() {
        super(new Day08Solution(), 8);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(21, result.part1Result().result());
        assertEquals(8, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(1698, result.part1Result().result());
        assertEquals(672280, result.part2Result().result());
    }

}
