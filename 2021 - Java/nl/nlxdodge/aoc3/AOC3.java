package nl.nlxdodge.aoc3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class AOC3 {
    private static final String FILE_PATH = "./nl/nlxdodge/aoc3/input.txt";

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> l = stream.toList();

            String mostSignificant = significantBits(false, l.size(), parseInputToMap(l));
            String leastSignificant = significantBits(true, l.size(), parseInputToMap(l));

            int result1 = Integer.parseInt(mostSignificant, 2) * Integer.parseInt(leastSignificant, 2);

            Logger.getGlobal().info(() -> String.format("Result 1: %s", result1));

            String oxygen = reducer(true, l);
            String co2Scrubber = reducer(false, l);

            int result2 = Integer.parseInt(oxygen, 2) * Integer.parseInt(co2Scrubber, 2);

            Logger.getGlobal().info(() -> String.format("Result 2: %s", result2));
        }
    }

    public static String reducer(boolean zeroCheck, List<String> input) {
        List<String> newList = new ArrayList<>(input);
            for (int i = 0; i < input.get(0).length(); i++) {
                String newBit = significantBits(zeroCheck, newList.size(), parseInputToMap(newList));
                for (String line : input) {
                    if (line.charAt(i) != newBit.charAt(i) && newList.size() != 1) {
                        newList.remove(line);
                    }
                }
            }
            return newList.get(0);
    }

    public static String significantBits(boolean zeroCheck, int inputSize, Map<Integer, Integer> numbers) {
        StringBuilder bits = new StringBuilder();
        for (Integer ones : numbers.values()) {
            Integer zeros = inputSize - ones;
            if (zeroCheck) {
                bits.append(ones >= zeros ? "1" : "0");
            } else {
                bits.append(ones < zeros ? "1" : "0");
            }
        }
        return bits.toString();
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
                    numbers.put(i, str.charAt(i) == '1' ? 1 : 0);
                }
            }
        }
        return numbers;
    }
}
