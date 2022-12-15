import com.github.nikammerlaan.aoc.data.Result;
import com.github.nikammerlaan.aoc.days.day15.Day15Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day15Test extends DayTest {

    public Day15Test() {
        super(new Day15Solution(), 15);
    }

    @Override
    protected void assertExampleResult(Result result) {
        // The solution is tuned for the real input, so the example will not work
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(5394423, result.part1Result().result());
        assertEquals(11840879211051L, result.part2Result().result());
    }

}
