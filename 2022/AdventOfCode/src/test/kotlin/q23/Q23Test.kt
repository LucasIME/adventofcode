package q23

import junit.framework.TestCase.assertEquals
import java.nio.file.Paths
import kotlin.test.Test

class Q23Test {
    @Test
    fun `part 1 works`()  {
        val filePath = javaClass.getResource("/q23.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(4249 , part1(filePath!!) )
    }

    @Test
    fun `part 2 works`()  {
        val filePath = javaClass.getResource("/q23.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(980, part2(filePath!!) )
    }
}