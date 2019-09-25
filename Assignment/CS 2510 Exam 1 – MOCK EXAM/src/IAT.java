import tester.Tester;

interface IAT {
  // returns true if this ancestry tree contains some Person with the given name
  boolean containsName(String name);

  // returns true if anyone in this ancestry tree has the same name as one of
  // their ancestors
  boolean duplicateNames();

}

class Unknown implements IAT {

  public boolean containsName(String name) {
    return false;
  }

  public boolean duplicateNames() {
    return false;
  }

}

class Person implements IAT {
  String name;
  IAT dad, mom;

  Person(String name, IAT dad, IAT mom) {
    this.name = name;
    this.dad = dad;
    this.mom = mom;
  }

  public boolean containsName(String name) {
    return this.name.contentEquals(name) || this.dad.containsName(name)
        || this.mom.containsName(name);
  }

  public boolean duplicateNames() {
    return this.mom.containsName(this.name) || this.dad.containsName(this.name)
        || this.dad.duplicateNames() || this.mom.duplicateNames();
  }
}

class ExamplesIAT {
  IAT davisSr = new Person("Davis", new Unknown(), new Unknown());
  IAT edna = new Person("Edna", new Unknown(), new Unknown());
  IAT davisJr = new Person("Davis", davisSr, edna);
  IAT carl = new Person("Carl", new Unknown(), new Unknown());
  IAT candace = new Person("Candace", davisJr, new Unknown());
  IAT claire = new Person("Claire", new Unknown(), new Unknown());
  IAT bill = new Person("Bill", carl, candace);
  IAT bree = new Person("Bree", new Unknown(), claire);
  IAT anthony = new Person("Anthony", bill, bree);

  boolean testContainsName(Tester t) {
    return t.checkExpect(this.davisJr.containsName("a"), false)
        && t.checkExpect(this.davisJr.containsName("Davis"), true)
        && t.checkExpect(this.anthony.containsName("Carl"), true)
        && t.checkExpect(new Unknown().containsName("a"), false);
  }

  boolean testDuplicateNames(Tester t) {
    return t.checkExpect(this.anthony.duplicateNames(), true)
        && t.checkExpect(this.edna.duplicateNames(), false);
  }
}