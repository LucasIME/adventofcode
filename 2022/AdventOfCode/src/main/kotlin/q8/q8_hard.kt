package q8

import java.io.File
import java.lang.Integer.max

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(input: List<List<Int>>): Int {
    var bestScenicScore = Int.MIN_VALUE

    for (r in input.indices) {
        for (c in input[r].indices) {
            val score = getScenicScore(input, r, c)
            bestScenicScore = max(score, bestScenicScore)
        }
    }

    return bestScenicScore
}

private fun getScenicScore(input: List<List<Int>>, r: Int, c: Int): Int {
    var upScore = 0
    for (ri in r - 1 downTo 0) {
        upScore++
        if (input[ri][c] >= input[r][c]) {
            break
        }
    }


    var bottomScore = 0
    for (ri in r + 1 until input.size) {
        bottomScore++
        if (input[ri][c] >= input[r][c]) {
            break
        }
    }


    var leftScore = 0
    for (ci in c - 1 downTo 0) {
        leftScore++
        if (input[r][ci] >= input[r][c]) {
            break
        }
    }

    var rightScore = 0
    for (ci in c + 1 until input[0].size) {
        rightScore++
        if (input[r][ci] >= input[r][c]) {
            break
        }
    }

    return upScore * bottomScore * rightScore * leftScore
}


private fun parseInput(): List<List<Int>> {
    return File("src/main/resources/q8.txt")
        .readLines()
        .map { it.map { it.toString().toInt() } }
}
