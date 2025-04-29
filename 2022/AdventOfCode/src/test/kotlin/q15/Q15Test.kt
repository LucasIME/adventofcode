package q15

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

class Q15Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q15.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(4919281, part1(filePath!!))
    }

    @Test
    fun `part 2 works`() {
        val filePath = javaClass.getResource("/q15.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(12630143363767, part2(filePath!!))
    }
}