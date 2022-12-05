import utils.FileUtil

class AOC05 {
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
            val forbidden = listOf(" ", "[", "]")
            val playingField = mutableListOf<ArrayDeque<String>>()
            val size = input[8].last().toString().toInt()
            for (f in 1..size) {
                playingField.add(ArrayDeque())
            }
            outer@ for (line in input) {
                for (index in line.indices) {
                    if (forbidden.contains(line[index].toString())) {
                        continue
                    }
                    if (line[index].toString() == "1") {
                        break@outer
                    }
                    val realIndex = input[8][index].toString().toInt() - 1
                    if (playingField.size > realIndex) {
                        playingField[realIndex].add(line[index].toString())
                    }
                }
            }
            for (line in input) {
                if (!line.startsWith("move")) {
                    continue
                }
                val values = line.split(" ")
                for (times in 1..values[1].toInt()) {
                    playingField[values[5].toInt() - 1].addFirst(playingField[values[3].toInt() - 1].first())
                    playingField[values[3].toInt() - 1].removeFirstOrNull()
                }
            }
            return playingField.map { it -> it.first() }.joinToString("")
        }

        private fun part2(input: List<String>): String {
            val forbidden = listOf(" ", "[", "]")
            val playingField = mutableListOf<ArrayDeque<String>>()
            val size = input[8].last().toString().toInt()
            for (f in 1..size) {
                playingField.add(ArrayDeque())
            }
            outer@ for (line in input) {
                for (index in line.indices) {
                    if (forbidden.contains(line[index].toString())) {
                        continue
                    }
                    if (line[index].toString() == "1") {
                        break@outer
                    }
                    val realIndex = input[8][index].toString().toInt() - 1
                    if (playingField.size > realIndex) {
                        playingField[realIndex].add(line[index].toString())
                    }
                }
            }
            for (line in input) {
                if (!line.startsWith("move")) {
                    continue
                }
                val values = line.split(" ")
                val toMove = values[1].toInt()

                val tempList = mutableListOf<String>()
                for (times in 1..toMove) {
                    tempList.add(playingField[values[3].toInt() - 1].first())
                    playingField[values[3].toInt() - 1].removeFirstOrNull()
                }
                while (tempList.isNotEmpty()) {
                    playingField[values[5].toInt() - 1].addFirst(tempList.last())
                    tempList.removeLast()
                }
            }
            return playingField.map { it -> it.first() }.joinToString("")
        }
    }
}

