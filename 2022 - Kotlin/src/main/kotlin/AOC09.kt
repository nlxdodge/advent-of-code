import utils.FileUtil
import kotlin.math.abs

class AOC09 {
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
            val size = 800
            val grid = mutableListOf<MutableList<String>>()
            for (x in 1..size) {
                val lists = mutableListOf<String>()
                for (y in 1..size) {
                    lists.add(".")
                }
                grid.add(lists)
            }
            var hPos = IntRange((grid.size - 1) / 2, (grid.size - 1) / 2)
            var oldHPos: IntRange
            var tPos = IntRange((grid.size - 1) / 2, (grid.size - 1) / 2)
            grid[tPos.first][tPos.last] = "#"

            for (command in input) {
                val times = command.slice(IntRange(2, command.length - 1))
                for (t in 1..times.toInt()) {
                    oldHPos = hPos
                    if (command.startsWith("R")) {
                        hPos = IntRange(hPos.first, hPos.last + 1)
                    }
                    if (command.startsWith("L")) {
                        hPos = IntRange(hPos.first, hPos.last - 1)
                    }
                    if (command.startsWith("U")) {
                        hPos = IntRange(hPos.first - 1, hPos.last)
                    }

                    if (command.startsWith("D")) {
                        hPos = IntRange(hPos.first + 1, hPos.last)
                    }
                    if (!isTouching(hPos, tPos)) {
                        tPos = oldHPos
                        grid[tPos.first][tPos.last] = "#"
                    }
//                    print(grid, hPos, tPos)
                }

            }
            print(grid, IntRange(-1, -1), IntRange(-1, -1))
            return (grid.sumOf { it.count { x -> x == "#" } }).toString()
        }

        private fun moveT(): IntRange {
            return IntRange(0, 0)
        }

        private fun part2(input: List<String>): String {
            return ""
        }

        private fun isTouching(hPos: IntRange, tPos: IntRange): Boolean {
            val diffX = hPos.first - tPos.first
            val diffY = hPos.last - tPos.last
            if (abs(diffX) >= 2 || abs(diffY) >= 2) {
                return false
            }
            return true
        }

        private fun print(grid: MutableList<MutableList<String>>, hPos: IntRange, tPos: IntRange) {
            val builder = StringBuilder()
            for (x in 0 until grid.size) {
                for (y in 0 until grid.size) {
                    if (hPos.first == x && hPos.last == y) {
                        builder.append("H")
                    } else if (tPos.first == x && tPos.last == y) {
                        builder.append("T")
                    } else {
                        builder.append(grid[x][y])
                    }
                }
                builder.append("\r\n")
            }
            FileUtil.writeToFile(builder.toString())
//            println(builder.toString())
        }
    }
}
