package q2

import java.nio.file.Path


private fun process(input: List<List<Move>>): Long {
    var score = 0L

    input.forEach {
        val opponent = it[0]
        val you = it[1]

        score += Move.getScore(you) + getScore(opponent, you)
    }

    return score
}

private fun getScore(opponent: Move, you: Move): Long {
    if (opponent == you) {
        return 3
    }

    if ((you == Move.SCISSOR && opponent == Move.PAPER)
        or (you == Move.PAPER && opponent == Move.ROCK)
        or (you == Move.ROCK && opponent == Move.SCISSOR)) {
        return 6
    }
    return 0
}

private fun parseInput(filePath: Path): List<List<Move>> {
    return filePath.toFile().readLines().map { it.split(" ").map { s -> Move.toMove(s) } }
}

fun part1(inputPath: Path): Long {
    val input = parseInput(inputPath)
    return process(input)
}

private fun process2(input: List<List<String>>): Long {
    var score = 0L

    input.forEach {
        val opponent = Move.toMove(it[0])
        val youOutcome = Outcome.toOutcome(it[1])
        val youMove = getMoveToOutcome(opponent, youOutcome)

        score += Move.getScore(youMove) + Outcome.getScore(youOutcome)
    }

    return score
}

private fun getMoveToOutcome(opponent: Move, youOutcome: Outcome): Move {
    return when (youOutcome) {
        Outcome.DRAW -> opponent
        Outcome.WIN -> Move.getWinMoveAgainst(opponent)
        Outcome.LOSE -> Move.getLoseMoveAgainst(opponent)
    }
}

private fun parseInput2(filePath: Path): List<List<String>> {
    return filePath.toFile().readLines().map {
        it.split(" ")
    }
}

fun part2(inputPath: Path): Long {
    val input = parseInput2(inputPath)
    return process2(input)
}
