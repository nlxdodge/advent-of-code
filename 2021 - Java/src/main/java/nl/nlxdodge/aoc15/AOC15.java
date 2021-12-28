package src.main.java.nl.nlxdodge.aoc15;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class AOC15 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./2021 - Java/src/main/java/nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            Node[][] map = initializeMap(list);

            List<Node> smallestPath = aStar(map);

            Long result1 = (long) smallestPath
                    .subList(0, smallestPath.size() - 1)
                    .stream()
                    .mapToLong(n -> n.value)
                    .sum();
            System.out.println(String.format("Result 1: %s", result1));

            Node[][] biggerMap = resizeMap(map, 5);
            List<Node> smallestPathBiggerMap = aStar(biggerMap);

            Long result2 = (long) smallestPathBiggerMap
                    .subList(0, smallestPathBiggerMap.size() - 1)
                    .stream()
                    .mapToLong(n -> n.value)
                    .sum();
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    private static List<Node> aStar(Node[][] map) {
        Node start = map[0][0];
        Node target = map[map[0].length - 1][map.length - 1];

        Map<Node, Node> cameFrom = new LinkedHashMap<>();
        PriorityQueue<Node> openSet = new PriorityQueue<>(List.of(start));

        while (!openSet.isEmpty()) {
            Node current = openSet.stream().min(Node::compareTo).get();
            if (current == target) {
                return reConstructPath(cameFrom, current);
            }
            openSet.remove(current);
            List<Node> neighbours = getNeighbours(map, current);
            for (Node m : neighbours) {
                Long gScore = current.g + m.value;
                if (gScore < m.g) {
                    cameFrom.put(m, current);
                    m.g = gScore;
                    m.f = gScore + m.value;
                    if (!openSet.contains(m)) {
                        openSet.add(m);
                    }
                }
            }
        }
        throw new RuntimeException("Couldn't find target in A-star algorithm");
    }

    private static List<Node> reConstructPath(Map<Node, Node> cameFrom, Node current) {
        List<Node> path = new ArrayList<>(List.of(current));
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }
        return path;
    }

    public static void printMap(List<Node> path, Node[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map.length; x++) {
                System.out.print(path.contains(map[y][x]) ? "█" : map[y][x].value);
            }
            System.out.println("");
        }
    }

    private static void save(List<Node> path, Node[][] map) {
        try (PrintStream output = new PrintStream(new File("./nl/nlxdodge/aoc15/output.txt"))) {
            for (int y = 0; y < map.length; y++) {
                StringBuilder line = new StringBuilder();
                for (int x = 0; x < map.length; x++) {
                    line.append(path.contains(map[y][x]) ? "█" : map[y][x].value);
                }
                output.println(line.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<Node> getNeighbours(Node[][] map, Node node) {
        List<Node> neighbours = new ArrayList<>();
        if (node.x + 1 < map.length) {
            neighbours.add(map[node.x + 1][node.y]);
        }
        if (node.x - 1 > 0) {
            neighbours.add(map[node.x - 1][node.y]);
        }
        if (node.y + 1 < map[0].length) {
            neighbours.add(map[node.x][node.y + 1]);
        }
        if (node.y - 1 > 0) {
            neighbours.add(map[node.x][node.y - 1]);
        }
        return neighbours;
    }

    private static Node[][] initializeMap(List<String> list) {
        Node[][] map = new Node[list.size()][list.get(0).length()];
        for (int y = 0; y < list.size(); y++) {
            for (int x = 0; x < list.get(0).length(); x++) {
                String line = list.get(y);
                map[y][x] = new Node(y, x, Integer.parseInt("" + line.charAt(x)));
            }
        }
        return map;
    }

    private static Node[][] resizeMap(Node[][] map, int times) {
        Node[][] biggerMap = new Node[map.length * times][map.length * times];
        for (int yT = 0; yT < times; yT++) {
            for (int xT = 0; xT < times; xT++) {
                for (int y = 0; y <= map.length - 1; y++) {
                    for (int x = 0; x <= map.length - 1; x++) {
                        Integer newValue = map[y][x].value + yT + xT;
                        newValue = (newValue >= 10 ? (newValue % 10) + 1 : newValue);
                        Node newNode = new Node(x + (xT * map.length), y + (yT * map.length), newValue);
                        biggerMap[x + (xT * map.length)][y + (yT * map.length)] = newNode;
                    }
                }
            }
        }
        return biggerMap;
    }
}

class Node implements Comparable<Node> {
    Integer y = 0;
    Integer x = 0;
    Integer value = 0;
    Long g = Long.MAX_VALUE;
    Long f = Long.MAX_VALUE;

    public Node(Integer x, Integer y, Integer value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    @Override
    public int compareTo(Node node) {
        return Long.compare(f, node.f);
    }

    public String toString() {
        return String.format("(Y%s,X%s = V%s G%s F%s)", y, x, value, g, f);
    }
}
