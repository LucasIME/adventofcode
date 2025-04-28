package q7

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

class Q7Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q7.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(2104783, part1(filePath!!))
    }

    @Test
    fun `part 2 works`() {
        val filePath = javaClass.getResource("/q7.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(5883165, part2(filePath!!))
    }
}