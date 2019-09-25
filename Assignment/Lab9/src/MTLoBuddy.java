
// represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {
  MTLoBuddy() {
  }

  // Effect: add a list of buddies to a given person, change his/her buddies list
  public void addTo(Person p) {
    return;
  }

  // determine if a list of buddies has the same name as given one
  public boolean anySameName(String that) {
    return false;
  }

  // count the number of common buddies
  public int countIntersect(ILoBuddy that) {
    return 0;
  }

  // determine if a list of buddies has the same name as the given one
  public boolean anySamePerson(Person p) {
    return false;
  }

  // count the number of a list of buddies
  public int size() {
    return 0;
  }

  // get all buddies
  public ILoBuddy inviteAll(ILoBuddy sofar) {
    return sofar;
  }

  // get the max likelihood of given list of buddy
  public double getMax(double maxSofar, Person target, ILoDouble pathSofar, ILoBuddy visited) {
    return Math.max(maxSofar, pathSofar.likelihood());
  }
}
