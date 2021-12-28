package src.main.java.nl.nlxdodge.aoc6;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class AOC6 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            Map<Integer, Long> school = initializeMap(list);

            Long result1 = simulateDays(80, new HashMap<>(school));
            Long result2 = simulateDays(256, new HashMap<>(school));

            System.out.println(String.format("Result 1: %s", result1));
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    public static Long simulateDays(int days, Map<Integer, Long> school) {
        Map<Integer, Long> newSchool = new HashMap<>();
        for (int day = 1; day < days + 1; day++) {
            for (Entry<Integer, Long> fish : school.entrySet()) {
                int spawn = fish.getKey();
                newSchool.putAll(handleFish(spawn, school));

            }
            long old0 = school.get(0) != null ? school.get(0) : 0;
            long old7 = school.get(7) != null ? school.get(7) : 0;
            newSchool.put(6, old0 + old7);

            school.clear();
            school.putAll(newSchool);
            newSchool.clear();
        }
        return mapSize(school);
    }

    public static Map<Integer, Long> handleFish(Integer spawn, Map<Integer, Long> school) {
        Map<Integer, Long> newSchool = new HashMap<>();
        if (spawn == 0) {
            newSchool.put(8, school.get(0) != null ? school.get(0) : 0);
        } else {
            int newDay = spawn - 1;
            if (school.get(newDay) == null && school.get(spawn) >= 1) {
                newSchool.put(newDay, school.get(spawn));
            } else {
                long oldNumb = school.get(spawn) != null ? school.get(spawn) : 0;
                newSchool.put(newDay, oldNumb);
            }
        }
        return newSchool;
    }

    public static Map<Integer, Long> initializeMap(List<String> list) {
        Map<Integer, Long> school = new HashMap<>(
                Map.of(0, 0L, 1, 0L, 2, 0L, 3, 0L, 4, 0L, 5, 0L, 6, 0L, 7, 0L, 8, 0L));
        for (String line : list) {
            List<String> spawns = List.of(line.split(","));
            for (String spawn : spawns) {
                int number = Integer.parseInt(spawn);
                school.put(number, school.get(number) + 1);
            }
        }
        return school;
    }

    public static long mapSize(Map<Integer, Long> school) {
        return school.values().stream().mapToLong(Long::longValue).sum();
    }
}
