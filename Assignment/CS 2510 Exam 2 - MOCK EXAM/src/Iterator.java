import java.util.ArrayList;
import java.util.Iterator;

import tester.Tester;

class TenIterator implements Iterator<Integer> {
  int ints;

  TenIterator() {
    this.ints = 0;
  }

  public boolean hasNext() {
    return ints <= 10;
  }

  public Integer next() {
    int temp = this.ints;
    this.ints = this.ints + 1;
    return temp;
  }
}

class EvenIterator implements Iterator<Integer> {
  int ints;

  EvenIterator() {
    this.ints = 0;
  }

  public boolean hasNext() {
    return true;
  }

  public Integer next() {
    int temp = this.ints;
    this.ints = this.ints + 2;
    return temp;
  }
}

class StringToIterator implements Iterator<Character> {
  String string;
  int current;

  StringToIterator(String s) {
    this.string = s;
    this.current = 0;
  }

  public boolean hasNext() {
    return this.current < this.string.length();
  }

  @Override
  public Character next() {
    int temp = this.current;
    current = current + 1;
    return this.string.substring(temp, temp + 1).charAt(0);
  }

}

// Design an iterator that is given another iterator when constructed and
// iterates over every other element in the given iterator
class AnotherIterator<T> implements Iterator<T> {
  Iterator<T> iter;

  AnotherIterator(Iterator<T> iter) {
    this.iter = iter;
  }

  public boolean hasNext() {
    return this.iter.hasNext();
  }

  public T next() {
    return this.iter.next();
  }
}

class EachCharacter implements Iterable<String>{
  String s;
  Iterator<Character> int;
  
  EachCharacter(String s){
    this.s = s;
  }
  
  public Iterator<String> iterator() {
    return null;
  }

  
}

class testIterator {

  TenIterator ten = new TenIterator();
  EvenIterator even = new EvenIterator();
  StringToIterator s = new StringToIterator("abc");
  AnotherIterator<Integer> another = new AnotherIterator<Integer>(this.ten);

  int sumAll() {
    int sum = 0;
    while (this.another.hasNext()) {
      sum = sum + ten.next();
    }
    return sum;
  }

  void print() {
    while (this.s.hasNext()) {
      System.out.println(this.s.next());
    }
  }

  void test(Tester t) {
    t.checkExpect(this.sumAll(), 55);
    this.print();
  }

}