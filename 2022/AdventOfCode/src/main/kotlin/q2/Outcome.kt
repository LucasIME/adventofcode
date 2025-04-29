package q2

enum class Outcome {
    LOSE, DRAW, WIN;

    companion object {
        fun toOutcome(s: String): Outcome {
            if (s == "X") {
                return LOSE
            }

            if (s == "Y") {
                return DRAW
            }

            return WIN
        }

        fun getScore(outcome: Outcome): Long {
            return when (outcome) {
                WIN -> 6
                DRAW -> 3
                LOSE -> 0
            }
        }
    }
}
