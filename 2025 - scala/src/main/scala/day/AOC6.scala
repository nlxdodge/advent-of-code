package day

class AOC6 extends Day {
  def part1(): String = {
    val input = scala.io.Source.fromResource("aoc.txt").getLines().toList
    val calcSize = input.head.split(" ").length
    val calculations = List[List[String]]()
    for(line <- input) {
      val parts = line.split(" ")
      calculations.
    }
    
    ""
  }

  def part2(): String = {
    ""
  }
}
