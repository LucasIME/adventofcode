package q13

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

class Q13Test {

    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q13.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(5588, part1(filePath!!))
    }

    @Test
    fun `part 2 works`() {
        val filePath = javaClass.getResource("/q13.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(23958, part2(filePath!!))
    }
}