import tester.Tester;

// a campus tour
class CampusTour {
  int startTime; // minutes from midnight
  ITourLocation startingLocation;

  CampusTour(int startTime, ITourLocation startingLocation) {
    this.startTime = startTime;
    this.startingLocation = startingLocation;
  }

  // is this tour the same tour as the given one?
  boolean sameTour(CampusTour other) {
    // TODO: complete this method
    return this.startTime == other.startTime
        && this.startingLocation.sameTourLocation(other.startingLocation);
  }
}

///////////////////////////////////////////////////////////////////////////////
// a spot on the tour
interface ITourLocation {
  boolean sameTourLocation(ITourLocation other);

  boolean sameTourEnd(TourEnd other);

  boolean sameMandatory(Mandatory other);

  boolean sameBranchingTour(BranchingTour other);
}

abstract class ATourLocation implements ITourLocation {
  String speech; // the speech to give at this spot on the tour

  ATourLocation(String speech) {
    this.speech = speech;
  }
}

// the end of the tour
class TourEnd extends ATourLocation {
  ICampusLocation location;

  TourEnd(String speech, ICampusLocation location) {
    super(speech);
    this.location = location;
  }

  public boolean sameTourLocation(ITourLocation other) {
    if (other instanceof TourEnd) {
      return this.sameTourEnd((TourEnd) other);
    }
    else {
      return false;
    }
  }

  public boolean sameTourEnd(TourEnd other) {
    return this.speech.equals(other.speech) && this.location.sameCampusLocation(other.location);
  }

  @Override
  public boolean sameMandatory(Mandatory other) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean sameBranchingTour(BranchingTour other) {
    // TODO Auto-generated method stub
    return false;
  }
}

//a mandatory spot on the tour with the next place to go
class Mandatory extends ATourLocation {
  ICampusLocation location;
  ITourLocation next;

  Mandatory(String speech, ICampusLocation location, ITourLocation next) {
    super(speech);
    this.location = location;
    this.next = next;
  }

  public boolean sameTourLocation(ITourLocation other) {
    if (other instanceof Mandatory) {
      return this.sameMandatory((Mandatory) other);
    }
    else {
      return false;
    }
  }

  public boolean sameMandatory(Mandatory other) {
    return this.speech.equals(other.speech) && this.location.sameCampusLocation(other.location)
        && this.next.sameTourLocation(other.next);

  }

  @Override
  public boolean sameTourEnd(TourEnd other) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean sameBranchingTour(BranchingTour other) {
    // TODO Auto-generated method stub
    return false;
  }
}

// up to the tour guide where to go next
class BranchingTour extends ATourLocation {
  ITourLocation option1;
  ITourLocation option2;

  BranchingTour(String speech, ITourLocation option1, ITourLocation option2) {
    super(speech);
    this.option1 = option1;
    this.option2 = option2;
  }

  public boolean sameTourLocation(ITourLocation other) {
    if (other instanceof BranchingTour) {
      return this.sameBranchingTour((BranchingTour) other);
    }
    else {
      return false;
    }
  }

  public boolean sameBranchingTour(BranchingTour other) {
    return this.speech.equals(other.speech) && ((this.option1.sameTourLocation(other.option1)
        && this.option2.sameTourLocation(other.option2))
        || (this.option1.sameTourLocation(other.option2)
            && this.option2.sameTourLocation(other.option1)));
  }

  @Override
  public boolean sameTourEnd(TourEnd other) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean sameMandatory(Mandatory other) {
    // TODO Auto-generated method stub
    return false;
  }
}

///////////////////////////////////////////////////////////////////////////////
interface ICampusLocation {
  boolean sameCampusLocation(ICampusLocation other);

  public boolean sameBuilding(Building other);

  boolean sameQuad(Quad other);
}

class Building implements ICampusLocation {
  String name;
  Address address;

  Building(String name, Address address) {
    this.name = name;
    this.address = address;
  }

  public boolean sameCampusLocation(ICampusLocation other) {
    if (other instanceof Building) {
      return this.sameBuilding((Building) other);
    }
    else {
      return false;
    }
  }

  public boolean sameBuilding(Building other) {
    return this.name.equals(other.name) && this.address.sameAddress(other.address);
  }

  @Override
  public boolean sameQuad(Quad other) {
    // TODO Auto-generated method stub
    return false;
  }
}

class Address {
  String street;
  int number;

  Address(String street, int number) {
    this.number = number;
    this.street = street;
  }

  boolean sameAddress(Address other) {
    return this.number == other.number && this.street.equals(other.street);
  }
}

class Quad implements ICampusLocation {
  String name;
  ILoCampusLocation surroundings; // in clockwise order, starting north

  Quad(String name, ILoCampusLocation surroundings) {
    this.name = name;
    this.surroundings = surroundings;
  }

  public boolean sameCampusLocation(ICampusLocation other) {
    if (other instanceof Quad) {
      return this.sameQuad((Quad) other);
    }
    else {
      return false;
    }
  }

  public boolean sameQuad(Quad other) {
    return this.name.equals(other.name)
        && this.surroundings.sameLoCampusLocation(other.surroundings);
  }

  @Override
  public boolean sameBuilding(Building other) {
    // TODO Auto-generated method stub
    return false;
  }
}

interface ILoCampusLocation {
  boolean sameLoCampusLocation(ILoCampusLocation other);

