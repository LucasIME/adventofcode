package q19

import junit.framework.TestCase
import java.nio.file.Paths
import kotlin.test.Test

class Q19Test {

    @Test
    fun `ex 1 works`()  {
        val filePath = javaClass.getResource("/q19_ex.txt")?.toURI()?.let { Paths.get(it) }
        TestCase.assertEquals(33, part1(filePath!!))
    }

    @Test
    fun `part 1 works`()  {
        val filePath = javaClass.getResource("/q19.txt")?.toURI()?.let { Paths.get(it) }
        TestCase.assertEquals(-1, part1(filePath!!))
    }
}