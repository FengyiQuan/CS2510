import tester.Tester;

class Person {
  String name;
  int phone;

  Person(String name, int phone) {
    this.name = name;
    this.phone = phone;
  }

  // Returns true when the given person has the same name and phone number as this
  // person
  boolean sameName(String that) {
    return this.name.equals(that);
  }

  // Effect: this person's number is changed to the given one
  void changeNum(int num) {
    this.phone = num;
  }
}

class ExamplePhoneLists {
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

  ILoPerson friends, family, work;

  void initData() {
    this.friends = new ConsLoPerson(this.anne,
        new ConsLoPerson(this.clyde, new ConsLoPerson(this.gail,
            new ConsLoPerson(this.frank, new ConsLoPerson(this.jenny, new MtLoPerson())))));
    this.family = new ConsLoPerson(this.anne,
        new ConsLoPerson(this.dana, new ConsLoPerson(this.frank, new MtLoPerson())));
    this.work = new ConsLoPerson(this.bob,
        new ConsLoPerson(this.clyde, new ConsLoPerson(this.dana, new ConsLoPerson(this.eric,
            new ConsLoPerson(this.henry, new ConsLoPerson(this.irene, new MtLoPerson()))))));
  }

  void testPhoneLists(Tester t) {
    this.initData();
    t.checkExpect(this.friends.findNum("Clyde"), 6789);
    this.friends.changePhone("Clyde", 7890);
    t.checkExpect(this.friends.findNum("Clyde"), 7890);
    t.checkExpect(this.work.findNum("Clyde"), 7890);
  }

  void testRemoveFirstPerson(Tester t) {
    this.initData();
    ILoPerson list1 = new ConsLoPerson(this.anne,
        new ConsLoPerson(this.clyde, new ConsLoPerson(this.henry, new MtLoPerson())));
    ILoPerson list2 = new ConsLoPerson(this.anne,
        new ConsLoPerson(this.dana, new ConsLoPerson(this.gail, new MtLoPerson())));
    t.checkExpect(list1.contains("Anne"), true);
    t.checkExpect(list2.contains("Anne"), true);
    list1.removePerson("Anne");
    t.checkExpect(list1.contains("Anne"), false);
    t.checkExpect(list2.contains("Anne"), true);
  }

  void testRemoveMiddlePerson(Tester t) {
    this.initData();
    ILoPerson list1 = new ConsLoPerson(this.anne,
        new ConsLoPerson(this.clyde, new ConsLoPerson(this.henry, new MtLoPerson())));
    ILoPerson list2 = new ConsLoPerson(this.dana,
        new ConsLoPerson(this.clyde, new ConsLoPerson(this.gail, new MtLoPerson())));
    t.checkExpect(list1.contains("Clyde"), true);
    t.checkExpect(list2.contains("Clyde"), true);
    list1.removePerson("Clyde");
    t.checkExpect(list1.contains("Clyde"), false);
    t.checkExpect(list2.contains("Clyde"), true);
  }

  void testRemoveLastPerson(Tester t) {
    this.initData();
    ILoPerson list1 = new ConsLoPerson(this.anne,
        new ConsLoPerson(this.clyde, new ConsLoPerson(this.henry, new MtLoPerson())));
    ILoPerson list2 = new ConsLoPerson(this.dana,
        new ConsLoPerson(this.gail, new ConsLoPerson(this.henry, new MtLoPerson())));
    t.checkExpect(list1.contains("Henry"), true);
    t.checkExpect(list2.contains("Henry"), true);
    list1.removePerson("Henry");
    t.checkExpect(list1.contains("Henry"), false);
    t.checkExpect(list2.contains("Henry"), true);
  }
}