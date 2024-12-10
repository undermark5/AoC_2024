private enum class Dir(val rowOffset: Int, val colOffset: Int) {
    N(-1, 0),
    NE(-1, 1),
    E(0, 1),
    SE(1, 1),
    S(1, 0),
    SW(1,-1),
    W(0, -1),
    NW(-1, -1)
}
val chars = "XMAS"
fun main() {
    fun part1(input: List<String>): Int {
        fun countXmas(row: Int, col: Int): Int {
            var validOffsets = Dir.entries.toList()
            for (i in 1..3) {
                val target = chars[i]
                validOffsets = validOffsets.mapNotNull {
                    try {
                        val rowOffset = it.rowOffset * i
                        val colOffset = it.colOffset * i

                        if (input[row + rowOffset][col + colOffset] == target) {
                            it
                        } else {
                            null
                        }
                    } catch (_: Exception) {
                        null
                    }
                }
            }
            return validOffsets.size
        }
        var total = 0
        input.forEachIndexed { row, s ->
            s.forEachIndexed { col, c ->
                if (c == 'X') {
                    total += countXmas(row,col)
                }
            }
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        fun isXMas(row: Int, col: Int): Boolean {
            return try {
                val topLeft = input[row - 1][col - 1]
                val topRight = input[row - 1][col + 1]
                val bottomLeft = input[row + 1][col - 1]
                val bottomRight = input[row + 1][col + 1]
                val forward = "${bottomLeft}A${topRight}"
                val backward = "${topLeft}A${bottomRight}"
                when (forward) {
                    "SAM", "MAS" -> {
                        when(backward) {
                            "SAM", "MAS" -> true
                            else -> false
                        }
                    }
                    else -> false
                }
            } catch (_: Exception) {
                false
            }
        }
        input.forEachIndexed { row, s ->
            s.forEachIndexed { col, c ->
                if (c == 'A' && isXMas(row,col)) {
                    total++
                }
            }
        }
        return total
    }

    val testInput = readSampleInput("Day04")
    check(part1(testInput).also { it.println() } == 18)
    check(part2(testInput).also{it.println()} == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
