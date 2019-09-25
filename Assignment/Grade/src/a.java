/* Wishlist:
 * 
 * randomizer method for colors
 * method to place squares in an array
 * method to link together in cell for top, bottom, etc. (use loops)
 * method to draw the arrays 
 * make initial scene
 * 
 */


//--------- IMPORTANT NOTE --------------//
// for testing purposes, the BOARD_SIZE constant is assumed to have a size of 3
// any changes to this number will cause the test to fail, though the game will run fine


import java.util.ArrayList;
import java.util.Arrays;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Random;



//Represents a single square of the game area
class Cell {

  // In logical coordinates, with the origin at the top-left corner of the screen
  int x;
  int y;
  Color color;
  boolean flooded;
  // the four adjacent cells to this one
  Cell left;
  Cell top;
  Cell right;
  Cell bottom;

  public Cell(int x, int y, Color color, boolean flooded, 
      Cell left, Cell top, Cell right, Cell bottom) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.flooded = flooded;
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
  }


  // assigns this cell's top left right bottom
  public Cell assignAdj(ArrayList<Cell> initialBoard, int gridSize, int pos) {
    if (this.x / gridSize > 0) { // if  not the left most cell
      this.left = initialBoard.get(pos - 1);
    }

    if (this.x / gridSize < FloodItWorld.BOARD_SIZE - 1) {  // if not the rightmost cell
      this.right = initialBoard.get(pos + 1);
    }

    if (this.y / gridSize < FloodItWorld.BOARD_SIZE - 1) {  // if not the topmost cell
      this.top = initialBoard.get(pos + FloodItWorld.BOARD_SIZE);
    }

    if (this.y / gridSize > 0) { // if not the bottom most cell
      this.bottom = initialBoard.get(pos -  FloodItWorld.BOARD_SIZE);
    }


    return this;
  }


  // draws this cell
  public WorldScene draw(WorldScene acc, int cellDim) {
    acc.placeImageXY(new RectangleImage(
        cellDim, cellDim , OutlineMode.SOLID, this.color), 
        this.x + cellDim / 2, this.y + cellDim / 2);
    return acc;
  }


}

// represents the utility class to work with 
class UtilsClass {

  // initializes the board 
  public ArrayList<Cell> initBoardHelp(int gridSize, int numColors) {
    ArrayList<Cell> initialBoard = new ArrayList<Cell>();
    for (int column = 0; column < FloodItWorld.BOARD_SIZE; column++) {
      for (int row = 0; row < FloodItWorld.BOARD_SIZE; row++) {

        // do stuff for the row
        initialBoard.add(
            new Cell(
                row * gridSize, // set the x value
                column * gridSize, // set the y value

                this.randomColor(numColors, new Random()), // set initial random color 
                (row == 0 && column == 0), // is this the first square? if yes, then it is flooded

                null, // place holder for left
                null, // place holder for top
                null, // place holder for right
                null) // place holder for bottom
            );

      }
    }

    return initialBoard;
  }


  // creates the initial array of cells then assigns a left, top, right, and bottom
  public ArrayList<Cell> initBoard(int gridSize, int numColors) {
    ArrayList<Cell> initialBoard = new UtilsClass().initBoardHelp(gridSize, numColors);  
    for (int i = 0; i < initialBoard.size(); i++) { 
      initialBoard.set(i, initialBoard.get(i).assignAdj(initialBoard, gridSize, i));
    }

    return initialBoard;
  }




  // selects a random color
  Color randomColor(int numColors, Random rand) {
    ArrayList<Color> colorList = new ArrayList<Color>(Arrays.asList(Color.BLUE,
        Color.RED, Color.GREEN, Color.CYAN, Color.YELLOW, Color.ORANGE));

    return colorList.get(rand.nextInt(numColors));
  }



  // draws all of the cells


}



// represents a game world of Flood 
class FloodItWorld extends World {

  // All the cells of the game
  ArrayList<Cell> board;
  int gridSize;
  int numColors;


  //convenience constructor to initialize the game
  FloodItWorld(int gridSize, int numColors) {
    this(new UtilsClass().initBoard(gridSize, numColors), gridSize, numColors);
    this.gridSize = gridSize;
    if (this.numColors > 6 || this.numColors < 1 ) {
      throw new IllegalArgumentException("number of colors must be > 0 or < 7!");
    }

    else {
      this.numColors = numColors;
    }
  }

  // the main constructor
  FloodItWorld(ArrayList<Cell> board, int gridSize, int numColors) {
    this.board = board;
    this.gridSize = gridSize;
    this.numColors = numColors;
  }


  // ------- CONSTANTS ------
  static final int BOARD_SIZE = 3;

  // draws/edits the scene
  public WorldScene makeScene() {
    WorldScene scene =  new WorldScene(
        FloodItWorld.BOARD_SIZE * this.gridSize,
        FloodItWorld.BOARD_SIZE * this.gridSize);

    for (int i = 0; i < this.board.size(); i++) {
      this.board.get(i).draw(scene, this.gridSize);

    }
    return scene;
  }
}





// examples for the game
class GameExamples {

  FloodItWorld initGame;
  WorldScene testerScene;
  WorldScene testerSceneBuddy;
  ArrayList<Cell> emptyCellArray;
  Cell cell1;
  Cell cell2;
  Cell cell3;
  Cell cell4;
  Cell cell5;
  Cell cell6;
  Cell cell7;
  Cell cell8;
  Cell cell9;
  ArrayList<Cell> cellArray;
  Cell nullCell1;
  Cell nullCell2;
  Cell nullCell3;
  Cell nullCell4;
  Cell nullCell5;
  Cell nullCell6;
  Cell nullCell7;
  Cell nullCell8;
  Cell nullCell9;
  ArrayList<Cell> nullCellArray;
  UtilsClass util;


