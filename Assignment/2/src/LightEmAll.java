import java.util.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

// light them all(world state)
class LightEmAll extends World {
  static final int GAME_ROW = 8;
  static final int GAME_COLUMN = 8;
  static final int PIECE_SIZE = 50;
  static final int BG_WIDTH = PIECE_SIZE * GAME_COLUMN;
  static final int BG_HEIGHT = PIECE_SIZE * GAME_ROW;
  static final int FONT_SIZE = PIECE_SIZE / 3;

  Random rand = new Random();
  Utils u = new Utils();
  ArrayList<ArrayList<GamePiece>> board;
  // a list of all nodes
  ArrayList<GamePiece> nodes;
  // a list of edges of the minimum spanning tree
  ArrayList<Edge> mst;
  int width;
  int height;
  int powerRow;
  int powerCol;
  static int radius;
  int time;
  int steps;

  LightEmAll() {
    this.width = GAME_COLUMN;
    this.height = GAME_ROW;
    this.powerRow = 0;
    this.powerCol = GAME_COLUMN / 2;
    this.board = u.generateGrids(this.width, this.height);
    this.time = 0;
    this.steps = 0;
    this.nodes = new ArrayList<GamePiece>();
    this.unfoldBoard();
    this.mst = this.generateKruskal();
    this.modifier3();
    // this.modifier2(0, this.height - 1, 0, this.width - 1);
    this.connectAll();
    LightEmAll.radius = this.getDeepestLength() / 2 + 1;
//    this.shuffleAll();
//    this.disconnectedAll();
//    this.connectAll();

  }

  // disconnect all the game piece
  void disconnectedAll() {
    for (ArrayList<GamePiece> col : this.board) {
      u.disconnected(col);
    }
  }

  // connect the proper gamePiece
  void connectAll() {
    this.getPower().powerStation = true;
    this.getPower().isConnected = true;
    this.getPower().lev = radius;
    this.getPower().lightThemAll(this.board);
  }

  // for Part 2: shuffle all game piece
  void shuffleAll() {
    for (ArrayList<GamePiece> col : this.board) {
      u.shuffle(col);
    }
  }

  // Part 2: generate fractal-like wiring
  void modifier2(int startRow, int endRow, int startCol, int endCol) {
    int rowNum = endRow - startRow + 1;
    int colNum = endCol - startCol + 1;
    int midCol = (endCol + startCol) / 2;
    int midRow = (endRow + startRow) / 2;

    if (rowNum > 3 && colNum > 3) {
      this.modifier2(startRow, midRow, startCol, midCol);
      this.modifier2(startRow, midRow, midCol + 1, endCol);
      this.modifier2(midRow + 1, endRow, startCol, midCol);
      this.modifier2(midRow + 1, endRow, midCol + 1, endCol);

      this.board.get(startCol).get(midRow).bottom = true;
      this.board.get(startCol).get(midRow + 1).top = true;
      this.board.get(endCol).get(midRow).bottom = true;
      this.board.get(endCol).get(midRow + 1).top = true;
      this.board.get(midCol).get(endRow).right = true;
      this.board.get(midCol + 1).get(endRow).left = true;
    } else if (rowNum > 3) {
      this.modifier2(startRow, midRow, startCol, endCol);
      this.modifier2(midRow + 1, endRow, startCol, endCol);

      this.board.get(endCol).get(midRow).bottom = true;
      this.board.get(endCol).get(midRow + 1).top = true;
    } else if (colNum > 3) {
      this.modifier2(startRow, endRow, startCol, midCol);
      this.modifier2(startRow, endRow, midCol + 1, endCol);

      this.board.get(midCol).get(endRow).right = true;
      this.board.get(midCol + 1).get(endRow).left = true;
    } else {
      this.generateBase(startRow, endRow, startCol, endCol);
    }
  }

