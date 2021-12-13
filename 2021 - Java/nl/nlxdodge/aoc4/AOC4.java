package nl.nlxdodge.aoc4;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AOC4 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();
            List<String> mutableList = new ArrayList<>(list);

            List<String> bingoTodo = new ArrayList<>(Arrays.asList(mutableList.get(0).split(",")));
            mutableList.remove(0);
            mutableList.remove(0);

            List<int[][]> boards = new ArrayList<>();
            List<String> splitBoards = List.of(String.join("\n", mutableList).split("\n\n"));
            for (String board : splitBoards) {
                boards.add(makeBoard(board));
            }

            List<int[][]> wonBoards = new ArrayList<>();
            List<Integer> haveBingo = new ArrayList<>();
            for (String hit : bingoTodo) {
                haveBingo.add(Integer.parseInt(hit));
                for (int[][] board : boards) {
                    if (hasBingo(haveBingo, board) && !wonBoards.contains(board)) {
                        wonBoards.add(board);
                        checkResults(boards.size(), board, hit, haveBingo, wonBoards);
                    }
                }
            }
        }
    }

    public static void checkResults(Integer boardSize, int[][] board, String hit, List<Integer> haveBingo, List<int[][]> wonBoards) {
        if (wonBoards.size() == 1) {
            String result1 = "" + sumUnmarked(haveBingo, board) * Integer.parseInt(hit);
            System.out.println(String.format("Result 1: %s", result1));
        }
        if (wonBoards.size() == boardSize) {
            String result2 = "" + sumUnmarked(haveBingo, board) * Integer.parseInt(hit);
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    public static int sumUnmarked(List<Integer> hits, int[][] input) {
        int count = 0;
        for (int[] sub : input) {
            for (int number : sub) {
                if (!hits.contains(number)) {
                    count += number;
                }
            }
        }
        return count;
    }

    public static int[][] makeBoard(String input) {
        int[][] board = new int[5][5];
        List<String> lines = new ArrayList<>(List.of(input.split("\n")));
        for (int counter = 0; counter < 5; counter++) {
            List<String> line = new ArrayList<>(List.of(lines.get(counter).trim().split("\s+")));
            for (int number = 0; number < 5; number++) {
                board[counter][number] = Integer.parseInt(line.get(number));
            }
        }
        return board;
    }

    public static boolean hasBingo(List<Integer> hits, int[][] board) {
        boolean hasBingo = false;
        for (int ver = 0; ver < 5; ver++) {
            int xC = 0;
            int yC = 0;
            for (int hor = 0; hor < 5; hor++) {
                if (hits.contains(board[ver][hor])) {
                    xC++;
                }
                if (hits.contains(board[hor][ver])) {
                    yC++;
                }
                if (xC == 5 || yC == 5) {
                    return true;
                }
            }
        }
        return hasBingo;
    }
}
