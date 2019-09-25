import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import tester.Tester;
import java.util.Objects;
import java.util.Random;

//Shall we play a game?

/* 
"snake" "cat" "horse" "bat" "dog", rotate to the right by 3
"horse" "bat" "dog" "snake" "cat" 
 */

class ArrayUtils { 

  // *shudder*
  // A short 2-line solution of effectively write-only code!
  // and to make it work negative as well we had to:
  // (((n % m) + m) % m) 
  <T> void rotateRight(ArrayList<T> al, int numPlaces) {
    for (numPlaces = numPlaces % al.size(); numPlaces > 0; numPlaces--) {
      al.add(0,al.remove(al.size() - 1));     
    }

    /* 
    for(int i = 0; i < al.size(); i++) { 
      T old = al.get((i + numPlaces) % al.size());
      al.set(i, old);
    }
     */ 
  }

}

// Returns only every nth element of the previous iterator
class EveryN<X> implements Iterator<X> {
  Iterator <X> iter;
  int c; 

  EveryN(Iterator <X> iter, int c) {
    this.iter = iter;
    this.c = c;
  }

  public boolean hasNext() {
    return this.iter.hasNext();
  }

  public X next() {
    X res = this.iter.next();
    for (int i = 1; i < c; i++) { 
      if (this.iter.hasNext()) {
        this.iter.next();
      }
    }
    return res;
  }

}

class Pair<X,Y> {
  X x;
  Y y;

  Pair(X x, Y y) {
    this.x = x;
    this.y = y;
  }

}

class Zipperator<X,Y> implements Iterator<Pair<X,Y>> {
  Iterator<X> itx;
  Iterator<Y> ity;

  // the obvious constructor
  
  public boolean hasNext() {
    return this.itx.hasNext() && this.ity.hasNext();
  }

  public Pair<X, Y> next() {
    return new Pair<X,Y>(this.itx.next(),this.ity.next());
  }
  
}

// or-predicates

interface IFunc<Input,Output> { 
  Output apply (Input in);
}

interface IPred<Input> extends IFunc<Input,Boolean> { }

class OrPred<Input> implements IPred<Input> {
  IPred<Input> left;
  IPred<Input> right;
  
  OrPred(IPred<Input> left, IPred<Input> right) {
    this.left = left;
    this.right = right;
  }
  
  public Boolean apply (Input in) {
    return this.left.apply(in) || this.right.apply(in);
  } 
  
}

// Hungry for more??
//And Predicates
//Left-and-not-right
//XOR
// Doesn't it feel like there's a missing abstraction! Go find it! 

class ExamplesReview {
  ArrayUtils au = new ArrayUtils();
  ArrayList<String> exArrL;
  ArrayList<String> critters 
  = new ArrayList<String>(Arrays.asList("snake",
      "cat",
      "horse",
      "bat",
      "dog"));

  // We weren't sure about how the arithmetic works
  // So we *tested it*!!
  int answer = (((-13) % 4) + 4) % 4;
  int answer2 = ((7 % 4) + 4) % 4;

  void initData() { 
    this.exArrL 
    = new ArrayList<String>(Arrays.asList("fish", 
        "horse",
        "turtle",
        "hampster"));
  }

  void testRotateRight (Tester t) { 
    this.initData();
    t.checkExpect(this.exArrL.size(), 4);
    t.checkExpect(this.exArrL, 
        new ArrayList<String>(Arrays.asList("fish", 
            "horse",
            "turtle",
            "hampster")));
    this.au.rotateRight(this.exArrL, 2); 
    t.checkExpect(this.exArrL, 
        new ArrayList<String>(Arrays.asList("turtle",
            "hampster",
            "fish", 
            "horse")));
    this.initData();
    this.au.rotateRight(this.exArrL, 13); 
    t.checkExpect(this.exArrL, 
        new ArrayList<String>(Arrays.asList(
                                            "hampster",
                                            "fish", 
                                            "horse",
                                            "turtle")));

  }

  HashMap<String,String> hm;
  Iterator<String> strs;
  Iterator<String> strs2;

  void initHM () { 
    this.hm = new HashMap<String,String>();
    hm.put("fish", "filet");
    hm.put("turtle", "soup");
    hm.put("horse", "tartare");
  }

  void testHashMap (Tester t) { 
    this.initHM();
    strs = hm.values().iterator(); // for the values
    strs = hm.keySet().iterator(); // for the keys
    
  }
}


