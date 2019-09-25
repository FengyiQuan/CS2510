import java.util.ArrayList;
import java.util.Arrays; // Note what we had to add here. 
import tester.*;

// Just setup 
interface IFunc<X, Y> { 
  Y apply (X x);
}

interface IPred<X> extends IFunc<X, Boolean> { }

interface IRed<X, Y> {
  Y reduce (X x, Y y);
}

class Card { 
  String val1;
  String val2;

  Card (String v, String s) {
    this.val1 = v;
    this.val2 = s;
  }

}

// Today's topics:
// More work with styles of for loops
// Power and peril of indices
// Syntactic superpowers

class Utils { 

  // Produce a list the results of f on ints [0, n) 
  <T> ArrayList<T> buildList(int n, IFunc<Integer, T> f) { 
    ArrayList<T> ans = new ArrayList<T>();
    for (int i = 0; i < n; i = i + 1) { 
      ans.add(f.apply(i));
    }
    return ans;
  } 

  // Produce a right-fold of an ArrayList starting at base and 
  // reducing by red over al
  /* Whabout incr, decr syntax? */
  <T, U> U foldr(ArrayList<T> al, IRed<T, U> red, U base) { 
    for(int i = al.size() - 1; 0 <= i; i = i - 1) {
      base = red.reduce(al.get(i), base);
    }
    return base;
  }

  /* 
  i++
  ++i++ ???!
  --i++ ???!
  */ 
  
  // Beginning with a's, interleaves two lists of the same length
  <T> ArrayList<T> interleave (ArrayList<T> as, ArrayList<T> bs) { 
    ArrayList<T> ans = new ArrayList<T>();
    for (int i = 0; i < as.size(); i = i + 1) {
      ans.add(as.get(i));
      ans.add(bs.get(i));
    }
    return ans;
  }

  // Produces an ArrayList of all the as for which we remove the badOnes
  <T> ArrayList<T> filterOut (ArrayList<T> ts, IPred<T> isBad) {
    ArrayList<T> ans = new ArrayList<T>();
    for (T t : ts) { 
      if (!isBad.apply(t)) {   
        ans.add(t);
      } 
    }
    return ans;
  }

  // return a deck of cards represented by each suit value combination
  ArrayList<Card> buildDeck(ArrayList<String> suits, ArrayList<String> vals) {
    ArrayList<Card> ans = new ArrayList<Card>();
    /* 
    for (int i = 0; i < suits.size(); i = i + 1) { 
      for (int j = 0; j < vals.size(); j = j + 1) {
        Card newC = new Card(suits.get(i), vals.get(j));
        ans.add(newC);
      }
    }
    */ 
    for (String s : suits) {
      for (String v : vals) { 
        ans.add(new Card(s, v));
      }
    }
    return ans;
  }
  
  // Transpose the values of the cards
  void transpose(Card c) {  
    c = new Card(c.val2, c.val1);
  }
}

class ArrayExamples  {
  ArrayList<String> suits;
  ArrayList<String> vals;
  Utils u;
  
  void initData() {
    this.u = new Utils();
    // A special incantation to go from List -> ArrayList.
    // See documentation if you want to know more!
    this.suits = new ArrayList<String>(Arrays.asList("Spades", "Hearts", "Diamonds", "Clubs"));
    this.vals = new ArrayList<String>(Arrays.asList("Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"));
  }

} 
