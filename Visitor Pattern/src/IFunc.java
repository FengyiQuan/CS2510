
interface IFunc<X, Y> {
  Y apply(X arg);
}

interface IPred<X> extends IFunc<X, Boolean> {

}

interface IFunc2<X, Y, Z> {
  Z apply(X x, Y y);
}