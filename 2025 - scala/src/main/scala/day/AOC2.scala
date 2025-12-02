package day

class AOC2 extends Day {
  def part1(): String = {
    var invalidIdCalc = 0.toLong
    val input = scala.io.Source.fromResource("aoc.txt").getLines().toList
    val combos = input.head.split(',').toList
    for (combo <- combos) {
      val parts = combo.split('-')
      val start = parts(0).toLong
      val end = parts(1).toLong
      for (id <- start to end) {
        val idStr = id.toString
        val splits = idStr.splitAt(idStr.length / 2)
        if (splits._1 == splits._2) {
          invalidIdCalc += id
        }
      }
    }
    invalidIdCalc.toString
  }

  def part2(): String = {
    var invalidIdCalc = 0.toLong
    val input = scala.io.Source.fromResource("aoc.txt").getLines().toList
    val combos = input.head.split(',').toList
    for (combo <- combos) {
      val parts = combo.split('-')
      val start = parts(0).toLong
      val end = parts(1).toLong
      for (id <- start to end) {
        val idStr = id.toString
        val splits = idStr.splitAt(idStr.length / 2)
        if (splits._1 == splits._2) {
          invalidIdCalc += id
        } else {
          for (end <- 1 to idStr.length) {
            val stringPart = idStr.substring(0, end)
            val noCharLeft = idStr.replace(stringPart, "").isEmpty
            val atLeastTwoFound = stringPart.r.findAllMatchIn(idStr).size >= 2
            if (noCharLeft && atLeastTwoFound) {
              invalidIdCalc += id
            }
          }
        }
      }
    }
    invalidIdCalc.toString
  }
}