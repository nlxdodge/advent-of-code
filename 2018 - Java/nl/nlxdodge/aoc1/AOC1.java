package nl.nlxdodge.aoc1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

class AOC1 {
    private static final String LOGGER = "AOC1";
    private static final String FILE_PATH = "./aoc1/input.txt";

    public static void main(String[] args) throws IOException {
        int rounds = 0;
        Frequency frequency = new Frequency();

        while (!frequency.hasDuplicate()) {
            rounds += 1;
            try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
                stream.forEach(frequency::runCommand);
            }
            if (rounds == 1) {
                Logger.getLogger(LOGGER).log(Level.INFO, "Result 1: {0}", frequency.getFrequency());
            }
        }
        Logger.getLogger(LOGGER).log(Level.INFO, "Result 2: {0}", frequency.getFrequency());
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

    /**
     * When running a command add that to the frequency and hitlist
     * 
     * @param command
     */
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

    /**
     * Checks if the current frequency already overlaps
     * 
     * @return
     */
    public boolean doesOverlap() {
        return frequenciesHitList.contains(frequency);
    }
}