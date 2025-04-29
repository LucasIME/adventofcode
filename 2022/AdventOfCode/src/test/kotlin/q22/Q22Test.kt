package q22

import junit.framework.TestCase.assertEquals
import java.nio.file.Paths
import kotlin.test.Test

class Q22Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q22.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals(197160, part1(filePath!!))
    }
}