package nl.nlxdodge.aoc7;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class AOC7 {
    public static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    public static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();
            List<Integer> crabSpawn = initializeCrabs(list.get(0));

            int highest = getHighestValue(crabSpawn);

            Map<Integer, Integer> fuelMap1 = calculateFuelMap(highest, crabSpawn, false);
            int lowestFuelConsumption1 = getLowestValue(fuelMap1.values());
            Logger.getGlobal().info(() -> String.format("Result 1: %s", lowestFuelConsumption1));

            Map<Integer, Integer> fuelMap2 = calculateFuelMap(highest, crabSpawn, true);
            int lowestFuelConsumption2 = getLowestValue(fuelMap2.values());
            Logger.getGlobal().info(() -> String.format("Result 2: %s", lowestFuelConsumption2));
        }
    }

    public static List<Integer> initializeCrabs(String input) {
        List<Integer> list = new ArrayList<>();
        String[] crabs = input.split(",");
        for (String crab : crabs) {
            list.add(Integer.parseInt(crab));
        }
        return list;
    }

    public static Integer getLowestValue(Collection<Integer> values) {
        Optional<Integer> lowest = values.stream().min((x, i) -> x.compareTo(i));
        if(lowest.isPresent()) {
            return lowest.get();
        }
        return 0;
    }

    public static Integer getHighestValue(Collection<Integer> values) {
        Optional<Integer> lowest = values.stream().max((x, i) -> x.compareTo(i));
        if(lowest.isPresent()) {
            return lowest.get();
        }
        return 0;
    }

    public static Map<Integer, Integer> calculateFuelMap(int highest, List<Integer> crabSpawn, boolean incrementalConsumtion) {
        int fuelSpend = 0;
        Map<Integer, Integer> fuelMap = new HashMap<>();
        for (int i = 0; i < highest; i++) {
            for (int crab : crabSpawn) {
                int fuelRound = 0;
                int steps = Math.abs(crab - i);
                if(incrementalConsumtion) {
                    for (int step = 1; step <= steps; step++) {
                        fuelRound += step;
                    }
                } else {
                    fuelRound = steps;
                }
                fuelSpend += fuelRound;
            }
            fuelMap.put(i, fuelSpend);
            fuelSpend = 0;
        }
        return fuelMap;
    }
}
