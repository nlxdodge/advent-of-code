import utils.FileUtil

class Template {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val lines = FileUtil.getFileList("aoc01.txt")

            val part1 = part1(lines)
            val part2 = part2(lines)

            println("Day 1 result 1: $part1")
            println("Day 1 result 2: $part2")
        }

        private fun part1(input: List<String>): String {
            for (line in input) {
                println("Line: $line")
            }
            return input.first()
        }

        private fun part2(input: List<String>): String {
            return input.first()
        }
    }
}

