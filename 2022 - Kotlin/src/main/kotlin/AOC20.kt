import utils.FileUtil

class AOC20 {
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
            val cypher = mutableListOf<Int>()
            for (line in input) {
                cypher.add(line.toInt())
            }
            val looper = mutableListOf<Int>()
            looper.addAll(cypher)
            for (item in looper) {
                if (cypher.indexOf(item) + item < 0) {
                    var fits = true
                    do {
                        cypher.indexOf(item) + item


                        if (cypher.indexOf(item) + item > 0 && cypher.indexOf(item) + item < cypher.size) {
                            fits = false
                        }
                    } while (fits)
                }
                if (cypher.indexOf(item) + item > cypher.size) {
                    // calculate the new position
                }
                cypher.add(cypher.indexOf(item) + item, item)
                cypher.remove(item)
                println("OOF")
            }

            return ""
        }

        private fun part2(input: List<String>): String {
            return ""
        }
    }
}

