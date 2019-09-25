import java.util.ArrayList;
import java.awt.Color;
import javalib.worldimages.*;

// a cell
class Cell {
  int size;
  boolean reveal;
  boolean mine;
  boolean mark;
  ArrayList<Cell> neighbors;
  Utils u = new Utils();

  Cell(boolean reveal, boolean mine, boolean mark, ArrayList<Cell> neighbors) {
    this.size = Game.GRID_SIZE;
    this.reveal = reveal;
    this.mine = mine;
    this.mark = mark;
    this.neighbors = neighbors;
  }

  Cell() {
    this.size = Game.GRID_SIZE;
    this.reveal = false;
    this.mine = false;
    this.mark = false;
    this.neighbors = new ArrayList<Cell>();
  }

  // determine if this cell is a bomb
  boolean isBomb() {
    return this.mine;
  }

  // EFFECT: add the given neighborhood to this cell
  void addNeighbors(ArrayList<Cell> n) {
    this.neighbors = n;
  }

  // Effect: change its mark to true
  void flaged() {
    this.mark = true;
  }

  // Effect: change its mine to true
  void changeToBomb() {
    this.mine = true;
  }

  // Effect: change its reveal to true and do flood-fill
  void reveal() {
    if (!this.reveal) {
      this.reveal = true;
      if (this.countBomb() == 0 && !this.isBomb()) {
        for (Cell c : this.neighbors) {
          c.reveal();
        }
      }
    }
  }

  // count the number of bombs in its neighbors list
  int countBomb() {
    return u.foldr(this.neighbors, new Bomb(), 0);
  }

  // connect one cell with its all neighbors
  void connectedOneCell(int row, int col, ArrayList<ArrayList<Cell>> grids) {

    if (row - 1 >= 0 && col - 1 >= 0) {
      Cell topLeft = grids.get(row - 1).get(col - 1);
      this.neighbors.add(topLeft);
    }
    if (row - 1 >= 0) {
      Cell top = grids.get(row - 1).get(col);
      this.neighbors.add(top);
    }
    if (row - 1 >= 0 && col + 1 < grids.get(row - 1).size()) {
      Cell topRight = grids.get(row - 1).get(col + 1);
      this.neighbors.add(topRight);
    }
    if (col - 1 >= 0) {

      Cell left = grids.get(row).get(col - 1);
      this.neighbors.add(left);
    }
    if (col + 1 < grids.get(row).size()) {
      Cell right = grids.get(row).get(col + 1);
      this.neighbors.add(right);
    }
    if (row + 1 < grids.size() && col - 1 >= 0) {
      Cell botLeft = grids.get(row + 1).get(col - 1);
      this.neighbors.add(botLeft);
    }
    if (row + 1 < grids.size()) {
      Cell bot = grids.get(row + 1).get(col);
      this.neighbors.add(bot);
    }
    if (row + 1 < grids.size() && col + 1 < grids.get(row + 1).size()) {

      Cell botRight = grids.get(row + 1).get(col + 1);
      this.neighbors.add(botRight);
    }
  }

  // draw cell
  WorldImage drawCell() {
    WorldImage frame = new RectangleImage(this.size, this.size, OutlineMode.OUTLINE, Color.black);
    WorldImage num = new TextImage(Integer.toString(this.countBomb()), Color.GREEN);
    WorldImage bomb = new CircleImage(Game.GRID_SIZE / 2, OutlineMode.SOLID, Color.MAGENTA);
    if (this.reveal) {
      if (this.mine) {
        return new OverlayImage(frame, new OverlayImage(bomb,
            new RectangleImage(this.size, this.size, OutlineMode.SOLID, Color.GRAY)));
      }
      else if (this.countBomb() == 0) {
        return new OverlayImage(frame,
            new RectangleImage(this.size, this.size, OutlineMode.SOLID, Color.GRAY));
      }
      else {
        return new OverlayImage(frame, new OverlayImage(num,
            new RectangleImage(this.size, this.size, OutlineMode.SOLID, Color.GRAY)));
      }
    }
    else if (this.mark) {
      return new OverlayImage(frame,
          new OverlayImage(
              new EquilateralTriangleImage(Game.GRID_SIZE / 3, OutlineMode.SOLID, Color.YELLOW),
              new RectangleImage(this.size, this.size, OutlineMode.SOLID, Color.DARK_GRAY)));
    }
    else {
      return new OverlayImage(frame,
          new RectangleImage(this.size, this.size, OutlineMode.SOLID, Color.DARK_GRAY));
    }
  }
}
