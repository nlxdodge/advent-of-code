/**
 * Day08.java
 * <p>
 * Copyright © 2024 ING Group. All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of ING Group ("Confidential Information").
 */

package nl.nlxdodge.days;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import nl.nlxdodge.util.Day;
import nl.nlxdodge.util.FileReader;
import nl.nlxdodge.util.GridHelper;
import nl.nlxdodge.util.Pair;

/**
 * Add class description here
 */
public class Day08 implements Day {
  
  @Override
  public String part1() {
    var grid = FileReader.get2dList("08.txt");
    return "" + calculateAntiNodes(grid, false);
  }
  
  @Override
  public String part2() {
    var grid = FileReader.get2dList("08.txt");
    return "" + calculateAntiNodes(grid, true);
  }
  
  private int calculateAntiNodes(List<List<Character>> grid, boolean raycast) {
    var antennas = findAntennas(grid);
    var groupedAntennas = antennas.stream().collect(Collectors.groupingBy(x -> x.left));
    
    var foundAntiNodes = new HashSet<Pair<Integer, Integer>>();
    for (var antennaGroup : groupedAntennas.values()) {
      for (var antenna : antennaGroup) {
        for (var crossCheckAntenna : antennaGroup) {
          if (antenna.equals(crossCheckAntenna)) {
            continue;
          }
          
          var diffX = antenna.right.right - crossCheckAntenna.right.right;
          var diffY = antenna.right.left - crossCheckAntenna.right.left;
          
          boolean outOfBounds = false;
          if (raycast) {
            var initialX = antenna.right.right;
            var initialY = antenna.right.left;
            foundAntiNodes.add(new Pair<>(initialY, initialX));
            while (!outOfBounds) {
              initialX = initialX + diffX;
              initialY = initialY + diffY;
              if (!(initialX >= grid.size() || initialX < 0) && !(initialY >= grid.size() || initialY < 0)) {
                foundAntiNodes.add(new Pair<>(initialY, initialX));
              } else {
                outOfBounds = true;
              }
            }
          } else {
            var x = diffX + antenna.right.right;
            var y = diffY + antenna.right.left;
            if (!(x >= grid.size() || x < 0) && !(y >= grid.size() || y < 0)) {
              foundAntiNodes.add(new Pair<>(y, x));
            }
          }
        }
      }
    }
    
    return foundAntiNodes.size();
  }
  
  private ArrayList<Pair<Character, Pair<Integer, Integer>>> findAntennas(List<List<Character>> grid) {
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
    return antennas;
  }
}