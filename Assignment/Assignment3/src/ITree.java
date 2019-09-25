import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.*;
import java.awt.Color;

interface ITree {

  // renders ITree to a picture
  WorldImage draw();

  // computes whether any of the twigs in the tree (either stems or branches) are
  // pointing downward rather than upward
  boolean isDrooping();

  // takes the current tree and a given tree and produces a Branch using the given
  // arguments, with this tree on the left and the given tree on the right
  ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree givenTree);

  // rotate a ITree in the given degree
  ITree rotate(double theta);

  // returns the width of the tree
  double getWidth();

  // get the most left length in a ITree
  public double getTheMostLeft(double mostLeftSofar, double distanceToRoot);

  // get the most right length in a ITree
  public double getTheMostRight(double mostRightSofar, double distanceToRoot);
}

class Leaf implements ITree {
  int size; // represents the radius of the leaf
  Color color; // the color to draw it

  Leaf(int size, Color c) {
    this.size = size;
    this.color = c;
  }

  /*
   * fields:
   * this.size ... int
   * this.color ... Color
   * 
   * methods:
   * this.draw() ... WorldImage
   * this.isDrooping() ... boolean
   * this.combine(int leftLength, int rightLength, double leftTheta, double
   * rightTheta, ITree givenTree) ... ITree
   * this.rotate(double theta) ... ITree
   * this.getWidth() ... double
   * this.getTheMostLeft(double mostLeftSofar, double distanceToRoot) ... double
   * this.getTheMostRight(double mostRightSofar, double distanceToRoot) ... double
   */

  // draw a leaf
  public WorldImage draw() {
    return new CircleImage(this.size, "solid", this.color);
  }

  // computes whether any of the twigs in the tree (either stems or branches) are
  // pointing downward rather than upward
  public boolean isDrooping() {
    return false;
  }

  // takes the current tree and a given tree and produces a Branch using the given
  // arguments
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree givenTree) {
    return new Branch(leftLength, rightLength, leftTheta, rightTheta, this.rotate(leftTheta - 90),
        givenTree.rotate(rightTheta - 90));
  }

  // rotate a Leaf in the given degree
  public ITree rotate(double theta) {
    return this;
  }

  // returns the width of the tree
  public double getWidth() {
    return this.size * 2;
  }

  // get the most left length in a Leaf
  public double getTheMostLeft(double mostLeftSofar, double distanceToRoot) {
    // TODO Auto-generated method stub
    return Math.min(mostLeftSofar, distanceToRoot - this.size);
  }

  // get the most right length in a Leaf
  public double getTheMostRight(double mostRightSofar, double distanceToRoot) {
    return Math.max(mostRightSofar, distanceToRoot + this.size);
  }
}

class Stem implements ITree {
  // How long this stick is
  int length;
  // The angle (in degrees) of this stem, relative to the +x axis
  double theta;
  // The rest of the tree
  ITree tree;

  Stem(int length, double theta, ITree tree) {
    this.length = length;
    this.theta = theta;
    this.tree = tree;
  }
  /*
   * fields:
   * this.length ... int
   * this.theta ... double
   * this.tree ... ITree
   * 
   * methods:
   * this.draw() ... WorldImage
   * this.isDrooping() ... boolean
   * this.combine(int leftLength, int rightLength, double leftTheta, double
   * rightTheta, ITree givenTree) ... ITree
   * this.rotate(double theta) ... ITree
   * this.getWidth() ... double
   * this.getTheMostLeft(double mostLeftSofar, double distanceToRoot) ... double
   * this.getTheMostRight(double mostRightSofar, double distanceToRoot) ... double
   * 
   * methods for fields:
   * this.tree.draw() ... WorldImage
   * this.tree.isDrooping() ... boolean
   * this.tree.combine(int leftLength, int rightLength, double leftTheta, double
   * rightTheta, ITree givenTree) ... ITree
   * this.tree.rotate(double theta) ... ITree
   * this.tree.getWidth() ... double
   * this.tree.getTheMostLeft(double mostLeftSofar, double distanceToRoot) ...
   * double
   * this.tree.getTheMostRight(double mostRightSofar, double distanceToRoot) ...
   * double
   */

