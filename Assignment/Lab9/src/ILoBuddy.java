
// represents a list of Person's buddies
interface ILoBuddy {
  // Effect: add a list of buddies to a given person, change his/her buddies list
  void addTo(Person p);

  // determine if a list of buddies has the same name as given one
  boolean anySameName(String that);

  // count the number of common buddies
  int countIntersect(ILoBuddy that);

  // determine if a list of buddies has the same name as the given one
  boolean anySamePerson(Person p);

  // count the number of a list of buddies
  int size();

  // get all buddies
  ILoBuddy inviteAll(ILoBuddy sofar);

  // get the max likelihood of given list of buddy
  double getMax(double maxSofar, Person target, ILoDouble pathSofar, ILoBuddy visited);
}
