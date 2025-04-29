package q25

import java.nio.file.Path
import kotlin.math.max

private fun process(input: List<String>): String {
    return input.reduce(::sumSnafu)
}

private fun sumSnafu(n1: String, n2: String): String {
    val n1Reversed = n1.reversed()
    val n2Reversed = n2.reversed()

    val reversedResp = mutableListOf<Int>()
    var carry = 0
    for (i in 0 until max(n1.length, n2.length)) {
        val c1 = n1Reversed.getOrElse(i) { '0' }
        val c2 = n2Reversed.getOrElse(i) { '0' }

        var value = fromSnafu(c1) + fromSnafu(c2) + carry
        carry = 0
        while (value > 2) {
            value -= 5
            carry++
        }
        while (value < -2) {
            value += 5
            carry--
        }
        reversedResp.add(value)
    }

    if (carry != 0) {
        reversedResp.add(carry)
    }

    return reversedResp.reversed().map { toSnafuDigit(it) }.joinToString("")

}

private fun toSnafuDigit(i: Int): Char {
    return when (i) {
        2 -> '2'
        1 -> '1'
        0 -> '0'
        -1 -> '-'
        -2 -> '='
        else -> throw Exception("Unknown digit")
    }
}

private fun fromSnafu(c: Char): Int {
    return when (c) {
        '0' -> 0
        '1' -> 1
        '2' -> 2
        '-' -> -1
        '=' -> -2
        else -> throw Exception("Unrecognized digit")
    }
}

private fun parseInput(inputPath: Path): List<String> {
    return inputPath.toFile()
        .readLines()
}

fun part1(inputPath: Path): String {
    val input = parseInput(inputPath)
    return process(input)
}
