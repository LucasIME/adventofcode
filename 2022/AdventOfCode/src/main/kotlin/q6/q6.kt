package q6

import java.io.File

fun main() {
    val input = parseInput()
    println(process(input))
}

private const val EMPTY = '$'

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
        run {
            if (it in set) {
                return false
            }
            set.add(it)
        }
    }

    return EMPTY !in set
}

private fun parseInput(): String {
    return File("src/main/resources/q6.txt").readLines().first()
}
