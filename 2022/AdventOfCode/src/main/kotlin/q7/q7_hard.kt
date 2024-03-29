package q7

import java.io.File

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(input: FileSystemNode): Int {
    val sizes = mutableListOf<Int>()

    getSizeAndAddToListIfFolder(input, sizes)

    val totalDiskSpace = 70000000
    val amountNeeded = 30000000
    val unused = totalDiskSpace - sizes.max()
    val needToDelete = amountNeeded - unused

    return sizes.sorted().first { it >= needToDelete }
}

private fun getSizeAndAddToListIfFolder(input: FileSystemNode, out: MutableList<Int>): Int {
    if (input.isFile) {
        return input.size!!
    }

    var total = 0
    input.childrenByName.forEach { (_, node) ->
        total += getSizeAndAddToListIfFolder(node, out)
    }

    out.add(total)
    return total
}

private fun parseInput(): FileSystemNode {
    val lines = File("src/main/resources/q7.txt").readLines()
    val fsHead = FileSystemNode("/", null, mutableMapOf(), false)
    var cur = fsHead
    lines.forEach {
        if (it[0] == '$') {
            val commandAndArgs = it.split(" ")
            val command = commandAndArgs[1]
            if (command == "cd") {
                val next = when (val arg = commandAndArgs[2]) {
                    ".." -> cur.parent
                    "/" -> fsHead
                    else -> cur.childrenByName[arg]
                }
                cur = next!!
            }
        } else {
            val (typeOrSize, name) = it.split(" ")
            if (typeOrSize == "dir") {
                cur.childrenByName[name] = FileSystemNode(name, cur, mutableMapOf(), false)
            } else {
                cur.childrenByName[name] = FileSystemNode(name, cur, mutableMapOf(), true, typeOrSize.toInt())
            }
        }
    }
    return fsHead
}
