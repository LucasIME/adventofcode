package q2

internal enum class Move {
    ROCK, PAPER, SCISSOR;

    companion object {
        fun toMove(s: String): Move {
            if (s == "X" || s == "A") {
                return ROCK
            }

            if (s == "Y" || s == "B")  {
                return PAPER
            }

            return SCISSOR
        }

        fun getScore(move: Move): Long {
            return when(move) {
                ROCK -> 1
                PAPER -> 2
                SCISSOR -> 3
            }
        }

        fun getLoseMoveAgainst(move: Move): Move {
            return when(move) {
                ROCK -> SCISSOR
                SCISSOR -> PAPER
                PAPER -> ROCK
            }
        }

        fun getWinMoveAgainst(move: Move): Move {
            return when(move) {
                ROCK -> PAPER
                PAPER -> SCISSOR
                SCISSOR -> ROCK
            }
        }
    }
}