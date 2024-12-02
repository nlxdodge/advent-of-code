package nl.nlxdodge.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.nlxdodge.util.Day;
import nl.nlxdodge.util.FileReader;

@SuppressWarnings("unused")
public class Day02 implements Day {
  
  @Override
  public String part1() {
    var reports = getInputData();
    
    return "" + reports.stream().filter(this::isReportSafe).count();
  }
  
  @Override
  public String part2() {
    var reports = getInputData();
    
    return "" + reports.stream().filter(list -> isReportSafe(list) || isSafeWithDampener(list)).count();
  }
  
  private boolean isSafeWithDampener(List<Integer> report) {
    for (var index = 0; index < report.size(); index++) {
      var copiedReport = new ArrayList<>(report);
      copiedReport.remove(index);
      if (isReportSafe(copiedReport)) {
        return true;
      }
    }
    return false;
  }
  
  private boolean isReportSafe(List<Integer> report) {
    if (!report.stream().sorted().toList().equals(report) &&
        !report.stream().sorted(Collections.reverseOrder()).toList().equals(report)) {
      return false;
    }
    
    Collections.sort(report);
    
    var last = 0;
    for (Integer detected : report) {
      if (last == detected) {
        return false;
      }
      if (last == 0 || detected <= last + 3) {
        last = detected;
      } else {
        return false;
      }
    }
    return true;
  }
  
  private List<List<Integer>> getInputData() {
    var input = FileReader.readLines("02.txt");
    List<List<Integer>> reports = new ArrayList<>();
    
    for (String line : input) {
      reports.add(new ArrayList<>(Arrays.stream(line.split(" ")).map(Integer::parseInt).toList()));
    }
    
    return reports;
  }
}
