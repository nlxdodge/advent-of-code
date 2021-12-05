package nl.nlxdodge.template;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class AOC {
    public static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    public static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            // code for part one

            String result1 = "";
            Logger.getGlobal().info(() -> String.format("Result 1: %s", result1));

            // code for part two

            String result2 = "";
            Logger.getGlobal().info(() -> String.format("Result 2: %s", result2));
        }
    }

    public static void func() {

    }
}
