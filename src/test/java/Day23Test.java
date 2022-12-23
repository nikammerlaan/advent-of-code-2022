import com.github.nikammerlaan.aoc.data.Result;
import com.github.nikammerlaan.aoc.days.day23.Day23Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day23Test extends DayTest {

    public Day23Test() {
        super(new Day23Solution(), 23);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(110, result.part1Result().result());
        assertEquals(20, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(3864, result.part1Result().result());
        assertEquals(946, result.part2Result().result());
    }

}
