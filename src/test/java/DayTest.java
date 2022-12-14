import com.github.nikammerlaan.aoc.data.Result;
import com.github.nikammerlaan.aoc.days.DaySolution;
import com.github.nikammerlaan.aoc.misc.InputUtils;
import org.junit.jupiter.api.Test;

public abstract class DayTest {

    private final DaySolution daySolution;
    private final int dayNumber;

    public DayTest(DaySolution daySolution, int dayNumber) {
        this.daySolution = daySolution;
        this.dayNumber = dayNumber;
    }

    @Test
    public void test_exampleInput() {
        var input = InputUtils.getExampleInput(dayNumber);
        var result = daySolution.calculateAnswers(input);
        assertExampleResult(result);
    }

    protected abstract void assertExampleResult(Result result);

    @Test
    public void test_realInput() {
        var input = InputUtils.getRealInput(dayNumber);
        var result = daySolution.calculateAnswers(input);
        assertRealResult(result);
    }

    protected abstract void assertRealResult(Result result);

}
