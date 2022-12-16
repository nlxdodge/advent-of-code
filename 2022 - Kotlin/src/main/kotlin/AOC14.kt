import utils.FileUtil
import utils.GridUtil

class AOC14 {
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

        private fun part1(lines: MutableList<String>, part2: Boolean = false): String {
            val grid = drawLines(lines)
            GridUtil.gridToFile("grid", grid)
            var sandPos = IntRange(500, 0)
            while (true) {
                val posBelow = IntRange(sandPos.first, sandPos.last + 1)
                val posBelowLeft = IntRange(sandPos.first + 1, sandPos.last + 1)
                val posBelowRight = IntRange(sandPos.first - 1, sandPos.last + 1)
                // break if input is stuck
                if (grid[sandPos.first][sandPos.last] == "0" && part2) {
                    break
                }
                // break if abyss
                if ((posBelow.last >= grid[0].size || posBelow.first >= grid.size) && !part2) {
                    break
                }
                if (grid[posBelow.first][posBelow.last] == ".") {
                    sandPos = posBelow
                } else if (grid[posBelowRight.first][posBelowRight.last] == ".") {
                    sandPos = posBelowRight
                } else if (grid[posBelowLeft.first][posBelowLeft.last] == ".") {
                    sandPos = posBelowLeft
                } else {
                    grid[sandPos.first][sandPos.last] = "0"
                    sandPos = IntRange(500, 0)
                    continue
                }
            }
            GridUtil.gridToFile("test", grid)
            return grid.flatten().count { it == "0" }.toString()
        }

        private fun part2(input: MutableList<String>): String {
            return part1(input, true)
        }

        private fun drawLines(lines: MutableList<String>): MutableList<MutableList<String>> {
            val depth = lines.flatMap { it.split(" -> ") }.map { it.split(",")[1] }.maxOf { it.toInt() } + 3
            var drawGrid = GridUtil.generateMutableGrid(IntRange(2000, depth), ".")
            for (line in lines) {
                val lineCords = mutableListOf<IntRange>()
                val cordsPairs = line.split(" -> ")
                for (cord in cordsPairs) {
                    val cordSplit = cord.split(",").map { it.toInt() }
                    lineCords.add(IntRange(cordSplit[0], cordSplit[1]))
                }
                for (index in lineCords.indices) {
                    if (lineCords.size > index + 1) {
                        drawGrid = GridUtil.drawLineOnGrid(drawGrid, lineCords[index], lineCords[index + 1], '█')
                    }
                }
            }
            return drawGrid
        }
    }
}

