package nl.nlxdodge.aoc13;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
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

            Long result1 = Arrays.stream(manual).flatMap(Arrays::stream).filter(c -> c.equals("#")).count();
            System.out.println(String.format("Result 1: %s", result1));

            manual = initializeManual(cords);
            for (String fold : folds) {
                manual = performFold(fold, manual);
            }
            print(manual);
        }
    }

    public static String[][] mirror(Boolean vertical, String[][] left, String[][] right) {
        for (int y = 0; y < left.length; y++) {
            for (int x = 0; x < left[0].length; x++) {
                if (left[y][x].equals("#")) {
                    if (vertical) {
                        int newVar = right.length - 1 - y;
                        right[newVar][x] = left[y][x];
                    } else {
                        int newVar = right[0].length - 1 - x;
                        right[y][newVar] = left[y][x];
                    }
                }
            }
        }
        return right;
    }

    private static String[][] performFold(String fold, String[][] manual) {
        Integer foldLine = Integer.parseInt(fold.split("=")[1]);
        createLine(manual, foldLine, fold.contains("x"));
        if (fold.contains("y")) {
            String[][] right = Arrays.copyOfRange(manual, 0, foldLine);
            String[][] left = Arrays.copyOfRange(manual, foldLine + 1, manual.length);

            return mirror(true, left, right);
        } else {
            String[][] right = setAllTo(new String[manual.length][foldLine], ".");
            String[][] left = setAllTo(new String[manual.length][foldLine], ".");

            // map to smaller array sizes
            for (int y = 0; y < manual.length; y++) {
                for (int x = 0; x < manual[0].length; x++) {
                    if (x < foldLine) {
                        right[y][x] = manual[y][x];
                    } else if (x > foldLine) {
                        left[y][x - foldLine - 1] = manual[y][x];
                    }
                }
            }
            return mirror(false, left, right);
        }
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
            System.out.print("\n");
        }
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

        String[][] manual = setAllTo(new String[x.getAsInt() + 1][y.getAsInt() + 1], ".");
        for (String line : list) {
            String[] cords = line.split(",");
            manual[Integer.parseInt(cords[1])][Integer.parseInt(cords[0])] = "#";
        }
        return manual;
    }
}
