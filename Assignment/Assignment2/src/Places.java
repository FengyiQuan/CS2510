import tester.Tester;

// to represent Place
class Place {
  String name;
  ILoFeature features;

  Place(String name, ILoFeature f) {
    this.name = name;
    this.features = f;
  }
  // fields:
  // this.name ... String
  // this.features ... ILoFeature

  // methods:
  // this.totalCapacity() ... int
  // this.foodinessRating() ... double
  // this.placeSumRating() ... double
  // this.placeAllRestaurant() ... int
  // this.restaurantInfo() ... String
  // this.restaurantInfoWithComma ... String

  // methods for fields:
  // this.features.lofCapacity() ... int
  // this.features.loftotalRating() ... double
  // this.features.loftotalRestaurant() ... int
  // this.features.lofRestaurantInfo() ... String

  // computes the total available seating in all the Venue reachable from the
  // current place
  int totalCapacity() {
    return this.features.lofCapacity();
  }

  // computes the average rating of all the restaurants’ ratings, for all the
  // restaurants reachable from the current place
  double foodinessRating() {
    if (this.placeAllRestaurant() == 0) {
      return 0.0;
    }
    else {
      return this.placeSumRating() / this.placeAllRestaurant();
    }
  }

  // computes the sum of all features in a Place
  double placeSumRating() {
    return this.features.loftotalRating();
  }

  // counts the total number of restaurants in a Place
  int placeAllRestaurant() {
    return this.features.loftotalRestaurant();
  }

  // produces one String that has in it all names of restaurants reachable from
  // a place, their food types in parentheses, and each restaurant separated by
  // comma and space
  String restaurantInfo() {
    if (this.restaurantInfoWithComma().length() == 0) {
      return "";
    }
    else {
      return this.restaurantInfoWithComma().substring(0,
          this.restaurantInfoWithComma().length() - 2);
    }
  }

  // produces one String that has in it all names of restaurants reachable from
  // a place, their food types in parentheses, each restaurant separated by
  // comma and space, and leaves a comma and a space in the end of output String
  String restaurantInfoWithComma() {
    return this.features.lofRestaurantInfo();
  }
}

// to represent list of Feature
interface ILoFeature {
  // computes the total available seating in all the venues reachable from
  // given ILoFeature
  int lofCapacity();

  // computes total number of all restaurant rating, for all the restaurants
  // reachable from given ILoFeature
  double loftotalRating();

  // computes the total number of restaurant, for all the restaurants reachable
  // from given ILoFeature
  int loftotalRestaurant();

  // returns all restaurant information by given a ILoFeature
  String lofRestaurantInfo();
}

class MtLoFeature implements ILoFeature {

  // computes the total capacity in an empty list of Feature
  public int lofCapacity() {
    return 0;
  }

  // computes the sum of rating in an empty list of Feature
  public double loftotalRating() {
    return 0.0;
  }

  // computes the total counts of restaurant in an empty list of Feature
  public int loftotalRestaurant() {
    return 0;
  }

  // returns empty String in an empty list of Feature
  public String lofRestaurantInfo() {
    return "";
  }
}

class ConsLoFeature implements ILoFeature {
  IFeature first;
  ILoFeature rest;

  ConsLoFeature(IFeature first, ILoFeature rest) {
    this.first = first;
    this.rest = rest;
  }
  // fields:
  // this.first ... IFeature
  // this.rest ... ILoFeature

  // methods:
  // this.lofCapacity() ... int
  // this.loftotalRating() ... double
  // this.loftotalRestaurant() ... int
  // this.lofRestaurantInfo() ... String

  // methods for fields:
  // this.first.featureCapacity() ... int
  // this.first.getRestaurantRating() ... double
  // this.first.add1IfRestaurant() ... int
  // this.first.getRestaurantInfo() ... String
  // this.rest.lofCapacity() ... int
  // this.rest.loftotalRating() ... double
  // this.rest.loftotalRestaurant() ... int
  // this.rest.lofRestaurantInfo() ... String

