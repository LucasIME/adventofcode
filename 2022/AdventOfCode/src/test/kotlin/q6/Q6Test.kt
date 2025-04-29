package q6

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

class Q6Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q6.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(1850, part1(filePath!!))
    }

    @Test
    fun `part 2 works`() {
        val filePath = javaClass.getResource("/q6.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(2823, part2(filePath!!))
    }
}
