package q20

import java.nio.file.Path
import kotlin.math.abs

private fun process(input: List<Long>): Long{
    val head = buildNodes(input)
    val originalOrder = getListOfOriginalOrder(head, input)

    for (node in originalOrder) {
        if (node.value == 0L) {
            continue
        }

        // Remove current node from chain
        val originalHeadNext = node.next!!
        val originalHeadPrev = node.previous!!
        originalHeadNext.previous = originalHeadPrev
        originalHeadPrev.next = originalHeadNext

        if (node.value < 0) {
            var toInsetBefore = node
            for (i in 0 until abs(node.value)) {
                toInsetBefore = toInsetBefore.previous!!
            }

            val originalPreviousToInsert = toInsetBefore.previous!!
            node.next = toInsetBefore
            node.previous = originalPreviousToInsert
            toInsetBefore.previous = node
            originalPreviousToInsert.next = node
        } else {
            var toInsetAfter = node
            for (i in 0 until node.value) {
                toInsetAfter = toInsetAfter.next!!
            }

            val originalNextAfterToInsert = toInsetAfter.next!!
            node.previous = toInsetAfter
            node.next = originalNextAfterToInsert
            toInsetAfter.next = node
            originalNextAfterToInsert.previous = node
        }

        assert(node.next!!.previous == node)
        assert(node.previous!!.next == node)
        assert(node.next != node)
        assert(node.previous != node)
    }

    val zeroNode = getZero(head)

    val nodesWeCareAbout = listOf(
        getNodeAfterSteps(zeroNode, 1000),
        getNodeAfterSteps(zeroNode, 2000),
        getNodeAfterSteps(zeroNode, 3000)
    )

    return nodesWeCareAbout.sumOf { it.value }
}

private fun parseInput(inputPath: Path): List<Long> {
    return inputPath.toFile()
        .readLines()
        .map { it.toLong() }
}

fun part1(inputPath: Path): Long {
    val input = parseInput(inputPath)
    return process(input)
}

private fun process2(input: List<Long>): Long {
    val head = buildNodes(input)
    val originalOrder = getListOfOriginalOrder(head, input)

    for (i in 0 until 10) {
        mix(originalOrder)
    }

    val zeroNode = getZero(head)

    val nodesWeCareAbout = listOf(
        getNodeAfterSteps(zeroNode, 1000),
        getNodeAfterSteps(zeroNode, 2000),
        getNodeAfterSteps(zeroNode, 3000)
    )

    return nodesWeCareAbout.sumOf { it.value }
}

private fun mix(originalOrder: List<Node<Long>>) {
    for (node in originalOrder) {
        if (node.value == 0L) {
            continue
        }

        // Remove current node from chain
        val originalHeadNext = node.next!!
        val originalHeadPrev = node.previous!!
        originalHeadNext.previous = originalHeadPrev
        originalHeadPrev.next = originalHeadNext

        val steps = abs(node.value) % (originalOrder.size - 1)
        if (node.value < 0) {
            var toInsetBefore = node
            for (i in 0 until steps) {
                toInsetBefore = toInsetBefore.previous!!
            }

            val originalPreviousToInsert = toInsetBefore.previous!!
            node.next = toInsetBefore
            node.previous = originalPreviousToInsert
            toInsetBefore.previous = node
            originalPreviousToInsert.next = node
        } else {
            var toInsetAfter = node
            for (i in 0 until steps) {
                toInsetAfter = toInsetAfter.next!!
            }

            val originalNextAfterToInsert = toInsetAfter.next!!
            node.previous = toInsetAfter
            node.next = originalNextAfterToInsert
            toInsetAfter.next = node
            originalNextAfterToInsert.previous = node
        }

        assert(node.next!!.previous == node)
        assert(node.previous!!.next == node)
        assert(node.next != node)
        assert(node.previous != node)
    }
}

private fun getZero(head: Node<Long>): Node<Long> {
    var cur = head
    while (cur.value != 0L) {
        cur = cur.next!!
    }

    return cur
}

private fun getNodeAfterSteps(head: Node<Long>, steps: Int): Node<Long> {
    var cur = head
    for (i in 0 until steps) {
        cur = cur.next!!
    }

    return cur
}

private fun getListOfOriginalOrder(head: Node<Long>, input: List<Long>): List<Node<Long>> {
    val originalOrder = mutableListOf<Node<Long>>()

    var cur = head
    for (i in input.indices) {
        originalOrder.add(cur)
        cur = cur.next!!
    }

    return originalOrder
}

private fun buildNodes(input: List<Long>): Node<Long> {
    val head = Node(input[0], null, null)
    var prev = head
    for (x in input.subList(1, input.size)) {
        val newNode = Node(x, prev, null)
        prev.next = newNode
        prev = newNode
    }

    prev.next = head
    head.previous = prev

    return head
}

private fun parseInput2(inputPath: Path): List<Long> {
    return inputPath.toFile()
        .readLines()
        .map { it.toLong() * 811589153 }
}

fun part2(inputPath: Path): Long {
    val input = parseInput2(inputPath)
    return process2(input)
}
