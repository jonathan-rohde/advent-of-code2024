package aoc.years.y2024

import aoc.common.Day
import aoc.common.printResults
import utils.toIntList
import kotlin.math.abs

fun main() {
    Day01().execute().printResults()
}

class Day01: Day(day = 1, year = 2024, testPart1 = 11, testPart2 = 31) {

    fun parseLists(input: List<String>): Pair<List<Int>, List<Int>> {
        return input.map {
            val parts = it.toIntList()
            parts[0] to parts[1]
        }
            .toList()
            .unzip()
    }

    override fun part1(input: List<String>): Int {
        val (first, second) = parseLists(input)
            .let {
                it.first.sorted() to it.second.sorted()
            }
        return first.zip(second)
            .sumOf { (a, b) -> abs(a - b) }
    }

    override fun part2(input: List<String>): Int {
        val (first, second) = parseLists(input)
        return first.sumOf { left ->
            left * second.count { right ->
                right == left
            }
        }
    }
}
