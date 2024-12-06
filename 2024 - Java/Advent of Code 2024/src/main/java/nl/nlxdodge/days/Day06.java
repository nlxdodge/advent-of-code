/**
 * Day06.java
 *
 * Copyright © 2024 ING Group. All rights reserved.
 *
 * This software is the confidential and proprietary information of ING Group ("Confidential Information").
 */

package nl.nlxdodge.days;

import nl.nlxdodge.util.Day;
import nl.nlxdodge.util.FileReader;
import nl.nlxdodge.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Add class description here
 */
public class Day06 implements Day {
  @Override
  public String part1() {
    var grid = getInputData();

    grid = itterateGuardUntilGone(grid);
    
    return "";
  }

  private List<List<Character>> itterateGuardUntilGone(List<List<Character>> grid) {
    var guardPoss = findGuardPos(grid);
    var guardDeleta = new Pair<>(-1, 0);
    var onGrid = true;

    while(onGrid) {
      onGrid = doGuardStep(grid, guardPoss, guardDeleta);
    }

    return grid;
  }

  private boolean doGuardStep(List<List<Character>> grid, Pair<Integer, Integer> guardPoss, Pair<Integer, Integer> guardDelta) {
    Character gridCharacter = grid.get(guardPoss.left * guardDelta.left).get(guardPoss.right * guardDelta.right);
    if(gridCharacter == '#') {
      // calculate guard delta to move to next 90 degrees
    }
    if(gridCharacter == '.') {
      grid.get(guardPoss.left).set(guardPoss.right, 'X');
      guardPoss.left += guardDelta.left;
      guardPoss.right += guardDelta.right;
      return grid.size() >= guardPoss.left && grid.size() >= guardPoss.right;
    }
    return true;
  }

  private Pair<Integer, Integer> findGuardPos(List<List<Character>> grid, ) {
    for(var i = 0; i < grid.size(); i++) {
      for (var j = 0; j < grid.size(); j++) {
        if(grid.get(i).get(j) == '^') {
          return new Pair<>(i, j);
        }
      }
    }
    return null;
  }

  @Override
  public String part2() {
    return "";
  }

  private List<List<Character>> getInputData() {
    var input = FileReader.readLines("06.txt");
    List<List<Character>> out = new ArrayList<>();
    for(var i = 0; i < input.size(); i++) {
      List<Character> row = new ArrayList<>();
      for (var j = 0; j < input.size(); j++) {
        row.add(input.get(i).charAt(j));
      }
      out.add(row);
    }
    return out;
  }
}