package nl.nlxdodge.days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.nlxdodge.util.Day;
import nl.nlxdodge.util.FileReader;
import nl.nlxdodge.util.Pair;

@SuppressWarnings("unused")
public class Day01 implements Day {
  
  @Override
  public String part1() {
    var inputData = getInputData();
    int distanceCount = 0;
    
    for (var index = 0; index < inputData.right.size(); index++) {
      distanceCount += Math.abs(inputData.left.get(index) - inputData.right.get(index));
    }
    
    return "" + distanceCount;
  }
  
  @Override
  public String part2() {
    var inputData = getInputData();
    var similarityCount = 0;
    
    for (var index = 0; index < inputData.right.size(); index++) {
      int lefty = inputData.left.get(index);
      similarityCount += lefty * (int) findDuplicates(inputData.right, lefty);
    }
    
    return "" + similarityCount;
  }
  
  private Pair<List<Integer>, List<Integer>> getInputData() {
    var input = FileReader.readLines("01.txt");
    List<Integer> leftList = new ArrayList<>();
    List<Integer> rightList = new ArrayList<>();
    
    for (String line : input) {
      String[] parts = line.split(" {3}");
      leftList.add(Integer.parseInt(parts[0]));
      rightList.add(Integer.parseInt(parts[1]));
    }
    Collections.sort(leftList);
    Collections.sort(rightList);
    return new Pair<>(leftList, rightList);
  }
  
  private long findDuplicates(List<Integer> input, Integer needle) {
    return input.stream().filter(number -> number.equals(needle)).count();
  }
}
