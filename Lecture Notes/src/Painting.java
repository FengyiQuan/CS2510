import tester.Tester;

class Painting {
	Artist artist;
	String title;
	double value; // in millions of dollars
	int year;

	Painting(Artist artist, String title, double value, int year) {
		this.artist = artist;
		this.title = title;
		this.value = value;
		this.year = year;
	}

	/* fields:
	 * this.artist ... Artist
	 * this.title ... String
	 * this.value ... double
	 * this.year ... int
	 * methods:
	 * this.byArtist(String) ... boolean
	 * this.comesBefore(Painting)... boolean
	 * methods for fields:
	 * this.artist.byArtist(String) ... boolean
	 */

	//is this Painting the same as the given one?
	boolean samePainting(Painting that) {
		return this.title.equals(that.title) &&
				this.year == that.year &&
				this.value - that.value < .0001 &&
				this.artist.sameArtist(that.artist);
	}

	// is this painting by the given artist?
	boolean byArtist(String artistName) {
		return this.artist.byArtist(artistName);
	}

	//does this painting's title come before the given one's?
	boolean comesBefore(Painting p) {
		return this.title.compareTo(p.title) < 0;
	}
	
	//is this painting painted before 1800?
	boolean before1800() {
		return this.year < 1800;
	}
}

class Artist { 
	String name;
	int yob;

	Artist(String n, int y) {
		this.name = n;
		this.yob = y;
	}

	/* fields:
	 * this.name ... String
	 * this.yob ... int
	 * methods:
	 * this.sameArtist(Artist) ... boolean
	 * this.byArtist(String)
	 */

	//is this artist the same as that artist
	boolean sameArtist(Artist that) {
		return this.name.equals(that.name) && 
				this.yob == that.yob;
	}

	//is this artist's name the same as the given name?
	boolean byArtist(String artist) {
		return this.name.equals(artist);
	}
}

interface IPaintingPredicate {
	//asks a question about the given painting
	boolean apply(Painting p);
}

class Before1800 implements IPaintingPredicate {
	//is the given painting painted before 1800?
	public boolean apply(Painting p) {
		return p.year < 1800;
	}
}

class ByArtist implements IPaintingPredicate {
	String name;
	
	ByArtist(String name) {
		this.name = name;
	}
	
	//is the given painting by the artist?
	public boolean apply(Painting p) {
		return p.byArtist(this.name);
	}
	
}

class AndPredicate implements IPaintingPredicate {
	IPaintingPredicate left;
	IPaintingPredicate right;
	
	AndPredicate(IPaintingPredicate left, IPaintingPredicate right) {
		this.left = left;
		this.right = right;
	}
	
	//does the given painting pass all of the tests on the left and the right?
	public boolean apply(Painting p) {
		return this.left.apply(p) && this.right.apply(p);
	}
	
}


interface ILoPainting {
	//does this ILoPainting contain a painting by the artist with the given name?
	boolean hasArtist(String name);
	//get all of the paintings by the artist with the given name
	ILoPainting filterByArtist(String name);
	//sort by title in this ILoPainting
	ILoPainting sortByTitle();
	//inserts the given painting into this sorted list
	ILoPainting insertByTitle(Painting p);
	//get the paintings painted before 1800
	ILoPainting filterByBefore1800();
	//filter this ILoPainting by the given predicate
	ILoPainting filter(IPaintingPredicate pred);
	//does this ILoPainting have at least one painting that passes the given test?
	boolean ormap(IPaintingPredicate pred);
}

class MtLoPainting implements ILoPainting {

	//does this empty list contain a painting by an artist with the given name?
	public boolean hasArtist(String name) {
		return false;
	}


	//sort this empty list by painting title
	public ILoPainting sortByTitle() {
		return this;
	}

	//insert the given painting into this empty list
	public ILoPainting insertByTitle(Painting p) {
		return new ConsLoPainting(p, this);
	}

	//get the painting in this empty list by the artist with the given name
	public ILoPainting filterByArtist(String name) {
		return this;
	}

	//get the paintings painted before 1800
	public ILoPainting filterByBefore1800() {
		return this;
	}


	//filter this MtLoPainting by the given predicate
	public ILoPainting filter(IPaintingPredicate pred) {
		return this;
	}


	//is there at least one painting in this MtLoPainting that passes the given predicate?
	public boolean ormap(IPaintingPredicate pred) {
		return false;
	}
}

class ConsLoPainting implements ILoPainting {
	Painting first;
	ILoPainting rest;

	ConsLoPainting(Painting first, ILoPainting rest) {
		this.first = first;
		this.rest = rest;
	}


	/* this.first  ... Painting
	 * this.rest ... ILoPainting
	 * methods:
	 *  this.hasArtist(String) ... boolean
	 *  this.filterByArtist(String) ... ILoPainting
	 *  this.sortByTitle() ... ILoPainting
	 *  this.insertByTitle(Painting p) ... ILoPainting
	 * methods for fields:
	 * this.rest.hasArtist(String) ... boolean
	 * this.first.byArtist(String) ... boolean
	 * this.rest.sortByTitle() ... ILoPainting
	 *  this.rest.insertByTitle(Painting p) ... ILoPainting
	 *  this.first.comesBefore(Painting) ... boolean
	 */

