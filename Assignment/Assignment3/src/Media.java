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

abstract class AMedia implements IMedia {
  String title;
  ILoString captionOptions;

  AMedia(String title, ILoString captionOptions) {
    this.title = title;
    this.captionOptions = captionOptions;
  }

  // is this media really old?
  public abstract boolean isReallyOld();

  // are captions available in this language?
  public boolean isCaptionAvailable(String language) {
    return this.captionOptions.available(language);
  }

  // a string showing the proper display of the media
  public abstract String format();
}

// to represent a Movie
class Movie extends AMedia {
  int year;

  Movie(String title, int year, ILoString captionOptions) {
    super(title, captionOptions);
    this.year = year;
  }

  /*
   * fields:
   * this.title ... String
   * this.year ... int
   * this.captionOptions ... ILoString
   * 
   * methods:
   * this.isReallyOld() ... boolean
   * this.isCaptionAvailable(String language) ... boolean
   * this.format() ... String
   * 
   * methods for fields:
   * this.captionOptions.available(String language) ... boolean
   */

  // determine if a movie is really old?
  public boolean isReallyOld() {
    return this.year < 1930;
  }

  // produces a String that are formatted by the title followed by the year in
  // parentheses
  public String format() {
    return this.title + " (" + this.year + ")";
  }
}

// to represent a TV episodes
class TVEpisode extends AMedia {
  String showName;
  int seasonNumber;
  int episodeOfSeason;

  TVEpisode(String title, String showName, int seasonNumber, int episodeOfSeason,
      ILoString captionOptions) {
    super(title, captionOptions);
    this.showName = showName;
    this.seasonNumber = seasonNumber;
    this.episodeOfSeason = episodeOfSeason;
  }

  /*
   * fields:
   * this.showName ... String
   * this.seasonNumber ... int
   * this.episodeOfSeason ... int
   * this.captionOptions ... ILoString
   * 
   * methods:
   * this.isReallyOld() ... boolean
   * this.isCaptionAvailable(String language) ... boolean
   * this.format() ... String
   * 
   * methods for fields:
   * this.captionOptions.available(String language) ... boolean
   */

  // return false because TV episodes are never be old
  public boolean isReallyOld() {
    return false;
  }

  // produces a String that formatted by the show’s title followed by the season
  // and episode number, separated by a period, followed by a dash and then the
  // episode title
  public String format() {
    return this.showName + " " + this.seasonNumber + "." + this.episodeOfSeason + " - "
        + this.title;
  }
}

// to represent YouTube videos
class YTVideo extends AMedia {

  String channelName;

  public YTVideo(String title, String channelName, ILoString captionOptions) {
    super(title, captionOptions);
    this.channelName = channelName;
  }

  /*
   * fields:
   * this.title ... String
   * this.channelName ... String
   * this.captionOptions ... ILoString
   * 
   * methods:
   * this.isReallyOld() ... boolean
   * this.isCaptionAvailable(String language) ... boolean
   * this.format() ... String
   * 
   * methods for fields:
   * this.captionOptions.available(String language) ... boolean
   */

  // return false because YouTube videos are never be old
  public boolean isReallyOld() {
    return false;
  }

  // produces a String that are formatted by the video’s title followed by the
  // word by followed by the channel name
  public String format() {
    return this.title + " by " + this.channelName;
  }
}

// lists of strings
interface ILoString {
  // check if there is available language in a list of String
  boolean available(String language);
}

// an empty list of strings
class MtLoString implements ILoString {
  // check if there is available language in a list of String
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

  /*
   * fields:
   * this.first ... String
   * this.rest ... ILoString
   * 
   * methods:
   * this.available(String language) ... boolean
   * 
   * methods for fields:
   * this.rest.available(String language) ... boolean
   */

  // check if there is available language in a list of String
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