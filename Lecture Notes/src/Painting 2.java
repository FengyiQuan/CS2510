import tester.*;

// Lists of Numbers
interface ILoNumber {
 // sum a list of numbers 
 int sum();
 
}

// an empty list of numbers
class MtLoNumber implements ILoNumber {
  
  /* Template
   * Fields:
   * 
   * Methods: 
   * this.sum() ... int 
   * Methods of fields: 
   * 
   */
  
  // calculate sum of this empty list of numbers.
  public int sum() {
    return 0;
  }

}

// a non-empty list of numbers 
class ConsLoNumber implements ILoNumber {
  int first;
  ILoNumber rest;
  
  ConsLoNumber (int first, ILoNumber rest) {
    this.first = first;
    this.rest = rest;
  }
  
  /* Template
   * Fields:
   * this.first ... int 
   * this.rest ... ILoNumber
   * Methods: 
   * this.sum() ... int
   * Methods of fields: 
   * this.rest.sum() ... int
   */
  
  // calculate sum of this non-empty list of numbers
  public int sum() {
    return this.first + this.rest.sum();
  }
  
}

// Union datatype for a list of Persons
interface ILoPerson {
  // total amount owed by this list for a given vig percent 
  double valueOfDownstream(double vigPercentage);  
}

// an empty list of persons
class MtLoPerson implements ILoPerson {

  // calculate the total vig for an empty list of persons
  public double valueOfDownstream(double vigPercentage) {
    return 0;
  }
}

// a non-empty list of persons
class ConsLoPerson implements ILoPerson {
  Person p;
  ILoPerson rest;
  
  ConsLoPerson (Person p, ILoPerson rest) {
    this.p = p;
    this.rest = rest;
  }
 
  /* Template
   * Fields:
   * this.p ... Person
   * this.rest ... ILoPerson  
   * Methods:
   * this.valueOfDownstream(double) ... double
   * Methods of Fields:
   * this.p.totalGrossincome(double) ... double
   * this.rest.valueOfDownstream(double) ... double
   */
  
  // calculate the vig owed by a non-empty list of persons
  public double valueOfDownstream(double vigPercentage) {
   return (this.p.totalGrossIncome(vigPercentage) * (vigPercentage / 100))
          + this.rest.valueOfDownstream(vigPercentage); 
  }
  
}

// class describing a person in a pyramid scheme
class Person {
  String name;
  int personalSalesProfit;
  ILoPerson immediateHires;
  
  Person(String name, int personalSalesProfit, ILoPerson immediateHires) {
    this.name = name;
    this.personalSalesProfit = personalSalesProfit;
    this.immediateHires = immediateHires;
  }
  
  /* Template 
   * Fields:
   * this.name ... String
   * this.personalSalesProfit ... int
   * this.immediateHires ... ILoPerson
   * Methods:
   * this.totalGrossIncome(double) ... double
   * Methods on Fields
   * this.immediateHires.valueOfDownstream(double)
   */
  
  // calculate this person's total income, not accounting for their vig 
  double totalGrossIncome(double vigPercentage) {
    return this.personalSalesProfit + immediateHires.valueOfDownstream(vigPercentage);
  }
}

// Examples for the Business Racket (Pyramid Scheme) lesson
class ExamplesBusinessRacket { 
  Person alice = 
   new Person ("Alice", 1000, new MtLoPerson ());
  Person bob = 
   new Person ("Bob", 1900, new MtLoPerson ());
  ILoPerson charliesDownstream = 
   new ConsLoPerson (this.alice, 
                     new ConsLoPerson (this.bob, new MtLoPerson()));
  Person charlie = new Person ("Charlie", 80, charliesDownstream);
  Person dennis = new Person ("Dennis", 20,  
   new ConsLoPerson (this.charlie, new MtLoPerson()));
  Person jason = new Person ("Jason", 0, 
      new ConsLoPerson (this.dennis, new MtLoPerson()));
  
  // check that basic income calculations work
  boolean testBasicIncomes (Tester t) {
    return t.checkExpect(this.alice.totalGrossIncome(20), 1000.0)
        && t.checkExpect(this.bob.totalGrossIncome(10), 1900.0);
  }
  // test that our downstream calculation work
  boolean testDownstreamCalculations (Tester t) { 
    return t.checkExpect(this.charliesDownstream.valueOfDownstream(50), 1450.0);
  }
  // test that our total Gross Income function works for larger examples
  boolean testLargerIncomeCalculation (Tester t) { 
    return t.checkExpect(this.charlie.totalGrossIncome(50), 1530.0);
  }
  
}

interface ILoPainting {
  // filter-in the Mondrian paintings in this list of paintings
  ILoPainting paintedByMondrian();
  // calculate the length of this list of paintings
  int length();
  // calculate the number of Mondrian paintings in this list of paintings
  int numOfMondrians();
  // return a sorted-by-title list of paintings 
  ILoPainting sortByTitle();
  // insert a painting in to this Sorted list of paintings
  ILoPainting insertPainting(Painting p);
}

// an empty list of paintings
class MtLoPainting implements ILoPainting {

  /* Template
   * Fields
   * Methods
   * this.paintedByMondrian() ... ILoPainting
   * this.length() ... int
   * this.numOfMondrians ... int
   * this.sortByTitle() ... ILoPainting
   * this.insertPainting(Painting) ... ILoPainting
   * Methods of Fields
   */
  
  // return a list of all the Mondrian paintings in an empty list
  public ILoPainting paintedByMondrian() {
    return this;
  }
  
  // return the number of paintings in an empty list
  public int length() {
    return 0;
  }
  
  // return the number of Mondrian paintings in an empty list
  public int numOfMondrians() {
    return 0;
  }
  
