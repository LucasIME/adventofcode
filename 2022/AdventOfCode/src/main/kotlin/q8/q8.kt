package q8

import java.lang.Integer.max
import java.nio.file.Path

private fun process(input: List<List<Int>>): Int {
    val visibleSet: MutableSet<Pair<Int, Int>> = mutableSetOf()

    for (r in input.indices) {
        var leftRowMax = Int.MIN_VALUE
        for (c in input[r].indices) {
            val height = input[r][c]
            if (height > leftRowMax) {
                visibleSet.add(r to c)
                leftRowMax = height
            }
        }
    }

    for (r in input.indices) {
        var rightRowMax = Int.MIN_VALUE
        for (c in input[r].indices.reversed()) {
            val height = input[r][c]
            if (height > rightRowMax) {
                visibleSet.add(r to c)
                rightRowMax = height
            }
        }
    }

    for (c in input[0].indices) {
        var topColMax = Int.MIN_VALUE
        for (r in input.indices) {
            val height = input[r][c]
            if (height > topColMax) {
                visibleSet.add(r to c)
                topColMax = height
            }
        }
    }

    for (c in input[0].indices) {
        var bottomColMax = Int.MIN_VALUE
        for (r in input.indices.reversed()) {
            val height = input[r][c]
            if (height > bottomColMax) {
                visibleSet.add(r to c)
                bottomColMax = height
            }
        }
    }

    return visibleSet.size
}

private fun parseInput(inputPath: Path): List<List<Int>> {
    return inputPath.toFile()
        .readLines()
        .map { list ->
            list.map { it.toString().toInt() }
        }
}

fun part1(inputPath: Path): Int {
    val input = parseInput(inputPath)
    return process(input)
}

private fun process2(input: List<List<Int>>): Int {
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

fun part2(inputPath: Path): Int {
    val input = parseInput(inputPath)
    return process2(input)
}

