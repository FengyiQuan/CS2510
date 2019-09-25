import tester.*;

import java.util.ArrayList;
import java.util.Arrays;

import javalib.impworld.*;
import java.awt.Color;
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
  ArrayList<ArrayList<GamePiece>> nodeset1;
  ArrayList<ArrayList<GamePiece>> nodeset2;
  ArrayList<GamePiece> set1;
  ArrayList<GamePiece> set2;
  Edge eg1;
  Edge eg2;

  Random rand = new Random();
  Utils u = new Utils();
  LightEmAll game;
  LightEmAll game22;

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
    this.game = new LightEmAll(new Random(1), 4, 4);
    this.game22 = new LightEmAll(new Random(1), 2, 2);
    this.u = new Utils();
    this.rand = new Random();
    this.set1 = new ArrayList<GamePiece>(Arrays.asList(this.gp1, this.gp2, this.gp3));
    this.set2 = new ArrayList<GamePiece>(Arrays.asList(this.gp1, this.gp2, this.gp4));
    this.nodeset1 = new ArrayList<ArrayList<GamePiece>>();
    this.nodeset2 = new ArrayList<ArrayList<GamePiece>>(Arrays.asList(this.set1, this.set2));
    this.eg1 = new Edge(this.gp1, this.gp2, 5);
    this.eg2 = new Edge(this.gp2, this.gp3, 1);
  }

  void testBigBang(Tester t) {
    this.initData();
    new LightEmAll().bigBang(LightEmAll.BG_WIDTH, LightEmAll.BG_HEIGHT + (5 * LightEmAll.FONT_SIZE),
        1);
  }

  void testRotate(Tester t) {
    this.initData();
    t.checkExpect(this.gp1, this.gp1);
    this.gp1.rotate();
    t.checkExpect(this.gp1, this.gp2);
    this.initData();
  }

  // test for bigbang

  void testLightAll(Tester t) {
    this.initData();
    game.getPower().lightThemAll(game.board, 14);
    t.checkExpect(game.board.get(3).get(1).lev, 4);
    this.initData();
    game.getPower().lightThemAll(game.board, 14);
    t.checkExpect(game.board.get(game.powerCol + 1).get(game.powerRow).isConnected, true);
    t.checkExpect(game.board.get(0).get(3).isConnected, false);
  }

  void testGetPower(Tester t) {
    this.initData();
    t.checkExpect(this.game.getPower(), game.board.get(game.powerCol).get(game.powerRow));
  }

  void testConnect(Tester t) {
    this.initData();
    t.checkExpect(game.board.get(game.powerCol + 1).get(game.powerRow).isConnected, true);
    game.disconnectedAll();
    t.checkExpect(game.board.get(game.powerCol + 1).get(game.powerRow).isConnected, false);
    game.connectAll();
    t.checkExpect(game.board.get(game.powerCol + 1).get(game.powerRow).isConnected, true);
    t.checkExpect(u.isColLight(game.board.get(0)), false);
    t.checkExpect(u.isAllLight(game.board), false);
  }

  void testDraw(Tester t) {
    this.initData();
    WorldScene scene = game.getEmptyScene();
    scene = u.draw(game.board, scene);
    t.checkExpect(game.makeScene(), scene);
  }

  void testOnKey(Tester t) {
    this.initData();
    t.checkExpect(this.game.getPower().col, 2);
    t.checkExpect(this.game.getPower().row, 2);
    game.onKeyEvent("right");
    t.checkExpect(this.game.getPower().row, 2);
    t.checkExpect(this.game.getPower().col, 3);
    game.onKeyEvent("up");
    t.checkExpect(game.getPower().row, 1);
  }

  void testOnMouse(Tester t) {
    this.initData();
    t.checkExpect(game.board.get(0).get(0).isConnected, true);
    game.onMousePressed(new Posn(LightEmAll.PIECE_SIZE / 2, LightEmAll.PIECE_SIZE / 2),
        "LeftButton");
    t.checkExpect(game.board.get(0).get(0).isConnected, false);
    this.initData();
    t.checkExpect(game.board.get(1).get(0).isConnected, true);
    game.onMousePressed(new Posn(LightEmAll.PIECE_SIZE / 2 * 3, LightEmAll.PIECE_SIZE / 2),
        "RightButton");
    t.checkExpect(game.board.get(1).get(0).isConnected, true);
  }

  void testDrawGP(Tester t) {
    WorldImage frame = new RectangleImage(LightEmAll.PIECE_SIZE, LightEmAll.PIECE_SIZE,
        OutlineMode.OUTLINE, Color.BLACK);
    WorldImage cell = new RectangleImage(LightEmAll.PIECE_SIZE, LightEmAll.PIECE_SIZE,
        OutlineMode.SOLID, Color.DARK_GRAY);
    WorldImage brokenHorizonalLine = new RectangleImage(LightEmAll.PIECE_SIZE / 2,
        LightEmAll.PIECE_SIZE / 10, OutlineMode.SOLID, Color.LIGHT_GRAY);
    this.initData();
    t.checkExpect(this.gp3.draw(), new OverlayImage(frame, cell));
    t.checkExpect(this.gp4.draw(),
        new OverlayImage(brokenHorizonalLine.movePinhole(LightEmAll.PIECE_SIZE / 4, 0),
            new OverlayImage(frame, cell)));
  }

  void testGetDistance(Tester t) {
    this.initData();
    t.checkExpect(this.game.getDepth(this.game.board.get(0).get(0), this.game.board.get(0).get(1)),
        3);
    t.checkExpect(this.game.getDeepestLength(), 10);
  }

  void testAllNeig(Tester t) {
    this.initData();
    ArrayList<GamePiece> alist = new ArrayList<GamePiece>();
    alist.add(game.board.get(1).get(0));
    t.checkExpect(game.board.get(0).get(0).findConnectedNeighbor(game.board), alist);
  }

  void testSameGP(Tester t) {
    this.initData();
    t.checkExpect(this.gp8.sameGP(gp9), true);
    t.checkExpect(this.gp8.sameGP(gp2), false);
  }

  void testEdge(Tester t) {
    this.initData();
    t.checkExpect(
        new Edge(this.game.board.get(0).get(0), this.game.board.get(0).get(1), 4).isLeftRight(),
        false);
    t.checkExpect(
        new Edge(this.game.board.get(0).get(0), this.game.board.get(0).get(1), 4).isTopDown(),
        true);
    t.checkExpect(
        new Edge(this.game.board.get(0).get(0), this.game.board.get(1).get(0), 4).isTopDown(),
        false);
    t.checkExpect(
        new Edge(this.game.board.get(0).get(0), this.game.board.get(1).get(0), 4).isLeftRight(),
        true);
  }

  void testSets(Tester t) {
    this.initData();
    t.checkExpect(this.gp1.isInSets(nodeset1), false);
    t.checkExpect(this.gp1.isInSets(nodeset2), true);
    t.checkExpect(this.gp6.isInSets(nodeset2), false);

    t.checkExpect(this.set1, new ArrayList<GamePiece>(Arrays.asList(this.gp1, this.gp2, this.gp3)));
    this.gp5.addtoSets(this.gp3, this.nodeset2);
    t.checkExpect(this.set1,
        new ArrayList<GamePiece>(Arrays.asList(this.gp1, this.gp2, this.gp3, this.gp5)));

    t.checkExpect(this.gp5.findSet(nodeset2), this.set1);
    t.checkExpect(this.gp4.findSet(nodeset2), this.set2);
  }

  void testShuffle(Tester t) {
    this.initData();
    t.checkExpect(this.game.board.get(0).get(0).bottom, false);
    t.checkExpect(this.game.board.get(0).get(0).top, false);
    t.checkExpect(this.game.board.get(0).get(0).right, true);
    t.checkExpect(this.game.board.get(0).get(0).left, false);
    this.game.shuffleAll();
    t.checkExpect(this.game.board.get(0).get(0).bottom, true);
    t.checkExpect(this.game.board.get(0).get(0).top, false);
    t.checkExpect(this.game.board.get(0).get(0).right, false);
    t.checkExpect(this.game.board.get(0).get(0).left, false);
  }

  void testClickRestart(Tester t) {
    this.initData();
    WorldImage time = new TextImage("Time: " + Integer.toString(2), LightEmAll.FONT_SIZE,
        Color.BLACK);
    WorldImage score = new TextImage("Step: " + Integer.toString(2), LightEmAll.FONT_SIZE,
        Color.BLACK);
    WorldImage restart = new TextImage("Restart", LightEmAll.FONT_SIZE, Color.BLACK);
    WorldImage restartButton = new OverlayImage(new RectangleImage(((int) restart.getWidth()),
        ((int) restart.getHeight()), OutlineMode.OUTLINE, Color.BLACK), restart);
    t.checkExpect(
        game.clickRestart(new Posn((LightEmAll.BG_WIDTH - ((int) restartButton.getWidth())) / 2,
            (int) (LightEmAll.BG_HEIGHT + time.getHeight() + score.getHeight()))),
        true);
    t.checkExpect(game
        .clickRestart(new Posn(((LightEmAll.BG_WIDTH - ((int) restartButton.getWidth())) / 2) - 1,
            (int) (LightEmAll.BG_HEIGHT + time.getHeight() + score.getHeight()))),
        false);
  }

  void testOntick(Tester t) {
    this.initData();
    t.checkExpect(game.time, 0);
    game.onTick();
    t.checkExpect(game.time, 1);
    game.onTick();
    t.checkExpect(game.time, 2);
  }

  void testWinAndLost(Tester t) {
    this.initData();
    t.checkExpect(game.worldEnds(), new WorldEnd(false, game.makeScene()));
    WorldScene scene = game.makeScene();
    WorldImage win = new TextImage("You win!!", LightEmAll.FONT_SIZE, Color.GREEN);
    scene.placeImageXY(win, LightEmAll.BG_WIDTH / 2, LightEmAll.BG_HEIGHT / 2);
    t.checkExpect(game.winScene(), scene);
  }

  void testDepth(Tester t) {
    this.initData();
    t.checkExpect(game.getDepth(gp2, gp1), -1);
    t.checkExpect(game.getDepth(this.game.board.get(0).get(0), this.game.board.get(3).get(3)), 6);
    t.checkExpect(game.getDepth(this.game.board.get(0).get(0), this.game.board.get(1).get(0)), 1);
    t.checkExpect(game.getDeepestLength(), 10);
    t.checkExpect(game.findTheDeepestGP(game.getPower()), this.game.board.get(3).get(3));
  }

  void testGenerateKruskal(Tester t) {
    this.initData();
    GamePiece gp3 = new GamePiece(1, 0, false, true, false, false, false, true, 1, 1.0);
    GamePiece gp4 = new GamePiece(1, 1, true, false, true, false, true, true, 2, 1.0);
    GamePiece gp6 = new GamePiece(0, 0, false, true, false, false, false, true, 0, 0.5);
    GamePiece gp7 = new GamePiece(0, 1, true, false, false, true, false, true, 1, 1.0);
    Edge eg2 = new Edge(gp3, gp4, 1);
    Edge eg5 = new Edge(gp6, gp7, 4);
    Edge eg8 = new Edge(gp7, gp4, 13);

    t.checkExpect(game22.generateKruskal(), new ArrayList<Edge>(Arrays.asList(eg2, eg5, eg8)));
  }

  void testGeneratePrim(Tester t) {
    this.initData();
    GamePiece gp3 = new GamePiece(0, 0, false, true, false, false, false, true, 0, 0.5);
    GamePiece gp4 = new GamePiece(0, 1, true, false, false, true, false, true, 1, 1.0);
    GamePiece gp6 = new GamePiece(1, 1, true, false, true, false, true, true, 2, 1.0);
    GamePiece gp7 = new GamePiece(1, 0, false, true, false, false, false, true, 1, 1.0);
    Edge eg2 = new Edge(gp3, gp4, 4);
    Edge eg8 = new Edge(gp7, gp6, 7);

    t.checkExpect(game22.generatePrim().get(0), eg2);
    t.checkExpect(game22.generatePrim().get(2), eg8);

  }

  void testGetAllConnect(Tester t) {
    this.initData();
    t.checkExpect(game.getAllConnected(new ArrayList<GamePiece>(), new ArrayList<Edge>(),
        new ArrayList<Edge>()), new ArrayList<Edge>());
    GamePiece gp3 = new GamePiece(0, 0, false, true, false, false, false, true, 0, 0.5);
    GamePiece gp4 = new GamePiece(0, 1, true, false, false, true, false, true, 1, 1.0);
    Edge eg2 = new Edge(gp3, gp4, 4);
    t.checkExpect(
        game.getAllConnected(new ArrayList<GamePiece>(Arrays.asList(gp3)),
            new ArrayList<Edge>(Arrays.asList(eg2)), new ArrayList<Edge>()),
        new ArrayList<Edge>(Arrays.asList(eg2)));
  }

  void testGenerateAllEdge(Tester t) {
    this.initData();
    GamePiece gp3 = new GamePiece(0, 0, false, true, false, false, false, true, 0, 0.5);
    GamePiece gp4 = new GamePiece(0, 1, true, false, false, true, false, true, 1, 1.0);
    Edge eg2 = new Edge(gp3, gp4, 4);

    t.checkExpect(game22.generateAllEdge().get(0), eg2);
  }

  void testModifier3(Tester t) {
    this.initData();
    game22.modifier3();
    t.checkExpect(this.game.board.get(0).get(0).bottom, false);
    t.checkExpect(this.game.board.get(0).get(0).top, false);
    t.checkExpect(this.game.board.get(0).get(0).right, true);
    t.checkExpect(this.game.board.get(0).get(0).left, false);
    t.checkExpect(this.game.board.get(1).get(0).bottom, true);
    t.checkExpect(this.game.board.get(1).get(0).top, false);
    t.checkExpect(this.game.board.get(1).get(0).right, true);
    t.checkExpect(this.game.board.get(1).get(0).left, true);
    t.checkExpect(this.game.board.get(0).get(1).bottom, true);
    t.checkExpect(this.game.board.get(0).get(1).top, false);
    t.checkExpect(this.game.board.get(0).get(1).right, true);
    t.checkExpect(this.game.board.get(0).get(1).left, false);
    t.checkExpect(this.game.board.get(1).get(1).bottom, true);
    t.checkExpect(this.game.board.get(1).get(1).top, true);
    t.checkExpect(this.game.board.get(1).get(1).right, false);
    t.checkExpect(this.game.board.get(1).get(1).left, true);
  }

  void testUnfold(Tester t) {
    this.initData();
    this.game.unfoldBoard();
    t.checkExpect(this.game.nodes.size(), 32);
    t.checkExpect(this.game22.nodes.size(), 4);
    t.checkExpect(this.game22.board.size(), 2);
    t.checkExpect(this.game.board.size(), 4);
  }

  void testSortbyweight(Tester t) {
    t.checkExpect(new SortbyWeight().compare(eg1, eg2), 4);
  }
}
