class Deque<T> {
  Sentinel<T> header;

  Deque() {
    this.header = new Sentinel<T>();
  }

  Deque(Sentinel<T> sent) {
    this.header = sent;
  }

  // count the number of nodes in a list Deque
  int size() {
    return this.header.sizeHelper();
  }

  // EFFECT: insert T to the front of the list
  void addAtHead(T t) {
    this.header.addAtHead(t);
  }

  // EFFECT: insert T to the tail of the list
  void addAtTail(T t) {
    this.header.addAtTail(t);
  }

  // EFFECT: remove the first node from this Deque
  T removeFromHead() {
    return this.header.removeFromHead();
  }

  // EFFECT: remove the last node from this Deque
  T removeFromTail() {
    return this.header.removeFromTail();
  }

  // takes an IPred<T> and produces the first node
  // in this Deque for which the given predicate returns true
  ANode<T> find(IPred<T> pred) {
    return this.header.find(pred);
  }

  // remove the given node from this Deque
  void removeNode(ANode<T> n) {
    n.removeNodeHelper();
  }
}
