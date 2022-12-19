package q18

import java.io.File

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(input: List<Triple<Int, Int, Int>>): Int {
    val outsidePieces = getOutsidePieces(input)

    var resp = 0
    for (piece in input) {
        for (neigh in getNeighs(piece)) {
            if (neigh in outsidePieces) {
                resp++
            }
        }
    }

    return resp
}

private fun getOutsidePieces(
    input: List<Triple<Int, Int, Int>>,
): Set<Triple<Int, Int, Int>> {
    val inputSet = input.toSet()

    val minX = input.map { it.first }.min()
    val maxX = input.map { it.first }.max()
    val minY = input.map { it.second }.min()
    val maxY = input.map { it.second }.max()
    val minZ = input.map { it.third }.min()
    val maxZ = input.map { it.third }.max()

    val q = ArrayDeque<Triple<Int, Int, Int>>()
    q.add(Triple(minX - 1, minY - 1, minZ - 1))

    val outsidePieces: MutableSet<Triple<Int, Int, Int>> = mutableSetOf()
    val visited: MutableSet<Triple<Int, Int, Int>> = mutableSetOf()

    while (q.isNotEmpty()) {
        val cur = q.removeFirst()

        if (cur in visited) {
            continue
        }

        visited.add(cur)

        if (cur.first >= minX - 1
            && cur.first <= maxX + 1
            && cur.second >= minY - 1
            && cur.second <= maxY + 1
            && cur.third >= minZ - 1
            && cur.third <= maxZ + 1
            && cur !in inputSet
        ) {
            outsidePieces.add(cur)
            for (neigh in getNeighs(cur)) {
                q.add(neigh)
            }
        }
    }

    return outsidePieces
}

private fun getNeighs(cube: Triple<Int, Int, Int>): List<Triple<Int, Int, Int>> {
    val dirs = listOf(
        Triple(-1, 0, 0),
        Triple(1, 0, 0),
        Triple(0, -1, 0),
        Triple(0, 1, 0),
        Triple(0, 0, -1),
        Triple(0, 0, 1)
    )

    return dirs.map { Triple(cube.first + it.first, cube.second + it.second, cube.third + it.third) }
}

private fun parseInput(): List<Triple<Int, Int, Int>> {
    return File("src/main/resources/q18.txt")
        .readLines()
        .map { it.split(",") }
        .map { Triple(it[0].toInt(), it[1].toInt(), it[2].toInt()) }
}
