package q1

import junit.framework.TestCase.assertEquals
import java.nio.file.Paths
import kotlin.test.Test


class Q1Test {

    @Test
    fun `part 1 works`()  {
        val filePath = javaClass.getResource("/q1.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(70720, part1(filePath!!) )
    }


    @Test
    fun `part 2 works`()  {
        val filePath = javaClass.getResource("/q1.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(207148 , part2(filePath!!) )
    }
}