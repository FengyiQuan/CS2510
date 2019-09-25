import java.util.Iterator;

class IListIterator<T> implements Iterator<T> {
  IList<T> items;

  IListIterator(IList<T> items) {
    this.items = items;
  }

  public boolean hasNext() {
    return this.items.isCons();
  }

  public T next() {
    ConsList<T> itemsAsCons = this.items.asCons();
    T answer = itemsAsCons.first;
    this.items = itemsAsCons.rest;
    return answer;
  }

  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}

interface IList<T> extends Iterable<T> {
  boolean isCons();

  ConsList<T> asCons();
}

class MtList<T> implements IList<T> {

  public boolean isCons() {
    return false;
  }

  public ConsList<T> asCons() {
    throw new ClassCastException("Can't do this!");
  }

  public Iterator<T> iterator() {
    return new IListIterator<T>(this);
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T f, IList<T> r) {
    this.first = f;
    this.rest = r;
  }

  public boolean isCons() {
    return true;
  }

  public ConsList<T> asCons() {
    return this;
  }

  public Iterator<T> iterator() {
    return new IListIterator<T>(this);
  }
}