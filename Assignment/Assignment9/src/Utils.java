import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import javalib.impworld.WorldScene;

class Utils {
  // generate all grids by given row and column
  ArrayList<ArrayList<GamePiece>> generateGrids(int width, int height) {
    ArrayList<ArrayList<GamePiece>> result = new ArrayList<ArrayList<GamePiece>>();
    for (int i = 0; i < width; i++) {
      result.add(this.generateOneCol(i, height));
    }
    return result;
  }

  // generate one column by given height
  ArrayList<GamePiece> generateOneCol(int i, int height) {
    ArrayList<GamePiece> col = new ArrayList<GamePiece>();
    for (int j = 0; j < height; j++) {
      col.add(new GamePiece(j, i));
    }
    return col;
  }

  // generate all horizontal lines with a single, vertical bar through the center
  void modifier(ArrayList<ArrayList<GamePiece>> board) {
    for (int i = 0; i < board.size(); i++) {
      if (i == board.size() / 2) {
        this.changeToCenter(board.get(i));
      }
      else {
        this.changeToVertical(board.get(i));
      }
    }
  }

  void changeToCenter(ArrayList<GamePiece> col) {
    for (int i = 0; i < col.size(); i++) {
      if (i == 0) {
        col.get(i).right = true;
        col.get(i).left = true;
        col.get(i).bottom = true;
      }
      else if (i == col.size() - 1) {
        col.get(i).right = true;
        col.get(i).left = true;
        col.get(i).top = true;
      }
      else {
        col.get(i).right = true;
        col.get(i).left = true;
        col.get(i).bottom = true;
        col.get(i).top = true;
      }
    }
  }

  void changeToVertical(ArrayList<GamePiece> col) {
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

  // determine if one col is all been connected
  boolean isColLight(ArrayList<GamePiece> col) {
    boolean result = true;
    for (GamePiece p : col) {
      result = result && p.isConnected == true;
    }
    return result;
  }

  // bfs
  GamePiece findTheDeepestGP(GamePiece from, ArrayList<ArrayList<GamePiece>> board) {
    return this.findTheDeepestGP(from, board, new Queue<GamePiece>());
  }

  GamePiece findTheDeepestGP(GamePiece from, ArrayList<ArrayList<GamePiece>> board,
      Queue<GamePiece> worklist) {

    Deque<GamePiece> visited = new ArrayDeque<GamePiece>();
    worklist.add(from);
    while (!worklist.isEmpty()) {
      GamePiece next = worklist.remove();
      if (visited.contains(next)) {
      }
      else {
        worklist.addAll(next.findConnectedNeighbor(board));
      }
      visited.addFirst(next);
    }
    return visited.getFirst();
  }

}
