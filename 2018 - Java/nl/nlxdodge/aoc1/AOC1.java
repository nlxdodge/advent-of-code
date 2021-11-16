package nl.nlxdodge.aoc1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

class AOC1 {
    private static final String FILE_PATH = "./nl/nlxdodge/aoc1/input.txt";

    public static void main(String[] args) throws IOException {
        int rounds = 0;
        Frequency frequency = new Frequency();

        while (!frequency.hasDuplicate()) {
            rounds += 1;
            try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
                stream.forEach(frequency::runCommand);
            }
            if (rounds == 1) {
                Logger.getGlobal().info(() -> String.format("Result 1: %s", frequency.getFrequency()));
            }
        }
        Logger.getGlobal().info(() -> String.format("Result 2: %s", frequency.getFrequency()));
    }

}

class Frequency {
    private int frequency = 0;
    private List<Integer> frequenciesHitList = new ArrayList<>();
    private boolean foundDuplicate = false;

    public int getFrequency() {
        return frequency;
    }

    public boolean hasDuplicate() {
        return foundDuplicate;
    }

    public void runCommand(String command) {
        String operator = command.substring(0, 1);
        int amount = Integer.parseInt(command.substring(1, command.length()));
        switch (operator) {
        default:
        case "+":
            frequency += amount;
            break;
        case "-":
            frequency -= amount;
            break;
        }

        // Also check within loop for duplicate frequency
        if (doesOverlap()) {
            foundDuplicate = true;
        }

        frequenciesHitList.add(frequency);
    }

    public boolean doesOverlap() {
        return frequenciesHitList.contains(frequency);
    }
}