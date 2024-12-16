package nl.nlxdodge.days;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import nl.nlxdodge.util.Day;
import nl.nlxdodge.util.FileReader;

@SuppressWarnings("unused")
public class Day09 implements Day {

  @Override
  public String part1() {
    var disk = getInputData();
    var newDisk = freeUpSpaceOnDisk(disk, false);
    return "" + calculateCheckSum(newDisk);
  }

  @Override
  public String part2() {
    var disk = getInputData();
    var newDisk = freeUpSpaceOnDisk(disk, true);
    return "" + calculateCheckSum(newDisk);
  }

  private long calculateCheckSum(List<Block> newDisk) {
    long index = 0;
    long counter = 0;

    for (int i = 0; i < newDisk.size(); i++) {
      for (int j = 0; j < newDisk.get(i).value(); j++) {
        var block = newDisk.get(i);
        var blockId = block.id();
        if (block.isEmpty()) {
          break;
        }
        counter += (long) blockId * index;
        index += 1;
      }
    }
    return counter;
  }

  private List<Block> freeUpSpaceOnDisk(List<Block> disk, boolean skipToBig) {
    int emptyIndex = getFirstFreeSpace(disk);
    int fullIndex = getLastFullSpace(disk);
    while (emptyIndex != -1 || fullIndex != -1 || emptyIndex > fullIndex) {
      emptyIndex = getFirstFreeSpace(disk);
      fullIndex = getLastFullSpace(disk);
      Block readingBlock = disk.get(fullIndex);
      Block emptyBlock = disk.get(getFirstFreeSpace(disk));
      printDisk(disk);
      if (!readingBlock.isEmpty()) {
        var delta = readingBlock.value() - emptyBlock.value();
        if (delta == 0) {
          disk.set(emptyIndex, new Block(readingBlock.id(), readingBlock.value(), false));
          disk.set(fullIndex, new Block(-1, -1, true));
        } else if (delta < 0) {
          disk.add(emptyIndex, new Block(readingBlock.id(), readingBlock.value(), false));
          disk.set(emptyIndex + 1, new Block(readingBlock.id(), -delta, true));
          disk.set(fullIndex + 1, new Block(-1, -1, true));
          fullIndex++;
        } else if (!skipToBig) {
          disk.set(emptyIndex, new Block(readingBlock.id(), readingBlock.value() - delta, false));
          disk.set(fullIndex, new Block(readingBlock.id(), delta, false));
          fullIndex++;
        }
      }
    }
    return disk;
  }

  private int getLastFullSpace(List<Block> disk) {
    return IntStream.range(0, disk.size()).filter(d -> !disk.get(d).isEmpty()).findFirst().orElse(-1);
  }

  private int getFirstFreeSpace(List<Block> disk) {
    return IntStream.range(0, disk.size()).filter(d -> disk.get(d).isEmpty()).findFirst().orElse(-1);
  }


  private List<Block> getInputData() {
    var input = FileReader.readLines("09.txt");
    List<Block> diskMap = new ArrayList<>();
    int idCounter = 0;
    boolean write = true;
    for (String line : input.getFirst().split("")) {
      diskMap.add(new Block(idCounter, Integer.parseInt(line), !write));
      if (write) {
        idCounter++;
      }
      write = !write;
    }
    return diskMap;
  }

  private void printDisk(List<Block> blocks) {
    for (Block block : blocks) {
      for (int i = 0; i < block.value(); i++) {
        System.out.print((!block.isEmpty() ? block.id() : "."));
      }
    }
    System.out.println();
  }
}


record Block(int id, int value, boolean isEmpty) {

}