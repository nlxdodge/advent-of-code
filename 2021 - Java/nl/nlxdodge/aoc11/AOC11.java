package nl.nlxdodge.aoc11;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class AOC11 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            int[][][] map = initializeDataset(list);

            int steps = 100;
            Long totalFlashes = 0L;
            for (int step = 1; step <= steps; step++) {
                totalFlashes += itterateOctopusses(map);
            }
            Long result1 = totalFlashes;
            Logger.getGlobal().info(() -> String.format("Result 1: %s", result1));

            map = initializeDataset(list);
            int step = 0;
            Long flashAllStep = 0L;
            while (flashAllStep == 0) {
                step++;
                itterateOctopusses(map);
                if (flashAllAtSametime(map)) {
                    flashAllStep = (long) step;
                }
            }

            Long result2 = flashAllStep;
            Logger.getGlobal().info(() -> String.format("Result 2: %s", result2));
        }
    }

    private static Long itterateOctopusses(int[][][] map) {
        Long flashes = 0L;
        addToOctoPusses(map);
        flashes += checkFlashes(map);
        cleanFlashes(map);
        return flashes;
    }

    public static void addToOctoPusses(int[][][] map) {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                map[x][y][1]++;
            }
        }
    }

    public static Long checkFlashes(int[][][] map) {
        Long flashes = 0L;
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y][1] >= 10 && map[x][y][0] == 0) {
                    flashes += flash(map, x, y);
                }
            }
        }
        return flashes;
    }

    public static void cleanFlashes(int[][][] map) {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y][1] >= 10) {
                    map[x][y][0] = 0;
                    map[x][y][1] = 0;
                }
            }
        }
    }

    public static Long flash(int[][][] map, int x, int y) {
        Long flashes = 1L;
        map[x][y][0] = 1;
        for (int xA = -1; xA <= 1; xA++) {
            for (int yA = -1; yA <= 1; yA++) {
                int newX = x + xA;
                int newY = y + yA;
                if (newX >= 0 && newX < map.length && newY >= 0 && newY < map[0].length) {
                    map[newX][newY][1]++;
                    if (map[newX][newY][1] >= 10 && map[newX][newY][0] == 0) {
                        flashes += flash(map, newX, newY);
                    }
                }
            }
        }
        return flashes;
    }

    public static boolean flashAllAtSametime(int[][][] map) {
        boolean allSame = true;
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y][1] != map[0][0][1]) {
                    allSame = false;
                }
            }
        }
        return allSame;
    }

    public static int[][][] initializeDataset(List<String> list) {
        int[][][] map = new int[list.size()][list.get(0).length()][2];
        for (int l = 0; l < list.size(); l++) {
            String[] chars = list.get(l).split("");
            for (int c = 0; c < chars.length; c++) {
                map[l][c][0] = 0;
                map[l][c][1] = Integer.parseInt(chars[c]);
            }
        }
        return map;
    }
}
