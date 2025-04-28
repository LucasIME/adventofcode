package q12

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

class Q12Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q12.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(504, part1(filePath!!))
    }

    @Test
    fun `part 2 works`() {
        val filePath = javaClass.getResource("/q12.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(500, part2(filePath!!))
    }
}