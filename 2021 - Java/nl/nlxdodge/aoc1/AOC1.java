package nl.nlxdodge.aoc1;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

class AOC1 {
    public static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    public static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);
    public static void main(String[] args) throws IOException {
        List<Depth> filterDepths = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<Depth> depths = stream.map(Depth::new).toList();
            List<Depth> sumDepths = new ArrayList<>();

            depths.forEach(depth -> {
                if (!filterDepths.isEmpty() && filterDepths.get(filterDepths.size() - 1).checkIncrease(depth)) {
                    depth.up = true;
                }
                if (filterDepths.size() >= 2) {
                    Depth second = filterDepths.get(filterDepths.size() - 1);
                    Depth third = filterDepths.get(filterDepths.size() - 2);
                    Depth sumDepth = new Depth(depth.getSum(second, third).toString());
                    if (!sumDepths.isEmpty() && sumDepths.get(sumDepths.size() - 1).checkIncrease(sumDepth)) {
                        sumDepth.up = true;
                    }
                    sumDepths.add(sumDepth);
                }

                filterDepths.add(depth);
            });

            long result1 = filterDepths.stream().filter(d -> d.up).count();
            long result2 = sumDepths.stream().filter(d -> d.up).count();

            Logger.getGlobal().info(() -> String.format("Result 1: %s", result1));
            Logger.getGlobal().info(() -> String.format("Result 2: %s", result2));
        }
    }
}

class Depth {
    int x;
    boolean up;

    public Depth(String x) {
        this.x = Integer.parseInt(x);
    }

    public boolean checkIncrease(Depth depth) {
        return x < depth.x;
    }

    public Integer getSum(Depth d1, Depth d2) {
        return x + d1.x + d2.x;
    }

    public String toString() {
        return "Depth: " + x + " Up: " + up + "\r\n";
    }
}