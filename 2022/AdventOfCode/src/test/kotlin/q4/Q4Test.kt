package q4

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals


class Q4Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q4.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(571, part1(filePath!!))
    }

    @Test
    fun `part 2 works`() {
        val filePath = javaClass.getResource("/q4.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(917, part2(filePath!!))
    }
}