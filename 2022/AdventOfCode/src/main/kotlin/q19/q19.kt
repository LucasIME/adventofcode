package q19

import java.nio.file.Path
import java.util.LinkedList
import java.util.Queue
import kotlin.math.max

data class Blueprint(
    val id: Long,
    val ore: Long, // costInOre
    val clay: Long, // costInOre
    val obsidian: Pair<Long, Long>, // cost in Ore and Clay
    val geode: Pair<Long, Long>, // cost in Ore and Obsidian
)

data class State(
    val ore: Long,
    val oreRobots: Long,
    val clay: Long,
    val clayRobots: Long,
    val obsidian: Long,
    val obsidianRobots: Long,
    val geode: Long,
    val geodeRobots: Long,
) {
    fun produce() : State {
        return this.copy(
            ore = ore + oreRobots,
            clay  = clay + clayRobots,
            obsidian = obsidian + obsidianRobots,
            geode = geode + geodeRobots,
        )
    }

    fun tryBuildOreRobot(blueprint: Blueprint) : State? {
        if (ore < blueprint.ore) {
            return null
        }

        return this.copy(
            ore = ore - blueprint.ore,
            oreRobots = oreRobots + 1,
        )
    }


    fun tryBuildClayRobot(blueprint: Blueprint) : State? {
        if (ore < blueprint.clay) {
            return null
        }

        return this.copy(
            ore = ore - blueprint.clay,
            clayRobots = clayRobots + 1,
        )
    }

    fun tryBuildObsidianRobot(blueprint: Blueprint) : State? {
        if (ore < blueprint.obsidian.first || clay < blueprint.obsidian.second) {
            return null
        }

        return this.copy(
            ore = ore - blueprint.obsidian.first,
            clay = clay - blueprint.obsidian.second,
            obsidianRobots = obsidianRobots + 1,
        )
    }

    fun tryBuildGeodeRobot(blueprint: Blueprint) : State? {
        if (ore < blueprint.geode.first || obsidian < blueprint.geode.second) {
            return null
        }

        return this.copy(
            ore = ore - blueprint.geode.first,
            obsidian = obsidian - blueprint.geode.second,
            geodeRobots = geodeRobots + 1,
        )
    }
}

private fun parseInput(inputPath: Path): List<Blueprint> {
    val regex = Regex(
        """Blueprint (\d+): Each ore robot costs (\d+) ore\. Each clay robot costs (\d+) ore\. Each obsidian robot costs (\d+) ore and (\d+) clay\. Each geode robot costs (\d+) ore and (\d+) obsidian\."""
    )
    return inputPath.toFile()
        .readLines()
        .map {
            val match = regex.matchEntire(it)
            val (blueprintId, oreOre, clayOre, obsidianOre, obsidianClay, geodeOre, geodeObsidian) = match!!.destructured
            Blueprint(
                id = blueprintId.toLong(),
                ore = oreOre.toLong(),
                clay = clayOre.toLong(),
                obsidian = obsidianOre.toLong() to obsidianClay.toLong(),
                geode = geodeOre.toLong() to geodeObsidian.toLong()
            )
        }
}

private fun getLargestGeodes(blueprint: Blueprint, targetMinutes: Long) : Long {
    val start = State(0, oreRobots = 1, 0, 0, 0, 0,0, 0)

    val queue: Queue<Pair<State, Long>> = LinkedList(listOf(start to 0))

    var maxSeenGeode = 0L

    while (queue.isNotEmpty()) {
        val (curState, curMinute) = queue.remove()

        maxSeenGeode = max(maxSeenGeode, curState.geode)

        if (curMinute == targetMinutes) {
            continue
        }

        curState.tryBuildOreRobot(blueprint)?.let {
            val withProduce = it.produce().copy(ore = it.ore - 1)
            queue.add(withProduce to curMinute + 1)
        }

        curState.tryBuildClayRobot(blueprint)?.let {
            val withProduce = it.produce().copy(clay = it.clay - 1)
            queue.add(withProduce to curMinute + 1)
        }

        curState.tryBuildObsidianRobot(blueprint)?.let {
            val withProduce = it.produce().copy(obsidian = it.obsidian - 1)
            queue.add(withProduce to curMinute + 1)
        }

        curState.tryBuildGeodeRobot(blueprint)?.let {
            val withProduce = it.produce().copy(geode = it.geode)
            queue.add(withProduce to curMinute + 1)
        }

        queue.add(curState.produce() to curMinute + 1)
    }

    return maxSeenGeode
}

private fun getQuality(blueprint: Blueprint) : Long {
    return blueprint.id * getLargestGeodes(blueprint, 24)
}

private fun process(blueprints: List<Blueprint>): Long {
    return blueprints.sumOf { getQuality(it) }
}

fun part1(inputPath: Path): Long {
    val input = parseInput(inputPath)
    return process(input)
}
