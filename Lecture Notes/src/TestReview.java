// represents a list of strings
interface ILoString {
  // takes this list of strings and turns it into two lists of strings
  PairOfLists unzip();
  
  // splits this list based on argument keeping track of which list adding to
  PairOfLists unzipHelp(boolean addToFirst, PairOfLists pol);
  
  /// Splits this list based on argument keeping track of which list adding to (second way to solve)
  PairOfLists unzipHelp(boolean addToFirst);
  
  // reverses this list
  ILoString reverse(ILoString acc);
}

// Represents an empty list of strings
class MtLoString implements ILoString {
  // takes this empty list of strings and turns it into a pair of lists
  public PairOfLists unzip() {
    return new PairOfLists(this, this);
  }
  
  // return the final pair of lists
  public PairOfLists unzipHelp(boolean addToFirst, PairOfLists pol) {
    return pol.reverse();
  }

  // reverse this empty list
  public ILoString reverse(ILoString acc) {
    return acc;
  }

  // Second way to solve
  public PairOfLists unzipHelp(boolean addToFirst) {
    return new PairOfLists(this, this);
  }
}

// Represents a non empty list of strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;
  
  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }
  
  // takes this non empty list of strings and turns it into a pair of lists
  public PairOfLists unzip() {
    return this.unzipHelp(true, new PairOfLists(new MtLoString(), new MtLoString()));
  }
  
  // add the first element of the list to the correct list in pol based on the boolean argument
  public PairOfLists unzipHelp(boolean addToFirst, PairOfLists pol) {
    if (addToFirst) {
      return this.rest.unzipHelp(false, pol.addToFirst(this.first));
    } else {
      return this.rest.unzipHelp(true, pol.addToSecond(this.first));
    }
  }
  
  // Second solution
  public PairOfLists unzipHelp(boolean addToFirst) {
    if (addToFirst) {
      return this.rest.unzipHelp(false).addToFirst(this.first);
    } else {
      return this.rest.unzipHelp(true).addToSecond(this.first);
    }
  }

  // Reverses this non empty list
  public ILoString reverse(ILoString acc) {
    return this.rest.reverse(new ConsLoString(this.first, acc));
  }
}

// Represents a pair of lists of strings
class PairOfLists {
  ILoString first, second;
  PairOfLists(ILoString first, ILoString second) {
    this.first = first;
    this.second = second;
  }
  
  // Produces a new pair of lists, with the given String added to
  // the front of the first list of this pair
  PairOfLists addToFirst(String first) {
    return new PairOfLists(new ConsLoString(first, this.first), this.second);
  }
  
  // Produces a new pair of lists, with the given String added to
  // the front of the second list of this pair
  PairOfLists addToSecond(String second) {
    return new PairOfLists(this.first, new ConsLoString(second, this.second));
  }
  
  // reverses the lists in this pol
  PairOfLists reverse() {
    return new PairOfLists(this.first.reverse(new MtLoString()), this.second.reverse(new MtLoString()));
  }
}

class Examples {
  ILoString mt = new MtLoString();
  ILoString list1 = new Utils().makeList("A", "B", "C", "D", "E");
  ILoString list2 = new Utils().makeList("F");
  ILoString list3 = new Utils().makeList("G");
  
  PairOfLists p1 = new PairOfLists(this.mt, this.mt);
  PairOfLists p2 = new PairOfLists(this.list2, this.mt);
  PairOfLists p3 = new PairOfLists(this.list2, this.list3);
  
  // p1.addToFirst("F") -> p2
  // p2.addToSecond("G") -> p3 
  
  // mt.unzip() -> this.p1
  // list1.unzip() -> new PairOfLists(makeList("A", "C", "E"), makeList("B", "D"))
  
  // mt.unzipHelp(true, p2) -> p2
  // list2.unzipHelp(true, p1) -> p2
  // list2.unzipHelp(false, p2) -> new PairOfLists(list2, list2)
  
  // We skipped tests for helpers to save time. You need to write tests for all methods
  // on the test
}

// Ignore this class. I used it to make writing examples faster
class Utils {
  ILoString makeList(String... los) {
    ILoString result = new MtLoString();
    for (String s : los) {
      result = new ConsLoString(s, result);
    }
    return result;
  }
}