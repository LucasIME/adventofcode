package q5

import java.io.File
import java.util.Stack

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(game: Game): String {
    var stacks = game.stacks
    for (move in game.moves) {
        stacks = updateStacks(stacks, move)
    }
    return joinTopElements(stacks)
}

private fun updateStacks(stacks: List<Stack<Char>>, move: Triple<Int, Int, Int>): List<Stack<Char>> {
    val tempStack = Stack<Char>()
    for (i in 1..move.first) {
        tempStack.push(stacks[move.second - 1].pop())
    }
    for (i in 1..move.first) {
        stacks[move.third - 1].push(tempStack.pop())
    }

    return stacks
}

private fun joinTopElements(stacks: List<Stack<Char>>): String {
    return stacks.map { it.peek() }.joinToString("")
}

private fun parseInput(): Game {
    val f = File("src/main/resources/q5.txt")

    val fileText = f.readText()
    val splits = fileText.split("\n\n")

    val stackRaw = splits[0].split("\n").dropLast(1)
    val movesRaw = splits[1].split("\n").dropLast(1)


    val numberOfStacks = (stackRaw[0].length - 2) / 3
    val stacks = MutableList<Stack<Char>>(numberOfStacks) { _ -> Stack() }

    stackRaw
        .forEach { s ->
            s.forEachIndexed { i, c ->
                run {
                    if (i % 4 == 1 && c != ' ') {
                        stacks.get(i / 4).push(c)
                    }
                }
            }
        }

    val finalStacks = stacks.map { reverseStack(it) }

    val moves = movesRaw.map {
        run {
            val split = it.split(" ")
            Triple(split[1].toInt(), split[3].toInt(), split[5].toInt())
        }
    }

    return Game(finalStacks, moves)
}

private fun <T> reverseStack(s: Stack<T>): Stack<T> {
    val out: Stack<T> = Stack()
    s.reversed().forEach { out.push(it) }
    return out
}
