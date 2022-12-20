import com.github.nikammerlaan.aoc.data.Result;
import com.github.nikammerlaan.aoc.days.day20.Day20Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day20Test extends DayTest {

    public Day20Test() {
        super(new Day20Solution(), 20);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(3L, result.part1Result().result());
        assertEquals(1623178306L, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(11123L, result.part1Result().result());
        assertEquals(4248669215955L, result.part2Result().result());
    }

}
