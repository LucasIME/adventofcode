package q22

import java.io.File
import java.nio.file.Path

private fun process(input: Pair<List<List<Char>>, List<String>>): Int {
    val map = input.first
    val instructions = input.second


    val dirArray = listOf('>', 'V', '<', 'A')
    val dirToPosition = mapOf('>' to 0, 'V' to 1, '<' to 2, 'A' to 3)
    var pos = getStartPosition(input.first)
    var dir = '>'

    for (instruction in instructions) {
        if (instruction[0].isLetter()) {
            val rotate = instruction[0]
            dir = when (rotate) {
                'R' -> dirArray[Math.floorMod(dirToPosition[dir]!! + 1, dirArray.size)]
                'L' -> dirArray[Math.floorMod(dirToPosition[dir]!! - 1, dirArray.size)]
                else -> throw Exception("Unknown rotation")
            }

        } else {
            val steps = instruction.toInt()
            for (i in 0 until steps) {
                val newPos = getNextPos(map, pos, dir)
                pos = newPos
            }
        }
    }

    val password = 1000 * (pos.first + 1) + 4 * (pos.second + 1) + dirToPosition[dir]!!
    return password
}

private fun getNextPos(map: List<List<Char>>, pos: Pair<Int, Int>, dir: Char): Pair<Int, Int> {
    val immediatellyAfter = when (dir) {
        '>' -> pos.first to (pos.second + 1) % map[pos.first].size
        'V' -> (pos.first + 1) % map.size to pos.second
        '<' -> pos.first to Math.floorMod((pos.second - 1), map[pos.first].size)
        'A' -> Math.floorMod((pos.first - 1), map.size) to pos.second
        else -> throw Exception("Unknown dir to calculate next point")
    }
    val nextCell = map[immediatellyAfter.first][immediatellyAfter.second]
    if (nextCell == '.') {
        return immediatellyAfter
    }
    if (nextCell == '#') {
        return pos
    }

    // Now we have to wrap around and find first the first '.'.
    if (dir == '>') {
        for ((col, c) in map[pos.first].withIndex()) {
            if (c == '.') {
                return pos.first to col
            }

            if (c == '#') {
                return pos
            }
        }
    }

    if (dir == '<') {
        for ((col, c) in map[pos.first].withIndex().reversed()) {
            if (c == '.') {
                return pos.first to col
            }

            if (c == '#') {
                return pos
            }
        }
    }


    if (dir == 'V') {
        for (row in map.indices) {
            val c = map[row][pos.second]
            if (c == '.') {
                return row to pos.second
            }

            if (c == '#') {
                return pos
            }
        }
    }


    if (dir == 'A') {
        for (row in map.indices.reversed()) {
            val c = map[row][pos.second]
            if (c == '.') {
                return row to pos.second
            }

            if (c == '#') {
                return pos
            }
        }
    }

    throw Exception("Couldn't calculate next")
}

private fun getStartPosition(map: List<List<Char>>): Pair<Int, Int> {
    val row = 0
    for ((col, c) in map[0].withIndex()) {
        if (c == '.') {
            return row to col
        }
    }

    throw Exception("Didn't find dot in first row")
}

private fun parseInput(inputPath: Path): Pair<List<List<Char>>, List<String>> {
    val allLines = inputPath.toFile().readLines()
    val rawMap = allLines.subList(0, allLines.size - 2)
    val nRows = rawMap.size
    val nCols = rawMap.maxOf { it.length }

    val map = MutableList(nRows) { MutableList(nCols) { ' ' } }

    for ((row, rowStr) in rawMap.withIndex()) {
        for ((col, char) in rowStr.withIndex()) {
            map[row][col] = char
        }
    }

    val rawCommands = allLines.last()

    return map to toCommands(rawCommands)
}

private fun toCommands(rawCommands: String): List<String> {
    val resp = mutableListOf<String>()
    var strBuilder = StringBuilder()

    for (c in rawCommands) {
        if (c.isLetter()) {
            if (strBuilder.isNotEmpty()) {
                resp.add(strBuilder.toString())
                strBuilder = StringBuilder()
            }
            resp.add(c.toString())
        } else {
            strBuilder.append(c)
        }
    }

    if (strBuilder.isNotEmpty()) {
        resp.add(strBuilder.toString())
    }

    return resp
}


fun part1(inputPath: Path) : Int {
    val input = parseInput(inputPath)
    return process(input)
}
