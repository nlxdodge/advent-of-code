package nl.nlxdodge.util;

import java.time.Instant;

public class Timer {
  private static Instant startTime;

  public static void startTimer() {
    startTime = Instant.now();
  }

  public static void stopTimer() {
    System.out.printf("Ran for %s seconds\n", startTime.until(Instant.now()).getSeconds());
  }
}