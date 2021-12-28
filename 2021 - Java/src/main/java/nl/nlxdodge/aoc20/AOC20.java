package src.main.java.nl.nlxdodge.aoc20;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AOC20 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input2.txt", FOLDER_NAME);

    private static final Integer SIZE = 120;
    private static final Integer OFFSET = 5;

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            String algorithm = list.get(0);
            Character[][] image = parseImageInput(list.subList(2, list.size()));
            
            Character[][] newImage = enhanceImage(image, algorithm);
            newImage = enhanceImage(newImage, algorithm);
            toFile(newImage);

            Long result1 = Arrays.stream(newImage).flatMap(Arrays::stream).filter(v -> v.equals('#')).count();
            System.out.println(String.format("Result 1: %s", result1));

            // code for part two

            String result2 = "";
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    private static void toFile(Character[][] image) throws IOException {
        try (FileWriter writer = new FileWriter(String.format("./nl/nlxdodge/%s/ouput.txt", FOLDER_NAME))) {
            for (Character[] chrs : image) {
                String str = Arrays.stream(chrs).map(c -> c.toString()).collect(Collectors.joining());
                writer.write(str + System.lineSeparator());
            }
        }

    }

    private static Character[][] enhanceImage(Character[][] image, String algorithm) {
        Character[][] newImage = new Character[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                newImage[i][j] = determinePixel(image, algorithm, i, j);
            }
            System.out.print("Enhancing: " + (i + 1) + "/" + SIZE + "\r");
        }
        System.out.println("");
        return newImage;
    }

    private static void printImage(String[][] image) {
        for (int i = 0; i < image.length - 1; i++) {
            for (int j = 0; j < image[0].length - 1; j++) {
                System.out.print(image[i][j]);
            }
            System.out.println("");
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

    private static Character[][] parseImageInput(List<String> list) {
        Character[][] image = new Character[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Character chr = ',';
                if (i < list.size() - 1 && j < list.get(0).length()) {
                    chr = list.get(i).charAt(j);
                }
                image[i][j] = image[i][j] == null ? ',' : image[i][j];
                if (i + OFFSET < image.length && j + OFFSET < image[0].length) {
                    image[i + OFFSET][j + OFFSET] = chr;
                }
            }
            System.out.print("Loading: " + (i + 1) + "/" + SIZE + "\r");
        }
        System.out.println("");
        return image;
    }

    private static Character determinePixel(Character[][] image, String algo, Integer x, Integer y) {
        StringBuilder input = new StringBuilder();
        for (int i = x - 2; i <= x; i++) {
            for (int j = y - 2; j <= y; j++) {
                if (i >= 0 && j >= 0 && i < image.length && j < image.length) {
                    input.append(image[i][j]);
                }
            }
        }
        String binaryInput = input.toString().replace("#", "1").replace(".", "0");
        if (binaryInput.contains("1") || binaryInput.contains("0")) {
            binaryInput = binaryInput.replace(",", "0");
            Integer position = Integer.parseInt(binaryInput, 2);
            return algo.charAt(position);
        } else {
            return ',';
        }
    }
}