import utils.FileUtil
import java.lang.StringBuilder

class AOC04 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day  = this::class.java.declaringClass.simpleName.substring(3, 5)
            val lines = FileUtil.getFileList("AOC$day.txt")

            val part1 = part1(lines)
            val part2 = part2(lines)

            println("Day $day result 1: $part1")
            println("Day $day result 2: $part2")
        }

        private fun part1(input: List<String>): String {
            var counter = 0
            for (line in input) {
                val pairs = mutableListOf<String>()
                val lines = line.split(",")
                for (ranges in lines) {
                    val subrange = ranges.split("-")
                    val from = subrange[0].toInt()
                    val to = subrange[1].toInt()
                    val items = StringBuilder()
                    for (item in from.rangeTo(to)) {
                        items.append("-")
                        items.append(item)
                        items.append("-")
                    }
                    pairs.add(items.toString())
                }

                if (pairs.get(0).contains(pairs.get(1)) || pairs.get(1).contains(pairs.get(0))) {
                    counter++
                }
            }
            return counter.toString()
        }

        private fun part2(input: List<String>): String {
            var counter = 0
            for (line in input) {
                val pairs = mutableListOf<List<Int>>()
                for (ranges in line.split(",")) {
                    val subrange = ranges.split("-")
                    val from = subrange[0].toInt()
                    val to = subrange[1].toInt()
                    val items = mutableListOf<Int>()
                    for (item in from.rangeTo(to)) {
                        items.add(item)
                    }
                    pairs.add(items)

                }
                for (chr in pairs.get(0)) {
                    if (pairs[1].contains(chr)) {
                        counter++
                        break
                    }
                }
            }
            return counter.toString()
        }
    }
}

