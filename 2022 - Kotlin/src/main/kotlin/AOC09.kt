import utils.FileUtil
import utils.GridUtil.Companion.generateMutableGrid
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
            val grid = generateMutableGrid(800)
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
                }

            }
            return (grid.sumOf { it.count { x -> x == "#" } }).toString()
        }

        private fun part2(input: List<String>): String {
            var grid = generateMutableGrid(30)

            val snakes = mutableListOf<SnakeNode>()

            val middleRange = IntRange((grid.size - 1) / 2, (grid.size - 1) / 2)

            snakes.add(SnakeNode("H", null, middleRange, middleRange))
            for (s in 1..9) {
                snakes.add(SnakeNode(s.toString(), snakes.last(), middleRange, middleRange))
            }
            grid[snakes.last().pos.first][snakes.last().pos.last] = "#"

            print(grid, snakes)

            for (command in input) {
                val times = command.slice(IntRange(2, command.length - 1))
                for (t in 1..times.toInt()) {
                    snakes.first().oldPos = snakes.first().pos
                    if (command.startsWith("R")) {
                        snakes.first().pos = IntRange(snakes.first().pos.first, snakes.first().pos.last + 1)
                    }
                    if (command.startsWith("L")) {
                        snakes.first().pos = IntRange(snakes.first().pos.first, snakes.first().pos.last - 1)
                    }
                    if (command.startsWith("U")) {
                        snakes.first().pos = IntRange(snakes.first().pos.first - 1, snakes.first().pos.last)
                    }
                    if (command.startsWith("D")) {
                        snakes.first().pos = IntRange(snakes.first().pos.first + 1, snakes.first().pos.last)
                    }
                    for (snake in snakes.subList(1, snakes.indices.last)) {
                        if (!snake.isTouchingParent()) {
                            snake.oldPos = snake.pos
                            snake.pos = snake.parent?.oldPos ?: snake.pos
                        }
                    }
                    grid[snakes.last().pos.first][snakes.last().pos.last] = "#"
                }
                print(grid, snakes)

            }
            return (grid.sumOf { it.count { x -> x == "#" } }).toString()
        }

        private fun isTouching(hPos: IntRange, tPos: IntRange): Boolean {
            val diffX = hPos.first - tPos.first
            val diffY = hPos.last - tPos.last
            if (abs(diffX) >= 2 || abs(diffY) >= 2) {
                return false
            }
            return true
        }

        private fun print(grid: MutableList<MutableList<String>>, snakes: MutableList<SnakeNode>) {
            for (snake in snakes.reversed()) {
                grid[snake.pos.first][snake.pos.last] = snake.name
            }
            val builder = StringBuilder()
            for (x in 0 until grid.size) {
                for (y in 0 until grid.size) {
                    builder.append(grid[x][y])
                }
                builder.append("\r\n")
            }
//            FileUtil.writeToFile(builder.toString())
            println(builder.toString())
        }
    }
}

class SnakeNode(val name: String, val parent: SnakeNode?, var pos: IntRange, var oldPos: IntRange) {


    fun isTouchingParent(): Boolean {
        val diffX = (parent?.pos?.first ?: pos.first) - pos.first
        val diffY = (parent?.pos?.last ?: pos.last) - pos.last
        if (abs(diffX) >= 2 || abs(diffY) >= 2) {
            return false
        }
        return true
    }

    override fun toString(): String {
        return "Snake $name: x: ${pos.first} y: ${pos.last}"
    }
}
