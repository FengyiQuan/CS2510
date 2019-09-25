import tester.Tester;

// QUIZ: Finish implementing majorityTrue().
// You do not have to write any more examples
// and tests, even if you design helper methods,
// nor do you have to write any templates.

interface ILoBool {
  // are the majority (> half) of the booleans in this list true?
  boolean majorityTrue();

  int trueNum();

  int total();
}

class MtLoBool implements ILoBool {

  // are the majority (> half) of the booleans in this empty list true?
  public boolean majorityTrue() {
    return false;
  }

  @Override
  public int trueNum() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int total() {
    // TODO Auto-generated method stub
    return 0;
  }
}

class ConsLoBool implements ILoBool {
  boolean first;
  ILoBool rest;

  ConsLoBool(boolean first, ILoBool rest) {
    this.first = first;
    this.rest = rest;
  }

  // are the majority (> half) of the booleans in this empty list true?
  public boolean majorityTrue() {
    return this.trueNum() > this.total() / 2;
  }

  @Override
  public int trueNum() {
    // TODO Auto-generated method stub
    if (this.first) {
      return 1 + this.rest.trueNum();
    }
    else {
      return this.rest.trueNum();
    }
  }

  @Override
  public int total() {
    // TODO Auto-generated method stub
    return 1 + this.rest.total();
  }
}

class ExamplesILoBool {
  ILoBool mt = new MtLoBool();
  ILoBool tru = new ConsLoBool(true, mt);
  ILoBool falsTru = new ConsLoBool(false, tru);
  ILoBool truFalsTru = new ConsLoBool(true, falsTru);

  boolean testMajorityTrue(Tester t) {
    return t.checkExpect(mt.majorityTrue(), false) && t.checkExpect(tru.majorityTrue(), true)
        && t.checkExpect(falsTru.majorityTrue(), false)
        && t.checkExpect(truFalsTru.majorityTrue(), true);
  }
}