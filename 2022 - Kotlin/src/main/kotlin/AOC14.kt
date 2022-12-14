import utils.FileUtil
import utils.GridUtil

class AOC14 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = FileUtil.getDay()
            val lines = FileUtil.getFileList("AOC$day.txt")
            val grid = GridUtil.generateMutableGrid(600, ".")

            val part1 = part1(grid, lines)
            val part2 = part2(lines)

            println("Day $day result 1: $part1")
            println("Day $day result 2: $part2")
        }

        private fun part1(grid: MutableList<MutableList<String>>, lines: MutableList<String>): String {
            var drawGrid = grid
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


            // do sand emulation
            var abyss = false
            var sandPos = IntRange(500, 0)
            while (!abyss) {
                val posBelow = IntRange(sandPos.first, sandPos.last + 1)
                var posBelowLeft = IntRange(sandPos.first + 1, sandPos.last + 1)
                var posBelowRight = IntRange(sandPos.first - 1, sandPos.last + 1)
                if(grid[sandPos.first][sandPos.last] == "0") {
                    GridUtil.gridToFile("test", grid)
                    throw Exception("Valve is stuck with sand it's not ging in the abyss")
                }

                if(posBelow.last > grid.size) {
                    abyss = true
                }
                if (grid[posBelow.first][posBelow.last] == ".") {
                    sandPos = posBelow
                    continue
                } else {
                    while(grid[posBelowLeft.first][posBelowLeft.last] == ".") {
                        if (grid[posBelowLeft.first][posBelowLeft.last] != ".") {
                            grid[posBelowLeft.first][posBelowLeft.last] = "0"
                            sandPos = IntRange(500, 0)
                            continue
                        }
                        posBelowLeft = IntRange(posBelowLeft.first + 1, posBelowLeft.last + 1)
                    }
                    while(grid[posBelowRight.first][posBelowRight.last] == ".") {
                        if (grid[posBelowRight.first][posBelowRight.last] != ".") {
                            grid[posBelowRight.first][posBelowRight.last] = "0"
                            sandPos = IntRange(500, 0)
                            continue
                        }
                        posBelowRight = IntRange(posBelowRight.first + 1, posBelowRight.last + 1)
                    }
                    grid[sandPos.first][sandPos.last] = "0"
                    sandPos = IntRange(500, 0)
                    continue
                }
            }
            GridUtil.gridToFile("test", grid)
            return ""
        }

        private fun part2(input: List<String>): String {
            return ""
        }
    }
}

