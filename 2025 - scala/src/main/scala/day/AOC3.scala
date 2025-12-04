package day

class AOC3 extends Day {
  def part1(): String = {
    var battiesCounter = 0
    val input = scala.io.Source.fromResource("aoc.txt").getLines().toList
    for(line <- input) {
      var batteries = ""
      val lineListFull = line.split("").map(x => x.toInt).toList
      val lineList = lineListFull.init
      batteries += lineList.max.toString
      val (before, after) = lineListFull.splitAt(lineListFull.indexOf(lineList.max) + 1)
      batteries += after.max.toString
      battiesCounter += batteries.toInt
    }
    battiesCounter.toString
  }



  def part2(): String = {
    var battiesCounter = 0.toLong
    val input = scala.io.Source.fromResource("aoc.txt").getLines().toList
    for(line <- input) {
      var batteries = ""
      var lineList = line.split("").map(x => x.toInt).toList
      for(end <- 12 to 1 by -1) {
        val nextMaxInList = nextMax(lineList, lineList.length - end + 1)
        batteries += nextMaxInList
        lineList = lineList.splitAt(lineList.indexOf(nextMaxInList) + 1)._2
      }
      battiesCounter += batteries.toLong
    }
    battiesCounter.toString
  }

  def nextMax(list: List[Int], space: Int): Int = {
    val (left, right) = list.splitAt(space)
    if(left.isEmpty) {
      return right.max
    }
    left.max
  }
}
