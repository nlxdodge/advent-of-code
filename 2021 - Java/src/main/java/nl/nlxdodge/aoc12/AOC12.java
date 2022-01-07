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
    private static final String FILE_PATH = String.format("./2021 - Java/src/main/java/nl/nlxdodge/%s/input.txt",
            FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            List<Cave> caves = setListOfCaves(list);

            List<List<Cave>> allRoutes = findAllPaths(caves);

            System.out.println("All Routes: " + allRoutes);

            int result1 = allRoutes.size();
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
            traverse(allRoutes, new ArrayList<>(List.of(startCave.get())), caves);
        }
        return allRoutes;
    }

    public static List<Cave> traverse(List<List<Cave>> allRoutes, List<Cave> route, List<Cave> caves) {
        Cave currentCave = route.get(route.size() - 1);
        
        // reset visited if not in route
        caves.forEach(cave -> {
            if (!route.contains(cave)) {
                cave.setVisited(0);
            }
        });

        // with the current route get last Cave's subcaves
        for (Cave subCave : currentCave.getSubCaves()) {

            // can we visit this subcave and is this not in the current allroutes
            if (subCave.canVisit(1) && !allRoutes.contains(route)) {

                // add subcave to route and set visited
                route.add(subCave);
                subCave.setVisited(subCave.getVisited() + 1);

                System.out.println(
                        String.format("%s -> %s (%s)", currentCave.getName(), subCave.getName(),
                                subCave.canVisit(1)));

                // if last equals end add to Allroutes
                if (subCave.getName().equals("end")) {
                    System.out.println("New route found: " + route);
                    allRoutes.add(new ArrayList<>(route));
                } else {
                    traverse(allRoutes, new ArrayList<>(route), caves);
                }
            }
        }
        return route;
    }

    public static List<Cave> setListOfCaves(List<String> input) {
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
        return visitedTimes < times || Character.isUpperCase(name.charAt(0)) || name.equals("end");
    }

    public int getVisited() {
        return visitedTimes;
    }

    public void setVisited(int visited) {
        visitedTimes = visited;
    }

    public String toString() {
        return String.format("%s", name);
    }
}
