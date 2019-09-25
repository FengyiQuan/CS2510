import java.util.ArrayList;
import java.util.Random;
import javalib.impworld.*;

class Utils {
  // determine is there any member of the list satisfying by the given predicate
  <T> boolean ormap(ArrayList<T> alist, IPred<T> pred) {
    boolean result = false;
    for (T t : alist) {
      result = pred.apply(t) || result;
    }
    return result;
  }

  // determine every member of the list satisfying by the given predicate
  <T> boolean andmap(ArrayList<T> alist, IPred<T> pred) {
    boolean result = true;
    for (T t : alist) {
      result = pred.apply(t) && result;
    }
    return result;
  }

  // combines the items in the list from right to left
  <U, T> U foldr(ArrayList<T> alist, IFunc2<T, U, U> fun, U base) {
    for (int i = alist.size() - 1; i > -1; i--) {
      base = fun.apply(alist.get(i), base);
    }
    return base;
  }

  // generate the whole grids for minesweeper game
  ArrayList<ArrayList<Cell>> generateGrids(int col, int row) {
    ArrayList<ArrayList<Cell>> grids = new ArrayList<ArrayList<Cell>>();

    for (int j = 0; j < row; j++) {
      grids.add(this.getOneRow(col));
    }
    return grids;
  }

  // generate one row for minesweeper game
  ArrayList<Cell> getOneRow(int col) {
    ArrayList<Cell> eachRow = new ArrayList<Cell>();

    for (int i = 0; i < col; i++) {
      eachRow.add(new Cell());
    }
    return eachRow;
  }

  // connect all cells with their neighbors
  void connectedNeighbors(ArrayList<ArrayList<Cell>> grids) {
    for (ArrayList<Cell> oneRow : grids) {
      for (Cell column : oneRow) {
        column.connectedOneCell(grids.indexOf(oneRow), oneRow.indexOf(column), grids);
      }
    }
  }

  // change given position cells to mines
  void addBomb(ArrayList<ArrayList<Cell>> grids, ArrayList<Integer> index) {
    for (int ind : index) {
      int row = ind / Game.GAME_COLUMN;
      int col = ind % Game.GAME_COLUMN;
      grids.get(row).get(col).changeToBomb();
    }
  }

  // draw game
  WorldScene draw(ArrayList<ArrayList<Cell>> grids, WorldScene scene) {
    for (ArrayList<Cell> row : grids) {
      for (Cell cell : row) {
        scene.placeImageXY(cell.drawCell(), row.indexOf(cell) * cell.size + cell.size / 2,
            grids.indexOf(row) * cell.size + cell.size / 2);
      }
    }
    return scene;
  }

  // get all cells position in the grids
  ArrayList<Integer> allIndex(int row, int col) {
    ArrayList<Integer> result = new ArrayList<Integer>();
    int all = row * col;
    for (int i = 0; i < all; i++) {
      result.add(i);
    }
    return result;
  }

  // get all bomb position in the grids
  ArrayList<Integer> allBombIndex(ArrayList<Integer> allIndex, int num) {
    ArrayList<Integer> allBombIndex = new ArrayList<Integer>();
    for (int i = 0; i < num; i++) {
      Random rand = new Random();
      allBombIndex.add(allIndex.remove(rand.nextInt(allIndex.size() - 1)));
    }
    return allBombIndex;
  }
}
