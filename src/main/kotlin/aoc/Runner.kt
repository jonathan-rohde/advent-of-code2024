package aoc

import aoc.common.RunnerService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
    runApplication<Runner>(*args)
}

@SpringBootApplication
@ConfigurationPropertiesScan
class Runner(
    val runnerService: RunnerService
): CommandLineRunner {

    override fun run(vararg args: String?) {
        runnerService.execute(listOf(2024), emptyList())
    }
}
