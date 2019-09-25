import tester.Tester;

// Every road trip has two drivers, driver1 and driver2, as well as directions, which is a list of Direction
class RoadTrip2 {
  String driver1;
  String driver2;
  ILoDirection directions;

  RoadTrip2(String driver1, String driver2, ILoDirection directions) {
    this.driver1 = driver1;
    this.driver2 = driver2;
    this.directions = directions;
  }

  // transforms a road trip into a list of road trip chunks, based on the given
  // number of miles the drivers want to switch-off
  ILoRoadTripChunk splitUpTrip(int miles) {
    return this.splitUpTripAcc(miles, this.driver1, this.driver2, this.driver1);
  }

  //
  // accumulator:
  ILoRoadTripChunk splitUpTripAcc(int miles, String d1, String d2, String currentDriver) {
    return this.directions.getTripChunk(miles, miles, d1, d2, d1, new MtLoDirection());
  }

}

///////////////////////////////////////////////////////////////////////
interface ILoDirection {
  ILoRoadTripChunk getTripChunk(int miles, int milesSofar, String d1, String d2,
      String currentDriver, ILoDirection directionsSofar);

  ILoDirection reverse();

  ILoDirection append(ILoDirection that);
}

class MtLoDirection implements ILoDirection {

  public ILoDirection reverse() {
    // TODO Auto-generated method stub
    return this;
  }

  public ILoDirection append(ILoDirection that) {
    // TODO Auto-generated method stub
    return that;
  }

  public ILoRoadTripChunk getTripChunk(int miles, int milesSofar, String d1, String d2,
      String currentDriver, ILoDirection directionsSofar) {
    return new ConsLoRoadTripChunk(new RoadTripChunk(currentDriver, directionsSofar.reverse()),
        new MtLoRoadTripChunk());
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

  public ILoRoadTripChunk getTripChunk(int miles, int milesSofar, String d1, String d2,
      String currentDriver, ILoDirection directionsSofar) {

    if (milesSofar <= 0) {
      return new ConsLoRoadTripChunk(new RoadTripChunk(currentDriver, directionsSofar.reverse()),
          this.getTripChunk(miles, miles, d1, d2,
              new changeDriver(d1, d2).switchDriver(currentDriver), new MtLoDirection()));
    }

    else if (this.first.needMoreDirection(milesSofar)) {
      return this.rest.getTripChunk(miles, this.first.getNewMilesSofar(milesSofar), d1, d2, d1,
          new ConsLoDirection(this.first, directionsSofar));
    }
    else if (this.first.anotherDriveSameDirection(milesSofar)) {
      return new ConsLoRoadTripChunk(
          new RoadTripChunk(currentDriver,
              new ConsLoDirection(new Direction(
                  "Switch with " + new changeDriver(d1, d2).switchDriver(currentDriver),
                  milesSofar), directionsSofar).reverse()),
          (new ConsLoDirection(this.first.getLeftDirection(milesSofar), this.rest)).getTripChunk(
              miles, miles, d1, d2, new changeDriver(d1, d2).switchDriver(currentDriver),
              new MtLoDirection()));
    }
    else if (this.first.sameAsSetting(milesSofar)) {
      return new ConsLoRoadTripChunk(
          new RoadTripChunk(currentDriver,
              new ConsLoDirection(new Direction(
                  "Switch with " + new changeDriver(d1, d2).switchDriver(currentDriver),
                  milesSofar), new ConsLoDirection(this.first, directionsSofar)).reverse()),
          this.rest.getTripChunk(miles, miles, d1, d2,
              new changeDriver(d1, d2).switchDriver(currentDriver), new MtLoDirection()));
    }
    else {
      return new ConsLoRoadTripChunk(new RoadTripChunk("i love u", new MtLoDirection()),
          new MtLoRoadTripChunk());
    }
  }
}

// to represent two driver's name
class changeDriver {
  String d1;
  String d2;

  changeDriver(String d1, String d2) {
    this.d1 = d1;
    this.d2 = d2;
  }

