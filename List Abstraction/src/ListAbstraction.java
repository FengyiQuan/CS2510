interface IPred<T> {
  // asks a question about T
  boolean apply(T t);
}

interface IFunc<X, Y> {
  Y apply(X x);
}

interface IFunc2<X, Y, Z> {
  Z apply(X x, Y y);
}

// Generic classes: implementing lists
interface IList<T> {
  // filters this IList by the given predicate
  IList<T> filter(IPred<T> pred);

  // maps a function onto every member of the list
  <Y> IList<Y> map(IFunc<T, Y> fun);

  // determine is there any member of the list satisfying by the given predicate
  boolean ormap(IPred<T> pred);

  // determine every member of the list satisfying by the given predicate
  boolean andmap(IPred<T> pred);

  // combines the items in this IList according to the given function
  <Y> Y foldr(IFunc2<T, Y, Y> fun, Y base);

  <Y> Y foldl(IFunc2<T, Y, Y> fun, Y base);

  // find a item in a list according to given IPred
  T find(IPred<T> whichOne);

  // Effect: changes an item that matches the predicate in the list according to
  // the given function
  Void changeItem(IPred<T> pred, IFunc<T, Void> fun);

  // append two IList
  IList<T> append(IList<T> other);

  // get a reversed list of this IList
  IList<T> reverse();
}

class MtList<T> implements IList<T> {

  public IList<T> filter(IPred<T> pred) {
    return this;
  }

  public <Y> IList<Y> map(IFunc<T, Y> fun) {
    return new MtList<Y>();
  }

  public boolean ormap(IPred<T> pred) {
    return false;
  }

  public boolean andmap(IPred<T> pred) {
    return true;
  }

  public <Y> Y foldr(IFunc2<T, Y, Y> fun, Y base) {
    return base;
  }

  public <Y> Y foldl(IFunc2<T, Y, Y> fun, Y base) {
    return base;
  }

  public T find(IPred<T> whichOne) {
    return null;
  }

  public Void changeItem(IPred<T> pred, IFunc<T, Void> fun) {
    return null;

  }

  public IList<T> append(IList<T> other) {
    return other;
  }

  public IList<T> reverse() {
    return this;
  }

}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public IList<T> filter(IPred<T> pred) {
    if (pred.apply(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  public <Y> IList<Y> map(IFunc<T, Y> fun) {
    return new ConsList<Y>(fun.apply(this.first), this.rest.map(fun));
  }

  public boolean ormap(IPred<T> pred) {
    return pred.apply(this.first) || this.rest.ormap(pred);
  }

  public boolean andmap(IPred<T> pred) {
    return pred.apply(this.first) && this.rest.andmap(pred);
  }

  public <Y> Y foldr(IFunc2<T, Y, Y> fun, Y base) {
    return fun.apply(this.first, this.rest.foldr(fun, base));
  }

  public <Y> Y foldl(IFunc2<T, Y, Y> fun, Y base) {
    return this.rest.foldl(fun, fun.apply(this.first, base));
  }

  public T find(IPred<T> whichOne) {
    if (whichOne.apply(this.first)) {
      return this.first;
    }
    else {
      return this.rest.find(whichOne);
    }
  }

  public Void changeItem(IPred<T> pred, IFunc<T, Void> fun) {
    if (pred.apply(this.first)) {
      return fun.apply(this.first);
    }
    else {
      return this.rest.changeItem(pred, fun);
    }
  }

  public IList<T> append(IList<T> other) {
    return new ConsList<T>(this.first, this.rest.append(other));
  }

  public IList<T> reverse() {
    return this.rest.reverse().append(new ConsList<T>(this.first, new MtList<T>()));
  }
}
