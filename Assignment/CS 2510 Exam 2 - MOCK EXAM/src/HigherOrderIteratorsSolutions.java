import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import tester.Tester;

// -- Tester iterators --
// --   Ignore this    --
class FromZeroToTen implements Iterator<Integer> {
  int curVal;

  FromZeroToTen() {
    this.curVal = 0;
  }

  public boolean hasNext() {
    return curVal <= 10;
  }

  public Integer next() {
    int oldValue = this.curVal;
    curVal++;
    return oldValue;
  }
}

class FromTenToTwenty implements Iterator<Integer> {
  int curVal;

  FromTenToTwenty() {
    this.curVal = 10;
  }

  public boolean hasNext() {
    return curVal <= 20;
  }

  public Integer next() {
    int oldValue = this.curVal;
    curVal++;
    return oldValue;
  }
}

class FromTenToFifteen implements Iterator<Integer> {
  int curVal;

  FromTenToFifteen() {
    this.curVal = 10;
  }

  public boolean hasNext() {
    return curVal <= 15;
  }

  public Integer next() {
    int oldValue = this.curVal;
    curVal++;
    return oldValue;
  }
}

class FromNegTenToFive implements Iterator<Integer> {
  int curVal;

  FromNegTenToFive() {
    this.curVal = -10;
  }

  public boolean hasNext() {
    return curVal <= 5;
  }

  public Integer next() {
    int oldValue = this.curVal;
    curVal++;
    return oldValue;
  }
}

class FromOneToThree implements Iterator<Integer> {
  int curVal;

  FromOneToThree() {
    this.curVal = 1;
  }

  public boolean hasNext() {
    return curVal <= 3;
  }

  public Integer next() {
    int oldValue = this.curVal;
    curVal++;
    return oldValue;
  }
}

// 0. Design an iterator that takes in an iterator of finite length, and reverses it.
// Hint: For ArrayLists, al.add(0, item) adds the item to the front of the list,
// while al.add(item) adds it to the back.
class ReverseIterator<T> implements Iterator<T>, Iterable<T> {
  ArrayList<T> contents;
  Iterator<T> iterToReverse;
  int idx;
  
  ReverseIterator(Iterator<T> iterToReverse) {
    this.iterToReverse = iterToReverse;
    this.contents = this.emptyContents();
    this.idx = 0;
  }
  
  ArrayList<T> emptyContents() {
    ArrayList<T> tmp = new ArrayList<T>();
    while(this.iterToReverse.hasNext()) {
      tmp.add(0, this.iterToReverse.next());
    }
    return tmp;
  }
  
  public boolean hasNext() {
    return this.idx < this.contents.size();
  }
  
  public T next() {
    T tmp = contents.get(this.idx);
    this.idx++;
    return tmp;
  }
  
  public Iterator<T> iterator() {
    return this;
  } 
}

// 1. Design an iterator that takes in two iterators OF THE SAME LENGTH,
// and alternates between the first, and then the second iterator
// of them until both iterators are exhausted.
// -- BOTH ITERATORS HAVE THE SAME LENGTH. --
class Interleave<T> implements Iterator<T>, Iterable<T> {
  Iterator<T> iterA;
  Iterator<T> iterB;
  boolean currentIsIterA;


  Interleave(Iterator<T> iterA, Iterator<T> iterB) {
    this.iterA = iterA;
    this.iterB = iterB;
    this.currentIsIterA = true;
  }

  public boolean hasNext() {
    return this.iterB.hasNext();
  }

  public T next() {
    if(currentIsIterA) {
      this.currentIsIterA = false;
      return this.iterA.next();
    } else {
      this.currentIsIterA = true;
      return this.iterB.next();
    }
  }

  public Iterator<T> iterator() {
    return this;
  }
}