  String switchDriver(String current) {
    if (current.equals(this.d1)) {
      return d2;
    }
    else {
      return d1;
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

// A RoadTripChunk has a driver as well as directions, which is a list of Direction.
class RoadTripChunk {
  String driver;
  ILoDirection directions;

  RoadTripChunk(String driver, ILoDirection directions) {
    this.driver = driver;
    this.directions = directions;
  }
}

////////////////////////////////////////////////////
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

  RoadTrip2 hazelAndHenry = new RoadTrip2("Hazel", "Henry", this.original);

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
  // boolean testSplitUpTrip(Tester t) {
  // return t.checkExpect(this.hazelAndHenry.splitUpTrip(15), this.splitAfter);
  // }

  // test for changeDriver
  boolean testchangeDriver(Tester t) {
    return t.checkExpect(new changeDriver("abc", "c").switchDriver("abc"), "c")
        && t.checkExpect(new changeDriver("abc", "c").switchDriver("c"), "abc");
  }

  // test for Direction
  boolean testDirection(Tester t) {
    return t.checkExpect(this.alber.getNewMilesSofar(20), 7)
        && t.checkExpect(this.dest.needMoreDirection(13), true)
        && t.checkExpect(this.dest.needMoreDirection(10), false)
        && t.checkExpect(this.fork.anotherDriveSameDirection(1), true)
        && t.checkExpect(this.fork.anotherDriveSameDirection(10), false) && t.checkExpect(
            this.shortOverpass.getLeftDirection(2), new Direction("Take the overpass", 1));
  }

  // test for MtLoDirection
  boolean testMtLoDirection(Tester t) {
    return t.checkExpect(this.mtr.getTripChunk(10, 3, "abc", "c", "c", this.mtr), this.mtc);
  }

  ConsLoDirection test1 = new ConsLoDirection(this.alber, this.mtr);
  ConsLoDirection test2 = new ConsLoDirection(this.alber, new ConsLoDirection(this.fork, this.mtr));

  // test for ConsLoDirection
  // boolean testConsLoDirection(Tester t) {
  // return t
  // .checkExpect(this.test1.getTripChunk(13, 13, "a", "b", "a", this.mtr),
  // new ConsLoRoadTripChunk(
  // new RoadTripChunk("a",
  // new ConsLoDirection(this.alber,
  // new ConsLoDirection(new Direction("Switch with b", 0), this.mtr))),
  // this.mtc));
  // }

  ILoRoadTripChunk test3 = new ConsLoRoadTripChunk(this.hazel1,
      new ConsLoRoadTripChunk(this.henry2, this.mtc));

  // test for MtLoRoadTripChunk
  boolean testMtLoRoadTripChunk(Tester t) {
    return t.checkExpect(this.mtc.append(this.mtc), this.mtc)
        && t.checkExpect(this.mtc.append(test3), test3)
        && t.checkExpect(this.mtc.reverse(), this.mtc);
  }

  // test for ConsLoRoadTripChunk
  boolean testConsLoRoadTripChunk(Tester t) {
    return t.checkExpect(new ConsLoRoadTripChunk(this.hazel1, this.mtc).append(this.mtc),
        new ConsLoRoadTripChunk(this.hazel1, this.mtc))
        && t.checkExpect(new ConsLoRoadTripChunk(this.hazel1, this.mtc).append(this.test3),
            new ConsLoRoadTripChunk(this.hazel1,
                new ConsLoRoadTripChunk(this.hazel1,
                    new ConsLoRoadTripChunk(this.henry2, this.mtc))))
        && t.checkExpect(new ConsLoRoadTripChunk(this.hazel1, this.mtc).reverse(),
            new ConsLoRoadTripChunk(this.hazel1, this.mtc))
        && t.checkExpect(test3.reverse(),
            new ConsLoRoadTripChunk(this.henry2, new ConsLoRoadTripChunk(this.hazel1, this.mtc)));

  }
}
