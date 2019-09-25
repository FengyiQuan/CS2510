import java.util.ArrayList;
import java.util.Random;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

// minesweeper game
class Game extends World {
  static final int GAME_ROW = 16;// 16
  static final int GAME_COLUMN = 30;// 30
  static final int GRID_SIZE = 20;
  static final int MINES_NUMBER = 99;// 99
  static final int BG_WIDTH = GRID_SIZE * GAME_COLUMN;
  static final int BG_HEIGHT = GRID_SIZE * GAME_ROW;
  static final int FONT_SIZE = GRID_SIZE * 5;

  Random rand;
  ArrayList<ArrayList<Cell>> cells;
  Utils u = new Utils();

  Game() {
    this.cells = u.generateGrids(GAME_COLUMN, GAME_ROW);
    u.connectedNeighbors(this.cells);
    u.addBomb(this.cells, u.allBombIndex(u.allIndex(GAME_ROW, GAME_COLUMN), MINES_NUMBER));
  }

  // constructor for test
  Game(ArrayList<ArrayList<Cell>> cells) {
    this.cells = cells;
  }

  // to draw: draw the game of Minesweeper
  public WorldScene makeScene() {
    WorldScene scene = this.getEmptyScene();
    scene = u.draw(this.cells, scene);
    return scene;
  }

  // On-click: press left button to reveal cell and press right button to mark it
  public void onMousePressed(Posn pos, String buttonName) {
    int row = pos.y / Game.GRID_SIZE;
    int col = pos.x / Game.GRID_SIZE;
    if (buttonName.equals("LeftButton") && 0 <= pos.x && pos.x <= Game.GRID_SIZE * Game.GAME_COLUMN
        && 0 <= pos.y && pos.y <= Game.GRID_SIZE * Game.GAME_ROW) {
      this.cells.get(row).get(col).reveal();
    }
    else if (buttonName.equals("RightButton") && 0 <= pos.x
        && pos.x <= Game.GRID_SIZE * Game.GAME_COLUMN && 0 <= pos.y
        && pos.y <= Game.GRID_SIZE * Game.GAME_ROW) {
      if (!(this.cells.get(row).get(col).mark)) {
        this.cells.get(row).get(col).flaged();
      }
      else {
        this.cells.get(row).get(col).mark = false;
      }
    }
    if (u.ormap(this.cells, new AnyBombReveal())) {
      this.endOfWorld("lost");
    }
    else if (u.andmap(this.cells, new AllSafeReveal())) {
      this.endOfWorld("win");
    }
  }

  // draw the last scene whether win or lost
  public WorldScene lastScene(String msg) {
    WorldScene scene = this.getEmptyScene();
    if (msg.equals("lost")) {
      scene = this.lostScene();
    }
    else if (msg.equals("win")) {
      scene = this.winScene();
    }
    else {
      scene = this.makeScene();
    }
    return scene;
  }

  // draw the scene if lost
  WorldScene lostScene() {
    WorldImage lost = new TextImage("You lost!!", FONT_SIZE, Color.GREEN);
    for (ArrayList<Cell> row : this.cells) {
      for (Cell c : row) {
        if (c.isBomb()) {
          c.reveal = true;
        }
      }
    }
    WorldScene scene = this.makeScene();
    scene.placeImageXY(lost, BG_WIDTH / 2, BG_HEIGHT / 2);
    return scene;
  }

  // draw the scene if win
  WorldScene winScene() {
    WorldScene scene = this.makeScene();
    WorldImage win = new TextImage("You win!!", FONT_SIZE, Color.GREEN);
    scene.placeImageXY(win, BG_WIDTH / 2, BG_HEIGHT / 2);
    return scene;
  }
}
