package q3

import java.io.File

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(input: List<List<String>>): Int {
    return input.map { toCommonElement(it) }.sumOf { toVal(it) }
}

fun toCommonElement(strings: List<String>): Char {
    return strings.map { it.toSet() }.reduce { a, b -> a.intersect(b) }.single()
}

private fun toVal(c: Char): Int {
    if (c.isLowerCase()) {
        return c.code - 'a'.code + 1
    }

    return c.code - 'A'.code + 27
}

private fun parseInput(): List<List<String>> {
    val f = File("src/main/resources/q3.txt")
    return f.readLines().chunked(3)
}
