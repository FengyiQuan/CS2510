import tester.Tester;

class Deque<T> {
  Sentinel<T> header;

  Deque(Sentinel<T> header) {
    this.header = header;
  }

  Deque() {
    this.header = new Sentinel<T>();
  }

  // counts the size of this deque
  int size() {
    if (header.next.equals(this.header)) {
      return 0;
    }

    else {
      return this.header.next.counter(this.header);
    }

  }

  // EFFECT: consumes a value of type T and inserts it at the front of the list
  void addAtHead(T t) {
    this.header.adder(t);
  }

  // EFFECT: consumes a value of type T and inserts it at the end of the list
  void addAtTail(T t) {
    this.header.tailer(t);
  }

  // EFFECT: remove the first element from a Deque in the head
  // and return the removed item
  T removeFromHead() {
    return this.header.removeHead();
  }

  // EFFECT: remove the first element from a Deque in the tail
  // and return the removed item
  T removeFromTail() {
    return this.header.removeTail();
  }

  // find the node for the given prediction
  // return the sentinel otherwise
  ANode<T> find(IPred<T> pred) {
    return this.header.finder(pred);
  }

  // EFFECT: remove the given node from a deque
  void removeNode(ANode<T> n) {
    n.remover();
  }
}

abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  public int counter(ANode<T> head) {
    if (this.next.equals(head)) {
      return 1;
    } else {
      return 1 + this.next.counter(head);
    }
  }

  // remove the item from first and return removed one
  abstract T remover();

  // find the required one first occurs
  abstract ANode<T> finderHelp(IPred<T> pred);
}

class Sentinel<T> extends ANode<T> {
  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  // add the last term by modify the sentinel
  public void tailer(T t) {
    new Node<T>(t, this, this.prev);
  }

  // add the first term by modify the sentinel
  public void adder(T t) {
    new Node<T>(t, this.next, this);

  }

  // find the Anode based on the pred given
  public ANode<T> finder(IPred<T> pred) {
    return this.next.finderHelp(pred);
  }

  // delegate to the ANode, remove the node from deque
  public T removeHead() {
    return this.next.remover();
  }

  // delegate to the ANode, remove the node from deque
  public T removeTail() {
    return this.prev.remover();
  }

  // can't remove the sentinel
  T remover() {
    throw new RuntimeException("Can't remove the sentinel");
  }

  // return the sentinel if tried to remove this
  ANode<T> finderHelp(IPred<T> pred) {
    return this;
  }
}

class Node<T> extends ANode<T> {
  T data;

  Node(T t) {
    this.data = t;
    this.next = null;
    this.prev = null;
  }

  Node(T t, ANode<T> next, ANode<T> prev) {
    this.data = t;
    this.prev = prev;
    this.next = next;
    if ((prev == null) || (next == null)) {
      throw new IllegalArgumentException("Can't modify the null node");
    } else {

      this.next.prev = this;
      this.prev.next = this;

    }

  }

  // relocate the link to remove the node from first
  T remover() {
    prev.next = this.next;
    next.prev = this.prev;
    return this.data;
  }

  // keep visiting till find the one that satisfy the pred
  ANode<T> finderHelp(IPred<T> pred) {
    if (pred.apply(this.data)) {
      return this;
    } else {
      return this.next.finderHelp(pred);
    }
  }

}

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

class ExamplesDeque {
  Deque<String> deque1;
  Deque<String> deque2;
  Deque<String> deque3;
  Sentinel<String> s1;
  Sentinel<String> s2;
  Sentinel<String> s3;
  Node<String> n1;
  Node<String> n2;
  Node<String> n3;
  Node<String> n4;
  Node<String> n5;
  Node<String> n6;
  Node<String> n7;
  Node<String> n8;
  Node<String> n9;
  Node<String> n10;

