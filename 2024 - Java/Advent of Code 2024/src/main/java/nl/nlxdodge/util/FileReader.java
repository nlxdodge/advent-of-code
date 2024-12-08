package nl.nlxdodge.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
  
  public static List<String> readLines(String resourceName) {
    List<String> lines = new ArrayList<>();
    
    try (InputStream stream = FileReader.class.getClassLoader().getResourceAsStream(resourceName)) {
      if (stream == null) {
        System.out.printf("File not found: %s", resourceName);
        return lines;
      }
      BufferedReader bf = new BufferedReader(new InputStreamReader(stream));
      String line;
      while ((line = bf.readLine()) != null) {
        lines.add(line);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return lines;
  }

  public static List<List<Character>> get2dList(String day) {
    var input = FileReader.readLines(day);
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
