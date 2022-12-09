package utils

class GridUtil {

    companion object {
        fun generateMutableGrid(size: Int, char: String = "."): MutableList<MutableList<String>> {
            val grid = mutableListOf<MutableList<String>>()
            for (x in 1..size) {
                val lists = mutableListOf<String>()
                for (y in 1..size) {
                    lists.add(char.toString())
                }
                grid.add(lists)
            }
            return grid
        }
    }
}
