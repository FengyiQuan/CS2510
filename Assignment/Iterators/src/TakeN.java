import java.util.Iterator;

class TakeN<T> implements Iterator<T> {
  Iterator<T> source;
  int howMany;
  int countSoFar;

  TakeN(Iterator<T> source, int n) {
    this.source = source;
    this.howMany = n;
    this.countSoFar = 0;
  }

  public boolean hasNext() {
    return (this.countSoFar < this.howMany) && this.source.hasNext();
  }

  public T next() {
    this.countSoFar = this.countSoFar + 1;
    return this.source.next();
  }

}

class GetN<T> implements Iterator<T>, Iterable<T> {
  int n;
  Iterator<T> iter;

  GetN(int n, Iterator<T> iter) {
    this.n = n;
    this.iter = iter;
  }

  public Iterator<T> iterator() {
    return this;
  }

  public boolean hasNext() {
    return iter.hasNext() && n > 0;
  }

  public T next() {
    T result = iter.next();
    n = n - 1;
    return result;
  }

}