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
                    printResult("\tPart 1 (Test)", day.testPart1) {
                        day.part1(day.readFile(true))
                    }
                    printResult("\tPart 2 (Test)") {
                        day.part1(day.readFile(false))
                    }
                    printResult("\tPart 1 (Real)", day.testPart2) {
                        day.part2(day.readFile(true))
                    }
                    printResult("\tPart 2 (Real)") {
                        day.part2(day.readFile(false))
                    }
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