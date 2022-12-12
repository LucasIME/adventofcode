package q12

import java.io.File

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(maze: List<List<Char>>): Int {
    val starts = getAllStarts(maze)
    return starts.map { getDist(maze, it) }.min()
}

private fun getAllStarts(maze: List<List<Char>>): List<Pair<Int, Int>> {
    val resp = mutableListOf<Pair<Int, Int>>()
    for (i in maze.indices) {
        for (j in maze[i].indices) {
            if (maze[i][j] == 'S' || maze[i][j] == 'a') {
                resp.add(i to j)
            }
        }
    }

    return resp
}

private fun getDist(maze: List<List<Char>>, start: Pair<Int, Int>): Int {
    val q = ArrayDeque<Triple<Int, Int, Int>>()
    q.add(Triple(start.first, start.second, 0))
    val visited = mutableSetOf<Pair<Int, Int>>()
    while (q.isNotEmpty()) {
        val (row, col, dist) = q.removeFirst()

        if (maze[row][col] == 'E') {
            return dist
        }

        if (row to col in visited) {
            continue
        }

        visited.add(row to col)

        for (neigh in getNeighs(maze, row, col)) {
            q.add(Triple(neigh.first, neigh.second, dist + 1))
        }

    }

    return Int.MAX_VALUE
}

private fun getNeighs(maze: List<List<Char>>, row: Int, col: Int): List<Pair<Int, Int>> {
    val dirs = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)
    val resp = mutableListOf<Pair<Int, Int>>()
    for ((dr, dc) in dirs) {
        if (isValid(maze, row + dr, col + dc)
            && (getElevation(maze, row + dr, col + dc) - getElevation(maze, row, col) <= 1)) {
            resp.add(row + dr to col + dc)
        }
    }

    return resp
}

private fun getElevation(maze: List<List<Char>>, row: Int, col: Int): Int {
    return when (val char = maze[row][col]) {
        'S' -> 'a'.toInt()
        'E' -> 'z'.toInt()
        else -> char.toInt()
    }
}

private fun isValid(maze: List<List<Char>>, row: Int, col: Int): Boolean {
    return row >= 0 && row < maze.size && col >= 0 && col < maze[row].size
}

private fun parseInput(): List<List<Char>> {
    return File("src/main/resources/q12.txt")
        .readLines()
        .map { it.toList() }
}
