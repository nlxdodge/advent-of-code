import utils.FileUtil


class AOC12 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = FileUtil.getDay()
            val lines = FileUtil.getFileList("AOC$day.txt")

            val grid = generateMutableGridWithInput(lines)
            val s = findCharPosition(grid, 'S'.code)
            val e = findCharPosition(grid, 'E'.code)

            s.height = 96
            e.height = 123

            val part1 = part1(grid, s, e)
            val part2 = part2(lines)

            println("Day $day result 1: $part1")
            println("Day $day result 2: $part2")
        }

        private fun part1(grid: MutableList<MutableList<Node>>, start: Node, end: Node): String {
            return aStarPathFind(grid, start, end).count().toString()
        }

        private fun part2(input: List<String>): String {
            return ""
        }

        private fun aStarPathFind(grid: MutableList<MutableList<Node>>, start: Node, end: Node): List<Node> {
            val cameFrom = mutableMapOf<Node, Node>()
            val openSet = mutableListOf(start)

            while (openSet.isNotEmpty()) {
                val current = openSet.maxBy { x -> x.height }
                if (current.height == end.height) {
                    printGrid(grid, reConstructPath(cameFrom, current))
                    return reConstructPath(cameFrom, current)
                }
                openSet.remove(current)
                val neighbors = findNeighbors(grid, current).sortedByDescending { it.height }
                for (neighbor in neighbors) {
                    if (zeroOrOneOf(neighbor.height, current.height)) {
                        cameFrom[neighbor] = current
                        if (!openSet.contains(neighbor)) {
                            openSet.add(neighbor)
                        }
                    }
                }
            }
            throw RuntimeException("No path found")
        }

        private fun findNeighbors(grid: MutableList<MutableList<Node>>, current: Node): MutableList<Node> {
            val neighbours = mutableListOf<Node>()
            if (current.x + 1 < grid.size) {
                neighbours.add(grid[current.x + 1][current.y])
            }
            if (current.x - 1 >= 0) {
                neighbours.add(grid[current.x - 1][current.y])
            }
            if (current.y + 1 < grid[0].size) {
                neighbours.add(grid[current.x][current.y + 1])
            }
            if (current.y - 1 >= 0) {
                neighbours.add(grid[current.x][current.y - 1])
            }
            return neighbours
        }

        fun bg(n: Int) = "\u001b[48;5;${n}m"

        private fun reConstructPath(cameFrom: MutableMap<Node, Node>, current: Node): MutableList<Node> {
            var subCurrent = current
            val path = mutableListOf(subCurrent)
            while (cameFrom.containsKey(subCurrent)) {
                subCurrent = cameFrom[subCurrent]!!
                path.add(subCurrent)
            }
            return path.reversed().toMutableList()
        }

        private fun zeroOrOneOf(neighbor: Int, current: Int): Boolean {
            var calcCurrent = current
            var calcNeighbor = neighbor
            if (calcCurrent == calcNeighbor)
                return true

            if (calcCurrent + 1 == calcNeighbor)
                return true

            return false
        }

        private fun findCharPosition(grid: MutableList<MutableList<Node>>, find: Int): Node {
            for (line in grid) {
                for (chr in line) {
                    if (chr.height == find) {
                        return chr
                    }
                }
            }
            throw RuntimeException("Search for $find in grid failed!")
        }

        private fun generateMutableGridWithInput(input: List<String>): MutableList<MutableList<Node>> {
            val grid = mutableListOf<MutableList<Node>>()
            for ((x, line) in input.withIndex()) {
                val currentLine = mutableListOf<Node>()
                for ((y, chr) in line.withIndex()) {
                    currentLine.add(Node(x, y, chr.code, chr.toString()))
                }
                grid.add(currentLine)
            }
            return grid
        }

        private fun printGrid(grid: MutableList<MutableList<Node>>, path: MutableList<Node>) {
            for (column in grid) {
                for (node in column) {
                    if (node in path) {
                        print("${bg(40)}${node.height.toChar()}")
                    } else {
                        print("${node.height.toChar()}")
                    }
                }
                println()
            }
        }
    }
}

class Node(
    val x: Int,
    val y: Int,
    var height: Int,
    var char: String,
    var g: Int = Int.MIN_VALUE,
    var f: Int = Int.MIN_VALUE
) {
    override fun toString(): String {
        return "Node(x: $x, y: $y, height: $height, char: $char)"
    }
}
