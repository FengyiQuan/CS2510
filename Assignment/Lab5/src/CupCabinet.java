import java.awt.Color;

class CupCabinet {
  int shelves;
  ILoCup cups;

  CupCabinet(int shelves, ILoCup cups) {
    this.shelves = shelves;
    this.cups = cups;
  }
}

interface ILoCup {
}

class MtLoCup implements ILoCup {
}

class ConsLoCup implements ILoCup {
  ICup first;
  ILoCup rest;

  ConsLoCup(ICup first, ILoCup rest) {
    this.first = first;
    this.rest = rest;
  }
}

interface ICup {
  // determines if a given mug is the same as this one
  boolean sameMug(ICup other);
}

abstract class ACup implements ICup {
  int oz;

  ACup(int oz) {
    this.oz = oz;
  }
}

class Mug extends ACup {
  String text; // the quirky text on a mug (possibly empty)
  Color color;

  Mug(int oz, String text, Color color) {
    super(oz);
    this.text = text;
    this.color = color;
  }

}

class Goblet extends ACup {
  boolean hasJewels; // does this goblet have jewels?

  Goblet(int oz, boolean hasJewels) {
    super(oz);
    this.hasJewels = hasJewels;
  }
}

class Glass extends ACup {
  int chips; // the number of chips on this potentially jagged glass

  Glass(int oz, int chips) {
    super(oz);
    this.chips = chips;
  }
}