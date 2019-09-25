import tester.*;

interface IFunc<X, Y> {
  Y call(X x);
}

// Because a predicate is a specialized variety of function
interface IPred<X> extends IFunc<X, Boolean>{ }

interface IRed<X, Y> {
  Y red(X x, Y y);
}

class Filter<X> implements IRed<X, ILo<X>> {
  IPred<X> pred;

  Filter(IPred<X> pred) {
    this.pred = pred;
  }

  public ILo<X> red(X x, ILo<X> y) {
    if (this.pred.call(x)) {
      return new ConsLo<X>(x,y);
    }
    else {
      return y;
    }
  } 
}

class Map<X,Y> implements IRed<X, ILo<Y>> {
  IFunc<X,Y> func;
  
  Map(IFunc<X, Y> func) { 
    this.func = func;
  }
  public ILo<Y> red(X x, ILo<Y> y) {
    return new ConsLo<Y> (this.func.call(x), y);
  } 
}

class StrLen implements IFunc<String, Integer> {
  public Integer call(String x) {
    return x.length();
  }
}

class NonZebra implements IPred<String> {
  
  public Boolean call(String t) {
    return !(t.equals("zebra"));
  } 
}

interface ILo<T> {
  <Y> Y foldr(IRed<T, Y> red, Y base);
  // Notice the filter & map methods on lists are now redundant.
  <Y> ILo<Y> map(IFunc<T, Y> fun);
  ILo<T> filter(IPred<T> pred); 
  
}

class MtLo<T> implements ILo<T> {

  public ILo<T> filter(IPred<T> pred) {
    return this.foldr(new Filter<T>(pred), new MtLo<T>());
  }
  
  public <Y> ILo<Y> map(IFunc<T, Y> fun) {
    return this.foldr(new Map<T, Y>(fun), new MtLo<Y>());
  }

  public <Y> Y foldr(IRed<T, Y> red, Y base) {
    return base;
  }

}

class ConsLo<T> implements ILo<T> {
  T first;
  ILo<T> rest;

  ConsLo(T first, ILo<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public ILo<T> filter(IPred<T> pred) {
    return this.foldr(new Filter<T>(pred), new MtLo<T>());
  }

  
  public <Y> ILo<Y> map(IFunc<T, Y> fun) {
    return this.foldr(new Map<T, Y>(fun), new MtLo<Y>());
  }

  public <Y> Y foldr(IRed<T, Y> red, Y base) {
    return red.red(this.first, this.rest.foldr(red, base));
  }
}

class ExamplesLectureLists {
  ILo<String> stringTest = new MtLo<String>();
  ILo<String> lotsOfStrings =
      new ConsLo<String>("hi" , 
          new ConsLo<String>("howdy" , 
              new ConsLo<String>("sayonara" , 
                  new ConsLo<String>("howdyDo", 
                      new ConsLo<String>("zebra" , 
                          this.stringTest)))));
  ILo<String> withoutZebs = this.lotsOfStrings.filter(new NonZebra());
  ILo<Integer> mtnums = new MtLo<Integer>();
  ILo<Integer> ints = 
      new ConsLo<Integer>(10 , 
          new ConsLo<Integer>(17 , 
              new ConsLo<Integer>(87 , 
                  new ConsLo<Integer>(12 , 
                      new ConsLo<Integer>(4 , this.mtnums)))));
  ILo<Integer> lens = this.lotsOfStrings.map(new StrLen());
  ILo<String> noZebs2 = this.lotsOfStrings.foldr(new Filter<String>(new NonZebra()), 
      new MtLo<String>());

}




