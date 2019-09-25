import tester.Tester;

// Every road trip has two drivers, driver1 and driver2, as well as directions, which is a list of Direction
class RoadTrip {
  String driver1;
  String driver2;
  ILoDirection directions;

  RoadTrip(String driver1, String driver2, ILoDirection directions) {
    this.driver1 = driver1;
    this.driver2 = driver2;
    this.directions = directions;
  }
  // transforms a road trip into a list of road trip chunks, based on the given
  // number of miles the drivers want to switch-off

  ILoRoadTripChunk splitUpTrip(int miles) {
    return this.splitUpTripAcc(miles, this.driver1, this.driver2);
  }

  //
  // accumulator: keep track of current driver
  ILoRoadTripChunk splitUpTripAcc(int miles, String d1, String d2) {
    return this.directions.getTripChunk(miles, miles, d1, d2, new MtLoDirection());
  }
}

///////////////////////////////////////////////////////////////////////
interface ILoDirection {
  ILoRoadTripChunk getTripChunk(int miles, int milesSofar, String d1, String d2,
      ILoDirection directionsSofar);

  ILoDirection reverse();

  ILoDirection append(ILoDirection that);

  boolean directionsSofarIsNotEmpty();
}

class MtLoDirection implements ILoDirection {

  public ILoDirection reverse() {
    return this;
  }

  public ILoDirection append(ILoDirection that) {
    return that;
  }

  public boolean directionsSofarIsNotEmpty() {
    return false;
  }

  public ILoRoadTripChunk getTripChunk(int miles, int milesSofar, String d1, String d2,
      ILoDirection directionsSofar) {
    if (directionsSofar.directionsSofarIsNotEmpty()) {
      return new ConsLoRoadTripChunk(new RoadTripChunk(d1, directionsSofar.reverse()),
          new MtLoRoadTripChunk());
    }
    else {
      return new MtLoRoadTripChunk();
    }
  }
}

class ConsLoDirection implements ILoDirection {
  Direction first;
  ILoDirection rest;

  ConsLoDirection(Direction first, ILoDirection rest) {
    this.first = first;
    this.rest = rest;
  }

  public ILoDirection reverse() {
    return this.rest.reverse().append(new ConsLoDirection(this.first, new MtLoDirection()));
  }

  public ILoDirection append(ILoDirection that) {
    return new ConsLoDirection(this.first, this.rest.append(that));
  }

  public boolean directionsSofarIsNotEmpty() {
    return true;
  }

  public ILoRoadTripChunk getTripChunk(int miles, int milesSofar, String d1, String d2,
      ILoDirection directionsSofar) {
    if (milesSofar == 0) {
      return new ConsLoRoadTripChunk(new RoadTripChunk(d1,
          new ConsLoDirection(new Direction("Switch with " + d2, 0), directionsSofar).reverse()),
          this.rest.getTripChunk(miles, miles, d2, d1, new MtLoDirection()));
    }

    else if (this.first.needMoreDirection(milesSofar)) {
      return this.rest.getTripChunk(miles, this.first.getNewMilesSofar(milesSofar), d1, d2,
          new ConsLoDirection(this.first, directionsSofar));
    }
    else if (this.first.anotherDriveSameDirection(milesSofar)) {
      return new ConsLoRoadTripChunk(
          new RoadTripChunk(d1,
              new ConsLoDirection(new Direction("Switch with " + d2, milesSofar), directionsSofar)
                  .reverse()),
          (new ConsLoDirection(this.first.getLeftDirection(milesSofar), this.rest))
              .getTripChunk(miles, miles, d2, d1, new MtLoDirection()));
    }

    else if (this.first.sameAsSetting(milesSofar)) {
      return this.rest.getTripChunk(miles, miles, d1, d2,
          new ConsLoDirection(this.first, directionsSofar));
    }
    else {
      return new ConsLoRoadTripChunk(new RoadTripChunk("i love u", new MtLoDirection()),
          new MtLoRoadTripChunk());
    }
  }
}

//A Direction has a description, and a number of miles
class Direction {
  String description;
  int miles;

  Direction(String description, int miles) {
    this.description = description;
    this.miles = miles;
  }

  int getNewMilesSofar(int milesSofar) {
    return milesSofar - this.miles;
  }

