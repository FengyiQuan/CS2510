import tester.Tester;

// An updatable object that has some state of type X
// that can be updated
interface Updatable<X> {
  // get the current state
  X currentState();

  // update to the next state
  void nextState();
}

// Functional object
interface IFunc<X, Y> {
  Y apply(X x);
}

class Helper implements IFunc<Integer, Integer> {
  int num;

  Helper(int num) {
    this.num = num;
  }

  public Integer apply(Integer x) {
    return x + this.num;
  }

}

// produces function objects that adds 0, then 1, then 2... etc.
class Adders implements Updatable<IFunc<Integer, Integer>> {
  int num;

  Adders(int num) {
    this.num = num;
  }

  // initial state: produces function objects that add 0
  Adders() {
    this.num = 0;
  }

  public IFunc<Integer, Integer> currentState() {
    return new Helper(num);
  }

  public void nextState() {
    this.num = this.num + 1;
  }
}

class ExamplesAdder {
  Updatable<IFunc<Integer, Integer>> adders;

  // set adders to have a current state of outputting functions that add 0
  void initializeData() {
    this.adders = new Adders(0);
  }

  void testAdders(Tester t) {
    this.initializeData();
    t.checkExpect(adders.currentState().apply(50), 50);
    adders.nextState();
    t.checkExpect(adders.currentState().apply(11), 12);
    adders.nextState();
    t.checkExpect(adders.currentState().apply(400), 402);
    adders.nextState();
    t.checkExpect(adders.currentState().apply(16), 19);
  }
}