import java.util.*;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;


//Represents a single square of the game area
class Cell {
  // In logical coordinates, with the origin at the top-left corner of the screen
  int x;
  int y;
  Color color;
  boolean flooded;
  // the four adjacent cells to this one
  Cell left;
  Cell top;
  Cell right;
  Cell bottom;

  Cell(int x, int y, Color color, boolean flooded) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.flooded = flooded;
    this.left = null;
    this.top = null;
    this.right = null;
    this.bottom = null;
  }

  // connect that to this left
  void connectLeft(Cell that) {
    this.left = that;
    that.right = this;
  }

  // connect that to this top
  void connectTop(Cell that) {
    this.top = that;
    that.bottom = this;
  }


  //draw this cell
  public WorldScene draw(WorldScene acc, int size) {
    acc.placeImageXY(new RectangleImage(size, size, 
        OutlineMode.SOLID, this.color).movePinhole(- size / 2, - size / 2), 
        this.x, this.y);
    return acc;
  }
}

// represents the flood game
class FloodItWorld extends World {

  int gridSize;
  int numColor;
  ArrayList<Cell> board;
  int stepCount = 0;
  double timer = 0;
  int currentScore = 0;


  Random rand = new Random();

  //Defines an int constant
  static final int BOARD_SIZE = 22;
  int totalStep = 3 * numColor + 2 * FloodItWorld.BOARD_SIZE;


  FloodItWorld(int gridSize, int numColor) {
    this.gridSize = gridSize;
    this.numColor = numColor;
    this.board = new ArrayList<Cell>();
    this.initial(FloodItWorld.BOARD_SIZE, numColor);
    this.initialList(this.board, FloodItWorld.BOARD_SIZE);
  }

  FloodItWorld() {
    this.gridSize = 20;
    this.numColor = 3;
    this.board = new ArrayList<Cell>();
  }


  // draw the world
  public WorldScene makeScene() {
    int windowsSize = FloodItWorld.BOARD_SIZE * gridSize;
    WorldScene scene = new WorldScene(windowsSize + 200, 
        windowsSize + 100);
    scene.placeImageXY(new TextImage(this.stepCount + "/" + 
        this.totalStep, 18, Color.BLACK), windowsSize / 2, windowsSize + 30);
    scene.placeImageXY(new TextImage("you have spent:" + Math.round(this.timer) + "s", 
        18, Color.BLACK), 
        windowsSize + 100, windowsSize / 2);
    scene.placeImageXY(new TextImage("your score:" + this.currentScore, 18, Color.BLACK), 
        windowsSize + 100, windowsSize / 2 + 50);
    return this.drawCells(scene, 0);
  }

  // draw the world
  WorldScene drawCells(WorldScene acc, int i) {
    if (i < this.board.size() - 1) {
      this.board.get(i).draw(this.drawCells(acc, i + 1), this.gridSize);
      return acc;
    }
    else {
      this.board.get(i).draw(acc, this.gridSize);
      return acc;
    }
  }

  // change the world according to mouse event
  public void onMouseClicked(Posn pos, String buttonName) {
    Color newColor = this.board.get(pos.y / gridSize 
        * FloodItWorld.BOARD_SIZE + pos.x / gridSize).color;
    if (buttonName.equals("LeftButton") && !this.board.get(0).color.equals(newColor)) {
      this.board.get(0).color = newColor;
      this.board.get(0).flooded = true;
      this.stepCount++;
      this.currentScore = this.currentScore + 100;
    }
  }


  // copy the arraylist
  public ArrayList<Cell> copy(ArrayList<Cell> a) {
    ArrayList<Cell> temp = new ArrayList<Cell>();
    for (Cell c: a) {
      temp.add(c);
    }
    return temp;
  }

  // change the world after one tick
  public void onTick() {
    Color initial = this.board.get(0).color;
    ArrayList<Cell> temp = waitList(this.board);
    for (Cell c: temp) {
      if (this.changeC(c, initial)) {
        c.color = initial;
        c.flooded = true;
      }
    }
    this.timer = this.timer + 0.01;
  }

  // resetting the game
  public void onKeyEvent(String key) {
    if (key.equals("r")) {
      this.initial(FloodItWorld.BOARD_SIZE, numColor);
      this.initialList(this.board, FloodItWorld.BOARD_SIZE);
      this.stepCount = 0;
      this.timer = 0;
    }
  }

