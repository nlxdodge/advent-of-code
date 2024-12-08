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

@SuppressWarnings("unused")
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
  
  private int calculateAntiNodes(List<List<Character>> grid, boolean rayCast) {
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
          
          boolean inBounds = true;
          int initialX = antenna.right.right;
          int initialY = antenna.right.left;
          if(rayCast) {
            foundAntiNodes.add(new Pair<>(initialY, initialX));
          }
          while (inBounds) {
            initialX = initialX + diffX;
            initialY = initialY + diffY;
            if (initialX < grid.size() && initialX >= 0 && initialY < grid.size() && initialY >= 0) {
              foundAntiNodes.add(new Pair<>(initialY, initialX));
            } else {
              inBounds = false;
            }
            if(!rayCast) {
              inBounds = false;
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