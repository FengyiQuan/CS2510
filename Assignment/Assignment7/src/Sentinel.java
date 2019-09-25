class Sentinel<T> extends ANode<T> {

  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  // count the number of nodes in the sentinel
  int sizeHelper() {
    return 0;
  }

  // EFFECT: add note at the head of sentinel
  void addAtHead(T t) {
    new Node<T>(t, this.next, this);
  }

  void addAtTail(T t) {
    new Node<T>(t, this, this.prev);
  }

  // EFFECT: remove the Head of the node from the sentinel
  T removeFromHead() {
    return this.next.removeHelper();
  }

  // EFFECT: remove the tail of the node from the sentinel
  T removeFromTail() {
    return this.prev.removeHelper();
  }

  // EFFECT: remove the node from A Sentinel
  T removeHelper() {
    throw new RuntimeException("Can't try to remove on a Sentinel!");
  }

  // takes an IPred<T> and produces the first node
  // in this Sentinel for which the given predicate returns true
  ANode<T> find(IPred<T> pred) {
    return this.next.findHelper(pred);
  }

  // takes an IPred<T> and produces the first node
  // in this Sentinel for which the given predicate returns true
  ANode<T> findHelper(IPred<T> pred) {
    return this;
  }

  // remove the given node in Sentinel class
  void removeNodeHelper() {
    throw new RuntimeException("Can't remove the sentinel");
  }
}