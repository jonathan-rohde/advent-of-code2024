package aoc.common

import org.springframework.stereotype.Service
import utils.readInput
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

    fun execute() {
        runnerProperties.years.sorted()
            .forEach {
                println("#########\n# $it \n#########")
                years[it]?.forEach { day ->
                    println("Day ${day.day}")
                    val result = day.execute(runnerProperties.runs)
                    println("\tPart 1:")
                    println("\t\t Runs: ${runnerProperties.runs}")
                    println("\t\t Results: ${result.part1.distinct.joinToString(", ")}")
                    println("\t\t Min: ${result.part1.min}")
                    println("\t\t Max: ${result.part1.max}")
                    println("\t\t Avg: ${result.part1.avg}")
                    println("\t\t Median: ${result.part1.median}")
                    println("\tPart 2:")
                    println("\t\t Runs: ${runnerProperties.runs}")
                    println("\t\t Results: ${result.part2.distinct.joinToString(", ")}")
                    println("\t\t Min: ${result.part2.min}")
                    println("\t\t Max: ${result.part2.max}")
                    println("\t\t Avg: ${result.part2.avg}")
                    println("\t\t Median: ${result.part2.median}")
                }
            }
    }

    private fun Day.readFile(test: Boolean): List<String> {
        val dayToString = day.toString().padStart(2, '0')
        val fileName = if (test) "Day${dayToString}_test" else "Day${dayToString}"
        return readInput(fileName, year)
    }

    private fun printResult(description: String, check: Any? = null, exec: () -> Any?) {
        println("$description: ")
        val measured = measureTime {
            val result = exec()
            if (check != null) {
                check(result == check)
            }
            if (runnerProperties.opaqueResults) {
                println("\t\tResult: (censored)")
            } else {
                println("\t\tResult: $result")
            }
        }
        println("\t\tDuration: $measured")
    }
}