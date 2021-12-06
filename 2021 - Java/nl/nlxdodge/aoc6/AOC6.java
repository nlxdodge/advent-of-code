package nl.nlxdodge.aoc6;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class AOC6 {
    public static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    public static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            int days = 256;

            List<Byte> school = new ArrayList<>();
            for (String line : list) {
                List<String> spawns = List.of(line.split(","));
                for (String spawn : spawns) {
                    school.add(Byte.parseByte(spawn));
                }
            }

            List<Byte> currentFish = new ArrayList<>();
            for (int day = 1; day < days + 1; day++) {
                for (Byte timer : school) {
                    if (timer == 0) {
                        currentFish.add(handleFish(timer));
                        currentFish.add((byte) 8);
                    } else {
                        currentFish.add(handleFish(timer));
                    }
                }
                school.clear();
                school.addAll(currentFish);
                currentFish.clear();

                if(school.size() > 26984457539) {
                    System.out.println("Should have");
                }
            }

            final String result1 = String.format("%s", school.size());
            Logger.getGlobal().info(() -> String.format("Result 1: %s", result1));

            // code for part two

            String result2 = "";
            Logger.getGlobal().info(() -> String.format("Result 2: %s", result2));
        }
    }

    public static byte handleFish(byte timer) {
        if (timer == 0) {
            return 6;
        } else if (timer >= 1) {
            return --timer;
        }
        return timer;
    }
}
