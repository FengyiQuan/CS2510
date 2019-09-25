import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import tester.Tester;

interface IFunc<X,Y> { 
  Y apply (X x);
}

/* Reminder:

  An *Iterator* is an object that maintains local state so that you can:

  1. check if there's another element 
  2. get the next element

  c.f. "calculaTOR", "acceleraTOR", "prosecuTOR". 

  It's a thing that can do iterating ...
  ... through some kinda collection of stuff.

  If so, that collection of stuff is interABLE.

  An IterABLE is something that can be iterated
  An executABLE is something that can be executed
  A  variABLE  is something that can be varied.  
 */

class ArrayListIterator<T> implements Iterator<T> {
  int currIdx;
  ArrayList<T> al;

  ArrayListIterator(ArrayList<T> al) {
    this.al = al;
    this.currIdx = 0;
  }

  // test whether this arraylist has a next element
  public boolean hasNext() {
    return this.currIdx < al.size();
  }

  // get the next element of this arraylist
  // EFFECT increments the current index
  public T next() {
    T ans = this.al.get(currIdx);
    currIdx++; // remember? 
    return ans;
  }

}

interface ILo<T> extends Iterable<T> {
  boolean isCons();
  ConsLo<T> asCons();

}

class MtLo<T> implements ILo<T> {
  public Iterator<T> iterator() {
    return new ILoIterator<T>(this);
  }

  public boolean isCons() {
    return false;
  }

  public ConsLo<T> asCons() {
    throw new ClassCastException("you cannot get there from here");
  }
}

class ConsLo<T> implements ILo<T> {
  T first;
  ILo<T> rest;

  ConsLo(T first, ILo<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public Iterator<T> iterator() {
    return new ILoIterator<T>(this);
  }

  public boolean isCons() {
    return true;
  }

  public ConsLo<T> asCons() {
    return this;
  }
}

class ILoIterator<T> implements Iterator<T> { 
  ILo<T> items;

  ILoIterator (ILo<T> items) {
    this.items = items;
  }

  // Do we have another item to process? 
  public boolean hasNext() {
    // ATTENTION! 
    // Yes, this will be weird, and not our usual style!
    // See lecture notes. 
    // The data is "tightly coupled" to implementation
    return this.items.isCons();
  }

  // Get the next element to process.
  // EFFECT: sets items to be the rest of the old items
  public T next() {
    ConsLo<T> temp = this.items.asCons();
    this.items = temp.rest; 
    return temp.first;
  }
}

// An iterator with unbounded numbers of elements! 
class Squares implements Iterator<Integer> {
  int root = 1;

  public boolean hasNext() {
    return true;
  }

  public Integer next() {
    int square = root * root;
    root++;
    return square;
  } 
}

//Map a function over an iterator!
//An iterator to map a function over the items in an iterator
//class MapIter<T, U> implements Iterator<U>
class MapIter<T, U> implements Iterator<U>, Iterable<U> {
  Iterator<T> iter;
  IFunc<T, U> f;

  MapIter(Iterator<T> iter, IFunc<T, U> f) {
    this.iter = iter;
    this.f = f;
  }

  public boolean hasNext() {
    return this.iter.hasNext();
  }

  public U next() {
    return this.f.apply(this.iter.next());
  }

  public Iterator<U> iterator() {
    return this;
  }
}

class SquareIt implements IFunc<Integer, Integer> {

  public Integer apply(Integer x) {
    return x * x;
  }
}

// Iterator for another iterator
class FrontN<T> implements Iterator<T>, Iterable<T> { 
  int n;
  Iterator<T> iter;

  FrontN(int n, Iterator<T> iter) { 
    this.n = n;
    this.iter = iter;
  }

  // Produces an Iterator for FrontN 
  public Iterator<T> iterator() {
    return this;
  }

  public boolean hasNext() {
    return 0 < n && iter.hasNext();
  }

  //Return the next element of the underlying iterator, if it has one.
  //EFFECT: decrement counter n and advance the underlying iterator
  public T next() {
    n--;
    return iter.next();
  }
}

// You do! Lots of fun! 
// Interleave two iterators!
class InterleavedIterator<T> implements Iterator<T> {}


class IterExamples {     
  // Examples

  ArrayList<Integer> fstPrimes = new ArrayList<Integer>(Arrays.asList(2,3,5,7,11));
  // Testing our own home-made AL Iterator
  Iterator<Integer> otherFstPrimIter = new ArrayListIterator<Integer>(this.fstPrimes);
  // Using Java's built-in iterator maker
  Iterator<Integer> fstPrimIter = this.fstPrimes.iterator(); 
  // really, that method could be named makeIterator

  ILo<Integer> twoInts = 
      new ConsLo<Integer>(2, new ConsLo<Integer>(1, new MtLo<Integer>()));
  Iterator<Integer> sndILoIter = twoInts.iterator();
  Iterator<Integer> fstILoIter = new ILoIterator<Integer>(twoInts); 

  void testIter (Tester t) { 

    t.checkExpect(fstPrimIter.next(), 2);
    t.checkExpect(fstPrimIter.next(), 3);
    t.checkExpect(otherFstPrimIter.next(), 2);
    t.checkExpect(this.otherFstPrimIter.next(), 3);

    /* 
     * Question: 
     * Can we now use a for-each loop with an ILo<T>? 
     * Yes!
     */

    int total = 0;
    for (Integer i : twoInts) { 
      total = total + i;
    }

    t.checkExpect(total,3);

    t.checkExpect(fstILoIter.hasNext(), true);
    t.checkExpect(fstILoIter.hasNext(), true);
    t.checkExpect(fstILoIter.next(), 2);
    t.checkExpect(fstILoIter.next(), 1);
    t.checkExpect(fstILoIter.hasNext(), false);
    t.checkExpect(fstILoIter.hasNext(), false);
    t.checkExpect(sndILoIter.hasNext(), true);

    MapIter<Integer, Integer> mi 
    = new MapIter<Integer, Integer>(sndILoIter, new SquareIt());
    t.checkExpect(mi.next(), 4);
    t.checkExpect(sndILoIter.next(), 1);

    Iterator<Integer> squiterator = new Squares();
    t.checkExpect(squiterator.hasNext(), true);
    t.checkExpect(squiterator.next(), 1 * 1);
    t.checkExpect(squiterator.next(), 2 * 2);

    Iterable<Integer> firstSquares = new FrontN<Integer>(10, new Squares());
    Iterator<Integer> firstSquaresIter = firstSquares.iterator();
    t.checkExpect(firstSquaresIter.hasNext(), true);

    int sumOfSquares = 0;
    for (Integer i: firstSquares) {
      sumOfSquares += i;
    }

    t.checkExpect(sumOfSquares, 1 + 4 + 9 + 16 + 25 + 
        36 + 49 + 64 + 81 + 100);
    t.checkExpect(firstSquaresIter.next(),121);

  }
} 
