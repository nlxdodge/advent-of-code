package utils

import java.io.File
import java.util.stream.Stream

class FileUtil {
    companion object {
        fun getFileStream(fileName: String): Stream<String> {
            return getFile(fileName).useLines { it.toList().stream() }
        }

        fun getFileList(fileName: String): List<String> {
            return getFile(fileName).useLines { it.toList() }
        }

        private fun getFile(fileName: String): File {
            return File("src/main/resources/$fileName")
        }
    }
}
