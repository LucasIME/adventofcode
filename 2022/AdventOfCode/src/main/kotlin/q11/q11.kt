package q11

import java.io.File

fun main() {
    val input = parseInput()
    println(process(input))
}

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

private fun parseInput(): List<Monkey> {
    return File("src/main/resources/q11.txt")
        .readLines()
        .chunked(7)
        .map { toMonkey(it) }
}

private fun toMonkey(input: List<String>): Monkey {
    val monkeyNum = input[0].substring(7, input[0].length - 1).toInt()
    val items = input[1].split(": ")[1].split(", ").map { it.toInt() }.toMutableList()
    val operation = toOperationFunc(input[2].split(" = ")[1])
    val testFunc = toTestFunc(input[3].split(": ")[1])
    val trueMonkey = input[4].split(" ").last().toInt()
    val falseMonkey = input[5].split(" ").last().toInt()

    return Monkey(monkeyNum, items, operation, testFunc, trueMonkey, falseMonkey)
}

private fun toTestFunc(s: String): (Int) -> Boolean {
    val divisor = s.split(" by ").last().toInt()
    return { x -> x % divisor == 0 }
}

private fun toOperationFunc(s: String): (Int) -> Int {
    val (arg1, operation, arg2) = s.split(" ")

    return { entry: Int ->
        val arg1Value = if (arg1 == "old") entry else arg1.toInt()
        val arg2Value = if (arg2 == "old") entry else arg2.toInt()

        when (operation) {
            "+" -> arg1Value + arg2Value
            "-" -> arg1Value - arg2Value
            "*" -> arg1Value * arg2Value
            "/" -> arg1Value / arg2Value
            else -> throw Exception("Unexpected operation")
        }
    }
}