  void iniSDeque() {
    s1 = new Sentinel<String>();
    deque1 = new Deque<String>(this.s1);
    s2 = new Sentinel<String>();
    n1 = new Node<String>("abc", this.s2, this.s2);
    n2 = new Node<String>("bcd", this.s2, this.n1);
    n3 = new Node<String>("cde", this.s2, this.n2);
    n4 = new Node<String>("def", this.s2, this.n3);
    deque2 = new Deque<String>(this.s2);
    s3 = new Sentinel<String>();
    n5 = new Node<String>("5", this.s3, this.s3);
    n6 = new Node<String>("6", this.s3, this.n5);
    n7 = new Node<String>("7", this.s3, this.n6);
    n8 = new Node<String>("8", this.s3, this.n7);
    n9 = new Node<String>("9", this.s3, this.n8);
    n10 = new Node<String>("10", this.s3, this.n9);
    deque3 = new Deque<String>(this.s3);
  }

  void testConstructor(Tester t) {
    t.checkConstructorException(new IllegalArgumentException("Can't modify the null node"), "Node",
        "C", null, new Node<String>("B"));
  }

  void testInite(Tester t) {
    this.iniSDeque();
    t.checkExpect(this.deque1.header.next, this.s1);
    t.checkExpect(this.n1.next, this.n2);
    t.checkExpect(this.n1.prev, this.s2);
    t.checkExpect(this.s2.prev, this.n4);
    t.checkExpect(this.n10.data, "10");
  }

  void testSize(Tester t) {
    this.iniSDeque();
    t.checkExpect(this.deque2.size(), 4);
    t.checkExpect(this.deque1.size(), 0);
    t.checkExpect(this.deque3.size(), 6);
  }

  void testAddAtHead(Tester t) {
    this.iniSDeque();
    t.checkExpect(this.deque1.header.next, this.s1);
    this.deque1.addAtHead("1");
    t.checkExpect(this.deque1.header.next, new Node<String>("1", this.s1, this.s1));
    this.iniSDeque();
    t.checkExpect(this.s2.next, this.n1);
    this.deque2.addAtHead("1");
    t.checkExpect(this.s2.next, new Node<String>("1", this.n1, this.s2));
  }

  void testAddAtTail(Tester t) {
    this.iniSDeque();
    t.checkExpect(this.deque1.header.next, this.s1);
    this.deque1.addAtTail("1");
    t.checkExpect(this.deque1.header.prev, new Node<String>("1", this.s1, this.s1));
    t.checkExpect(this.s2.prev, this.n4);
    this.deque2.addAtTail("1");
    t.checkExpect(this.s2.prev, new Node<String>("1", this.s2, this.n4));
  }

  void testRemoveHead(Tester t) {
    this.iniSDeque();
    t.checkException(new RuntimeException("Can't remove the sentinel"), deque1, "removeFromHead");
    this.iniSDeque();
    t.checkExpect(this.n2.prev, this.n1);
    t.checkExpect(this.deque2.removeFromHead(), "abc");
    t.checkExpect(this.n2.prev, this.s2);
    t.checkExpect(this.s2.next, this.n2);
  }

  void testRemoveTail(Tester t) {
    this.iniSDeque();
    t.checkException(new RuntimeException("Can't remove the sentinel"), deque1, "removeFromTail");
    t.checkExpect(this.s2.prev, this.n4);
    t.checkExpect(this.deque2.removeFromTail(), "def");
    t.checkExpect(this.s2.prev, this.n3);
  }

  void testFind(Tester t) {
    this.iniSDeque();
    t.checkExpect(this.deque1.find(new SameAs("1")), this.s1);
    t.checkExpect(this.deque2.find(new SameAs("def")), this.n4);
    t.checkExpect(this.deque3.find(new SameAs("7")), this.n7);
  }

  void testRemove(Tester t) {
    this.iniSDeque();
    t.checkExpect(this.n1.next, this.n2);
    this.deque2.removeNode(this.n2);
    t.checkExpect(this.n1.next, this.n3);

  }

}
