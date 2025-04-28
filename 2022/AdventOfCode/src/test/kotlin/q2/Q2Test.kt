package q2

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

class Q2Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q2.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(11767, part1(filePath!!))
    }

    @Test
    fun `part 2 works`() {
        val filePath = javaClass.getResource("/q2.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(13886, part2(filePath!!))
    }
}