// 2. Design an iterator that takes in two iterators,
// and alternates between the first, and then the second iterator
// of them until both iterators are exhausted.
// -- In this problem, DON'T assume that both iterators are of the
// same length, so for example if the first iterator runs out,
// iterate through the rest of the second iterator and then terminate.
class InterleaveUneven2Iters<T> implements Iterator<T>, Iterable<T> {
  Iterator<T> iterA;
  Iterator<T> iterB;
  boolean iterAisMt;
  boolean iterBisMt;
  boolean currentIsIterA;


  InterleaveUneven2Iters(Iterator<T> iterA, Iterator<T> iterB) {
    this.iterA = iterA;
    this.iterB = iterB;
    this.currentIsIterA = true;
    this.iterAisMt = false;
    this.iterBisMt = false;
  }
  
  public boolean hasNext() {
    this.iterAisMt = !this.iterA.hasNext();
    this.iterBisMt = !this.iterB.hasNext();
    return this.iterA.hasNext()
        || this.iterB.hasNext();
  }

  @Override
  public T next() {
    // check mt cases first
    if(this.iterAisMt) {
      return this.iterB.next();
    }else if(this.iterBisMt) {
      return this.iterA.next();
    }
    // else check alternator
    if(this.currentIsIterA) {
      this.currentIsIterA = false;
      return this.iterA.next();
    } else {
      this.currentIsIterA = true;
      return this.iterB.next();
    }
  }
  
  public Iterator<T> iterator() {
    return this;
  }
}

//3. Design an iterator that takes in an ArrayList of iterators,
// and loops through the list of iterators one by one
//of them until every single iterators are exhausted.
//-- Like the previous problem, don't assume that both iterators are of the
//same length, so for example if the ArrayList contains three iterators,
// the first iterator runs out, then alternate between the second and third.

// -- EXAMPLE --
// We have 3 iterators that produce these lists:
// Iterator 1: [1, 2]
// Iterator 2: [10, 20, 30, 40, 50]
// Iterator 3: [100, 200, 300]
// For the first two iterations, the iterator would cycle between 1, 2, and 3.
// > [1, 10, 100, 2, 20, 200]
// However, now Iterator 1 has been depleted,
//Iterator 1: []
//Iterator 2: [30, 40, 50]
//Iterator 3: [300]
// So we must skip iterator 1 from now on.
//> [1, 10, 100, 2, 20, 200, 30, 300]
// Now iterator 2 is the only non mt iterator
//Iterator 1: []
//Iterator 2: [40, 50]
//Iterator 3: []
// So the rest of the iterator will be added onto the list.
//> [1, 10, 100, 2, 20, 200, 30, 300, 40, 50]
// Now all of the iterators have been emptied, so hasNext will finally return false.

class InterleaveUnevenNIters<T> implements Iterator<T>, Iterable<T> {
  ArrayList<Iterator<T>> iters;
  // we use this arraylist to track which iterators don't have a next.
  // it's not strictly necessary but makes this cleaner IMO.
  ArrayList<Boolean> iterAtIndexIsMt;
  int currentPos;
  
  InterleaveUnevenNIters(ArrayList<Iterator<T>> iters) {
    this.iters = iters;
    this.iterAtIndexIsMt = this.updateIterAtIndexIsMt();
    this.currentPos = 0;
  }

  // updates the list to reflect which iterators are mt.
  ArrayList<Boolean> updateIterAtIndexIsMt() {
    ArrayList<Boolean> tmp = new ArrayList<Boolean>();
    for(Iterator<T> it : this.iters) {
      tmp.add(!it.hasNext());
    }
    return tmp;
  }
  
  // gets the next non-mt index
  int getNextNonMtIndex() {
    // Check first from the index after the current position to the end of the list.
    for(int i = currentPos + 1; i < this.iters.size(); i++) {
      if(!this.iterAtIndexIsMt.get(i)) {
        return i;
      }
    }
    // If that fails, check the beginning of the list to the currentPos.
    for(int i = 0; i < currentPos + 1; i++) {
      if(!this.iterAtIndexIsMt.get(i)) {
        return i;
      }
    }
    // You could also use modulo here but thats lame.
    throw new RuntimeException("No non mt index exists!");
  }