  void generateBase(int startRow, int endRow, int startCol, int endCol) {
    int rowNum = endRow - startRow + 1;
    int colNum = endCol - startCol + 1;
    if (colNum == 1 && rowNum == 2) {
      this.board.get(startCol).get(startRow).bottom = true;
      this.board.get(startCol).get(startRow + 1).top = true;
    } else if (colNum == 2 && rowNum == 1) {
      this.board.get(startCol).get(startRow).right = true;
      this.board.get(startCol + 1).get(startRow).left = true;
    } else if (colNum == 2 && rowNum == 2) {
      this.board.get(startCol).get(startRow).bottom = true;
      this.board.get(startCol).get(startRow + 1).top = true;
      this.board.get(startCol).get(startRow + 1).right = true;
      this.board.get(startCol + 1).get(startRow).bottom = true;
      this.board.get(startCol + 1).get(startRow + 1).top = true;
      this.board.get(startCol + 1).get(startRow + 1).left = true;
    } else if (colNum == 1 && rowNum == 3) {
      this.board.get(startCol).get(startRow).bottom = true;
      this.board.get(startCol).get(startRow + 1).top = true;
      this.board.get(startCol).get(startRow + 1).bottom = true;
      this.board.get(startCol).get(startRow + 2).top = true;
      this.board.get(startCol).get(startRow + 2).bottom = true;
    } else if (colNum == 2 && rowNum == 3) {
      this.board.get(startCol).get(startRow).bottom = true;
      this.board.get(startCol).get(startRow + 1).top = true;
      this.board.get(startCol).get(startRow + 1).bottom = true;
      this.board.get(startCol).get(startRow + 2).top = true;
      this.board.get(startCol).get(startRow + 2).right = true;
      this.board.get(startCol + 1).get(startRow).bottom = true;
      this.board.get(startCol + 1).get(startRow + 1).top = true;
      this.board.get(startCol + 1).get(startRow + 1).bottom = true;
      this.board.get(startCol + 1).get(startRow + 2).top = true;
      this.board.get(startCol + 1).get(startRow + 2).left = true;
    } else if (colNum == 3 && rowNum == 1) {
      this.board.get(startCol).get(startRow).right = true;
      this.board.get(startCol + 1).get(startRow).left = true;
      this.board.get(startCol + 1).get(startRow).right = true;
      this.board.get(startCol + 2).get(startRow).left = true;
    } else if (colNum == 3 && rowNum == 2) {
      this.board.get(startCol).get(startRow).bottom = true;
      this.board.get(startCol).get(startRow + 1).top = true;
      this.board.get(startCol).get(startRow + 1).right = true;
      this.board.get(startCol + 1).get(startRow).bottom = true;
      this.board.get(startCol + 1).get(startRow + 1).top = true;
      this.board.get(startCol + 1).get(startRow + 1).left = true;
      this.board.get(startCol + 1).get(startRow + 1).right = true;
      this.board.get(startCol + 2).get(startRow).bottom = true;
      this.board.get(startCol + 2).get(startRow + 1).top = true;
      this.board.get(startCol + 2).get(startRow + 1).left = true;
    } else if (colNum == 3 && rowNum == 3) {
      this.board.get(startCol).get(startRow).bottom = true;
      this.board.get(startCol).get(startRow + 1).top = true;
      this.board.get(startCol).get(startRow + 1).bottom = true;
      this.board.get(startCol).get(startRow + 1).right = true;
      this.board.get(startCol).get(startRow + 2).top = true;
      this.board.get(startCol).get(startRow + 2).right = true;
      this.board.get(startCol + 1).get(startRow).bottom = true;
      this.board.get(startCol + 1).get(startRow + 1).top = true;
      this.board.get(startCol + 1).get(startRow + 1).left = true;
      this.board.get(startCol + 1).get(startRow + 2).left = true;
      this.board.get(startCol + 1).get(startRow + 2).right = true;
      this.board.get(startCol + 2).get(startRow).bottom = true;
      this.board.get(startCol + 2).get(startRow + 1).top = true;
      this.board.get(startCol + 2).get(startRow + 1).bottom = true;
      this.board.get(startCol + 2).get(startRow + 2).top = true;
      this.board.get(startCol + 2).get(startRow + 2).left = true;
    }
  }

  // make scene
  public WorldScene makeScene() {
    WorldImage time = new TextImage("Time: " + Integer.toString(this.time), FONT_SIZE, Color.BLACK);
    WorldImage score = new TextImage("Step: " + Integer.toString(this.steps), FONT_SIZE,
        Color.BLACK);
    WorldImage restart = new TextImage("Restart", FONT_SIZE, Color.BLACK);
    WorldImage restartButton = new OverlayImage(new RectangleImage(((int) restart.getWidth()),
        ((int) restart.getHeight()), OutlineMode.OUTLINE, Color.BLACK), restart);
    WorldImage info = new AboveImage(time, score, restartButton);

    WorldScene scene = this.getEmptyScene();
    scene = u.draw(this.board, scene);
    scene.placeImageXY(info, BG_WIDTH / 2, BG_HEIGHT + ((int) info.getHeight() / 2));
    return scene;
  }

