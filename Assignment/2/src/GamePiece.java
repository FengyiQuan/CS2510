import java.util.ArrayList;
import java.util.HashSet;
import java.awt.Color;
import javalib.worldimages.*;

// game piece
class GamePiece {
  int row;
  int col;
  boolean left;
  boolean right;
  boolean top;
  boolean bottom;
  boolean powerStation;
  // whether this game piece is connected with power station
  boolean isConnected;
  // the distance from power station
  int lev;

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

  Color gradientColor() {
    int percent = this.lev / LightEmAll.radius * 100;
    if (percent >= 86 && percent <= 100) {
      return Color.getHSBColor(60, 79, 99);
    }
    if (percent >= 72 && percent <= 86) {
      return Color.getHSBColor(56, 80, 92);
    }
    if (percent >= 58 && percent <= 72) {
      return Color.getHSBColor(52, 81, 83);
    }
    if (percent >= 44 && percent <= 58) {
      return Color.getHSBColor(48, 82, 73);
    }
    if (percent >= 30 && percent <= 44) {
      return Color.getHSBColor(43, 84, 65);
    }
    if (percent >= 16 && percent <= 30) {
      return Color.getHSBColor(37, 85, 54);
    } else {
      return Color.getHSBColor(30, 87, 44);
    }

  }

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

//    WorldImage connectedHorizonalLine = new RectangleImage(LightEmAll.PIECE_SIZE / 2,
//        LightEmAll.PIECE_SIZE / 10, OutlineMode.SOLID,
//        this.gradientColor());
//    WorldImage connectedVerticalLine = new RectangleImage(LightEmAll.PIECE_SIZE / 10,
//        LightEmAll.PIECE_SIZE / 2, OutlineMode.SOLID,
//        this.gradientColor());

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
    } else {
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
    if (this.left && (this.col - 1) >= 0 && this.lev > 0) {
      GamePiece left = board.get(this.col - 1).get(this.row);
      if (!left.isConnected && left.right && this.isConnected) {
        left.isConnected = true;
        left.lev = this.lev - 1;
        left.lightThemAll(board);
      }
    }
    if (this.right && (this.col + 1) < board.size() && this.lev > 0) {
      GamePiece right = board.get(this.col + 1).get(this.row);
      if (!right.isConnected && right.left && this.isConnected) {
        right.isConnected = true;
        right.lev = this.lev - 1;
        right.lightThemAll(board);
      }
    }
    if (this.top && (this.row - 1) >= 0 && this.lev > 0) {
      GamePiece top = board.get(this.col).get(this.row - 1);
      if (!top.isConnected && top.bottom && this.isConnected) {
        top.isConnected = true;
        top.lev = this.lev - 1;
        top.lightThemAll(board);
      }
    }
    if (this.bottom && (this.row + 1) < board.get(this.col).size() && this.lev > 0) {
      GamePiece bot = board.get(this.col).get(this.row + 1);
      if (!bot.isConnected && bot.top && this.isConnected) {
        bot.isConnected = true;
        bot.lev = this.lev - 1;
        bot.lightThemAll(board);
      }
    }
  }

  // find all connected neighbor
  ArrayList<GamePiece> findConnectedNeighbor(ArrayList<ArrayList<GamePiece>> board) {
    ArrayList<GamePiece> neig = new ArrayList<GamePiece>();

    if (this.left && this.col - 1 >= 0) {
      GamePiece left = board.get(this.col - 1).get(this.row);
      if (left.right) {
        neig.add(left);
      }
    }
    if (this.right && this.col + 1 < board.size()) {
      GamePiece right = board.get(this.col + 1).get(this.row);
      if (right.left) {
        neig.add(right);
      }
    }
    if (this.top && this.row - 1 >= 0) {
      GamePiece top = board.get(this.col).get(this.row - 1);
      if (top.bottom) {
        neig.add(top);
      }
    }
    if (this.bottom && this.row + 1 < board.get(this.col).size()) {
      GamePiece bot = board.get(this.col).get(this.row + 1);
      if (bot.top) {
        neig.add(bot);
      }
    }
    return neig;
  }

  // determine if two game piece are the same
  boolean sameGP(GamePiece target) {
    return this.row == target.row && this.col == target.col && this.left == target.left
        && this.right == target.right && this.top == target.top && this.bottom == target.bottom
        && this.powerStation == target.powerStation && this.isConnected == target.isConnected
        && this.lev == target.lev;
  }

  boolean isVisited(ArrayList<GamePiece> visited) {
    boolean result = false;
    for (GamePiece gp : visited) {
      result = result || (this.row == gp.row && this.col == gp.col);
    }
    return result;
  }

  public boolean isInSets(ArrayList<ArrayList<GamePiece>> nodeset) {
    boolean result = false;
    for (ArrayList<GamePiece> s : nodeset) {
      result = result || s.contains(this);
    }
    return result;
  }

  public void addtoSets(GamePiece fromNode, ArrayList<ArrayList<GamePiece>> nodeset) {
    for (ArrayList<GamePiece> s : nodeset) {
      if (s.contains(fromNode)) {
        s.add(this);
      }
    }

  }

  public ArrayList<GamePiece> findSet(ArrayList<ArrayList<GamePiece>> nodeset) {
    ArrayList<GamePiece> temp = new ArrayList<GamePiece>();
    for (ArrayList<GamePiece> s : nodeset) {
      if (s.contains(this)) {
        temp = s;
      }
    }
    return temp;

  }

}