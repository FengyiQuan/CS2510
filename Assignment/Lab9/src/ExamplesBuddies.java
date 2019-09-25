import tester.*;

// runs tests for the buddies problem
class ExamplesBuddies {

  Person ann;
  Person bob;
  Person cole;
  Person dan;
  Person ed;
  Person fay;
  Person gabi;
  Person hank;
  Person jan;
  Person kim;
  Person len;

  Person a;
  Person b;
  Person c;
  Person d;
  Person e;

  ILoBuddy mt;
  ILoBuddy annBuddy;
  ILoBuddy bobBuddy;
  ILoBuddy coleBuddy;
  ILoBuddy danBuddy;
  ILoBuddy edBuddy;
  ILoBuddy fayBuddy;
  ILoBuddy gabiBuddy;
  ILoBuddy janBuddy;
  ILoBuddy kimBuddy;
  ILoBuddy lenBuddy;

  ILoDouble mtLoDouble;
  ILoDouble LoDouble;

  void initBuddies() {
    this.ann = new Person("Ann");
    this.bob = new Person("Bob");
    this.cole = new Person("Cole");
    this.dan = new Person("Dan");
    this.ed = new Person("Ed");
    this.fay = new Person("Fay");
    this.gabi = new Person("Gabi");
    this.hank = new Person("Hank");
    this.jan = new Person("Jan");
    this.kim = new Person("Kim");
    this.len = new Person("Len");

    this.a = new Person("A", 0.95, 0.8);
    this.b = new Person("B", 0.85, 0.99);
    this.c = new Person("C", 0.95, 0.9);
    this.d = new Person("D", 1.0, 0.95);

    this.a.addBuddy(this.b);
    this.a.addBuddy(this.c);
    this.b.addBuddy(this.d);
    this.c.addBuddy(this.d);

    this.mt = new MTLoBuddy();
    this.annBuddy = new ConsLoBuddy(this.bob, new ConsLoBuddy(this.cole, this.mt));
    this.bobBuddy = new ConsLoBuddy(this.ann,
        new ConsLoBuddy(this.ed, new ConsLoBuddy(this.hank, this.mt)));
    this.coleBuddy = new ConsLoBuddy(this.dan, this.mt);
    this.danBuddy = new ConsLoBuddy(this.cole, this.mt);
    this.edBuddy = new ConsLoBuddy(this.fay, this.mt);
    this.fayBuddy = new ConsLoBuddy(this.ed, new ConsLoBuddy(this.gabi, this.mt));
    this.gabiBuddy = new ConsLoBuddy(this.ed, new ConsLoBuddy(this.fay, this.mt));
    this.janBuddy = new ConsLoBuddy(this.kim, new ConsLoBuddy(this.len, this.mt));
    this.kimBuddy = new ConsLoBuddy(this.jan, new ConsLoBuddy(this.len, this.mt));
    this.lenBuddy = new ConsLoBuddy(this.jan, new ConsLoBuddy(this.kim, this.mt));

    this.ann.addBuddies(this.annBuddy);
    this.bob.addBuddies(this.bobBuddy);
    this.cole.addBuddies(this.coleBuddy);
    this.dan.addBuddies(this.danBuddy);
    this.ed.addBuddies(this.edBuddy);
    this.fay.addBuddies(this.fayBuddy);
    this.gabi.addBuddies(this.gabiBuddy);
    this.jan.addBuddies(this.janBuddy);
    this.kim.addBuddies(this.kimBuddy);
    this.len.addBuddies(this.lenBuddy);

    this.mtLoDouble = new MtLoDouble();
    this.LoDouble = new ConsLoDouble(0.5, new ConsLoDouble(0.5, this.mtLoDouble));
  }

  void testAddBuddy(Tester t) {
    this.initBuddies();
    t.checkExpect(this.ed.buddies, this.edBuddy);
    this.hank.addBuddy(this.ann);
    t.checkExpect(this.hank.buddies, new ConsLoBuddy(this.ann, this.mt));
  }

  void testAddBuddies(Tester t) {
    this.initBuddies();
    t.checkExpect(this.ed.buddies, this.edBuddy);
    this.hank.addBuddies(this.edBuddy);
    t.checkExpect(this.hank.buddies, this.edBuddy);
  }

