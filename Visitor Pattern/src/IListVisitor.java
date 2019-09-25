interface IListVisitor<X, Y> extends IFunc<IList<X>, Y> {
  Y visitMt(MtList<X> mt);

  Y visitCons(ConsList<X> cons);
}

class Map<X, Y> implements IListVisitor<X, IList<Y>> {
  IFunc<X, Y> func;

  Map(IFunc<X, Y> func) {
    this.func = func;
  }

  public IList<Y> apply(IList<X> arg) {
    return arg.accept(this);
  }

  public IList<Y> visitMt(MtList<X> mt) {
    return new MtList<Y>();
  }

  public IList<Y> visitCons(ConsList<X> cons) {
    return new ConsList<Y>(this.func.apply(cons.first), cons.rest.accept(this));
  }
}

class Filter<X> implements IListVisitor<X, IList<X>> {
  IPred<X> pred;

  Filter(IPred<X> pred) {
    this.pred = pred;
  }

  public IList<X> apply(IList<X> arg) {
    return arg.accept(this);
  }

  public IList<X> visitMt(MtList<X> mt) {
    return mt;
  }

  public IList<X> visitCons(ConsList<X> cons) {
    if (this.pred.apply(cons.first)) {
      return new ConsList<X>(cons.first, cons.rest.accept(this));
    }
    else {
      return cons.rest.accept(this);
    }
  }
}

class AndMap<T> implements IListVisitor<T, Boolean> {
  IPred<T> pre;

  AndMap(IPred<T> pre) {
    this.pre = pre;
  }

  public Boolean apply(IList<T> arg) {
    return arg.accept(this);
  }

  public Boolean visitMt(MtList<T> mt) {
    return true;
  }

  public Boolean visitCons(ConsList<T> cons) {
    return this.pre.apply(cons.first) && cons.rest.accept(this);
  }
}

class OrMap<T> implements IListVisitor<T, Boolean> {
  IPred<T> pre;

  OrMap(IPred<T> pre) {
    this.pre = pre;
  }

  public Boolean apply(IList<T> arg) {
    return arg.accept(this);
  }

  public Boolean visitMt(MtList<T> mt) {
    return false;
  }

  public Boolean visitCons(ConsList<T> cons) {
    return this.pre.apply(cons.first) || cons.rest.accept(this);
  }
}
