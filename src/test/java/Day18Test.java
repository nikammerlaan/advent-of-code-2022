import com.github.nikammerlaan.aoc.data.Result;
import com.github.nikammerlaan.aoc.days.day18.Day18Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day18Test extends DayTest {

    public Day18Test() {
        super(new Day18Solution(), 18);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(64L, result.part1Result().result());
        assertEquals(58L, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(4322L, result.part1Result().result());
        assertEquals(2516L, result.part2Result().result());
    }

}
