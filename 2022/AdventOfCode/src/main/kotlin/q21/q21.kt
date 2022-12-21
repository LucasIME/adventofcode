package q21

import java.io.File
import java.util.stream.Collectors

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(input: Map<String, List<String>>): Long {
    return get(input, "root")
}

private fun get(input: Map<String, List<String>>, node: String): Long {
    if (node !in input) {
        throw Exception("Missing node")
    }

    if (input[node]!!.size == 1) {
        return input[node]!!.first().toLong()
    }

    val (arg1, operation, arg2) = input[node]!!

    return when (operation) {
        "+" -> get(input, arg1) + get(input, arg2)
        "-" -> get(input, arg1) - get(input, arg2)
        "*" -> get(input, arg1) * get(input, arg2)
        "/" -> get(input, arg1) / get(input, arg2)
        else -> throw Exception("Unrecognized operation")
    }
}

private fun parseInput(): Map<String, List<String>> {
    return File("src/main/resources/q21.txt")
        .readLines()
        .stream()
        .collect(Collectors.toMap({
            it.split(": ")[0]

        }, { it.split(": ")[1].split(" ") }))
}