  // computes all capacity in a list of Feature
  public int lofCapacity() {
    return this.first.featureCapacity() + this.rest.lofCapacity();
  }

  // computes the sum of restaurant rating in a list of Feature
  public double loftotalRating() {
    return this.first.getRestaurantRating() + this.rest.loftotalRating();
  }

  // computes total number of restaurants in a list of Feature
  public int loftotalRestaurant() {
    return this.first.add1IfRestaurant() + this.rest.loftotalRestaurant();
  }

  // returns all Restaurant information
  public String lofRestaurantInfo() {
    return this.first.getRestaurantInfo() + this.rest.lofRestaurantInfo();
  }
}

interface IFeature {
  // get capacity by given IFeature
  int featureCapacity();

  // get restaurant rating by given IFeature
  double getRestaurantRating();

  // return 1 if IFeature is a restaurant
  int add1IfRestaurant();

  // return information if restaurant
  String getRestaurantInfo();
}

// to represent Restaurant
class Restaurant implements IFeature {
  String name;
  String type;
  double averageRating;

  Restaurant(String name, String type, double ar) {
    this.name = name;
    this.type = type;
    this.averageRating = ar;
  }

  // fields:
  // this.name ... String
  // this.type ... String
  // this.averageRating ... double

  // methods:
  // this.featureCapacity() ... int
  // this.getRestaurantRating() ... double
  // this.add1IfRestaurant() ... int
  // this.getRestaurantInfo() ... String

  // return 0 if IFeature is a restaurant
  public int featureCapacity() {
    return 0;
  }

  // get average rating of a restaurant
  public double getRestaurantRating() {
    return this.averageRating;
  }

  // count 1 if it is a restaurant
  public int add1IfRestaurant() {
    return 1;
  }

  // returns a restaurant information
  public String getRestaurantInfo() {
    return this.name + " (" + this.type + "), ";
  }
}

// to represent Venue
class Venue implements IFeature {
  String name;
  String type;
  int capacity;

  Venue(String name, String type, int capacity) {
    this.name = name;
    this.type = type;
    this.capacity = capacity;
  }

  // fields:
  // this.name ... String
  // this.type ... String
  // this.capacity ... int

  // methods:
  // this.featureCapacity() ... int
  // this.getRestaurantRating() ... double
  // this.add1IfRestaurant() ... int
  // this.getRestaurantInfo() ... String

  // return its capacity
  public int featureCapacity() {
    return this.capacity;
  }

  // return 0 if it is not restaurant
  public int add1IfRestaurant() {
    return 0;
  }

  // return 0.0 if it is not restaurant
  public double getRestaurantRating() {
    return 0.0;
  }

  // returns "" if it is not Restaurant
  public String getRestaurantInfo() {
    return "";
  }
}

// to represent Shuttle Bus
class ShuttleBus implements IFeature {
  String name;
  Place destination;

  ShuttleBus(String name, Place d) {
    this.name = name;
    this.destination = d;
  }

  // fields:
  // this.name ... String
  // this.destination ... Place

  // methods:
  // this.featureCapacity() ... int
  // this.getRestaurantRating() ... double
  // this.add1IfRestaurant() ... int
  // this.getRestaurantInfo() ... String

  // methods for fields:
  // this.destination.foodinessRating() ... double
  // this.destination.totalCapacity() ... int
  // this.destination.placeAllRestaurant() ... int
  // this.destination.placeSumRating() ... double
  // this.destination.restaurantInfo() ... String
  // this.destination.restaurantInfoWithComma() ... String

  // get a total capacity of a Place that Shuttle bus can reach
  public int featureCapacity() {
    return this.destination.totalCapacity();
  }

  // count all number of restaurant of a Place that Shuttle bus can reach
  public int add1IfRestaurant() {
    return this.destination.placeAllRestaurant();
  }

  // get the sum of restaurant's rating of a Place that Shuttle bus can reach
  public double getRestaurantRating() {
    return this.destination.placeSumRating();
  }

  // return all restaurant infor of a Place that Shuttle bus can reach
  public String getRestaurantInfo() {
    return this.destination.restaurantInfoWithComma();
  }
}

