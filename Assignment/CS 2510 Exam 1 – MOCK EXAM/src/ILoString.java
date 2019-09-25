import tester.Tester;

interface ILoString {
  // takes a single ILoString and produces a pair of lists
  PairOfLists unzip();

  ILoString getFirst();

  ILoString getSecond();

  ILoString First();

  ILoString Second();

  String getTwo();
}

class MtLoString implements ILoString {

  public PairOfLists unzip() {
    return new PairOfLists(this, this);
  }

  public ILoString getFirst() {
    return this;
  }

  public ILoString getSecond() {
    return this;
  }

  public ILoString First() {
    return this;
  }

  public ILoString Second() {
    return this;
  }

  @Override
  public String getTwo() {
    // TODO Auto-generated method stub
    return "";
  }
}

class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  public PairOfLists unzip() {
    return new PairOfLists(this.getFirst(), this.getSecond());
  }

  public ILoString getFirst() {
    return new ConsLoString(this.first, this.rest.First());
  }

  public ILoString getSecond() {
    return new ConsLoString(this.rest.getTwo(), this.rest.Second());
  }

  public ILoString First() {
    return this.rest.getFirst();
  }

  public ILoString Second() {
    return this.rest.getSecond();
  }

  public String getTwo() {
    return this.first;
  }
}

//Represents a pair of lists of strings
class PairOfLists {
  ILoString first, second;

  PairOfLists(ILoString first, ILoString second) {
    this.first = first;
    this.second = second;
  }

  // Produces a new pair of lists, with the given String added to the front of the
  // first list of this pair
  PairOfLists addToFirst(String first) {
    return new PairOfLists(new ConsLoString(first, this.first), second);
  }

  // Produces a new pair of lists, with the given String added to the front of the
  // second list of this pair
  PairOfLists addToSecond(String second) {
    return new PairOfLists(first, new ConsLoString(second, this.second));
  }
}

class ExamplesString {
  ILoString t = new ConsLoString("a",
      new ConsLoString("b", new ConsLoString("c", new ConsLoString("d", new MtLoString()))));

  boolean testUnzip(Tester t) {
    return t.checkExpect(this.t.unzip(),
        new PairOfLists(new ConsLoString("a", new ConsLoString("c", new MtLoString())),
            new ConsLoString("b", new ConsLoString("d", new MtLoString()))));
  }
}