import com.github.nikammerlaan.aoc.data.Result;
import com.github.nikammerlaan.aoc.days.day25.Day25Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day25Test extends DayTest {

    public Day25Test() {
        super(new Day25Solution(), 25);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals("2=-1=0", result.part1Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals("122-12==0-01=00-0=02", result.part1Result().result());
    }

}
