interface IPred<T> extends IFunc<T, Boolean> {
  Boolean apply(T t);
}

interface IFunc<X, Y> {
  Y apply(X x);
}

interface Comparator<T> {
  boolean apply(T x, T y);
}

interface IListVisitor<X, Y> extends IFunc<IList<X>, Y> {
  Y visitMt(MtList<X> mt);

  Y visitCons(ConsList<X> cons);
}

class OrPredicate<T> implements IPred<T> {
  IList<IPred<T>> preds;

  OrPredicate(IList<IPred<T>> preds) {
    this.preds = preds;
  }

  public Boolean apply(T t) {
    return preds.accept(new AnyReturnTrue<T>(t));
  }
}

class AnyReturnTrue<T> implements IListVisitor<IPred<T>, Boolean> {
  T t;

  AnyReturnTrue(T t) {
    this.t = t;
  }

  public Boolean apply(IList<IPred<T>> x) {
    return x.accept(this);
  }

  public Boolean visitMt(MtList<IPred<T>> mt) {
    return true;
  }

  public Boolean visitCons(ConsList<IPred<T>> cons) {
    return cons.first.apply(t) || cons.rest.accept(this);
  }

}
