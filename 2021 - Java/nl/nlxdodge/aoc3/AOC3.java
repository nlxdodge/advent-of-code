package nl.nlxdodge.aoc3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AOC3 {
    private static final String FILE_PATH = "./nl/nlxdodge/aoc3/input.txt";

    public static void main(String[] args) throws IOException {
        String mostSignificant = "";
        String leastSignificant = "";

        String oxygen = "";
        String co2Scrubber = "";

        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> l = stream.toList();

            StringBuilder mostSB = new StringBuilder();
            StringBuilder leastSB = new StringBuilder();

            for (int i = 0; i < l.get(0).length(); i++) {
                mostSB.append(mostSignificantBits(l, i));
                leastSB.append(leastSignificantBits(l, i));
            }

            mostSignificant = mostSB.toString();
            leastSignificant = leastSB.toString();

            List<String> newList = new ArrayList<>(l);
            for (int i = 0; i < l.get(0).length(); i++) {
                char newBit = mostSignificantBits(newList, i);
                for (String line : l) {
                    if (line.charAt(i) != newBit && newList.size() != 1) {
                        newList.remove(line);
                    }
                }
            }
            oxygen = newList.get(0);

            newList = new ArrayList<>(l);
            for (int i = 0; i < l.get(0).length(); i++) {
                char newBit = leastSignificantBits(newList, i);
                for (String line : l) {
                    if (line.charAt(i) != newBit && newList.size() != 1) {
                        newList.remove(line);
                    }
                }
            }
            co2Scrubber = newList.get(0);
        }

        int result1 = Integer.parseInt(mostSignificant, 2) * Integer.parseInt(leastSignificant, 2);
        int result2 = Integer.parseInt(oxygen, 2) * Integer.parseInt(co2Scrubber, 2);

        System.out.println("result1 = " + result1);
        System.out.println("result2 = " + result2);
    }

    public static char mostSignificantBits(List<String> list, int index) {
        int zeros = 0;
        int ones = 0;

        for (String line : list) {
            if (line.charAt(index) == '0') {
                zeros++;
            }
            if (line.charAt(index) == '1') {
                ones++;
            }
        }

        if (zeros > ones) {
            return '0';
        } else {
            return '1';
        }
    }

    public static char leastSignificantBits(List<String> list, int index) {
        int zeros = 0;
        int ones = 0;

        for (String line : list) {
            if (line.charAt(index) == '0') {
                zeros++;
            }
            if (line.charAt(index) == '1') {
                ones++;
            }
        }

        if (zeros <= ones) {
            return '0';
        } else {
            return '1';
        }
    }
}
