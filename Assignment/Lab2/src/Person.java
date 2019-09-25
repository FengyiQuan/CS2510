import tester.Tester;

// to represent a pet owner
class Person {
  String name;
  IPet pet;
  int age;

  Person(String name, IPet pet, int age) {
    this.name = name;
    this.pet = pet;
    this.age = age;
  }

  // is this Person older than the given Person?
  boolean isOlder(Person other) {
    return this.age > other.age;
  }

  // does the name of this person's pet match the given name?
  boolean sameNamePet(String name) {
    return this.pet.getName().equals(name);
  }
}

// to represent a pet
interface IPet {
  String getName();
}

// to represent a pet cat
class Cat implements IPet {
  String name;
  String kind;
  boolean longhaired;

  Cat(String name, String kind, boolean longhaired) {
    this.name = name;
    this.kind = kind;
    this.longhaired = longhaired;
  }

  public String getName() {
    return this.name;
  }
}

// to represent a pet dog
class Dog implements IPet {
  String name;
  String kind;
  boolean male;

  Dog(String name, String kind, boolean male) {
    this.name = name;
    this.kind = kind;
    this.male = male;
  }

  public String getName() {
    return this.name;
  }
}

class ExamplesPerson {
  IPet dog1 = new Dog("abc", "a", true);
  IPet dog2 = new Dog("sdf", "s", false);
  IPet cat1 = new Cat("qwe", "z", true);
  IPet cat2 = new Cat("wer", "x", false);

  Person petowner1 = new Person("sean", this.dog1, 20);
  Person petowner2 = new Person("nel", this.dog2, 10);
  Person petowner3 = new Person("pal", this.cat1, 15);
  Person petowner4 = new Person("kal", this.cat2, 50);
  
  boolean testGetName(Tester t) {
    return t.checkExpect(this.petowner1.sameNamePet("ppp"), false)
        && t.checkExpect(this.petowner1.sameNamePet("abc"), true);
  }
}