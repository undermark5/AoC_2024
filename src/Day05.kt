fun main() {
    fun part1(input: List<String>): Int {
        val rules = input.takeWhile(String::isNotBlank).map {
            it.split("|").map(String::toInt)
        }
        val updates = input.takeLastWhile(String::isNotBlank).map {it.split(",").map(String::toInt) }
        val correctUpdates = updates.filter { update ->
            val applicableRules = rules.filter { rule ->
                update.containsAll(rule)
            }
            applicableRules.all { (prefix, suffix) ->
                update.indexOf(prefix) < update.indexOf(suffix)
            }
        }
        return correctUpdates.sumOf { it[it.size / 2] }
    }

    fun part2(input: List<String>): Int {
        val rules = input.takeWhile(String::isNotBlank).map {
            it.split("|").map(String::toInt)
        }
        val updates = input.takeLastWhile(String::isNotBlank).map {it.split(",").map(String::toInt) }
        val incorrectUpdates = updates.filterNot { update ->
            val applicableRules = rules.filter { rule ->
                update.containsAll(rule)
            }
            applicableRules.all { (prefix, suffix) ->
                update.indexOf(prefix) < update.indexOf(suffix)
            }
        }
        return incorrectUpdates.sumOf { update ->
            val applicableRules = rules.filter { rule ->
                update.containsAll(rule)
            }
            val afters = applicableRules.groupBy { it.last() }.mapValues { (_, value) -> value.map { it.first() } }
            val sortedAfters = afters.entries.sortedBy { (_, value) -> value.size }.map { it.key }
            sortedAfters[sortedAfters.size / 2 - 1]
        }
    }

    val testInput = readSampleInput("Day05")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
