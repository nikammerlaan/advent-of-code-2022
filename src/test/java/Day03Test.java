import com.github.nikammerlaan.aoc.data.Result;
import com.github.nikammerlaan.aoc.days.day03.Day03Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03Test extends DayTest {

    public Day03Test() {
        super(new Day03Solution(), 3);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(157, result.part1Result().result());
        assertEquals(70, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(7908, result.part1Result().result());
        assertEquals(2838, result.part2Result().result());
    }

}
