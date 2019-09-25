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
	

	// is this painting by the given artist?
	boolean byArtist(String artistName) {
		return this.artist.byArtist(artistName);
	}
	
	//does this painting's title come before the given one's?
	boolean comesBefore(Painting p) {
		return this.title.compareTo(p.title) < 0;
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


interface ILoPainting {
	//does this ILoPainting contain a painting by the artist with the given name?
	boolean hasArtist(String name);
	//get all of the paintings by the artist with the given name
	ILoPainting filterByArtist(String name);
	//sort by title in this ILoPainting
	ILoPainting sortByTitle();
	//inserts the given painting into this sorted list
	ILoPainting insertByTitle(Painting p);
}

class MtLoPainting implements ILoPainting {

	//does this empty list contain a painting by an artist with the given name?
	public boolean hasArtist(String name) {
		return false;
	}

	//get the painting in this empty list by the artist with the given name
	public ILoPainting filterByArtist(String name) {
		return this;
	}

	//sort this empty list by painting title
	public ILoPainting sortByTitle() {
		return this;
	}

	//insert the given painting into this empty list
	public ILoPainting insertByTitle(Painting p) {
		return new ConsLoPainting(p, this);
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
}


class Examples {
	Artist daVinci = new Artist("Da Vinci", 1452);
	Artist monet = new Artist("Monet", 1840);
	Painting mona = new Painting(this.daVinci, "Mona Lisa", 10, 1503);
	Painting last = new Painting(this.daVinci, "The Last Supper", 11, 1480);
	Painting sunflowers = new Painting(new Artist("Van Gogh", 1853), 
			"sunflowers", 9, 1889);
	Painting waterlilies = new Painting(this.monet, "Water Lilies", 20, 1915);

	ILoPainting mt = new MtLoPainting();
	ILoPainting lop1 = new ConsLoPainting(this.mona, this.mt);
	ILoPainting lop2 = new ConsLoPainting(this.waterlilies, this.lop1);
	

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
