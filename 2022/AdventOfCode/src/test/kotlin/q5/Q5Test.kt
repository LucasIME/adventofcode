package q5

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

class Q5Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q5.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals("VQZNJMWTR", part1(filePath!!))
    }

    @Test
    fun `part 2 works`() {
        val filePath = javaClass.getResource("/q5.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals("NLCDCLVMQ", part2(filePath!!))
    }
}