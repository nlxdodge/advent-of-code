package nl.nlxdodge.aoc15;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class AOC15 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            Node[][] map = initializeMap(list);
            // code for part one

            Node smallestPath = aStar(map);
            printPath(smallestPath, map);
            System.out.println("WAA");

            // Long path = getLowestRiskPath(map);

            Long result1 = (long) list.size();
            System.out.println(String.format("Result 1: %s", result1));

            // code for part two

            String result2 = "";
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    public static void printPath(Node target, Node[][] map) {
        Node n = target;
        List<Node> nodes = new ArrayList<>();
        while (n.parent != null) {
            nodes.add(n);
            n = n.parent;
        }
        nodes.add(n);

        for (Node node : nodes) {
            map[node.y][node.x].value = 0;
        }
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                String toPrint = map[y][x].value == 0 ? "█" : "" + map[y][x].value;
                System.out.print(toPrint);
            }
            System.out.println("");
        }
        System.out.println("----------");
    }

    private static Node aStar(Node[][] map) {
        Node start = map[0][0];
        Node target = map[map[0].length - 1][map.length - 1];

        PriorityQueue<Node> closedNodes = new PriorityQueue<>();
        PriorityQueue<Node> openNodes = new PriorityQueue<>(List.of(start));

        while (!openNodes.isEmpty()) {
            Node n = openNodes.peek();
            if (n == target) {
                return n;
            }
            List<Node> neighbours = getNeighbours(map, n);
            for (Node m : neighbours) {
                Long totalWeight = n.g + m.value;
                if (!closedNodes.contains(m) && !openNodes.contains(m)) {
                    m.parent = n;
                    m.g = totalWeight;
                    m.f = m.g + m.value;
                    openNodes.add(m);
                } else {
                    printPath(n, map);
                    if (totalWeight < m.g) {
                        m.parent = n;
                        m.g = totalWeight;
                        m.f = m.g + m.value;

                        if (closedNodes.contains(m)) {
                            closedNodes.remove(m);
                            openNodes.add(m);
                        }
                        
                    }
                }
            }
            openNodes.remove(n);
            closedNodes.add(n);
        }
        return null;
    }

    private static List<Node> getNeighbours(Node[][] map, Node node) {
        List<Node> neighbours = new ArrayList<>();
        // 4 of em
        if (node.y + 1 < map.length) {
            neighbours.add(map[node.y + 1][node.x]);
        }
        if (node.y - 1 > 0) {
            neighbours.add(map[node.y - 1][node.x]);
        }
        if (node.x + 1 < map[0].length) {
            neighbours.add(map[node.y][node.x + 1]);
        }
        if (node.x - 1 > 0) {
            neighbours.add(map[node.y][node.x - 1]);
        }
        return neighbours;
    }

    private static Node[][] initializeMap(List<String> list) {
        Node[][] map = new Node[list.size()][list.get(0).length()];
        for (int y = 0; y < list.size(); y++) {
            String line = list.get(y);
            for (int x = 0; x < list.get(0).length(); x++) {
                map[y][x] = new Node(y, x, Integer.parseInt("" + line.charAt(x)));
            }
        }
        return map;
    }
}

class Node implements Comparable<Node> {
    Integer y = 0;
    Integer x = 0;
    Integer value = 0;
    Node parent;
    Long g = -20L;
    Long f = -20L;

    public Node(Integer x, Integer y, Integer value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    @Override
    public int compareTo(Node node) {
        return Long.compare(this.f, f);
    }

    public String toString() {
        return String.format("(Y%s,X%s = V%s T%s)", y, x, value, g);
    }
}
