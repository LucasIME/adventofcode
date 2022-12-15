package q15

import kotlin.math.max
import kotlin.math.min

class RangeSet {
    private val ranges: MutableList<IntRange> = mutableListOf()

    fun add(range: IntRange) {
        addRangeToRightPlace(range)

        var i = 0
        while (i < ranges.size) {
            var j = i + 1
            while (j < ranges.size) {
                if (overlaps(ranges[i], ranges[j])){
                    ranges[i] = merge(ranges[i], ranges[j])
                    ranges.removeAt(j)
                    j-- // compensate for the element we deleted
                }
                j++
            }
            i++
        }
    }

    private fun addRangeToRightPlace(range: IntRange) : Int {
        var i = 0
        while (i < ranges.size) {
            if (range.first <= ranges[i].first) {
                ranges.add(i, range)
                return i
            }
            i++
        }

        // didn't find any place to add
         ranges.add(range)

        return ranges.size - 1
    }

    fun remove(range: IntRange) {
        var i = 0
        while (i < ranges.size) {
            if (fullyContains(range, ranges[i])) {
                ranges.removeAt(i)
                i--
            } else if (fullyContains(ranges[i], range)) {
                val (range1, range2) = split(ranges[i], range)
                ranges[i] = range1
                ranges.add(i+1, range2)
            } else if (overlaps(ranges[i], range)) {
                ranges[i] = remove(ranges[i], range)
            }
            i++
        }
    }

    private fun split(baseRange: IntRange, minorRange: IntRange): Pair<IntRange, IntRange> {
        return baseRange.first..minorRange.first -1 to minorRange.last + 1 ..baseRange.last
    }

    private fun remove(baseRange: IntRange, rangeToBeRemoved: IntRange): IntRange {
        return IntRange(
            if (rangeToBeRemoved.first >= baseRange.first) baseRange.first else rangeToBeRemoved.last + 1,
            if (rangeToBeRemoved.last <= baseRange.last)  baseRange.last  else rangeToBeRemoved.first -1
            )
    }

    private fun fullyContains(range: IntRange, range2: IntRange): Boolean {
        return range.first <= range2.first && range.last >= range2.last
    }

    fun getAllPoints(): List<Int> {
        return ranges.flatMap { listOf(it.first, it.last) }
    }

    private fun overlaps(range1: IntRange, range2: IntRange): Boolean {
        return range1.last >= range2.first && range1.last <= range2.last
                || range2.last >= range1.first && range2.last <= range1.last
    }

    private fun merge(range1: IntRange, range2: IntRange): IntRange {
        return IntRange(
            min(range1.first, range2.first),
            max(range1.last, range2.last)
        )
    }
}