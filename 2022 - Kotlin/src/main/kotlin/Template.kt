import utils.FileUtil

class Template {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = FileUtil.getDay()
            val lines = FileUtil.getFileList("AOC$day.txt")

            val part1 = part1(lines)
            val part2 = part2(lines)

            println("Day $day result 1: $part1")
            println("Day $day result 2: $part2")
        }

        private fun part1(input: List<String>): String {
            return ""
        }

        private fun part2(input: List<String>): String {
            return ""
        }
    }
}

