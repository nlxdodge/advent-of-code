import utils.FileUtil

class AOC13 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = FileUtil.getDay()
            val pairs = FileUtil.getFileString("AOC$day.txt").split("\n\n")

            val part1 = part1(pairs)
            val part2 = part2(pairs)

            println("Day $day result 1: $part1")
            println("Day $day result 2: $part2")
        }

        private fun part1(pairs: List<String>): String {
            val list = mutableListOf<Pair<Packet, Packet>>()
            for(pair in pairs) {
                val pair = parse(pair)
                list.add(pair)
                println(pair.first.compareTo(pair.second))
            }
            return ""
        }

        private fun part2(pairs: List<String>): String {
            return ""
        }

        private fun parse(input: String): Pair<Packet, Packet> {
            val pairs = input.split("\n")
            val buildPairs = mutableListOf<Packet>()
            for(pair in pairs) {
                val root = Packet()
                var current = root
                val checking = pair.removePrefix("[").removeSuffix("]")
                for (chr in checking) {
                    if (chr == '[') {
                        val newPacket = Packet(-1, current)
                        current.packets.add(newPacket)
                        current = newPacket
                        continue
                    }
                    if (chr == ']' && current.parent != null) {
                        current = current.parent!!
                        continue
                    }
                    if (chr.isDigit()) {
                        current.packets.add(Packet(chr.toString().toInt(), current))
                        continue
                    }
                }
                buildPairs.add(root)
            }
            return Pair(buildPairs[0], buildPairs[1])
        }
    }
}

class Packet(val number: Int = -1, val parent: Packet? = null, val packets: MutableList<Packet> = mutableListOf()): Comparable<Packet> {
    override fun compareTo(other: Packet): Int {
        if(packets.isEmpty() && other.packets.isEmpty()) {
            return number - other.number
        }
        if(packets.filter { it.number == -1 }.size == other.packets.filter { it.number == -1 }.size) {
            return 1
        } else {
            // size is different do magic now
        }
        return 0
    }

    override fun toString(): String {
        if(packets.isEmpty()) {
            return "$number"
        }
        if(number == -1) {
            return "[$packets]"
        }
        return "@"
    }
}