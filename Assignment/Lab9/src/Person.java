
// represents a Person with a user name and a list of buddies
class Person {

  String username;
  ILoBuddy buddies;
  double diction;
  double hearing;

  Person(String username) {
    this.username = username;
    this.buddies = new MTLoBuddy();
  }

  Person(String username, double diction, double hearing) {
    this.username = username;
    this.buddies = new MTLoBuddy();
    this.diction = diction;
    this.hearing = hearing;
  }

  // EFFECT: Change this person's buddy list so that it includes the given person
  void addBuddy(Person buddy) {
    this.buddies = new ConsLoBuddy(buddy, this.buddies);
  }

  // Effect: Change this person's buddy list so that it includes the given buddies
  // list
  void addBuddies(ILoBuddy list) {
    list.addTo(this);
  }

  // returns true if this Person has that as a direct buddy
  boolean hasDirectBuddy(Person that) {
    return this.buddies.anySameName(that.username);
  }

  // returns the number of people who will show up at the party
  // given by this person
  int partyCount() {
    return this.getAllBuddies().size();
  }

  // returns the number of people that are direct buddies
  // of both this and that person
  int countCommonBuddies(Person that) {
    return this.buddies.countIntersect(that.buddies);
  }

  // will the given person be invited to a party
  // organized by this person?
  boolean hasExtendedBuddy(Person that) {
    return this.getAllBuddies().anySamePerson(that);
  }

  // determine if two names are the same
  boolean sameName(String name) {
    return this.username.equals(name);
  }

  // get all buddies for this person (no duplication)
  ILoBuddy getAllBuddies() {
    return this.getAllBuddiesAcc(new MTLoBuddy());
  }

  // Accumulator: keep track of visited buddies already
  ILoBuddy getAllBuddiesAcc(ILoBuddy sofar) {
    if (this.alreadyInVisited(sofar)) {
      return sofar;
    }
    else {
      return this.buddies.inviteAll(new ConsLoBuddy(this, sofar));
    }
  }

  // determine if this person is already invited
  boolean alreadyInVisited(ILoBuddy sofar) {
    return sofar.anySamePerson(this);
  }

  // compute the maximum likelihood that person X can convey a message correctly
  // to person Y
  double maxLikelihood(Person y) {
    if (this.sameName(y.username)) {
      return 1;
    }
    else if (!this.hasExtendedBuddy(y)) {
      return 0;
    }
    else {
      return this.maxLikelihoodAcc(0, y, new ConsLoDouble(this.diction, new MtLoDouble()),
          new ConsLoBuddy(this, new MTLoBuddy()));
    }
  }

  // compute the maximum likelihood that person X can convey a message correctly
  // to person Y
  // Accumulator: keep track of the current max likelihood, double so far, already
  // visited buddies
  double maxLikelihoodAcc(double maxSofar, Person target, ILoDouble pathSofar, ILoBuddy visited) {
    return this.buddies.getMax(maxSofar, target, pathSofar, visited);
  }
}
