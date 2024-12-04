package nl.nlxdodge.days;

import nl.nlxdodge.util.Day;
import nl.nlxdodge.util.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 implements Day {
  @Override
  public String part1() {
    var input = FileReader.readLines("03.txt").stream().reduce("", (x, y) -> x + y);

    Pattern multiplier = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");

    Matcher matcher = multiplier.matcher(input);

    List<String> multipliers = matcher.results().map(MatchResult::group).toList();

    long outcome = multipliers.stream().mapToInt(str -> Integer.parseInt(str.substring(4).split(",")[0]) * Integer.parseInt(str.substring(4, str.length() - 1).split(",")[1])).sum();

    return "" + outcome;
  }

  @Override
  public String part2() {
    var input = FileReader.readLines("03.txt").stream().reduce("", (x, y) -> x + y);

    Pattern multiplier = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)");
    Matcher matcher = multiplier.matcher(input);

    List<String> captureGroups = matcher.results().map(MatchResult::group).toList();
    List<String> multipliers = new ArrayList<>(captureGroups);

    for(int index = 0; index < multipliers.size(); index++) {
      if(multipliers.get(index).equals("don't()")) {
        multipliers.remove(index);
        for(int subIndex = 0; subIndex < multipliers.size(); subIndex++) {
          if(multipliers.get(index).equals("do()")) {
            multipliers.remove(index);
            break;
          } else {
            multipliers.remove(index);
          }
        }
      }
      if(multipliers.get(index).equals("do()")) {
        multipliers.remove(index);
      }
    }

    long outcome = multipliers.stream().mapToInt(str -> Integer.parseInt(str.substring(4).split(",")[0]) * Integer.parseInt(str.substring(4, str.length() - 1).split(",")[1])).sum();

    return "" + outcome;
  }
}