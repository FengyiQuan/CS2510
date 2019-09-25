import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;
import java.util.Random;

interface IPred<T> {
  // asks a question about T
  boolean apply(T t);
}

// determine if a ship is not off-screen
class RemoveShip implements IPred<Ship> {

  // determine if a ship is not off-screen
  public boolean apply(Ship t) {
    return !t.position.isOffScreen(Game.SCREEN_WIDTH, Game.SCREEN_WIDTH);
  }
}

// determine if a bullet is not off-screen
class RemoveBullet implements IPred<Bullet> {

  // determine if a bullet is not off-screen
  public boolean apply(Bullet t) {
    return !t.position.isOffScreen(Game.SCREEN_WIDTH, Game.SCREEN_WIDTH);
  }
}

// determine if a ship is not hit by any of bullets in a IList of bullets
class ShipExist implements IPred<Ship> {
  IList<Bullet> b;

  ShipExist(IList<Bullet> b) {
    this.b = b;
  }

  // determine if a ship is not hit by any of bullets in a IList of bullets
  public boolean apply(Ship t) {
    return !b.ormap(new DoesBulletShotShip(t));
  }
}

// determine if any of a list of Bullet hits one ship
class ShipDestory implements IPred<Ship> {
  IList<Bullet> b;

  ShipDestory(IList<Bullet> b) {
    this.b = b;
  }

  // determine if any of a list of Bullet hits one ship
  public boolean apply(Ship t) {
    return b.ormap(new DoesBulletShotShip(t));
  }
}

// determine if one bullet hits a ship
class DoesBulletShotShip implements IPred<Bullet> {
  Ship s;

  DoesBulletShotShip(Ship s) {
    this.s = s;
  }

  // determine if one bullet hits a ship
  public boolean apply(Bullet t) {
    return t.doesHitShip(s);
  }
}

// determine if a bullet hits a given ship
class IsShipHittedByBullet implements IPred<Ship> {
  Bullet b;

  IsShipHittedByBullet(Bullet b) {
    this.b = b;
  }

  // determine if a bullet hits a given ship
  public boolean apply(Ship t) {
    return b.doesHitShip(t);
  }
}

interface IFunc<X, Y> {
  // applies an operation to the given
  Y apply(X x);
}

// move one bullet to next tick
class MoveAllBullet implements IFunc<Bullet, Bullet> {
  // move one bullet to next tick
  public Bullet apply(Bullet x) {
    return x.move();
  }
}

// move one ship to next tick
class MoveAllShip implements IFunc<Ship, Ship> {
  // move one ship to next tick
  public Ship apply(Ship x) {
    return x.move();
  }
}

// spawn new random ships
class SpawnNewShip implements IFunc<IList<Ship>, IList<Ship>> {
  double tick;
  int numShip;

  SpawnNewShip(double tick, int numShip) {
    this.tick = tick;
    this.numShip = numShip;
  }

  Random rad = new Random();
  Ship newLeftShip = new Ship(
      new MyPosn(0, (int) (rad.nextInt(Game.SCREEN_HEIGHT * 5 / 7) + Game.SCREEN_HEIGHT / 7)),
      new MyPosn(Game.SHIP_SPEED, 0));
  Ship newRightShip = new Ship(
      new MyPosn(Game.SCREEN_WIDTH,
          (int) (rad.nextInt(Game.SCREEN_HEIGHT * 5 / 7) + Game.SCREEN_HEIGHT / 7)),
      new MyPosn(-Game.SHIP_SPEED, 0));

  // spawn new random ships
  public IList<Ship> apply(IList<Ship> x) {
    if (this.numShip > 0) {
      if (rad.nextBoolean()) {
        return new SpawnNewShip(this.tick, this.numShip - 1)
            .apply(new ConsList<Ship>(this.newLeftShip, x));
      }
      else {
        return new SpawnNewShip(this.tick, this.numShip - 1)
            .apply(new ConsList<Ship>(this.newRightShip, x));
      }
    }
    else {
      return x;
    }
  }
}

// add new bullets to original
class AddNew implements IFunc<IList<Bullet>, IList<Bullet>> {

  // add new bullets to original
  public IList<Bullet> apply(IList<Bullet> x) {
    Bullet newBullet = new Bullet(new MyPosn(Game.SCREEN_WIDTH / 2, Game.SCREEN_HEIGHT),
        new MyPosn(0, -Game.BULLET_SPEED));
    return new ConsList<Bullet>(newBullet, x);
  }
}