  // is the game end?
  public WorldEnd worldEnds() {
    if (complete()) {
      return new WorldEnd(true, this.win());
    } 
    if (isOver()) {
      return new WorldEnd(true, this.lose());
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }

  // is the game success?
  boolean complete() {
    boolean temp = true;
    for (Cell c: this.board) {
      temp = temp && c.color.equals(this.board.get(0).color);
    }
    return temp;
  }

  // is the game lose?
  boolean isOver() {
    return this.totalStep == this.stepCount;
  }

  // return a winning scene
  WorldScene win() {
    int windowsSize = FloodItWorld.BOARD_SIZE * gridSize;
    WorldScene result = makeScene();
    result.placeImageXY(new TextImage("you win!", 20, Color.BLACK), 
        (windowsSize + 40) / 2, windowsSize + 50);
    return result;
  }

  // return a losing scene
  WorldScene lose() {
    int windowsSize = FloodItWorld.BOARD_SIZE * gridSize;
    WorldScene result = makeScene();
    result.placeImageXY(new TextImage("you lose", 20, Color.BLACK), 
        (windowsSize + 40) / 2, windowsSize + 50);
    return result;
  }

  // construct a list that contains all the Cell that needed to be changed in one tick
  ArrayList<Cell> waitList(ArrayList<Cell> a) {
    Color initial = this.board.get(0).color;
    ArrayList<Cell> temp = new ArrayList<Cell>();
    for (Cell c: a) {
      if (this.changeC(c, initial)) {
        temp.add(c);
      }
    }
    return temp;
  }

  // should this cell be changed?
  boolean changeC(Cell ce, Color c) {
    return left(ce, c) || right(ce, c) || top(ce, c) || bottom(ce, c);
  }

  // has the left been changed?
  boolean left(Cell ce, Color c) {
    if (ce.left instanceof Cell) {
      if (ce.flooded) {
        return ce.left.flooded && ce.left.color.equals(c);
      }
      else {
        return ce.left.flooded && ce.left.color.equals(c) && ce.color.equals(c);
      }
    }
    else {
      return false;
    }
  }

  // has the right been changed?
  boolean right(Cell ce, Color c) {
    if (ce.right instanceof Cell) {
      if (ce.flooded) {
        return ce.right.flooded && ce.right.color.equals(c);
      }
      else {
        return ce.right.flooded && ce.right.color.equals(c) && ce.color.equals(c);
      }
    }
    else {
      return false;
    }
  }

  // has the top been changed?
  boolean top(Cell ce, Color c) {
    if (ce.top instanceof Cell) {
      if (ce.flooded) {
        return ce.top.flooded && ce.top.color.equals(c);
      }
      else {
        return ce.top.flooded && ce.top.color.equals(c) && ce.color.equals(c);
      }
    }
    else {
      return false;
    }
  }

  // has the bottom been changed?
  boolean bottom(Cell ce, Color c) {
    if (ce.bottom instanceof Cell) {
      if (ce.flooded) {
        return ce.bottom.flooded && ce.bottom.color.equals(c);
      }
      else {
        return ce.bottom.flooded && ce.bottom.color.equals(c) && ce.color.equals(c);
      }
    }
    else {
      return false;
    }
  }

  // generate the initial list of cells
  void initial(int size, int color) {
    ArrayList<Cell> result = new ArrayList<Cell>();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        result.add(new Cell(j * this.gridSize, i * this.gridSize, randomColor(color), false));
      }
    }
    this.board = result;
  }


  // connect each cell in the list
  void initialList(ArrayList<Cell> l, int size) {
    for (Cell c: l) {
      if (c.y == 0) {
        if (c.x != 0) {
          c.connectLeft(l.get(l.indexOf(c) - 1));
        }
      }
      else {
        if (c.x == 0) {
          c.connectTop(l.get(l.indexOf(c) - size));
        }
        else {
          c.connectLeft(l.get(l.indexOf(c) - 1));
          c.connectTop(l.get(l.indexOf(c) - size));
        }
      }
    }
  }

  // generate a random color
  Color randomColor(int c) {
    if (rand.nextInt(c) == 0) {
      return Color.red;
    }
    else if (rand.nextInt(c) == 1) {
      return Color.blue;
    }
    else if (rand.nextInt(c) == 2) {
      return Color.green;
    }
    else if (rand.nextInt(c) == 3) {
      return Color.yellow;
    }
    else if (rand.nextInt(c) == 4) {
      return Color.pink;
    }
    else if (rand.nextInt(c) == 5) {
      return Color.cyan;
    }
    else if (rand.nextInt(c) == 6) {
      return Color.gray;
    }
    else {
      return Color.magenta;
    }
  }
}

class ExamplesFlood {

