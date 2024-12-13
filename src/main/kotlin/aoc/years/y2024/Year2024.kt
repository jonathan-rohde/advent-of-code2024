package aoc.years.y2024

import aoc.common.RunnerService
import aoc.common.Year

class Year2024 : Year {
    override fun execute(days: List<Int>, opaqueResults: Boolean, runs: Int) {
        val runner = RunnerService(
            days = listOf(
                // Add your days here
                Day01(),
                Day02()
            ),
            year = 2024,
            opaqueResults = opaqueResults,
            executeRuns = runs
        )
        runner.execute(limit = days)
    }
}
