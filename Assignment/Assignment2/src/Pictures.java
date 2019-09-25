import tester.Tester;

interface IPicture {
  // computes the overall width of this picture
  int getWidth();

  // computes the number of single shapes involved in producing the final image
  int countShapes();

  // computes how deeply operations are nested in the construction of this picture
  int comboDepth();

  // leave the entire image unchanged, except Beside combos, which should have
  // their two sub-images flipped (all names can remain untouched)
  IPicture mirror();

  // takes an integer depth, and produces a String representing the contents of
  // this picture, where the recipe for the picture has been expanded only depth
  // times
  String pictureRecipe(int depth);

  // get the name of this picture;
  String getName();
}

class Shape implements IPicture {
  String kind;
  int size;

  Shape(String kind, int size) {
    this.kind = kind;
    this.size = size;
  }

  public int getWidth() {
    return this.size;
  }

  public int countShapes() {
    return 1;
  }

  public int comboDepth() {
    return 0;
  }

  public IPicture mirror() {
    return this;
  }

  public String pictureRecipe(int depth) {
    return this.kind;
  }

  public String getName() {
    return this.kind;
  }
}

class Combo implements IPicture {
  String name;
  IOperation operation;

  Combo(String name, IOperation operation) {
    this.name = name;
    this.operation = operation;
  }

  //
  public int getWidth() {
    return this.operation.operationWidth();
  }

  public int countShapes() {
    return this.operation.countInOperation();
  }

  public IPicture mirror() {
    return new Combo(this.name, this.operation.flippedPic());
  }

  public int comboDepth() {
    return this.operation.countDepth();
  }

  public String pictureRecipe(int depth) {
    if (depth > 0) {
      return this.operation.getOperation(depth);
    }
    else {
      return this.name;
    }
  }

  public String getName() {
    return this.name;
  }
}

interface IOperation {

  //
  int operationWidth();

  int countInOperation();

  IOperation flippedPic();

  int countDepth();

  String getOperation(int depth);
}

// takes a single picture and draws it twice as large
class Scale implements IOperation {
  IPicture picture;

  Scale(IPicture p) {
    this.picture = p;
  }

  public int operationWidth() {
    return 2 * this.picture.getWidth();
  }

  public int countInOperation() {
    return this.picture.countShapes();
  }

  public IOperation flippedPic() {
    return new Scale(picture.mirror());
  }

  public int countDepth() {
    return 1 + this.picture.comboDepth();
  }

  public String getOperation(int depth) {
    if (depth > 0) {
      return "scale" + "(" + this.picture.pictureRecipe(depth - 1) + ")";
    }
    else {
      return this.picture.getName();
    }
  }
}

// takes two pictures, and draws picture1 to the left of picture2
class Beside implements IOperation {
  IPicture picture1;
  IPicture picture2;

  Beside(IPicture p1, IPicture p2) {
    this.picture1 = p1;
    this.picture2 = p2;
  }

  public int operationWidth() {
    return this.picture1.getWidth() + this.picture2.getWidth();
  }

  public int countInOperation() {
    return this.picture1.countShapes() + this.picture2.countShapes();
  }

  public IOperation flippedPic() {
    return new Beside(this.picture2.mirror(), this.picture1.mirror());
  }

  public int countDepth() {
    return 1 + Math.max(this.picture1.comboDepth(), this.picture2.comboDepth());
  }

  public String getOperation(int depth) {
    if (depth > 0) {
      return "beside" + "(" + this.picture1.pictureRecipe(depth - 1) + ", "
          + this.picture2.pictureRecipe(depth - 1) + ")";
    }
    else {
      return this.picture1.getName() + ", " + this.picture2.getName();
    }
  }
}

// takes two pictures, and draws top-picture on top of bottom-picture, with their centers aligned
class Overlay implements IOperation {
  IPicture topPicture;
  IPicture bottomPicture;

  Overlay(IPicture p1, IPicture p2) {
    this.topPicture = p1;
    this.bottomPicture = p2;
  }

  int biggerPic() {
    if (this.topPicture.getWidth() >= this.bottomPicture.getWidth()) {
      return this.topPicture.getWidth();
    }
    else {
      return this.bottomPicture.getWidth();
    }
  }

  public int operationWidth() {
    return this.biggerPic();
  }

  public int countInOperation() {
    return this.topPicture.countShapes() + this.bottomPicture.countShapes();
  }

  public IOperation flippedPic() {
    return new Overlay(this.topPicture.mirror(), this.bottomPicture.mirror());
  }

  public int countDepth() {
    return 1 + Math.max(this.topPicture.comboDepth(), this.bottomPicture.comboDepth());
  }

  public String getOperation(int depth) {
    if (depth > 0) {
      return "overlay" + "(" + this.topPicture.pictureRecipe(depth - 1) + ", "
          + this.bottomPicture.pictureRecipe(depth - 1) + ")";
    }
    else {
      return this.topPicture.getName() + ", " + this.bottomPicture.getName();
    }
  }
}

class ExamplesPicture {
  IPicture circle = new Shape("circle", 20);
  IPicture square = new Shape("square", 30);
  IPicture bigCircle = new Combo("big circle", new Scale(this.circle));
  IPicture squareOnCircle = new Combo("square on circle", new Overlay(this.square, this.bigCircle));
  IPicture doubledSquareOnCircle = new Combo("doubled square on circle",
      new Beside(this.squareOnCircle, this.squareOnCircle));

  IPicture triangle = new Shape("triangle", 10);
  IPicture bigTriangle = new Combo("big triangle", new Scale(this.triangle));
  IPicture triangleOnCircle = new Combo("trangle on circle",
      new Overlay(this.triangle, this.bigCircle));
  IPicture doubleTriangleOnCircle = new Combo("double triangle on circle",
      new Beside(this.triangleOnCircle, this.triangleOnCircle));

  // test
  boolean test(Tester t) {
    return t.checkExpect(this.doubledSquareOnCircle.comboDepth(), 3)
        && t.checkExpect(this.doubledSquareOnCircle.pictureRecipe(3),
            "beside(overlay(square, scale(circle)), overlay(square, scale(circle)))")
        && t.checkExpect(this.doubledSquareOnCircle.pictureRecipe(2),
            "beside(overlay(square, big circle), overlay(square, big circle))")
        && t.checkExpect(this.doubledSquareOnCircle.pictureRecipe(0), "doubled square on circle");

  }
}