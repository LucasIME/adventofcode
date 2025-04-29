package q20

import junit.framework.TestCase.assertEquals
import java.nio.file.Paths
import kotlin.test.Test


class Q20Test {
    @Test
    fun `part 1 works`()  {
        val filePath = javaClass.getResource("/q20.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(16533, part1(filePath!!) )
    }

    @Test
    fun `part 2 works`()  {
        val filePath = javaClass.getResource("/q20.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(4789999181006 , part2(filePath!!) )
    }
}