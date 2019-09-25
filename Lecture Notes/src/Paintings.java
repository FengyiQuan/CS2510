import tester.Tester;


class Painting {
	Artist artist;
	String title;

	Painting(Artist artist, String title) {
		this.artist = artist;
		this.title = title;
		//set the artist's painting to this painting
		this.artist.updatePaintings(this);
	}

	//is this Painting the same as the given one?
	boolean samePainting(Painting p) {
		return this.title.equals(p.title) &&
				this.artist.sameArtist(p.artist);
	}
}

class Artist { 
	String name;
	IList<Painting> paintings;

	Artist(String n) {
		this.name = n;
		this.paintings = new MtList<Painting>();
	}

	//is this artist the same as the given one?
	boolean sameArtist(Artist that) {
		return this.name.equals(that.name); 
		// && this.painting.samePainting(that.painting);
	}
	
	//EFFECT: adds the given painting to this.paintings
	void updatePaintings(Painting p) {
			this.paintings = new ConsList<Painting>(p, this.paintings);
	}
 
}

class Examples {
	Artist daVinci;
	Painting mona;
	Painting lastSupper;
	
	//Effect: brings the data back to its initial conditions
	void initData() {
		this.daVinci = new Artist("da Vinci");
		this.mona = new Painting(daVinci, "Mona Lisa");
		//this.lastSupper = new Painting(this.daVinci, "Last Supper");
	}
	
	void testPainting(Tester t) {
		this.initData();
		t.checkExpect(this.daVinci.paintings, new ConsList<Painting>(this.mona, new MtList<Painting>()));
		this.lastSupper = new Painting(this.daVinci, "Last Supper");
		t.checkExpect(this.daVinci.paintings, new ConsList<Painting>(this.lastSupper, 
				new ConsList<Painting>(this.mona, new MtList<Painting>())));
		
	}

}



class Counter {
	int val;

	Counter() {
		this(0);
	}

	Counter(int initialVal) {
		this.val = initialVal;
	}

	int get() {
		int ans = this.val;
		this.val = this.val + 1;
		return ans;
	}
}
class ExamplesC {
	Counter c1 = new Counter();
	Counter c2 = new Counter(2);

	boolean testCounter(Tester t) {

		// What should these tests be?
		return t.checkExpect(c1.get(), 0)                  // Test 1
				&& t.checkExpect(c2.get(), 2)              // Test 2
				&& t.checkExpect(c1.get() == c1.get(), false)  // Test 3
				&& t.checkExpect(c2.get() == c1.get(), true)  // Test 4
				&& t.checkExpect(c2.get() == c1.get(), true)  // Test 5
				&& t.checkExpect(c1.get() == c1.get(), false)  // Test 6
				&& t.checkExpect(c2.get() == c1.get(), false); // Test 7
	}

	boolean testCounter2(Tester t) {

		// What should these tests be?
		return t.checkExpect(c1.get(), 0)                  // Test 1
				&& t.checkExpect(c2.get(), 2)              // Test 2
				&& t.checkExpect(c1.get() == c1.get(), false)  // Test 3
				&& t.checkExpect(c2.get() == c1.get(), true)  // Test 4
				&& t.checkExpect(c2.get() == c1.get(), true)  // Test 5
				&& t.checkExpect(c1.get() == c1.get(), false)  // Test 6
				&& t.checkExpect(c2.get() == c1.get(), false); // Test 7
	}
}

