package aoc.common

import utils.println
import utils.readInput
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.DurationUnit
import kotlin.time.measureTimedValue


abstract class Day(
    val year: Int,
    val day: Int,
    val testPart1: Any?,
    val testPart2: Any?
) {

    fun execute(runs: Int = 1): Result {
        val dayString = day.toString().padStart(2, '0')
        val testFileName = "Day${dayString}_test"
        val fileName = "Day$dayString"
        val testInput = readInput(testFileName, year)
        val input = readInput(fileName, year)

        val testResults = executePart1(testInput, true) to executePart2(testInput, true)
        val results = (0 until runs).map {
            val part1 = executePart1(input)
            val part2 = executePart2(input)
            part1 to part2
        }
        val part1 = results.map { it.first }
        val min1 = part1.minOf { it.first }
        val max1 = part1.maxOf { it.first }
        val avg1 = part1.map { it.first }.map { it.toDouble(DurationUnit.MICROSECONDS) }.average().nanoseconds
        val median1 = part1.map { it.first }.sorted()[part1.size / 2]
        val part2 = results.map { it.second }
        val min2 = part1.minOf { it.first }
        val max2 = part1.maxOf { it.first }
        val avg2 = part1.map { it.first }.map { it.toDouble(DurationUnit.MICROSECONDS) }.average().nanoseconds
        val median2 = part1.map { it.first }.sorted()[part1.size / 2]
        return Result(
            testPart1 = testResults.first,
            testPart2 = testResults.second,
            part1 = PartResult(
                min = min1,
                max = max1,
                avg = avg1,
                median = median1,
                distinct = part1.map { it.second }.toSet()
            ),
            part2 = PartResult(
                min = min2,
                max = max2,
                avg = avg2,
                median = median2,
                distinct = part2.map { it.second }.toSet()
            )
        )
    }

    private fun executePart1(input: List<String>, test: Boolean = false) : Pair<Duration, Any> {
        val result = measureTimedValue { part1(input) }

        if (test) {
            check(result.value == testPart1) {
                "$testPart1 != $result"
            }
        }

        return result.duration to result.value
    }

    private fun executePart2(input: List<String>, test: Boolean = false) : Pair<Duration, Any> {
        val result = measureTimedValue { part2(input) }

        if (test) {
            check(result.value == testPart2) {
                "$testPart2 != $result"
            }
        }

        return result.duration to result.value
    }

    abstract fun part1(input: List<String>): Any
    abstract fun part2(input: List<String>): Any
}

data class Result(
    val testPart1: Pair<Duration, Any>,
    val testPart2: Pair<Duration, Any>,
    val part1: PartResult,
    val part2: PartResult
)

data class PartResult(
    val min: Duration,
    val max: Duration,
    val avg: Duration,
    val median: Duration,

    val distinct: Set<Any>
)

fun Result.printResults() {
    testPart1.second.println()
    testPart2.second.println()

    part1.distinct.joinToString(", ").println()
    part2.distinct.joinToString(", ").println()
}