class ExamplesPlaces {
  IFeature tdGarden = new Venue("TD Garden", "stadium", 19580);
  IFeature theDailyCatch = new Restaurant("The Daily Catch", "Sicilian", 4.4);
  ILoFeature mtf = new MtLoFeature();
  ILoFeature n = new ConsLoFeature(this.tdGarden, new ConsLoFeature(this.theDailyCatch, this.mtf));
  Place northEnd = new Place("North End", this.n);

  IFeature freshmen15 = new ShuttleBus("Freshmen-15", this.northEnd);
  IFeature borderCafe = new Restaurant("Border Cafe", "Tex-Mex", 4.5);
  IFeature harvardStadium = new Venue("Harvard Stadium", "football", 30323);
  ILoFeature h = new ConsLoFeature(this.freshmen15,
      new ConsLoFeature(this.borderCafe, new ConsLoFeature(this.harvardStadium, this.mtf)));
  Place harvard = new Place("Harvard", this.h);

  IFeature littleItalyExpress = new ShuttleBus("Little Italy Express", this.northEnd);
  IFeature reginasPizza = new Restaurant("Regina's Pizza", "pizza", 4.0);
  IFeature crimsonCruiser = new ShuttleBus("Crimson Cruiser", this.harvard);
  IFeature bostonCommon = new Venue("Boston Common", "public", 150000);
  ILoFeature s = new ConsLoFeature(this.littleItalyExpress, new ConsLoFeature(this.reginasPizza,
      new ConsLoFeature(this.crimsonCruiser, new ConsLoFeature(this.bostonCommon, this.mtf))));
  Place southStation = new Place("South Station", this.s);

  IFeature sarkuJapan = new Restaurant("Sarku Japan", "teriyaki", 3.9);
  IFeature starbucks = new Restaurant("Starbucks", "coffee", 4.1);
  IFeature bridgeShuttle = new ShuttleBus("bridge shuttle", this.southStation);
  ILoFeature c = new ConsLoFeature(this.sarkuJapan,
      new ConsLoFeature(this.starbucks, new ConsLoFeature(this.bridgeShuttle, this.mtf)));
  Place cambridgeSide = new Place("CambridgeSide Galleria", this.c);

  // test for Place:
  boolean testPlace(Tester t) {
    return t.checkExpect(this.northEnd.totalCapacity(), 19580)
        && t.checkExpect(this.harvard.totalCapacity(), 49903)
        && t.checkExpect(this.cambridgeSide.totalCapacity(), 219483)
        && t.checkExpect(this.northEnd.foodinessRating(), 4.4)
        && t.checkExpect(this.harvard.foodinessRating(), 4.45)
        && t.checkInexact(this.cambridgeSide.foodinessRating(), 4.2, 0.01)
        && t.checkExpect(this.northEnd.placeSumRating(), 4.4)
        && t.checkExpect(this.harvard.placeSumRating(), 8.9)
        && t.checkInexact(this.cambridgeSide.placeSumRating(), 25.3, 0.01)
        && t.checkExpect(this.northEnd.placeAllRestaurant(), 1)
        && t.checkExpect(this.harvard.placeAllRestaurant(), 2)
        && t.checkExpect(this.cambridgeSide.placeAllRestaurant(), 6)
        && t.checkExpect(this.northEnd.restaurantInfo(), "The Daily Catch (Sicilian)")
        && t.checkExpect(this.harvard.restaurantInfo(),
            "The Daily Catch (Sicilian), Border Cafe (Tex-Mex)")
        && t.checkExpect(this.cambridgeSide.restaurantInfo(),
            "Sarku Japan (teriyaki), Starbucks (coffee), The Daily Catch (Sicilian), Regina's "
                + "Pizza (pizza), The Daily Catch (Sicilian), Border Cafe (Tex-Mex)")
        && t.checkExpect(this.northEnd.restaurantInfoWithComma(), "The Daily Catch (Sicilian), ")
        && t.checkExpect(this.harvard.restaurantInfoWithComma(),
            "The Daily Catch (Sicilian), Border Cafe (Tex-Mex), ")
        && t.checkExpect(this.cambridgeSide.restaurantInfoWithComma(),
            "Sarku Japan (teriyaki), Starbucks (coffee), The Daily Catch (Sicilian), Regina's "
                + "Pizza (pizza), The Daily Catch (Sicilian), Border Cafe (Tex-Mex), ");
  }

