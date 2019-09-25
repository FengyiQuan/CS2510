/*interface ILoPerson {
  // Effect: change the number of the person with the given name
  void changePhone(String name, int newNum);

  int findNum(String name);

  boolean contains(String name);

  void removePerson(String name);
}

class MtLoPerson implements ILoPerson {

  public void changePhone(String name, int newNum) {
    return;
  }

  public int findNum(String name) {
    return -1;
  }

  public boolean contains(String name) {
    return false;
  }

  public void removePerson(String name) {

  }
}

class ConsLoPerson implements ILoPerson {
  Person first;
  ILoPerson rest;

  ConsLoPerson(Person f, ILoPerson r) {
    this.first = f;
    this.rest = r;
  }

  public void changePhone(String name, int newNum) {
    if (this.first.sameName(name)) {
      this.first.changeNum(newNum);
    }
    else {
      this.rest.changePhone(name, newNum);
    }
  }

  public int findNum(String name) {
    if (this.first.sameName(name)) {
      return this.first.phone;
    }
    else {
      return this.rest.findNum(name);
    }
  }

  public boolean contains(String name) {
    if (this.first.sameName(name)) {
      return true;
    }
    else {
      return this.rest.contains(name);
    }
  }

  public void removePerson(String name) {

  }
}
*/
import tester.Tester;

class MutablePersonList {
  Sentinel sentinel;

  MutablePersonList() {
    this.sentinel = new Sentinel(new MtLoPerson());
  }

  MutablePersonList(ANode anode) {
    this.sentinel = new Sentinel(anode);
  }

  void removePerson(String name) {
    this.sentinel.rest.removePersonHelp(name, this.sentinel);
  }

  void addPerson(String name, int phone) {
    this.sentinel.rest = new ConsLoPerson(new Person(name,phone), this.sentinel.rest);
  }
}

abstract class APersonList {
  abstract void removePersonHelp(String name, ANode prev);

  abstract void addPersonHelp(String name, int phone);
}

abstract class ANode extends APersonList {
  APersonList rest;

  ANode(APersonList rest) {
    this.rest = rest;
  }

}

class MtLoPerson extends APersonList {
  void removePersonHelp(String name, ANode prev) {
  }

  void addPersonHelp(String name, int phone) {
  }

}

class ConsLoPerson extends ANode {
  Person data;

  ConsLoPerson(Person data, APersonList rest) {
    super(rest);
    this.data = data;
  }

  void removePersonHelp(String name, ANode prev) {
    if (this.data.name.equals(name)) {
      prev.rest = this.rest;
    }
    else {
      this.rest.removePersonHelp(name, this);
    }
  }

  void addPersonHelp(String name, int phone) {
    this.data = new Person(name, phone);
    this.rest = this;
  }
}

class Sentinel extends ANode {
  Sentinel(APersonList rest) {
    super(rest);
  }

  void removePersonHelp(String name, ANode prev) {
    throw new RuntimeException("Can't try to remove on a Sentinel!");
  }

  void addPersonHelp(String name, int phone) {
    this.rest = new ConsLoPerson(new Person(name, phone), new MtLoPerson());
  }
}

class ExamplesList{
  MutablePersonList l1;
  MutablePersonList l2;
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
  
  void initData() {
    this.l1 = new MutablePersonList();
    this.l2 = new MutablePersonList();
  }
  
  void test(Tester t) {
    this.initData();
    t.checkExpect(this.l1, true);
    this.l1.addPerson("Anne", 1234);
    t.checkExpect(this.l1, true);
  }
}