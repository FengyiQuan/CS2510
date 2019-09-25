import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import javalib.impworld.WorldScene;

class Utils {
  // generate all grids by given row and column
  ArrayList<ArrayList<GamePiece>> generateGrids(int width, int height) {
    ArrayList<ArrayList<GamePiece>> result = new ArrayList<ArrayList<GamePiece>>();
    for (int i = 0; i < width; i++) {
      result.add(this.generateOneCol1(i, height));
    }
    return result;
  }

  // generate one column by given height
  ArrayList<GamePiece> generateOneCol1(int i, int height) {
    ArrayList<GamePiece> col = new ArrayList<GamePiece>();
    for (int j = 0; j < height; j++) {
      col.add(new GamePiece(j, i));
    }
    return col;
  }

  // generate all horizontal lines with a single, vertical bar through the center
  void modifier1(ArrayList<ArrayList<GamePiece>> board) {
    for (int i = 0; i < board.size(); i++) {
      if (i == board.size() / 2) {
        this.changeToCenter1(board.get(i));
      } else {
        this.changeToHorizontal1(board.get(i));
      }
    }
  }

  // change particular game piece to center game piece
  void changeToCenter1(ArrayList<GamePiece> col) {
    for (int i = 0; i < col.size(); i++) {
      if (i == 0) {
        col.get(i).right = true;
        col.get(i).left = true;
        col.get(i).bottom = true;
      } else if (i == col.size() - 1) {
        col.get(i).right = true;
        col.get(i).left = true;
        col.get(i).top = true;
      } else {
        col.get(i).right = true;
        col.get(i).left = true;
        col.get(i).bottom = true;
        col.get(i).top = true;
      }
    }
  }

  // change particular game piece to horizontal connection
  void changeToHorizontal1(ArrayList<GamePiece> col) {
    for (GamePiece p : col) {
      p.left = true;
      p.right = true;
    }
  }

  // draw game
  WorldScene draw(ArrayList<ArrayList<GamePiece>> grids, WorldScene scene) {
    for (ArrayList<GamePiece> col : grids) {
      for (GamePiece gp : col) {
        scene.placeImageXY(gp.draw(), gp.col * LightEmAll.PIECE_SIZE + LightEmAll.PIECE_SIZE / 2,
            gp.row * LightEmAll.PIECE_SIZE + LightEmAll.PIECE_SIZE / 2);
      }
    }
    return scene;
  }

  // disconnected all game piece
  void disconnected(ArrayList<GamePiece> col) {
    for (GamePiece p : col) {
      p.isConnected = false;
    }
  }

  // rotate all game piece in a random times
  void shuffle(ArrayList<GamePiece> col) {
    Random rand = new Random();
    for (GamePiece p : col) {
      for (int i = rand.nextInt(4); i > 0; i--) {
        p.rotate();
      }
    }
  }

  // determine if all game pieces are been connected
  boolean isAllLight(ArrayList<ArrayList<GamePiece>> grids) {
    boolean result = true;
    for (ArrayList<GamePiece> col : grids) {
      result = result && this.isColLight(col);
    }
    return result;
  }

  // determine if one column is all been connected
  boolean isColLight(ArrayList<GamePiece> col) {
    boolean result = true;
    for (GamePiece p : col) {
      result = result && p.isConnected;
    }
    return result;
  }

}
