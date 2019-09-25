import tester.Tester;

class ByArtist implements IPred<Painting> {
  String name;

  ByArtist(String name) {
    this.name = name;
  }

  public boolean apply(Painting t) {
    return t.byArtist(this.name);
  }
}

class ShortString implements IPred<String> {

  public boolean apply(String t) {
    return t.length() < 10;
  }
}

class PaintingToTitle implements IFunc<Painting, String> {
  public String apply(Painting p) {
    return p.title;
  }
}

class StringLength implements IFunc<String, Integer> {
  public Integer apply(String x) {
    return x.length();
  }

}

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

  // is this painting by the given artist?
  boolean byArtist(String artistName) {
    return this.artist.byArtist(artistName);
  }
}

class Artist {
  String name;
  int yob;

  Artist(String n, int y) {
    this.name = n;
    this.yob = y;
  }

  // is this artist's name the same as the given name?
  boolean byArtist(String artist) {
    return this.name.equals(artist);
  }
}

class ExamplesP {
  Artist daVinci = new Artist("Da Vinci", 1452);
  Painting mona = new Painting(this.daVinci, "Mona Lisa", 10, 1503);

  IList<Painting> listMt = new MtList<Painting>();
  IList<Painting> listOne = new ConsList<Painting>(this.mona, this.listMt);

  IList<Integer> ints = new ConsList<Integer>(1, new MtList<Integer>());

  boolean testMap(Tester t) {
    return t.checkExpect(this.listOne.map(new PaintingToTitle()),
        new ConsList<String>("Mona Lisa", new MtList<String>()));
  }
}

class Equals implements IPred<Integer> {

  public boolean apply(Integer t) {
    return t == 3;
  }

}

class TestIList {
  IList<Integer> test = new ConsList<Integer>(1,
      new ConsList<Integer>(2, new ConsList<Integer>(3, new MtList<Integer>())));

  void testFind(Tester t) {
    t.checkExpect(this.test.find(new Equals()), 3);
  }
}