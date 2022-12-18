package q18

import java.io.File

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(input: List<Triple<Int, Int, Int>>): Int {
    val space: MutableSet<Triple<Int, Int, Int>> = mutableSetOf()
    var resp = 0
    for (cube in input) {
        resp += 6
        for (neigh in getNeighs(cube)) {
            if (neigh in space) {
                resp -= 2
            }
        }
        space.add(cube)
    }

    return resp
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
