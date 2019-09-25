import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.*;
import java.awt.Color;

interface IGamePiece {

  // returns the value of a game piece
  int getValue();

  // returns the largest base tile value that can be found in the game piece
  int biggestBase();

  // combines this game piece with the given game piece to form a merged piece
  IGamePiece merge(IGamePiece given);

  // checks whether this game piece was created according to the rules of 2048:
  // only equal-valued pieces can merge
  boolean isValid();

}

class BaseTile implements IGamePiece {
  int value;

  BaseTile(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }

  public int biggestBase() {
    return this.value;
  }

  public IGamePiece merge(IGamePiece given) {
    return new MergeTile(given, this);
  }

  public boolean isValid() {
    return false;
  }
}

class MergeTile implements IGamePiece {
  IGamePiece piece1;
  IGamePiece piece2;

  MergeTile(IGamePiece p1, IGamePiece p2) {
    this.piece1 = p1;
    this.piece2 = p2;
  }

  public int getValue() {
    return this.piece1.getValue() + this.piece2.getValue();
  }

  public int biggestBase() {
    return Math.max(this.piece1.getValue(), this.piece2.getValue());
  }

  public IGamePiece merge(IGamePiece given) {
    return new MergeTile(given, this);
  }

  public boolean isValid() {
    return this.piece1.getValue() == this.piece2.getValue();
  }
}

class ExamplesShapes {

//shows image at the center of an equally-sized canvas,
  // and the text at the top of the frame is given by description
  boolean showImage(WorldImage image, String description) {
    int width = (int) Math.ceil(image.getWidth());
    int height = (int) Math.ceil(image.getHeight());
    WorldCanvas canvas = new WorldCanvas(width, height, description);
    WorldScene scene = new WorldScene(width, height);
    return canvas.drawScene(scene.placeImageXY(image, width / 2, height / 2)) && canvas.show();
  }

  WorldImage circleBlue = new CircleImage(100, OutlineMode.SOLID, Color.BLUE);
  WorldImage circleRed = new CircleImage(100, OutlineMode.SOLID, Color.RED);

  boolean testDrawMyShapes(Tester t) {
    return showImage(circleBlue, "Blue circle") && showImage(circleRed, "Red circle");
  }
}