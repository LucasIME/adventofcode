package q11

import java.nio.file.Path

private fun process(monkeys: List<Monkey>): Int {
    val rounds = 20
    for (i in 1..rounds) {
        monkeys.forEach { monkey ->
            val itemsIndex = 0
            while (itemsIndex < monkey.items.size) {
                val item = monkey.items[itemsIndex]
                var newWorry = monkey.operation(item)
                newWorry /= 3
                if (monkey.test(newWorry)) {
                    monkeys[monkey.trueMonkey].items.add(newWorry)
                } else {
                    monkeys[monkey.falseMonkey].items.add(newWorry)
                }
                monkey.inspections++
                monkey.items.removeAt(itemsIndex)
            }
        }
    }

    return monkeys.map { it.inspections }.sortedDescending().take(2).reduce { a, b -> a * b }
}

private fun parseInput(inputPath: Path): List<Monkey> {
    return inputPath.toFile()
        .readLines()
        .chunked(7)
        .map { toMonkey(it) }
}

private fun toMonkey(input: List<String>): Monkey {
    val monkeyNum = input[0].substring(7, input[0].length - 1).toInt()
    val items = input[1].split(": ")[1].split(", ").map { it.toLong() }.toMutableList()
    val operation = toOperationFunc(input[2].split(" = ")[1])
    val testFunc = toTestFunc(input[3].split(": ")[1])
    val remainder = input[3].split(" ").last().toLong()
    val trueMonkey = input[4].split(" ").last().toInt()
    val falseMonkey = input[5].split(" ").last().toInt()

    return Monkey(monkeyNum, items, operation, testFunc, remainder, trueMonkey, falseMonkey)
}

private fun toTestFunc(s: String): (Long) -> Boolean {
    val divisor = s.split(" by ").last().toInt()
    return { x -> x % divisor == 0L }
}

private fun toOperationFunc(s: String): (Long) -> Long {
    val (arg1, operation, arg2) = s.split(" ")

    return { entry: Long ->
        val arg1Value = if (arg1 == "old") entry else arg1.toLong()
        val arg2Value = if (arg2 == "old") entry else arg2.toLong()

        when (operation) {
            "+" -> arg1Value + arg2Value
            "-" -> arg1Value - arg2Value
            "*" -> arg1Value * arg2Value
            "/" -> arg1Value / arg2Value
            else -> throw Exception("Unexpected operation")
        }
    }
}

fun part1(inputPath: Path): Int {
    val input = parseInput(inputPath)
    return process(input)
}

private fun process2(monkeys: List<Monkey>): Long {
    val rounds = 10000
    val toDiv = monkeys.map { it.remainder }.reduce { a, b -> a * b }
    for (i in 1..rounds) {
        monkeys.forEach { monkey ->
            val itemsIndex = 0
            while (itemsIndex < monkey.items.size) {
                val item = monkey.items[itemsIndex]
                var newWorry = monkey.operation(item)
                // We only need to make sure the remainders stays the same, so the number doesn't need to be large
                newWorry %= toDiv
                if (monkey.test(newWorry)) {
                    monkeys[monkey.trueMonkey].items.add(newWorry)
                } else {
                    monkeys[monkey.falseMonkey].items.add(newWorry)
                }
                monkey.inspections++
                monkey.items.removeAt(itemsIndex)
            }
        }
    }

    return monkeys.map { it.inspections }.sortedDescending().map { it.toLong() }.take(2).reduce { a, b -> a * b }
}

fun part2(inputPath: Path): Long {
    val input = parseInput(inputPath)
    return process2(input)
}
