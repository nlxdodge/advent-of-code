import utils.FileUtil
import kotlin.math.roundToLong

class AOC11 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = FileUtil.getDay()
            val lines = FileUtil.getFileString("AOC$day.txt")

            val part1 = part1(lines.split("\r\n\r\n"))
            val part2 = part2(lines.split("\r\n\r\n"))

            println("Day $day result 1: $part1")
            println("Day $day result 2: $part2")
        }

        private fun part1(inputs: List<String>): String {
            val monkeys = mutableListOf<Monkey>()
            for (m in inputs) {
                val input = m.split("\r\n")
                monkeys.add(
                    Monkey(
                        input[0].substring(7, input[0].length - 1).toInt(),
                        input[1].substring(18, input[1].length).split(", ").map { Item(it.toLong()) }.toMutableList(),
                        input[2].substring(23, 24),
                        input[2].substring(25, input[2].length),
                        input[3].substring(21, input[3].length).toLong(),
                        input[4].substring(29, input[4].length).toInt(),
                        input[5].substring(30, input[5].length).toInt()
                    )
                )
            }
            val rounds = 20
            for (round in 1..rounds) {
                for (monkey in monkeys) {
                    while (monkey.items.iterator().hasNext()) {
                        val item = monkey.items.iterator().next()
                        item.inspect(monkey.getOperation())
                        monkey.inspections++
                        item.boredom()
                        monkeys[monkey.monkeyId].items.remove(item)
                        monkeys[monkey.testItem(item)].items.add(item)
                    }
                }
            }
            return monkeys.map { it.inspections }.sortedDescending().take(2).reduce { x, y -> x * y }.toString()
        }

        private fun part2(inputs: List<String>): String {
            val monkeys = mutableListOf<Monkey>()
            for (m in inputs) {
                val input = m.split("\r\n")
                monkeys.add(
                    Monkey(
                        input[0].substring(7, input[0].length - 1).toInt(),
                        input[1].substring(18, input[1].length).split(", ").map { Item(it.toLong()) }.toMutableList(),
                        input[2].substring(23, 24),
                        input[2].substring(25, input[2].length),
                        input[3].substring(21, input[3].length).toLong(),
                        input[4].substring(29, input[4].length).toInt(),
                        input[5].substring(30, input[5].length).toInt()
                    )
                )
            }
            val rounds = 10_000
            val modulo = monkeys.map { it.testOn }.reduce { x, y -> x * y }
            for (round in 1..rounds) {
                for (monkey in monkeys) {
                    while (monkey.items.iterator().hasNext()) {
                        val item = monkey.items.iterator().next()
                        item.inspect(monkey.getOperation())
                        monkey.inspections++
                        item.boredom(modulo)
                        monkeys[monkey.monkeyId].items.remove(item)
                        monkeys[monkey.testItem(item)].items.add(item)
                    }
                }
            }

            monkeys.sortBy { x -> x.inspections }
            return monkeys.takeLast(2).map { monkey -> monkey.inspections }.reduce { x, y -> x * y }.toString()
        }
    }
}

class Monkey(
    val monkeyId: Int,
    val items: MutableList<Item>,
    private val operation: String,
    private val operationValue: String,
    val testOn: Long,
    private val trueThrowTo: Int,
    private val falseThrowTo: Int,
    var inspections: Long = 0,
) {

    fun getOperation(): (Long) -> Long {
        if (operationValue == "old") {
            return { it: Long -> it * it }
        }
        when (operation) {
            "+" -> return { it: Long -> it + operationValue.toLong() }
            "*" -> return { it: Long -> it * operationValue.toLong() }
            else -> return { it: Long -> it * operationValue.toLong() }
        }
    }

    fun testItem(item: Item): Int {
        return if (item.value % testOn == 0L) {
            trueThrowTo
        } else {
            falseThrowTo
        }
    }

    override fun toString(): String {
        return "Monkey $monkeyId - inspections: $inspections"
    }
}

class Item(var value: Long) {
    fun inspect(operation: (Long) -> Long) {
        val old = value
        value = operation.invoke(value)
        if (value < old) {
            println("WAAA")
        }
    }

    override fun toString(): String {
        return "Item: $value"
    }

    fun boredom(modulo: Long = 0) {
        value = if (modulo == 0L) {
            (value / 3).toDouble().roundToLong()
        } else {
            (value % modulo).toDouble().roundToLong()
        }
    }
}