package nl.nlxdodge.aoc3;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Logger;
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

            mostSignificant = mostSignificantBits(l.size(), parseInputToMap(l));
            leastSignificant = leastSignificantBits(l.size(), parseInputToMap(l));

            List<String> newList = new ArrayList<>(l);
            for (int i = 0; i < l.get(0).length(); i++) {
                String newBit = mostSignificantBits(newList.size(), parseInputToMap(newList));
                for (String line : l) {
                    if (line.charAt(i) != newBit.charAt(i) && newList.size() != 1) {
                        newList.remove(line);
                    }
                }
            }
            oxygen = newList.get(0);

            newList = new ArrayList<>(l);
            for (int i = 0; i < l.get(0).length(); i++) {
                String newBit = leastSignificantBits(newList.size(), parseInputToMap(newList));
                for (String line : l) {
                    if (line.charAt(i) != newBit.charAt(i) && newList.size() != 1) {
                        newList.remove(line);
                    }
                }
            }
            co2Scrubber = newList.get(0);
        }

        int result1 = Integer.parseInt(mostSignificant, 2) * Integer.parseInt(leastSignificant, 2);
        int result2 = Integer.parseInt(oxygen, 2) * Integer.parseInt(co2Scrubber, 2);

        Logger.getGlobal().info(() -> String.format("Result 1: %s", result1));
        Logger.getGlobal().info(() -> String.format("Result 2: %s", result2));
    }

    public static String mostSignificantBits(int inputSize, Map<Integer, Integer> numbers) {
        StringBuilder most = new StringBuilder();
        for (Integer count : numbers.values()) {
            Integer splitLen = inputSize / 2;
            most.append(count >= splitLen ? "1" : "0");
        }
        return most.toString();
    }

    public static String leastSignificantBits(int inputSize, Map<Integer, Integer> numbers) {
        StringBuilder least = new StringBuilder();
        for (Integer count : numbers.values()) {
            Integer splitLen = inputSize / 2;
            if(inputSize == 2) {
                least.append("0");
            } else {
                least.append(count <= splitLen ? "1" : "0");
            }
        }
        return least.toString();
    }

    public static Map<Integer, Integer> parseInputToMap(List<String> inputArray) {
        Map<Integer, Integer> numbers = new HashMap<>();

        for (String str : inputArray) {
            for (int i = 0; i < str.length(); i++) {
                Integer val = numbers.get(i);
                if (val != null) {
                    if (str.charAt(i) == '1') {
                        numbers.put(i, numbers.get(i) + 1);
                    }
                } else {
                    numbers.put(i, 0);
                }
            }
        }
        return numbers;
    }
}
