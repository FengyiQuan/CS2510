import tester.Tester;

// a piece of media
interface IMedia {

  // is this media really old?
  boolean isReallyOld();

  // are captions available in this language?
  boolean isCaptionAvailable(String language);

  // a string showing the proper display of the media
  String format();
}

// represents a movie
class Movie implements IMedia {
  String title;
  int year;
  ILoString captionOptions; // available captions

  Movie(String title, int year, ILoString captionOptions) {
    this.title = title;
    this.year = year;
    this.captionOptions = captionOptions;
  }

  public boolean isReallyOld() {
    return this.year < 1930;
  }

  public boolean isCaptionAvailable(String language) {
    return this.captionOptions.available(language);
  }

  public String format() {
    return this.title + " (" + this.year + ")";
  }
}

// represents a TV episode
class TVEpisode implements IMedia {
  String title;
  String showName;
  int seasonNumber;
  int episodeOfSeason;
  ILoString captionOptions; // available captions

  TVEpisode(String title, String showName, int seasonNumber, int episodeOfSeason,
      ILoString captionOptions) {
    this.title = title;
    this.showName = showName;
    this.seasonNumber = seasonNumber;
    this.episodeOfSeason = episodeOfSeason;
    this.captionOptions = captionOptions;
  }

  public boolean isReallyOld() {
    return false;
  }

  public boolean isCaptionAvailable(String language) {
    return this.captionOptions.available(language);
  }

  public String format() {
    return this.title + " " + this.seasonNumber + "." + this.episodeOfSeason + " - "
        + this.showName;
  }
}

// represents a YouTube video
class YTVideo implements IMedia {
  String title;
  String channelName;
  ILoString captionOptions; // available captions

  public YTVideo(String title, String channelName, ILoString captionOptions) {
    this.title = title;
    this.channelName = channelName;
    this.captionOptions = captionOptions;
  }

  public boolean isReallyOld() {
    return false;
  }

  public boolean isCaptionAvailable(String language) {
    return this.captionOptions.available(language);
  }

  public String format() {
    return this.title + " by " + this.channelName;
  }

}

// lists of strings
interface ILoString {
  boolean available(String language);
}

// an empty list of strings
class MtLoString implements ILoString {
  public boolean available(String language) {
    return false;
  }
}

// a non-empty list of strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  public boolean available(String language) {
    return this.first.equals(language) || this.rest.available(language);
  }
}

class ExamplesMedia {
  MtLoString mtls = new MtLoString();
  ILoString avengersLang = new ConsLoString("English", new ConsLoString("French", this.mtls));
  ILoString circusLang = new ConsLoString("German", this.mtls);
  ILoString walkingLang = new ConsLoString("Chinese",
      new ConsLoString("Spanish", new ConsLoString("Russian", this.mtls)));
  ILoString goLang = new ConsLoString("English", this.mtls);
  ILoString bestLang = new ConsLoString("English", new ConsLoString("Arabic", this.mtls));
  ILoString starsLang = new ConsLoString("Spanish", new ConsLoString("Russian", this.mtls));

  IMedia avengers = new Movie("Avengers: Endgame", 2018, this.avengersLang);
  IMedia circus = new Movie("The Circus", 1928, this.circusLang);
  IMedia walking = new TVEpisode("The Walking Dead", "The Walking Dead", 2, 12, this.walkingLang);
  IMedia go = new TVEpisode("GO", "GO", 1, 1, this.goLang);
  IMedia best2018 = new YTVideo("Best 2018", "None", this.bestLang);
  IMedia stars = new YTVideo("Reaching for the stars", "YouTube SpotLight", this.starsLang);

  // test for isReallyOld
  boolean testIsReallyOld(Tester t) {
    return t.checkExpect(this.avengers.isReallyOld(), false)
        && t.checkExpect(this.circus.isReallyOld(), true)
        && t.checkExpect(this.walking.isReallyOld(), false)
        && t.checkExpect(this.best2018.isReallyOld(), false);
  }

  // test for isCaptionAvailable
  boolean testIsCaptionAvailable(Tester t) {
    return t.checkExpect(this.avengers.isCaptionAvailable("Chinese"), false)
        && t.checkExpect(this.best2018.isCaptionAvailable("English"), true)
        && t.checkExpect(this.walking.isCaptionAvailable("Russian"), true);
  }
  
  // test for format
  boolean testFormat(Tester t) {
    return t.checkExpect(this.avengers.format(), "Avengers: Endgame (2018)")
        && t.checkExpect(this.walking.format(), "The Walking Dead 2.12 - The Walking Dead")
        && t.checkExpect(this.best2018.format(), "Best 2018 by None");
  }
}