import tester.Tester;

interface ILoString {
  // Design a method that gets the last index of a string in this list
  // or -1 if it does not exist
  // [a, b, c, a, b] will return 3 for a 
  
  //return the last index of a given string in this list of strings
  int lastIndex(String s);
  
  //return the last index of a given string using the accumulator and this list 
  //of strings Accumulator 1 - the current index Accumulator 2 - the most recent string index
  int lastIndexAcc(String s, int curr, int mostRecent);
}

class MtLoString implements ILoString {
  //return the last index of a given string in this empty list of strings
  public int lastIndex(String s) {
    return -1;
  }
  
  //return the last index of the string using an accumulator Acc1 - curr , acc2 - recent
  public int lastIndexAcc(String s, int curr, int mostRecent) {
    return mostRecent;
  }
}

class ConsLoString implements ILoString {
  String first;
  ILoString rest;
  
  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }
  
  //return the last index of a given string in this ConsLoString
  public int lastIndex(String s) {
    return lastIndexAcc(s, 0, -1);
        
  }
  //return the last index of the given string using an accumulator (acc1 - curr) acc2 - recent
  public int lastIndexAcc(String s, int curr, int mostRecent) {
    if (this.first.equals(s)) {
      return this.rest.lastIndexAcc(s, curr + 1, curr);
    }
    else {
      return this.rest.lastIndexAcc(s, curr + 1, mostRecent);
    }
  }
 
}

class ExamplesString {
  ILoString mt = new MtLoString();
  ILoString l1 = new ConsLoString("a", new ConsLoString("b",
      new ConsLoString("c", new ConsLoString("d", new ConsLoString("e", this.mt)))));
  ILoString l2 = new ConsLoString("a", new ConsLoString("b", new ConsLoString("c",
      new ConsLoString("a", new ConsLoString("d", new ConsLoString("e", this.mt))))));

  boolean testLastIndexOf(Tester t) {
    return t.checkExpect(mt.lastIndex("a"), -1) &&
        t.checkExpect(l1.lastIndex("a"), 0) &&
        t.checkExpect(l1.lastIndex("f"), -1) &&
        t.checkExpect(l2.lastIndex("a"), 3) &&
        t.checkExpect(l2.lastIndex("e"), 5);
  }
}





