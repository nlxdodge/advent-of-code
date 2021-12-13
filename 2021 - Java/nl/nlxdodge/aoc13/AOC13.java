package nl.nlxdodge.aoc13;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class AOC13 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            List<String> cords = list.subList(0, list.indexOf(""));
            List<String> folds = list.subList(list.indexOf("") + 1, list.size());

            String[][] manual = initializeManual(cords);

            manual = performFold(folds.get(0), manual);

            Long result1 = countHashes(manual);
            Logger.getGlobal().info(() -> String.format("Result 1: %s", result1));

            // code for part two

            String result2 = "";
            Logger.getGlobal().info(() -> String.format("Result 2: %s", result2));
        }
    }

    private static Long countHashes(String[][] manual) {
        return Arrays.stream(manual).flatMap(Arrays::stream).filter(c -> c.equals("#")).count();
    }

    private static String[][] performFold(String fold, String[][] manual) {
        Integer startLine = Integer.parseInt(fold.split("=")[1]);
        createLine(manual, startLine, fold.contains("x"));
        if (fold.contains("y")) {
            // horizontal
            String[][] copy = Arrays.copyOfRange(manual, startLine + 1, manual.length);
            String[][] newManual = Arrays.copyOfRange(manual, 0, startLine);
            for (int y = 0; y < newManual.length; y++) {
                for (int x = 0; x < newManual[0].length; x++) {
                    if (copy[y][x].equals("#")) {
                        int newY = newManual.length - 1 - y;
                        newManual[newY][x] = copy[y][x];
                    }
                }
            }
            return newManual;
        } else if (fold.contains("x")) {
            // vertical
            String[][] copy = new String[manual.length][startLine];
            String[][] newManual = new String[manual.length][startLine];
            setAllTo(copy, ".");
            setAllTo(newManual, ".");

            // transform to only half the array size
            for (int y = 0; y < manual.length - 1; y++) {
                for (int x = 0; x < manual[0].length - 1; x++) {
                    if (x < startLine) {
                        copy[y][x] = manual[y][x];
                    } else {
                        newManual[y][x - startLine] = manual[y][x + 1];
                    }
                }
            }
            // do normal mapping to other side
            for (int y = 0; y < newManual.length; y++) {
                for (int x = 0; x < newManual[0].length; x++) {
                    if (copy[y][x].equals("#")) {
                        int newX = newManual[0].length - 1 - x;
                        newManual[y][newX] = copy[y][x];
                    }
                }
            }
            return newManual;
        }
        return new String[1][1];
    }

    private static void print(String[][] manual) {
        for (int y = 0; y < manual.length; y++) {
            for (int x = 0; x < manual[0].length; x++) {
                // apply new lines to manual
                String str = manual[y][x];
                if (str == null) {
                    str = ".";
                }
                System.out.print(str);
            }
            System.out.println("");
        }
        System.out.println("\n");
    }

    private static void createLine(String[][] manual, Integer startLine, boolean vertical) {
        if (vertical) {
            for (int z = 0; z < manual.length; z++) {
                manual[z][startLine] = "|";
            }
        } else {
            for (int z = 0; z < manual[0].length; z++) {
                manual[startLine][z] = "-";
            }
        }
    }

    public static String[][] setAllTo(String[][] manual, String input) {
        for (int y = 0; y < manual.length; y++) {
            for (int x = 0; x < manual[0].length; x++) {
                manual[y][x] = input;
            }
        }
        return manual;
    }

    private static String[][] initializeManual(List<String> list) {
        OptionalInt y = list.stream().mapToInt(s -> Integer.parseInt(s.split(",")[0])).max();
        OptionalInt x = list.stream().mapToInt(s -> Integer.parseInt(s.split(",")[1])).max();

        String[][] manual = new String[x.getAsInt() + 1][y.getAsInt() + 1];
        setAllTo(manual, ".");
        for (String line : list) {
            String[] cords = line.split(",");
            manual[Integer.parseInt(cords[1])][Integer.parseInt(cords[0])] = "#";
        }
        return manual;
    }
}
