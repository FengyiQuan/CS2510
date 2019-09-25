interface IPred<T> {
  boolean apply(T t);
}

interface IFunc<T, U> {
  U apply(T t);
}

interface IFunc2<X, Y, Z> {
  Z apply(X x, Y y);
}