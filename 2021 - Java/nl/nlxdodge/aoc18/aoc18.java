package nl.nlxdodge.aoc18;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class aoc18 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> numbers = stream.toList();

            List<Number> maths = createSnailNumbers(numbers);

            // code for part one

            Long result1 = (long) maths.size();
            System.out.println(String.format("Result 1: %s", result1));

            // code for part two

            String result2 = "";
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    private static List<Number> createSnailNumbers(List<String> numbers) {
        List<Number> maths = new ArrayList<>();
        for (String line : numbers) {
            Number root = new Number();

            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(0) == '[' && line.charAt(line.length() - 1) == ']') {
                    line = handleArray(root, line);
                }
            }
            maths.add(root);
        }
        return maths;
    }

    public static String handleArray(Number number, String line) {
        line = line.substring(1);
        line = line.substring(0, line.length() - 1);

        List<String> strNumbers = new ArrayList<>(List.of(line.split(",")));
        try {
            number.xNumber = Integer.parseInt(strNumbers.get(0));
            strNumbers.remove(0);
        } catch (NumberFormatException e) {
        }
        try {
            number.yNumber = Integer.parseInt(strNumbers.get(strNumbers.size() - 1));
            strNumbers.remove(strNumbers.size() - 1);
        } catch (NumberFormatException e) {
        }
        try {
            Integer.parseInt(strNumbers.get(0));
        } catch (NumberFormatException e) {
            number.xList = new Number();
            String sub = String.join(",", strNumbers.subList(0, 1));
            line = handleArray(number.xList, sub);
        } catch (IndexOutOfBoundsException e) {

        }
        try {
            Integer.parseInt(strNumbers.get(strNumbers.size() - 1));
        } catch (NumberFormatException e) {
            number.yList = new Number();
            String sub = String.join(",", strNumbers.subList(strNumbers.size() - 2, strNumbers.size() - 1));
            line = handleArray(number.yList, sub);
        } catch (IndexOutOfBoundsException e) {

        }
        return line;
    }
}

class Number {
    Integer xNumber = -1;
    Integer yNumber = -1;

    Number xList;
    Number yList;

    public Number() {
    }

    public Number(Integer x, Integer y) {
        this.xNumber = x;
        this.yNumber = y;
    }
}