interface IList<T> {
  <R> R accept(IListVisitor<T, R> ilv);
}

class MtList<T> implements IList<T> {
  public <R> R accept(IListVisitor<T, R> ilv) {
    return ilv.visitMt(this);
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T f, IList<T> r) {
    this.first = f;
    this.rest = r;
  }
  public <R> R accept(IListVisitor<T, R> ilv) {
    return ilv.visitCons(this);
  }
}


