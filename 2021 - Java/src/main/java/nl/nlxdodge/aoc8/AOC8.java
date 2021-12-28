package src.main.java.nl.nlxdodge.aoc8;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AOC8 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            Integer counter1 = 0;
            Integer counter2 = 0;

            for (String line : list) {
                String[] chunks = line.split(" \\| ");
                List<String> observed = List.of(chunks[0].split(" "));
                List<String> actual = List.of(chunks[1].split(" "));

                Map<Integer, String> determinedMappings = decodeDeterminedMappings(actual);
                Map<Integer, String> decodeMapping = decodeDeterminedMappings(observed);
                decodeMapping.putAll(decodeUnditerminedMappings(decodeMapping, observed));

                counter1 += findInDeterminedMapping(actual, determinedMappings);
                counter2 += decodeCombinedOutput(decodeMapping, actual);
            }

            Integer result1 = counter1;
            System.out.println(String.format("Result 1: %s", result1));

            Integer result2 = counter2;
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    public static Integer findInDeterminedMapping(List<String> observed, Map<Integer, String> determinedMappings) {
        int counter = 0;
        for (String line : observed) {
            for (Entry<Integer, String> entry : determinedMappings.entrySet()) {
                if (line.length() == entry.getValue().length()) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public static Map<Integer, String> decodeDeterminedMappings(List<String> lines) {
        Map<Integer, Integer> preDefined = new HashMap<>(Map.of(2, 1, 4, 4, 3, 7, 7, 8));
        Map<Integer, String> determiner = new HashMap<>();
        for (String smallChunk : lines) {
            if(preDefined.containsKey(smallChunk.length())) {
                determiner.put(preDefined.get(smallChunk.length()), smallChunk);
            }
        }
        return determiner;
    }

    public static Map<Integer, String> decodeUnditerminedMappings(Map<Integer, String> determiner, List<String> lines) {
        for (String smallChunk : lines) {
            Long fourCount = countCharsInString(smallChunk, determiner.get(4));
            Long sevenCount = countCharsInString(smallChunk, determiner.get(7));
            if (smallChunk.length() == 5) {
                if (sevenCount == 2 && fourCount == 2) {
                    determiner.put(2, smallChunk);
                }
                if (sevenCount == 3 && fourCount == 3) {
                    determiner.put(3, smallChunk);
                }
                if (sevenCount == 2 && fourCount == 3) {
                    determiner.put(5, smallChunk);
                }
            }
            if (smallChunk.length() == 6) {
                if (fourCount == 4) {
                    determiner.put(9, smallChunk);
                }
                if (sevenCount == 3 && fourCount == 3) {
                    determiner.put(0, smallChunk);
                }
                if (sevenCount == 2 && fourCount == 3) {
                    determiner.put(6, smallChunk);
                }
            }
        }
        return determiner;
    }

    public static Long countCharsInString(String find, String inString) {
        return Arrays.stream(inString.split("")).filter(find::contains).collect(Collectors.counting());
    }

    public static Integer decodeCombinedOutput(Map<Integer, String> mapping, List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : list) {
            for (Entry<Integer, String> entry : mapping.entrySet()) {
                if (countCharsInString(entry.getValue(), line) == line.length()
                        && line.length() == entry.getValue().length()) {
                    stringBuilder.append(entry.getKey().toString());
                    break;
                }
            }
        }
        return Integer.parseInt(stringBuilder.toString());
    }
}
