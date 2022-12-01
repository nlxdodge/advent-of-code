import utils.FileUtil

class AOC01 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val lines = FileUtil.getFileList("aoc01.txt")

            val part1 = part1(lines)
            val part2 = part2(lines)

            println("Day 1 result 1: $part1")
            println("Day 1 result 2: $part2")
        }

        fun part1(input: List<String>): String {
            var biggestCallories = 0
            var calories = 0
            input.forEach { food ->
                if (food.equals("")) {
                    if (biggestCallories < calories) {
                        biggestCallories = calories
                    }
                    calories = 0
                } else {
                    calories += food.toInt()
                }
            }
            return biggestCallories.toString()
        }

        fun part2(input: List<String>): String {
            var elfCalories = hashMapOf(-3 to 0, -2 to 0, -1 to 0)
            var index = 1
            var calories = 0
            input.forEach { food ->
                if (food.equals("")) {
                    var lowest = Int.MAX_VALUE
                    elfCalories.forEach {
                        if (it.value < lowest) {
                            lowest = it.value
                        }
                    }

                    for ((elf, theCalories) in elfCalories) {
                        if (theCalories < calories && theCalories == lowest) {
                            elfCalories.put(index, calories)
                            elfCalories.remove(elf)
                            break
                        }
                    }
                    index++
                    calories = 0
                } else {
                    calories += food.toInt()
                }
            }
            return elfCalories.values.sum().toString()
        }
    }
}

