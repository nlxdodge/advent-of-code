package src.main.java.nl.nlxdodge.aoc19;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AOC19 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        String file = Files.readString(Paths.get(FILE_PATH));

        List<Scanner> scanners = initializeScanners(file);

        // code for part one

        Long result1 = (long) scanners.size();
        System.out.println(String.format("Result 1: %s", result1));

        // code for part two

        String result2 = "";
        System.out.println(String.format("Result 2: %s", result2));

    }

    private static List<Scanner> initializeScanners(String file) {
        List<Scanner> scanners = new ArrayList<>();

        String[] scannnerStrings = file.split("\n\n");
        for (String scannerString : scannnerStrings) {
            System.out.println(scannerString);
            Scanner scanner = new Scanner();

            List<String> beaconStrings = List.of(scannerString.split("scanner"));

            scanners.add(scanner);

        }

        return scanners;
    }
}

class Scanner {
    int x;
    int y;
    int z;
    List<Beacon> beacons;
}

class Beacon {
    int x;
    int y;
    int z;
}
