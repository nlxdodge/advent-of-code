package utils

class GridUtil {

    companion object {
        fun generateMutableGrid(size: IntRange, char: String = "."): MutableList<MutableList<String>> {
            val grid = mutableListOf<MutableList<String>>()
            for (x in 1..size.first) {
                val lists = mutableListOf<String>()
                for (y in 1..size.last) {
                    lists.add(char)
                }
                grid.add(lists)
            }
            return grid
        }

        fun gridToFile(filename: String, grid: List<List<String>>) {
            val builder = StringBuilder()
            for (line in grid) {
                builder.append(line)
                builder.append("\r\n")
            }
            FileUtil.writeToFile(builder.toString())
        }

        fun drawLineOnGrid(
            grid: MutableList<MutableList<String>>,
            from: IntRange,
            to: IntRange,
            char: Char = '#'
        ): MutableList<MutableList<String>> {
            val posToDrawX = Math.min(from.first, to.first).rangeTo(Math.max(from.first, to.first)).toList()
            val posToDrawY = Math.min(from.last, to.last).rangeTo(Math.max(from.last, to.last)).toList()
            val posToDraw = mutableListOf<IntRange>()
            if (posToDrawX.size > posToDrawY.size) {
                for (x in posToDrawX) {
                    posToDraw.add(IntRange(x, posToDrawY[0]))
                }
            } else {
                for (y in posToDrawY) {
                    posToDraw.add(IntRange(posToDrawX[0], y))
                }
            }
            for (pos in posToDraw) {
                grid[pos.first][pos.last] = char.toString()
            }
            return grid
        }

        fun generateMutableGridWithInput(input: List<String>): MutableList<MutableList<String>> {
            val grid = mutableListOf<MutableList<String>>()
            for (line in input) {
                grid.add(line.toCharArray().map { it.toString() }.toMutableList())
            }
            return grid
        }
    }
}
