package q4

import java.io.File

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(input: List<Pair<IntRange, IntRange>>): Int {
    return input.filter { doesOneRangeFullyContainTheOther(it.first, it.second) }.size
}

private fun doesOneRangeFullyContainTheOther(range1: IntRange, range2: IntRange): Boolean {
    if ((range1.first <= range2.first && range1.last >= range2.last)
        || (range2.first <= range1.first && range2.last >= range1.last)) {
        return true
    }

    return false
}

private fun parseInput(): List<Pair<IntRange, IntRange>> {
    val f = File("src/main/resources/q4.txt")
    return f.readLines()
        .map { singleEntry ->
            singleEntry.split(",")
                .map { it.split("-") }
                .map { (start, end) -> start.toInt()..end.toInt() }
        }
        .map { Pair(it[0], it[1]) }
}
