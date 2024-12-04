package nl.nlxdodge.days;

import java.util.ArrayList;
import java.util.List;
import nl.nlxdodge.util.Day;
import nl.nlxdodge.util.FileReader;

public class Day04 implements Day {
  
  @Override
  public String part1() {
    var input = getInputData();
    return "" + findXMASesIn2DCollection(input, false);
  }
  
  @Override
  public String part2() {
    var input = getInputData();
    return "" + findXMASesIn2DCollection(input, true);
  }
  
  private Integer findXMASesIn2DCollection(List<List<Character>> grid, boolean realX) {
    int counter = 0;
    for (var x = 0; x < grid.size(); x++) {
      for (var y = 0; y < grid.getFirst().size(); y++) {
        counter += realX ? checkXDashMasAtPos(grid, x, y) : checkXMASAtPos(grid, x, y);
      }
    }
    return counter;
  }
  
  private int checkXDashMasAtPos(List<List<Character>> grid, int x, int y) {
    if (grid.get(x).get(y).equals('A')) {
      try {
        if ((
              grid.get(x - 1).get(y - 1).equals('S') && grid.get(x + 1).get(y + 1).equals('M') ||
              grid.get(x - 1).get(y - 1).equals('M') && grid.get(x + 1).get(y + 1).equals('S')
            ) && (
              grid.get(x - 1).get(y + 1).equals('S') && grid.get(x + 1).get(y - 1).equals('M') ||
              grid.get(x - 1).get(y + 1).equals('M') && grid.get(x + 1).get(y - 1).equals('S')
            )) {
          return 1;
        }
      } catch (IndexOutOfBoundsException _) {
      
      }
    }
    return 0;
  }
  
  private int checkXMASAtPos(List<List<Character>> grid, int x, int y) {
    int counter = 0;
    counter += checkDirection(grid, x, y, 0, 1);
    counter += checkDirection(grid, x, y, 0, -1);
    counter += checkDirection(grid, x, y, 1, 0);
    counter += checkDirection(grid, x, y, -1, 0);
    counter += checkDirection(grid, x, y, 1, 1);
    counter += checkDirection(grid, x, y, 1, -1);
    counter += checkDirection(grid, x, y, -1, 1);
    counter += checkDirection(grid, x, y, -1, -1);
    return counter;
  }
  
  private int checkDirection(List<List<Character>> grid, int x, int y, int deltaX, int deltaY) {
    StringBuilder word = new StringBuilder();
    try {
      for (var i = 0; i < 4; i++) {
        int newX = x + i * deltaX;
        int newY = y + i * deltaY;
        word.append(grid.get(newX).get(newY));
      }
    } catch (IndexOutOfBoundsException _) {
    
    }
    return word.toString().equals("XMAS") ? 1 : 0;
  }
  
  private List<List<Character>> getInputData() {
    var input = FileReader.readLines("04.txt");
    List<List<Character>> lines = new ArrayList<>();
    for (String line : input) {
      List<Character> row = new ArrayList<>();
      for (char c : line.toCharArray()) {
        row.add(c);
      }
      lines.add(row);
    }
    return lines;
  }
}