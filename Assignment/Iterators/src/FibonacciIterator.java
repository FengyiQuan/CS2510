import java.util.Iterator;

class FibonacciIterator implements Iterator<Integer> {
  int prevVal = 0;
  int curVal = 1;

  // There are always more Fibonacci numbers
  public boolean hasNext() {
    return true;
  }

  // Compute the next Fibonacci number
  public Integer next() {
    int answer = this.prevVal + this.curVal;
    this.prevVal = this.curVal;
    this.curVal = answer;
    return answer;
  }

  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}