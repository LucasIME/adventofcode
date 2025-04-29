package q10

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

class Q10Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q10.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(14340, part1(filePath!!))
    }

    @Test
    fun `part 2 works`() {
        val filePath = javaClass.getResource("/q10.txt")?.toURI()?.let { Paths.get(it) }
        // PAJCBHP
        val expected = """
            ###...##..###....##..##..###..#..#.###..
            #..#.#..#.#..#....#.#..#.#..#.#..#.#..#.
            #..#.#..#.#..#....#.#....###..####.#..#.
            ###..####.###.....#.#....#..#.#..#.###..
            #....#..#.#....#..#.#..#.#..#.#..#.#....
            #....#..#.#.....##...##..###..#..#.#....
        """.trimIndent()
        assertEquals(expected, part2(filePath!!))
    }
}