  // one mouse: rotate game piece
  public void onMousePressed(Posn pos, String buttonName) {
    int row = pos.y / LightEmAll.PIECE_SIZE;
    int col = pos.x / LightEmAll.PIECE_SIZE;
    if (buttonName.equals("LeftButton") && this.clickRestart(pos)) {
      // start a new game
      this.width = GAME_COLUMN;
      this.height = GAME_ROW;
      this.powerRow = 0;
      this.powerCol = GAME_COLUMN / 2;
      this.board = u.generateGrids(this.width, this.height);
      this.time = 0;
      this.steps = 0;
      this.modifier2(0, this.height - 1, 0, this.width - 1);
      this.connectAll();
      this.radius = this.getDeepestLength() / 2 + 1;
      this.shuffleAll();
      this.disconnectedAll();
      this.connectAll();
    }
    if (buttonName.equals("LeftButton") && 0 <= pos.x
        && pos.x <= LightEmAll.PIECE_SIZE * LightEmAll.GAME_COLUMN && 0 <= pos.y
        && pos.y <= LightEmAll.PIECE_SIZE * LightEmAll.GAME_ROW) {
      this.board.get(col).get(row).rotate();
      this.disconnectedAll();
      this.connectAll();
      this.steps++;
    }

  }

  // determine if user click restart button
  boolean clickRestart(Posn posn) {
    WorldImage time = new TextImage("Time: " + Integer.toString(this.time), FONT_SIZE, Color.BLACK);
    WorldImage score = new TextImage("Step: " + Integer.toString(this.steps), FONT_SIZE,
        Color.BLACK);
    WorldImage restart = new TextImage("Restart", FONT_SIZE, Color.BLACK);
    WorldImage restartButton = new OverlayImage(new RectangleImage(((int) restart.getWidth()),
        ((int) restart.getHeight()), OutlineMode.OUTLINE, Color.BLACK), restart);
    return (BG_WIDTH - ((int) restartButton.getWidth())) / 2 <= posn.x
        && posn.x <= (BG_WIDTH + ((int) restartButton.getWidth())) / 2
        && BG_HEIGHT + time.getHeight() + score.getHeight() <= posn.y
        && posn.y <= BG_HEIGHT + time.getHeight() + score.getHeight() + restartButton.getHeight();
  }

  // one key: pressing arrow key to move the power station
  public void onKeyEvent(String key) {
    this.getPower().powerStation = false;
    if (this.powerCol > 0 && key.equals("left")) {
      GamePiece left = this.board.get(this.powerCol - 1).get(this.powerRow);
      if (this.getPower().left && left.right) {
        this.powerCol--;
        this.steps++;
      }
    } else if (this.powerCol < this.board.size() - 1 && key.equals("right")) {
      GamePiece right = this.board.get(this.powerCol + 1).get(this.powerRow);
      if (this.getPower().right && right.left) {
        this.powerCol++;
        this.steps++;
      }
    } else if (this.powerRow > 0 && key.equals("up")) {
      GamePiece top = board.get(this.powerCol).get(this.powerRow - 1);
      if (this.getPower().top && top.bottom) {
        this.powerRow--;
        this.steps++;
      }
    } else if (this.powerRow + 1 < this.board.get(this.powerCol).size() && key.equals("down")) {
      GamePiece bot = board.get(this.powerCol).get(this.powerRow + 1);
      if (this.getPower().bottom && bot.top) {
        this.powerRow++;
        this.steps++;
      }
    }
    this.board.get(this.powerCol).get(this.powerRow).powerStation = true;
    this.disconnectedAll();
    this.connectAll();

  }

  // on-tick: increase 1 seconds per second
  public void onTick() {
    this.time++;
  }

  // draw win scene if win
  public WorldEnd worldEnds() {
    if (u.isAllLight(this.board)) {
      return new WorldEnd(true, this.winScene());
    } else {
      return new WorldEnd(false, this.makeScene());
    }
  }

  // draw the scene if win
  WorldScene winScene() {
    WorldScene scene = this.makeScene();
    WorldImage win = new TextImage("You win!!", FONT_SIZE, Color.GREEN);
    scene.placeImageXY(win, BG_WIDTH / 2, BG_HEIGHT / 2);
    return scene;
  }

  // get the power station GamePiece
  GamePiece getPower() {
    return this.board.get(this.powerCol).get(this.powerRow);
  }

