package q10

import java.nio.file.Path
import kotlin.math.abs

private fun process(input: List<List<String>>): Int {
    var cycle = 1
    var x = 1
    val cyclesToAnalyze = setOf(20, 60, 100, 140, 180, 220)

    var resp = 0

    input.forEach {
        if (cycle in cyclesToAnalyze) {
            resp += cycle * x
        }

        val command = it[0]
        when (command) {
            "noop" -> cycle++
            else -> {
                val toAdd = it[1].toInt()
                cycle++
                if (cycle in cyclesToAnalyze) {
                    resp += cycle * x
                }
                cycle++
                x += toAdd
            }
        }
    }

    return resp
}

private fun parseInput(inputPath: Path): List<List<String>> {
    return inputPath.toFile()
        .readLines()
        .map { it.split(" ") }
}

fun part1(inputPath: Path): Int {
    val input = parseInput(inputPath)
    return process(input)
}

private fun process2(input: List<List<String>>): String {
    val crt = List(6) { MutableList(40) { '.' } }
    var cycle = 1
    var x = 1

    input.forEach {
        var cycleIndex = cycle - 1
        if (abs(cycleIndex % 40 - x) <= 1) {
            crt[cycleIndex / 40][cycleIndex % 40] = '#'
        }

        val command = it[0]
        when (command) {
            "noop" -> cycle++
            else -> {
                val toAdd = it[1].toInt()
                cycle++
                cycleIndex = cycle - 1
                if (abs(cycleIndex % 40 - x) <= 1) {
                    crt[cycleIndex / 40][cycleIndex % 40] = '#'
                }
                cycle++
                x += toAdd
            }
        }
    }

    return crt.joinToString("\n") { it.joinToString("") }
}

fun part2(inputPath: Path): String {
    val input = parseInput(inputPath)
    return process2(input)
}
