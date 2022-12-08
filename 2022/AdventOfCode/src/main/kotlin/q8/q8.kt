package q8

import java.io.File

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(input: List<List<Int>>): Int {
    val visibleSet: MutableSet<Pair<Int, Int>> = mutableSetOf()

    for (r in input.indices) {
        var leftRowMax = Int.MIN_VALUE
        for (c in input[r].indices) {
            val height = input[r][c]
            if (height > leftRowMax) {
                visibleSet.add(r to c)
                leftRowMax = height
            }
        }
    }

    for (r in input.indices) {
        var rightRowMax = Int.MIN_VALUE
        for (c in input[r].indices.reversed()) {
            val height = input[r][c]
            if (height > rightRowMax) {
                visibleSet.add(r to c)
                rightRowMax = height
            }
        }
    }

    for (c in input[0].indices) {
        var topColMax = Int.MIN_VALUE
        for (r in input.indices) {
            val height = input[r][c]
            if (height > topColMax) {
                visibleSet.add(r to c)
                topColMax = height
            }
        }
    }

    for (c in input[0].indices) {
        var bottomColMax = Int.MIN_VALUE
        for (r in input.indices.reversed()) {
            val height = input[r][c]
            if (height > bottomColMax) {
                visibleSet.add(r to c)
                bottomColMax = height
            }
        }
    }

    return visibleSet.size
}

private fun parseInput(): List<List<Int>> {
    return File("src/main/resources/q8.txt")
        .readLines()
        .map { it.map { it.toString().toInt() } }
}
