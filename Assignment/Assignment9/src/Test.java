import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
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

  Utils u = new Utils();
  LightEmAll game = new LightEmAll();

  void initData() {
    this.gp1 = new GamePiece(0, 0, true, true, true, false, true);
    this.gp2 = new GamePiece(0, 0, false, true, true, true, true);
  }

  void testRotate(Tester t) {
    this.initData();
    t.checkExpect(this.gp1, this.gp1);
    this.gp1.rotate();
    t.checkExpect(this.gp1, this.gp2);
  }

  // test for bigbang
  void testBigBang(Tester t) {
    t.checkExpect(this.game, true);
    // game.bigBang(1000, 1000);
  }

  void test(Tester t) {
    // t.checkExpect(u.shuffle(game.board.get(0)), true);
  }
}
