package q10

import java.io.File
import kotlin.math.abs

fun main() {
    val input = parseInput()
    printCrt(process(input))
}

private fun printCrt(crt: List<List<Char>>) {
    crt.map { it.joinToString("") }.forEach { println(it) }
}

private fun process(input: List<List<String>>): List<List<Char>> {
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

    return crt
}

private fun parseInput(): List<List<String>> {
    return File("src/main/resources/q10.txt")
        .readLines()
        .map { it.split(" ") }
}