  // return a sorted empty list of paintings
  public ILoPainting sortByTitle() { 
    return this;
  }
  
  // insert a painting into an empty sorted list of paintings
  public ILoPainting insertPainting (Painting p) {
    return new ConsLoPainting(p,this);
  }
  
}

// a non-empty list of paintings
class ConsLoPainting implements ILoPainting {
  Painting p;
  ILoPainting rest;
  
  ConsLoPainting (Painting p, ILoPainting rest) {
    this.p = p;
    this.rest = rest;
  }
  /* Template
   * Fields:
   * this.p ... Painting
   * this.rest ... ILoPainting
   * Methods
   * this.paintedByMondrian() ... ILoPainting
   * this.length() ... int
   * this.numOfMondrians ... int
   * this.sortByTitle() ... ILoPainting
   * this.insertPainting(Painting) ... ILoPainting
   * Methods of Fields
   * this.p.isAMondrian() ... boolean
   * this.p.comesBeforeOther(Painting) ... boolean
   * this.rest.paintedByMondrian() ... ILoPainting
   * this.rest.length() ... int
   * this.rest.numOfMondrians ... int
   * this.rest.sortByTitle() ... ILoPainting
   * this.rest.insertPainting(Painting) ... ILoPainting
   */

  // return a list of the Mondrian paintings in this non-empty list of paintings 
  public ILoPainting paintedByMondrian() {
   if (this.p.isAMondrian()) {
     return new ConsLoPainting(this.p,this.rest.paintedByMondrian());
   }
   else {
     return this.rest.paintedByMondrian();
   }
  }
  
  // return the length of this non-empty list of paintings
  public int length() { 
    return 1 + this.rest.length();
  }
  
  // return the number of Mondrian paintings in this non-empty list of paintings
  public int numOfMondrians() {
    return this.paintedByMondrian().length();
  }
    
  // return a non-empty sorted-by-title list of paintings 
  public ILoPainting sortByTitle() { 
    return this.rest.sortByTitle().insertPainting(this.p);
  }
  
  // insert the other Painting into this sorted list of Paintings
  public ILoPainting insertPainting (Painting otherPainting) {
    if (this.p.comesBeforeOther(otherPainting)) {
      return new ConsLoPainting (this.p,this.rest.insertPainting(otherPainting));
    }
    else {
      return new ConsLoPainting (otherPainting,this);
    }
  }
}

// representation of a Painting
class Painting {
  Artist artist;
  String title; 
  double value; // in dollars 
  int year; 
  
  Painting (Artist artist, String title, double value, int year) { 
    this.artist = artist;
    this.title = title;
    this.value = value;
    this.year = year;
  }
  /* Template 
   * Fields:
   * this.artist ... Artist
   * this.title ... String
   * this.value ... double
   * this.year ... int
   * Methods:
   * this.isAMondrian() ... boolean
   * this.comesBeforeOther(Painting) ... boolean
   * Methods on Fields: 
   * this.artist.isMondrian() ... boolean
   */
  
  // return whether this painting is a Mondrian 
  boolean isAMondrian() { 
    return artist.isMondrian();
  }
  
  // determines if this Painting comes before the other Painting, lexicographically by title
  boolean comesBeforeOther(Painting otherPainting) {
    /* 
     * This is a little Java you didn't know.
     * The .compareTo method is a ternary function:
     * -1, 0, or 1 corresponding to less than, equal to, or greater than.
     * We use < 0 to achieve appropriate boolean behavior. 
     */
    return this.title.compareTo(otherPainting.title) < 0;
  }
  
}

// representation of an artist 
class Artist {
  String name;
  int birthYear;
  
  Artist (String name, int birthYear) {
    this.name = name;
    this.birthYear = birthYear;
  }
  
  /* Template 
   * Fields:
   * this.name ... String
   * this.birthYear ... int
   * Methods:
   * this.isMondrian() ... boolean
   * Methods on Fields: 
   *   
   */
  
  // check if this Artist has the name "Mondrian"
  boolean isMondrian() {
    return this.name.equals("Mondrian");
  }

}

// Examples for Paintings lesson 
class ExamplesPaintings { 
  Artist mondrian = new Artist ("Mondrian", 1850);
  Artist bruegel = new Artist ("Bruegel", 1480); 
  Artist rembrandt = new Artist ("Rembrandt", 1600);
  Painting composition = new Painting (this.mondrian, "Composition", 1500, 1920);
  Painting selfPortrait = new Painting (this.mondrian, "Self Portrait", 2300, 1902);
  Painting triumph = new Painting (this.bruegel, "Triumph of Death", 2000, 1520);
  Painting woman = new Painting (this.rembrandt, "Woman Bathing", 1950.5, 1720);
  ILoPainting gallery = 
      new ConsLoPainting(this.composition,
          new ConsLoPainting(this.triumph,
              new ConsLoPainting(this.woman,
                  new ConsLoPainting (this.selfPortrait,
                      new MtLoPainting()))));
  
  // test our counting of Mondrian paintings
  boolean testMondrianCount (Tester t) {
    return t.checkExpect(this.gallery.numOfMondrians(),2);
  }
  
  // test the painting order comparison function
  boolean testPaintingComparison (Tester t) { 
    return triumph.comesBeforeOther(woman) 
        && !(woman.comesBeforeOther(triumph));  
        
  }
  
  // test our sorting of a filtered list of paintings
  boolean testSortedMondrianPaintings (Tester t) { 
    return t.checkExpect(this.gallery.paintedByMondrian().sortByTitle(),
        new ConsLoPainting(this.composition,
            new ConsLoPainting (this.selfPortrait,
                new MtLoPainting())));
  }
  
  
}
