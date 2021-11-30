package nl.nlxdodge.aoc1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class AOC1 {
    private static final String FILE_PATH = "./nl/nlxdodge/aoc1/input.txt";

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            stream.forEach(String::new);
        }
    }
}