  // draw a stem
  public WorldImage draw() {
    double radius = Math.toRadians(-theta);
    int xlength = (int) (Math.cos(radius) * this.length);
    int ylength = (int) (Math.sin(radius) * this.length);
    Posn newPinhole = new Posn(xlength * 1 / 2, ylength * 1 / 2);

    return new OverlayImage(this.tree.draw(),
        new LineImage(new Posn(xlength, ylength), Color.orange).movePinholeTo(newPinhole))
            .movePinhole(-xlength, -ylength);
  }

  // computes whether any of the twigs in the tree (either stems or branches) are
  // pointing downward rather than upward
  public boolean isDrooping() {
    return (this.theta % 360) > 180 || this.tree.isDrooping();
  }

  // takes the current tree and a given tree and produces a Branch using the given
  // arguments
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree givenTree) {
    return new Branch(leftLength, rightLength, leftTheta, rightTheta, this.rotate(leftTheta - 90),
        givenTree.rotate(rightTheta - 90));
  }

  // rotate a Stem in the given degree
  public ITree rotate(double theta) {
    return new Stem(this.length, this.theta + theta, this.tree.rotate(theta));
  }

  // returns the width of the tree
  public double getWidth() {
    return this.getTheMostRight(0, 0) - this.getTheMostLeft(0, 0);
  }

  // get the most left length in a Stem
  public double getTheMostLeft(double mostLeftSofar, double distanceToRoot) {
    double radius = Math.toRadians(-theta);
    double xlength = (Math.cos(radius) * this.length);

    return this.tree.getTheMostLeft(Math.min(mostLeftSofar, distanceToRoot + xlength),
        distanceToRoot + xlength);
  }

  // get the most right length in a Stem
  public double getTheMostRight(double mostRightSofar, double distanceToRoot) {
    double radius = Math.toRadians(-theta);
    double xlength = (Math.cos(radius) * this.length);

    return this.tree.getTheMostRight(Math.max(mostRightSofar, distanceToRoot + xlength),
        distanceToRoot + xlength);
  }
}

class Branch implements ITree {
  // How long the left and right branches are
  int leftLength;
  int rightLength;
  // The angle (in degrees) of the two branches, relative to the +x axis,
  double leftTheta;
  double rightTheta;
  // The remaining parts of the tree
  ITree left;
  ITree right;

  Branch(int ll, int rl, double lt, double rt, ITree l, ITree r) {
    this.leftLength = ll;
    this.rightLength = rl;
    this.leftTheta = lt;
    this.rightTheta = rt;
    this.left = l;
    this.right = r;
  }
  /*
   * fields:
   * this.leftLength ... int
   * this.rightLength ... int
   * this.leftTheta ... double
   * this.rightTheta ... double
   * this.left ... ITree
   * this.right ... ITree
   * 
   * methods:
   * this.draw() ... WorldImage
   * this.isDrooping() ... boolean
   * this.combine(int leftLength, int rightLength, double leftTheta, double
   * rightTheta, ITree givenTree) ... ITree
   * this.rotate(double theta) ... ITree
   * this.getWidth() ... double
   * this.getTheMostLeft(double mostLeftSofar, double distanceToRoot) ... double
   * this.getTheMostRight(double mostRightSofar, double distanceToRoot) ... double
   * 
   * methods for fields:
   * this.left.draw() ... WorldImage
   * this.left.isDrooping() ... boolean
   * this.left.combine(int leftLength, int rightLength, double leftTheta, double
   * rightTheta, ITree givenTree) ... ITree
   * this.left.rotate(double theta) ... ITree
   * this.left.getWidth() ... double
   * this.left.getTheMostLeft(double mostLeftSofar, double distanceToRoot) ...
   * double
   * this.left.getTheMostRight(double mostRightSofar, double distanceToRoot) ...
   * double
   * this.right.draw() ... WorldImage
   * this.right.isDrooping() ... boolean
   * this.right.combine(int leftLength, int rightLength, double leftTheta, double
   * rightTheta, ITree givenTree) ... ITree
   * this.right.rotate(double theta) ... ITree
   * this.right.getWidth() ... double
   * this.right.getTheMostLeft(double mostLeftSofar, double distanceToRoot) ...
   * double
   * this.right.getTheMostRight(double mostRightSofar, double distanceToRoot) ...
   * double
   */

