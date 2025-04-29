package q1

import java.nio.file.Path
import java.util.PriorityQueue
import kotlin.math.max

private fun process(input: List<String>): Long {
    var maxSoFar = Long.MIN_VALUE
    var curMax = Long.MIN_VALUE

    input.forEach {
        if (it == "") {
            maxSoFar = max(maxSoFar, curMax)
            curMax = 0
        } else {
            curMax += Integer.parseInt(it)
        }
    }

    return maxSoFar
}

fun part1(inputPath: Path): Long {
    val input = inputPath.toFile().readLines()
    return process(input)
}

private fun process2(input: List<String>): Long {
    val heap = PriorityQueue(Comparator.naturalOrder<Long?>().reversed())
    var curVal = 0L
    input.spliterator()

    input.forEach {
        if (it == "") {
            heap.add(curVal)
            curVal = 0
        } else {
            curVal += Integer.parseInt(it)
        }
    }

    return heap.poll() + heap.poll() + heap.poll()
}

fun part2(inputPath: Path): Long {
    val input = inputPath.toFile().readLines()
    return process2(input)
}
