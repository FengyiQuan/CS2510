import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

// examples
class ExamplesMine {
  Utils u = new Utils();

  Cell c1;
  Cell c2;
  Cell c3;
  Cell c4;
  Cell c5;
  Cell c6;
  Cell c7;
  Cell c8;
  Cell c9;
  Cell mt;

  ArrayList<Cell> neighbor1;
  ArrayList<Cell> neighbor2;
  ArrayList<Cell> neighbor3;
  ArrayList<Cell> neighbor4;
  ArrayList<Cell> neighbor5;
  ArrayList<Cell> neighbor6;
  ArrayList<Cell> neighbor7;
  ArrayList<Cell> neighbor8;
  ArrayList<Cell> neighbor9;

  ArrayList<Cell> row1;
  ArrayList<Cell> row2;
  ArrayList<Cell> row3;

  ArrayList<ArrayList<Cell>> grids;
  ArrayList<ArrayList<Cell>> grids2;
  Game game;
  Game game2;

  void initDate() {

    this.game = new Game();
    this.game2 = new Game(this.grids);
    this.c1 = new Cell(true, false, false, this.neighbor1);
    this.c2 = new Cell(false, false, false, this.neighbor2);
    this.c3 = new Cell(false, false, false, this.neighbor3);
    this.c4 = new Cell(false, true, true, this.neighbor4);
    this.c5 = new Cell(false, false, false, this.neighbor5);
    this.c6 = new Cell(false, false, false, this.neighbor6);
    this.c7 = new Cell(false, true, true, this.neighbor7);
    this.c8 = new Cell(false, false, false, this.neighbor8);
    this.c9 = new Cell(true, true, true, this.neighbor9);
    this.mt = new Cell();

    this.neighbor1 = new ArrayList<Cell>();
    this.neighbor1.add(this.c2);
    this.neighbor1.add(this.c4);
    this.neighbor1.add(this.c5);

    this.neighbor2 = new ArrayList<Cell>();
    this.neighbor2.add(this.c1);
    this.neighbor2.add(this.c3);
    this.neighbor2.add(this.c4);
    this.neighbor2.add(this.c5);
    this.neighbor2.add(this.c6);

    this.neighbor3 = new ArrayList<Cell>();
    this.neighbor3.add(this.c2);
    this.neighbor3.add(this.c5);
    this.neighbor3.add(this.c6);

    this.neighbor4 = new ArrayList<Cell>();
    this.neighbor4.add(this.c1);
    this.neighbor4.add(this.c2);
    this.neighbor4.add(this.c5);
    this.neighbor4.add(this.c7);
    this.neighbor4.add(this.c8);

    this.neighbor5 = new ArrayList<Cell>();
    this.neighbor5.add(this.c1);
    this.neighbor5.add(this.c2);
    this.neighbor5.add(this.c3);
    this.neighbor5.add(this.c4);
    this.neighbor5.add(this.c6);
    this.neighbor5.add(this.c7);
    this.neighbor5.add(this.c8);
    this.neighbor5.add(this.c9);

    this.neighbor6 = new ArrayList<Cell>();
    this.neighbor6.add(this.c2);
    this.neighbor6.add(this.c3);
    this.neighbor6.add(this.c5);
    this.neighbor6.add(this.c8);
    this.neighbor6.add(this.c9);

    this.neighbor7 = new ArrayList<Cell>();
    this.neighbor7.add(this.c4);
    this.neighbor7.add(this.c5);
    this.neighbor7.add(this.c8);

    this.neighbor8 = new ArrayList<Cell>();
    this.neighbor8.add(this.c4);
    this.neighbor8.add(this.c5);
    this.neighbor8.add(this.c6);
    this.neighbor8.add(this.c7);
    this.neighbor8.add(this.c9);

    this.neighbor9 = new ArrayList<Cell>();
    this.neighbor9.add(this.c5);
    this.neighbor9.add(this.c6);
    this.neighbor9.add(this.c8);

    this.c1.addNeighbors(neighbor1);
    this.c2.addNeighbors(neighbor2);
    this.c3.addNeighbors(neighbor3);
    this.c4.addNeighbors(neighbor4);
    this.c5.addNeighbors(neighbor5);
    this.c6.addNeighbors(neighbor6);
    this.c7.addNeighbors(neighbor7);
    this.c8.addNeighbors(neighbor8);
    this.c9.addNeighbors(neighbor9);

    this.row1 = new ArrayList<Cell>();
    this.row1.add(this.c1);
    this.row1.add(this.c2);
    this.row1.add(this.c3);

    this.row2 = new ArrayList<Cell>();
    this.row2.add(this.c4);
    this.row2.add(this.c5);
    this.row2.add(this.c6);

    this.row3 = new ArrayList<Cell>();
    this.row3.add(this.c7);
    this.row3.add(this.c8);
    this.row3.add(this.c9);

    this.grids = new ArrayList<ArrayList<Cell>>();
    this.grids.add(this.row1);
    this.grids.add(this.row2);
    this.grids.add(this.row3);

  }

