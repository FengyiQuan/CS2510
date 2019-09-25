import java.util.ArrayList;
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
  double ratioToPower;

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

  // draw one game piece
  WorldImage draw() {
    WorldImage result = new EmptyImage();
    WorldImage frame = new RectangleImage(LightEmAll.PIECE_SIZE, LightEmAll.PIECE_SIZE,
        OutlineMode.OUTLINE, Color.BLACK);
    WorldImage cell = new RectangleImage(LightEmAll.PIECE_SIZE, LightEmAll.PIECE_SIZE,
        OutlineMode.SOLID, Color.DARK_GRAY);
    WorldImage powerStation = new StarImage(LightEmAll.PIECE_SIZE / 3, 7, OutlineMode.SOLID,
        Color.YELLOW);

    // this is for the gradient color
    WorldImage connectedHorizonalLine1 = new RectangleImage(LightEmAll.PIECE_SIZE / 2,
        LightEmAll.PIECE_SIZE / 10, OutlineMode.SOLID, Color.YELLOW);
    WorldImage connectedVerticalLine1 = new RectangleImage(LightEmAll.PIECE_SIZE / 10,
        LightEmAll.PIECE_SIZE / 2, OutlineMode.SOLID, Color.YELLOW);
    WorldImage connectedHorizonalLine2 = new RectangleImage(LightEmAll.PIECE_SIZE / 2,
        LightEmAll.PIECE_SIZE / 10, OutlineMode.SOLID, new Color(223, 197, 8));
    WorldImage connectedVerticalLine2 = new RectangleImage(LightEmAll.PIECE_SIZE / 10,
        LightEmAll.PIECE_SIZE / 2, OutlineMode.SOLID, new Color(223, 197, 8));
    WorldImage connectedHorizonalLine3 = new RectangleImage(LightEmAll.PIECE_SIZE / 2,
        LightEmAll.PIECE_SIZE / 10, OutlineMode.SOLID, new Color(175, 122, 3));
    WorldImage connectedVerticalLine3 = new RectangleImage(LightEmAll.PIECE_SIZE / 10,
        LightEmAll.PIECE_SIZE / 2, OutlineMode.SOLID, new Color(175, 122, 3));
    WorldImage connectedHorizonalLine4 = new RectangleImage(LightEmAll.PIECE_SIZE / 2,
        LightEmAll.PIECE_SIZE / 10, OutlineMode.SOLID, new Color(154, 95, 5));
    WorldImage connectedVerticalLine4 = new RectangleImage(LightEmAll.PIECE_SIZE / 10,
        LightEmAll.PIECE_SIZE / 2, OutlineMode.SOLID, new Color(154, 95, 5));
    WorldImage connectedHorizonalLine5 = new RectangleImage(LightEmAll.PIECE_SIZE / 2,
        LightEmAll.PIECE_SIZE / 10, OutlineMode.SOLID, new Color(105, 47, 6));
    WorldImage connectedVerticalLine5 = new RectangleImage(LightEmAll.PIECE_SIZE / 10,
        LightEmAll.PIECE_SIZE / 2, OutlineMode.SOLID, new Color(105, 47, 6));

    WorldImage brokenHorizonalLine = new RectangleImage(LightEmAll.PIECE_SIZE / 2,
        LightEmAll.PIECE_SIZE / 10, OutlineMode.SOLID, Color.LIGHT_GRAY);
    WorldImage brokenVerticalLine = new RectangleImage(LightEmAll.PIECE_SIZE / 10,
        LightEmAll.PIECE_SIZE / 2, OutlineMode.SOLID, Color.LIGHT_GRAY);
    result = new OverlayImage(frame, cell);

    if (this.isConnected) {
      if (this.ratioToPower <= 0.2) {
        if (this.left) {
          result = new OverlayImage(
              connectedHorizonalLine5.movePinhole(LightEmAll.PIECE_SIZE / 4, 0), result);
        }
        if (this.right) {
          result = new OverlayImage(
              connectedHorizonalLine5.movePinhole(-LightEmAll.PIECE_SIZE / 4, 0), result);
        }
        if (this.top) {
          result = new OverlayImage(
              connectedVerticalLine5.movePinhole(0, LightEmAll.PIECE_SIZE / 4), result);
        }
        if (this.bottom) {
          result = new OverlayImage(
              connectedVerticalLine5.movePinhole(0, -LightEmAll.PIECE_SIZE / 4), result);
        }
      }
      else if (this.ratioToPower <= 0.4) {
        if (this.left) {
          result = new OverlayImage(
              connectedHorizonalLine4.movePinhole(LightEmAll.PIECE_SIZE / 4, 0), result);
        }
        if (this.right) {
          result = new OverlayImage(
              connectedHorizonalLine4.movePinhole(-LightEmAll.PIECE_SIZE / 4, 0), result);
        }
        if (this.top) {
          result = new OverlayImage(
              connectedVerticalLine4.movePinhole(0, LightEmAll.PIECE_SIZE / 4), result);
        }
        if (this.bottom) {
          result = new OverlayImage(
              connectedVerticalLine4.movePinhole(0, -LightEmAll.PIECE_SIZE / 4), result);
        }
      }
      else if (this.ratioToPower <= 0.6) {
        if (this.left) {
          result = new OverlayImage(
              connectedHorizonalLine3.movePinhole(LightEmAll.PIECE_SIZE / 4, 0), result);
        }
        if (this.right) {
          result = new OverlayImage(
              connectedHorizonalLine3.movePinhole(-LightEmAll.PIECE_SIZE / 4, 0), result);
        }
        if (this.top) {
          result = new OverlayImage(
              connectedVerticalLine3.movePinhole(0, LightEmAll.PIECE_SIZE / 4), result);
        }
        if (this.bottom) {
          result = new OverlayImage(
              connectedVerticalLine3.movePinhole(0, -LightEmAll.PIECE_SIZE / 4), result);
        }
      }
      else if (this.ratioToPower <= 0.8) {
        if (this.left) {
          result = new OverlayImage(
              connectedHorizonalLine2.movePinhole(LightEmAll.PIECE_SIZE / 4, 0), result);
        }
        if (this.right) {
          result = new OverlayImage(
              connectedHorizonalLine2.movePinhole(-LightEmAll.PIECE_SIZE / 4, 0), result);
        }
        if (this.top) {
          result = new OverlayImage(
              connectedVerticalLine2.movePinhole(0, LightEmAll.PIECE_SIZE / 4), result);
        }
        if (this.bottom) {
          result = new OverlayImage(
              connectedVerticalLine2.movePinhole(0, -LightEmAll.PIECE_SIZE / 4), result);
        }
      }
      else if (this.ratioToPower <= 1.0) {
        if (this.left) {
          result = new OverlayImage(
              connectedHorizonalLine1.movePinhole(LightEmAll.PIECE_SIZE / 4, 0), result);
        }
        if (this.right) {
          result = new OverlayImage(
              connectedHorizonalLine1.movePinhole(-LightEmAll.PIECE_SIZE / 4, 0), result);
        }
        if (this.top) {
          result = new OverlayImage(
              connectedVerticalLine1.movePinhole(0, LightEmAll.PIECE_SIZE / 4), result);
        }
        if (this.bottom) {
          result = new OverlayImage(
              connectedVerticalLine1.movePinhole(0, -LightEmAll.PIECE_SIZE / 4), result);
        }
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
  void lightThemAll(ArrayList<ArrayList<GamePiece>> board, int level) {
    if (this.left && (this.col - 1) >= 0 && this.lev > 0) {
      GamePiece left = board.get(this.col - 1).get(this.row);
      if (!left.isConnected && left.right && this.isConnected) {
        left.isConnected = true;
        left.lev = this.lev - 1;
        left.ratioToPower = (double) ((double) this.lev / (double) level);
        left.lightThemAll(board, level);
      }
    }
    if (this.right && (this.col + 1) < board.size() && this.lev > 0) {
      GamePiece right = board.get(this.col + 1).get(this.row);
      if (!right.isConnected && right.left && this.isConnected) {
        right.isConnected = true;
        right.lev = this.lev - 1;
        right.ratioToPower = (double) ((double) this.lev / (double) level);
        right.lightThemAll(board, level);
      }
    }
    if (this.top && (this.row - 1) >= 0 && this.lev > 0) {
      GamePiece top = board.get(this.col).get(this.row - 1);
      if (!top.isConnected && top.bottom && this.isConnected) {
        top.isConnected = true;
        top.lev = this.lev - 1;
        top.ratioToPower = (double) ((double) this.lev / (double) level);
        top.lightThemAll(board, level);
      }
    }
    if (this.bottom && (this.row + 1) < board.get(this.col).size() && this.lev > 0) {
      GamePiece bot = board.get(this.col).get(this.row + 1);
      if (!bot.isConnected && bot.top && this.isConnected) {
        bot.isConnected = true;
        bot.lev = this.lev - 1;
        bot.ratioToPower = (double) ((double) this.lev / (double) level);
        bot.lightThemAll(board, level);
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

  // determine if given game piece in node set
  boolean isInSets(ArrayList<ArrayList<GamePiece>> nodeset) {
    boolean result = false;
    for (ArrayList<GamePiece> s : nodeset) {
      result = result || s.contains(this);
    }
    return result;
  }

  // add given node to node set
  void addtoSets(GamePiece fromNode, ArrayList<ArrayList<GamePiece>> nodeset) {
    for (ArrayList<GamePiece> s : nodeset) {
      if (s.contains(fromNode)) {
        s.add(this);
      }
    }

  }

  // find the node set
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