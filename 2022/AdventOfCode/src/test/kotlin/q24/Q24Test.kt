package q24

import junit.framework.TestCase.assertEquals
import java.nio.file.Paths
import kotlin.test.Test

class Q24Test {
    @Test
    fun `part 1 ex works`()  {
        val filePath = javaClass.getResource("/q24_ex.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(18, part1(filePath!!))
    }

    @Test
    fun `part 1 works`()  {
        val filePath = javaClass.getResource("/q24.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(277, part1(filePath!!))
    }

    @Test
    fun `part 2 ex works`()  {
        val filePath = javaClass.getResource("/q24_ex.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(54, part2(filePath!!))
    }

    @Test
    fun `part 2 works`()  {
        val filePath = javaClass.getResource("/q24.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(877, part2(filePath!!))
    }
}