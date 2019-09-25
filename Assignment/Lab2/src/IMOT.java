import tester.Tester;

// Represents a mode of transportation
interface IMOT {
  boolean meetsFuelEfficiency(int targetEfficiency);
}

// Represents a bicycle as a mode of transportation
class Bicycle implements IMOT {
  String brand;

  Bicycle(String brand) {
    this.brand = brand;
  }

  public boolean meetsFuelEfficiency(int targetEfficiency) {
    return true;
  }
}

// Represents a car as a mode of transportation
class Car implements IMOT {
  String make;
  int mpg; // represents the fuel efficiency in miles per gallon

  Car(String make, int mpg) {
    this.make = make;
    this.mpg = mpg;
  }

  public boolean meetsFuelEfficiency(int targetEfficiency) {
    return this.mpg >= targetEfficiency;
  }
}

// Keeps track of how a person is transported
class Person {
  String name;
  IMOT mot;

  Person(String name, IMOT mot) {
    this.name = name;
    this.mot = mot;
  }

  // Does this person's mode of transportation meet the given fuel
  // efficiency target (in miles per gallon)?
  boolean motMetsFuelEfficiency(int targetEfficiency) {
    return this.mot.meetsFuelEfficiency(targetEfficiency);
  }
}

class ExamplesPerson {
  IMOT diamondback = new Bicycle("Diamondback");
  IMOT toyota = new Car("Toyota", 30);
  IMOT lamborghini = new Car("Lamborghini", 17);

  Person bob = new Person("Bob", diamondback);
  Person ben = new Person("Ben", toyota);
  Person becca = new Person("Becca", lamborghini);

  boolean testMotMetsFuelEfficiency(Tester t) {
    return t.checkExpect(this.bob.motMetsFuelEfficiency(100), true)
        && t.checkExpect(this.ben.motMetsFuelEfficiency(15), true)
        && t.checkExpect(this.becca.motMetsFuelEfficiency(20), false)
        && t.checkExpect(this.becca.motMetsFuelEfficiency(17), true);
  }

  boolean testMeetsFuelEfficiency(Tester t) {
    return t.checkExpect(this.diamondback.meetsFuelEfficiency(500), true)
        && t.checkExpect(this.lamborghini.meetsFuelEfficiency(5), true)
        && t.checkExpect(this.toyota.meetsFuelEfficiency(40), false);
  }
}