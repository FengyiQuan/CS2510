import java.util.Deque;
import java.util.Iterator;

class BreadthFirstIterator<T> implements Iterator<T> {
  Deque<IBinaryTree<T>> worklist;

  BreadthFirstIterator(IBinaryTree<T> source) {
    this.worklist = new Deque<IBinaryTree<T>>();
    this.addIfNotLeaf(source);
  }

  // EFFECT: only adds the given binary-tree if it's not a leaf
  void addIfNotLeaf(IBinaryTree bt) {
    if (bt.isNode()) {
      this.worklist.addAtTail(bt);
    }
  }
  
  public boolean hasNext() {
    // we have a next item if the worklist isn't empty
    return this.worklist.size() > 0;
  }

  public T next() {
    // Get (and remove) the first item on the worklist --
    // and we know it must be a BTNode
    BTNode<T> node = this.worklist.removeAtHead().asNode();
    // Add the children of the node to the tail of the list
    this.addIfNotLeaf(node.left);
    this.addIfNotLeaf(node.right);
    // return the answer
    return node.data;
  }

  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}