  void testHasDirectBuddy(Tester t) {
    this.initBuddies();
    t.checkExpect(this.ann.hasDirectBuddy(this.bob), true);
    t.checkExpect(this.ann.hasDirectBuddy(this.len), false);
  }

  void testCountCommonBuddies(Tester t) {
    this.initBuddies();
    t.checkExpect(this.ann.countCommonBuddies(this.dan), 1);
    t.checkExpect(this.kim.countCommonBuddies(this.kim), 2);
  }

  void testPartyCount(Tester t) {
    this.initBuddies();
    t.checkExpect(this.ann.partyCount(), 8);
    t.checkExpect(this.hank.partyCount(), 1);
  }

  void testhasExtendedBuddy(Tester t) {
    this.initBuddies();
    t.checkExpect(this.jan.hasExtendedBuddy(this.len), true);
    t.checkExpect(this.ann.hasExtendedBuddy(this.len), false);
    t.checkExpect(this.a.hasExtendedBuddy(this.b), true);
    t.checkExpect(this.b.hasExtendedBuddy(this.a), false);
  }

  void testSameName(Tester t) {
    this.initBuddies();
    t.checkExpect(this.ann.sameName("Ann"), true);
    t.checkExpect(this.ann.sameName("Anns"), false);
  }

  void testGetAllBuddies(Tester t) {
    this.initBuddies();
    t.checkExpect(this.hank.getAllBuddies(), new ConsLoBuddy(this.hank, this.mt));

  }

  void testGetAllBuddiesAcc(Tester t) {
    this.initBuddies();
    t.checkExpect(this.hank.getAllBuddiesAcc(this.mt), new ConsLoBuddy(this.hank, this.mt));
  }

  void testAlreadyInVisited(Tester t) {
    this.initBuddies();
    t.checkExpect(this.hank.alreadyInVisited(annBuddy), false);
    t.checkExpect(this.bob.alreadyInVisited(this.annBuddy), true);
  }

  void testAddTo(Tester t) {
    this.initBuddies();
    t.checkExpect(this.hank.buddies, this.mt);
    this.edBuddy.addTo(this.hank);
    t.checkExpect(this.hank.buddies, this.edBuddy);
    this.mt.addTo(this.ed);
    t.checkExpect(this.ed.buddies, this.edBuddy);
  }

  void testAnySameName(Tester t) {
    this.initBuddies();
    t.checkExpect(this.mt.anySameName("d"), false);
    t.checkExpect(this.annBuddy.anySameName("Bob"), true);
  }

  void testCountIntersect(Tester t) {
    this.initBuddies();
    t.checkExpect(this.mt.countIntersect(bobBuddy), 0);
    t.checkExpect(this.annBuddy.countIntersect(this.annBuddy), 2);
  }

  void testAnySamePerson(Tester t) {
    this.initBuddies();
    t.checkExpect(this.mt.anySamePerson(this.cole), false);
    t.checkExpect(this.coleBuddy.anySamePerson(this.dan), true);
  }

  void testSize(Tester t) {
    this.initBuddies();
    t.checkExpect(this.mt.size(), 0);
    t.checkExpect(this.annBuddy.size(), 2);
  }

  void testInviteAll(Tester t) {
    this.initBuddies();
    t.checkExpect(this.mt.inviteAll(this.fayBuddy), this.fayBuddy);
    t.checkExpect(this.hank.buddies.inviteAll(this.mt), this.mt);
  }

  void testMaxLikelihood(Tester t) {
    this.initBuddies();
    t.checkInexact(this.a.maxLikelihood(this.d), 0.7716375, 0.00001);
    t.checkInexact(this.a.maxLikelihood(this.b), 0.9405, 0.00001);
  }

  void testLikelihood(Tester t) {
    this.initBuddies();
    t.checkInexact(this.mtLoDouble.likelihood(), 1.0, 0.00001);
    t.checkInexact(this.LoDouble.likelihood(), 0.25, 0.00001);
  }

  void testMaxLikelihoodAcc(Tester t) {
    this.initBuddies();
    t.checkExpect(this.a.maxLikelihoodAcc(-1, this.b, new ConsLoDouble(0.95, this.mtLoDouble),
        new ConsLoBuddy(this.a, this.mt)), 0.9405);
  }
}