  // draw a branch
  public WorldImage draw() {
    double leftradius = Math.toRadians(-leftTheta);
    double rightRaidus = Math.toRadians(-rightTheta);
    int leftxlength = (int) (Math.cos(leftradius) * this.leftLength);
    int leftylength = (int) (Math.sin(leftradius) * this.leftLength);
    int rightxlength = (int) (Math.cos(rightRaidus) * this.rightLength);
    int rightylength = (int) (Math.sin(rightRaidus) * this.rightLength);

    return new OverlayImage(
        new OverlayImage(this.left.draw(),
            new LineImage(new Posn(leftxlength, leftylength), Color.orange)
                .movePinholeTo(new Posn(leftxlength * 1 / 2, leftylength * 1 / 2)))
                    .movePinhole(-leftxlength, -leftylength),
        new OverlayImage(this.right.draw(),
            new LineImage(new Posn(rightxlength, rightylength), Color.orange)
                .movePinholeTo(new Posn(rightxlength * 1 / 2, rightylength * 1 / 2)))
                    .movePinhole(-rightxlength, -rightylength));
  }

  // computes whether any of the twigs in the tree (either stems or branches) are
  // pointing downward rather than upward
  public boolean isDrooping() {
    return (this.leftTheta % 360) > 180 || (this.rightTheta % 360) > 180 || this.left.isDrooping()
        || this.right.isDrooping();
  }

  // takes the current tree and a given tree and produces a Branch using the given
  // arguments
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree givenTree) {
    return new Branch(leftLength, rightLength, leftTheta, rightTheta, this.rotate(leftTheta - 90),
        givenTree.rotate(rightTheta - 90));
  }

  // rotate a branch in the given degree
  public ITree rotate(double theta) {
    return new Branch(this.leftLength, this.rightLength, this.leftTheta + theta,
        this.rightTheta + theta, this.left.rotate(theta), this.right.rotate(theta));
  }

  // returns the width of the tree
  public double getWidth() {
    return this.getTheMostRight(0, 0) - this.getTheMostLeft(0, 0);
  }

  // get the most left length in a Branch
  public double getTheMostLeft(double mostLeftSofar, double distanceToRoot) {
    double leftradius = Math.toRadians(-leftTheta);
    double rightRaidus = Math.toRadians(-rightTheta);
    double leftxlength = (Math.cos(leftradius) * this.leftLength);
    double rightxlength = (Math.cos(rightRaidus) * this.rightLength);

    return Math.min(
        this.left.getTheMostLeft(Math.min(mostLeftSofar, distanceToRoot + leftxlength),
            distanceToRoot + leftxlength),
        this.right.getTheMostLeft(Math.min(mostLeftSofar, distanceToRoot + rightxlength),
            distanceToRoot + rightxlength));
  }

  // get the most right length in a Branch
  public double getTheMostRight(double mostRightSofar, double distanceToRoot) {
    double leftradius = Math.toRadians(-leftTheta);
    double rightRaidus = Math.toRadians(-rightTheta);
    double leftxlength = (Math.cos(leftradius) * this.leftLength);
    double rightxlength = (Math.cos(rightRaidus) * this.rightLength);

    return Math.max(
        this.left.getTheMostRight(Math.max(mostRightSofar, distanceToRoot + leftxlength),
            distanceToRoot + leftxlength),
        this.right.getTheMostRight(Math.max(mostRightSofar, distanceToRoot + rightxlength),
            distanceToRoot + rightxlength));
  }
}

class ExamplesTree {
  ITree leaf1 = new Leaf(10, Color.LIGHT_GRAY);
  ITree leaf2 = new Leaf(10, Color.ORANGE);

  ITree stem1 = new Stem(100, 135, this.leaf1);
  ITree stem2 = new Stem(50, 45, this.stem1);
  ITree downStem = new Stem(1, 190, this.leaf1);

  ITree tree1 = new Branch(30, 30, 135, 40, new Leaf(10, Color.RED), this.leaf1);
  ITree tree2 = new Branch(30, 30, 115, 65, new Leaf(15, Color.GREEN), this.tree1);
  ITree TREE1 = new Branch(30, 30, 135, 40, new Leaf(10, Color.RED), new Leaf(15, Color.BLUE));
  ITree TREE2 = new Branch(30, 30, 115, 65, new Leaf(15, Color.GREEN), new Leaf(8, Color.ORANGE));
  ITree given = new Branch(40, 50, 150, 30, TREE1, TREE2);
  ITree downTree = new Branch(40, 50, 150, 30, this.downStem, TREE1);

