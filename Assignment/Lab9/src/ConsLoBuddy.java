// represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {

  Person first;
  ILoBuddy rest;

  ConsLoBuddy(Person first, ILoBuddy rest) {
    this.first = first;
    this.rest = rest;
  }

  // Effect: add a list of buddies to a given person, change his/her buddies list
  public void addTo(Person p) {
    p.addBuddy(this.first);
    this.rest.addTo(p);
  }

  // determine if a list of buddies has the same name as given one
  public boolean anySameName(String that) {
    return this.first.sameName(that) || this.rest.anySameName(that);
  }

  // count the number of common buddies
  public int countIntersect(ILoBuddy that) {
    if (that.anySamePerson(this.first)) {
      return 1 + that.countIntersect(this.rest);
    }
    else {
      return that.countIntersect(this.rest);
    }
  }

  // determine if a list of buddies has the same name as the given one
  public boolean anySamePerson(Person p) {
    return this.first.sameName(p.username) || this.rest.anySameName(p.username);
  }

  // count the number of a list of buddies
  public int size() {
    return 1 + this.rest.size();
  }

  // get all buddies
  public ILoBuddy inviteAll(ILoBuddy sofar) {
    return this.rest.inviteAll(this.first.getAllBuddiesAcc(sofar));
  }

  // get the max likelihood of given list of buddy
  public double getMax(double maxSofar, Person target, ILoDouble pathSofar, ILoBuddy visited) {
    if ((this.first.alreadyInVisited(visited)) || !(this.first.hasExtendedBuddy(target))) {
      return this.rest.getMax(maxSofar, target, pathSofar, visited);
    }
    else if (this.first.sameName(target.username)) {
      return this.rest.getMax(
          Math.max(maxSofar, new ConsLoDouble(this.first.hearing, pathSofar).likelihood()), target,
          new ConsLoDouble(this.first.hearing, pathSofar), visited);
    }
    else {
      return this.first.maxLikelihoodAcc(maxSofar, target,
          new ConsLoDouble(this.first.hearing, new ConsLoDouble(this.first.diction, pathSofar)),
          new ConsLoBuddy(this.first, visited));
    }
  }
}
