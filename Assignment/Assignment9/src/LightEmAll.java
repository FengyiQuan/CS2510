import java.util.ArrayList;
import java.util.Random;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class LightEmAll extends World {
  static final int GAME_ROW = 9;
  static final int GAME_COLUMN = 8;
  static final int PIECE_SIZE = 40;
  static final int BG_WIDTH = PIECE_SIZE * GAME_COLUMN;
  static final int BG_HEIGHT = PIECE_SIZE * GAME_ROW;
  static final int FONT_SIZE = PIECE_SIZE * 5;

  Random rand;
  Utils u = new Utils();
  // a list of columns of GamePieces,
  // i.e., represents the board in column-major order
  ArrayList<ArrayList<GamePiece>> board;
  // a list of all nodes
  ArrayList<GamePiece> nodes;
  // a list of edges of the minimum spanning tree
  ArrayList<Edge> mst;
  // the width and height of the board
  int width;
  int height;
  // the current location of the power station,
  // as well as its effective radius
  int powerRow;
  int powerCol;
  int radius;

  LightEmAll() {
    this.width = GAME_COLUMN;
    this.height = GAME_ROW;
    this.powerRow = GAME_ROW / 2;
    this.powerCol = GAME_COLUMN / 2;
    this.board = u.generateGrids(this.width, this.height);
    u.modifier(this.board);
    this.radius = u.getDiameter(this) / 2 + 1;
    this.shuffleAll();
    this.disconnectedAll();
    this.connectAll();

  }

  void disconnectedAll() {
    for (ArrayList<GamePiece> col : this.board) {
      u.disconnected(col);
    }
  }

  void connectAll() {
    this.board.get(this.powerCol).get(this.powerRow).powerStation = true;
    this.board.get(this.powerCol).get(this.powerRow).isConnected = true;
    this.board.get(this.powerCol).get(this.powerRow).lightThemAll(this.board);
  }

  void shuffleAll() {
    for (ArrayList<GamePiece> col : this.board) {
      u.shuffle(col);
    }
  }

  public WorldScene makeScene() {
    WorldScene scene = this.getEmptyScene();
    scene = u.draw(this.board, scene);
    return scene;
  }

  public void onMousePressed(Posn pos, String buttonName) {
    int row = pos.y / LightEmAll.PIECE_SIZE;
    int col = pos.x / LightEmAll.PIECE_SIZE;
    if (buttonName.equals("LeftButton") && 0 <= pos.x
        && pos.x <= LightEmAll.PIECE_SIZE * LightEmAll.GAME_COLUMN && 0 <= pos.y
        && pos.y <= LightEmAll.PIECE_SIZE * LightEmAll.GAME_ROW) {
      this.board.get(col).get(row).rotate();
      this.disconnectedAll();
      this.connectAll();

    }
    if (u.isAllLight(this.board)) {
      this.endOfWorld("win");
    }
  }

//draw the last scene when win
  public WorldScene lastScene(String msg) {
    WorldScene scene = this.getEmptyScene();
    if (msg.equals("win")) {
      scene = this.winScene();
    }
    else {
      scene = this.makeScene();
    }
    return scene;
  }

  // draw the scene if win
  WorldScene winScene() {
    WorldScene scene = this.makeScene();
    WorldImage win = new TextImage("You win!!", FONT_SIZE, Color.GREEN);
    scene.placeImageXY(win, BG_WIDTH / 2, BG_HEIGHT / 2);
    return scene;
  }

  public void onKeyEvent(String key) {
    this.board.get(this.powerCol).get(this.powerRow).powerStation = false;
    if (this.powerCol > 0 && key.equals("left")) {
      GamePiece left = this.board.get(this.powerCol - 1).get(this.powerRow);
      if (left.isConnected && left.right) {
        this.powerCol--;
      }
    }
    else if (this.powerCol < this.board.size() - 1 && key.equals("right")) {
      GamePiece right = this.board.get(this.powerCol + 1).get(this.powerRow);
      if (right.isConnected && right.left) {
        this.powerCol++;
      }
    }
    else if (this.powerRow > 0 && key.equals("up")) {
      GamePiece top = board.get(this.powerCol).get(this.powerRow - 1);
      if (top.isConnected && top.bottom) {
        this.powerRow--;
      }
    }
    else if (this.powerRow + 1 < this.board.get(this.powerCol).size() && key.equals("down")) {
      GamePiece bot = board.get(this.powerCol).get(this.powerRow + 1);
      if (bot.isConnected && bot.top) {
        this.powerRow++;
      }
    }
    this.board.get(this.powerCol).get(this.powerRow).powerStation = true;
    this.disconnectedAll();
    this.connectAll();
  }
}