import utils.readInput
import utils.testAndPrint

fun main() {
    fun part1(input: List<String>): Long {
        return input.size.toLong()
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }

    val testInput = readInput("Day25_test")
    part1(testInput).testAndPrint(1)
    part2(testInput).testAndPrint(1)

    val input = readInput("Day25")
    part1(input).testAndPrint()
    part2(input).testAndPrint()
}