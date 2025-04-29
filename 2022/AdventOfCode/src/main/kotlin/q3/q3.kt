package q3

import java.nio.file.Path

private fun process(input: List<Pair<String, String>>): Int {
    return input.map { toCommonElement(it) }.sumOf { toVal(it) }
}

private fun toVal(c: Char): Int {
    if (c.isLowerCase()) {
        return c.code - 'a'.code + 1
    }

    return c.code - 'A'.code + 27
}

private fun toCommonElement(pair: Pair<String, String>): Char {
    return pair.first.toSet().intersect(pair.second.toSet()).single()
}

private fun parseInput(input: Path): List<Pair<String, String>> {
    return input.toFile().readLines().map { Pair(it.substring(0, it.length / 2), it.substring(it.length / 2)) }
}

fun part1(inputPath: Path): Int {
    val input = parseInput(inputPath)
    return process(input)
}

private fun parseInput2(input: Path): List<List<String>> {
    return input.toFile().readLines().chunked(3)
}

fun toCommonElement(strings: List<String>): Char {
    return strings.map { it.toSet() }.reduce { a, b -> a.intersect(b) }.single()
}

private fun process2(input: List<List<String>>): Int {
    return input.map { toCommonElement(it) }.sumOf { toVal(it) }
}

fun part2(inputPath: Path): Int {
    val input = parseInput2(inputPath)
    return process2(input)
}
