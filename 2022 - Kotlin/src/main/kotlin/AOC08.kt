import utils.FileUtil

class AOC08 {
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
            val grid = mutableListOf<MutableList<Pair<Int, Boolean>>>()
            for (size in input.indices) {
                grid.add(mutableListOf())
            }
            for ((index, line) in input.withIndex()) {
                for (chr in line) {
                    grid[index].add(Pair(chr.toString().toInt(), false))
                }
            }
            var counter = 0
            for ((first, lineOf) in grid.withIndex()) {
                for ((last, tree) in lineOf.withIndex()) {
                    counter += if (checkTreeVisable(grid, IntRange(first, last))) {
                        grid[first][last] = Pair(grid[first][last].first, true)
                        1
                    } else {
                        0
                    }
                }
            }
            return counter.toString()
        }

        private fun part2(input: List<String>): String {
            val grid = mutableListOf<MutableList<Pair<Int, Boolean>>>()
            for (size in input.indices) {
                grid.add(mutableListOf())
            }
            for ((index, line) in input.withIndex()) {
                for (chr in line) {
                    grid[index].add(Pair(chr.toString().toInt(), false))
                }
            }
            var counter = 0
            for ((first, lineOf) in grid.withIndex()) {
                for ((last, tree) in lineOf.withIndex()) {
                    counter += if (checkTreeVisable(grid, IntRange(first, last))) {
                        grid[first][last] = Pair(grid[first][last].first, true)
                        1
                    } else {
                        0
                    }
                }
            }
            var trees = mutableListOf<Int>()
            for ((first, lineOf) in grid.withIndex()) {
                for ((last, tree) in lineOf.withIndex()) {
                    if (grid[first][last].second == true) {
                        trees.add(generateScore(grid, IntRange(first, last)))
                    }
                }
            }
            return trees.maxOf { it }.toString()
        }

        private fun generateScore(grid: MutableList<MutableList<Pair<Int, Boolean>>>, pos: IntRange): Int {
            var muls = mutableListOf(0, 0, 0, 0)
            val treeHeight = grid[pos.first][pos.last].first
            var posCheck = 1
            while (pos.last + posCheck <= grid.size - 1) {
                if (grid[pos.first][pos.last + posCheck].first >= treeHeight) {
                    muls[0] = posCheck
                    break
                }
                if (pos.last + posCheck >= grid.size - 1) {
                    muls[0] = posCheck
                }
                posCheck += 1
            }
            posCheck = 1
            while (pos.last - posCheck >= 0) {
                if (grid[pos.first][pos.last - posCheck].first >= treeHeight) {
                    muls[1] = posCheck
                    break
                }
                if (pos.last - posCheck <= 0) {
                    muls[1] = posCheck
                }
                posCheck += 1
            }
            posCheck = 1
            while (pos.first + posCheck <= grid.size - 1) {
                if (grid[pos.first + posCheck][pos.last].first >= treeHeight) {
                    muls[2] = posCheck
                    break
                }
                if (pos.first + posCheck >= grid.size - 1) {
                    muls[2] = posCheck
                }
                posCheck += 1
            }
            posCheck = 1
            while (pos.first - posCheck >= 0) {
                if (grid[pos.first - posCheck][pos.last].first >= treeHeight) {
                    muls[3] = posCheck
                    break
                }
                if (pos.first - posCheck <= 0) {
                    muls[3] = posCheck
                }
                posCheck += 1
            }

            return muls[0] * muls[1] * muls[2] * muls[3]
        }


        private fun checkTreeVisable(grid: MutableList<MutableList<Pair<Int, Boolean>>>, pos: IntRange): Boolean {
            // outer ring always true
            if (pos.first <= 0 || pos.first >= grid.size - 1 || pos.last <= 0 || pos.last >= grid.size - 1) {
                return true
            }
            val treeHeight = grid[pos.first][pos.last].first
            var posCheck = 0
            val seeFromDirection = mutableListOf(true, true, true, true)
            while (pos.last + posCheck < grid.size - 1) {
                posCheck += 1
                if (grid[pos.first][pos.last + posCheck].first >= treeHeight) {
                    seeFromDirection[0] = false
                    break
                }
            }
            posCheck = 0
            while (pos.last - posCheck > 0) {
                posCheck += 1
                if (grid[pos.first][pos.last - posCheck].first >= treeHeight) {
                    seeFromDirection[1] = false
                    break
                }
            }
            posCheck = 0
            while (pos.first + posCheck < grid.size - 1) {
                posCheck += 1
                if (grid[pos.first + posCheck][pos.last].first >= treeHeight) {
                    seeFromDirection[2] = false
                    break
                }
            }
            posCheck = 0
            while (pos.first - posCheck > 0) {
                posCheck += 1
                if (grid[pos.first - posCheck][pos.last].first >= treeHeight) {
                    seeFromDirection[3] = false
                    break
                }
            }
            return seeFromDirection.any { it }
        }
    }
}
