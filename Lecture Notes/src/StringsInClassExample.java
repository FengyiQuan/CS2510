import tester.*;

//Lists of strings
interface ILoString {
  // sort lexicographically 
  ILoString sortByString();
  // insert the element to insert in this sorted list
  ILoString insertInOrder(String eti);
  // reverse this list of strings
  ILoString reverse();
  // append this list onto the base list
  ILoString append(ILoString base);
  // reverse this list of strings with an accumulator 
  // acc: the strings reversed so far
  ILoString reverse2(ILoString acc);
}

//an empty list of strings
class MtLoString implements ILoString {

  /* Template
   * Methods:
   *  
   * Methods of fields: 
   * 
   */

   // sort an empty list of strings
   public ILoString sortByString() {
     return this;
   }
   
   public ILoString insertInOrder(String eti) {
     return new ConsLoString(eti,this);
   }
   
   public ILoString reverse() { 
     return this;
   }
   
   public ILoString append(ILoString base) {
     return base;     
   }
   
   public ILoString reverse2(ILoString acc) {
     return acc;
   }
}



//a non-empty list of numbers 
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString (String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  /* Template
   * Fields:
   * this.first ... String 
   * this.rest ... ILoString
   * Methods:
   *  
   * Methods of fields: 
   */

   // sort this non-empty list of Strings
   public ILoString sortByString() {
     return this.rest.sortByString().insertInOrder(this.first);
   }
  
   // insert the element to insert in this sorted list of strings
   public ILoString insertInOrder(String eti) {
     // to compare strings
     if (this.first.compareTo(eti) < 0) {
       // 9, (3 . rest)
       return new ConsLoString(this.first,this.rest.insertInOrder(eti));
     }
     else {
       return new ConsLoString(eti,this);
     }
   }
   
   public ILoString reverse() {
    return this.rest.reverse().append(new ConsLoString(this.first,new MtLoString()));
   }
   
   public ILoString append(ILoString base) {
     return new ConsLoString(this.first,this.rest.append(base));
   }
   
   public ILoString reverse2(ILoString acc) {
     return this.rest.reverse2(new ConsLoString(this.first,acc));
   }
   
}

class ExamplesStrings { 
  ILoString namesList = 
      new ConsLoString ("Alice",
          new ConsLoString ("Bob",
              new ConsLoString ("Charlie",
                  new ConsLoString ("Daniel",
                      new MtLoString()))));

  ILoString flavorsList = 
      new ConsLoString ("Espresso",
          new ConsLoString ("Dulce de leche",
              new ConsLoString ("Chocolate",
                  new ConsLoString ("Butterscotch",
                      new ConsLoString ("Almond",
                          new MtLoString())))));
  
  ILoString basicList = new ConsLoString ("Daniel", new MtLoString());
  ILoString res1 = basicList.insertInOrder("Ebenezer");
  ILoString test2 = flavorsList.insertInOrder("Alfalfa").sortByString();
  ILoString bigList = flavorsList.append(this.namesList);
  ILoString sravolfList = flavorsList.reverse2(new MtLoString());
  
  }







