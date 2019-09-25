
import tester.*;

class Person {
  String name;
  int phone;

  Person(String name, int phone) {
    this.name = name;
    this.phone = phone;
  }

  // Returns true when the given person has the same name and phone number as this
  // person
  boolean samePerson(Person that) {
    return this.name.equals(that.name) && this.phone == that.phone;
  }

  boolean sameName(String that) {
    return this.name.equals(that);
  }
}

//Represents a sentinel at the start, a node in the middle,
//or the empty end of a list
abstract class APersonList {
  abstract void removePersonHelp(String name, ANode prev);

  abstract boolean contains(String name);

  APersonList() {
  } // nothing to do here
}

//Represents a node in a list that has some list after it
abstract class ANode extends APersonList {
  APersonList rest;

  ANode(APersonList rest) {
    this.rest = rest;
  }

}

//Represents the empty end of the list
class MtLoPerson extends APersonList {
  MtLoPerson() {
  } // nothing to do

  void removePersonHelp(String name, ANode prev) {
    return;
  }

  boolean contains(String name) {
    return false;
  }
}

//Represents a data node in the list
class ConsLoPerson extends ANode {
  Person data;

  ConsLoPerson(Person data, APersonList rest) {
    super(rest);
    this.data = data;
  }

  void removePersonHelp(String name, ANode prev) {
    if (this.data.name.equals(name)) {
      prev.rest = this.rest;
    } else {
      this.rest.removePersonHelp(name, this);
    }
  }

  public boolean contains(String name) {
    return this.data.sameName(name) || this.rest.contains(name);
  }
}

//Represents the dummy node before the first actual node of the list
class Sentinel extends ANode {
  Sentinel(APersonList rest) {
    super(rest);
  }

  void removePersonHelp(String name, ANode prev) {
    throw new RuntimeException("Can't try to remove on a Sentinel!");
  }

  @Override
  boolean contains(String name) {
    // TODO Auto-generated method stub
    return this.rest.contains(name);
  }
}

class MutablePersonList {
  Sentinel sentinel;

  MutablePersonList() {
    this.sentinel = new Sentinel(new MtLoPerson());
  }

  void removePerson(String name) {
    this.sentinel.rest.removePersonHelp(name, this.sentinel);
  }
}

class Examples {
  Person anne = new Person("Anne", 1234);
  Person bob = new Person("Bob", 3456);
  Person clyde = new Person("Clyde", 6789);
  Person dana = new Person("Dana", 1357);
  Person eric = new Person("Eric", 12469);
  Person frank = new Person("Frank", 7294);
  Person gail = new Person("Gail", 9345);
  Person henry = new Person("Henry", 8602);
  Person irene = new Person("Irene", 91302);
  Person jenny = new Person("Jenny", 8675309);

  APersonList friends, work;

//In ExamplePhoneLists
  void initData() {
    // ... initialize Anne, Bob, etc...
    this.work = new ConsLoPerson(this.bob, new ConsLoPerson(this.clyde, new ConsLoPerson(this.dana,
        new ConsLoPerson(this.eric, new ConsLoPerson(this.frank, new MtLoPerson())))));

    // We're friends with everyone at work, and also with other people
    this.friends = new ConsLoPerson(this.anne,
        new ConsLoPerson(this.gail, new ConsLoPerson(this.henry, this.work)));
  }

  void testRemoveCoworker(Tester t) {
    this.initData();
    t.checkExpect(this.work.contains("Bob"), true);
    this.work.removePersonHelp("Bob", new Sentinel(this.work));
    t.checkExpect(this.work.contains("Bob"), false);
    // t.checkExpect(this.work.contains("dana"), true);
  }
}


