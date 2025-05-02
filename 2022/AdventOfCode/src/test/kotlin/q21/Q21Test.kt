package q21

import junit.framework.TestCase.assertEquals
import java.nio.file.Paths
import kotlin.test.Test

class Q21Test {
    @Test
    fun `part 1 works`()  {
        val filePath = javaClass.getResource("/q21.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(93813115694560, part1(filePath!!) )
    }

    @Test
    fun `part 2 works`()  {
        val filePath = javaClass.getResource("/q21.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(3910938071092, part2(filePath!!) )
    }
}