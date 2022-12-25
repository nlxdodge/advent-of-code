import utils.FileUtil

class AOC18 {
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
            var surfaceArea = 0
            val droplets = mutableListOf<Droplet>()
            for (line in input) {
                val split = line.split(",").map { it.toInt() }
                droplets.add(Droplet(split[0], split[1], split[2]))
            }
            for (droplet in droplets) {
                surfaceArea += droplet.calculateSurfaceArea(droplets)
            }
            return (surfaceArea).toString()
        }

        private fun part2(input: List<String>): String {
            return ""
        }
    }
}

class Droplet(private val x: Int, private val y: Int, private val z: Int) {
    fun calculateSurfaceArea(droplets: MutableList<Droplet>): Int {
        return 6 - droplets.filter { isNeighbor(it) }.toMutableList().size
    }

    private fun isNeighbor(other: Droplet): Boolean {
        val xDiff = other.x - x
        val yDiff = other.y - y
        val zDiff = other.z - z
        if((xDiff == 1 || xDiff == -1) && yDiff == 0 && zDiff == 0) {
          return true
        }
        if((yDiff == 1 || yDiff == -1) && xDiff == 0 && zDiff == 0) {
            return true
        }
        if((zDiff == 1 || zDiff == -1) && yDiff == 0 && xDiff == 0) {
            return true
        }
        return false
    }

    override fun toString(): String {
        return "Droplet($x, $y, $z)"
    }
}

