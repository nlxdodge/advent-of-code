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
    var workingDisk = new ArrayList<>(disk);
    for (int readingIndex = workingDisk.size() - 1; readingIndex >= 0; readingIndex--) {
      int emptyIndex = getFirstFreeSpace(workingDisk);
      if (emptyIndex == -1) {
        break;
      }
      Block readingBlock = workingDisk.get(readingIndex);
      Block emptyBlock = workingDisk.get(getFirstFreeSpace(workingDisk));
      printDisk(workingDisk);
      if (!readingBlock.isEmpty()) {
        
        var delta = readingBlock.value() - emptyBlock.value();
        if (delta == 0) {
          workingDisk.set(emptyIndex, new Block(readingBlock.id(), readingBlock.value(), false));
          workingDisk.set(readingIndex, new Block(-1, -1, true));
        } else if (delta < 0) {
          workingDisk.add(emptyIndex, new Block(readingBlock.id(), readingBlock.value(), false));
          workingDisk.set(emptyIndex + 1, new Block(readingBlock.id(), -delta, true));
          workingDisk.set(readingIndex + 1, new Block(-1, -1, true));
          readingIndex++;
        } else if (!skipToBig) {
          workingDisk.set(emptyIndex, new Block(readingBlock.id(), readingBlock.value() - delta, false));
          workingDisk.set(readingIndex, new Block(readingBlock.id(), delta, false));
          readingIndex++;
        }
      }
    }
    return workingDisk;
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