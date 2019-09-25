import java.util.ArrayList;

interface IPred<X> {
  boolean apply(X x);
}

// determine if any bomb revealed in a list of cell
class AnyBombReveal implements IPred<ArrayList<Cell>> {

  // determine if any bomb revealed in a list of cell
  public boolean apply(ArrayList<Cell> x) {
    return new Utils().ormap(x, new AnyBombRevealInOneRow());
  }
}

//determine if a bomb revealed
class AnyBombRevealInOneRow implements IPred<Cell> {
  // determine if a bomb revealed
  public boolean apply(Cell x) {
    if (x.mine) {
      return x.reveal;
    }
    else {
      return false;
    }
  }
}

// determine if all safe cell revealed in a list of cell
class AllSafeReveal implements IPred<ArrayList<Cell>> {
  // determine if all safe cell revealed in a list of cell
  public boolean apply(ArrayList<Cell> x) {
    return new Utils().andmap(x, new AllSafeRevealInOneRow());
  }
}

// determine if a cell revealed 
class AllSafeRevealInOneRow implements IPred<Cell> {
  // determine if a cell revealed
  public boolean apply(Cell x) {
    if (!x.mine) {
      return x.reveal;
    }
    else {
      return true;
    }
  }
}

interface IFunc2<X, Y, Z> {
  Z apply(X x, Y y);
}

// counts the number of bombs in a list 
class Bomb implements IFunc2<Cell, Integer, Integer> {
  // counts the number of bombs in a list
  public Integer apply(Cell x, Integer y) {
    if (x.isBomb()) {
      return 1 + y;
    }
    else {
      return y;
    }
  }
}