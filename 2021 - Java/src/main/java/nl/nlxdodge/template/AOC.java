package nl.nlxdodge.template;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class AOC {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./2021 - Java/src/main/java/nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            // code for part one

            Long result1 = (long) list.size();
            System.out.println(String.format("Result 1: %s", result1));

            // code for part two

            String result2 = "";
            System.out.println(String.format("Result 2: %s", result2));
        }
    }
}
