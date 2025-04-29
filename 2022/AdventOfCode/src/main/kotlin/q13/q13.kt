package q13

import java.io.File
import java.nio.file.Path
import kotlin.math.min

private fun process(input: List<List<String>>): Int {
    return input.mapIndexed { i, s -> i to isInRightOrder(s[0], s[1]) }
        .filter { it.second == 1 }
        .sumOf { it.first + 1 }
}

// -1 not, 0 not decided, 1 is
private fun isInRightOrder(first: String, second: String): Int {
    val isFirstList = isList(first)
    val isSecondList = isList(second)

    if (!isFirstList && !isSecondList) {
        val firstVal = first.toLong()
        val secondVal = second.toLong()
        if (firstVal < secondVal) {
            return 1
        } else if (firstVal > secondVal) {
            return -1
        } else {
            return 0
        }
    } else if (isFirstList && isSecondList) {
        val fistList = getList(first)
        val secondList = getList(second)

        for (i in 0 until min(fistList.size, secondList.size)) {
            val comp = isInRightOrder(fistList[i], secondList[i])
            if (comp != 0) {
                return comp
            }
        }

        if (fistList.size < secondList.size) {
            return 1
        } else if (secondList.size < fistList.size) {
            return -1
        } else {
            return 0
        }

    } else if (isFirstList && !isSecondList) {
        return isInRightOrder(first, "[$second]")
    } else if (!isFirstList && isSecondList) {
        return isInRightOrder("[$first]", second)
    }

    throw Exception("Unexpected case")
}

private fun isList(s: String): Boolean {
    return s[0] == '['
}

private fun getList(s: String): List<String> {
    val resp = mutableListOf<String>()
    var level = 0
    var curString = StringBuilder()
    for (c in s.substring(1, s.length - 1)) {
        if (c == '[') {
            level++
        } else if (c == ']') {
            level--
        }

        if (c == ',' && level == 0) {
            resp.add(curString.toString())
            curString = StringBuilder()
        } else {
            curString.append(c)
        }
    }
    // There is no last comma, so we need to get the value on the buffer
    if (curString.isNotEmpty()) {
        resp.add(curString.toString())
    }

    return resp
}

private fun parseInput(inputPath: Path): List<List<String>> {
    return inputPath.toFile()
        .readLines()
        .chunked(3)
}

fun part1(inputPath: Path): Int {
    val input = parseInput(inputPath)
    return process(input)
}

val valuesToAdd = listOf("[[2]]", "[[6]]")

private fun process2(input: List<String>): Int {
    return input.sortedWith(CustomStringComparator())
        .asReversed()
        .mapIndexed { index, s -> index + 1 to s }
        .filter { valuesToAdd.contains(it.second) }
        .map { it.first }
        .reduce { a, b -> a * b }
}

private fun parseInput2(inputPath: Path): List<String> {
    val originalList = inputPath.toFile()
        .readLines()
        .filter { it.isNotEmpty() }
        .toMutableList()
    valuesToAdd.forEach { originalList.add(it) }
    return originalList
}

fun part2(inputPath: Path): Int {
    val input = parseInput2(inputPath)
    return process2(input)
}
