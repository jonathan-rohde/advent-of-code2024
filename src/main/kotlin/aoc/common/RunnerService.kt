package aoc.common

import org.springframework.stereotype.Service
import utils.readInput
import kotlin.time.Duration
import kotlin.time.measureTime

@Service
class RunnerService(
    val runnerProperties: RunnerProperties,
    days: List<Day>
) {
    private var years: Map<Int, List<Day>> = days.groupBy { it.year }
        .mapValues { (_, value) ->
            value.sortedBy { it.day }
        }

    fun execute(years: List<Int>, days: List<Int> = emptyList()) {
        runnerProperties.years
            .filter { it in years }
            .sorted()
            .forEach {
                println("#########\n# $it \n#########")
                this.years[it]
                    ?.filter { proc -> days.isEmpty() || proc.day in days }
                    ?.forEach { proc ->
                    println("Day ${proc.day}")
                    val result = proc.execute(runnerProperties.runs)
                    result.testPart1.printTestResult("Part 1")
                    result.part1.print("Part 1")
                    result.testPart1.printTestResult("Part 2")
                    result.part2.print("Part 2")
                }
            }
    }

    private fun PartResult.print(headline: String) {
        println("\t$headline:")
        println("\t\t Runs: ${runnerProperties.runs}")
        if (runnerProperties.opaqueResults) {
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