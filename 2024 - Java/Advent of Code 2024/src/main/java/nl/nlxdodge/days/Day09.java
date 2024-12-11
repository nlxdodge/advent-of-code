package nl.nlxdodge.days;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nl.nlxdodge.util.Day;
import nl.nlxdodge.util.FileReader;

@SuppressWarnings("unused")
public class Day09 implements Day {
  
  @Override
  public String part1() {
    var disk = getInputData();
    var newDisk = freeUpSpaceOnDisk(disk);
    printDisk(newDisk);
    return "" + calculateCheckSum(newDisk);
  }
  
  private long calculateCheckSum(List<Block> newDisk) {
    long index = 0;
    long counter = 0;
    for (int i = 0; i < newDisk.size(); i++) {
      for (int j = 0; j < newDisk.get(i).value(); j++) {
        var block = newDisk.get(i);
        counter += (long) newDisk.get(i).id() * index;
        index += 1;
      }
    }
    return counter;
  }
  
  private List<Block> freeUpSpaceOnDisk(List<Block> disk) {
    var formattedBlocks = new ArrayList<>(disk);
    for (int formattedIndex = 0; formattedIndex < formattedBlocks.size(); formattedIndex++) {
      if (formattedBlocks.get(formattedIndex).isEmpty()) {
        var toFill = formattedBlocks.get(formattedIndex).value();
        Block foundBlock;
        
        while (toFill != 0) {
          for (int readingIndex = formattedBlocks.size() - 1; readingIndex >= 0; readingIndex--) {
            foundBlock = formattedBlocks.get(readingIndex);
            if(readingIndex < formattedIndex) {
              toFill = 0;
              break;
            }
            
            if (!foundBlock.isEmpty()) {
              if (foundBlock.value() <= toFill) {
                formattedBlocks.set(readingIndex, new Block(foundBlock.id(), 0, false));
                if (formattedBlocks.get(formattedIndex).isEmpty()) {
                  formattedBlocks.set(formattedIndex, new Block(foundBlock.id(), foundBlock.value(), false));
                } else {
                  formattedBlocks.add(formattedIndex + 1, new Block(foundBlock.id(), foundBlock.value(), false));
                }
                toFill -= foundBlock.value();
              } else {
                formattedBlocks.set(readingIndex,
                  new Block(foundBlock.id(), formattedBlocks.get(readingIndex).value() - toFill, false));
                if (formattedBlocks.get(formattedIndex).isEmpty()) {
                  formattedBlocks.set(formattedIndex,
                    new Block(foundBlock.id(), toFill, false));
                } else {
                  formattedBlocks.add(formattedIndex + 1,
                    new Block(foundBlock.id(), toFill, false));
                }
                toFill = 0;
                break;
              }
            }
          }
        }
      }
    }
    return formattedBlocks;
  }
  
  @Override
  public String part2() {
    return "";
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
//    printDisk(diskMap);
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