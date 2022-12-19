package q17

import java.io.File
import kotlin.math.max

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(input: String): Int {
    val space: MutableSet<Pair<Int, Int>> = mutableSetOf()
    val piecesOrder = listOf(Shapes.Horizontal, Shapes.Plus, Shapes.InvertedL, Shapes.Vertical, Shapes.Square)
    val leftEdge = 0
    var topEdge = 0

    var time = 0
    for (i in 0 until 2022) {
        var pivot = leftEdge + 3 to topEdge + 4
        val shape = piecesOrder[i % piecesOrder.size]
        var doesCollideOnDrop = false

        while (!doesCollideOnDrop) {
            pivot = tryMoveHorizontally(input, time, pivot, shape, space)

            val newPivotCandidate = pivot.first to pivot.second - 1
            val newPoints = getPoints(newPivotCandidate, shape)
            for (point in newPoints) {
                if (isOccupied(space, point)) {
                    doesCollideOnDrop = true
                }
            }

            time++

            if (doesCollideOnDrop) {
                for (point in getPoints(pivot, shape)) {
                    space.add(point)
                    topEdge = max(topEdge, point.second)
                }
            } else {
                pivot = newPivotCandidate
            }
        }
    }

    return topEdge
}

private fun tryMoveHorizontally(
    input: String,
    time: Int,
    pivot: Pair<Int, Int>,
    shape: Shapes,
    space: MutableSet<Pair<Int, Int>>
): Pair<Int, Int> {
    val direction = input[time % input.length]
    val newPivot = when (direction) {
        '<' -> pivot.first - 1 to pivot.second
        '>' -> pivot.first + 1 to pivot.second
        else -> throw Exception("Unexpected input")
    }

    val newPoints = getPoints(newPivot, shape)
    var doesCollide = false
    for (point in newPoints) {
        if (isOccupied(space, point)) {
            doesCollide = true
        }
    }

    return if (doesCollide) pivot else newPivot
}

private fun isOccupied(space: MutableSet<Pair<Int, Int>>, point: Pair<Int, Int>): Boolean {
    if (point.first <= 0
        || point.first >= 8
        || point.second <= 0) {
        return true
    }

    return point in space;
}

private fun getPoints(pivot: Pair<Int, Int>, piece: Shapes): List<Pair<Int, Int>> {
    return when (piece) {
        Shapes.Horizontal -> listOf(0, 1, 2, 3).map { pivot.first + it to pivot.second }
        Shapes.Plus -> listOf(
            1 to 0,
            0 to 1,
            1 to 1,
            2 to 1,
            1 to 2
        ).map { it.first + pivot.first to it.second + pivot.second }

        Shapes.InvertedL -> listOf(
            0 to 0,
            1 to 0,
            2 to 0,
            2 to 1,
            2 to 2
        ).map { it.first + pivot.first to it.second + pivot.second }

        Shapes.Vertical -> listOf(0, 1, 2, 3).map { pivot.first to pivot.second + it }
        Shapes.Square -> listOf(
            0 to 0,
            0 to 1,
            1 to 0,
            1 to 1
        ).map { it.first + pivot.first to it.second + pivot.second }
    }
}

private fun parseInput(): String {
    return File("src/main/resources/q17.txt")
        .readLines().first()
}
