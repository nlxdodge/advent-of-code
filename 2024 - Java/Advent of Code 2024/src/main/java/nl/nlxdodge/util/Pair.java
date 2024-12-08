package nl.nlxdodge.util;

public class Pair<L, R> {
  
  public L left;
  public R right;
  
  public Pair(L l, R r) {
    left = l;
    right = r;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof Pair pair) {
      return pair.left.equals(this.left) && pair.right.equals(this.right);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    return left.hashCode() + right.hashCode();
  }
  
  @Override
  public String toString() {
    return "Pair<%s, %s>".formatted(left, right);
  }
}
