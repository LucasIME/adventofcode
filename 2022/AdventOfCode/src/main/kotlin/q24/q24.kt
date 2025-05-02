package q24

import java.nio.file.Path

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;
}

data class Position(val row: Int, val col: Int)

private fun nextPos(pos: Position, direction: Direction): Position {
    return when (direction) {
        Direction.UP -> Position(row = pos.row - 1, col = pos.col)
        Direction.DOWN -> Position(row = pos.row + 1, col = pos.col)
        Direction.LEFT -> Position(row = pos.row, col = pos.col - 1)
        Direction.RIGHT -> Position(row = pos.row, col = pos.col + 1)
    }
}

data class Blizzard(val position: Position, val direction: Direction) {
    fun next(grid: Grid, time: Int): Blizzard {
        val h = grid.rows - 2
        val w = grid.cols - 2

        var nextRow = position.row
        var nextCol = position.col

        when (direction) {
            Direction.UP -> {
                val relativeRow = position.row - 1
                val nextRelativeRow = ((relativeRow - time) % h + h) % h
                nextRow = nextRelativeRow + 1
            }
            Direction.DOWN -> {
                val relativeRow = position.row - 1
                val nextRelativeRow = ((relativeRow + time) % h + h) % h
                nextRow = nextRelativeRow + 1
            }
            Direction.LEFT -> {
                val relativeCol = position.col - 1
                val nextRelativeCol = ((relativeCol - time) % w + w) % w
                nextCol = nextRelativeCol + 1
            }
            Direction.RIGHT -> {
                val relativeCol = position.col - 1
                val nextRelativeCol = ((relativeCol + time) % w + w) % w
                nextCol = nextRelativeCol + 1
            }
        }

        return Blizzard(position = Position(row = nextRow, col = nextCol), direction)
    }

    fun after(time: Long, grid: Grid): Blizzard {
        val edgeLength = when (direction) {
            Direction.UP, Direction.DOWN -> grid.rows - 2
            Direction.LEFT, Direction.RIGHT -> grid.cols - 2
        }

        val extras = time % edgeLength
        return this.next(grid, extras.toInt())
    }
}

data class Grid(
    val rows: Int,
    val cols: Int,
    val blizzards: List<Blizzard>,
)

data class Entry(
    val start: Position,
    val goal: Position,
    val grid: Grid,
)

private fun parseInput(inputPath: Path): Entry {
    val lines = inputPath.toFile()
        .readLines()

    val startCol = lines[0].withIndex().first { it.value == '.' }.index
    val start = Position(row = 0, col = startCol)
    val endCol = lines.last().withIndex().first { it.value == '.' }.index
    val end = Position(row = lines.size - 1, col = endCol)

    val blizzards = mutableListOf<Blizzard>()
    lines.withIndex().forEach { line ->
        line.value.withIndex().forEach { col ->

            val blizz = when (col.value) {
                '<' -> Blizzard(position = Position(row = line.index, col = col.index), direction = Direction.LEFT)
                '>' -> Blizzard(position = Position(row = line.index, col = col.index), direction = Direction.RIGHT)
                '^' -> Blizzard(position = Position(row = line.index, col = col.index), direction = Direction.UP)
                'v' -> Blizzard(position = Position(row = line.index, col = col.index), direction = Direction.DOWN)
                else -> null
            }

            blizz?.let { blizzards.add(it) }
        }
    }

    val grid = Grid(rows = lines.size, cols = lines[0].length, blizzards = blizzards)

    return Entry(start = start, goal = end, grid = grid)
}

fun isValid(position: Position, grid: Grid): Boolean {
    if (position == Position(0, 1)
        || position == Position(grid.rows - 1, grid.cols - 2)) return true

    if (position.row <= 0 || position.col <= 0 || position.row >= grid.rows - 1 || position.col >= grid.cols - 1) {
        return false
    }

    val possibleHittingBlizz = grid.blizzards.filter { it.position.row == position.row || it.position.col == position.col }
    val hitBlizz = possibleHittingBlizz.any { it.position == position }

    return !hitBlizz
}

fun getValidNeighs(position: Position, grid: Grid, time: Long): List<Position> {
    val directions = Direction.entries
    val rawNeighs = directions.map { nextPos(position, it) }

    val futureBlizzards = grid.blizzards.map { it.after(time, grid) }
    val futureGrid = grid.copy(blizzards = futureBlizzards)

    return (rawNeighs + position).filter { isValid(it, futureGrid) }
}

fun getTimeDist(entry: Entry, timeStart: Long): Long {
    val queue = ArrayDeque<Pair<Position, Long>>()
    queue.add(entry.start to timeStart)
    val visited = mutableSetOf<Pair<Position, Long>>()

    while (!queue.isEmpty()) {
        val (curPos, steps) = queue.removeFirst()

        if (visited.contains(curPos to steps)) {
            continue
        }

        visited.add(curPos to steps)

        if (curPos == entry.goal) {
            return steps
        }

        val neighs = getValidNeighs(curPos, entry.grid, steps + 1)
        neighs.forEach { queue.addLast(it to steps + 1) }
    }

    throw Exception("No path possible")
}

fun process(entry: Entry): Long {
    return getTimeDist(entry, 0)
}

fun part1(inputPath: Path): Long {
    val input = parseInput(inputPath)
    return process(input)
}

fun process2(entry: Entry): Long {
    val timeThere = getTimeDist(entry, 0L)
    val timeBack = getTimeDist(entry.copy(
        start = entry.goal,
        goal = entry.start,
    ), timeThere)
    val thereAgain = getTimeDist(entry, timeBack)

    return thereAgain
}

fun part2(inputPath: Path): Long {
    val input = parseInput(inputPath)
    return process2(input)
}
