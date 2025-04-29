package q21

import java.io.File
import java.nio.file.Path
import java.util.stream.Collectors

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

private fun parseInput(inputPath: Path): Map<String, List<String>> {
    return inputPath.toFile()
        .readLines()
        .stream()
        .collect(Collectors.toMap({
            it.split(": ")[0]

        }, { it.split(": ")[1].split(" ") }))
}

fun part1(inputPath: Path): Long {
    val input = parseInput(inputPath)
    return process(input)
}


