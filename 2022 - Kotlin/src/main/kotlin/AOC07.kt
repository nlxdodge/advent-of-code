import utils.FileUtil

class AOC07 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = FileUtil.getDay()
            val lines = FileUtil.getFileList("AOC$day.txt")
            lines.remove("$ cd /")
            val rootFolder = parseCommands(lines)

            val part1 = part1(rootFolder)
            val part2 = part2(rootFolder)

            println("Day $day result 1: $part1")
            println("Day $day result 2: $part2")
        }

        private fun part1(rootFolder: Folder): String {
            return findDirSizes(rootFolder, 100_000, false).sumOf { folder -> folder.getDirSize() }.toString()
        }

        private fun part2(rootFolder: Folder): String {
            return findDirSizes(
                rootFolder,
                30_000_000 - (70_000_000 - rootFolder.getDirSize()),
                true
            ).minOf { folder -> folder.getDirSize() }.toString()
        }

        private fun parseCommands(input: List<String>): Folder {
            val rootFolder = Folder("/", null, mutableListOf(), mutableListOf())
            var currentFolder = rootFolder
            for (line in input) {
                if (line.startsWith("$ cd")) {
                    val name = line.slice(IntRange(5, line.length - 1))
                    currentFolder = if (name == "..") {
                        currentFolder.parent!!
                    } else {
                        currentFolder.findSubFolder(name)!!
                    }
                } else if (!line.startsWith("$ ls")) {
                    handleListDirectory(line, currentFolder)
                }
            }
            return rootFolder
        }

        private fun handleListDirectory(line: String, currentFolder: Folder) {
            if (line.startsWith("dir")) {
                val name = line.slice(IntRange(4, line.length - 1))
                if (currentFolder.findSubFolder(name) == null) {
                    currentFolder.subFolders.add(
                        Folder(
                            name,
                            currentFolder,
                            mutableListOf(),
                            mutableListOf()
                        )
                    )
                }
            } else {
                currentFolder.files.add(File(line.split(" ")[0].toInt()))
            }
        }

        private fun findDirSizes(rootFolder: Folder, size: Int, biggerThen: Boolean = false): List<Folder> {
            val dirList = mutableListOf<Folder>()
            for (folder in rootFolder.subFolders) {
                if (biggerThen) {
                    if (folder.getDirSize() > size) {
                        dirList.add(folder)
                    }
                } else {
                    if (folder.getDirSize() < size) {
                        dirList.add(folder)
                    }
                }
                if (folder.subFolders.isNotEmpty()) {
                    dirList.addAll(findDirSizes(folder, size, biggerThen))
                }
            }
            return dirList
        }
    }
}


class Folder(
    private val name: String, val parent: Folder?, val files: MutableList<File>, val subFolders: MutableList<Folder>
) {
    fun getDirSize(): Int {
        return subFolders.sumOf { it.getDirSize() } + files.sumOf { it.size }
    }

    fun findSubFolder(name: String): Folder? {
        return subFolders.find { it.name == name }
    }
}

class File(val size: Int)
