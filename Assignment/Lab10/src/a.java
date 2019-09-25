import tester.Tester;
import java.util.ArrayList;

interface IFunc<X, Y> {
  Y apply(X x);
}

class ArrayUtil {
  // get the *earliest* index of the elements in arr
  // that produces the longest string when func is applied
  // or -1 if empty
  // Reminder: Strings have the length() method, which returns an int
  // Reminder: index-based for loops on array lists have the following
  // boilerplate:
  // for (int i = 0; i < arr.size(); i++) {
  // }
  <X> int indexLongest(IFunc<X, String> func, ArrayList<X> arr) {
    int maxSoFar = 0;
    int indexSoFar = -1;
    for (int i = 0; i < arr.size(); i++) {
      if (func.apply(arr.get(i)).length() > maxSoFar) {
        maxSoFar = func.apply(arr.get(i)).length();
        indexSoFar = i;
      }
    }
    return indexSoFar;
  }
}

// A Person with a name and nickname
class Person {
  String name;
  String nickname; // empty string represents no nickname

  Person(String name) {
    this.name = name;
    this.nickname = "";
  }

  Person(String name, String nickname) {
    this.name = name;
    this.nickname = nickname;
  }

  // the name to call this person
  String nameToCall() {
    if (this.nickname.equals("")) {
      return name;
    }
    else {
      return nickname;
    }
  }
}

// Function object that gets name of a person
class NameToCall implements IFunc<Person, String> {
  public String apply(Person p) {
    return p.nameToCall();
  }
}

class ExamplesArr {
  IFunc<Person, String> nameToCall = new NameToCall();
  ArrayUtil util = new ArrayUtil();
  Person matt = new Person("Matthew", "Matt");
  Person becca = new Person("Rebecca", "Becca");
  Person david = new Person("David");

  ArrayList<Person> mt;
  ArrayList<Person> justMatt;
  ArrayList<Person> mattAndBecca;
  ArrayList<Person> mattAndBeccaAndDavid;

  void initializeData() {
    mt = new ArrayList<Person>();
    justMatt = new ArrayList<Person>();
    justMatt.add(matt);
    mattAndBecca = new ArrayList<Person>();
    mattAndBecca.add(matt);
    mattAndBecca.add(becca);
    mattAndBeccaAndDavid = new ArrayList<Person>();
    mattAndBeccaAndDavid.add(matt);
    mattAndBeccaAndDavid.add(becca);
    mattAndBeccaAndDavid.add(david);
  }

  void testName(Tester t) {
    t.checkExpect(matt.nameToCall(), "Matt");
    t.checkExpect(becca.nameToCall(), "Becca");
    t.checkExpect(david.nameToCall(), "David");
  }

  void testIndexLongestOnName(Tester t) {
    initializeData();
    testIndexLongestOnName(t, mt, -1);
    testIndexLongestOnName(t, justMatt, 0);
    testIndexLongestOnName(t, mattAndBecca, 1);
    testIndexLongestOnName(t, mattAndBeccaAndDavid, 1);
  }

  void testIndexLongestOnName(Tester t, ArrayList<Person> people, int expected) {
    t.checkExpect(util.indexLongest(nameToCall, people), expected);
  }
}