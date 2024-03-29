package q15

import java.io.File
import kotlin.math.abs

fun main() {
    val input = parseInput()
    println(process(input))
}

private fun process(sensorsAndBeacons: List<List<Pair<Int, Int>>>): Int {
    val desiredRow = 2_000_000
    val excludedPointSet = mutableSetOf<Pair<Int, Int>>()
    for ((sensor, beacon) in sensorsAndBeacons) {
        val colRange = -10_000_000..10_000_000
        val currentDistThreshold = getDist(sensor, beacon)
        for (col in colRange) {
            val candidatePoint = col to desiredRow
            if (candidatePoint != beacon && getDist(sensor, candidatePoint) <= currentDistThreshold) {
                excludedPointSet.add(candidatePoint)
            }
        }
    }
    return excludedPointSet.size
}

private fun getDist(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Int {
    return abs(p2.first - p1.first) + abs(p2.second - p1.second)
}

private fun parseInput(): List<List<Pair<Int, Int>>> {
    return File("src/main/resources/q15.txt")
        .readLines()
        .map {
            val splits = it.substring(12).split(": closest beacon is at x=")
            val sensor = splits[0].split(", y=").map { it.toInt() }
            val sensorPair = sensor[0] to sensor[1]
            val beacon = splits[1].split(", y=").map { it.toInt() }
            val beaconPair = beacon[0] to beacon[1]

            listOf(sensorPair, beaconPair)
        }
}
