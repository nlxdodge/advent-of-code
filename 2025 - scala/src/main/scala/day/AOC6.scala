package day

import scala.collection.mutable

class AOC6 extends Day {
  def part1(): String = {
    val input = scala.io.Source.fromResource("aoc.txt").getLines().toList
    val calcSize = input.head.split(" ").length
    var calculations: mutable.ListBuffer[mutable.ListBuffer[String]] = mutable.ListBuffer.empty
    for(line <- input) {
      calculations.append(line.split(" ").filter(_.trim.nonEmpty).to(mutable.ListBuffer))
    }

    var totalSum = 0L

    calculations = calculations.transpose

    for(calc <- calculations) {
      var totalResult = calc.remove(0).toLong
      val operator = calc.remove(calc.size - 1)
      for(number <- calc) {
        val currentResult = operator match {
          case "*" => totalResult * number.toLong
          case "+" => totalResult + number.toLong
        }
        totalResult = currentResult
      }
      totalSum += totalResult
    }
    
    totalSum.toString
  }

  def part2(): String = {
    val input = scala.io.Source.fromResource("aoc.txt").getLines().toList
    val calcSize = input.head.split(" ").length
    var calculations: mutable.ListBuffer[mutable.ListBuffer[String]] = mutable.ListBuffer.empty
    for (line <- input) {
      calculations.append(line.split(" ").filter(_.trim.nonEmpty).to(mutable.ListBuffer))
    }

    var totalSum = 0L

    calculations = calculations.transpose

    for (calc <- calculations) {
      val operator = calc.remove(calc.size - 1)
      val padded = calc.map(_.reverse.padTo(calc.map(_.length).max, '$').reverse)
      val transposed = padded.transpose
      val removeDollars = transposed.map(_.filter(_ != '$'))
      val mapped = removeDollars.map(_.mkString).map(_.toLong)

      var totalResult = mapped.remove(0)
      for (number <- mapped) {
        val currentResult = operator match {
          case "*" => totalResult * number
          case "+" => totalResult + number
        }
        totalResult = currentResult
      }
      totalSum += totalResult
    }

    totalSum.toString
  }
}
