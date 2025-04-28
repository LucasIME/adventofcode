package q4

import java.nio.file.Path

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

private fun parseInput(input: Path): List<Pair<IntRange, IntRange>> {
    return input.toFile().readLines()
        .map { singleEntry ->
            singleEntry.split(",")
                .map { it.split("-") }
                .map { (start, end) -> start.toInt()..end.toInt() }
        }
        .map { Pair(it[0], it[1]) }
}

fun part1(inputPath: Path): Int {
    val input = parseInput(inputPath)
    return process(input)
}

private fun doesRangeOverlap(range1: IntRange, range2: IntRange): Boolean {
    if ((range2.first >= range1.first && range2.first <= range1.last)
        || (range1.first >= range2.first && range1.first <= range2.last)) {
        return true;
    }

    return false
}

private fun process2(input: List<Pair<IntRange, IntRange>>): Int {
    return input.filter { doesRangeOverlap(it.first, it.second) }.size
}

fun part2(inputPath: Path): Int {
    val input = parseInput(inputPath)
    return process2(input)
}
