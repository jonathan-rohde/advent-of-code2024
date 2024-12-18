package aoc.common

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "aoc")
data class RunnerProperties(
    val years: List<Int>,
    val opaqueResults: Boolean = false,
    val runs: Int = 1,
)