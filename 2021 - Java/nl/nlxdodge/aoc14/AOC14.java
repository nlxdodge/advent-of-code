package nl.nlxdodge.aoc14;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

            List<Polymer> polymer = initalizePolymer(list.get(0));
            Map<String, String> rules = initalizeRules(list.subList(2, list.size()));

            Long tenStepResult = 0L;
            int maxSteps = 40;
            for (int step = 1; step <= maxSteps; step++) {
                polymer = parseStep(polymer, rules);
                polymer.stream().forEach(p -> p.newChar = false);
                System.out.println(String.format("Step: %s Polymer size: %s", step, polymer.size()));
                if (step == 100) {
                    tenStepResult = subTractHighestLowest(polymer);
                }
            }

            Long result1 = tenStepResult;
            System.out.println(String.format("Result 1: %s", result1));

            // code for part two

            Long result2 = subTractHighestLowest(polymer);
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    private static Long subTractHighestLowest(List<Polymer> polymer) {
        Long lowest = Long.MAX_VALUE;
        Long highest = 0L;

        for (Polymer poly : polymer) {
            Long count = polymer.stream().filter(p -> p.chr.equals(poly.chr)).count();
            if (count > highest) {
                highest = count;
            }
            if (count < lowest) {
                lowest = count;
            }
        }
        return highest - lowest;
    }

    private static List<Polymer> initalizePolymer(String string) {
        List<Polymer> polymer = new ArrayList<>();
        for (char chr : string.toCharArray()) {
            polymer.add(new Polymer(Character.toString(chr)));
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

    private static List<Polymer> parseStep(List<Polymer> polymer, Map<String, String> rules) {
        for (Entry<String, String> rule : rules.entrySet()) {
            for (int walk = 0; walk < polymer.size() - 1; walk++) {
                if (polymer.get(walk).newChar || polymer.get(walk + 1).newChar) {
                    continue;
                }
                String searchFor = polymer.get(walk).chr + "" + polymer.get(walk + 1).chr;
                if (searchFor.equals(rule.getKey())) {
                    Polymer newPolymer = new Polymer(rule.getValue(), true);
                    polymer.add(walk + 1, newPolymer);
                    // System.out.println(String.format("%s -> %s", polymer, newPolymer));
                }
            }
        }
        return polymer;
    }
}

class Polymer {
    String chr;
    Boolean newChar = false;

    public Polymer(String chr) {
        this.chr = chr;
    }

    public Polymer(String chr, Boolean newChar) {
        this(chr);
        this.newChar = newChar;
    }

    public String toString() {
        return String.format("%s = %s", chr, newChar);
    }
}
