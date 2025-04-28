package q9

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

class Q9Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q9.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(6175, part1(filePath!!))
    }

    @Test
    fun `part 2 works`() {
        val filePath = javaClass.getResource("/q9.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(2578, part2(filePath!!))
    }
}