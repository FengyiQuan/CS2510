import tester.Tester;
 
interface IDoll {
  // is this doll the same as the given one?
  boolean sameDoll(IDoll doll);
}
 
class GenericDoll implements IDoll {
  String brand;
 
  GenericDoll(String brand) {
    this.brand = brand;
  }
 
  // Is the given doll the same as this generic doll?
  public boolean sameDoll(IDoll that) {
    return that instanceof GenericDoll
        && this.brand.equals(((GenericDoll) that).brand);
  }
}
 
class Barbie extends GenericDoll {
 
  boolean isKen;
 
  Barbie(boolean isKen) {
    super("Barbie");
    this.isKen = isKen;
  }
 
  // Is the given doll the same as this Barbie?
  public boolean sameDoll(IDoll that) {
    return that instanceof Barbie
        && this.isKen == ((Barbie) that).isKen;
  }
}
 
 
/**
Vocab reminder:
sameness being reflexive means:
  x is the same as x
sameness being symmetric means:
  if x is the same as y then y is the same as x
sameness being transitive means:
  if a is the same as b and b is the same as c then a is the same as c
*/
 
class ExamplesDoll {
  IDoll genericBarbie = new GenericDoll("Barbie");
  IDoll kenBarbie = new Barbie(true);
 
 
  boolean testA(Tester t) {
    return t.checkExpect(genericBarbie.sameDoll(genericBarbie), true) &&
        t.checkExpect(kenBarbie.sameDoll(kenBarbie), true);
  }
 
  boolean testB(Tester t) {
    return t.checkExpect(kenBarbie.sameDoll(genericBarbie), false);
  }
 
  boolean testC(Tester t) {
    return t.checkExpect(genericBarbie.sameDoll(kenBarbie), false);
  }
}