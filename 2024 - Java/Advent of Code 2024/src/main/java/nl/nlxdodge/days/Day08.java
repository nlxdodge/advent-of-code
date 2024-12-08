/**
 * Day08.java
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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Add class description here
 */
public class Day08 implements Day {
  @Override
  public String part1() {
    var grid = FileReader.get2dList("08.txt");


    return "" + calculateAntiNodes(grid);
  }

  private int calculateAntiNodes(List<List<Character>> grid) {
    var antennas = new ArrayList<Pair<Character, Pair<Integer, Integer>>>();
    for (int i = 0; i < grid.size(); i++) {
      for (int j = 0; j < grid.size(); j++) {
        Pattern anyPattern = Pattern.compile("[A-Za-z0-9]");
        var antennaCharacter = grid.get(i).get(j);
        if (anyPattern.matcher(antennaCharacter.toString()).matches()) {
          antennas.add(new Pair<>(antennaCharacter, new Pair<>(i, j)));
        }
      }
    }

    var groupedAntennas = antennas.stream().collect(Collectors.groupingBy(x -> x.left));

    var counter = 0;

    for (var antennaGroup : groupedAntennas.values()) {
      for (var antenna : antennaGroup) {
        for (var crossCheckAntenna : antennaGroup) {
          var x = antenna.right.right + crossCheckAntenna.right.right;
          var y = antenna.right.left + crossCheckAntenna.right.left;
          // to debug set it on the grid
          if (!(x > grid.size() || x < 0) && !(y > grid.size() || y < 0)) {
              counter++;
          }
        }
      }
    }

    return counter;
  }

  @Override
  public String part2() {
    return "";
  }
}