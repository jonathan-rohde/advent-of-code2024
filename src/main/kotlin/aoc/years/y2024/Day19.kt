package aoc.years.y2024

import aoc.common.Day
import aoc.common.printResults

class Day19 : Day(2024, 19, 6 to 16L) {
    override fun part1(input: List<String>): Int =
        input.parseInput().calculateCombinations().count { (_, combinations) -> combinations > 0 }

    override fun part2(input: List<String>): Long =
        input.parseInput().calculateCombinations().values.sum()
}


fun main() {
    Day19().execute().printResults()
}

private fun Onsen.combinationsOf(towel: Towel): Long {
    if (towel in cache) return cache[towel]!!
    if (towel.isBlank()) return 0
    return (patterns
        .filter { towel.startsWith(it) }
        .map { combinationsOf(towel.drop(it.length)) }
        .sumOf { it }
            + patterns.count { it == towel })
        .also {
            cache[towel] = it
        }
}

private fun Onsen.calculateCombinations(): Map<Towel, Long> = towels.associateWith { combinationsOf(it) }

private fun List<String>.parseInput(): Onsen = Onsen(
    towels = drop(2),
    patterns = first().split(", ")
)

private typealias Pattern = String
private typealias Patterns = List<Pattern>
private typealias Towel = String
private typealias Towels = List<Towel>

private data class Onsen(
    val towels: Towels,
    val patterns: Patterns,
    val cache: MutableMap<Pattern, Long> = mutableMapOf()
)
