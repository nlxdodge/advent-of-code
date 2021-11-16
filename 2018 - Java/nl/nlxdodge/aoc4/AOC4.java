package nl.nlxdodge.aoc4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class AOC4 {
    private static Guard currentGuard;
    private static List<Guard> guards = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        final String FILE_PATH = "./nl/nlxdodge/aoc4/input.txt";

        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {

            stream.forEach(AOC4::parseLine);

            Optional<Guard> guardSleepMost = guards.stream()
                    .max((x, y) -> Integer.compare(x.getTotalTimeAsleep(), y.getTotalTimeAsleep()));

            if (guardSleepMost.isPresent()) {
                // check all matches that have more then 2 claims starstar star
                Logger.getGlobal().log(Level.INFO, () -> String.format("Result 1: %s", guardSleepMost.get().getId()));
            }
        }
    }

    public static void parseLine(String input) {
        String date = input.split("]")[0];
        String secondPart = input.split("]")[1];

        // check if the string contains a guard part
        if (secondPart.contains("Guard")) {
            int guardId = Integer
                    .parseInt(secondPart.substring(secondPart.indexOf("#") + 1, secondPart.indexOf(" begins shift")));
            checkAndAddGuard(guardId);
        }

        // Create new guardTime object as it sets the initial date and start time
        if (secondPart.contains("falls asleep")) {
            GuardTime guardTime = new GuardTime(date.substring(1));
            currentGuard.addGuardTime(guardTime);
        }

        // find guard who has open guardTime and add closing time to it.
        if (secondPart.contains("wakes up")) {
            GuardTime guardTime = currentGuard.getLastCreatedGuardTime();
            guardTime.parseEndMinute(date.substring(1));
        }
    }

    public static void checkAndAddGuard(int guardId) {
        Optional<Guard> foundGuard = guards.stream().filter(g -> g.getId() == guardId).findFirst();
        if (foundGuard.isEmpty()) {
            Guard newGuard = new Guard(guardId);
            guards.add(newGuard);
            currentGuard = newGuard;
        } else {
            currentGuard = foundGuard.get();
        }

    }
}

class Guard {
    private int id;
    private List<GuardTime> guardTimes = new ArrayList<>();

    public Guard(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void addGuardTime(GuardTime guardTime) {
        guardTimes.add(guardTime);
    }

    public GuardTime getLastCreatedGuardTime() {
        return guardTimes.get(guardTimes.size() - 1);
    }

    public int getTimeAsleepMost() {
        guardTimes.stream().mapToInt(GuardTime::getStartMinute).forEach(startMinute -> {

        });
        return 1;
    }

    public int getTotalTimeAsleep() {
        return guardTimes.stream().mapToInt(GuardTime::asleepTime).sum();
    }

    @Override
    public String toString() {
        return String.format("%nID: %s, GuardTimes %s", id, guardTimes.toString());
    }
}

class GuardTime {
    private DateTimeFormatter guardFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private LocalDateTime date;
    private int startMinute;
    private int endMinute;

    public GuardTime(String date) {
        this.date = LocalDateTime.parse(date, guardFormat);
        this.startMinute = this.date.getMinute();
    }

    public void parseEndMinute(String date) {
        this.endMinute = LocalDateTime.parse(date, guardFormat).getMinute();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int asleepTime() {
        return endMinute - startMinute;
    }

    @Override
    public String toString() {
        return String.format("%n(Date: %s SleepTime: %s)", date, asleepTime());
    }
}
