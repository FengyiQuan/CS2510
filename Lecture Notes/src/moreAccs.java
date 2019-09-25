import tester.*;

interface ILoNumber {
  // compute the run-length encoding of this list of numbers
  ILoNumber rle();

  // compute the run-length encoding of a list whose head has been seen count times
  // Acc: count -- number of times we've seen the head
  ILoNumber rleHelp(int count);

  // is the head of this list the same as the comparator?
  boolean headSameAs(int comparator);

  String toCSV();
  
}

class MtLoNumber implements ILoNumber { 

  public ILoNumber rle() { 
    return this;
  }

  public ILoNumber rleHelp(int count) {
    return this;
  }

  public boolean headSameAs(int comparator) { 
    return false;
  }
  
  public String toCSV() {
    return "";
  }
}

class ConsLoNumber implements ILoNumber {
  int num; 
  ILoNumber rest;

  ConsLoNumber(int num, ILoNumber rest) {
    this.num = num;
    this.rest = rest;
  }

  public String toCSV() {
    return this.num + "," + this.rest.toCSV();
  }
  
  public boolean headSameAs(int comparator) {
    return this.num == comparator;
  }

  public ILoNumber rle() { 
    return this.rleHelp(1);
  }

  /* 
   * [7 | 7 ... ]
   * [7 | 8 ... ]
   * [7,7]
   * [7]
   * [7,8,7]
   */

  public ILoNumber rleHelp(int count) { 
    if (this.rest.headSameAs(this.num)) {
      return this.rest.rleHelp(1+count);
    }
    else {
      return new ConsLoNumber(this.num,
          new ConsLoNumber(count, this.rest.rle()));         
    }   
  }
}

class ExamplesAccumulators { 
  ILoNumber origList = 
      new ConsLoNumber (1, new ConsLoNumber (1, new ConsLoNumber (1, new ConsLoNumber (2, 
          new ConsLoNumber (5, new ConsLoNumber (5, new ConsLoNumber (9, new ConsLoNumber (9, 
              new ConsLoNumber (9, new ConsLoNumber (9, new ConsLoNumber (3, 
                  new MtLoNumber ())))))))))));
  ILoNumber newList = 
      new ConsLoNumber (2, new ConsLoNumber (2, new ConsLoNumber (4, new ConsLoNumber (7, 
          new ConsLoNumber (7, new ConsLoNumber (7, new ConsLoNumber (7, new ConsLoNumber (7, 
              new ConsLoNumber (7, new MtLoNumber ())))))))));
  
  ILoNumber rleOrigList = this.origList.rle();
  
  String whathawhaa = 42 + "hello";
  
  String theCSVnewList = newList.toCSV();
  String CSVrleNewList = newList.rle().toCSV();
}








