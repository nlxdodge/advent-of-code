package nl.nlxdodge;

import java.util.stream.IntStream;
import nl.nlxdodge.util.DayCaller;

public class DayRunner {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            IntStream.rangeClosed(1, 30).forEach(day -> {
                printSingleDay("%02d".formatted(day));
                System.out.println("⭐🌟✨🦌🦌🦌🛷🎅🏻🎁🎄");
            });
            return;
        }
        String dayNumber = args[0];
        printSingleDay(dayNumber);
    }
    
    private static void printSingleDay(String dayNumber) {
        System.out.printf("Day %s part 1 is: %s \n", dayNumber, DayCaller.call(dayNumber).part1());
        System.out.printf("Day %s part 2 is: %s \n", dayNumber, DayCaller.call(dayNumber).part2());
    }
}