  void testCountBomb(Tester t) {
    this.initDate();
    t.checkExpect(this.c1.countBomb(), 1);
    t.checkExpect(this.c3.countBomb(), 0);
  }

  // test for cell
  void testIsBomb(Tester t) {
    this.initDate();
    t.checkExpect(this.c1.isBomb(), false);
    t.checkExpect(this.c4.isBomb(), true);
  }

  void testFlaged(Tester t) {
    this.initDate();
    t.checkExpect(this.c1.mark, false);
    this.c1.flaged();
    t.checkExpect(this.c1.mark, true);
  }

  void testChangeToBomb(Tester t) {
    this.initDate();
    t.checkExpect(this.c1.mine, false);
    this.c1.changeToBomb();
    t.checkExpect(this.c1.mine, true);
  }

  void testReveal(Tester t) {
    this.initDate();
    t.checkExpect(this.c2.reveal, false);
    this.c2.reveal();
    t.checkExpect(this.c2.reveal, true);
    this.c1.reveal();
    t.checkExpect(this.c1.reveal, true);
  }

  void testConnectedOneCell(Tester t) {
    this.initDate();
    t.checkExpect(this.mt.neighbors, new ArrayList<Cell>());
    this.mt.connectedOneCell(0, 0, grids);
    t.checkExpect(this.mt.neighbors, this.neighbor1);
    this.initDate();
    t.checkExpect(this.mt.neighbors, new ArrayList<Cell>());
    this.mt.connectedOneCell(0, 1, grids);
    t.checkExpect(this.mt.neighbors, this.neighbor2);
    this.initDate();
    t.checkExpect(this.mt.neighbors, new ArrayList<Cell>());
    this.mt.connectedOneCell(1, 1, grids);
    t.checkExpect(this.mt.neighbors, this.neighbor5);
  }

  void testDrawCell(Tester t) {
    this.initDate();
    WorldImage frame = new RectangleImage(Game.GRID_SIZE, Game.GRID_SIZE, OutlineMode.OUTLINE,
        Color.black);
    WorldImage num = new TextImage("1", Color.GREEN);
    t.checkExpect(this.c1.drawCell(), new OverlayImage(frame, new OverlayImage(num,
        new RectangleImage(Game.GRID_SIZE, Game.GRID_SIZE, OutlineMode.SOLID, Color.GRAY))));
  }

  // test for IFunc
  void testAnyBombReveal(Tester t) {
    this.initDate();
    t.checkExpect(new AnyBombReveal().apply(this.row2), false);
    t.checkExpect(new AnyBombReveal().apply(this.row3), true);
  }

  void testAnyBombRevealInOneRow(Tester t) {
    this.initDate();
    t.checkExpect(new AnyBombRevealInOneRow().apply(this.c1), false);
    t.checkExpect(new AnyBombRevealInOneRow().apply(this.c4), false);
    t.checkExpect(new AnyBombRevealInOneRow().apply(this.c9), true);
  }

  void testAllSafeReveal(Tester t) {
    this.initDate();
    t.checkExpect(new AllSafeReveal().apply(row1), false);
    t.checkExpect(new AllSafeReveal().apply(row3), false);

  }

