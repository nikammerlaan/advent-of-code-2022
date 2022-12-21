import com.github.nikammerlaan.aoc.data.Result;
import com.github.nikammerlaan.aoc.days.day21.Day21Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day21Test extends DayTest {

    public Day21Test() {
        super(new Day21Solution(), 21);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(152L, result.part1Result().result());
        assertEquals(301L, result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(10037517593724L, result.part1Result().result());
        assertEquals(3272260914328L, result.part2Result().result());
    }

}
