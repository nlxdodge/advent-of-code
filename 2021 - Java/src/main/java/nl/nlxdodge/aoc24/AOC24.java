package nl.nlxdodge.aoc24;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class AOC24 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./2021 - Java/src/main/java/nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            ALU alu = new ALU();

            for (String line : list) {
                parseLine(alu, line);
            }

            // code for part one

            Long result1 = (long) list.size();
            System.out.println(String.format("Result 1: %s", result1));

            // code for part two

            String result2 = "";
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    private static void parseLine(ALU alu, String line) {
        int left = Integer.parseInt(line.substring(4, 5));
        int right = Integer.parseInt(line.substring(6, 7));

        switch (line.substring(0, 3)) {
            case "inp":
                // alu.add(left);
                break;
            case "mul":
                alu.mul(left);
                break;
            default:
                System.out.println("NOOP FOUND");
                break;

        }
    }
}

class ALU {
    int w, x, y, z = 0;

    int a = 0;
    int b = 0;

    public void inp(String input) {
        if(input.equals("x")) {
            // this.x = input
        }
    }

    public void mul(int left) {

    }

}
