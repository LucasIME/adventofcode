package q23

import java.io.File

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(input: List<Pair<Int, Int>>): Int {
    var elves = input

    var moveProposalPriority = mutableListOf<(Pair<Int, Int>, List<Pair<Int, Int>>) -> Pair<Int, Int>?>(
        ::getMaybeNorthProposal,
        ::getMaybeSouthProposal,
        ::getMaybeWestProposal,
        ::getMaybeEastProposal
    )

    var lastElves: List<Pair<Int, Int>>? = null
    var i = 0
    while (elves != lastElves) {
        lastElves = elves
        elves = move(elves, moveProposalPriority)
        moveProposalPriority = reshuffle(moveProposalPriority)
        i++
    }

    return i
}

private fun <T> reshuffle(list: MutableList<T>): MutableList<T> {
    val first = list.removeFirst()
    list.add(first)
    return list
}

private fun move(
    elves: List<Pair<Int, Int>>,
    movePriority: List<(Pair<Int, Int>, List<Pair<Int, Int>>) -> Pair<Int, Int>?>
): List<Pair<Int, Int>> {
    val newElves = mutableListOf<Pair<Int, Int>>()

    val proposedMoveToElves = mutableMapOf<Pair<Int, Int>, MutableList<Pair<Int, Int>>>()
    elves.forEach lambda@{ elf ->
        val neighs = getOccupiedNeighs(elves, elf)

        val candidate = movePriority.firstNotNullOfOrNull {
            it(elf, neighs)
        }

        if (neighs.isEmpty() || candidate == null) {
            newElves.add(elf)
            return@lambda
        }

        val elfList = proposedMoveToElves.getOrDefault(candidate, mutableListOf())
        elfList.add(elf)

        proposedMoveToElves[candidate] = elfList
    }

    proposedMoveToElves.forEach { (candidate, elfList) ->
        if (elfList.size == 1) {
            newElves.add(candidate)
        } else {
            elfList.forEach { newElves.add(it) }
        }
    }

    return newElves
}

private fun getOccupiedNeighs(elves: List<Pair<Int, Int>>, pos: Pair<Int, Int>): MutableList<Pair<Int, Int>> {
    val resp = mutableListOf<Pair<Int, Int>>()
    for (row in pos.first - 1..pos.first + 1) {
        for (col in pos.second - 1..pos.second + 1) {
            if (row to col != pos) {
                if (row to col in elves) {
                    resp.add(row to col)
                }
            }
        }
    }

    return resp
}

private fun getMaybeNorthProposal(pos: Pair<Int, Int>, neighs: List<Pair<Int, Int>>): Pair<Int, Int>? {
    for (i in -1..1) {
        if (pos.first - 1 to pos.second - i in neighs) {
            return null
        }
    }

    return pos.first - 1 to pos.second
}

private fun getMaybeSouthProposal(pos: Pair<Int, Int>, neighs: List<Pair<Int, Int>>): Pair<Int, Int>? {
    for (i in -1..1) {
        if (pos.first + 1 to pos.second - i in neighs) {
            return null
        }
    }

    return pos.first + 1 to pos.second
}

private fun getMaybeWestProposal(pos: Pair<Int, Int>, neighs: List<Pair<Int, Int>>): Pair<Int, Int>? {
    for (i in -1..1) {
        if (pos.first - i to pos.second - 1 in neighs) {
            return null
        }
    }

    return pos.first to pos.second - 1
}

private fun getMaybeEastProposal(pos: Pair<Int, Int>, neighs: List<Pair<Int, Int>>): Pair<Int, Int>? {
    for (i in -1..1) {
        if (pos.first - i to pos.second + 1 in neighs) {
            return null
        }
    }

    return pos.first to pos.second + 1
}


private fun parseInput(): List<Pair<Int, Int>> {
    return File("src/main/resources/q23.txt")
        .readLines()
        .withIndex()
        .flatMap { (row, s) ->
            s.withIndex().map { (col, c) ->
                if (c == '#') {
                    row to col
                } else {
                    null
                }
            }
        }.filterNotNull()
}