  Cell c1 = new Cell(0, 0, Color.blue, false);
  Cell c2 = new Cell(1, 0, Color.blue, false);
  Cell c3 = new Cell(2, 0, Color.blue, false);
  Cell c4 = new Cell(3, 0, Color.blue, false);
  Cell c5 = new Cell(0, 1, Color.blue, false);
  Cell c6 = new Cell(1, 1, Color.blue, false);
  Cell c7 = new Cell(2, 1, Color.blue, false);
  Cell c8 = new Cell(3, 1, Color.blue, false);
  Cell c9 = new Cell(0, 2, Color.blue, false);
  Cell c10 = new Cell(1, 2, Color.blue, false);
  Cell c11 = new Cell(2, 2, Color.blue, false);
  Cell c12 = new Cell(3, 2, Color.blue, false);
  Cell c13 = new Cell(0, 3, Color.blue, false);
  Cell c14 = new Cell(1, 3, Color.blue, false);
  Cell c15 = new Cell(2, 3, Color.blue, false);
  Cell c16 = new Cell(3, 3, Color.pink, false);
  ArrayList<Cell> a = new ArrayList<Cell>(Arrays.asList(c1));
  ArrayList<Cell> b = new ArrayList<Cell>(Arrays.asList(c2));
  ArrayList<Cell> c = new ArrayList<Cell>(Arrays.asList(c1, c2, c3, c4, c5, c6, c7,
      c8, c9, c10, c11, c12, c13, c14, c15, c16));
  ArrayList<Cell> d = new ArrayList<Cell>(Arrays.asList(c1, c2, c5, c6));
  int gridSize;
  int numOfColor;


  void initialData() {
    gridSize = 20;
    numOfColor = 8;
  }

  boolean testcopy(Tester t) {
    this.initialData();
    return t.checkExpect(new FloodItWorld(gridSize, numOfColor).copy(d), d)
        && t.checkExpect(new FloodItWorld(gridSize, numOfColor).copy(c), c)
        && t.checkExpect(new FloodItWorld(gridSize, numOfColor).copy(a), a);
  }

  void testinitialList(Tester t) {
    this.initialData();
    new FloodItWorld(gridSize, numOfColor).initialList(d, 2);
    t.checkExpect(c1.right, c2);
    t.checkExpect(c2.left, c1);
    t.checkExpect(c1.bottom, c5);
    t.checkExpect(c6.top, c2);
  }

  boolean testchangeC(Tester t) {
    this.initialData();
    return 
        t.checkExpect(new FloodItWorld(gridSize, numOfColor).changeC(c2, Color.black), false)
        && t.checkExpect(new FloodItWorld(gridSize, numOfColor).changeC(c1, Color.blue), false);
  }

  boolean testleft(Tester t) {
    this.initialData();
    return 
        t.checkExpect(new FloodItWorld(gridSize, numOfColor).left(c2, Color.black), false)
        && t.checkExpect(new FloodItWorld(gridSize, numOfColor).left(c1, Color.blue), false);
  }

  boolean testright(Tester t) {
    this.initialData();
    return 
        t.checkExpect(new FloodItWorld(gridSize, numOfColor).right(c2, Color.black), false)
        && t.checkExpect(new FloodItWorld(gridSize, numOfColor).right(c1, Color.blue), false);
  }

  boolean testtop(Tester t) {
    this.initialData();
    return 
        t.checkExpect(new FloodItWorld(gridSize, numOfColor).top(c2, Color.black), false)
        && t.checkExpect(new FloodItWorld(gridSize, numOfColor).top(c1, Color.blue), false);
  }

  boolean testbottom(Tester t) {
    this.initialData();
    return 
        t.checkExpect(new FloodItWorld(gridSize, numOfColor).bottom(c2, Color.black), false)
        && t.checkExpect(new FloodItWorld(gridSize, numOfColor).bottom(c1, Color.blue), false);
  }

  boolean testrandomcolor(Tester t) {
    this.initialData();
    return t.checkExpect(new FloodItWorld(this.gridSize, this.numOfColor).randomColor(1), 
        Color.red);
  }

  void testconnectLeft(Tester t) {
    this.c2.connectLeft(c1);
    t.checkExpect(c2.left, c1);

    this.c3.connectLeft(c2);
    t.checkExpect(c2.right, c3);
  }

  void testconnectTop(Tester t) {
    this.c5.connectTop(c1);
    t.checkExpect(c5.top, c1);

    this.c9.connectTop(c5);
    t.checkExpect(c5.bottom, c9);
  }

  boolean testwaitList(Tester t) {
    return
        t.checkExpect(new FloodItWorld(gridSize, numOfColor).waitList(this.c), 
            new ArrayList<Cell>())
        && t.checkExpect(new FloodItWorld(gridSize, numOfColor).waitList(this.d), 
            new ArrayList<Cell>());
  }

  void testonMouse(Tester t) {
    this.initialData();
    FloodItWorld w = new FloodItWorld();
    w.board = d;
    t.checkExpect(w.board.get(0).color, Color.blue);
    w.onMouseClicked(new Posn(0, 0), "LeftButton");
    t.checkExpect(w.board.get(0).color, Color.blue);
  }

  void testFloodIt(Tester t) {
    initialData();
    FloodItWorld game = new FloodItWorld(this.gridSize, this.numOfColor);
    game.bigBang(FloodItWorld.BOARD_SIZE * this.gridSize + 
        200, FloodItWorld.BOARD_SIZE * this.gridSize + 100, 0.01);
  }
}