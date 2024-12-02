package nl.nlxdodge.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.nlxdodge.util.Day;
import nl.nlxdodge.util.FileReader;

public class Day02 implements Day {

  public String part1() {
    var reports = getInputData();

    return "" + reports.stream().filter(this::isReportSafe).count();
  }

  public String part2() {
    return "";
  }

  private boolean isReportSafe(List<Integer> report) {
    if (!report.stream().sorted().toList().equals(report) && !report.stream().sorted(Collections.reverseOrder()).toList().equals(report)) {
      System.out.println("Bad report because not sorted" + report);
      return false;
    }

    var last = 0;
    for(Integer detected : report) {
      if(last == detected) {
        return false;
      }
      if(last == 0 || detected >= last + 3 || detected <= last - 3) {
        last = detected;
      } else {
        System.out.println("Bad report above or below +3: " + report);
        return false;
      }
    }
    System.out.println("Good report: " + report);
    return true;
  }

  private List<List<Integer>> getInputData() {
    var input = FileReader.readLines("02.txt");
    List<List<Integer>> reports = new ArrayList<>();

    for (String line : input) {
      reports.add(new ArrayList<>(Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).boxed().toList()));
    }

    return reports;
  }
}
