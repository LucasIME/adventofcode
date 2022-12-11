package q11

import java.math.BigInteger

data class Monkey(
    val num: Int,
    val items: MutableList<Int>,
    val operation: (Int) -> Int,
    val test: (Int) -> Boolean,
    val trueMonkey: Int,
    val falseMonkey: Int,
    var inspections: Int = 0
)

data class BigMonkey(
    val num: Int,
    val items: MutableList<BigInteger>,
    val operation: (BigInteger) -> BigInteger,
    val test: (BigInteger) -> Boolean,
    val remainder: Long,
    val trueMonkey: Int,
    val falseMonkey: Int,
    var inspections: BigInteger = BigInteger.ZERO
)
