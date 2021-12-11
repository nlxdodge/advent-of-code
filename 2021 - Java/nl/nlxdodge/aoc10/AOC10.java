package nl.nlxdodge.aoc10;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class AOC10 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    private static final List<String> IN = new ArrayList<>(List.of("(", "[", "{", "<"));
    private static final List<String> OUT = new ArrayList<>(List.of(")", "]", "}", ">"));

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            List<String> incompleteLines = new ArrayList<>();
            List<String> corruptLines = new ArrayList<>();
            
            for (String line : list) {
                Optional<String> wrongChar = parseLine(line, false);
                if (wrongChar.isPresent()) {
                    corruptLines.add(wrongChar.get());
                } else {
                    incompleteLines.add(line);
                }
            }

            Integer result1 = calScore(corruptLines);
            Logger.getGlobal().info(() -> String.format("Result 1: %s", result1));

            List<String> missingChars = new ArrayList<>();
            for (String line : incompleteLines) {
                missingChars.add(autoComplete(line));
            }

            Long result2 = calcScoreMissing(missingChars);
            Logger.getGlobal().info(() -> String.format("Result 2: %s", result2));
        }
    }

    public static Integer calScore(List<String> wrongChars) {
        Map<String, Integer> scores = Map.of(")", 3, "]", 57, "}", 1197, ">", 25137);
        Integer score = 0;
        for (String chr : wrongChars) {
            score += scores.get(chr);
        }
        return score;
    }

    public static Optional<String> parseLine(String input, boolean autoComplete) {
        Optional<String> returnValue = Optional.empty();
        List<String> buffer = new ArrayList<>();
        for (char chr : input.toCharArray()) {
            String charStr = Character.toString(chr);
            if (IN.contains(charStr)) {
                buffer.add(charStr);
            } else {
                int index = OUT.indexOf(charStr);
                if (buffer.get(buffer.size() - 1).equals(IN.get(index))) {
                    buffer.remove(buffer.size() - 1);
                } else {
                    if (!autoComplete) {
                        returnValue = Optional.of(charStr);
                        break;
                    }
                }
            }
        }
        return returnValue;
    }

    public static Long calcScoreMissing(List<String> missingChars) {
        List<Long> scores = new ArrayList<>();
        for (String line : missingChars) {
            Long score = 0L;
            for (Character chr : line.toCharArray()) {
                score *= 5L;
                score += OUT.indexOf(Character.toString(chr)) + 1;
            }
            scores.add(score);
        }
        List<Long> sortedScores = scores.stream().sorted().toList();
        return sortedScores.get(sortedScores.size() / 2);
    }

    public static String autoComplete(String input) {
        StringBuilder autoCompleteBuffer = new StringBuilder();
        List<String> buffer = new ArrayList<>(Arrays.asList(input.split("")));

        while (!buffer.isEmpty()) {
            Integer counter = 1;
            String chr = buffer.get(buffer.size() - 1);
            for (int i = buffer.size() - 1; i >= 0; i--) {
                String loopChar = buffer.get(i);
                if (OUT.indexOf(chr) == -1) {
                    // missing char needed for completing code
                    autoCompleteBuffer.append(OUT.get(IN.indexOf(chr)));
                    buffer.add(OUT.get(IN.indexOf(chr)));
                    break;
                }
                if (counter > 1 && loopChar.equals(IN.get(OUT.indexOf(chr)))) {
                    counter--;
                }
                if (loopChar.equals(chr)) {
                    counter++;
                }
                if (counter == 1 && loopChar.equals(IN.get(OUT.indexOf(chr)))) {
                    buffer.remove(i);
                    buffer.remove(buffer.size() - 1);
                    break;
                }
            }
        }
        return autoCompleteBuffer.toString();
    }
}
