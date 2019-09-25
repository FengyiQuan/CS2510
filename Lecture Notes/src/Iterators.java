import java.util.ArrayList;
import java.util.Iterator;

import tester.Tester;

/*
class MyArrayList<E> implements Iterable<E> {
	
	//creates an Iterator for this list
	Iterator<E> iterator() {
		return new ArrayListIterator<E>(this);
	}
}
*/
class ArrayListIterator<T> implements Iterator<T> {
	ArrayList<T> items;
	int currentIndex;
	
	ArrayListIterator(ArrayList<T> items) {
		this.items = items;
		this.currentIndex = 0;
	}
	
	//is there another item to process?
	public boolean hasNext() {
		return currentIndex < items.size();
	}

	//get the next item
	//Effect: currentIndex is incremented by 1
	public T next() {
		T temp = items.get(currentIndex);
		currentIndex += 1;	
		return temp;
	}
	
}







interface IList<T> extends Iterable<T> {
	//is this list a cons list?
	boolean isCons();
	//casts this list to ConsList
	ConsList<T> asCons();
}

class MtList<T> implements IList<T> {

	//creates an Iterator for this MtList
	public Iterator<T> iterator() {
		return new IListIterator<T>(this);
	}

	//is this a Cons list?
	public boolean isCons() {
		return false;
	}

	//cast this to a Cons list
	public ConsList<T> asCons() {
		throw new ClassCastException("can't cast mt to cons!");
	}
	
}

class ConsList<T> implements IList<T> {
	T first;
	IList<T> rest;

	ConsList(T first, IList<T> rest) {
		this.first = first;
		this.rest = rest;
	}

	//creates an Iterator for this ConsList
	public Iterator<T> iterator() {
		return new IListIterator<T>(this);
	}

	//is this a Cons list?
	public boolean isCons() {
		return true;
	}

	//cast this to a cons list
	public ConsList<T> asCons() {
		return this;
	}

	
}

class IListIterator<T> implements Iterator<T> {
	IList<T> items;
	
	IListIterator(IList<T> items) {
		this.items = items;
	}

	//is there another item to process?
	public boolean hasNext() {
		return this.items.isCons();
	}

	//get the next item
	//Effect: items is set to its rest
	public T next() {
		ConsList<T> temp = this.items.asCons();
		this.items = temp.rest;
		return temp.first;
	}
	
	
	
}

class Squares implements Iterator<Integer> {
	int currentN = 1;
	
	//is there another square to produce?
	public boolean hasNext() {
		return true;
	}

	//get the next square
	//Effect: currentN is incremented
	public Integer next() {
		int temp = currentN * currentN;
		currentN++;
		return temp;
	}
	
}


class GetN<T> implements Iterator<T>, Iterable<T> {
	int n;
	Iterator<T> iter;
	
	GetN(int n, Iterator<T> iter) {
		this.n = n;
		this.iter = iter;
	}

	//are there any more items to process?
	public boolean hasNext() {
		return iter.hasNext() && n > 0;
	}

	//gets the next item
	//Effect: n is decremented and iter is advanced
	public T next() {
		T result = iter.next();
		n = n - 1;
		return result;
	}

	//produces an Iterator for GetN
	public Iterator<T> iterator() {
		return this;
	}
	
	
}

interface IFunc<X, Y> {
	Y apply(X x);
}

//an Iterator to map a function over the items from a given Iterator
class MapIter<T, U> implements Iterator<U> {
	IFunc<T, U> fun;
	Iterator<T> iter;
	
	MapIter(IFunc<T, U> fun, Iterator<T> iter) {
		this.fun = fun;
		this.iter = iter;
	}

	//is there another item to process?
	public boolean hasNext() {
		return this.iter.hasNext();
	}

	//get the next item
	//Effect: iter is advanced
	public U next() {
		return fun.apply(iter.next());
	}
	
}

class Examples {
	IList<Integer> mt = new MtList<Integer>();
	IList<Integer> ints = new ConsList<Integer>(1, this.mt);
	IList<Integer> ints2 = new ConsList<Integer>(2, this.ints);
	Iterator<Integer> iter1 = new IListIterator<Integer>(ints2);
	Iterator<Integer> iter2 = ints2.iterator();
	Iterator<Integer> iter3 = new Squares();
	
	Iterable<Integer> iter4 = new GetN<Integer>(10, new Squares());
	Iterator<Integer> iter5 = new GetN<Integer>(5, new Squares());
	
	void testIter(Tester t) {
		t.checkExpect(iter1.hasNext(), true);
		t.checkExpect(iter1.hasNext(), true);
		t.checkExpect(iter1.next(), 2);
		t.checkExpect(iter1.hasNext(), true);
		t.checkExpect(iter1.next(), 1);
		t.checkExpect(iter1.hasNext(), false);
		t.checkExpect(iter1.hasNext(), false);
		t.checkExpect(iter1.hasNext(), false);
		t.checkExpect(iter2.hasNext(), true);
		
		int sum = 0;
		for (Integer i: ints2) {
			sum = sum + i;
		}
		t.checkExpect(sum, 3);
		
		t.checkExpect(iter5.hasNext(), true);
		t.checkExpect(iter5.next(), 1);
		
		for(Integer i: iter4) {
			System.out.println(i);
		}
	}
}
