import tester.Tester;

interface IShape {
  <R> R accept(IShapeVisitor<R> visitor);
}

class Circle implements IShape {
  int x, y;
  int radius;
  String color;

  Circle(int x, int y, int r, String color) {
    this.x = x;
    this.y = y;
    this.radius = r;
    this.color = color;
  }

  public <R> R accept(IShapeVisitor<R> visitor) {
    return visitor.visitCircle(this);
  }
}

class Rect implements IShape {
  int x, y, w, h;
  String color;

  Rect(int x, int y, int w, int h, String color) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.color = color;
  }

  public <R> R accept(IShapeVisitor<R> visitor) {
    return visitor.visitRect(this);
  }
}

interface IFunc<X, Y> {
}

interface IShapeVisitor<R> extends IFunc<IShape, R> {
  R visitCircle(Circle c);

  R visitRect(Rect r);
}

class ShapeArea implements IShapeVisitor<Double> {
  public Double visitCircle(Circle c) {
    return Math.PI * c.radius * c.radius;
  }

  public Double visitRect(Rect r) {
    return (double) r.w * r.h;
  }
}

class BiggerThan10 implements IShapeVisitor<Boolean> {

  public Boolean visitCircle(Circle c) {
    return c.accept(new ShapeArea()) > 10;
  }

  public Boolean visitRect(Rect r) {
    return r.accept(new ShapeArea()) > 10;
  }

}

class example {
  IShape c1 = new Circle(10, 10, 10, "red");
  IShape r1 = new Rect(10, 10, 10, 10, "black");

  boolean testShapeArea(Tester t) {
    return t.checkInexact(c1.accept(new ShapeArea()), 314.159, 0.001);
  }

  boolean testBiggerThan10(Tester t) {
    return t.checkExpect(c1.accept(new BiggerThan10()), true);
  }
}