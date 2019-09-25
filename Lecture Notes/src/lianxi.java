interface IShape {
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

interface IShapeVisitor<R> extends IFunc<IShape, R>{
  R visitCircle(Circle c);

  R visitRect(Rect r);
}

class ShapeArea implements IShapeVisitor<Double> {
  public Double visitCircle(Circle c) {
    return Math.PI * c.radius * c.radius;
  }
  public Double visitRect(Rect r) {
    return r.w * r.h;
  }

}