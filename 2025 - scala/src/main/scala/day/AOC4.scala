package day

class AOC4 extends Day {
  def part1(): String = {
    val input = scala.io.Source.fromResource("aoc.txt").getLines().toList
    val gridSize = input.length
    var grid = createGrid(input, gridSize)

    grid = checkMarkX(grid, gridSize)
    countXes(grid).toString
  }

  def part2(): String = {
    var removalCounter = 0.toLong

    val input = scala.io.Source.fromResource("aoc.txt").getLines().toList
    val gridSize = input.length
    var grid = createGrid(input, gridSize)

    while(removalCounter == 0 || countXes(grid) != 0) {
      grid = removeXes(grid, gridSize)
      grid = checkMarkX(grid, gridSize)
      removalCounter += countXes(grid)
    }

    removalCounter.toString
  }

  def createGrid(input: List[String], gridSize: Int): Map[(Int, Int), String] = {
    var grid = Map[(Int, Int), String]()
    for(y <- 0 until gridSize) {
      for(x <- 0 until gridSize) {
        grid += ((x, y) -> input(y).split("")(x))
      }
    }
    grid
  }

  def checkMarkX(grid: Map[(Int, Int), String], gridSize: Int): Map[(Int, Int), String] = {
    var newGrid = grid
    for (y <- 0 until gridSize) {
      for (x <- 0 until gridSize) {
        if (grid((x, y)) == "@") {
          val deltas = List((-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1))
          val count = deltas.count {
            case (dx, dy) => grid.get((x + dx, y + dy)).exists(x => x == "X" || x == "@")
          }
          if (count < 4) {
            newGrid += ((x, y) -> "X")
          }
        }
      }
    }
    newGrid
  }

  def countXes(grid: Map[(Int, Int), String]): Int = {
    grid.count { case (_, value) => value == "X" }
  }

  def removeXes(grid: Map[(Int, Int), String], gridSize: Int): Map[(Int, Int), String] = {
    var newGrid = grid
    for(y <- 0 until gridSize) {
      for(x <- 0 until gridSize) {
        if(newGrid((x, y)) == "X") {
          newGrid += ((x, y) -> ".")
        }
      }
    }
    newGrid
  }
}
