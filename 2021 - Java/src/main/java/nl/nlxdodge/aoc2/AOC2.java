package src.main.java.nl.nlxdodge.aoc2;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

class AOC2 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> commands = stream.toList();
            Integer hor = 0;
            Integer depth = 0;
            Integer aim = 0;

            for (String line : commands) {
                String comm = line.substring(0, line.length() - 1);
                Integer val = Integer.parseInt(line.substring(line.length() - 1, line.length()));
                if (comm.equals("forward ")) {
                    hor += val;
                    depth += aim * val;
                } else if (comm.equals("down ")) {
                    aim += val;
                } else if (comm.equals("up ")) {
                    aim -= val;
                }
            }
            int result1 = hor * aim;
            int result2 = hor * depth;

            System.out.println(String.format("Result 1: %s", result1));
            System.out.println(String.format("Result 2: %s", result2));
        }
    }
}