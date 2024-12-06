package nl.nlxdodge.days;

import java.util.ArrayList;
import java.util.List;

import nl.nlxdodge.util.Day;
import nl.nlxdodge.util.FileReader;
import nl.nlxdodge.util.Pair;

public class Day05 implements Day {

  @Override
  public String part1() {
    var input = getInputData();
    int counter = 0;

    for (List<Integer> instructionSet : input.right) {
      if (isInstructionSetPossible(instructionSet, input.left)) {
        counter += instructionSet.get(instructionSet.size() / 2);
      }
    }

    return "" + counter;
  }

  @Override
  public String part2() {
    var input = getInputData();
    int counter = 0;

    for (List<Integer> instructionSet : input.right) {
      if (!isInstructionSetPossible(instructionSet, input.left)) {
        List<Integer> fixedInstructionSet = fixInstructionSet(instructionSet, input.left);
        counter += fixedInstructionSet.get(fixedInstructionSet.size() / 2);
      }
    }

    return "" + counter;
  }

  private List<Integer> fixInstructionSet(List<Integer> instructionSet, List<Pair<Integer, Integer>> rules) {
    while (!isInstructionSetPossible(instructionSet, rules)) {
      for (var index = 0; index < instructionSet.size(); index++) {
        Integer number = instructionSet.get(index);
        List<Pair<Integer, Integer>> applicableRules = rules.stream().filter(x -> x.left.equals(number)).toList();
        for (Pair<Integer, Integer> applicableRule : applicableRules) {
          Integer right = applicableRule.right;
          if (instructionSet.contains(right) && instructionSet.indexOf(right) < instructionSet.indexOf(number)) {
            instructionSet.remove(right);
            instructionSet.add(instructionSet.indexOf(number) + 1, right);
          }
        }
      }
    }
    return instructionSet;
  }

  private boolean isInstructionSetPossible(List<Integer> instructionSet, List<Pair<Integer, Integer>> rules) {
    for (int number : instructionSet) {
      List<Pair<Integer, Integer>> applicableRules = rules.stream().filter(x -> x.left.equals(number)).toList();
      for (Pair<Integer, Integer> applicableRule : applicableRules) {
        int right = applicableRule.right;
        if (instructionSet.contains(right) && instructionSet.indexOf(right) < instructionSet.indexOf(number)) {
          return false;
        }
      }
    }
    return true;
  }

  private Pair<List<Pair<Integer, Integer>>, List<List<Integer>>> getInputData() {
    var input = FileReader.readLines("05.txt");
    List<Pair<Integer, Integer>> rules = new ArrayList<>();
    List<List<Integer>> instructions = new ArrayList<>();

    for (String line : input) {
      if (line.contains("|")) {
        rules.add(new Pair<>(Integer.parseInt(line.split("\\|")[0]), Integer.parseInt(line.split("\\|")[1])));
      } else if (line.contains(",")) {
        List<Integer> numbers = new ArrayList<>();
        for (String number : line.split(",")) {
          numbers.add(Integer.parseInt(number));
        }
        instructions.add(numbers);
      }
    }
    return new Pair<>(rules, instructions);
  }
}
