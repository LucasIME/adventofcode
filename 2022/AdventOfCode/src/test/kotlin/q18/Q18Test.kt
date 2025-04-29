package q18

import junit.framework.TestCase.assertEquals
import java.nio.file.Paths
import kotlin.test.Test

class Q18Test {
    @Test
    fun `part 1 works`()  {
        val filePath = javaClass.getResource("/q18.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(3346, part1(filePath!!) )
    }

    @Test
    fun `part 2 works`()  {
        val filePath = javaClass.getResource("/q18.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(1980 , part2(filePath!!) )
    }
}