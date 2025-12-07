@main def dayRunner(): Unit = {
  val dayNum = 6
  try {
    val dayClass = Class.forName(s"day.AOC$dayNum")
    val instance = dayClass.getDeclaredConstructor().newInstance().asInstanceOf[day.Day]
    println(s"Day $dayNum")
    println(s" ╠ Part 1: " + instance.part1())
    println(s" ╚ Part 2: " + instance.part2())
  } catch {
    case e: ClassNotFoundException => println(s"Class for Day $dayNum not found.")
  }
}