  boolean needMoreDirection(int milesSofar) {
    return this.miles < milesSofar;
  }

  boolean anotherDriveSameDirection(int milesSofar) {
    return this.miles > milesSofar;
  }

  boolean sameAsSetting(int milesSofar) {
    return this.miles == milesSofar;
  }

// if milesSofar is less than this direction miles, returns a new direction
// whose miles is difference between miles and milesSofar
  Direction getLeftDirection(int milesSofar) {
    return new Direction(this.description, this.miles - milesSofar);
  }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////

interface ILoRoadTripChunk {
  ILoRoadTripChunk reverse();

  ILoRoadTripChunk append(ILoRoadTripChunk that);
}

class MtLoRoadTripChunk implements ILoRoadTripChunk {

  @Override
  public ILoRoadTripChunk reverse() {
    // TODO Auto-generated method stub
    return this;
  }

  @Override
  public ILoRoadTripChunk append(ILoRoadTripChunk that) {
    // TODO Auto-generated method stub
    return that;
  }

}

class ConsLoRoadTripChunk implements ILoRoadTripChunk {
  RoadTripChunk first;
  ILoRoadTripChunk rest;

  ConsLoRoadTripChunk(RoadTripChunk first, ILoRoadTripChunk rest) {
    this.first = first;
    this.rest = rest;
  }

  @Override
  public ILoRoadTripChunk reverse() {
    return this.rest.reverse().append(new ConsLoRoadTripChunk(this.first, new MtLoRoadTripChunk()));
  }

  @Override
  public ILoRoadTripChunk append(ILoRoadTripChunk that) {
    return new ConsLoRoadTripChunk(this.first, this.rest.append(that));
  }
}

//A RoadTripChunk has a driver as well as directions, which is a list of Direction.
class RoadTripChunk {
  String driver;
  ILoDirection directions;

  RoadTripChunk(String driver, ILoDirection directions) {
    this.driver = driver;
    this.directions = directions;
  }
}

class Examples {
  ILoDirection mtr = new MtLoDirection();

  Direction alber = new Direction("Make a left at Alberquerque", 13);
  Direction fork = new Direction("Make a right at the fork", 2);
  Direction nextFork = new Direction("Make a left at the next fork", 3);
  Direction overpass = new Direction("Take the overpass", 45);
  Direction shortOverpass = new Direction("Take the overpass", 3);
  Direction dest = new Direction("Destination on left", 12);

  ILoDirection original = new ConsLoDirection(this.alber,
      new ConsLoDirection(this.fork, new ConsLoDirection(this.nextFork,
          new ConsLoDirection(this.overpass, new ConsLoDirection(this.dest, this.mtr)))));

  RoadTrip hazelAndHenry = new RoadTrip("Hazel", "Henry", this.original);

  ILoRoadTripChunk mtc = new MtLoRoadTripChunk();

  RoadTripChunk hazel1 = new RoadTripChunk("Hazel",
      new ConsLoDirection(this.alber, new ConsLoDirection(this.fork,
          new ConsLoDirection(new Direction("Switch with Henry", 0), this.mtr))));
  RoadTripChunk henry2 = new RoadTripChunk("Henry", new ConsLoDirection(this.nextFork,
      new ConsLoDirection(new Direction("Switch with Hazel", 12), this.mtr)));
  RoadTripChunk hazel3 = new RoadTripChunk("Hazel",
      new ConsLoDirection(new Direction("Switch with Henry", 15), this.mtr));
  RoadTripChunk henry4 = new RoadTripChunk("Henry",
      new ConsLoDirection(new Direction("Switch with Hazel", 15), this.mtr));
  RoadTripChunk hazel5 = new RoadTripChunk("Hazel",
      new ConsLoDirection(this.shortOverpass, new ConsLoDirection(this.dest, this.mtr)));

  ILoRoadTripChunk splitAfter = new ConsLoRoadTripChunk(this.hazel1,
      new ConsLoRoadTripChunk(this.henry2, new ConsLoRoadTripChunk(this.hazel3,
          new ConsLoRoadTripChunk(this.henry4, new ConsLoRoadTripChunk(this.hazel5, this.mtc)))));

  // test for splitUpTrip

  }
