package aoc.common

import kotlin.time.Duration

class RunnerService(
    val days: List<Day>,
    val year: Int,
    val executeRuns: Int = 1,
    val opaqueResults: Boolean = false
) {

    fun execute(limit: List<Int> = emptyList()) {
        println("#########\n# $year \n#########")
        days.filter { proc -> limit.isEmpty() || proc.day in limit }
            .forEach { proc ->
                println("Day ${proc.day}")
                val result = proc.execute(executeRuns)
                result.testPart1.printTestResult("Part 1")
                result.part1.print("Part 1")
                result.testPart1.printTestResult("Part 2")
                result.part2.print("Part 2")
            }
    }

    private fun PartResult.print(headline: String) {
        println("\t$headline:")
        println("\t\t Runs: $executeRuns")
        if (opaqueResults) {
            println("\t\t Result: (censored)")
        } else {
            println("\t\t Result: ${distinct.joinToString(", ")}")
        }
        println("\t\t Min: $min")
        println("\t\t Max: $max")
        println("\t\t Avg: $avg")
        println("\t\t Median: $median")
    }

    private fun Pair<Duration, Any>.printTestResult(headline: String) {
        println("\t$headline (Test):")
        println("\t\t Results: $second")
        println("\t\t Time: $first")
    }
}
