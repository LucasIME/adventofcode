package q16

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

class Q16Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q16.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(1641, part1(filePath!!))
    }

    @Test
    fun `part 2 works`() {
        val filePath = javaClass.getResource("/q16.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(2261, part2(filePath!!))
    }
}