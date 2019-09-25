// Represents functions of signature A -> R, for some argument type A and
// result type R
interface IFunc<A, R> {
  R apply(A input);
}

//Visitor Pattern
interface IListVisitor<T, R> extends IFunc<IList<T>, R> {
  R visitMt(MtList<T> mt);

  R visitCons(ConsList<T> cons);

}

interface IFunc2<X, Y, R> {
  R apply(X x, Y y);
}

class OneToInt implements IFunc2<JSON, Integer, Integer> {

  public Integer apply(JSON x, Integer y) {
    return new JSONToNumber().apply(x) + y;
  }

}

// generic list
interface IList<T> {
  // map over a list, and produce a new list with a (possibly different)
  // element type
  <U> IList<U> map(IFunc<T, U> f);

  <U> U foldr(IFunc2<T, U, U> f, U base);

  <R> R accept(IListVisitor<T, R> visitor);
}

// empty generic list
class MtList<T> implements IList<T> {
  public <U> IList<U> map(IFunc<T, U> f) {
    return new MtList<U>();
  }

  public <U> U foldr(IFunc2<T, U, U> f, U base) {
    return base;
  }

  public <R> R accept(IListVisitor<T, R> visitor) {
    return visitor.visitMt(this);
  }
}

// non-empty generic list
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public <U> IList<U> map(IFunc<T, U> f) {
    return new ConsList<U>(f.apply(this.first), this.rest.map(f));
  }

  public <U> U foldr(IFunc2<T, U, U> f, U base) {
    return f.apply(this.first, this.rest.foldr(f, base));
  }

  public <R> R accept(IListVisitor<T, R> visitor) {
    return visitor.visitCons(this);
  }

}