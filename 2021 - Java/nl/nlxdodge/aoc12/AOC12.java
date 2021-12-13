package nl.nlxdodge.aoc12;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class AOC12 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            List<Cave> caves = setCavePaths(list);

            Integer result1 = findAllPaths(caves).size();
            System.out.println(String.format("Result 1: %s", result1));

            // code for part two

            String result2 = "";
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    public static List<List<Cave>> findAllPaths(List<Cave> caves) {
        Optional<Cave> startCave = findCave(caves, "start");

        List<List<Cave>> allRoutes = new ArrayList<>();

        if (startCave.isPresent()) {
            traverse(allRoutes, startCave.get(), new ArrayList<>(List.of(startCave.get())));
        }
        return allRoutes;
    }

    public static List<Cave> traverse(List<List<Cave>> allRoutes, Cave cave, List<Cave> route) {
        for (Cave subCave : cave.getSubCaves()) {
            if (subCave.canVisit(1)) {
                route.add(subCave);
                subCave.setVisited();
                if (subCave.getName().equals("end")) {
                    System.out.println(route);
                    allRoutes.add(new ArrayList<>(route));
                    route = new ArrayList<>();

                    

                } else {
                    traverse(allRoutes, subCave, new ArrayList<>(route));
                }
            }
        }
        return route;
    }

    public static List<Cave> setCavePaths(List<String> input) {
        List<Cave> caves = new ArrayList<>();
        for (String line : input) {
            String[] nodesText = line.split("-");
            Optional<Cave> cave1 = findCave(caves, nodesText[0]);
            Optional<Cave> cave2 = findCave(caves, nodesText[1]);
            if (cave1.isEmpty()) {
                cave1 = Optional.of(new Cave(nodesText[0]));
                caves.add(cave1.get());
            }
            if (cave2.isEmpty()) {
                cave2 = Optional.of(new Cave(nodesText[1]));
                caves.add(cave2.get());
            }
            cave1.get().addSubCave(cave2.get());
            cave2.get().addSubCave(cave1.get());
        }
        return caves;
    }

    public static Optional<Cave> findCave(List<Cave> caves, String name) {
        return caves.stream().filter(c -> c.getName().equals(name)).findFirst();
    }
}

class Cave {
    private String name;
    private Integer visitedTimes = 0;
    private List<Cave> subCaves = new ArrayList<>();

    public Cave(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Cave> getSubCaves() {
        return this.subCaves;
    }

    public void addSubCave(Cave cave) {
        this.subCaves.add(cave);
    }

    public List<Cave> findConnection(String name) {
        return subCaves.stream().filter(c -> c.name.equals(name)).toList();
    }

    public boolean canVisit(Integer times) {
        if (name.equals("start")) {
            return false;
        }
        return visitedTimes < times || Character.isUpperCase(name.charAt(0));
    }

    public void setVisited() {
        visitedTimes += 1;
    }

    public String toString() {
        return String.format("%s", name);
    }
}
