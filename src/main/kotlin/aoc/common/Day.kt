package aoc.common

import utils.readInput


abstract class Day(
    val year: Int,
    val day: Int,
    val testPart1: Any,
    val testPart2: Any
) {

    abstract fun part1(input: List<String>): Any
    abstract fun part2(input: List<String>): Any
}