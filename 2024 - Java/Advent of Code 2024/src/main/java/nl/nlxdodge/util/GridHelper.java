package nl.nlxdodge.util;

import java.util.List;

public class GridHelper {
  public static <T> void printGrid(List<List<T>> grid) {
    for (int i = 0; i < grid.size(); i++) {
      for (int j = 0; j < grid.getFirst().size(); j++) {
        System.out.print(grid.get(i).get(j));
      }
      System.out.println();
    }
  }
}
