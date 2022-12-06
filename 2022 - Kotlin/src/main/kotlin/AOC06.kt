import utils.FileUtil

class AOC06 {
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
            val markerSize = 4
            var startMarker = 0
            for (index in input[0].indices) {
                startMarker = index
                val list = mutableListOf<Char>()
                for (parseIndex in 1..markerSize) {
                    list.add(input[0][index + parseIndex])
                }
                if (list.distinct().size == markerSize) {
                    break
                }
            }
            return (startMarker + markerSize + 1).toString()
        }


        private fun part2(input: List<String>): String {
            val markerSize = 14
            var startMarker = 0
            for (index in input[0].indices) {
                startMarker = index
                val list = mutableListOf<Char>()
                for (parseIndex in 1..markerSize) {
                    list.add(input[0][index + parseIndex])
                }
                if (list.distinct().size == markerSize) {
                    break
                }
            }
            return (startMarker + markerSize + 1).toString()
        }
    }
}