// spread bullet when a bullet hits a ship
class SpreadBullet implements IFunc<IList<Bullet>, IList<Bullet>> {
  IList<Ship> s;

  SpreadBullet(IList<Ship> s) {
    this.s = s;
  }

  // spread bullet when a bullet hits a ship
  public IList<Bullet> apply(IList<Bullet> x) {
    return x.foldr(new ShouldItSpread(s), new MtList<Bullet>());
  }
}

//generate a list of spread bullet after collision
class GenerateBullet implements IFunc<Bullet, IList<Bullet>> {
  int times;

  GenerateBullet(int t) {
    this.times = t;
  }

  // generate a list of spread bullet after collision
  public IList<Bullet> apply(Bullet x) {
    if (this.times > 0) {
      return new ConsList<Bullet>(x.getNextBullet(this.times),
          new GenerateBullet(this.times - 1).apply(x));
    }
    else {
      return new MtList<Bullet>();
    }
  }
}

interface IFunc2<X, Y, Z> {
  // applies an operation to the given x and y
  Z apply(X x, Y y);
}

// place one ship to a given world scene
class PlaceAllShip implements IFunc2<Ship, WorldScene, WorldScene> {

  // place one ship to a given world scene
  public WorldScene apply(Ship x, WorldScene y) {
    return x.place(y);
  }
}

//place one bullet to a given world scene
class PlaceAllBullet implements IFunc2<Bullet, WorldScene, WorldScene> {

  // place one bullet to a given world scene
  public WorldScene apply(Bullet x, WorldScene y) {
    return x.place(y);
  }
}

// determine if a ship is destroyed and counts the number of destroyed ship
class IsShipDestory implements IFunc2<Ship, Integer, Integer> {
  IList<Bullet> b;

  IsShipDestory(IList<Bullet> b) {
    this.b = b;
  }

  // determine if a ship is destroyed and counts the number of destroyed ship
  public Integer apply(Ship x, Integer y) {
    if (b.ormap(new DoesBulletShotShip(x))) {
      return 1 + y;
    }
    return y;
  }

}

// count how many ship destroyed
class HowManyShipDestory implements IFunc2<IList<Ship>, IList<Bullet>, Integer> {
  int score;

  HowManyShipDestory(int score) {
    this.score = score;
  }

  // count how many ship destroyed
  public Integer apply(IList<Ship> x, IList<Bullet> y) {
    return x.foldr(new IsShipDestory(y), score);
  }
}

// determine if a bullet should spread and generate new bullets after collision 
class ShouldItSpread implements IFunc2<Bullet, IList<Bullet>, IList<Bullet>> {
  IList<Ship> s;

  ShouldItSpread(IList<Ship> s) {
    this.s = s;
  }

  // determine if a bullet should spread and generate new bullets after collision
  public IList<Bullet> apply(Bullet x, IList<Bullet> y) {
    if (s.ormap(new IsShipHittedByBullet(x))) {
      return new GenerateBullet(x.explodTime + 1).apply(x).append(y);
    }
    return new ConsList<Bullet>(x, y);
  }
}

// to represent a IList
interface IList<T> {
  // filters this IList by the given predicate
  IList<T> filter(IPred<T> pred);

  // maps a function onto every member of the list
  <Y> IList<Y> map(IFunc<T, Y> fun);

  // combines the items in this IList according to the given function
  <Y> Y foldr(IFunc2<T, Y, Y> fun, Y base);

  // determine if any items in this IList satisfy this predicate
  boolean ormap(IPred<T> pred);

  // determine if it is an empty list
  boolean isEmpty();

  // append two list
  IList<T> append(IList<T> that);
}

// to represent a MtList
class MtList<T> implements IList<T> {

  // filters this MtList by the given predicate
  public IList<T> filter(IPred<T> pred) {
    return this;
  }

  // maps a function onto every member of the MtList
  public <Y> IList<Y> map(IFunc<T, Y> fun) {
    return new MtList<Y>();
  }

  // combines the items in this MtList according to the given function
  public <Y> Y foldr(IFunc2<T, Y, Y> fun, Y base) {
    return base;
  }

  // determine if any items in this MtList satisfy this predicate
  public boolean ormap(IPred<T> pred) {
    return false;
  }

