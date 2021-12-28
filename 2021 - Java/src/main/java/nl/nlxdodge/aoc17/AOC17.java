package nl.nlxdodge.aoc17;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AOC17 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./2021 - Java/src/main/java/nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            List<Cord> targetArea = initializeTargetDisplay(list.get(0));

            int highestSearchX = targetArea.stream().mapToInt(x -> x.x).max().getAsInt();
            int lowestSearchY = targetArea.stream().mapToInt(pos -> pos.y).min().getAsInt();

            int highestY = 0;
            List<Cord> vels = new ArrayList<>();
            for (int x = 0; x <= highestSearchX; x++) {
                for (int y = lowestSearchY; y <= Math.abs(lowestSearchY); y++) {
                    Probe probe = new Probe(x, y, targetArea);
                    if (probe.canHitTarget()) {
                        vels.add(new Cord(x, y));
                        if (probe.highestY > highestY) {
                            highestY = probe.highestY;
                        }
                    }
                }
            }
            Integer result1 = highestY;
            Integer result2 = vels.size();

            System.out.println(String.format("Result 1: %s", result1));
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    private static List<Cord> initializeTargetDisplay(String string) {
        String[] split = string.substring(13, string.length()).split(", ");
        String[] xSplit = split[0].substring(2, split[0].length()).split("\\.\\.");
        String[] ySplit = split[1].substring(2, split[1].length()).split("\\.\\.");

        List<Cord> targets = new ArrayList<>();
        for (int y = Integer.parseInt(ySplit[0]); y <= Integer.parseInt(ySplit[1]); y++) {
            for (int x = Integer.parseInt(xSplit[0]); x <= Integer.parseInt(xSplit[1]); x++) {
                targets.add(new Cord(x, y));
            }
        }
        return targets;
    }
}

class Cord {
    int x;
    int y;

    public Cord(int x, int y) {
        this.y = y;
        this.x = x;
    }

    public boolean equals(Object o) {
        Cord input = (Cord) o;
        if (input == null) {
            return false;
        }
        return (input.x == x && input.y == y);
    }

    public String toString() {
        return String.format("(%s, %s)", x, y);
    }
}

class Probe {
    Cord pos = new Cord(0, 0);
    Cord vel;
    int highestY = 0;
    List<Cord> targets;

    public Probe(int xVel, int yVel, List<Cord> targets) {
        this.vel = new Cord(xVel, yVel);
        this.targets = targets;
    }

    public boolean canHitTarget() {
        while (!inRange()) {
            if (step()) {
                return true;
            }
        }
        return false;
    }

    public boolean step() {
        pos.x += vel.x;
        pos.y += vel.y;
        if (vel.x > 0) {
            vel.x--;
        } else if (vel.x < 0) {
            vel.x++;
        }
        if (highestY < pos.y) {
            highestY = pos.y;
        }
        vel.y--;
        return hitTarget();
    }

    public boolean inRange() {
        int highestX = targets.stream().mapToInt(c -> c.x).max().getAsInt();
        int smallestY = targets.stream().mapToInt(c -> c.y).min().getAsInt();
        return (pos.x > highestX || pos.y < smallestY);
    }

    public boolean hitTarget() {
        return targets.contains(pos);
    }
}
