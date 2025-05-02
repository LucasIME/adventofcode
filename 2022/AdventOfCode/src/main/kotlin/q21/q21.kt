package q21

import java.nio.file.Path
import java.util.stream.Collectors

private fun get(input: Map<String, List<String>>, node: String): Long {
    if (node !in input) {
        throw Exception("Missing node")
    }

    val cur = input[node]!!
    if (cur.size == 1) {
        return cur.first().toLong()
    }

    val (arg1, operation, arg2) = input[node]!!

    return when (operation) {
        "+" -> get(input, arg1) + get(input, arg2)
        "-" -> get(input, arg1) - get(input, arg2)
        "*" -> get(input, arg1) * get(input, arg2)
        "/" -> get(input, arg1) / get(input, arg2)
        "=" -> {
            val val1 = get(input, arg1)
            val val2 = get(input, arg2)
            val matches = val1 == val2

            if (matches) return 1 else -1
        }

        else -> throw Exception("Unrecognized operation")
    }
}

private fun parseInput(inputPath: Path): MutableMap<String, List<String>> {
    return inputPath.toFile()
        .readLines()
        .stream()
        .collect(Collectors.toMap({
            it.split(": ")[0]

        }, { it.split(": ")[1].split(" ") }))
}

private fun process(input: Map<String, List<String>>): Long {
    return get(input, "root")
}

fun part1(inputPath: Path): Long {
    val input = parseInput(inputPath)
    return process(input)
}

private fun guess(input: Map<String, List<String>>, node: String) {
    val (arg1, operation, arg2) = input[node]!!

    val leftDeps = deps(input, arg1)
    val rightDeps = deps(input, arg2)

    if (leftDeps.contains("humn")) {

    }

}

private fun deps(input: Map<String, List<String>>, node: String): Set<String> {
    val cur = input[node]!!

    if (cur.size == 1) {
        return emptySet()
    }

    return setOf(cur.first(), cur.last()) + deps(input, cur.first()) + deps(input, cur.last())
}

private fun getResultCandidate(input: MutableMap<String, List<String>>): Long {
    var low = (-1e15).toLong()
    var high = 1e15.toLong()
    while (true) {
        val i = low + (high - low) / 2
        input["humn"] = listOf(i.toString())
        val passes = get(input, "root") == 1L
        if (passes) {
            return i
        }
        val target = get(input, input["root"]!![2])
        val result = get(input, input["root"]!![0])
        if (result > target) {
            low = i - 1
        } else {
            high = i + 1
        }
    }
}

private fun process2(input: MutableMap<String, List<String>>): Long {
    val rootEntry = input["root"]!!
    input["root"] = listOf(rootEntry[0], "=", rootEntry[2])

    val initialTarget = getResultCandidate(input.toMutableMap())

    // Unclear why initial target doesn't work. Maybe precision lost on Integer division? But keep trying nearby numbers
    // till we find the lowest that works
    for (x in (initialTarget - 100) until (initialTarget + 101)) {
        input["humn"] = listOf(x.toString())
        val passes = get(input, "root") == 1L
        if (passes) {
            return x
        }
    }

    return initialTarget
}

fun part2(inputPath: Path): Long {
    val input = parseInput(inputPath)
    return process2(input)
}


