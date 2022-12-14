import com.github.nikammerlaan.aoc.data.Result;
import com.github.nikammerlaan.aoc.days.day14.Day14Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14Test extends DayTest {

    public Day14Test() {
        super(new Day14Solution(), 14);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(24, result.part1Result().result());
        assertEquals(93, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(757, result.part1Result().result());
        assertEquals(24943, result.part2Result().result());
    }

}
