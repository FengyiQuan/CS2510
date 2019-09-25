interface IPred<T> {
  boolean apply(T t);
}

interface IFunc<T, U> {
  U apply(T t);
}