  // determine if it is an empty list
  public boolean isEmpty() {
    return true;
  }

  // append two list
  public IList<T> append(IList<T> that) {
    return that;
  }
}

// to represent ConsList
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  // filters this IList by the given predicate
  public IList<T> filter(IPred<T> pred) {
    if (pred.apply(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  // maps a function onto every member of the ConsList
  public <Y> IList<Y> map(IFunc<T, Y> fun) {
    return new ConsList<Y>(fun.apply(this.first), this.rest.map(fun));
  }

  // combines the items in this ConsList according to the given function
  public <Y> Y foldr(IFunc2<T, Y, Y> fun, Y base) {
    return fun.apply(this.first, this.rest.foldr(fun, base));
  }

  // determine if any items in this ConsList satisfy this predicate
  public boolean ormap(IPred<T> pred) {
    return pred.apply(this.first) || this.rest.ormap(pred);
  }

  // determine if it is an empty list
  public boolean isEmpty() {
    return false;
  }

  // append two list
  public IList<T> append(IList<T> that) {
    return new ConsList<T>(this.first, this.rest.append(that));
  }
}

// to represent Posn
class MyPosn extends Posn {
  MyPosn(int x, int y) {
    super(x, y);
  }

  /*
   * fields:
   * this.x ... int
   * this.y ... int
   * 
   * methods:
   * this.add(MyPosn) ... MyPosn
   * this.isOffScreen(double,double) ... boolean
   * this.getX() ... int
   * this.getY() ... int
   * this.xDifference(MyPosn) ... int
   * this.yDifference(MyPosn) ... int
   */

  // add two MyPosn together
  MyPosn add(MyPosn other) {
    return new MyPosn(this.x + other.x, this.y + other.y);
  }

  // determine if this MyPosn is off screen
  boolean isOffScreen(double h, double w) {
    return this.x > w || this.x < 0 || this.y > h || this.y < 0;
  }

  // return its x coordinate
  int getX() {
    return this.x;
  }

  // return its y coordinate
  int getY() {
    return this.y;
  }

  // calculate the difference between two position's x coordinate
  int xDifference(MyPosn other) {
    return this.x - other.x;
  }

  // calculate the difference between two position's y coordinate
  int yDifference(MyPosn other) {
    return this.y - other.y;
  }
}

// to represent ship or bullet
interface ShipOrBullet {

  // move ship or bullet
  ShipOrBullet move();

  // determine if it is off-screen
  boolean isOffscreen(double h, double w);

  // draw ship or bullet
  WorldImage draw();

  // place ship or bullet on a given world scene
  WorldScene place(WorldScene ws);
}

// abstract class to represent AShipOrBullet
abstract class AShipOrBullet implements ShipOrBullet {
  MyPosn position; // in pixels
  MyPosn velocity; // in pixels/tick
  int radius;
  Color color;

  AShipOrBullet(MyPosn position, MyPosn velocity, int radius, Color color) {
    this.position = position;
    this.velocity = velocity;
    this.radius = radius;
    this.color = color;
  }

  // move ship or bullet
  public abstract ShipOrBullet move();

  // determine if it is off-screen
  public boolean isOffscreen(double h, double w) {
    return this.position.isOffScreen(h, w);
  }

  // draw ship or bullet
  public WorldImage draw() {
    return new CircleImage(this.radius, "Solid", this.color);
  }

  // place ship or bullet on a given world scene
  public WorldScene place(WorldScene ws) {
    return ws.placeImageXY(this.draw(), this.position.getX(), this.position.getY());
  }
}

// to represent a ship
class Ship extends AShipOrBullet {

  Ship(MyPosn position, MyPosn velocity, int radius, Color color) {
    super(position, velocity, radius, color);
  }

  Ship(MyPosn position, MyPosn velocity) {
    this(position, velocity, Game.SHIP_RADIUS, Game.SHIP_COLOR);
  }

  /*
   * fields:
   * this.position ... MyPosn
   * this.velocity ... MyPosn
   * this.radius ... int
   * this.color ... Color
   * 
   * methods:
   * this.move() ... Ship
   * this.destory(Bullet) ... boolean
   * this.isOffscreen(double, double) ... boolean
   * this.draw() ... WorldImage
   * this.place(WorldScene) ... WorldScene
   * 
   * methods for fields:
   * this.position.add(MyPosn) ... MyPosn
   * this.position.isOffScreen(double,double) ... boolean
   * this.position.getX() ... int
   * this.position.getY() ... int
   * this.position.xDifference(MyPosn) ... int
   * this.position.yDifference(MyPosn) ... int
   * this.velocity.add(MyPosn) ... MyPosn
   * this.velocity.isOffScreen(double,double) ... boolean
   * this.velocity.getX() ... int
   * this.velocity.getY() ... int
   * this.velocity.xDifference(MyPosn) ... int
   * this.velocity.yDifference(MyPosn) ... int
   */

  // move this ship
  public Ship move() {
    return new Ship(this.position.add(this.velocity), this.velocity, this.radius, this.color);
  }

  // determine if this ship is destroy by given bullet
  boolean destroy(Bullet b) {
    return Math.hypot(this.position.xDifference(b.position),
        this.position.yDifference(b.position)) <= this.radius + b.radius;
  }
}

// to represent a bullet
class Bullet extends AShipOrBullet {
  int explodTime;

  Bullet(MyPosn position, MyPosn velocity, int radius, Color color, int explodTime) {
    super(position, velocity, radius, color);
    this.explodTime = explodTime;
  }

  Bullet(MyPosn position, MyPosn velocity) {
    this(position, velocity, Game.BULLET_RADIUS, Game.BULLET_COLOR, 1);
  }

  /*
   * fields:
   * this.position ... MyPosn
   * this.velocity ... MyPosn
   * this.radius ... int
   * this.color ... Color
   * this.explodTime ... int
   * 
   * methods:
   * this.move() ... Ship
   * this.doesHitShip(Ship) ... boolean
   * this.getNextBullet(int) ... Bullet
   * this.isOffscreen(double, double) ... boolean
   * this.draw() ... WorldImage
   * this.place(WorldScene) ... WorldScene
   * 
   * methods for fields:
   * this.position.add(MyPosn) ... MyPosn
   * this.position.isOffScreen(double,double) ... boolean
   * this.position.getX() ... int
   * this.position.getY() ... int
   * this.position.xDifference(MyPosn) ... int
   * this.position.yDifference(MyPosn) ... int
   * this.velocity.add(MyPosn) ... MyPosn
   * this.velocity.isOffScreen(double,double) ... boolean
   * this.velocity.getX() ... int
   * this.velocity.getY() ... int
   * this.velocity.xDifference(MyPosn) ... int
   * this.velocity.yDifference(MyPosn) ... int
   */

  // move bullet
  public Bullet move() {
    return new Bullet(this.position.add(this.velocity), this.velocity, this.radius, this.color,
        this.explodTime);
  }

  // determine if this bullet hits a given ship
  boolean doesHitShip(Ship s) {
    return Math.hypot(this.position.xDifference(s.position),
        this.position.yDifference(s.position)) <= (this.radius + s.radius);
  }

  // generate next bullet according to times
  Bullet getNextBullet(int times) {
    double theta = 2.0 * Math.PI * times / (this.explodTime + 1.0);
    int xvelocity = (int) (Math.cos(theta) * Game.BULLET_SPEED);
    int yvelocity = (int) (Math.sin(theta) * Game.BULLET_SPEED);

    if (this.radius <= Game.MAX_BULLET_RADIUS) {
      return new Bullet(this.position, new MyPosn(xvelocity, yvelocity),
          this.radius + Game.RAIDUS_OF_BULLET_AFTER_EXPLOSION, this.color, this.explodTime + 1);
    }
    else {
      return new Bullet(this.position, new MyPosn(xvelocity, yvelocity), this.radius, this.color,
          this.explodTime + 1);
    }
  }
}

// to represent the world state of the big bang program
class Game extends World {
  Random rad = new Random();
  static final int SCREEN_WIDTH = 500;
  static final int SCREEN_HEIGHT = 300;
  static final double TICK_RATE = 1.0 / 28.0;
  static final double SHIP_SPAWN_FREQUENCY = 1.0;
  static final int BULLET_RADIUS = 2;
  static final int RAIDUS_OF_BULLET_AFTER_EXPLOSION = 2;
  static final int MAX_BULLET_RADIUS = 10;
  static final Color BULLET_COLOR = Color.PINK;
  static final int BULLET_SPEED = 8;
  static final int SHIP_RADIUS = SCREEN_HEIGHT / 30;
  static final Color SHIP_COLOR = Color.CYAN;
  static final int SHIP_SPEED = BULLET_SPEED / 2;
  static final Color FONT_COLOR = Color.BLACK;
  static final int FONT_SIZE = 13;

  IList<Ship> ship;
  IList<Bullet> bullet;
  int left;
  int score;
  int tick;

  Game(IList<Ship> ship, IList<Bullet> bullet, int left, int score, int tick) {
    this.ship = ship;
    this.bullet = bullet;
    this.left = left;
    this.score = score;
    this.tick = tick;
  }

  Game(int left) {
    this(new MtList<Ship>(), new MtList<Bullet>(), left, 0, 0);
  }

  /*
   * fields:
   * this.ship ... IList<Ship>
   * this.bullet ... IList<Bullet>
   * this.left ... int
   * this.score ... int
   * this.tick ... int
   * 
   * methods:
   * this.drawBulletShip() ... WorldScene
   * this.makeScene() ... WorldScene
   * this.move() ... Game
   * this.spawnShip() ... Game
   * this.spreadBullet() ... Game
   * this.removeDestory() ... Game
   * this.getPoint() ... Game
   * this.removeOffScreen() ... Game
   * this.addTick() ... Game
   * this.onTick() ... Game
   * this.onKeyEvent(String) ... Game
   * this.drawScore() ... WorldScene
   * this.noBulletOnScreen() ... boolean
   * this.worldEnds() ... WorldEnd
   * 
   * methods for fields:
   * this.ship.move() ... Ship
   * this.ship.destory(Bullet) ... boolean
   * this.ship.isOffscreen(double, double) ... boolean
   * this.ship.draw() ... WorldImage
   * this.ship.place(WorldScene) ... WorldScene
   * this.bullet.move() ... Ship
   * this.bullet.doesHitShip(Ship) ... boolean
   * this.bullet.getNextBullet(int) ... Bullet
   * this.bullet.isOffscreen(double, double) ... boolean
   * this.bullet.draw() ... WorldImage
   * this.bullet.place(WorldScene) ... WorldScene
   */

  // to draw: draw all ships and bullets
  WorldScene drawBulletShip() {
    WorldScene ws = new WorldScene(500, 300);
    return this.bullet.foldr(new PlaceAllBullet(), this.ship.foldr(new PlaceAllShip(), ws));
  }

  // main make scene function: draw all ships, bullets, score and the number of
  // the left bullets
  public WorldScene makeScene() {
    WorldImage scoreAndLeft = new TextImage("Score is: " + Integer.toString(this.score)
        + "Left Bullets: " + Integer.toString(this.left), FONT_SIZE, FONT_COLOR);

    return this.drawBulletShip().placeImageXY(scoreAndLeft, (int) (SCREEN_WIDTH * 0.4),
        SCREEN_HEIGHT - FONT_SIZE);
  }

  // on tick: move all ships and bullets
  Game move() {
    return new Game(this.ship.map(new MoveAllShip()), this.bullet.map(new MoveAllBullet()),
        this.left, this.score, this.tick);
  }

  // on tick: spawn new ship at given frequency
  Game spawnShip() {
    int numberOfShipToSpawn = rad.nextInt(3) + 1;

    if (this.tick % (Game.SHIP_SPAWN_FREQUENCY / Game.TICK_RATE) <= 0.00001) {
      return new Game(new SpawnNewShip(Game.TICK_RATE, numberOfShipToSpawn).apply(this.ship),
          this.bullet, this.left, this.score, this.tick);
    }
    else {
      return this;
    }
  }

  // on tick: spread bullet when it hits some ship
  Game spreadBullet() {
    return new Game(this.ship, new SpreadBullet(this.ship).apply(this.bullet), this.left,
        this.score, this.tick);
  }

  // on tick: remove all destroyed ship
  Game removeDestory() {
    return new Game(this.ship.filter(new ShipExist(this.bullet)), this.bullet, this.left,
        this.score, this.tick);
  }

  // on tick: get point for it
  Game getPoint() {
    return new Game(this.ship, this.bullet, this.left,
        new HowManyShipDestory(this.score).apply(this.ship, this.bullet), this.tick);
  }

  // on tick: remove all ships and bullets that are off-screen
  Game removeOffScreen() {
    return new Game(this.ship.filter(new RemoveShip()), this.bullet.filter(new RemoveBullet()),
        this.left, this.score, this.tick);
  }

  // on tick: add 1 tick to the given game
  Game addTick() {
    return new Game(this.ship, this.bullet, this.left, this.score, this.tick + 1);
  }

  // main on tick function: move all bullets and ships and get point for destroyed
  // ship
  public Game onTick() {
    return this.move().spawnShip().spreadBullet().getPoint().removeDestory().removeOffScreen()
        .addTick();
  }

  // main on key function: when press space bar, it shots bullet
  public Game onKeyEvent(String ke) {
    if ((ke.equals(" ")) && this.left > 0) {
      return new Game(this.ship, new AddNew().apply(this.bullet), this.left - 1, this.score,
          this.tick);
    }
    else {
      return this;
    }
  }

  // stop-when: draw score
  WorldScene drawScore() {
    WorldScene ws = new WorldScene(500, 300);
    return ws.placeImageXY(new TextImage("Your Final Score is: " + Integer.toString(this.score),
        FONT_SIZE, FONT_COLOR), SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
  }

  // stop-when: determine if there is no bullet on the screen
  boolean noBulletOnScreen() {
    return this.bullet.isEmpty();
  }

  // main stop-when function: when game is end, return score
  public WorldEnd worldEnds() {
    if (this.left <= 0 && this.noBulletOnScreen()) {
      return new WorldEnd(true, this.drawScore());
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }
}

class ExamplesGame {
  WorldScene ws1 = new WorldScene(500, 300);

  Ship ship1 = new Ship(new MyPosn(10, 10), new MyPosn(5, 0), 5, Color.black);
  Ship ship2 = new Ship(new MyPosn(15, 10), new MyPosn(5, 0), 5, Color.black);
  Ship ship3 = new Ship(new MyPosn(100, 10), new MyPosn(5, 0), 5, Color.black);
  Ship ship4 = new Ship(new MyPosn(15, 10), new MyPosn(5, 0), 5, Color.black);
  Ship shipoff = new Ship(new MyPosn(600, 600), new MyPosn(5, 0), 5, Color.black);

  IList<Ship> mts = new MtList<Ship>();
  IList<Ship> s = new ConsList<Ship>(ship1, new MtList<Ship>());
  IList<Ship> s2 = new ConsList<Ship>(ship2, new MtList<Ship>());
  IList<Ship> soff = new ConsList<Ship>(this.shipoff, this.s);

  Bullet bullet1 = new Bullet(new MyPosn(10, 10), new MyPosn(5, 0), 5, Color.black, 2);
  Bullet bullet2 = new Bullet(new MyPosn(10, 10), new MyPosn(-3, 6), 7, Color.black, 3);
  Bullet bullet3 = new Bullet(new MyPosn(10, 10), new MyPosn(-3, 6), 7, Color.black, 1);
  Bullet bullet4 = new Bullet(new MyPosn(10, 10), new MyPosn(8, 0), 9, Color.black, 2);
  Bullet bullet5 = new Bullet(new MyPosn(10, 10), new MyPosn(-8, 0), 9, Color.black, 2);
  Bullet bullet6 = new Bullet(new MyPosn(10, 10), new MyPosn(8, 0), 7, Color.black, 3);
  Bullet bullet7 = new Bullet(new MyPosn(10, 10), new MyPosn(-4, -6), 7, Color.black, 3);
  Bullet bullet8 = new Bullet(new MyPosn(10, 10), new MyPosn(-3, 6), 7, Color.black, 3);
  Bullet bullet9 = new Bullet(new MyPosn(10, 10), new MyPosn(5, 0), 5, Color.black, 2);
  Bullet bullet10 = new Bullet(new MyPosn(600, 600), new MyPosn(-3, 6), 7, Color.black, 3);

  Bullet bulletnew = new Bullet(new MyPosn(Game.SCREEN_WIDTH / 2, Game.SCREEN_HEIGHT),
      new MyPosn(0, -Game.BULLET_SPEED));
  Bullet bulletoff = new Bullet(new MyPosn(600, 600), new MyPosn(-3, 6), 7, Color.black, 3);

  IList<Bullet> b = new MtList<Bullet>();
  IList<Bullet> b2 = new ConsList<Bullet>(this.bullet1, new MtList<Bullet>());
  IList<Bullet> b3 = new ConsList<Bullet>(this.bullet3, new MtList<Bullet>());
  IList<Bullet> bnew = new ConsList<Bullet>(this.bulletnew, new MtList<Bullet>());
  IList<Bullet> boff = new ConsList<Bullet>(this.bulletoff, this.b2);
  IList<Bullet> bspread = new ConsList<Bullet>(this.bullet4,
      new ConsList<Bullet>(this.bullet5, new MtList<Bullet>()));
  IList<Bullet> bspread2 = new ConsList<Bullet>(this.bullet6,
      new ConsList<Bullet>(this.bullet7, new ConsList<Bullet>(this.bullet8,
          new ConsList<Bullet>(this.bullet9, new MtList<Bullet>()))));

  Game g = new Game(s, b, 10, 0, 0);
  Game g2 = new Game(s, b2, 10, 0, 0);
  Game conv = new Game(10);
  Game g1 = new Game(new MtList<Ship>(), new MtList<Bullet>(), 10, 0, 0);
  Game g3 = new Game(new MtList<Ship>(), new MtList<Bullet>(), 10, 0, 1);
  Game g4 = new Game(new MtList<Ship>(), new MtList<Bullet>(), 0, 0, 1);

  boolean testMove(Tester t) {
    return t.checkExpect(this.g.move(), new Game(s2, b, 10, 0, 0))
        && t.checkExpect(this.conv, this.conv);

  }

  boolean testBigBang(Tester t) {
    return conv.bigBang(500, 300, Game.TICK_RATE);
  }

  boolean testDrawScore(Tester t) {
    return t.checkExpect(this.conv.drawScore(),
        new WorldScene(500, 300)
            .placeImageXY(new TextImage("Your Final Score is: " + Integer.toString(0),
                Game.FONT_SIZE, Game.FONT_COLOR), Game.SCREEN_WIDTH / 2, Game.SCREEN_HEIGHT / 2));
  }

  boolean testDoesHitship(Tester t) {
    return t.checkExpect(this.bullet1.doesHitShip(ship3), false)
        && t.checkExpect(this.bullet1.doesHitShip(ship1), true);
  }

  boolean testOntick(Tester t) {
    return t.checkExpect(new Game(mts, b, 10, 0, 1).onTick(), new Game(mts, b, 10, 0, 2));
  }

  boolean testGenerateBullet(Tester t) {
    return t.checkExpect(new GenerateBullet(1).apply(bullet1),
        new ConsList<Bullet>(this.bullet2, new MtList<Bullet>()));
  }

  boolean testNoBulletOnScreen(Tester t) {
    return t.checkExpect(this.conv.noBulletOnScreen(), true)
        && t.checkExpect(this.g2.noBulletOnScreen(), false);
  }

  boolean testOnKeyEvent(Tester t) {
    return t.checkExpect(this.conv.onKeyEvent(" "), new Game(new MtList<Ship>(), bnew, 9, 0, 0))
        && t.checkExpect(this.conv.onKeyEvent("up"), this.conv);
  }

  boolean testAddTick(Tester t) {
    return t.checkExpect(this.conv.addTick(), new Game(this.mts, this.b, 10, 0, 1));
  }

  boolean testRemoveOffScreen(Tester t) {
    return t.checkExpect(new Game(this.soff, this.boff, 10, 0, 0).removeOffScreen(), this.g2)
        && t.checkExpect(this.g.removeOffScreen(), this.g);
  }

  boolean testGetPoint(Tester t) {
    return t.checkExpect(this.conv.getPoint(), this.g1) && t.checkExpect(
        new Game(this.s, this.b2, 10, 1, 1).getPoint(), new Game(this.s, this.b2, 10, 2, 1));
  }

  boolean testSpawnNewShip(Tester t) {
    return t.checkExpect(this.g3.spawnShip(), this.g3)
        && t.checkExpect(new SpawnNewShip(0, 0).apply(this.mts), this.mts);
  }

  boolean testRemoveDestroy(Tester t) {
    return t.checkExpect(this.g.removeDestory(), this.g) && t.checkExpect(
        new Game(this.s, this.b2, 10, 1, 1).removeDestory(), new Game(this.mts, this.b2, 10, 1, 1));
  }

  boolean testerSpreadBullet(Tester t) {
    return t.checkExpect(new Game(this.s, this.b3, 10, 1, 1).spreadBullet(),
        new Game(this.s, this.bspread, 10, 1, 1))
        && t.checkExpect(this.conv.spreadBullet(), this.g1);
  }

  boolean testDrawBulletShip(Tester t) {
    return t.checkExpect(this.g2.drawBulletShip(), this.b2.foldr(new PlaceAllBullet(),
        this.s.foldr(new PlaceAllShip(), new WorldScene(500, 300))));
  }

  boolean testRemoveShip(Tester t) {
    return t.checkExpect(new RemoveShip().apply(this.shipoff), false)
        && t.checkExpect(new RemoveShip().apply(this.ship1), true);
  }

  boolean testRemoveBullet(Tester t) {
    return t.checkExpect(new RemoveBullet().apply(this.bulletoff), false)
        && t.checkExpect(new RemoveBullet().apply(this.bullet1), true);
  }

  boolean testShipExist(Tester t) {
    return t.checkExpect(new ShipExist(this.b2).apply(this.ship1), false)
        && t.checkExpect(new ShipExist(this.b).apply(this.ship2), true);
  }

  boolean testShipDestory(Tester t) {
    return t.checkExpect(new ShipDestory(this.b2).apply(this.ship1), true)
        && t.checkExpect(new ShipDestory(this.b).apply(this.ship2), false);
  }

  boolean testDoesBulletShotShip(Tester t) {
    return t.checkExpect(new DoesBulletShotShip(this.ship1).apply(this.bullet1), true)
        && t.checkExpect(new DoesBulletShotShip(this.ship1).apply(this.bulletoff), false);
  }

  boolean testIsShipHittedByBullet(Tester t) {
    return t.checkExpect(new ShipExist(this.b2).apply(this.ship1), false)
        && t.checkExpect(new ShipExist(this.b).apply(this.ship2), true);
  }

  boolean testMoveAllBullet(Tester t) {
    return t.checkExpect(new MoveAllBullet().apply(this.bullet1),
        new Bullet(new MyPosn(15, 10), new MyPosn(5, 0), 5, Color.black, 2));
  }

  boolean testMoveAllShip(Tester t) {
    return t.checkExpect(new MoveAllShip().apply(this.ship1),
        new Ship(new MyPosn(15, 10), new MyPosn(5, 0), 5, Color.black))
        && t.checkExpect(this.s.map(new MoveAllShip()), this.s2)
        && t.checkExpect(this.mts.map(new MoveAllShip()), this.mts);
  }

  boolean testAddNew(Tester t) {
    return t.checkExpect(new AddNew().apply(this.b),
        new ConsList<Bullet>(this.bulletnew, new MtList<Bullet>()));
  }

  boolean testSpreadBullet(Tester t) {
    return t.checkExpect(new SpreadBullet(this.mts).apply(this.b), this.b)
        && t.checkExpect(new SpreadBullet(this.s).apply(this.b), this.b);
  }

  boolean testPlaceAllShip(Tester t) {
    return t.checkExpect(new PlaceAllShip().apply(this.ship1, this.ws1),
        this.ship1.place(this.ws1));
  }

  boolean testPlaceAllBullet(Tester t) {
    return t.checkExpect(new PlaceAllBullet().apply(this.bullet1, this.ws1),
        this.bullet1.place(this.ws1));
  }

  boolean testIsShipDestory(Tester t) {
    return t.checkExpect(new IsShipDestory(this.b).apply(this.ship1, 3), 3)
        && t.checkExpect(new IsShipDestory(this.b2).apply(this.ship1, 3), 4);
  }

  boolean testHowManyShipDestory(Tester t) {
    return t.checkExpect(new HowManyShipDestory(3).apply(this.s, this.b2), 4)
        && t.checkExpect(new HowManyShipDestory(3).apply(this.s, this.b), 3);
  }

  boolean testShouldItSpread(Tester t) {
    return t.checkExpect(new ShouldItSpread(this.s).apply(this.bullet1, this.b2), this.bspread2)
        && t.checkExpect(new ShouldItSpread(this.mts).apply(this.bulletoff, this.b),
            new ConsList<Bullet>(this.bullet10, this.b));
  }

  boolean testWorldEnd(Tester t) {
    return t.checkExpect(this.g.worldEnds(), new WorldEnd(false, g.makeScene()))
        && t.checkExpect(this.g4.worldEnds(), new WorldEnd(true, g.drawScore()));
  }
}
