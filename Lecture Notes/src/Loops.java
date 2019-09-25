import java.util.ArrayList;
import java.util.Arrays;

import tester.Tester;


class MoreUtils {

	//Effect: removes items that don't pass the test
	<T> void removeExcept(ArrayList<T> alist, IPred<T> pred) {
		for(int i = alist.size()-1; i > -1; i--) {
			if (!pred.apply(alist.get(i))) {
				alist.remove(i);
			}
		}
	}

	//Effect: removes items that don't pass the test
	//this can throw a ConcurrentModificationException!
	<T> void removeExcept2(ArrayList<T> alist, IPred<T> pred) {
		for (T t : alist) {
			if (!pred.apply(t)) {
				alist.remove(t);
			}
		}
	}

	//Effect: transposes the xs and ys of the posns
	void transpose(ArrayList<Posn> points) {
		for (Posn p : points) {
			int temp = p.x;
			p.x = p.y;
			p.y = temp;
		}
	}

	//interleaves the two given lists of the same length
	<T> ArrayList<T> interleave(ArrayList<T> a, ArrayList<T> b) {
		ArrayList<T> result = new ArrayList<T>();
		for(int i = 0; i < a.size(); i = i + 1) {
			result.add(a.get(i));
			result.add(b.get(i));			
		}
		return result;
	}

	//combines the items in the list from right to left
	<U,T> U foldr(ArrayList<T> alist, IFunc2<T, U, U> fun, U base) {
		for(int i = alist.size()-1; i > -1; i--) {
			base = fun.apply(alist.get(i), base);
		}
		return base;
	}
}


class Examples {
	ArrayList<String> strings = new ArrayList<String>(Arrays.asList("cat", "dog", "bird"));
	ArrayList<Integer> ints = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 3));
	ArrayList<Integer> ints2 = new ArrayList<Integer>(Arrays.asList(4,5,6,7));
	ArrayList<Integer> ints3 = new ArrayList<Integer>(Arrays.asList(4,5,6));
	ArrayList<Posn> posns = new ArrayList<Posn>(Arrays.asList(new Posn(3, 4), new Posn(6, 8)));
	MoreUtils mu = new MoreUtils();

	//need more tests
	void testLoops(Tester t) {
		t.checkExpect(mu.foldr(ints, new Add(), 0), 9);
		t.checkExpect(mu.interleave(new ArrayList<Integer>(), new ArrayList<Integer>()), new ArrayList<Integer>());
		t.checkExpect(mu.interleave(ints, ints2), new ArrayList<Integer>(Arrays.asList(1,4,2,5,3,6,3,7)));
		//t.checkExpect(new Util().cards(), 0);
		mu.transpose(posns);
		t.checkExpect(posns.get(0), new Posn(4,3));
		mu.removeExcept(ints3, new Even());
		t.checkExpect(ints3, new ArrayList<Integer>(Arrays.asList(4,6)));
		mu.removeExcept(ints, new Even());
		t.checkExpect(ints, new ArrayList<Integer>(Arrays.asList(2)));
		ints = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 3));
		ints2 = new ArrayList<Integer>(Arrays.asList(4,5,6,7));
		ints3 = new ArrayList<Integer>(Arrays.asList(4,5,6));
		
		mu.removeExcept2(ints3, new Even());
		t.checkExpect(ints3, new ArrayList<Integer>(Arrays.asList(4,6)));
		mu.removeExcept2(ints, new Even());
		t.checkExpect(ints, new ArrayList<Integer>(Arrays.asList(2)));
	}
}



class Even implements IPred<Integer> {

	//is t an even number?
	public boolean apply(Integer t) {
		return t % 2 == 0;
	}
}






class Card {
	String value;
	String suit;
	Card(String v, String s) {this.value = v; this.suit = s; }
}

class Util {

	//create a deck of 52 cards
	ArrayList<Card> cards() {
		ArrayList<Card> cards = new ArrayList<Card>();
		ArrayList<String> vals = new ArrayList<String>(Arrays.asList("ace", "two", "three", "four", "five", "six", "seven",
				"eight", "nine", "ten", "jack", "queen", "king"));

		ArrayList<String> suits = new ArrayList<String>(Arrays.asList("hearts", "diamonds", "clubs", "spades"));

		for (int i = 0; i < vals.size(); i++) {
			for (int j = 0; j < suits.size(); j++) {
				cards.add(new Card(vals.get(i), suits.get(j)));
			}
		}

		return cards;
	}
}