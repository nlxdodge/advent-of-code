import utils.FileUtil

class Template {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val lines = FileUtil.getFileStream("aoc1.txt")
            var part1 = ""
            var part2 = ""

            for (line in lines) {
                println("Line: $line")
            }

            println("Day 1 result 1: $part1")
            println("Day 1 result 2: $part2")
        }

        fun part1(input: String): String {
            return input
        }

        fun part2(input: String): String {
            return input
        }
    }
}