  boolean sameMtLoCampusLocation(MtLoCampusLocation other);

  boolean sameConsLoCampusLocation(ConsLoCampusLocation other);
}

class MtLoCampusLocation implements ILoCampusLocation {

  public boolean sameLoCampusLocation(ILoCampusLocation other) {
    if (other instanceof MtLoCampusLocation) {
      return this.sameMtLoCampusLocation((MtLoCampusLocation) other);
    }
    else {
      return false;
    }
  }

  public boolean sameMtLoCampusLocation(MtLoCampusLocation other) {
    return true;
  }

  @Override
  public boolean sameConsLoCampusLocation(ConsLoCampusLocation other) {
    // TODO Auto-generated method stub
    return false;
  }
}

class ConsLoCampusLocation implements ILoCampusLocation {
  ICampusLocation first;
  ILoCampusLocation rest;

  ConsLoCampusLocation(ICampusLocation first, ILoCampusLocation rest) {
    this.first = first;
    this.rest = rest;
  }

  public boolean sameLoCampusLocation(ILoCampusLocation other) {
    if (other instanceof ConsLoCampusLocation) {
      return this.sameConsLoCampusLocation((ConsLoCampusLocation) other);
    }
    return false;
  }

  public boolean sameConsLoCampusLocation(ConsLoCampusLocation other) {
    return this.first.sameCampusLocation(other.first) && this.rest.sameLoCampusLocation(other.rest);
  }

  @Override
  public boolean sameMtLoCampusLocation(MtLoCampusLocation other) {
    // TODO Auto-generated method stub
    return false;
  }
}

class ExamplesCampus {
  Address address1 = new Address("123 St", 123);
  Address address1_1 = new Address("123 St", 123);
  Address address2 = new Address("234 St", 234);

  Building building1 = new Building("building", this.address1);
  Building building1_1 = new Building("building", this.address1);
  Building building2 = new Building("building2", this.address1);

  ICampusLocation Ibuilding1 = new Building("building", this.address1);
  ICampusLocation Ibuilding1_1 = new Building("building", this.address1);

  MtLoCampusLocation mtcl = new MtLoCampusLocation();

  ConsLoCampusLocation surrounding1 = new ConsLoCampusLocation(this.building1, this.mtcl);
  ConsLoCampusLocation surrounding1_1 = new ConsLoCampusLocation(this.building1, this.mtcl);

  ILoCampusLocation Imtcl = new MtLoCampusLocation();
  ILoCampusLocation Isurrounding1 = new ConsLoCampusLocation(this.building1, this.mtcl);
  ILoCampusLocation Isurrounding1_1 = new ConsLoCampusLocation(this.building1, this.mtcl);
  ILoCampusLocation Isurrounding1_2 = new ConsLoCampusLocation(this.Ibuilding1_1, this.mtcl);

  ICampusLocation Iquad = new Quad("quad", this.Isurrounding1);

  Quad quad1 = new Quad("quad", this.surrounding1);
  Quad quad1_1 = new Quad("quad", this.surrounding1);
  Quad quad2 = new Quad("quadx", this.surrounding1);

  // test for same address
  boolean testSameAddress(Tester t) {
    return t.checkExpect(this.address1.sameAddress(address1), true)
        && t.checkExpect(this.address1.sameAddress(address1_1), true)
        && t.checkExpect(this.address1.sameAddress(address2), false);
  }

  // test for same building
  boolean testSameBuilding(Tester t) {
    return t.checkExpect(this.building1.sameBuilding(this.building1), true)
        && t.checkExpect(this.building1.sameBuilding(this.building1_1), true)
        && t.checkExpect(this.building1.sameBuilding(this.building2), false)
        && t.checkExpect(this.quad1.sameBuilding(this.building2), false)
        && t.checkExpect(this.Ibuilding1.sameBuilding(this.building1), true);
  }

  // test for same quad
  boolean testSameQuad(Tester t) {
    return t.checkExpect(this.Iquad.sameQuad(this.quad1), true)
        && t.checkExpect(this.building1.sameQuad(this.quad1), false)
        && t.checkExpect(this.Iquad.sameQuad(this.quad1_1), true);
  }

  // test for same MtLoCampusLocation
  boolean testSameMtLoCampusLocation(Tester t) {
    return t.checkExpect(this.Imtcl.sameMtLoCampusLocation(this.mtcl), true)
        && t.checkExpect(this.Isurrounding1.sameMtLoCampusLocation(this.mtcl), false);
  }

  // test for same ConsLoCampusLocation
  boolean testSameConsLoCampusLocation(Tester t) {
    return t.checkExpect(this.mtcl.sameConsLoCampusLocation(this.surrounding1), false)
        && t.checkExpect(this.Isurrounding1.sameConsLoCampusLocation(this.surrounding1_1), true)
        && t.checkExpect(this.surrounding1.sameConsLoCampusLocation(this.surrounding1_1), true);
  }

  // test for same LoCampusLocation
  boolean testSameLoCampusLocation(Tester t) {
    return t.checkExpect(this.mtcl.sameLoCampusLocation(this.surrounding1), false)
        && t.checkExpect(this.Isurrounding1.sameLoCampusLocation(this.surrounding1_1), true)
        && t.checkExpect(this.surrounding1.sameLoCampusLocation(this.surrounding1_1), true)
        && t.checkExpect(this.Isurrounding1.sameLoCampusLocation(this.surrounding1), true);
  }
}
