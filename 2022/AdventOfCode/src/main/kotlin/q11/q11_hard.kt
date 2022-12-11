package q11

import java.io.File
import java.math.BigInteger

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(monkeys: List<BigMonkey>): BigInteger {
    val rounds = 10000
    val toDiv = monkeys.map { it.remainder }.reduce { a, b -> a * b }
    for (i in 1..rounds) {
        monkeys.forEach { monkey ->
            val itemsIndex = 0
            while (itemsIndex < monkey.items.size) {
                val item = monkey.items[itemsIndex]
                var newWorry = monkey.operation(item)
                // We only need to make sure the remainders stays the same, so the number doesn't need to be large
                newWorry = newWorry.remainder(BigInteger.valueOf(toDiv))
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

    return monkeys.map { it.inspections }.sortedDescending().take(2).reduce { a, b -> a.multiply(b) }
}

private fun parseInput(): List<BigMonkey> {
    return File("src/main/resources/q11.txt")
        .readLines()
        .chunked(7)
        .map { toMonkey(it) }
}

private fun toMonkey(input: List<String>): BigMonkey {
    val monkeyNum = input[0].substring(7, input[0].length - 1).toInt()
    val items = input[1].split(": ")[1].split(", ").map { BigInteger.valueOf(it.toLong()) }.toMutableList()
    val operation = toOperationFunc(input[2].split(" = ")[1])
    val testFunc = toTestFunc(input[3].split(": ")[1])
    val remainder = input[3].split(" ").last().toLong()
    val trueMonkey = input[4].split(" ").last().toInt()
    val falseMonkey = input[5].split(" ").last().toInt()

    return BigMonkey(monkeyNum, items, operation, testFunc, remainder, trueMonkey, falseMonkey)
}

private fun toTestFunc(s: String): (BigInteger) -> Boolean {
    val divisor = BigInteger.valueOf(s.split(" by ").last().toLong())
    return { x -> x.remainder(divisor) == BigInteger.ZERO }
}

private fun toOperationFunc(s: String): (BigInteger) -> BigInteger {
    val (arg1, operation, arg2) = s.split(" ")

    return { entry: BigInteger ->
        val arg1Value = if (arg1 == "old") entry else BigInteger.valueOf(arg1.toLong())
        val arg2Value = if (arg2 == "old") entry else BigInteger.valueOf(arg2.toLong())

        when (operation) {
            "+" -> arg1Value.plus(arg2Value)
            "-" -> arg1Value.minus(arg2Value)
            "*" -> arg1Value.multiply(arg2Value)
            "/" -> arg1Value.divide(arg2Value)
            else -> throw Exception("Unexpected operation")
        }
    }
}
