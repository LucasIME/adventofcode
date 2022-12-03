package q3

import java.io.File
import java.util.stream.Collectors

fun main() {
    val input = parseInput()
    println(process(input))
}

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
    val firstCharSet = pair.first.chars().mapToObj { it.toChar() }.collect(Collectors.toSet())
    val secondCharSet = pair.second.chars().mapToObj { it.toChar() }.collect(Collectors.toSet())
    return firstCharSet.intersect(secondCharSet).single()
}

private fun parseInput(): List<Pair<String, String>> {
    val f = File("src/main/resources/q3.txt")
    return f.readLines().map { Pair(it.substring(0, it.length / 2), it.substring(it.length / 2)) }
}
