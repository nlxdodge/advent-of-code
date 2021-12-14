package nl.nlxdodge.aoc14;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class AOC14 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            Map<String, Long> polymer = initalizePolymer(list.get(0));
            Map<String, String> rules = initalizeRules(list.subList(2, list.size()));

            Long tenStepResult = 0L;
            int maxSteps = 40;
            for (int step = 1; step <= maxSteps; step++) {
                System.out.println("Step: " + step);
                polymer = parseStep(polymer, rules);
                if (step == 10) {
                    Map<String, Long> countMap = mapToSingleChar(polymer);
                    tenStepResult = subTractHighestLowest(countMap);
                }
            }

            Long result1 = tenStepResult;
            System.out.println(String.format("Result 1: %s", result1));

            // code for part two

            Long result2 = subTractHighestLowest(polymer);
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    private static Map<String, Long> mapToSingleChar(Map<String, Long> polymer) {
        Map<String, Long> countMap = new HashMap<>();
        for (Entry<String, Long> entry : polymer.entrySet()) {
            String[] chars = entry.getKey().split("");
            for (String chr : chars) {
                if (!countMap.containsKey(chr)) {
                    countMap.put(chr, 0L);
                }
            }
        }
        for (Entry<String, Long> entry : polymer.entrySet()) {
            String[] keys = entry.getKey().split("");
            countMap.put(keys[1], countMap.get(keys[1]) + entry.getValue());
        }
        return countMap;
    }

    private static Long subTractHighestLowest(Map<String, Long> countMap) {
        Long lowest = Long.MAX_VALUE;
        Long highest = 0L;
        for (Entry<String, Long> entry : countMap.entrySet()) {
            if (entry.getValue() > highest) {
                highest = entry.getValue();
            }
            if (entry.getValue() < lowest) {
                lowest = entry.getValue();
            }
        }
        return highest - lowest;
    }

    private static Map<String, Long> initalizePolymer(String input) {
        Map<String, Long> polymer = new HashMap<>();
        for (int i = 0; i < input.length() - 1; i++) {
            String pair = input.charAt(i) + "" + input.charAt(i + 1);
            polymer.put(pair, polymer.containsKey(pair) ? polymer.get(pair) : 1);
        }
        return polymer;
    }

    private static Map<String, String> initalizeRules(List<String> list) {
        Map<String, String> rules = new HashMap<>();
        for (String line : list) {
            String[] chrs = line.split(" -> ");
            rules.put(chrs[0], chrs[1]);
        }
        return rules;
    }

    private static Map<String, Long> parseStep(Map<String, Long> pairs, Map<String, String> rules) {
        var newPairs = new HashMap<>(pairs);
        for (Entry<String, Long> pair : pairs.entrySet()) {

            newPairs.put(pair.getKey(), newPairs.get(pair.getKey()) - pair.getValue());
            var chars = pair.getKey().split("");

            var key1 = chars[0] + rules.get(pair.getKey());
            var key2 = rules.get(pair.getKey()) + chars[1];
            newPairs.put(key1, newPairs.containsKey(key1) ? newPairs.get(key1) + pair.getValue() : 1L);
            newPairs.put(key2, newPairs.containsKey(key2) ? newPairs.get(key2) + pair.getValue() : 1L);

        }
        return newPairs;
    }
}