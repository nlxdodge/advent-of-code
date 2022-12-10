import utils.FileUtil

class AOC10 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = FileUtil.getDay()
            val lines = FileUtil.getFileList("AOC$day.txt")

            val part1 = part1(lines)

            println("Day $day result 1: $part1")
            println("Day $day result 2 is visible in the terminal")
        }

        private fun part1(input: List<String>): String {
            var crt = mutableListOf(
                ".".repeat(40).toMutableList(),
                ".".repeat(40).toMutableList(),
                ".".repeat(40).toMutableList(),
                ".".repeat(40).toMutableList(),
                ".".repeat(40).toMutableList(),
                ".".repeat(40).toMutableList(),
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
            printCRT(crt)
            return signalStrength.toString()
        }

        private fun part2() {

        }

        private fun drawCRT(
            crt: MutableList<MutableList<Char>>,
            cycle: Int,
            xStart: Int
        ): MutableList<MutableList<Char>> {
            val cycled = cycle % (6 * 40)
            val pos = IntRange(cycled % 40, cycled / 40)
            if (xStart == pos.first || xStart == pos.first - 1 || xStart == pos.first - 2) {
                crt[pos.last][pos.first] = '█'
            }
            return crt
        }

        private fun printCRT(crt: MutableList<MutableList<Char>>) {
            for ((x, line) in crt.withIndex()) {
                for ((y, chr) in line.withIndex()) {
                    print(chr)
                }
                println()
            }
        }

        private fun checkSignalStrength(x: Int, cycles: Int): Int {
            if (cycles == 20 || (cycles - 20) % 40 == 0) {
                return x * cycles
            }
            return 0
        }
    }
}

