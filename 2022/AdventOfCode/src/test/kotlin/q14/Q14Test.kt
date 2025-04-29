package q14

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

class Q14Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q14.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(779, part1(filePath!!))
    }

    @Test
    fun `part 2 works`() {
        val filePath = javaClass.getResource("/q14.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(27426, part2(filePath!!))
    }
}