import tester.*;

// The setup

// Let's once and for all, do the dispatching, 
// for all kinds methods on shapes
// That return all manner of types.
// Uh oh--union data. Dun dun dun ....

interface IShape { 
  // we needed to add this one method 
  // for calling an IShape dispatch function
  <R> R callIShapeDispF(IShapeDispF<R> df); 
  
} 

class Rect implements IShape { 
  int h = 5;
  int w = 10;
  // some constructor;
  
  // This is where we link things up properly. 
  // We use the dispatch for the Rect in the Rect class ..
  public <R> R callIShapeDispF(IShapeDispF<R> df) {
    return df.forRect(this);
  }
  
}

class Circ implements IShape { 
  int rad = 1;
  // some constructor

  @Override
  public <R> R callIShapeDispF(IShapeDispF<R> df) {
    return df.forCirc(this);
  }
  

}

// End Setup

interface IFunc<X, Y> {
  Y call(X x);
}

interface IShapeDispF<R> extends IFunc<IShape, R> {
 // for every flavor of Shape
 R forCirc(Circ c);
 R forRect(Rect r);
 // we still get that `call` method from IFunc
 
}

class Area implements IShapeDispF<Double> { 
  
  // This is copy-paste-y
  public Double call(IShape x) {
    return x.callIShapeDispF(this);
  }

  @Override
  public Double forCirc(Circ c) {
    // and you only need to implement ...
  }

  @Override
  public Double forRect(Rect r) {
    // and you only need to implement ...
  }

}

class Perimeter implements IShapeDispF<Double> {

  @Override
  public Double call(IShape x) {
    return x.callIShapeDispF(this);
  }

  @Override
  public Double forCirc(Circ c) {
    return c.rad * 3.14 * 2;
  }

  @Override
  public Double forRect(Rect r) {
    return 2.0 * (r.w + r.h);
  } 
  
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
  ILo<Double> shapePers = new MtLo<IShape>().map(new Perimeter());

}




