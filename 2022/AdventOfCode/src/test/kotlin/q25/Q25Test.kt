package q25

import junit.framework.TestCase.assertEquals
import java.nio.file.Paths
import kotlin.test.Test

class Q25Test {
    @Test
    fun `part 1 works`() {
        val filePath = javaClass.getResource("/q25.txt")?.toURI()?.let { Paths.get(it) }
        assertEquals("2=--00--0220-0-21==1", part1(filePath!!))
    }
}