  ITree right = new Branch(100, 30, 135, 110, new Leaf(10, Color.RED), new Leaf(15, Color.GREEN));
  ITree bigRight = new Branch(1, 30, 135, 40, new Leaf(10, Color.RED), this.right);

  ITree cleft = new Stem(50, 45, this.leaf1);
  ITree cright = new Stem(50, 135, this.leaf1);
  ITree cross = new Branch(10, 10, 135, 45, this.cleft, this.cright);
  WorldImage a = new VisiblePinholeImage(new OverlayImage(
      new LineImage(new Posn(50, 50), Color.orange).movePinholeTo(new Posn(50, 50)),
      new LineImage(new Posn(-300, 400), Color.orange).movePinholeTo(new Posn(50, 50))));

  // test for draw
  boolean testDrawTree(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    return t.checkExpect(this.leaf1.draw(), this.leaf1.draw())
        && t.checkExpect(this.stem1.draw(), this.stem1.draw())
        && t.checkExpect(this.given.draw(), this.given.draw())
        && c.drawScene(s.placeImageXY(cross.draw(), 250, 250)) && c.show();
  }

  // test for isDrooping
  boolean testIsDrooping(Tester t) {
    return t.checkExpect(this.leaf1.isDrooping(), false)
        && t.checkExpect(this.stem1.isDrooping(), false)
        && t.checkExpect(this.downStem.isDrooping(), true)
        && t.checkExpect(this.TREE1.isDrooping(), false)
        && t.checkExpect(this.downTree.isDrooping(), true);
  }

  // test for combine
  boolean testCombine(Tester t) {
    return t.checkExpect(this.leaf1.combine(5, 5, 30, 30, this.leaf2),
        new Branch(5, 5, 30, 30, this.leaf1, this.leaf2))
        && t.checkExpect(this.leaf1.combine(5, 5, 30, 30, this.stem1),
            new Branch(5, 5, 30, 30, this.leaf1, new Stem(100, 75, this.leaf1)));
  }

  // test for rotate
  boolean testRotate(Tester t) {
    return t.checkExpect(this.leaf1.rotate(135), this.leaf1)
        && t.checkExpect(this.stem1.rotate(10), new Stem(100, 145, this.leaf1))
        && t.checkExpect(this.tree1.rotate(10),
            new Branch(30, 30, 145, 50, new Leaf(10, Color.RED), this.leaf1));
  }

  // test for getWidth
  boolean testGetWidth(Tester t) {
    return t.checkExpect(this.leaf1.getWidth(), 20.0)
        && t.checkInexact(this.stem1.getWidth(), 80.71068, 0.00001)
        && t.checkInexact(this.tree1.getWidth(), 64.19453, 0.00001)
        && t.checkInexact(this.tree1.getWidth(), 64.19453, 0.00001)
        && t.checkInexact(this.given.getWidth(), 129.83403, 0.00001)
        && t.checkInexact(this.cross.getWidth(), 76.56834, 0.00001);
  }

  // test for getTheMostLeft
  boolean testGetTheMostLeft(Tester t) {
    return t.checkExpect(this.leaf1.getTheMostLeft(-50, -45), -55.0)
        && t.checkInexact(this.stem2.getTheMostLeft(0, 0), -45.35534, 0.00001)
        && t.checkInexact(this.tree1.getTheMostLeft(0, 0), -31.21320, 0.00001)
        && t.checkInexact(this.given.getTheMostLeft(0, 0), -65.85421, 0.00001)
        && t.checkInexact(this.bigRight.getTheMostLeft(0, 0), -57.72935, 0.00001)
        && t.checkInexact(this.cross.getTheMostLeft(0, 0), -38.28417, 0.00001);
  }

  // test for getTheMostRight
  boolean testGetTheMostRight(Tester t) {
    return t.checkExpect(this.leaf1.getTheMostRight(50, 45), 55.0)
        && t.checkExpect(this.stem1.getTheMostRight(0, 0), 0.0)
        && t.checkInexact(this.tree1.getTheMostRight(0, 0), 32.98133, 0.00001)
        && t.checkInexact(this.given.getTheMostRight(0, 0), 63.97982, 0.00001)
        && t.checkInexact(this.cross.getTheMostRight(0, 0), 38.28417, 0.00001);
  }
}
