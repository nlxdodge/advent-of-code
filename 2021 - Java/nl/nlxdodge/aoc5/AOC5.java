package nl.nlxdodge.aoc5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class AOC5 {
    private static final String FILE_PATH = "./nl/nlxdodge/aoc5/input.txt";

    public static void main(String[] args) throws IOException {
        int size = 999;
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            int[][] grid = new int[size][size];
            for (String line : list) {
                setGridLine(grid, createPositionList(line), false);
            }
            Long result1 = higherAsTwo(grid);
            Logger.getGlobal().info(() -> String.format("Result 1: %s", result1));

            int[][] gridVertical = new int[size][size];
            for (String line : list) {
                setGridLine(gridVertical, createPositionList(line), true);
            }
            Long result2 = higherAsTwo(gridVertical);
            Logger.getGlobal().info(() -> String.format("Result 2: %s", result2));
        }
    }

    public static Long higherAsTwo(int[][] grid) {
        return Arrays.stream(grid).flatMapToInt(row -> Arrays.stream(row).filter(number -> number >= 2)).count();
    }

    public static void setGridLine(int[][] grid, List<Integer> positions, boolean diagonal) {
        int x1 = positions.get(0);
        int x2 = positions.get(2);
        int y1 = positions.get(1);
        int y2 = positions.get(3);
        if (x1 != x2 && y1 != y2) {
            if (diagonal) {
                drawVerticalLine(grid, positions);
            }
        } else {
            drawStraightLine(grid, positions);
        }
    }

    public static void drawStraightLine(int[][] grid, List<Integer> positions) {
        int x1 = Math.min(positions.get(0), positions.get(2));
        int x2 = Math.max(positions.get(0), positions.get(2));
        int y1 = Math.min(positions.get(1), positions.get(3));
        int y2 = Math.max(positions.get(1), positions.get(3));
        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x <= x2; x++) {
                grid[y][x] = grid[y][x] + 1;
            }
        }
    }

    public static void drawVerticalLine(int[][] grid, List<Integer> positions) {
        int x1 = positions.get(0);
        int x2 = positions.get(2);
        int y1 = positions.get(1);
        int y2 = positions.get(3);
        int min = Math.min(x1, x2);
        int max = Math.max(x1, x2);
        int size = (max - min);

        int yC = y1;
        int xC = x1;
        for (int i = 0; i <= size; i++) {
            grid[yC][xC] = grid[yC][xC] + 1;
            if (x1 > x2) {
                xC--;
            } else {
                xC++;
            }
            if (y1 > y2) {
                yC--;
            } else {
                yC++;
            }
        }
    }

    public static List<Integer> createPositionList(String line) {
        String[] splitLine = line.split(" -> ");
        List<Integer> fromTo = new ArrayList<>();
        for (String doubleSplit : splitLine) {
            String[] commands = doubleSplit.split(",");
            fromTo.add(Integer.parseInt(commands[0]));
            fromTo.add(Integer.parseInt(commands[1]));
        }
        return fromTo;
    }
}
