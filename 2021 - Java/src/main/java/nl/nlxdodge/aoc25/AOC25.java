package nl.nlxdodge.aoc25;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AOC25 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./2021 - Java/src/main/java/nl/nlxdodge/%s/input.txt",
            FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            List<List<String>> cucumbers = initalizeCucumbers(list);

            // > then V

            simulateStep(cucumbers);

            // code for part one

            Long result1 = (long) list.size();
            System.out.println(String.format("Result 1: %s", result1));

            // code for part two

            String result2 = "";
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    private static void printGrid(List<List<String>> grid) {
        StringBuilder gridString = new StringBuilder();

        for (List<String> line : grid) {
            for (String str : line) {
                gridString.append(str);
            }
            gridString.append("\n");
        }
        System.out.print(gridString.toString() + "\r");
        System.out.println("----------");
    }

    private static void simulateStep(List<List<String>> cucumbers) {
        for (int y = 0; y < cucumbers.size(); y++) {
            for (int x = 0; x < cucumbers.get(0).size(); x++) {
                String value = cucumbers.get(y).get(x);
                if (value.equals(">")) {

                    if (x + 1 >= cucumbers.get(0).size()) {
                        // wrap around if 0 is free
                        if (cucumbers.get(y).get(0).equals(".")) {
                            cucumbers.get(y).set(x, ".");
                            cucumbers.get(y).set(0, value);
                            printGrid(cucumbers);
                        }
                    } else if (cucumbers.get(y).get(x + 1).equals(".")) {
                        // check if next if free
                        cucumbers.get(y).set(x, ".");
                        cucumbers.get(y).set(x + 1, value);
                        printGrid(cucumbers);
                    }
                }
            }
        }
        System.out.println("Start going down now");
        for (int y = 0; y < cucumbers.size(); y++) {
            for (int x = 0; x < cucumbers.get(0).size(); x++) {
                String value = cucumbers.get(y).get(x);
                if (value.equals("v")) {
                    // check if free
                    if (y + 1 >= cucumbers.size()) {
                        if (cucumbers.get(0).get(x).equals(".")) {
                            cucumbers.get(y).set(x, ".");
                            cucumbers.get(0).set(x, value);
                            printGrid(cucumbers);
                        }
                    } else {
                        if (cucumbers.get(y + 1).get(x).equals(".")) {
                            // free space so clear old pos and set new pos
                            cucumbers.get(y).set(x, ".");
                            cucumbers.get(y + 1).set(x, value);
                            printGrid(cucumbers);
                        }
                    }
                }
            }
        }
    }

    private static List<List<String>> initalizeCucumbers(List<String> list) {
        List<List<String>> cucumbers = new ArrayList<>();
        for (String string : list) {
            cucumbers.add(new ArrayList<>(List.of(string.split(""))));
        }
        return cucumbers;
    }
}
