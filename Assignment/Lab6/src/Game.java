import tester.*; // The tester library
import javalib.worldimages.*; // images, like RectangleImage or OverlayImages
import javalib.funworld.*; // the abstract World class and the big-bang library
import javalib.worldcanvas.WorldCanvas;

import java.awt.Color; // general colors (as triples of red,green,blue values)
                       // and predefined colors (Color.RED, Color.GRAY, etc.)

class MyPosn extends Posn {

  // standard constructor
  MyPosn(int x, int y) {
    super(x, y);
  }

  // constructor to convert from a Posn to a MyPosn
  MyPosn(Posn p) {
    this(p.x, p.y);
  }

  MyPosn add(MyPosn other) {
    return new MyPosn(this.x + other.x, this.y + other.y);
  }

  boolean isOffscreen(double h, double w) {
    return this.x > w || this.y > h;
  }
}

class Circle {

  MyPosn position; // in pixels
  MyPosn velocity; // in pixels/tick
  int radius;
  Color color;

  Circle(MyPosn position, MyPosn velocity, int radius, Color color) {
    this.position = position;
    this.velocity = velocity;
    this.radius = radius;
    this.color = color;
  }

  Circle move() {
    return new Circle(this.position.add(this.velocity), this.velocity, this.radius, this.color);
  }

  boolean isOffscreen(double h, double w) {
    return this.position.isOffscreen(h, w);
  }

  WorldImage draw() {
    return new CircleImage(this.radius, "Solid", this.color);
  }

  WorldScene place(WorldScene ws) {
    return ws.placeImageXY(this.draw(), this.position.x, this.position.y);
  }
}

interface ILoCircle {
  ILoCircle moveAll();

  ILoCircle removeOffscreen(double h, double w);

  WorldScene placeAll(WorldScene ws);
}

class MtLoCircle implements ILoCircle {

  @Override
  public ILoCircle moveAll() {
    // TODO Auto-generated method stub
    return this;
  }

  @Override
  public ILoCircle removeOffscreen(double h, double w) {
    // TODO Auto-generated method stub
    return this;
  }

  @Override
  public WorldScene placeAll(WorldScene ws) {
    // TODO Auto-generated method stub
    return ws;
  }
}

class ConsLoCircle implements ILoCircle {
  Circle first;
  ILoCircle rest;

  ConsLoCircle(Circle f, ILoCircle r) {
    this.first = f;
    this.rest = r;
  }

  @Override
  public ILoCircle moveAll() {
    // TODO Auto-generated method stub
    return new ConsLoCircle(this.first.move(), this.rest.moveAll());
  }

  @Override
  public ILoCircle removeOffscreen(double h, double w) {
    if (this.first.isOffscreen(h, w)) {
      return this.rest.removeOffscreen(h, w);
    }
    else {
      return new ConsLoCircle(this.first, this.rest.removeOffscreen(h, w));
    }
  }

  @Override
  public WorldScene placeAll(WorldScene ws) {
    // TODO Auto-generated method stub
    return this.first.place(this.rest.placeAll(ws));
  }
}

class Game extends World {
  ILoCircle l;
  int deadNum;

  Game(ILoCircle l, int d) {
    this.l = l;
    this.deadNum = d;
  }

  Game(int d) {
    this.l = new ConsLoCircle(new Circle(new MyPosn(1, 1), new MyPosn(2, 2), 5, Color.blue),
        new MtLoCircle());
    this.deadNum = d;
  }

  @Override
  public WorldScene makeScene() {
    // TODO Auto-generated method stub
    return this.l.placeAll(new WorldScene(500, 500));
  }

  public WorldEnd worldEnds() {
    return new WorldEnd(false, this.makeScene());// ????????????
  }

  public Game onMouseClicked(Posn pos) {
    return new Game(new ConsLoCircle(
        new Circle(new MyPosn(pos.x, pos.y), new MyPosn(0, 1), 5, Color.green),
        this.l), this.deadNum);
  }

  public Game onTick() {
    return new Game(this.l.moveAll().removeOffscreen(500, 500), this.deadNum);
  }
}

class ExamplesGame {
  Circle c1 = new Circle(new MyPosn(50, 50), new MyPosn(6, 6), 10, Color.DARK_GRAY);
  Circle c2 = new Circle(new MyPosn(200, 200), new MyPosn(6, 6), 10, Color.DARK_GRAY);
  ILoCircle l = new ConsLoCircle(c1, new ConsLoCircle(c2, new MtLoCircle()));
  WorldScene ws = new WorldScene(500, 500);
  Game g = new Game(l, 10);


  boolean testBigBang(Tester t) {
    Game world = new Game(10);
    return world.bigBang(500, 500, .02);
  }
}