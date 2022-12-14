import com.github.nikammerlaan.aoc.data.Result;
import com.github.nikammerlaan.aoc.days.day05.Day05Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05Test extends DayTest {

    public Day05Test() {
        super(new Day05Solution(), 5);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals("CMZ", result.part1Result().result());
        assertEquals("MCD", result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals("WSFTMRHPP", result.part1Result().result());
        assertEquals("GSLCMFBRP", result.part2Result().result());
    }

}