  public boolean hasNext() {
    this.iterAtIndexIsMt = this.updateIterAtIndexIsMt();
    // A more thorough solution would to be to implement
    // an orMap, but this is quicker.
    if(iterAtIndexIsMt.contains(false)) {
      return true;
    } else {
      return false;
    }
  }


  public T next() {
    T tmp = this.iters.get(this.currentPos).next();
    this.currentPos = this.getNextNonMtIndex();
    return tmp;
  }
  
  public Iterator<T> iterator() {
    return this;
  } 
}

// -- Premade tests --
// Uncomment them as needed.

class ExamplesIterator {
  void testReverse(Tester t) {
    ArrayList<Integer> expectedResult = 
        new ArrayList<Integer>(Arrays.asList(10,9,8,7,6,5,4,3,2,1,0));
    ArrayList<Integer> result = new ArrayList<Integer>();
    for(Integer i : new ReverseIterator<Integer>(new FromZeroToTen())) {
      result.add(i);
    }
    t.checkExpect(result, expectedResult);
  }
  
  void testInterleave(Tester t) {
    ArrayList<Integer> expectedResult = 
        new ArrayList<Integer>(Arrays.asList(0,10,1,11,2,12,3,13,4,14,5,15,6,16,7,17,8,18,9,19,10,20));
    ArrayList<Integer> result = new ArrayList<Integer>();
    for(Integer i : new Interleave<Integer>(new FromZeroToTen(), new FromTenToTwenty())) {
      result.add(i);
    }
    t.checkExpect(result, expectedResult);
  }
  
  void testInterleaveUneven2Iters(Tester t) {
    ArrayList<Integer> expectedResult = 
        new ArrayList<Integer>(Arrays.asList(0,10,1,11,2,12,3,13,4,14,5,15,6,7,8,9,10));
    ArrayList<Integer> result = new ArrayList<Integer>();
    for(Integer i : new InterleaveUneven2Iters<Integer>(new FromZeroToTen(), new FromTenToFifteen())) {
      result.add(i);
    }
    t.checkExpect(result, expectedResult);
  }
  
  void testInterleaveUnevenNIters(Tester t) {
    ArrayList<Integer> expectedResult = 
        new ArrayList<Integer>(Arrays.asList(0,10,-10,1,11,-9,2,12,-8,3,13,-7,4,14,-6,5,15,-5,6,-4,7,-3,8,-2,9,-1,10,0,1,2,3,4,5));
    ArrayList<Integer> result = new ArrayList<Integer>();
    for(Integer i : new InterleaveUnevenNIters<Integer>(
        new ArrayList<Iterator<Integer>>(
            Arrays.asList(
                new FromZeroToTen(), 
                new FromTenToFifteen(), 
                new FromNegTenToFive())))) {
      result.add(i);
    }
    t.checkExpect(result, expectedResult);
    ArrayList<Integer> expectedResult2 = 
        new ArrayList<Integer>(
            Arrays.asList(0,1,10,10,-10,1,2,11,11,-9,2,3,12,12,-8,3,13,13,-7,4,14,14,-6,5,15,15,-5,6,16,-4,7,17,-3,8,18,-2,9,19,-1,10,20,0,1,2,3,4,5));
    ArrayList<Integer> result2 = new ArrayList<Integer>();
    for(Integer i : new InterleaveUnevenNIters<Integer>(
        new ArrayList<Iterator<Integer>>(
            Arrays.asList(
                new FromZeroToTen(), 
                new FromOneToThree(), 
                new FromTenToTwenty(), 
                new FromTenToFifteen(), 
                new FromNegTenToFive())))) {
      result2.add(i);
    }
    t.checkExpect(result2, expectedResult2);
  }
}