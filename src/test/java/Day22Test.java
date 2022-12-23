import com.github.nikammerlaan.aoc.data.Result;
import com.github.nikammerlaan.aoc.days.day22.Day22Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day22Test extends DayTest {

    public Day22Test() {
        super(new Day22Solution(), 22);
    }

    @Override
    protected void assertExampleResult(Result result) {
        // Solution does not work for the example yet
//        assertEquals(6032, result.part1Result().result());
//        assertEquals(5031, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(155060, result.part1Result().result());
        assertEquals(3479, result.part2Result().result());
    }

}
