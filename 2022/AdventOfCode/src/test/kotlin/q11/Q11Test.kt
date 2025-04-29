package q11

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

class Q11Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q11.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(107822, part1(filePath!!))
    }

    @Test
    fun `part 2 works`() {
        val filePath = javaClass.getResource("/q11.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(27267163742, part2(filePath!!))
    }
}