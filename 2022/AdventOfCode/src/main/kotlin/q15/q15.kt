package q15

import java.nio.file.Path
import kotlin.math.abs

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

private fun parseInput(inputPath: Path): List<List<Pair<Int, Int>>> {
    return inputPath.toFile()
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

fun part1(inputPath: Path): Int {
    val input = parseInput(inputPath)
    return process(input)
}

private fun process2(sensorsAndBeacons: List<List<Pair<Int, Int>>>): Long {
    val sensorToBeacon = toSensorMap(sensorsAndBeacons)
    val sensorToMaxDist = toMaxDist(sensorToBeacon)
    val MAX_RANGE = 4_000_000

    for (row in 0..MAX_RANGE) {
        val validRange = RangeSet()
        validRange.add(0..MAX_RANGE)

        for ((sensor, sensorMaxDist) in sensorToMaxDist) {
            val pointAlignedVerticallyWithSensor = sensor.first to row
            val dist = getDist(pointAlignedVerticallyWithSensor, sensor)
            if (dist <= sensorToMaxDist[sensor]!!) {
                val rangeToExclude = sensorMaxDist - dist
                val leftMostValueToExclude = sensor.first - rangeToExclude
                val rightMostValueToExclude = sensor.first + rangeToExclude

                validRange.remove(leftMostValueToExclude..rightMostValueToExclude)
            }
        }

        val maybePoints = validRange.getAllPoints()
        if (maybePoints.isNotEmpty()) {
            val finalCol = maybePoints.first().toLong()
            return finalCol * 4_000_000L + row.toLong()
        }
    }

    return -1
}

private fun toMaxDist(sensorToBeacon: Map<Pair<Int, Int>, Pair<Int, Int>>): Map<Pair<Int, Int>, Int> {
    val resp = mutableMapOf<Pair<Int, Int>, Int>()
    for ((sensor, beacon) in sensorToBeacon) {
        resp[sensor] = getDist(sensor, beacon)
    }

    return resp
}

private fun toSensorMap(sensorsAndBeacons: List<List<Pair<Int, Int>>>): Map<Pair<Int, Int>, Pair<Int, Int>> {
    val map = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
    sensorsAndBeacons
        .forEach { map[it[0]] = it[1] }

    return map
}

fun part2(inputPath: Path): Long {
    val input = parseInput(inputPath)
    return process2(input)
}
