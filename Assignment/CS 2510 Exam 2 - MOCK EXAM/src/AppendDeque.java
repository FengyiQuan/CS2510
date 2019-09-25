class Deque<T>{
  Sentinel header;
  
  Deque(Sentinel h){
    this.header = h;
  }
  
  void append(Deque<T> other) {
    this.header.prev.next = t.header.next;
    t.header.next.prev =this.header.prev;
    this.header.prev = t.header.prev;
    t.header.prev.next = this.header;
  }
  
  void reverse() {
    this.header.next.reverseHelper();
  }
}

class Node<T>{
  void reverseHelper() {
    ANode<T> temp = this.prev;
    this.prev = this.next;
    this.next = temp;
    this.prev.reverseHelper();
  }
}

class Sentinel<T>{
  void reverseHelper() {
    ANode<T> temp = this.next;
    this.next = this.prev;
    this.prev = temp;
  }
}