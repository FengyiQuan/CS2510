import tester.Tester;

interface ILoInt {
  ILoInt removeLast();

  ILoInt removeLastAcc(ILoInt listSofar);

  boolean isItLast();
}

class MtLoInt implements ILoInt {

  public ILoInt removeLast() {
    return new MtLoInt();
  }

  public ILoInt removeLastAcc(ILoInt listSofar) {
    return listSofar;
  }

  public boolean isItLast() {
    return true;
  }
}

class ConsLoInt implements ILoInt {
  int first;
  ILoInt rest;

  ConsLoInt(int first, ILoInt rest) {
    this.first = first;
    this.rest = rest;
  }

  public ILoInt removeLast() {
    return this.removeLastAcc(new MtLoInt());
  }

  public ILoInt removeLastAcc(ILoInt listSofar) {
    if (this.rest.isItLast()) {
      return listSofar;
    }
    else {
      return this.rest.removeLastAcc(new ConsLoInt(this.first, listSofar));
    }
  }

  public boolean isItLast() {
    return false;
  }
}

class Examples {
  ILoInt mt = new MtLoInt();
  ILoInt one = new ConsLoInt(1, this.mt);
  ILoInt twoOne = new ConsLoInt(2, this.one);

  // test
  boolean testRemoveLast(Tester t) {
    return t.checkExpect(mt.removeLast(), this.mt) && t.checkExpect(this.one.removeLast(), mt)
        && t.checkExpect(this.twoOne.removeLast(), new ConsLoInt(2, this.mt));
  }
}
