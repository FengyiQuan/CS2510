import tester.Tester;
import java.util.ArrayList;
import java.util.Arrays;


class Posn {
	int x;
	int y;
	
	Posn(int x, int y) {
		this.x = x;
		this.y = y;
	}
}



class Utils {
	//maps the function onto every member of the list
	<T, U> ArrayList<U> map(ArrayList<T> alist, IFunc<T, U> fun) {
		return this.mapHelp(alist, fun, 0, new ArrayList<U>());
	}
	//helps map a function onto every member of the list by keeping track of the current index
	<U, T> ArrayList<U> mapHelp(ArrayList<T> alist, IFunc<T, U> fun, int currentIndex, ArrayList<U> result) {
		if (alist.size() == currentIndex) {
			return result;
		}
		else {
			result.add(fun.apply(alist.get(currentIndex)));
			return this.mapHelp(alist, fun, currentIndex+1, result);
		}
	}
	
	//maps the function onto every member of the list using a for-each loop
	<T, U> ArrayList<U> map2(ArrayList<T> alist, IFunc<T, U> fun) {
		ArrayList<U> result = new ArrayList<U>();
		for (T t: alist) {
			result.add(fun.apply(t));
		}
		return result;
	}
	
	//combines the items in the list from left to right
	<U, T> U foldl(ArrayList<T> alist, IFunc2<T, U, U> fun, U base) {
		for (T t: alist) {
			base = fun.apply(t, base);
		}
		return base;
	}
	
	//combines the items in the list from right to left
	<U,T> U foldr(ArrayList<T> alist, IFunc2<T, U, U> fun, U base) {
		ArrayList<T> reversed = new ArrayList<T>();
		for (T t: alist) {
			reversed.add(0, t);
		}
		
		return this.foldl(reversed, fun, base); 
		
	}
	
	//Effect: the x and y positions of the Posn are swapped
	void swapPosn(Posn p1, Posn p2) {
		Posn temp = new Posn(p1.x, p1.y);
		p1.x = p2.x;
		p1.y = p2.y;
		p2.x = temp.x;
		p2.y = temp.y;
	}
}


class Examples {
	ArrayList<String> strings = new ArrayList<String>(Arrays.asList("a", "bb", "ccc"));
	ArrayList<Integer> ints = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
	Posn a = new Posn(3, 4);
	Posn b = new Posn(6, 8);
	Utils u = new Utils();
	
	void testPosn(Tester t) {
		t.checkExpect(this.a.x, 3);
		t.checkExpect(this.b.x, 6);
		t.checkExpect(this.a.y, 4);
		t.checkExpect(this.b.y, 8);
		u.swapPosn(this.a, this.b);
		t.checkExpect(this.a.x, 6);
		t.checkExpect(this.b.x, 3);
		t.checkExpect(this.a.y, 8);
		t.checkExpect(this.b.y, 4);

	}
	
	void testLists(Tester t) {
		t.checkExpect(this.u.map(this.strings, new StringLength()), this.ints);
		t.checkExpect(this.u.map(new ArrayList<String>(), new StringLength()), new ArrayList<Integer>());
		t.checkExpect(this.u.foldl(this.ints, new Add(), 0), 6);
		t.checkExpect(this.u.foldl(new ArrayList<Integer>(), new Add(), 0), 0);
		t.checkExpect(this.u.foldr(this.ints, new Add(), 0), 6);
		t.checkExpect(this.u.foldr(new ArrayList<Integer>(), new Add(), 0), 0);
	}
}

interface IFunc<X, Y> {
	Y apply(X x);
}

class StringLength implements IFunc<String, Integer> {

	//computes the length of the given string
	public Integer apply(String x) {
		return x.length();
	}
	
}

interface IFunc2<X, Y, Z> {
	Z apply(X x, Y y);
}

class Add implements IFunc2<Integer, Integer, Integer> {
	//adds the two given integers
	public Integer apply(Integer x, Integer y) {
		return x + y;
	}
}