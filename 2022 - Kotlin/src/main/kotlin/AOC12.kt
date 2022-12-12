import utils.FileUtil

class AOC12 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = FileUtil.getDay()
            val lines = FileUtil.getFileList("AOC$day.txt")


            var grid = generateMutableGridWithInput(lines)
            val s = findCharPosition(grid, 'S'.code)
            val e = findCharPosition(grid, 'E'.code)

            val part1 = part1(grid, s, e)
            val part2 = part2(lines)

            println("Day $day result 1: $part1")
            println("Day $day result 2: $part2")
        }

        private fun part1(grid: MutableList<MutableList<Node>>, start: Node, end: Node): String {
            AStarPathFind(grid, start, end)

            return ""
        }

        private fun part2(input: List<String>): String {
            return ""
        }

        private fun AStarPathFind(grid: MutableList<MutableList<Node>>, start: Node, end: Node) {
            val cameFrom = mutableMapOf<Node, Node>()
            val openSet = mutableListOf(start)

            while (!openSet.isEmpty()) {
                var current = openSet.minBy { x -> x.height }
                if (current == end) {
                    // found the path
                    println("FOUND IT")
                }
                openSet.remove(current)
                for (neighbor in findNeighbors(grid, current)) {
                    val gScore = current.g + neighbor.height
                    if (gScore < neighbor.g) {
                        cameFrom[neighbor] = current
                        neighbor.g = gScore
                        neighbor.f = gScore + neighbor.height
                        if (!openSet.contains(neighbor)) {
                            openSet.add(neighbor)
                        }
                    }
                }
            }

        }

        private fun findNeighbors(grid: MutableList<MutableList<Node>>, current: Node): MutableList<Node> {
            val neighbours = mutableListOf<Node>()
            if (current.x + 1 < grid.size) {
                neighbours.add(grid[current.x + 1][current.y])
            }
            if (current.x - 1 > 0) {
                neighbours.add(grid[current.x - 1][current.y])
            }
            if (current.y + 1 < grid[0].size) {
                neighbours.add(grid[current.x][current.y + 1])
            }
            if (current.y - 1 > 0) {
                neighbours.add(grid[current.x][current.y - 1])
            }
            return neighbours
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
                    currentLine.add(Node(x, y, chr.code))
                }
                grid.add(currentLine)
            }
            return grid
        }
    }
}

class Node(val x: Int, val y: Int, var height: Int, var g: Int = Int.MAX_VALUE, var f: Int = Int.MAX_VALUE) {
    override fun toString(): String {
        return "Node(x: $x, y: $y, height: $height)"
    }
}
