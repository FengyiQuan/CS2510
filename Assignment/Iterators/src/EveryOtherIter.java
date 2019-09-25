import java.util.Iterator;

// Represents the subsequence of the first, third, fifth, etc. items from a given sequence
class EveryOtherIter<T> implements Iterator<T> {
  Iterator<T> source;

  EveryOtherIter(Iterator<T> source) {
    this.source = source;
  }

  public boolean hasNext() {
    // this sequence has a next item if the source does
    return this.source.hasNext();
  }

  public T next() {
    T answer = this.source.next(); // gets the answer, and advances the source
    // We need to have the source "skip" the next value
    if (this.source.hasNext()) {
      this.source.next(); // get the next value and ignore it
    }
    return answer;
  }

  public void remove() {
    // We can remove an item if our source can remove the item
    this.source.remove(); // so just delegate to the source
  }
}