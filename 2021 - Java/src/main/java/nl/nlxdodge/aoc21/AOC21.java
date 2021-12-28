package nl.nlxdodge.aoc21;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AOC21 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./2021 - Java/src/main/java/nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = stream.toList();

            Die die = new Die(100);
            Pawn p1 = new Pawn("Player 1", Integer.parseInt(list.get(0).substring(28)));
            Pawn p2 = new Pawn("Player 2", Integer.parseInt(list.get(1).substring(28)));

            boolean p1Turn = true;
            while (p1.score < 1000 && p2.score < 1000) {
                if (p1Turn) {
                    p1.move(die.throwDie() + die.throwDie() + die.throwDie());
                    p1Turn = false;
                } else {
                    p2.move(die.throwDie() + die.throwDie() + die.throwDie());
                    p1Turn = true;
                }
            }
            int lowestScore = Math.min(p1.score, p2.score);
            int result1 = lowestScore * die.totalRols;
            System.out.println(String.format("Result 1: %s", result1));

            List<State> states = new ArrayList<>();

            int result2 = 1;
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    private static Pawn determineWinner(List<State> states, Die die, Pawn p1, Pawn p2) {
        if(states.contains(new State(die, p1, p2))) {
            
        }
        if (p1.score >= 21) {
            return p1;
        } else if (p2.score >= 21) {
            return p2;
        }
        // else go further with scoring

        return p1;
    }
}

class State {
    Die die;
    Pawn p1;
    Pawn p2;

    public State(Die die, Pawn p1, Pawn p2) {
        this.die = die;
        this.p1 = p1;
        this.p2 = p2;
    }
}

class Die {
    int totalRols = 0;
    int current = 1;
    int size = 0;

    public Die(int size) {
        this.size = size;
    }

    public int throwDie() {
        totalRols++;
        if (current > size) {
            current = 1;
        }
        return current++;
    }

    public String toString() {
        return String.format("(%s) C: %s T: %s", size, current, totalRols);
    }
}

class Pawn {
    String name;
    Integer pos = 1;
    Integer startPos = 0;

    Integer score = 0;

    public Pawn(String name, Integer startPos) {
        this.name = name;
        this.startPos = startPos;
        this.pos = startPos;
    }

    public void move(int totalMove) {
        pos += totalMove;
        if (pos > 10) {
            int remaining = pos % 10;
            pos = remaining;
            if (pos == 0) {
                pos = 10;
            }
        }
        score += pos;
        // System.out.println(toString());
    }

    public String toString() {
        return String.format("(%s: %s -> %s score: %s)", name, startPos, pos, score);
    }
}
