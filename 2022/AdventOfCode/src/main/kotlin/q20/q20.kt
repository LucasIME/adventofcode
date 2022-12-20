package q20

import java.io.File
import kotlin.math.abs

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(input: List<Int>): Int {
    val head = buildNodes(input)
    val originalOrder = getListOfOriginalOrder(head, input)

    for (node in originalOrder) {
        if (node.value == 0) {
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

private fun getZero(head: Node<Int>): Node<Int> {
    var cur = head
    while (cur.value != 0) {
        cur = cur.next!!
    }

    return cur
}

private fun getNodeAfterSteps(head: Node<Int>, steps: Int): Node<Int> {
    var cur = head
    for (i in 0 until steps) {
        cur = cur.next!!
    }

    return cur
}

private fun getListOfOriginalOrder(head: Node<Int>, input: List<Int>): List<Node<Int>> {
    val originalOrder = mutableListOf<Node<Int>>()

    var cur = head
    for (i in input.indices) {
        originalOrder.add(cur)
        cur = cur.next!!
    }

    return originalOrder
}

private fun buildNodes(input: List<Int>): Node<Int> {
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

private fun parseInput(): List<Int> {
    return File("src/main/resources/q20.txt")
        .readLines()
        .map { it.toInt() }
}