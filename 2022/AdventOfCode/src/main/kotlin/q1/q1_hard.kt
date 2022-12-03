package q1

import java.io.File
import java.util.PriorityQueue

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(input: List<String>): Long {
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

private fun parseInput(): List<String> {
    val f = File("src/main/resources/q1.txt")
    return f.readLines()
}
