// to represent a sundae
interface ISundae {
}

//to represent scoop
class Scoop implements ISundae {
  String flavor;

  Scoop(String flavor) {
    this.flavor = flavor;
  }
}

class Topping implements ISundae {
  ISundae inner;
  String name;

  Topping(ISundae inner, String name) {
    this.inner = inner;
    this.name = name;
  }
}

class ExamplesSundae {
  ExamplesSundae() {
  }

  /*
   * yummy is a "chocolate" scoop topped by "rainbow sprinkles" topped by
   * "caramel" topped by "whipped cream" noThankyou is a "vanilla" scoop topped by
   * "chocolate sprinkles" topped by "fudge" topped by "plum sauce"
   */

  ISundae chocolate = new Scoop("chocolate");
  ISundae rainbowsprinkles = new Topping(this.chocolate, "rainbow sprinkles");
  ISundae caramel = new Topping(this.rainbowsprinkles, "caramel");
  ISundae yummy = new Topping(this.caramel, "whipped cream");
  ISundae vanilla = new Scoop("vanilla");
  ISundae chocolatesprinkles = new Topping(this.vanilla, "chocolate sprinkles");
  ISundae fudge = new Topping(this.chocolatesprinkles, "fudge");
  ISundae noThankYou = new Topping(this.fudge, "plum sauce");
}