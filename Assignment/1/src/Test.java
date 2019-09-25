import tester.*;
//import javalib.impworld.*;
//import java.awt.Color;
//import java.util.ArrayList;
import java.util.Random;
import javalib.worldimages.*;

class Test {

  GamePiece gp1;
  GamePiece gp2;
  GamePiece gp3;
  GamePiece gp4;
  GamePiece gp5;
  GamePiece gp6;
  GamePiece gp7;
  GamePiece gp8;
  GamePiece gp9;

  Random rand = new Random();
  Utils u = new Utils();;
  LightEmAll game;

  void initData() {
    this.gp1 = new GamePiece(0, 0, true, true, true, false, true);
    this.gp2 = new GamePiece(0, 0, false, true, true, true, true);
    this.gp3 = new GamePiece(0, 0, false, false, false, false, false);
    this.gp4 = new GamePiece(0, 0, true, false, false, false, false);
    this.gp5 = new GamePiece(0, 0, false, false, false, false, false);
    this.gp6 = new GamePiece(0, 0, false, false, false, false, false);
    this.gp7 = new GamePiece(0, 0, false, false, false, false, false);
    this.gp8 = new GamePiece(0, 0, false, false, false, false, false);
    this.gp9 = new GamePiece(0, 0, false, false, false, false, false);
    this.game = new LightEmAll();
    this.u = new Utils();
    this.rand = new Random();
  }
//
//  void testRotate(Tester t) {
//    this.initData();
//    t.checkExpect(this.gp1, this.gp1);
//    this.gp1.rotate();
//    t.checkExpect(this.gp1, this.gp2);
//    this.initData();
//  }

