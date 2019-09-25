// to represent a graphic novel
class GraphicNovel {
  String title;
  String author;
  String artist;
  int year;
  double cost;
  boolean monochrome;

  // the constructor
  GraphicNovel(String title, String author, String artist, int year, double cost,
      boolean monochrome) {
    this.title = title;
    this.author = author;
    this.artist = artist;
    this.year = year;
    this.cost = cost;
    this.monochrome = monochrome;
  }
}

// examples and tests for the classes that represent
// graphic novels
class ExamplesGraphicNovel {
  ExamplesGraphicNovel() {
  }

  GraphicNovel maus = new GraphicNovel("Maus", "Spiegelman", "Spiegelman", 1980, 17.60, true);
  GraphicNovel logicomix = new GraphicNovel("Logicomix", "Doxiadis", "Papadatos", 2009, 21.00,
      false);
  GraphicNovel fakebook = new GraphicNovel("fakebook", "fakeman1", "fakeman2", 2018, 20.00, true);
}