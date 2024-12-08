package nl.nlxdodge.days;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import nl.nlxdodge.util.Day;
import nl.nlxdodge.util.FileReader;
import nl.nlxdodge.util.Pair;

@SuppressWarnings("unused")
public class Day06 implements Day {
  
  @Override
  public String part1() {
    var grid = getInputData();
    
    grid = iterateGuardUntilGone(grid);
    
    return "" + grid.stream().flatMap(Collection::stream).filter(character -> character.equals('X')).count();
  }
  
  @Override
  public String part2() {
    var grid = getInputData();
    var counter = 0;
    
    for (var x = 0; x < grid.size(); x++) {
      for (var y = 0; y < grid.size(); y++) {
        var gridCopy = getInputData();
        if (gridCopy.get(x).get(y).equals('.')) {
          gridCopy.get(x).set(y, '@');
          if (guardStuckInLoop(gridCopy)) {
            counter++;
          }
        }
      }
    }
    
    return "" + counter;
  }
  
  private boolean guardStuckInLoop(List<List<Character>> grid) {
    var guardPoss = findGuardPos(grid);
    var guardDelta = new Pair<>(-1, 0);
    var onGrid = true;
    var lastCorners = new ArrayList<Pair<Integer, Integer>>();
    
    // todo check and re-write to my own
    while (onGrid) {
      onGrid = doGuardStep(grid, guardPoss, guardDelta, lastCorners);
      if (lastCorners.size() >= 170) {
        if (lastCorners.contains(guardPoss)) {
          for (int patternSize = 4; patternSize <= lastCorners.size() / 2; patternSize++) {
            for (int start = 0; start <= lastCorners.size() - patternSize; start++) {
              ArrayList<Pair<Integer, Integer>> pattern = new ArrayList<>(lastCorners.subList(start, start + patternSize));
              int foundPattenrn = 0;
              for (int i = start + patternSize; i < lastCorners.size(); i += patternSize) {
                ArrayList<Pair<Integer, Integer>> subList = new ArrayList<>(lastCorners.subList(i, Math.min(i + patternSize, lastCorners.size())));
                if (pattern.equals(subList)) {
                  foundPattenrn++;
                } else {
                  break;
                }
              }
              if (foundPattenrn >= 2 && (lastCorners.size() - start) % patternSize == 0) {
                return true;
              }
            }
          }
        }
      }
    }
    
    return false;
  }
  
  private List<List<Character>> iterateGuardUntilGone(List<List<Character>> grid) {
    var guardPoss = findGuardPos(grid);
    var guardDelta = new Pair<>(-1, 0);
    var onGrid = true;
    
    while (onGrid) {
      onGrid = doGuardStep(grid, guardPoss, guardDelta, null);
    }
    
    return grid;
  }
  
  private boolean doGuardStep(List<List<Character>> grid, Pair<Integer, Integer> guardPoss, Pair<Integer, Integer> guardDelta,
    List<Pair<Integer, Integer>> lastCorners) {
    var nextTurn = new HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>();
    nextTurn.put(new Pair<>(-1, 0), new Pair<>(0, 1));
    nextTurn.put(new Pair<>(0, 1), new Pair<>(1, 0));
    nextTurn.put(new Pair<>(1, 0), new Pair<>(0, -1));
    nextTurn.put(new Pair<>(0, -1), new Pair<>(-1, 0));
    
    try {
      grid.get(guardPoss.left).set(guardPoss.right, 'X');
      Character gridCharacter = grid.get(guardPoss.left + guardDelta.left).get(guardPoss.right + guardDelta.right);
      if (gridCharacter.equals('#') || gridCharacter.equals('@')) {
        if (lastCorners != null) {
          lastCorners.add(new Pair<>(guardPoss.left, guardPoss.right));
        }
        var newDelta = nextTurn.get(guardDelta);
        guardDelta.left = newDelta.left;
        guardDelta.right = newDelta.right;
      }
      if (gridCharacter.equals('.') || gridCharacter.equals('X')) {
        guardPoss.left += guardDelta.left;
        guardPoss.right += guardDelta.right;
      }
      return true;
    } catch (IndexOutOfBoundsException _) {
      return false;
    }
  }
  
  private Pair<Integer, Integer> findGuardPos(List<List<Character>> grid) {
    for (var i = 0; i < grid.size(); i++) {
      for (var j = 0; j < grid.size(); j++) {
        if (grid.get(i).get(j) == '^') {
          return new Pair<>(i, j);
        }
      }
    }
    throw new RuntimeException("Guard not found");
  }
  
  private void p(List<List<Character>> g) {
    for (int i = 0; i < g.size(); i++) {
      for (int j = 0; j < g.size(); j++) {
        System.out.print(g.get(i).get(j));
      }
      System.out.println("");
    }
    System.out.println("");
  }
  
  
  private List<List<Character>> getInputData() {
    var input = FileReader.readLines("06.txt");
    List<List<Character>> out = new ArrayList<>();
    for (var i = 0; i < input.size(); i++) {
      List<Character> row = new ArrayList<>();
      for (var j = 0; j < input.size(); j++) {
        row.add(input.get(i).charAt(j));
      }
      out.add(row);
    }
    return out;
  }
}