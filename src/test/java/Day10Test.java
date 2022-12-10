import co.vulpin.aoc.data.Result;
import co.vulpin.aoc.days.day10.Day10Solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test extends DayTest {

    public Day10Test() {
        super(new Day10Solution(), 10);
    }

    @Override
    protected void assertExampleResult(Result result) {
        assertEquals(13140, result.part1Result().result());
        assertEquals("""
            
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....""",
            result.part2Result().result());
    }

    @Override
    protected void assertRealResult(Result result) {
        assertEquals(16060, result.part1Result().result());
        assertEquals("""
            
            ###...##...##..####.#..#.#....#..#.####.
            #..#.#..#.#..#.#....#.#..#....#..#.#....
            ###..#..#.#....###..##...#....####.###..
            #..#.####.#....#....#.#..#....#..#.#....
            #..#.#..#.#..#.#....#.#..#....#..#.#....
            ###..#..#..##..####.#..#.####.#..#.#....""",
            result.part2Result().result());
    }

}
