package nl.nlxdodge.aoc9;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AOC9 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./2021 - Java/src/main/java/nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();
            int[][] map = initializeMap(list);

            List<Integer> lowest = markLowerPoint(map); 
            List<Integer> basins = calculateBasins(map).stream().sorted().toList();
            
            int result1 = lowest.stream().map(l -> l + 1).mapToInt(Integer::intValue).sum();
            int result2 = basins.subList(basins.size() - 2, basins.size())
            .stream()
            .reduce(basins.get(basins.size() - 3), (a, b) -> a * b);

            System.out.println(String.format("Result 1: %s", result1));
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    public static List<Integer> calculateBasins(int[][] map) {
        List<Integer> basinSizes = new ArrayList<>();
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (!smallerAdjecent(map, x, y)) {
                    List<int[]> parsedLocations = new ArrayList<>();
                    basinSizes.add(findHigherRecursive(map, x, y, parsedLocations).size());
                }
            }
        }
        return basinSizes;
    }

    public static List<int[]> findHigherRecursive(int[][] map, int x, int y, List<int[]> parsedLocations) {
        int[] directions = new int[] { x, y };
        if (!isInList(parsedLocations, directions)) {
            parsedLocations.add(directions);
        }
        for (int xC = x - 1; xC <= x + 1; xC += 2) {
            if (xC <= 0 || xC >= map.length) {
                continue;
            }
            if (map[xC][y] > map[x][y] && map[xC][y] != 9 && !isInList(parsedLocations, new int[] { xC, y })) {
                parsedLocations.add(new int[] { xC, y });
                parsedLocations = findHigherRecursive(map, xC, y, parsedLocations);
            }
        }
        for (int yC = y - 1; yC <= y + 1; yC += 2) {
            if (yC <= 0 || yC >= map.length) {
                continue;
            }
            if (map[x][yC] > map[x][y] && map[x][yC] != 9 && !isInList(parsedLocations, new int[] { x, yC })) {
                parsedLocations.add(new int[] { x, yC });
                parsedLocations = findHigherRecursive(map, x, yC, parsedLocations);
            }
        }
        return parsedLocations;
    }

    public static boolean isInList(List<int[]> list, int[] toFind) {
        boolean found = false;
        for (int[] array : list) {
            found = (array[0] == toFind[0] && array[1] == toFind[1]);
            if (found) {
                break;
            }
        }
        return found;
    }

    public static List<Integer> markLowerPoint(int[][] map) {
        List<Integer> lowest = new ArrayList<>();
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (!smallerAdjecent(map, x, y)) {
                    lowest.add(map[x][y]);
                }
            }
        }
        return lowest;
    }

    public static boolean smallerAdjecent(int[][] map, int x, int y) {
        int original = map[x][y];
        boolean foundSmaller = false;
        if (x - 1 >= 0 && map[x - 1][y] <= original) {
            foundSmaller = true;
        }
        if (x + 1 < map.length && map[x + 1][y] <= original) {
            foundSmaller = true;
        }
        if (y - 1 >= 0 && map[x][y - 1] <= original) {
            foundSmaller = true;
        }
        if (y + 1 < map[0].length && map[x][y + 1] <= original) {
            foundSmaller = true;
        }
        return foundSmaller;
    }

    public static int[][] initializeMap(List<String> list) {
        int[][] map = new int[list.size()][list.get(0).length()];
        for (int l = 0; l < list.size(); l++) {
            String[] chars = list.get(l).split("");
            for (int c = 0; c < chars.length; c++) {
                map[l][c] = Integer.parseInt(chars[c]);
            }
        }
        return map;
    }
}
