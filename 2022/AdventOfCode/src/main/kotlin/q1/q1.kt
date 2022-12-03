package q1

import java.io.File
import kotlin.math.max

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(input: List<String>): Long {
    var maxSoFar = Long.MIN_VALUE
    var curMax = Long.MIN_VALUE

    input.forEach {
        if (it == "") {
            maxSoFar = max(maxSoFar, curMax)
            curMax = 0
        } else {
            curMax += Integer.parseInt(it)
        }
    }

    return maxSoFar
}

private fun parseInput(): List<String> {
    val f = File("src/main/resources/q1.txt")
    return f.readLines()
}
