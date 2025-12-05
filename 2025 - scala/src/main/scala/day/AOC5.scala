package day

class AOC5 extends Day {
  def part1(): String = {
    val input = scala.io.Source.fromResource("aoc.txt").mkString
    var freshIngredients = 0L

    val (ranges, ingredients) = input.splitAt(input.indexOf("\n\n"))

    val rangesList = ranges.split("\n").map { line =>
      val Array(start, end) = line.split("-").map(_.toLong)
      (start, end)
    }.toList

    val ingredientsList = ingredients.split("\n").filter(_.nonEmpty).map(_.toLong).toList

    for (ingredientId <- ingredientsList) {
      var found = false
      for ((start, end) <- rangesList) {
        if (!found && ingredientId >= start && ingredientId <= end) {
          freshIngredients += 1
          found = true
        }
      }
    }
    freshIngredients.toString
  }

  def part2(): String = {
    val input = scala.io.Source.fromResource("aoc.txt").mkString

    val (ranges, ingredients) = input.splitAt(input.indexOf("\n\n"))

    val rangesList = ranges.split("\n").map { line =>
      val Array(start, end) = line.split("-").map(_.toLong)
      (start, end)
    }.toList

    val sortedRanges = rangesList.sortBy(_._1)
    val mergedRanges = scala.collection.mutable.ListBuffer[(Long, Long)]()

    for (range <- sortedRanges) {
      if (mergedRanges.isEmpty) {
        mergedRanges += range
      } else {
        val last = mergedRanges.last
        if (range._1 <= last._2) {
          // Overlap, merge with last
          mergedRanges(mergedRanges.size - 1) = (last._1, Math.max(last._2, range._2))
        } else {
          mergedRanges += range
        }
      }
    }

    mergedRanges.map { x => x._2 - x._1 + 1 }.sum.toString
  }
}
