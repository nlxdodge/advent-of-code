import utils.FileUtil

class AOC19 {
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
            var bluePrints = mutableListOf<BluePrint>()
            for (bluePrintLine in input) {
                val bluePrint = BluePrint(bluePrintLine)
                bluePrint.getQualityLevel()
                bluePrints.add(bluePrint)
            }
            return bluePrints.maxOf { it.getQualityLevel() }.toString()
        }

        private fun part2(input: List<String>): String {
            return ""
        }
    }
}

class BluePrint(bluePrintLine: String) {
    private var index = 0
    private var oreRobotCost = 0
    private var clayRobotCost = 0
    private var obsidianRobotCost = Pair(0, 0)
    private var geodeRobotCost = Pair(0, 0)
    private var oreRobots = 1
    private var ore = 0
    private var clayRobots = 0
    private var clay = 0
    private var obsidianRobots = 0
    private var obsidian = 0
    private var geodeRobots = 0
    private var geodes = 0

    init {
        val numbers = Regex("[0-9]+").findAll(bluePrintLine).toMutableList().map { it.value.toInt() }
        index = numbers[0]
        oreRobotCost = numbers[1]
        clayRobotCost = numbers[2]
        obsidianRobotCost = Pair(numbers[3], numbers[4])
        geodeRobotCost = Pair(numbers[5], numbers[6])
    }

    fun getQualityLevel(): Int {
        return index * simulate()
    }

    private fun simulate(): Int {
        for (index in 0..24) {
            ore += oreRobots
            clay += clayRobots
            obsidian += obsidianRobots
            geodes += geodeRobots
            constructRobot()
        }
        return geodes
    }

    private fun constructRobot() {
        if (ore >= oreRobotCost && oreRobots < oreRobotCost) {
            oreRobots++
            ore -= oreRobotCost
            return
        }
        if (ore >= clayRobotCost && clayRobots < clayRobotCost) {
            clayRobots++
            ore -= clayRobotCost
            return
        }
        if (ore >= obsidianRobotCost.first && clay >= obsidianRobotCost.second) {
            obsidianRobots++
            ore -= obsidianRobotCost.first
            clay -= obsidianRobotCost.second
            return
        }
        if (ore >= geodeRobotCost.first && obsidian >= geodeRobotCost.second) {
            geodeRobots++
            ore -= geodeRobotCost.first
            obsidian -= geodeRobotCost.second
            return
        }
    }

    override fun toString(): String {
        return "BP: $index (ore: $ore, clay: $clay, obsi: $obsidian, geode: $geodes)" +
                "((oreRobot: $oreRobots, clayRobots: $clayRobots, obisidianRobots: $obsidianRobots, geodeRobots: $geodeRobots))"
    }
}

