package day

class AOC1 extends Day {
  def part1(): String = {
    var zeroCounter = 0
    var dial = 50
    val input = scala.io.Source.fromResource("aoc.txt").getLines()
    for (line <- input) {
      val predial = dial
      val direction = line.charAt(0)
      val distance = line.substring(1).toInt % 100
      direction match {
        case 'R' => dial += distance
        case 'L' => dial -= distance
      }
      if (dial < 0) {
        dial = 100 + dial
      }
      if (dial >= 100) {
        dial = dial - 100
      }
      if (dial == 0) {
        zeroCounter += 1
      }
    }
    zeroCounter.toString
  }

  def part2(): String = {
    var zeroCounter = 0
    var dial = 50
    val input = scala.io.Source.fromResource("aoc1.txt").getLines()
    for (line <- input) {
      val predial = dial
      val direction = line.charAt(0)
      val distance = line.substring(1).toInt % 100
      zeroCounter += line.substring(1).toInt / 100
      direction match {
        case 'R' => dial += distance
        case 'L' => dial -= distance
      }
      if (dial == 0 || dial == 100) {
        dial = 0
        zeroCounter += 1
      } else if (dial < 0) {
        dial = 100 + dial
        if (predial != 0) {
          zeroCounter += 1
        }
      } else if (dial > 100) {
        dial = dial - 100
        if (predial != 0) {
          zeroCounter += 1
        }
      }
    }
    zeroCounter.toString
  }
}
