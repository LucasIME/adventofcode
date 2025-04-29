package q17

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

class Q17Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q17.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(3161, part1(filePath!!))
    }

    @Test
    fun `part 2 works`() {
        val filePath = javaClass.getResource("/q17.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(1575931232076, part2(filePath!!))
    }
}