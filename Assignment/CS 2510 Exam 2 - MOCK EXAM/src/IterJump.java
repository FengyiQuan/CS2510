import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import tester.Tester;

class IterJump<T> implements Iterator<T>, Iterable<T> {
  Iterator<T> iterA;
  Iterator<T> iterB;
  boolean currentIsIterA;

  IterJump(Iterator<T> iterA, Iterator<T> iterB) {
    this.iterA = iterA;
    this.iterB = iterB;
    this.currentIsIterA = true;
  }

  public boolean hasNext() {
    return (this.currentIsIterA && this.iterA.hasNext())
        || (!this.currentIsIterA && this.iterB.hasNext());
  }

  public T next() {
    if (this.currentIsIterA) {
      this.currentIsIterA = false;
      this.iterB.next();
      return this.iterA.next();
    }
    else {
      this.currentIsIterA = true;
      this.iterA.next();
      return this.iterB.next();
    }
  }

  public Iterator<T> iterator() {
    return this;
  }
}

class test {
  void testInterleave(Tester t) {
    ArrayList<Integer> expectedResult = new ArrayList<Integer>(
        Arrays.asList(0, 11, 2, 13, 4, 15, 6, 17, 8, 19, 10));
    ArrayList<Integer> result = new ArrayList<Integer>();
    for (Integer i : new IterJump<Integer>(new FromZeroToTen(), new FromTenToTwenty())) {
      result.add(i);
    }
    t.checkExpect(result, expectedResult);
  }
}