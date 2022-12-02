package q2

import java.io.File

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(input: List<List<String>>): Long {
    var score = 0L

    input.forEach{
        val opponent = Move.toMove(it[0])
        val youOutcome = Outcome.toOutcome(it[1])
        val youMove = getMoveToOutcome(opponent, youOutcome)

        score += Move.getScore(youMove) + Outcome.getScore(youOutcome)
    }

    return score
}

private fun getMoveToOutcome(opponent: Move, youOutcome: Outcome): Move {
    return when(youOutcome) {
        Outcome.DRAW -> opponent
        Outcome.WIN -> Move.getWinMoveAgainst(opponent)
        Outcome.LOSE -> Move.getLoseMoveAgainst(opponent)
    }
}

enum class Outcome {
    LOSE, DRAW,WIN;

    companion object {
        fun toOutcome(s: String): Outcome{
            if (s == "X") {
                return LOSE
            }

            if (s == "Y")  {
                return DRAW
            }

            return WIN
        }

        fun getScore(outcome: Outcome): Long {
            return when(outcome) {
                WIN -> 6
                DRAW -> 3
                LOSE -> 0
            }
        }
    }
}

private fun parseInput(): List<List<String>> {
    val f = File("src/main/resources/q2.txt")
    return f.readLines().map { it.split(" ") }
}
