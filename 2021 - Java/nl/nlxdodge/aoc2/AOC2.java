package nl.nlxdodge.aoc2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

class AOC2 {
    private static final String FILE_PATH = "./nl/nlxdodge/aoc2/input.txt";

    public static void main(String[] args) throws IOException {

        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            int hor = 0;
            int depth = 0;

            List<String> commands = stream.toList();

            for (String line : commands) {
                String comm = line.substring(0, line.length() - 1);
                Integer val = Integer.parseInt(line.substring(line.length() - 1, line.length()));
                switch (comm) {
                    default:
                    case "forward ":
                        hor += val;
                        break;
                    case "down ":
                        depth += val;
                        break;
                    case "up ":
                        depth -= val;
                        break;
                }
            }
            int result1 = hor * depth;

            hor = 0;
            depth = 0;
            int aim = 0;
            for (String line : commands) {
                String comm = line.substring(0, line.length() - 1);
                Integer val = Integer.parseInt(line.substring(line.length() - 1, line.length()));
                switch (comm) {
                    default:
                    case "forward ":
                        hor += val;
                        depth += aim * val;
                        break;
                    case "down ":
                        aim += val;
                        break;
                    case "up ":
                        aim -= val;
                        break;
                }
            }
            int result2 = hor * depth;

            Logger.getGlobal().info(() -> String.format("Result 1: %s", result1));
            Logger.getGlobal().info(() -> String.format("Result 2: %s", result2));
        }
    }
}