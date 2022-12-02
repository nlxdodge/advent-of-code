import utils.FileUtil

class AOC02 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val lines = FileUtil.getFileList("aoc02.txt")

            val part1 = part1(lines)
            val part2 = part2(lines)

            println("Day 1 result 1: $part1")
            println("Day 1 result 2: $part2")
        }

        fun part1(input: List<String>): String {
            var score = 0
            for (line in input) {
                val you = line[2].code - 23
                val enemy = line[0].code

                score += you - 64
                // 65 steen
                // 66 papier
                // 67 schaar

//                if(enemy % 3 + 1 == you) {
//                    // win
//                    score += 6
//                } else if (you % 3 + 1 == enemy) {
//                    // lose
//                } else {
//                    // draw
//                    score += 3
//                }


                if(you == enemy) {
                    score += 3
                }
                if(you == 65 && enemy == 66) {
                    score += 0
                }
                if(you == 65 && enemy == 67) {
                    score += 6
                }
                if(you == 66 && enemy == 65) {
                    score += 6
                }
                if(you == 66 && enemy == 67) {
                    score += 0
                }
                if(you == 67 && enemy == 65) {
                    score += 0
                }
                if(you == 67 && enemy == 66) {
                    score += 6
                }

            }
            return score.toString()
        }

        fun part2(input: List<String>): String {
            var score = 0

            for (line in input) {
                val you = line[2].code - 23
                val enemy = line[0].code

                // 65 steen
                // 66 papier
                // 67 schaar
                if(you == 65) { // lose
                    if(enemy == 65) {
                        score += 3
                    }
                    if(enemy == 66) {
                        score += 1
                    }
                    if(enemy == 67) {
                        score += 2
                    }
                }
                if(you == 66) { // draw
                    if(enemy == 65) {
                        score += 4
                    }
                    if(enemy == 66) {
                        score += 5
                    }
                    if(enemy == 67) {
                        score += 6
                    }
                }
                if(you == 67) { // win
                    if(enemy == 65) {
                        score += 8
                    }
                    if(enemy == 66) {
                        score += 9
                    }
                    if(enemy == 67) {
                        score += 7
                    }
                }
            }
            return score.toString()
        }
    }
}

