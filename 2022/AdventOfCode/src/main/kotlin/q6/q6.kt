package q6

import java.nio.file.Path

private const val EMPTY = '$'
private const val WINDOW_SIZE = 14

private fun process(input: String): Int {
    val window = CharArray(4) { EMPTY }
    var windowEndIndex = -1
    for (i in input.indices) {
        val c = input[i]
        window[i % 4] = c
        if (areAllDifferent(window)) {
            windowEndIndex = i
            break
        }
    }

    return windowEndIndex + 1
}

private fun areAllDifferent(window: CharArray): Boolean {
    val set = mutableSetOf<Char>()
    window.forEach {
        if (it in set) {
            return false
        }
        set.add(it)
    }

    return EMPTY !in set
}

private fun parseInput(inputPath: Path): String {
    return inputPath.toFile().readLines().first()
}

fun part1(inputPath: Path): Int {
    val input = parseInput(inputPath)
    return process(input)
}

private fun process2(input: String): Int {
    val window = CharArray(WINDOW_SIZE) { EMPTY }
    var windowEndIndex = -1
    for (i in input.indices) {
        val c = input[i]
        window[i % WINDOW_SIZE] = c
        if (areAllDifferent(window)) {
            windowEndIndex = i
            break
        }
    }

    return windowEndIndex + 1
}

fun part2(inputPath: Path): Int {
    val input = parseInput(inputPath)
    return process2(input)
}
