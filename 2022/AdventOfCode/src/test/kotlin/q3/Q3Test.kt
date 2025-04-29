package q3

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

class Q3Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q3.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(8085, part1(filePath!!))
    }

    @Test
    fun `part 2 works`() {
        val filePath = javaClass.getResource("/q3.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(2515, part2(filePath!!))
    }
}