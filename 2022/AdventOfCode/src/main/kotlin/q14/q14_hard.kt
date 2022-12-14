package q14

import java.io.File
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun getMaxDepth(input: List<List<Pair<Int, Int>>>): Int {
    return input.flatMap { it.map { it.second } }
        .max()
}

private fun process(input: List<List<Pair<Int, Int>>>): Int {
    val cave = buildCave(input)
    var maxDepth = getMaxDepth(input)
    addFloor(cave, maxDepth)
    maxDepth += 2

    var sandCount = 0
    while (true) {
        sandCount += 1
        var sandPos = 500 to 0
        do {
            if (cave[sandPos.second + 1][sandPos.first] == '.') {
                sandPos = sandPos.first to sandPos.second + 1
            } else if (cave[sandPos.second + 1][sandPos.first - 1] == '.') {
                sandPos = sandPos.first - 1 to sandPos.second + 1
            } else if (cave[sandPos.second + 1][sandPos.first + 1] == '.') {
                sandPos = sandPos.first + 1 to sandPos.second + 1
            } else {
                cave[sandPos.second][sandPos.first] = 'O'
                break
            }
        } while (true)

        if (sandPos == 500 to 0) {
            break
        }
    }

    return sandCount
}

private fun addFloor(cave: List<MutableList<Char>>, maxDepth: Int) {
    for (col in cave[0].indices) {
        cave[maxDepth + 2][col] = '#'
    }
}

private fun buildCave(input: List<List<Pair<Int, Int>>>): List<MutableList<Char>> {
    val cave = List(200) { MutableList(1200) { '.' } }
    for (path in input) {
        var lastPoint: Pair<Int, Int>? = null
        for (point in path) {
            if (lastPoint != null) {
                val miniCol = min(point.first, lastPoint.first)
                val maxCol = max(point.first, lastPoint.first)
                val miniRow = min(point.second, lastPoint.second)
                val maxRow = max(point.second, lastPoint.second)
                for (col in miniCol..maxCol) {
                    for (row in miniRow..maxRow) {
                        cave[row][col] = '#'
                    }
                }
            }
            lastPoint = point
        }
    }
    return cave
}

private fun parseInput(): List<List<Pair<Int, Int>>> {
    return File("src/main/resources/q14.txt")
        .readLines()
        .map {
            it
                .split(" -> ")
                .map {
                    val commaSplit = it.split(",")
                    commaSplit[0].toInt() to commaSplit[1].toInt()
                }
        }
}
