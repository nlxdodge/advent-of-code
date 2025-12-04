package day

import scala.collection.mutable

class AOC4 extends Day {
  def part1(): String = {
    val input = scala.io.Source.fromResource("aoc.txt").getLines().toList
    val gridSize = input.length
    val grid = createGrid(input, gridSize)

    checkMarkX(grid, gridSize)
    countXes(grid).toString
  }

  def part2(): String = {
    var removalCounter = 0.toLong

    val input = scala.io.Source.fromResource("aoc.txt").getLines().toList
    val gridSize = input.length
    val grid = createGrid(input, gridSize)

    while(removalCounter == 0 || countXes(grid) != 0) {
      removeXes(grid, gridSize)
      checkMarkX(grid, gridSize)
      removalCounter += countXes(grid)
    }

    removalCounter.toString
  }

  def createGrid(input: List[String], gridSize: Int): mutable.Map[(Int, Int), Char] = {
    val grid = mutable.Map[(Int, Int), Char]()
    for(y <- 0 until gridSize) {
      for(x <- 0 until gridSize) {
        grid((x, y)) = input(y)(x)
      }
    }
    grid
  }

  def checkMarkX(grid: mutable.Map[(Int, Int), Char], gridSize: Int): Unit = {
    for (y <- 0 until gridSize) {
      for (x <- 0 until gridSize) {
        if (grid((x, y)) == '@') {
          val deltas = List((-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1))
          val count = deltas.count {
            case (dx, dy) => grid.get((x + dx, y + dy)).exists(c => c == 'X' || c == '@')
          }
          if (count < 4) {
            grid((x, y)) = 'X'
          }
        }
      }
    }
  }

  def countXes(grid: mutable.Map[(Int, Int), Char]): Int = {
    grid.count { case (_, value) => value == 'X' }
  }

  def removeXes(grid: mutable.Map[(Int, Int), Char], gridSize: Int): Unit = {
    for(y <- 0 until gridSize) {
      for(x <- 0 until gridSize) {
        if(grid((x, y)) == 'X') {
          grid((x, y)) = '.'
        }
      }
    }
  }
}
