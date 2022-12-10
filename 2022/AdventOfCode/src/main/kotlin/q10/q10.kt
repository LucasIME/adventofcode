package q10

import java.io.File

fun main() {
    val input = parseInput()
    println(process(input))
}

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

private fun parseInput(): List<List<String>> {
    return File("src/main/resources/q10.txt")
        .readLines()
        .map { it.split(" ") }
}