  void testAllSafeRevealInOneRow(Tester t) {
    this.initDate();
    t.checkExpect(new AllSafeRevealInOneRow().apply(c1), true);
    t.checkExpect(new AllSafeRevealInOneRow().apply(c3), false);
  }

  void testBomb(Tester t) {
    this.initDate();
    t.checkExpect(new Bomb().apply(this.c1, 0), 0);
    t.checkExpect(new Bomb().apply(this.c3, 8), 8);
  }

  // test for Game
  void testMakeScene(Tester t) {
    this.initDate();
    t.checkExpect(game.makeScene(), u.draw(game.cells, game.getEmptyScene()));
  }

  void testOnMousePressed(Tester t) {
    this.initDate();
    game2.onMousePressed(new Posn(1, 2), "MiddleButton");
    t.checkExpect(this.game2, this.game2);
  }

  void testLastScene(Tester t) {
    this.initDate();
    t.checkExpect(game.lastScene("win"), game.winScene());
    t.checkExpect(game.lastScene("lost"), game.lostScene());
  }

  void testLostScene(Tester t) {
    this.initDate();
    WorldImage lost = new TextImage("You lost!!", Color.GREEN);
    WorldScene s1 = this.game.lostScene();
    WorldScene s2 = this.game.makeScene();
    s2.placeImageXY(lost, Game.BG_WIDTH / 2, Game.BG_HEIGHT / 2);
    t.checkExpect(s1, s2);

  }

  void testWinScene(Tester t) {
    this.initDate();
    WorldImage win = new TextImage("You win!!", Color.GREEN);
    WorldScene s1 = game.lostScene();
    WorldScene s2 = game.makeScene();
    s2.placeImageXY(win, Game.BG_WIDTH / 2, Game.BG_HEIGHT / 2);
    t.checkExpect(s1, s2);

  }

  // test for Utils
  void testGenerateGrids(Tester t) {
    this.initDate();
    ArrayList<ArrayList<Cell>> gri = new ArrayList<ArrayList<Cell>>();
    ArrayList<Cell> aList = new ArrayList<Cell>();
    aList.add(new Cell());
    gri.add(aList);
    t.checkExpect(u.generateGrids(1, 1), gri);
  }

  void testGetOneRow(Tester t) {
    this.initDate();
    ArrayList<Cell> aList = new ArrayList<Cell>();
    aList.add(new Cell());
    t.checkExpect(u.getOneRow(1), aList);
  }

  void testConnectedNeighbors(Tester t) {
    this.initDate();
    ArrayList<ArrayList<Cell>> gri1 = u.generateGrids(1, 2);
    t.checkExpect(gri1.get(1).get(0).neighbors, new ArrayList<Cell>());
    ArrayList<Cell> aList1 = new ArrayList<Cell>();
    aList1.add(gri1.get(1).get(0));
    u.connectedNeighbors(gri1);
    t.checkExpect(gri1.get(1).get(0).neighbors, aList1);
  }

  void testAddBomb(Tester t) {
    this.initDate();
    ArrayList<ArrayList<Cell>> gri = u.generateGrids(1, 1);
    ArrayList<Integer> index = new ArrayList<Integer>();
    index.add(0);
    t.checkExpect(gri.get(0).get(0).isBomb(), false);
    u.addBomb(gri, index);
    t.checkExpect(gri.get(0).get(0).isBomb(), true);
  }

  void testDraw(Tester t) {
    this.initDate();
    WorldImage frame = new RectangleImage(Game.GRID_SIZE, Game.GRID_SIZE, OutlineMode.OUTLINE,
        Color.black);
    WorldImage num = new TextImage(Integer.toString(this.c1.countBomb()), Color.GREEN);
    t.checkExpect(this.c1.drawCell(), new OverlayImage(frame, new OverlayImage(num,
        new RectangleImage(this.c1.size, this.c1.size, OutlineMode.SOLID, Color.GRAY))));
  }

  // test for bigbang
  void testBigBang(Tester t) {
    this.initDate();
    game.bigBang(1000, 1000);
  }
}