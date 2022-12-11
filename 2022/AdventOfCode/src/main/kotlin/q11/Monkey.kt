package q11

data class Monkey(
    val num: Int,
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val test: (Long) -> Boolean,
    val remainder: Long,
    val trueMonkey: Int,
    val falseMonkey: Int,
    var inspections: Int = 0
)
