/**
 * Day04.java
 *
 * Copyright © 2024 ING Group. All rights reserved.
 *
 * This software is the confidential and proprietary information of ING Group ("Confidential Information").
 */

package nl.nlxdodge.days;

import nl.nlxdodge.util.Day;
import nl.nlxdodge.util.FileReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Add class description here
 */
public class Day04 implements Day {
  @Override
  public String part1() {
    var input = getInputData();
    return "" + findXMASesIn2DCollection(input);
  }

  @Override
  public String part2() {
    return "";
  }

  private Integer findXMASesIn2DCollection(List<List<Character>> lines) {
    int counter = 0;
    // right to left
    for (List<Character> row : lines) {
      for (var index = 0; index <= row.size() - 1; index++) {
        if (checkXMASAtPos(row, index)) {
          counter += 1;
        }
      }
    }

    // left to right
    for (List<Character> row : lines) {
      row = row.reversed();
      for (var index = 0; index <= row.size() - 1; index++) {
        if (checkXMASAtPos(row, index)) {
          counter += 1;
        }
      }
    }

    // diagonal left to right
    for (var x = 0; x <= .size() - 1; x++) {
      checkDiagonalXMASAtPos(lines, x, y);
    }

    // diagonal right to left


    // top to bottom
    for (var index = 0; index <= lines.size() - 1; index++) {
      int finalIndex = index;
      var newRow = lines.stream().map(line -> line.get(finalIndex)).toList();
      for (var rowIndex = 0; rowIndex < newRow.size(); rowIndex++) {
        if (checkXMASAtPos(newRow, rowIndex)) {
          counter += 1;
        }
      }
    }

    // bottom to top
    for (var index = 0; index <= lines.size() - 1; index++) {
      int finalIndex = index;
      var newRow = lines.stream().map(line -> line.get(finalIndex)).toList();
      newRow = newRow.reversed();
      for (var rowIndex = 0; rowIndex < newRow.size(); rowIndex++) {
        if (checkXMASAtPos(newRow, rowIndex)) {
          counter += 1;
        }
      }
    }


    return 0;
  }

  private boolean checkXMASAtPos(List<Character> row, Integer index) {
    try {
      if (row.get(index) == 'X' && row.get(index + 1) == 'M' && row.get(index + 2) == 'A' && row.get(index + 3) == 'S') {
        return true;
      }
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
    return false;
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