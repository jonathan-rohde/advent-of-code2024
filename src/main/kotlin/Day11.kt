import utils.println
import utils.readInput
import utils.testAndPrint
import utils.toLongList
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Long {
        return input.first().toLongList()
            .sumOf { it.countSplitStone(25) }

    }

    fun part2(input: List<String>): Long {
        return input.first().toLongList()
            .sumOf { it.countSplitStone(75) }
    }

    val testInput = readInput("Day11_test")
    measureTime {
        part1(testInput)//.testAndPrint(55312L)
    }.let {
        println("Part 1 (Test): $it")
    }
    measureTime {
        part2(testInput)//.testAndPrint()
    }.let {
        println("Part 2 (Test): $it")
    }

    val input = readInput("Day11")
    measureTime {
        part1(input)//.testAndPrint()
    }.let {
        println("Part 1: $it")
    }
    measureTime {
        part2(input)//.testAndPrint()
    }.let {
        println("Part 2: $it")
    }
}

val cache = mutableMapOf<Pair<Long, Int>, Long>()


private fun Long.countSplitStone(steps: Int): Long {
    return countSplitStones(steps)
}

private fun Long.countSplitStones(stepsMissing: Int): Long {
    if (cache.contains(Pair(this, stepsMissing))) {
        val cachedResult = cache[Pair(this, stepsMissing)]!!
        return cachedResult
    }
    if (stepsMissing == 0) {
        return 1
    }
    if (this == 0L) {
        val list = listOf(1L)
        val result = list.sumOf { it.countSplitStones(stepsMissing - 1) }
        cache[Pair(0, stepsMissing)] = result
        return result
    }
    if (this.toString().length % 2 == 0) {
        val string = this.toString()
        val half = string.length / 2
        val firstHalf = string.substring(0, half).toLong(10)
        val secondHalf = string.substring(half).toLong(10)
        val list = listOf(firstHalf, secondHalf)
        val result = list.sumOf { it.countSplitStones(stepsMissing - 1) }
        cache[Pair(this, stepsMissing)] = result
        return result
    }

    val list = listOf(this * 2024)
    val result = list.sumOf { it.countSplitStones(stepsMissing - 1) }
    cache[Pair(this, stepsMissing)] = result
    return result

}
