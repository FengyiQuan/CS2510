import tester.Tester;

interface IList<T> {
  // Visitor Pattern
  <R> R accept(IListVisitor<T, R> visitor);

  <Y> Y foldr(IFunc2<T, Y> red, Y base);

  // Notice the filter & map methods on lists are now redundant.
  <Y> IList<Y> map(IFunc<T, Y> fun);

  IList<T> filter(IPred<T> pred);
  
  boolean ormap(IFunc2<T, Boolean> critria);
}

class MtList<T> implements IList<T> {
  public <R> R accept(IListVisitor<T, R> visitor) {
    return visitor.visitMt(this);
  }

  public IList<T> filter(IPred<T> pred) {
    return this.foldr(new Filter<T>(pred), new MtList<T>());
  }

  public <Y> IList<Y> map(IFunc<T, Y> fun) {
    return this.foldr(new Map<T, Y>(fun), new MtList<Y>());
  }

  public <Y> Y foldr(IFunc2<T, Y> red, Y base) {
    return base;
  }

}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T f, IList<T> r) {
    this.first = f;
    this.rest = r;
  }

  public <R> R accept(IListVisitor<T, R> visitor) {
    return visitor.visitCons(this);
  }

  public IList<T> filter(IPred<T> pred) {
    return this.foldr(new Filter<T>(pred), new MtList<T>());
  }

  public <Y> IList<Y> map(IFunc<T, Y> fun) {
    return this.foldr(new Map<T, Y>(fun), new MtList<Y>());
  }

  public <Y> Y foldr(IFunc2<T, Y> red, Y base) {
    return red.red(this.first, this.rest.foldr(red, base));
  }
}

interface IFunc<T, R> {
  R apply(T t);
}

interface IPred<T> extends IFunc<T, Boolean> {
}

// Visitor Pattern
interface IListVisitor<T, R> extends IFunc<IList<T>, R> {
  R visitMt(MtList<T> mt);

  R visitCons(ConsList<T> cons);

}

//////////////////////////////////////////////////////////////////////
interface IFunc2<X, Y> {
  Y red(X x, Y y);
}

class Filter<X> implements IFunc2<X, IList<X>> {
  IPred<X> pred;

  Filter(IPred<X> pred) {
    this.pred = pred;
  }

  public IList<X> red(X x, IList<X> y) {
    if (this.pred.apply(x)) {
      return new ConsList<X>(x, y);
    }
    else {
      return y;
    }
  }
}

class Map<X, Y> implements IFunc2<X, IList<Y>> {
  IFunc<X, Y> func;

  Map(IFunc<X, Y> func) {
    this.func = func;
  }

  public IList<Y> red(X x, IList<Y> y) {
    return new ConsList<Y>(this.func.apply(x), y);
  }
}

//????????????????
class OrMap<X> implements IFunc2<X, Boolean> {
  IPred<X> pred;

  OrMap(IPred<X> pred) {
    this.pred = pred;
  }

  public Boolean red(X x, Boolean y) {
    return (this.pred.apply(x)) || y;
  }
}

// filter function object
class Greater10 implements IPred<Integer> {
  public Boolean apply(Integer t) {
    return t > 10;
  }
}

class GreaterThan10 implements IListVisitor<Integer, IList<Integer>> {

  public IList<Integer> apply(IList<Integer> ilt) {
    return ilt.accept(this);
  }

  public IList<Integer> visitMt(MtList<Integer> mt) {
    return mt;
  }

  public IList<Integer> visitCons(ConsList<Integer> cons) {
    if (cons.first > 10) {
      return new ConsList<Integer>(cons.first, cons.rest.accept(this));
    }
    else {
      return cons.rest.accept(this);
    }
  }
}

class examples {
  IList<Integer> l = new ConsList<Integer>(15,
      new ConsList<Integer>(5, new ConsList<Integer>(10, new MtList<Integer>())));

  boolean testGreaterThan10(Tester t) {
    return t.checkExpect(this.l.accept(new GreaterThan10()),
        new ConsList<Integer>(15, new MtList<Integer>()));
  }

  boolean testFilter(Tester t) {
    return t.checkExpect(this.l.filter(new Greater10()),
        new ConsList<Integer>(15, new MtList<Integer>()));
  }
}