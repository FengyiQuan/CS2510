import tester.Tester;

class allTrue implements IPred<Boolean>{

  public Boolean apply(Boolean arg) {
    return arg;
  }
  
}
class ex {
  IList<Boolean> l1 = new ConsList<Boolean>(true,
      new ConsList<Boolean>(true, new MtList<Boolean>()));
  IList<Boolean> l2 = new ConsList<Boolean>(true,
      new ConsList<Boolean>(false, new MtList<Boolean>()));
  IList<Boolean> l3 = new ConsList<Boolean>(false,
      new ConsList<Boolean>(false, new MtList<Boolean>()));

  void testOrMap(Tester t) {
    t.checkExpect(new OrMap<Boolean>(new allTrue()).apply(l1), true);
    t.checkExpect(new OrMap<Boolean>(new allTrue()).apply(l2), true);
    t.checkExpect(new OrMap<Boolean>(new allTrue()).apply(l3), false);
  }
}
