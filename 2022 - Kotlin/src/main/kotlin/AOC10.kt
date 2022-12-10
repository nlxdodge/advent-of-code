import utils.FileUtil

class AOC10 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = FileUtil.getDay()
            val lines = FileUtil.getFileList("AOC$day.txt")

            val part1And2 = part1And2(lines)

            println("Day $day result 1: ${part1And2.first}")
            println("Day $day result 2:")
            printCRT(part1And2.second)
        }

        private fun part1And2(input: List<String>): Pair<String, MutableList<MutableList<String>>> {
            var crt = mutableListOf(
                ".".repeat(40).map { it.toString() }.toMutableList(),
                ".".repeat(40).map { it.toString() }.toMutableList(),
                ".".repeat(40).map { it.toString() }.toMutableList(),
                ".".repeat(40).map { it.toString() }.toMutableList(),
                ".".repeat(40).map { it.toString() }.toMutableList(),
                ".".repeat(40).map { it.toString() }.toMutableList(),
            )
            var signalStrength = 0
            var cycles = 1
            var x = 1
            for (inst in input) {
                val iterations = if (inst.startsWith("addx")) 2 else 1
                for (currentCycle in 1..iterations) {
                    crt = drawCRT(crt, cycles, x)
                    signalStrength += checkSignalStrength(x, cycles)
                    cycles += 1
                    if (inst.startsWith("addx")) {
                        if (currentCycle == 2) {
                            x += inst.slice(IntRange(5, inst.length - 1)).toInt()
                        }
                    }
                }
            }
            return Pair(signalStrength.toString(), crt)
        }

        private fun drawCRT(
            crt: MutableList<MutableList<String>>,
            cycle: Int,
            xStart: Int
        ): MutableList<MutableList<String>> {
            val cycled = cycle % (6 * 40)
            val pos = IntRange(cycled % 40, cycled / 40)
            if (xStart == pos.first || xStart == pos.first - 1 || xStart == pos.first - 2) {
                crt[pos.last][pos.first] = "█"
            }
            return crt
        }

        private fun printCRT(crt: MutableList<MutableList<String>>) {
            crt.forEach { line -> println(line.reduce { x, y -> x + y }) }
        }

        private fun checkSignalStrength(x: Int, cycles: Int): Int {
            if (cycles == 20 || (cycles - 20) % 40 == 0) {
                return x * cycles
            }
            return 0
        }
    }
}

