package q2

import java.io.File


fun main() {
    val input = parseInput()
    println(process(input))
}

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

private fun parseInput(): List<List<Move>> {
    val f = File("src/main/resources/q2.txt")
    return f.readLines().map { it.split(" ").map { s -> Move.toMove(s) } }
}