  // test for MtLoFeature:
  boolean testMtLoFeature(Tester t) {
    return t.checkExpect(this.mtf.lofCapacity(), 0) && t.checkExpect(this.mtf.loftotalRating(), 0.0)
        && t.checkExpect(this.mtf.loftotalRestaurant(), 0)
        && t.checkExpect(this.mtf.lofRestaurantInfo(), "");
  }

  // test for ConsLoFeature:
  boolean testConsLoFeature(Tester t) {
    return t.checkExpect(this.n.lofCapacity(), 19580) && t.checkExpect(this.h.lofCapacity(), 49903)
        && t.checkExpect(this.c.lofCapacity(), 219483)
        && t.checkExpect(this.n.loftotalRating(), 4.4)
        && t.checkExpect(this.h.loftotalRating(), 8.9)
        && t.checkInexact(this.c.loftotalRating(), 25.3, 0.01)
        && t.checkExpect(this.n.loftotalRestaurant(), 1)
        && t.checkExpect(this.h.loftotalRestaurant(), 2)
        && t.checkExpect(this.c.loftotalRestaurant(), 6)
        && t.checkExpect(this.n.lofRestaurantInfo(), "The Daily Catch (Sicilian), ")
        && t.checkExpect(this.h.lofRestaurantInfo(),
            "The Daily Catch (Sicilian), Border Cafe (Tex-Mex), ")
        && t.checkExpect(this.c.lofRestaurantInfo(),
            "Sarku Japan (teriyaki), Starbucks (coffee), The Daily Catch (Sicilian), Regina's "
                + "Pizza (pizza), The Daily Catch (Sicilian), Border Cafe (Tex-Mex), ");
  }

  // test for Restaurant
  boolean testRestaurant(Tester t) {
    return t.checkExpect(this.theDailyCatch.featureCapacity(), 0)
        && t.checkExpect(this.theDailyCatch.getRestaurantRating(), 4.4)
        && t.checkExpect(this.theDailyCatch.add1IfRestaurant(), 1)
        && t.checkExpect(this.borderCafe.getRestaurantInfo(), "Border Cafe (Tex-Mex), ");
  }

  // test for Venue:
  boolean testVenue(Tester t) {
    return t.checkExpect(this.tdGarden.featureCapacity(), 19580)
        && t.checkExpect(this.harvardStadium.getRestaurantInfo(), "")
        && t.checkExpect(this.bostonCommon.getRestaurantRating(), 0.0)
        && t.checkExpect(this.harvardStadium.add1IfRestaurant(), 0);
  }

  // test for ShuttleBus
  boolean testShuttleBus(Tester t) {
    return t.checkExpect(this.freshmen15.featureCapacity(), 19580)
        && t.checkExpect(this.freshmen15.getRestaurantRating(), 4.4)
        && t.checkExpect(this.crimsonCruiser.add1IfRestaurant(), 2)
        && t.checkExpect(this.bridgeShuttle.getRestaurantInfo(), "The Daily Catch (Sicilian), "
            + "Regina's Pizza (pizza), The Daily Catch (Sicilian), Border Cafe (Tex-Mex), ");
  }
}

// the reason why it will double-count:
/*
 * it will double-count because the example, cambridgeSide, went North End
 * twice. Harvard's shuttle bus will goes to North End once, and South Station's
 * shuttle bus ill goes to North End once. So every time when methods goes from
 * CambridgeSide Galleria, it will goes to North End twice. All information in
 * North End example will count as twice. There three methods - totalCapacity,
 * foodinessRating and restaurantInfo - will double North End twice.
 */