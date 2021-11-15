package nl.nlxdodge.aoc3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AOC3 {
    public static void main(String[] args) throws IOException {
        final String FILE_PATH = "./nl/nlxdodge/aoc3/input.txt";

        Fabric fabric = new Fabric(1000);

        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            stream.forEach(elfLine -> convertElfLine(elfLine, fabric, true));

            // check all matches that have more then 2 claims starstar star
            Logger.getGlobal().log(Level.INFO, () -> String.format("Result 1: %s", fabric.checkDoubleHits()));
        }

        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<Integer> nonOverlapping = stream.map(elfLine -> convertElfLine(elfLine, fabric, false))
                    .filter(id -> id != -1).collect(Collectors.toList());

            // when we got overlap list check again if it overlaps
            Logger.getGlobal().log(Level.INFO,
                    () -> String.format("Result 2: %s", nonOverlapping.get(nonOverlapping.size() - 1)));
        }

    }

    public static int convertElfLine(String line, Fabric fabric, boolean mark) {
        String[] elfThought = line.split(" ");
        int id = Integer.parseInt(elfThought[0].substring(1, elfThought[0].length()));

        String[] padding = elfThought[2].split(",");
        int padX = Integer.parseInt(padding[0]);
        int padY = Integer.parseInt(padding[1].substring(0, padding[1].length() - 1));

        String[] coordinates = elfThought[3].split("x");
        int corX = Integer.parseInt(coordinates[0]);
        int corY = Integer.parseInt(coordinates[1]);

        if (mark) {
            fabric.markFabric(padX, padY, corX, corY);
            return -1;
        } else {
            return fabric.checkFabric(padX, padY, corX, corY) ? -1 : id;
        }

    }
}

class Fabric {
    private int[][] hits;

    public Fabric(int size) {
        this.hits = new int[size][size];
    }

    public boolean checkFabric(int padX, int padY, int corX, int corY) {
        boolean overlaps = false;
        for (int x = padX; x <= corX + padX - 1; x++) {
            for (int y = padY; y <= corY + padY - 1; y++) {
                if (hits[x][y] > 1) {
                    overlaps = true;
                }
            }
        }
        return overlaps;
    }

    public void markFabric(int padX, int padY, int corX, int corY) {
        for (int x = padX; x <= corX + padX - 1; x++) {
            for (int y = padY; y <= corY + padY - 1; y++) {
                hits[x][y] += 1;
            }
        }
    }

    public long checkDoubleHits() {
        return Arrays.stream(hits).flatMapToInt(array -> Arrays.stream(array).filter(count -> count >= 2)).count();
    }
}
