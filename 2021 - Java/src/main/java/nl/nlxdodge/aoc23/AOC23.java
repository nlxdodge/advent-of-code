package nl.nlxdodge.aoc23;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AOC23 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./2021 - Java/src/main/java/nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            String[][] burrow = loadBurrow(list);

            Long energy = solveForEnergy(burrow);

            Long result1 = energy;
            System.out.println(String.format("Result 1: %s", result1));

            // code for part two

            String result2 = "";
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    private static Long solveForEnergy(String[][] burrow) {
        // A = 1
        // B = 10
        // C = 100
        // D = 1000

        List<Amphipods> amphipods = new ArrayList<>();
        for (int i = 0; i < burrow.length; i++) {
            for (int j = 0; j < burrow[0].length; j++) {
                if (burrow[i][j].contains("A") || burrow[i][j].contains("B") || burrow[i][j].contains("C")
                        || burrow[i][j].contains("D")) {
                    amphipods.add(new Amphipods(burrow[i][j], i, j));

                }
            }
        }
        for (Amphipods amphipod : amphipods) {
            System.out.println(amphipod);
        }
        return 1L;
    }

    private static String[][] loadBurrow(List<String> list) {
        String[][] burrow = new String[5][13];
        for (int i = 0; i < list.size(); i++) {
            String[] chr = list.get(i).split("");
            for (int j = 0; j < list.get(0).length(); j++) {
                burrow[i][j] = chr.length > j ? chr[j] : " ";
            }
        }
        return burrow;
    }
}

class Amphipods {
    String type;
    int fuelCost = 0;
    int x;
    int y;

    public Amphipods(String type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        calculateFuelCost();
    }

    private void calculateFuelCost() {
        StringBuilder fuelBuilder = new StringBuilder("1");
        IntStream.range(0, Integer.parseInt(type, 16) % 10).forEach(c -> fuelBuilder.append("0"));
        fuelCost = Integer.parseInt(fuelBuilder.toString());
    }

    public String toString() {
        return String.format("t: %s fuel: %s x: %s y: %s", type, fuelCost, x, y);
    }
}