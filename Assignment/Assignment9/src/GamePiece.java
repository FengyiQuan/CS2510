import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class GamePiece {
  // in logical coordinates, with the origin
  // at the top-left corner of the screen
  int row;
  int col;
  // whether this GamePiece is connected to the
  // adjacent left, right, top, or bottom pieces
  boolean left;
  boolean right;
  boolean top;
  boolean bottom;
  // whether the power station is on this piece
  boolean powerStation;
  // whether this game piece is connected with power station
  boolean isConnected;

  GamePiece(int row, int col) {
    this.row = row;
    this.col = col;
    this.left = false;
    this.right = false;
    this.top = false;
    this.bottom = false;
    this.powerStation = false;
    this.isConnected = false;
  }

  GamePiece(int row, int col, boolean l, boolean r, boolean t, boolean b, boolean p) {
    this.row = row;
    this.col = col;
    this.left = l;
    this.right = r;
    this.top = t;
    this.bottom = b;
    this.powerStation = p;
    this.isConnected = false;
  }

  // rotate this game piece in the clockwise direction
  void rotate() {
    boolean tpleft = this.left;
    boolean tpright = this.right;
    boolean tptop = this.top;
    boolean tpbottom = this.bottom;
    this.left = tpbottom;
    this.right = tptop;
    this.top = tpleft;
    this.bottom = tpright;
  }

  // determine if this line is connected with powerstation

  // draw one game piece
  WorldImage draw() {
    WorldImage result = new EmptyImage();
    WorldImage frame = new RectangleImage(LightEmAll.PIECE_SIZE, LightEmAll.PIECE_SIZE,
        OutlineMode.OUTLINE, Color.BLACK);
    WorldImage cell = new RectangleImage(LightEmAll.PIECE_SIZE, LightEmAll.PIECE_SIZE,
        OutlineMode.SOLID, Color.DARK_GRAY);
    WorldImage powerStation = new StarImage(LightEmAll.PIECE_SIZE / 3, 7, OutlineMode.SOLID,
        Color.getHSBColor(116, 251, 253));
    WorldImage connectedHorizonalLine = new RectangleImage(LightEmAll.PIECE_SIZE / 2,
        LightEmAll.PIECE_SIZE / 10, OutlineMode.SOLID, Color.YELLOW);
    WorldImage connectedVerticalLine = new RectangleImage(LightEmAll.PIECE_SIZE / 10,
        LightEmAll.PIECE_SIZE / 2, OutlineMode.SOLID, Color.YELLOW);
    WorldImage brokenHorizonalLine = new RectangleImage(LightEmAll.PIECE_SIZE / 2,
        LightEmAll.PIECE_SIZE / 10, OutlineMode.SOLID, Color.LIGHT_GRAY);
    WorldImage brokenVerticalLine = new RectangleImage(LightEmAll.PIECE_SIZE / 10,
        LightEmAll.PIECE_SIZE / 2, OutlineMode.SOLID, Color.LIGHT_GRAY);
    result = new OverlayImage(frame, cell);
    if (this.isConnected) {
      if (this.left) {
        result = new OverlayImage(connectedHorizonalLine.movePinhole(LightEmAll.PIECE_SIZE / 4, 0),
            result);
      }
      if (this.right) {
        result = new OverlayImage(connectedHorizonalLine.movePinhole(-LightEmAll.PIECE_SIZE / 4, 0),
            result);
      }
      if (this.top) {
        result = new OverlayImage(connectedVerticalLine.movePinhole(0, LightEmAll.PIECE_SIZE / 4),
            result);
      }
      if (this.bottom) {
        result = new OverlayImage(connectedVerticalLine.movePinhole(0, -LightEmAll.PIECE_SIZE / 4),
            result);
      }
    }
    else {
      if (this.left) {
        result = new OverlayImage(brokenHorizonalLine.movePinhole(LightEmAll.PIECE_SIZE / 4, 0),
            result);
      }
      if (this.right) {
        result = new OverlayImage(brokenHorizonalLine.movePinhole(-LightEmAll.PIECE_SIZE / 4, 0),
            result);
      }
      if (this.top) {
        result = new OverlayImage(brokenVerticalLine.movePinhole(0, LightEmAll.PIECE_SIZE / 4),
            result);
      }
      if (this.bottom) {
        result = new OverlayImage(brokenVerticalLine.movePinhole(0, -LightEmAll.PIECE_SIZE / 4),
            result);
      }
    }
    if (this.powerStation) {
      result = new OverlayImage(powerStation, result);
    }
    return result;
  }

  // light all its neighbors if connected
  void lightThemAll(ArrayList<ArrayList<GamePiece>> board) {
    if (this.left && (this.col - 1) >= 0) {
      GamePiece left = board.get(this.col - 1).get(this.row);
      if (left.isConnected == false && left.right && this.isConnected) {
        left.isConnected = true;
        left.lightThemAll(board);
      }
    }
    if (this.right && (this.col + 1) < board.size()) {
      GamePiece right = board.get(this.col + 1).get(this.row);
      if (right.isConnected == false && right.left && this.isConnected) {
        right.isConnected = true;
        right.lightThemAll(board);
      }
    }
    if (this.top && (this.row - 1) >= 0) {
      GamePiece top = board.get(this.col).get(this.row - 1);
      if (top.isConnected == false && top.bottom && this.isConnected) {
        top.isConnected = true;
        top.lightThemAll(board);
      }
    }
    if (this.bottom && (this.row + 1) < board.get(this.col).size()) {
      GamePiece bot = board.get(this.col).get(this.row + 1);
      if (bot.isConnected == false && bot.top && this.isConnected) {
        bot.isConnected = true;
        bot.lightThemAll(board);
      }
    }
  }

  ArrayList<GamePiece> findConnectedNeighbor(ArrayList<ArrayList<GamePiece>> board) {
    ArrayList<GamePiece> neig = new ArrayList<GamePiece>();

    if (this.col - 1 >= 0) {
      GamePiece left = board.get(this.col - 1).get(this.row);
      if (left.isConnected) {
        neig.add(left);
      }
    }
    if (this.col + 1 < board.size()) {
      GamePiece right = board.get(this.col + 1).get(this.row);
      if (right.isConnected) {
        neig.add(right);
      }
    }
    if (this.row - 1 >= 0) {
      GamePiece top = board.get(this.col).get(this.row - 1);
      if (top.isConnected) {
        neig.add(top);
      }
    }
    if (this.row + 1 < board.get(this.col).size()) {
      GamePiece bot = board.get(this.col).get(this.row + 1);
      if (bot.isConnected) {
        neig.add(bot);
      }
    }
    return neig;
  }
}