	//does this non-empty list contain a painting by an artist with the given name?
	public boolean hasArtist(String name) {
		return this.first.byArtist(name) || this.rest.hasArtist(name);
	}


	//get the painting in this non-empty list by the artist with the given name
	public ILoPainting filterByArtist(String name) {
		if (this.first.byArtist(name)) {
			return new ConsLoPainting(this.first, this.rest.filterByArtist(name));
		}
		else {
			return this.rest.filterByArtist(name);
		}
	}

	//get the paintings painted before 1800
	public ILoPainting filterByBefore1800() {
		if (this.first.before1800()) {
			return new ConsLoPainting(this.first, this.rest.filterByBefore1800());
		}
		else {
			return this.rest.filterByBefore1800();
		}
	}

	//sort this non-empty list alphabetically by Painting title
	public ILoPainting sortByTitle() {
		return this.rest.sortByTitle().insertByTitle(this.first);
	}


	//insert the given painting into this sorted consLoPainting
	public ILoPainting insertByTitle(Painting p) {
		if (p.comesBefore(this.first)) {
			return new ConsLoPainting(p, this);
		}
		else {
			return new ConsLoPainting(this.first, this.rest.insertByTitle(p));
		}
	}


	//filter this ConsLoPainting by the given predicate
	public ILoPainting filter(IPaintingPredicate pred) {
		if (pred.apply(this.first)) {
			return new ConsLoPainting(this.first, this.rest.filter(pred));
		}
		else {
			return this.rest.filter(pred);
		}
	}


	//does this ConsLoPainting contain at least one painting that passes the given predicate?
	public boolean ormap(IPaintingPredicate pred) {
		return (pred.apply(this.first)) || this.rest.ormap(pred);
	}
}


class Examples {
	Artist daVinci = new Artist("Da Vinci", 1452);
	Artist daVinci2 = new Artist("Da Vinci", 1452);
	Artist monet = new Artist("Monet", 1840);
	Painting mona = new Painting(this.daVinci, "Mona Lisa", 10, 1503);
	Painting mona2 = new Painting(this.daVinci, "Mona Lisa", 10, 1503);
	Painting last = new Painting(this.daVinci, "The Last Supper", 11, 1480);
	Painting sunflowers = new Painting(new Artist("Van Gogh", 1853), 
			"sunflowers", 9, 1889);
	Painting waterlilies = new Painting(this.monet, "Water Lilies", 20, 1915);

	ILoPainting mt = new MtLoPainting();
	ILoPainting lop1 = new ConsLoPainting(this.mona, this.mt);
	ILoPainting lop2 = new ConsLoPainting(this.waterlilies, this.lop1);

	String s1 = "hello";
	String s2 = new String("hello");

	boolean testFilter(Tester t) {
		return t.checkExpect(this.mt.filter(new Before1800()), this.mt) &&
				t.checkExpect(this.lop2.filter(new Before1800()), this.lop1) &&
				t.checkExpect(this.lop2.filter(new ByArtist("Monet")), 
						new ConsLoPainting(this.waterlilies, this.mt)) &&
				t.checkExpect(this.lop2.ormap(new Before1800()), true) &&
				t.checkExpect(this.lop2.ormap(new AndPredicate(new Before1800(),
						new ByArtist("Da Vinci"))), true) &&
				t.checkExpect(new Before1800().apply(this.mona), true);
		//add more tests!!
	}

	boolean testSameness(Tester t) {
		return t.checkExpect(this.mona.equals(this.last), false) &&
				t.checkExpect(this.mona.equals(this.mona2), false) &&
				t.checkExpect(this.mona.equals(this.mona), true) &&
				t.checkExpect(this.mona.samePainting(this.mona2), true) &&
				t.checkExpect(this.s1.equals(s2), true) &&
				t.checkExpect(this.s1 == s2, false)
				;
	}
	//tests for lists of paintings
	boolean testPaintingLists(Tester t) {
		return t.checkExpect(this.mt.hasArtist("Monet"), false) &&
				t.checkExpect(this.lop2.hasArtist("Da Vinci"), true) &&
				t.checkExpect(this.lop2.hasArtist("Van Gogh"), false) &&
				t.checkExpect(this.mt.filterByArtist("Monet"), this.mt) &&
				t.checkExpect(this.lop2.filterByArtist("Da Vinci"), this.lop1) &&
				t.checkExpect(this.lop2.filterByArtist("Monet"), 
						new ConsLoPainting(this.waterlilies, this.mt)) &&
				t.checkExpect(this.mt.sortByTitle(), this.mt) &&
				t.checkExpect(this.lop2.sortByTitle(), 
						new ConsLoPainting(this.mona, new ConsLoPainting(this.waterlilies, this.mt)))
				
				;
	}

	// ADD MORE TESTS!!

	//tests for Paintings
	boolean testPaintings(Tester t) {
		return true;
	}

	//test for Artists
	boolean testArtists(Tester t) {
		return true;
	}	

}
