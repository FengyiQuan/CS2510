import tester.Tester;

// to represent Bagel Recipe
class BagelRecipe {
  double flour;
  double water;
  double yeast;
  double salt;
  double malt;

  BagelRecipe(double flour, double water, double yeast, double salt, double malt) {
    Utils u = new Utils();

    this.flour = u.checkWeight(flour, water, "Invalid weight of flour: "
        + "The weight of the flour should be equal to the weight of the water.");
    this.water = water;
    this.yeast = u.checkWeight(yeast, malt,
        "Invalid weight of yeast: The weight of the yeast should be equal the weight of the malt.");
    this.salt = u.checkWeight(salt, flour * 1 / 20 - yeast, "Invalid weight of salt and yeast: "
        + "The weight of the salt + yeast should be 1/20th the weight of the flour");
    this.malt = malt;
  }

  // requires the weights of flour and yeast, and produces a perfect bagel recipe
  BagelRecipe(double flour, double yeast) {
    this(flour, flour, yeast, flour / 20 - yeast, yeast);
  }

  // takes in the flour, yeast and salt as volumes rather than weights
  BagelRecipe(double flour, double yeast, double salt) {
    this(flour * 17 / 4, flour * 17 / 4, yeast / 48 * 5, salt / 48 * 10, yeast / 48 * 5);
  }
  /*
   * fields:
   * this.flour ... double
   * this.water ... double
   * this.yeast ... double
   * this.salt ... double
   * this.malt ... double
   * 
   * methods:
   * this.sameRecipe(BagelRecipe other) ... boolean
   * 
   * methods for other:
   * other.sameRecipe(BagelRecipe) ... boolean
   */

  // check if the same ingredients have the same weights to within 0.001 ounces
  boolean sameRecipe(BagelRecipe other) {
    return this.flour - other.flour <= 0.001 && this.water - other.water <= 0.001
        && this.yeast - other.yeast <= 0.001 && this.salt - other.salt <= 0.001
        && this.malt - other.malt <= 0.001;
  }
}

class Utils {
  // check if given weight is legal
  double checkWeight(double one, double two, String message) {
    if (Math.abs(one - two) <= 0.0001) {
      return one;
    }
    else {
      throw new IllegalArgumentException(message);
    }
  }
}

class ExamplesBagelRecipe {
  BagelRecipe rightbr = new BagelRecipe(20.0, 20.0, 0.4, 0.6, 0.4);
  BagelRecipe rightbr2 = new BagelRecipe(20.0, 20.0, 0.4, 0.6, 0.4);
  BagelRecipe rightbr3 = new BagelRecipe(20.0, 20.0, 0.4, 0.6, 0.4);
  BagelRecipe br1 = new BagelRecipe(20.0, .5);
  BagelRecipe br2 = new BagelRecipe(10.0, 10.0);

  // test
  boolean testBagelRecipe(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Invalid weight of salt and yeast: "
            + "The weight of the salt + yeast should be 1/20th the weight of the flour"),
        "BagelRecipe", 1.0, 1.0, 1.0, 1.0, 1.0)
        && t.checkConstructorException(
            new IllegalArgumentException("Invalid weight of flour: "
                + "The weight of the flour should be equal to the weight of the water."),
            "BagelRecipe", 20.0, 10.0, 0.4, 0.6, 0.4)
        && t.checkConstructorException(
            new IllegalArgumentException("Invalid weight of yeast: "
                + "The weight of the yeast should be equal the weight of the malt."),
            "BagelRecipe", 20.0, 20.0, 0.4, 0.6, 0.3)
        && t.checkExpect(this.br1, new BagelRecipe(20.0, 20.0, .5, .5, .5))
        && t.checkExpect(this.rightbr.sameRecipe(this.rightbr), true)
        && t.checkExpect(this.rightbr.sameRecipe(this.rightbr2), true)
        && t.checkExpect(this.rightbr2.sameRecipe(this.rightbr), true)
        && t.checkExpect(this.rightbr2.sameRecipe(this.rightbr3), true)
        && t.checkExpect(this.rightbr.sameRecipe(this.rightbr3), true)
        && t.checkExpect(this.rightbr.sameRecipe(this.br1), false);
  }
}
