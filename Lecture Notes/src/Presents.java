import tester.*;


/* 
 * For a version working with higher-order functions
 * 
interface IFunc<X, Y> { 
  Y apply (X x);
}

interface IPred<X> extends IFunc<X, Boolean> { } 

interface ILo<T> { 
  // EFFECT: find the first T that passes the predicate
  // perform on it the operation of the function
  void changeElement(IPred<T> whatToChange, IFunc<T,Void> howToChange);
} 

class MtLo<T> implements ILo<T> {

    public void changeElement(IPred<T> whatToChange, IFunc<T, Void> howToChange) {
     return ;    
  } 

} 

class ConsLo<T> implements ILo<T> { 
  T first;
  ILo<T> rest;

  //constructor 

  public void changeElement(IPred<T> whatToChange, IFunc<T, Void> howToChange) {
    if (whatToChange.apply(this.first)) { 
      howToChange.apply(this.first);
    } 
    else { 
      this.rest.changeElement(whatToChange, howToChange);
    }
  }
}
 */

class Present { 
  String name;
  int price;

  Present (String name, int price) {
    this.name = name;
    this.price = price;
  }

  //is this present the same as that other present  
  boolean samePresentAs(Present that) { 
    return that.name.equals(this.name)
        && that.price == this.price;
  }

  // Does this present have that String as a name 
  boolean presentHasName(String that) { 
    return this.name.equals(that);
  }

  // Return the price of this present
  int priceOf() {
    return this.price;
  }

  // EFFECT: Make this present a broken present, of 0 value.
  void makeBroken() { 
    this.name = "Broken " + this.name; 
    this.price = 0;
  }

}

interface ILoPresent { 
  // EFFECT: Break the present with this name
  void breakPresent(String presentName); 

  // return the cost of the present in the list with this name
  int costOfPresentWName(String presentName);  
  
  // EFFECT: Remove present w/ this name from the unique list if in the list
  void removePresentWName(String presentName); 
  
  void removePresentWNameHelp (String presentName, ConsLoPresent rest); 
  
}

class MtLoPresent implements ILoPresent {

  public void breakPresent(String presentName) { }

  public int costOfPresentWName(String presentName) {
    return -1;
  }

  public void removePresentWName(String presentName) { }

}

class ConsLoPresent implements ILoPresent { 
  Present present;
  ILoPresent rest;

  ConsLoPresent(Present present, ILoPresent rest) { 
    this.present = present;
    this.rest = rest;
  }

  // EFFECT: changes this non-empty list of presents by breaking one of them.
  public void breakPresent(String presentName) {
    if (this.present.presentHasName(presentName)) {
      this.present.makeBroken(); 
    }
    else { 
      this.rest.breakPresent(presentName);
    }
  }

  // Return the cost of the present in this list with that name
  public int costOfPresentWName(String presentName) {
    if (this.present.presentHasName(presentName)) {
      return this.present.priceOf();
    }
    else { 
      return this.rest.costOfPresentWName(presentName);
    }
  }
  
  public void removePresentWName(String presentName) {
    // here's the rub -- we need a SENTINEL. 
    this.rest.removePresentWNameHelp(presentName, this);
  }
  
  public void removePresentWNameHelp(String presentName, ConsLoPresent theOneBefore) {
    if (this.present.presentHasName(presentName)) {
      theOneBefore.rest = this.rest;
    }
    else { 
      this.rest.removePresentWNameHelp(presentName, this);
    }
    
  }
}

class ExamplesPresents { 
  ILoPresent myBabyStuff;
  ILoPresent myBrothersBabyStuff;
  Present nintendo;
  Present newNintendo;
  ILoPresent myStuff;
  ILoPresent myBrothersStuff;

  void initData() { 
    this.myBabyStuff = 
        new ConsLoPresent(new Present("RC Car", 20),
            new ConsLoPresent(new Present("Block Set", 10),
                new ConsLoPresent(new Present("Story Book", 8),
                    new ConsLoPresent(new Present("Onsie", 16),
                        new MtLoPresent()))));
    this.myBrothersBabyStuff = 
        new ConsLoPresent(new Present("RC Car", 20),
            new ConsLoPresent(new Present("Block Set", 10),
                new ConsLoPresent(new Present("Story Book", 8),
                    new ConsLoPresent(new Present("Onsie", 16),
                        new MtLoPresent()))));
    this.nintendo = new Present("Nintendo", 100);
    this.newNintendo = new Present("Nintendo", 100);
    this.myStuff = 
        new ConsLoPresent(new Present("PC Game", 40),
            new ConsLoPresent(new Present("Batman Returns", 15),
                new ConsLoPresent(this.nintendo, this.myBabyStuff)));
    // NB: My brother and I both share one Nintendo. They were expensive!
    this.myBrothersStuff = 
        new ConsLoPresent(new Present("Art Supplies", 45),
            new ConsLoPresent(new Present("Amadeus", 10),
                new ConsLoPresent(this.nintendo, this.myBrothersBabyStuff)));
  } 

  void testPresents (Tester t) { 
    this.initData();
    // t.checkExpect(this.myStuff, this.myBrothersStuff);
    this.myStuff.breakPresent("RC Car");
    // t.checkExpect(this.myStuff, this.myBrothersBabyStuff);
    // t.checkExpect(p == this.nintendo, true);

  }
  

}
