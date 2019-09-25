import tester.Tester;

class Building {
  Office office;

  Building(Office office) {
    this.office = office;
  }
}

class Office {
  String building;
  int room;
  String occupant;

  Office(String building, int room, String occupant) {
    this.building = building;
    this.room = room;
    this.occupant = occupant;
  }
}

class ExampleOffice {
  void F1(Office forOffice, String toOccupant) {
    forOffice = new Office(forOffice.building, forOffice.room, toOccupant);
  }

  String question1() {
    Office carmenOffice = new Office("CS", 176, "Carmen");
    F1(carmenOffice, "Fred");
    return carmenOffice.occupant;
  }
  // return "Carmen"

  void F2(Office o1, Office o2) {
    o2 = o1;
    o2.room = 63;
  }

  String question2() {
    Office labOffice = new Office("CS", 130, "Tyler Smith");
    Office commonOffice = new Office("CS", 275, "everyone");
    F2(labOffice, commonOffice);
    return labOffice.room + ", " + commonOffice.room;
  }
  // return "63, 275"

  void F3(Office o1) {
    Building bill = new Building(o1);
    bill.office.room = 999;
  }

  int question3() {
    Office peterOffice = new Office("CS", 37, "Peter Gibbons");
    Building cs = new Building(peterOffice);
    F3(peterOffice);
    return cs.office.room;
  }
  // return 999

  void F4(Office o1) {
    Office o = o1;
    o = new Office("CS", 640, "Walter");
  }

  String question4() {
    Office dillonOffice = new Office("CS", 808, "Dillon");
    F4(dillonOffice);
    return dillonOffice.occupant;
  }
  // return "Dillon"

  public int f() {
    int x = 17;
    return g();
  }

  public int g() {
    int y = 4;
    return x / y;
  }

  public int question5() {
    return f();
  }

  void test(Tester t) {
    t.checkExpect(this.question1(), "Carmen");
    t.checkExpect(this.question2(), "63, 275");
    t.checkExpect(this.question3(), 999);
    t.checkExpect(this.question4(), "Dillon");
    t.checkExpect(this.question5(), null);
  }
}
