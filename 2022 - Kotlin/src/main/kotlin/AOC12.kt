import utils.FileUtil
import utils.GridUtil

class AOC12 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = FileUtil.getDay()
            val lines = FileUtil.getFileList("AOC$day.txt")

            var grid = GridUtil.generateMutableGridWithInput(lines)

            val part1 = part1(lines)
            val part2 = part2(lines)

            println("Day $day result 1: $part1")
            println("Day $day result 2: $part2")
        }

        private fun part1(grid: MutableList<MutableList<String>>): String {


            return ""
        }

        private fun part2(input: List<String>): String {
            return ""
        }

        private fun AStarPathFind(grid: MutableList<MutableList<String>>, start: Node, end: Node) {
            val cameFrom = mutableMapOf<Node, Node>()
            val openSet = mutableListOf(start)

            while (!openSet.isEmpty()) {
                var current = openSet.minBy { Node::compareTo }
            }

        }
    }
}

class Node(val x: Int, val y: Int, val height: Int) : Comparable<Node> {
    override fun compareTo(other: Node): Int {
        return height.compareTo(other.height)
    }

    override fun toString(): String {
        return "Node(x: $x, y: $y, height: $height)"
    }
}

