package q16

import com.google.common.collect.Sets
import java.io.File
import java.util.PriorityQueue
import java.util.concurrent.Executors
import java.util.concurrent.Future

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(graph: Map<String, NodeInfo>): Int {
    val maxRounds = 26
    val reachabilityGraph = buildReachabilityMatrix(graph)

    // We want to remove the zero flow nodes to reduce problem space
    val nodesWeWantToVisit = graph.map { it.key }.toMutableSet()
    for ((nodeName, nodeInfo) in graph) {
        if (nodeInfo.flow == 0) {
            nodesWeWantToVisit.remove(nodeName)
        }
    }

    val executorService = Executors.newFixedThreadPool(16)
    val futuresList = mutableListOf<Future<*>>()
    val lock = Any()

    var maxScore = 0

    for (groupSize in 0..nodesWeWantToVisit.size / 2) {
        val possibleCombinations = Sets.combinations(nodesWeWantToVisit, groupSize)
        for (nodesForMeToVisit in possibleCombinations) {
            val elephantNodesToVisit = Sets.difference(nodesWeWantToVisit, nodesForMeToVisit)

            futuresList.add(
                executorService.submit {
                    val humanScore = solve(reachabilityGraph, graph, "AA", 0, 0, 0, nodesForMeToVisit, maxRounds)
                    val elephantScore = solve(reachabilityGraph, graph, "AA", 0, 0, 0, elephantNodesToVisit, maxRounds)

                    synchronized(lock) {
                        if (humanScore + elephantScore > maxScore) {
                            maxScore = humanScore + elephantScore
                        }

                    }
                }
            )
        }
    }

    for (future in futuresList) {
        future.get()
    }
    executorService.shutdown()

    return maxScore
}

private fun solve(
    reachabilityMatrix: Map<String, Map<String, Int>>,
    graph: Map<String, NodeInfo>,
    curNode: String,
    curRound: Int,
    currentPressure: Int,
    currentFlow: Int,
    remainingNodesToVisit: Set<String>,
    maxRounds: Int
): Int {
    val scoreIfWeDontDoAnythingElseProductive = currentPressure + (maxRounds - curRound) * currentFlow
    var maxScore = scoreIfWeDontDoAnythingElseProductive

    for (targetNode in remainingNodesToVisit) {
        val roundsToReachAndOpenNode = reachabilityMatrix[curNode]!![targetNode]!! + 1
        if (curRound + roundsToReachAndOpenNode < maxRounds) {
            val newRound = curRound + roundsToReachAndOpenNode
            val newPressure = currentPressure + roundsToReachAndOpenNode * currentFlow
            val newFlow = currentFlow + graph[targetNode]!!.flow

            val remainingNodesButCurrentOne = HashSet(remainingNodesToVisit)
            remainingNodesButCurrentOne.remove(targetNode)

            val possibleScore = solve(
                reachabilityMatrix,
                graph,
                targetNode,
                newRound,
                newPressure,
                newFlow,
                remainingNodesButCurrentOne,
                maxRounds
            )

            if (possibleScore > maxScore) {
                maxScore = possibleScore
            }
        }
    }

    return maxScore
}

private fun buildReachabilityMatrix(graph: Map<String, NodeInfo>): Map<String, Map<String, Int>> {
    val reachabilityMatrix: MutableMap<String, Map<String, Int>> = mutableMapOf()
    for ((node, _) in graph) {
        val minDists = dikjstra(graph, node)
        reachabilityMatrix[node] = minDists
    }

    return reachabilityMatrix
}

private fun dikjstra(graph: Map<String, NodeInfo>, source: String): Map<String, Int> {
    val nodeDist = mutableMapOf<String, Int>()
    val priorityQueue = PriorityQueue<ComparablePair<Int, String>>()

    for ((node, _) in graph) {
        if (node != source) {
            nodeDist[node] = Int.MAX_VALUE
            priorityQueue.add(ComparablePair(Int.MAX_VALUE, node))
        } else {
            nodeDist[node] = 0
            priorityQueue.add(ComparablePair(0, node))
        }
    }

    while (priorityQueue.isNotEmpty()) {
        val pair = priorityQueue.poll()
        val dist = pair.first
        val curNode = pair.second


        for (neigh in graph[curNode]!!.neighs) {
            val maybeNewDist = dist + 1
            val curDist = nodeDist[neigh]!!
            if (maybeNewDist < curDist) {
                priorityQueue.remove(ComparablePair(curDist, neigh))
                nodeDist[neigh] = maybeNewDist
                priorityQueue.add(ComparablePair(maybeNewDist, neigh))
            }
        }
    }

    return nodeDist
}

private fun parseInput(): Map<String, NodeInfo> {
    val resp: MutableMap<String, NodeInfo> = mutableMapOf()
    File("src/main/resources/q16.txt")
        .readLines()
        .forEach {
            val nodeName = it.substring(6, 8)
            val nodeVal = it.substring(23).split(';').first().toInt()
            val separator = if ("valves" in it) "valves " else "valve "
            val neighNames = it.split(separator)[1].split(", ")
            resp[nodeName] = NodeInfo(nodeVal, neighNames)
        }
    return resp
}
