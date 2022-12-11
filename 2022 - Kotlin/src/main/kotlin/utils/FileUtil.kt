package utils

import java.io.File

class FileUtil {
    companion object {
        fun getDay(): String {
            val fileName = Throwable().stackTrace[1].fileName
            if (fileName != null) {
                return fileName.substring(3, 5)
            }
            return "01"
        }

        fun getFileString(fileName: String): String {
            return getFile(fileName).readText()
        }

        fun getFileList(fileName: String): MutableList<String> {
            return getFile(fileName).useLines { it.toMutableList() }
        }

        fun writeToFile(fileName: String, text: String) {
            val file = File(fileName)
            file.createNewFile()
            file.writeText(text)
        }

        fun writeToFile(text: String) {
            writeToFile("output.txt", text)
        }

        private fun getFile(fileName: String): File {
            return File("src/main/resources/$fileName")
        }
    }
}
