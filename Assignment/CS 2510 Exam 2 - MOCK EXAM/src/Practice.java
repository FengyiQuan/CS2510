import java.util.HashMap;
import java.util.Iterator;

import tester.Tester;

class StringIterator implements Iterator<String> {
  String input;

  public StringIterator(String input) {
    this.input = input;
  }

  public boolean hasNext() {
    return this.input.length() > 0;
  }

  public String next() {
    String s = this.input.substring(0, 1);
    input = input.substring(0, 1);
    return s;
  }
}

class Exmaples {
  StringIterator abc;

  HashMap<String, Integer> hash;

  void initData() {
    this.hash.put("strawberry", 2);
    this.hash.put("bannana", 3);
    this.hash.get("bannana");
    this.hash.put("rasberry", 3);
    this.hash.get("rasberry");
    this.hash.put("strawberry", 5);
    this.abc = new StringIterator("abc");
  }

  String testfor(Tester t) {
    this.initData();
    for (int i = 0; i < 3; i++) {
      String a = this.abc.input.substring(0, i);
    }
    return a;
  }
}

class Glass {
  String type;
  int cost;

  Glass(String type, int cost) {
    this.type = type;
    this.cost = cost;
  }

  public boolean equals(Object o) {
    if (o instanceof Glass) {
      Glass g = (Glass) o;

      return this.type.equals(g.type) && this.cost == g.cost;
    }
    else {
      return false;
    }
  }

  public int hashCode() {
    return this.cost * 1000 * this.type.hashCode();
  }
}
