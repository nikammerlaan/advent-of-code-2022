import com.github.nikammerlaan.aoc.data.Result;
import com.github.nikammerlaan.aoc.days.day17.Day17Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day17Test extends DayTest {

    public Day17Test() {
        super(new Day17Solution(), 17);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(3068L, result.part1Result().result());
        assertEquals(1514285714288L, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(3184L, result.part1Result().result());
        assertEquals(1577077363915L, result.part2Result().result());
    }

}
