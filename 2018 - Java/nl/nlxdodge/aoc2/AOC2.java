package nl.nlxdodge.aoc2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AOC2 {
    public static void main(String[] args) throws IOException {
        final String FILE_PATH = "./nl/nlxdodge/aoc2/input.txt";

        long doubles = 0L;
        long triples = 0L;
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<CheckSum> checkSums = stream.map(CheckSum::new).collect(Collectors.toList());

            doubles = checkSums.stream().filter(checksum -> checksum.hasAtLeastOneOfCount(2)).count();
            triples = checkSums.stream().filter(checksum -> checksum.hasAtLeastOneOfCount(3)).count();
            long result1 = doubles * triples;

            Logger.getGlobal().info(() -> String.format("Result 1: %d", result1));

            checkSums.forEach(checkSum -> checkSums.forEach(checkSumSub -> {
                Optional<String> result2 = checkSumSub.isOneOff(checkSum);
                if (result2.isPresent()) {
                    Logger.getGlobal().info(() -> String.format("Result 2: %s", result2.get()));
                }
            }));

        }
    }
}

class CheckSum {
    private String line;
    private Map<Character, Integer> checkMap = new HashMap<>();

    public CheckSum(String line) {
        this.line = line;
        this.generateCheckSum();
    }

    public String getLine() {
        return line;
    }

    public boolean hasAtLeastOneOfCount(Integer toCheck) {
        return checkMap.values().stream().filter(count -> count.equals(toCheck)).count() > 0;
    }

    public Optional<String> isOneOff(CheckSum checkAgainst) {
        // skip equal strings
        if (checkAgainst.getLine().equals(line)) {
            return Optional.empty();
        }

        int differences = 0;
        int length = line.length();
        StringBuilder duplicates = new StringBuilder();
        for (int x = 0; x < length; x++) {
            if (line.charAt(x) != checkAgainst.getLine().charAt(x)) {
                differences++;
            } else {
                duplicates.append(line.charAt(x));
            }
        }
        if (differences == 1) {
            return Optional.of(duplicates.toString());
        } else {
            return Optional.empty();
        }
    }

    private void generateCheckSum() {
        checkMap.clear();
        line.chars().mapToObj(c -> (char) c).forEach(character -> {
            if (!checkMap.containsKey(character)) {
                checkMap.put(character, 1);
            } else {
                checkMap.put(character, checkMap.get(character) + 1);
            }
        });
    }
}