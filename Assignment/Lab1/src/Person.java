class Address {
  String city;
  String state;

  Address(String city, String state) {
    this.city = city;
    this.state = state;
  }
}

interface IAT {
}

class Unknown implements IAT {
  Unknown() {
  }
}

class Person implements IAT {
  String name;
  int age;
  String gender;
  Address address;

  Person(String name, int age, String gender, Address address) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.address = address;
  }
}

class ExamplesAncestors {
  Address tAddress = new Address("Boston", "MA");
  Address kAddress = new Address("Warwick", "RI");
  Address rAddress = new Address("Nashua", "NH");
  Person tim = new Person("Tim", 23, "Male", this.tAddress);
  Person kate = new Person("Kate", 22, "Female", this.kAddress);
  Person rebecca = new Person("Rebecca", 31, "Non-binary", this.rAddress);
  IAT unknown = new Unknown();
}