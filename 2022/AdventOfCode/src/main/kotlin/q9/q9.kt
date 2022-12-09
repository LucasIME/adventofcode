package q9

import java.io.File
import java.lang.Exception
import kotlin.math.abs

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(input: List<Command>): Int {
    val visited = mutableSetOf<Pair<Int, Int>>()

    var headPos = 0 to 0
    var tailPos = 0 to 0
    visited.add(tailPos)

    input.forEach { command ->
        val dir = command.dir
        for (i in 1..command.n) {
            val newHeadPos = updatePositions(headPos, dir)
            val newTailPos = getNewTailPosition(tailPos, newHeadPos)
            tailPos = newTailPos
            headPos = newHeadPos
            visited.add(tailPos)
        }
    }
    return  visited.size
}

private fun getNewTailPosition(
    tailPos: Pair<Int, Int>,
    newHeadPos: Pair<Int, Int>
): Pair<Int, Int> {
    if (abs(newHeadPos.first - tailPos.first) <= 1
        && abs(newHeadPos.second - tailPos.second) <= 1) {
        return tailPos
    }

    if ((newHeadPos.second - tailPos.second) == 2) {
        return newHeadPos.first to newHeadPos.second - 1
    }

    if ((-newHeadPos.second + tailPos.second) == 2) {
        return newHeadPos.first to newHeadPos.second + 1
    }


    if ((newHeadPos.first- tailPos.first) == 2) {
        return newHeadPos.first - 1 to newHeadPos.second
    }

    if (( -newHeadPos.first + tailPos.first) == 2) {
        return newHeadPos.first + 1 to newHeadPos.second
    }

    throw Exception("unkonwn movement case")
}

private fun updatePositions(pos: Pair<Int, Int>, direction: Direction): Pair<Int, Int> {
    return when (direction) {
        Direction.U -> pos.first to pos.second + 1
        Direction.R -> pos.first + 1 to pos.second
        Direction.L -> pos.first - 1 to pos.second
        Direction.D -> pos.first to pos.second - 1
    }
}

private fun parseInput(): List<Command> {
    return File("src/main/resources/q9.txt")
        .readLines()
        .map { it.split(" ") }
        .map { Command(Direction.valueOf(it[0]), it[1].toInt()) }
}
