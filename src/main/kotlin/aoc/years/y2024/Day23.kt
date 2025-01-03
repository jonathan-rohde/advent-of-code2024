package aoc.years.y2024

import aoc.common.Day
import aoc.common.printResults
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.collections.List
import kotlin.collections.Set
import kotlin.collections.any
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.component3
import kotlin.collections.distinct
import kotlin.collections.emptySet
import kotlin.collections.filter
import kotlin.collections.flatMap
import kotlin.collections.forEach
import kotlin.collections.isNotEmpty
import kotlin.collections.joinToString
import kotlin.collections.listOf
import kotlin.collections.map
import kotlin.collections.mapNotNull
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.collections.set
import kotlin.collections.sorted
import kotlin.collections.toMutableSet

class Day23 : Day(2024, 23, 7L to "co,de,ka,ta") {
    override fun part1(input: List<String>): Long {
        neighbourCache.clear()
        connectionCheckCache.clear()
        return input.countPairsStartingWith("t")
    }

    override fun part2(input: List<String>): String {
        neighbourCache.clear()
        connectionCheckCache.clear()
        return input.largestSet().sorted().joinToString(",")
    }
}

fun main() {
    Day23().execute().printResults()
}

private fun List<String>.countPairsStartingWith(t: String): Long {
    val result = mutableSetOf<Triple<String, String, String>>()

    val pairs = parse()
    val queue = ArrayDeque<Pair<String, String>>()
    pairs.filter { it.first.startsWith(t) || it.second.startsWith(t) }
        .forEach { queue.add(it) }

    while (queue.isNotEmpty()) {
        val node = queue.removeFirst()

        val matchingPairs = pairs.filter { it != node }
            .filter {
                it.first == node.first || it.first == node.second ||
                        it.second == node.first || it.second == node.second
            }

        val (a, b) = node
        matchingPairs.forEach { (c, d) ->
            val (x, y, z) = listOf(a, b, c, d).sorted().distinct()
            if (pairs.hasConnection(x, y) && pairs.hasConnection(y, z) && pairs.hasConnection(z, x)) {
                result.add(Triple(x, y, z))
            }
        }
    }

    return result.size.toLong()
}

private fun List<String>.largestSet(): Set<String> {
    val pairs = parse()
        .map { (a, b) ->
            val (x, y) = listOf(a, b).sorted()
            x to y
        }

    val connections = findConnections(pairs)
    return connections
}

private val neighbourCache = mutableMapOf<String, List<String>>()

private fun List<Pair<String, String>>.getNeighbour(node: String): List<String> {
    return neighbourCache[node] ?: mapNotNull { (a, b) ->
        if (a == node) {
            b
        } else if (b == node) {
            a
        } else {
            null
        }
    }
        .filter {
            // only if it has connection to all in group
            hasConnection(node, it)
        }.also {
            neighbourCache[node] = it
        }
}

private fun findConnections(pairs: List<Pair<String, String>>): Set<String> {
    val visited = mutableSetOf<String>()
    var result = emptySet<String>()
    val nodes = pairs.flatMap { (a, b) -> listOf(a, b) }.toMutableSet()
    val queue = PriorityQueue<String>()
    queue.addAll(nodes)

    while (queue.isNotEmpty()) {
        val node = queue.poll()
        if (visited.contains(node)) continue
        visited.add(node)
        val neighbourQueue = PriorityQueue<String>()
        pairs.getNeighbour(node).forEach { neighbourQueue.add(it) }
        val group = mutableSetOf(node)
        while (neighbourQueue.isNotEmpty()) {
            val neighbour = neighbourQueue.poll()
            if (group.contains(neighbour)) continue
            if (group.any { !pairs.hasConnection(it, neighbour) }) continue
            visited.add(neighbour)
            group.add(neighbour)
            pairs.getNeighbour(neighbour).forEach { neighbourQueue.add(it) }
        }
        if (group.size > result.size) {
            result = group
        }
    }
    return result
}

private val connectionCheckCache = mutableMapOf<Pair<String, String>, Boolean>()

private fun List<Pair<String, String>>.hasConnection(a: String, b: String): Boolean {
    return connectionCheckCache[a to b] ?:
    (contains(a to b) || contains(b to a)).also {
        connectionCheckCache[a to b] = it
    }
}

private fun String.parse(): Pair<String, String> = split("-").let { it[0] to it[1] }
private fun List<String>.parse(): List<Pair<String, String>> = map { it.parse() }
