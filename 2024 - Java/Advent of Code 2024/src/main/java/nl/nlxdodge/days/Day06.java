package nl.nlxdodge.days;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import nl.nlxdodge.util.Day;
import nl.nlxdodge.util.FileReader;
import nl.nlxdodge.util.Pair;
import nl.nlxdodge.util.Timer;

@SuppressWarnings("unused")
public class Day06 implements Day {

  @Override
  public String part1() {
    var grid = getInputData();

    iterateGuardUntilGone(grid);

    return "" + grid.stream().flatMap(Collection::stream).filter(character -> character.equals('X')).count();
  }

  @Override
  public String part2() {
    var grid = getInputData();
    var checkGrid = iterateGuardUntilGone(new ArrayList<>(grid));
    var counter = new AtomicInteger();

    IntStream.range(0, grid.size()).parallel().forEach(x -> IntStream.range(0, grid.size()).parallel().forEach(y -> {
      var gridCopy = getInputData();
      if (gridCopy.get(x).get(y).equals('.') && checkGrid.get(x).get(y).equals('X')) {
        gridCopy.get(x).set(y, '@');
        if (guardStuckInLoop(gridCopy)) {
          counter.set(counter.get() + 1);
        }
      }
    }));

    return "" + counter;
  }

  private boolean guardStuckInLoop(List<List<Character>> grid) {
    var guardPoss = findGuardPos(grid);
    var guardDelta = new Pair<>(-1, 0);
    var lastCorners = new ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>>();
    var onGrid = true;

    while (onGrid) {
      onGrid = doGuardStep(grid, guardPoss, guardDelta, lastCorners);
      if (lastCorners.contains(new Pair<>(guardDelta, guardPoss))) {
        return true;
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
      List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> lastCorners) {
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
          lastCorners.add(new Pair<>(new Pair<>(guardDelta.left, guardDelta.right), new Pair<>(guardPoss.left, guardPoss.right)));
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