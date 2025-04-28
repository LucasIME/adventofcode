package q9

import java.io.File
import java.lang.Exception
import java.nio.file.Path
import kotlin.math.abs


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

private fun parseInput(inputPath: Path): List<Command> {
    return inputPath.toFile()
        .readLines()
        .map { it.split(" ") }
        .map { Command(Direction.valueOf(it[0]), it[1].toInt()) }
}

fun part1(inputPath: Path): Int {
    val input = parseInput(inputPath)
    return process(input)
}

private fun getNewTailPosition2(
    tailPos: Pair<Int, Int>,
    newHeadPos: Pair<Int, Int>
): Pair<Int, Int> {
    if (abs(newHeadPos.first - tailPos.first) <= 1
        && abs(newHeadPos.second - tailPos.second) <= 1) {
        return tailPos
    }

    if (abs(newHeadPos.second - tailPos.second) == 2
        && abs(newHeadPos.first - tailPos.first) == 2) {

        return tailPos.first + (newHeadPos.first - tailPos.first) / 2 to tailPos.second + (newHeadPos.second - tailPos.second) / 2
    }

    if ((newHeadPos.second - tailPos.second) == 2) {
        return newHeadPos.first to newHeadPos.second - 1
    }

    if ((-newHeadPos.second + tailPos.second) == 2) {
        return newHeadPos.first to newHeadPos.second + 1
    }


    if ((newHeadPos.first - tailPos.first) == 2) {
        return newHeadPos.first - 1 to newHeadPos.second
    }

    if ((-newHeadPos.first + tailPos.first) == 2) {
        return newHeadPos.first + 1 to newHeadPos.second
    }

    throw Exception("unkonwn movement case")
}

private fun process2(input: List<Command>): Int {
    val visited = mutableSetOf<Pair<Int, Int>>()

    val rope = MutableList(10) { 0 to 0 }
    visited.add(rope[9])

    input.forEach { command ->
        val dir = command.dir
        for (i in 1..command.n) {
            for (ropeI in rope.indices) {
                if (ropeI == 0) {
                    rope[0] = updatePositions(rope[0], dir)
                } else {
                    val newPosOfPreviousNode = rope[ropeI - 1]
                    val newPosOfCurrentNode = getNewTailPosition2(rope[ropeI], newPosOfPreviousNode)
                    rope[ropeI] = newPosOfCurrentNode
                }
            }
            visited.add(rope[9])
        }
    }
    println(visited)
    return visited.size
}

fun part2(inputPath: Path): Int {
    val input = parseInput(inputPath)
    return process2(input)
}
