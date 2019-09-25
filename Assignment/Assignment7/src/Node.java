class Node<T> extends ANode<T> {
  T data;

  Node(T t) {
    this.data = t;
    this.next = null;
    this.prev = null;
  }

  Node(T t, ANode<T> next, ANode<T> prev) {
    this.data = t;
    this.next = next;
    this.prev = prev;

    if (next == null || prev == null) {
      throw new IllegalArgumentException("Can't modify the null node.");
    }
    else {
      // sentinel
      next.prev = this;
      prev.next = this;
    }

  }

  // count the number of nodes in the Node
  int sizeHelper() {
    return 1 + this.next.sizeHelper();
  }

  // EFFECT: remove the node from Node
  T removeHelper() {
    prev.next = this.next;
    next.prev = this.prev;
    return this.data;
  }

  // takes an IPred<T> and produces the first node
  // in this Node for which the given predicate returns true
  ANode<T> findHelper(IPred<T> pred) {
    if (pred.apply(this.data)) {
      return this;
    }
    else {
      return this.next.findHelper(pred);
    }
  }

  // remove the given node in Node class
  void removeNodeHelper() {
    this.removeHelper();
  }

}