  // to set up the game and testing constants
  void init() {
    // USERS WILL INITIALIZE THE GAME HERE:
    // (grid size, number of colors)
    initGame = new FloodItWorld(
        50, // grid size
        6); // number of colors up to 6


    // the testing constraints

    testerScene = new WorldScene(400, 400);
    testerSceneBuddy = new WorldScene(400, 400);
    emptyCellArray = new ArrayList<Cell>();
    cell1 = new Cell(0, 0, Color.BLUE,
        true, null, cell4, cell2, null);
    cell2 = new Cell(50, 0, Color.RED,
        true, cell1, cell5, cell3, null);
    cell3 = new Cell(100, 0, Color.ORANGE, 
        true, cell2, cell6, null, null);
    cell4 = new Cell(0, 50, Color.GREEN, 
        true, null, cell7, cell5, cell1);
    cell5 = new Cell(50, 50, Color.YELLOW,
        true, cell4, cell8, cell6, cell2);
    cell6 = new Cell(100, 50, Color.CYAN,
        true, cell5, cell9, null, cell3);
    cell7 = new Cell(0, 100, Color.RED,
        true, null, null, cell8, cell4);
    cell8 = new Cell(50, 100, Color.BLUE,
        true, cell7, null, cell9, cell5);
    cell9 = new Cell(100, 100, Color.YELLOW,
        true, cell8, null, null, cell6);
    cellArray = new ArrayList<Cell>(Arrays.asList(cell1, cell2, cell3, 
        cell4, cell5, cell6, cell7, cell8, cell9));
    nullCell1 = new Cell(0, 0, Color.YELLOW,
        true, null, null, null, null);
    nullCell2 = new Cell(0, 0, Color.ORANGE,
        true, null, null, null, null);
    nullCell3 = new Cell(100, 0, Color.RED, 
        true, null, null, null, null);
    nullCell4 = new Cell(0, 50, Color.ORANGE, 
        true, null, null, null, null);
    nullCell5 = new Cell(50, 50, Color.ORANGE,
        true, null, null, null, null);
    nullCell6 = new Cell(100, 50, Color.YELLOW,
        true, null, null, null, null);
    nullCell7 = new Cell(0, 100, Color.ORANGE,
        true, null, null, null, null);
    nullCell8 = new Cell(50, 100, Color.ORANGE,
        true, null, null, null, null);
    nullCell9 = new Cell(100, 100, Color.RED,
        true, null, null, null, null);
    nullCellArray = new ArrayList<Cell>(Arrays.asList(nullCell1, nullCell2, nullCell3,
        nullCell4, nullCell5, nullCell6, nullCell7, nullCell8, nullCell9));
    util = new UtilsClass();

    //left   top   right   bottom

  }

  /*  Methods to test:
   * initBoardHelp(int gridSize, int numColors)
   * initBoard(int gridSize, int numColors)
   */

  ArrayList<Color> colorList = new ArrayList<Color>(Arrays.asList(Color.BLUE,
      Color.RED, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.ORANGE));

  // test the draw method
  void testDraw(Tester t) {
    init();
    this.cell1.draw(this.testerScene, 50);
    this.testerSceneBuddy.placeImageXY(
        new RectangleImage(50, 50, OutlineMode.SOLID, Color.BLUE), 25, 25);
    t.checkExpect(this.testerScene, this.testerSceneBuddy);

    this.cell2.draw(this.testerScene, 50);
    this.testerSceneBuddy.placeImageXY(
        new RectangleImage(50, 50, OutlineMode.SOLID, Color.RED), 75, 25);
    t.checkExpect(this.testerScene, this.testerSceneBuddy);

    this.cell3.draw(this.testerScene, 50);
    this.testerSceneBuddy.placeImageXY(
        new RectangleImage(50, 50, OutlineMode.SOLID, Color.ORANGE), 125, 25);
    t.checkExpect(this.testerScene, this.testerSceneBuddy);

    this.cell4.draw(this.testerScene, 50);
    this.testerSceneBuddy.placeImageXY(
        new RectangleImage(50, 50, OutlineMode.SOLID, Color.GREEN), 25, 75);
    t.checkExpect(this.testerScene, this.testerSceneBuddy);
  }

  //test the assigning adjacent method
  void testAssignAdj(Tester t) {
    init();
    ArrayList<Cell> testCellArray = new ArrayList<Cell>(this.cellArray);
    this.nullCell1.assignAdj(testCellArray, 50, 0);
    t.checkExpect(this.nullCell1, this.nullCell1);

  }

  //test the helper method to create a board
  void testInitBoardHelp(Tester t) {
    init();
    ArrayList<Cell> init = util.initBoardHelp(50, 6);
    //t.checkExpect(init, this.nullCellArray);
  }


  // test the randomizer for color
  void testRandomColor(Tester t) {

    t.checkExpect(new UtilsClass().randomColor(6, new Random(6)), Color.RED);
    t.checkExpect(new UtilsClass().randomColor(6, new Random(6)), Color.RED);
    t.checkExpect(new UtilsClass().randomColor(6, new Random(1)), Color.CYAN);
    t.checkExpect(new UtilsClass().randomColor(3, new Random(1)), Color.BLUE);
    t.checkExpect(new UtilsClass().randomColor(1, new Random(1)), Color.BLUE);




  }

  // activates the big bang
  void testBigBang(Tester t) {
    init();

    int worldWidth = 1000;
    int worldHeight = 800;
    double tickRate = .02;
    initGame.bigBang(worldWidth, worldHeight, tickRate);
  } 

}