  // get the distance from two game piece, return -1 if they are not connected
  int getDepth(GamePiece from, GamePiece to) {
    int level = 0;
    Queue<GamePiece> worklist = new LinkedList<GamePiece>();
    Deque<GamePiece> visited = new ArrayDeque<GamePiece>();
    worklist.add(from);
    worklist.add(null);

    while (!worklist.isEmpty()) {
      GamePiece next = worklist.remove();
      if (next == null) {
        level++;
        worklist.add(null);
        if (worklist.peek() == null) {
          break;
        } else {
          continue;
        }
      }
      if (next.sameGP(to)) {
        return level;
      }
      if (!visited.contains(next) && next != null) {
        worklist.addAll(next.findConnectedNeighbor(this.board));
        visited.addFirst(next);
      }
    }
    return -1;
  }

  // calculate the deepest length in this board
  int getDeepestLength() {
    GamePiece first = this.findTheDeepestGP(this.getPower());
    GamePiece second = this.findTheDeepestGP(first);
    return getDepth(first, second);
  }

  // BFS: find the deepest game piece by given start game piece
  GamePiece findTheDeepestGP(GamePiece from) {
    Queue<GamePiece> worklist = new LinkedList<GamePiece>();
    Deque<GamePiece> visited = new ArrayDeque<GamePiece>();
    worklist.add(from);
    while (!worklist.isEmpty()) {
      GamePiece next = worklist.remove();
      if (!visited.contains(next)) {
        worklist.addAll(next.findConnectedNeighbor(this.board));
        visited.addFirst(next);
      }
    }
    return visited.getFirst();
  }

  ArrayList<Edge> generateKruskal() {
    ArrayList<Edge> allEdge = this.generateAllEdge();
    allEdge.sort(new Sortbyweight());
    ArrayList<Edge> result = new ArrayList<Edge>();
    ArrayList<ArrayList<GamePiece>> nodeset = new ArrayList<ArrayList<GamePiece>>();
    int vertices = this.width * this.height;
    while (result.size() < vertices - 1) {
      Edge next = allEdge.remove(allEdge.size() - 1);

      if (next.fromNode.isInSets(nodeset) && next.toNode.isInSets(nodeset)) {
        ArrayList<GamePiece> setFrom = next.fromNode.findSet(nodeset);
        ArrayList<GamePiece> setTo = next.toNode.findSet(nodeset);

        if (!(setFrom.contains(next.toNode) && setTo.contains(next.fromNode))) {
          result.add(next);
          setFrom.addAll(setTo);
          nodeset.remove(setTo);
        }

      }
      if (next.fromNode.isInSets(nodeset) && !next.toNode.isInSets(nodeset)) {

        result.add(next);

        next.toNode.addtoSets(next.fromNode, nodeset);
      }
      if (!next.fromNode.isInSets(nodeset) && next.toNode.isInSets(nodeset)) {

        result.add(next);

        next.fromNode.addtoSets(next.toNode, nodeset);
      }
      if (!next.fromNode.isInSets(nodeset) && !next.toNode.isInSets(nodeset)) {

        ArrayList<GamePiece> set = new ArrayList<GamePiece>();
        set.add(next.fromNode);
        set.add(next.toNode);
        nodeset.add(set);

        result.add(next);

      }

    }
    return result;
  }

  class Sortbyweight implements Comparator<Edge> {

    public int compare(Edge o1, Edge o2) {
      return o1.weight - o2.weight;
    }
  }

  boolean visiteAllEdge(ArrayList<GamePiece> visited) {
    boolean result = true;
    for (GamePiece gp : this.nodes) {
      result = result && gp.isVisited(visited);
    }
    return result;
  }

  ArrayList<Edge> generateAllEdge() {
    ArrayList<Edge> result = new ArrayList<Edge>();
    for (int i = 0; i < this.board.size(); i++) {
      for (int j = 0; j < this.board.get(i).size(); j++) {
        if (i < this.board.size() - 1) {
          Edge rightLeft = new Edge(this.board.get(i).get(j), this.board.get(i + 1).get(j),
              rand.nextInt(15));
          result.add(rightLeft);
        }
        if (j < this.board.get(i).size() - 1) {
          Edge topDown = new Edge(this.board.get(i).get(j), this.board.get(i).get(j + 1),
              rand.nextInt(15));
          result.add(topDown);
        }
      }
    }
    return result;
  }

  void modifier3() {
    for (Edge edge : this.mst) {
      if (edge.isTopDown()) {
        edge.fromNode.bottom = true;
        edge.toNode.top = true;
      }
      if (edge.isLeftRight()) {
        edge.fromNode.right = true;
        edge.toNode.left = true;
      }
    }
  }

  void unfoldBoard() {
    for (int i = 0; i < this.board.size(); i++) {
      for (int j = 0; j < this.board.get(i).size(); j++) {
        this.nodes.add(this.board.get(i).get(j));
      }
    }
  }
}