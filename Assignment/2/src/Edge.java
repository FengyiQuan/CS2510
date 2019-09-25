// edge
class Edge {
  GamePiece fromNode;
  GamePiece toNode;
  int weight;

  Edge(GamePiece fromNode, GamePiece toNode, int weight) {
    this.fromNode = fromNode;
    this.toNode = toNode;
    this.weight = weight;
  }

  boolean isTopDown() {
    return this.fromNode.row + 1 == this.toNode.row && this.fromNode.col == this.toNode.col;
  }

  boolean isLeftRight() {
    return this.fromNode.row == this.toNode.row && this.fromNode.col + 1 == this.toNode.col;
  }

}