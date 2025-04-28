package q8

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

class Q8Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q8.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(1560, part1(filePath!!))
    }

    @Test
    fun `part 2 works`() {
        val filePath = javaClass.getResource("/q8.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(252000, part2(filePath!!))
    }
}