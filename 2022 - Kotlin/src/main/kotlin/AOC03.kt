import utils.FileUtil
import kotlin.math.roundToInt

class AOC03 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val lines = FileUtil.getFileList("aoc03.txt")

            val part1 = part1(lines)
            val part2 = part2(lines)

            println("Day 3 result 1: $part1")
            println("Day 3 result 2: $part2")
        }

        private fun part1(input: List<String>): String {
            var totalScore = 0
            for (line in input) {
                val half = (line.length / 2).toDouble().roundToInt()
                val first = line.substring(0, half)
                val last = line.substring(half, line.length)
                for (chr in first) {
                    if (last.contains(chr)) {
                        totalScore += calculatePriority(chr)
                        break
                    }
                }
            }
            return totalScore.toString()
        }

        private fun part2(input: List<String>): String {
            var totalScore = 0
            for (lines in input.chunked(3)) {
                for (chr in lines[0]) {
                    if (lines[1].contains(chr) && lines[2].contains(chr)) {
                        totalScore += calculatePriority(chr)
                        break
                    }
                }
            }
            return totalScore.toString()
        }

        private fun calculatePriority(chr: Char): Int {
            return (chr.lowercaseChar() - (if (chr.isUpperCase()) 70 else 96)).code
        }
    }
}

