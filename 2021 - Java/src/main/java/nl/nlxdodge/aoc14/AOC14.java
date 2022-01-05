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
    private static final String FILE_PATH = String.format("./2021 - Java/src/main/java/nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            Map<String, Long> polymer = initalizePolymer(list.get(0));
            Map<String, String> rules = initalizeRules(list.subList(2, list.size()));

            Long tenStepResult = 0L;
            int maxSteps = 40;
            for (int step = 1; step <= maxSteps; step++) {
                polymer = parseStep(polymer, rules);
                if (step == 10) {
                    System.out.println(String.format("Step %s:", step));
                    Map<String, Long> countMap = mapToSingleChar(polymer);
                    tenStepResult = subTractHighestLowest(countMap);
                }
            }

            Long result1 = tenStepResult;
            Map<String, Long> countMap = mapToSingleChar(polymer);
            Long result2 = subTractHighestLowest(countMap);
            System.out.println(String.format("Result 1: %s", result1));
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    private static Map<String, Long> mapToSingleChar(Map<String, Long> polymer) {
        Map<String, Long> countMap = new HashMap<>();
        countMap.put("K", 1L);
        for (Entry<String, Long> entry : polymer.entrySet()) {
            String key = "" + entry.getKey().charAt(0);
            countMap.putIfAbsent(key, 0L);
            countMap.put(key, (countMap.get(key) + polymer.get(entry.getKey())));

        }
        System.out
                .println(String.format("Polymer Size: %s", polymer.values().stream().mapToLong(Long::longValue).sum()));
        System.out.println(
                String.format("CountMap Size: %s", countMap.values().stream().mapToLong(Long::longValue).sum()));
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

    private static Map<String, Long> parseStep(Map<String, Long> polymer, Map<String, String> rules) {
        HashMap<String, Long> newPairs = new HashMap<>();
        for (Entry<String, Long> entry : polymer.entrySet()) {
            String newPair1 = entry.getKey().charAt(0) + rules.get(entry.getKey());
            String newPair2 = rules.get(entry.getKey()) + entry.getKey().charAt(1);

            long orignialCount = polymer.get(entry.getKey());

            if (orignialCount > 0) {
                newPairs.put(newPair1, newPairs.containsKey(newPair1) ? newPairs.get(newPair1) + orignialCount : orignialCount);
                newPairs.put(newPair2, newPairs.containsKey(newPair2) ? newPairs.get(newPair2) + orignialCount : orignialCount);
            }
        }
        return newPairs;
    }
}