  // test for bigbang
  void testBigBang(Tester t) {
    this.initData();
    game.bigBang(1000, 1000, 1);
    // t.checkExpect(game.generateAllEdge(), true);
    // t.checkExpect(u.generateGrids(5,5), true);
    // t.checkExpect(this.generateAllEdge(u.generateGrids(3, 3)), true);
    // t.checkExpect(this.generateKruskal(), 2);
    // t.checkExpect(u.findShortest(this.generateAllEdge(u.generateGrids(3, 3))),
    // true);
  }
//
//  int generateKruskal() {
//    ArrayList<Edge> allEdge = this.generateAllEdge(u.generateGrids(3, 3));
//    ArrayList<GamePiece> visited = new ArrayList<GamePiece>();
//    ArrayList<Edge> result = new ArrayList<Edge>();
//    int i = 0;
//    while (!this.visiteAllEdge(visited, this.unfoldBoard(u.generateGrids(3, 3)))) {
//      Edge next = u.findShortest(allEdge);
//      allEdge.remove(u.findShortest(allEdge));
//
////      if (!u.isFormCycle(visited, next)) {
////        result.add(next);
////        /// ????
//      visited.add(next.fromNode);
//      visited.add(next.toNode);
////      }
//      i++;
//    }
//    return i;
//  }
//
////  boolean visiteAllEdge(ArrayList<GamePiece> visited, ArrayList<GamePiece> nodes) {
////    boolean result = false;
////    for (GamePiece gp : nodes) {
////      result = result && gp.isVisited(visited);
////    }
////    return result;
////  }
//
//  ArrayList<Edge> generateAllEdge(ArrayList<ArrayList<GamePiece>> board) {
//    ArrayList<Edge> result = new ArrayList<Edge>();
//    for (int i = 0; i < board.size(); i++) {
//      for (int j = 0; j < board.get(i).size(); j++) {
//        if (i < board.size() - 1) {
//          Edge rightLeft = new Edge(board.get(i).get(j), board.get(i + 1).get(j), rand.nextInt(2));
//          result.add(rightLeft);
//        }
//        if (j < board.get(i).size() - 1) {
//          Edge topDown = new Edge(board.get(i).get(j), board.get(i).get(j + 1), rand.nextInt(2));
//          result.add(topDown);
//        }
//      }
//    }
//    return result;
//  }
//
//  ArrayList<GamePiece> unfoldBoard(ArrayList<ArrayList<GamePiece>> board) {
//    ArrayList<GamePiece> nodes = new ArrayList<GamePiece>();
//    for (int i = 0; i < board.size(); i++) {
//      for (int j = 0; j < board.get(i).size(); j++) {
//        nodes.add(board.get(i).get(j));
//      }
//    }
//    return nodes;
//  }
////
////  /*
////   * void testRadius(Tester t) {
////   * this.initData();
////   * t.checkExpect(this.game.radius, 8);
////   * this.initData();
////   * t.checkExpect(game.board.get(game.powerCol - 1).get(game.powerRow).lev, 7);
////   * t.checkExpect(game.board.get(game.powerCol - 2).get(game.powerRow).lev, 6);
////   * t.checkExpect(u.getDepth(game), 15);
////   * t.checkExpect(this.game.radius, 8);
////   * t.checkExpect(this.game.getPower().lev, 8);
////   * }
////   */
////
////  void testLightAll(Tester t) {
////    this.initData();
////    t.checkExpect(game, new LightEmAll());
////    game.getPower().lightThemAll(game.board);
////    t.checkExpect(game, new LightEmAll());
////    this.initData();
////    game.getPower().lightThemAll(game.board);
////    t.checkExpect(game.board.get(game.powerCol - 1).get(game.powerRow).isConnected, true);
////  }
////
////  void testGetPower(Tester t) {
////    this.initData();
////    t.checkExpect(this.game.getPower(), game.board.get(game.powerCol).get(game.powerRow));
////  }
////
////  void testConnect(Tester t) {
////    this.initData();
////    t.checkExpect(u.isAllLight(game.board), true);
////    game.disconnectedAll();
////    t.checkExpect(u.isAllLight(game.board), false);
////    t.checkExpect(game.board.get(game.powerCol - 1).get(game.powerRow).isConnected, false);
////    game.connectAll();
////    t.checkExpect(game.board.get(game.powerCol - 1).get(game.powerRow).isConnected, true);
////  }
////
////  void testDraw(Tester t) {
////    this.initData();
////    WorldScene scene = game.getEmptyScene();
////    scene = u.draw(game.board, scene);
////    t.checkExpect(game.makeScene(), scene);
////  }
////
////  void testOnKey(Tester t) {
////    this.initData();
////    t.checkExpect(this.game.getPower().col, 4);
////    t.checkExpect(this.game.getPower().row, 4);
////    game.onKeyEvent("right");
////    t.checkExpect(this.game.getPower().row, 4);
////    t.checkExpect(this.game.getPower().col, 5);
////    game.onKeyEvent("up");
////    t.checkExpect(game.getPower().row, 4);
////  }
////
////  void testOnMouse(Tester t) {
////    this.initData();
////    t.checkExpect(game.board.get(0).get(0).isConnected, true);
////    game.onMousePressed(new Posn(LightEmAll.PIECE_SIZE / 2, LightEmAll.PIECE_SIZE / 2),
////        "LeftButton");
////    t.checkExpect(game.board.get(0).get(0).isConnected, false);
////    this.initData();
////    t.checkExpect(game.board.get(0).get(0).isConnected, true);
////    game.onMousePressed(new Posn(LightEmAll.PIECE_SIZE / 2, LightEmAll.PIECE_SIZE / 2),
////        "RightButton");
////    t.checkExpect(game.board.get(0).get(0).isConnected, true);
////  }
////
////  void testDrawGP(Tester t) {
////    WorldImage frame = new RectangleImage(LightEmAll.PIECE_SIZE, LightEmAll.PIECE_SIZE,
////        OutlineMode.OUTLINE, Color.BLACK);
////    WorldImage cell = new RectangleImage(LightEmAll.PIECE_SIZE, LightEmAll.PIECE_SIZE,
////        OutlineMode.SOLID, Color.DARK_GRAY);
////    WorldImage brokenHorizonalLine = new RectangleImage(LightEmAll.PIECE_SIZE / 2,
////        LightEmAll.PIECE_SIZE / 10, OutlineMode.SOLID, Color.LIGHT_GRAY);
////    this.initData();
////    t.checkExpect(this.gp3.draw(), new OverlayImage(frame, cell));
////    t.checkExpect(this.gp4.draw(),
////        new OverlayImage(brokenHorizonalLine.movePinhole(LightEmAll.PIECE_SIZE / 4, 0),
////            new OverlayImage(frame, cell)));
////  }
////
////  void testGetDistance(Tester t) {
////    this.initData();
////    t.checkExpect(this.game.getDepth(this.game.board.get(0).get(0), this.game.board.get(0).get(1)),
////        true);
////    // t.checkExpect(this.game.board.get(1).get(0).findConnectedNeighbor(this.game.board),
////    // true);
////    t.checkExpect(this.game.getDeepestLength(), true);
////  }
}
