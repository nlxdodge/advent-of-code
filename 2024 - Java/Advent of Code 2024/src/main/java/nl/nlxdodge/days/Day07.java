package nl.nlxdodge.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.nlxdodge.util.Day;
import nl.nlxdodge.util.FileReader;
import nl.nlxdodge.util.Pair;

@SuppressWarnings("unused")
public class Day07 implements Day {
  
  @Override
  public String part1() {
    var calibrations = getInputData();
    return "" + findCorrectEvaluations(calibrations, List.of('+', '*'));
  }
  
  @Override
  public String part2() {
    var calibrations = getInputData();
    return "" + findCorrectEvaluations(calibrations, List.of('+', '*', '|'));
  }
  
  private long findCorrectEvaluations(List<Pair<Long, List<Long>>> calibrations, List<Character> operations) {
    var totalCalibrationResult = 0L;
    for (var calibration : calibrations) {
      var calibrationTotal = calibration.left;
      var size = calibration.right.size() - 1;
      var possibilities = new ArrayList<List<Character>>();
      var currentCharacters = new ArrayList<Character>();
      
      generateAllPossibilities(possibilities, currentCharacters, size, operations);
      
      for (var currentPossibility : possibilities) {
        var currentTotal = calibration.right.getFirst();
        for (var index = 0; index < size; index++) {
          currentTotal = doCalculation(currentTotal, currentPossibility.get(index), calibration.right.get(index + 1));
        }
        if (currentTotal.equals(calibrationTotal)) {
          totalCalibrationResult += calibrationTotal;
          break;
        }
      }
    }
    return totalCalibrationResult;
  }
  
  private Long doCalculation(Long left, Character operator, Long right) {
    return switch (operator) {
      case '+' -> left + right;
      case '*' -> left * right;
      case '|' -> Long.parseLong(String.format("%s%s", left, right));
      default -> throw new IllegalStateException("Unexpected value: " + operator);
    };
  }
  
  private void generateAllPossibilities(List<List<Character>> possibilities, List<Character> current, int size, List<Character> operators) {
    if (current.size() >= size) {
      possibilities.add(current);
      return;
    }
    operators.forEach(chr -> {
      List<Character> newList = new ArrayList<>(List.of(chr));
      newList.addAll(current);
      generateAllPossibilities(possibilities, newList, size, operators);
    });
  }
  
  private List<Pair<Long, List<Long>>> getInputData() {
    var input = FileReader.readLines("07.txt");
    List<Pair<Long, List<Long>>> calibrations = new ArrayList<>();
    
    for (String line : input) {
      var outcome = Long.parseLong(line.split(":")[0]);
      var numbers = Arrays.stream(line.split(":")[1].strip().split(" ")).map(Long::valueOf).toList();
      calibrations.add(new Pair<>(outcome, numbers));
    }
    
    return calibrations;
  }
}
