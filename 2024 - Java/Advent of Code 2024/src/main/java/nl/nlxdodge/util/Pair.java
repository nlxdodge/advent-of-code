package nl.nlxdodge.util;

public class Pair<L, R> {
  
  public L left;
  public R right;
  
  public Pair(L l, R r) {
    left = l;
    right = r;
  }
  
  @Override
  public String toString() {
    return "Pair<%s, %s>".formatted(left, right);
  }
}
