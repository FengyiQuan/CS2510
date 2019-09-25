//Represents a boolean-valued question over values of type T
interface IPred<T> {
  boolean apply(T t);
}

class SameAs implements IPred<String> {
  String s;

  SameAs(String s) {
    this.s = s;
  }

  public boolean apply(String t) {
    return this.s.equals(t);
  }
}