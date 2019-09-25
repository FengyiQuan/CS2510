import java.util.ArrayList;
import java.util.Arrays;

import tester.Tester;

class StringLength implements IFunc<String, Integer> {

  public Integer apply(String t) {
    return t.length();
  }

}

class Examples {
  ArrayList<String> strings = new ArrayList<String>(Arrays.asList("a", "bb", "ccc"));
  ArrayList<Integer> ints = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
  Utils u = new Utils();

  void testLists(Tester t) {
    t.checkExpect(this.u.map(this.strings, new StringLength()), this.ints);
  }
}