package nl.nlxdodge.aoc22;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AOC22 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./2021 - Java/src/main/java/nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            // int size = 200;
            // int offset = size / 2;
            // int[][][] reactor = new int[size][size][size];

            // // TODO save a quboid as object with marking in list
            // // if quboids intersect parse them
            // // then go over quboids to see

            // for (String line : list) {
            //     String[] parts = line.split(" ");
            //     int on = parts[0].contains("on") ? 1 : 0;

            //     String[] cords = parts[1].split(",");

            //     String filter = "\\.\\.";
            //     String[] xCords = cords[0].substring(2, cords[0].length()).split(filter);
            //     String[] yCords = cords[1].substring(2, cords[1].length()).split(filter);
            //     String[] zCords = cords[2].substring(2, cords[2].length()).split(filter);

            //     for (int x = Integer.parseInt(xCords[0]); x <= Integer.parseInt(xCords[1]); x++) {
            //         String[] allCords = Stream.of(xCords, yCords, zCords).flatMap(Stream::of)
            //                 .toArray(String[]::new);
            //         if (!starup(allCords)) {
            //             continue;
            //         }
            //         for (int y = Integer.parseInt(yCords[0]); y <= Integer.parseInt(yCords[1]); y++) {
            //             for (int z = Integer.parseInt(zCords[0]); z <= Integer.parseInt(zCords[1]); z++) {
            //                 reactor[x + offset][y + offset][z + offset] = on;
            //                 // System.out.println(String.format("Setting: %s,%s,%s -> %s", x, y, z, on));
            //             }
            //         }
            //     }
            //     System.out.print("Parsing: " + (line + 1) + "/" + list.size() + "\r");
            //     // System.out.println("Next rule");
            // }

            // int counter = 0;
            // for (int x = 0; x < reactor.length; x++) {
            //     for (int y = 0; y < reactor.length; y++) {
            //         for (int z = 0; z < reactor.length; z++) {
            //             if (reactor[x][y][z] == 1) {
            //                 counter++;
            //             }
            //         }
            //     }
            // }

            // int result1 = counter;
            // System.out.println(String.format("Result 1: %s", result1));

            List<Cuboid> cuboids = new ArrayList<>();

            for (String line : list) {
                String[] parts = line.split(" ");
                boolean on = parts[0].contains("on");

                String[] cords = parts[1].split(",");

                String filter = "\\.\\.";

                List<Integer> xCords = Arrays.stream(cords[0].substring(2, cords[0].length()).split(filter))
                        .map(Integer::parseInt).toList();
                List<Integer> yCords = Arrays.stream(cords[1].substring(2, cords[1].length()).split(filter))
                        .map(Integer::parseInt).toList();
                List<Integer> zCords = Arrays.stream(cords[2].substring(2, cords[2].length()).split(filter))
                        .map(Integer::parseInt).toList();

                cuboids.add(new Cuboid(xCords.get(0), xCords.get(1), yCords.get(0), yCords.get(1), zCords.get(0),
                        zCords.get(1), on));
            }

            String result2 = "";
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    private static boolean starup(String[] allCords) {
        List<Integer> cords = Arrays.stream(allCords).map(Integer::parseInt).toList();
        for (Integer cord : cords) {
            if (cord > 50 || cord < -50) {
                return false;
            }
        }
        return true;
    }
}

class Cuboid {
    int xFrom = 0;
    int xTo = 0;
    int yFrom = 0;
    int yTo = 0;
    int zFrom = 0;
    int zTo = 0;

    // TODO make this to an 3d array inside it so it can acctualy be changed when Cuboids intersect

    boolean on = false;

    public Cuboid(
            int xFrom,
            int xTo,
            int yFrom,
            int yTo,
            int zFrom,
            int zTo,
            boolean on) {
        this.xFrom = xFrom;
        this.xTo = xTo;
        this.yFrom = yFrom;
        this.yTo = yTo;
        this.zFrom = zFrom;
        this.zTo = zTo;
        this.on = on;
    }


}
