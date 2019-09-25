import tester.*;


class Posn { 
  int x;
  int y;

  Posn(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  Posn(Posn o) { 
    this(o.x,o.y);
  }
  
  void swapH(Posn other) { 
    this.x = other.x;
    this.y = other.y;
  }
  
  void swapPos(Posn other) {
    Posn temp = new Posn(this);
    this.swapH(other);
    other.swapH(temp);
  }
} 

class Examples  {
  Posn a;
  Posn b;
  
  void initData() { 
    this.a = new Posn(0,0);
    this.b = new Posn(3,4); 
  }
  
  void testWorks (Tester t) { 
    this.initData();
    t.checkExpect(this.a.x == 0 && this.a.y == 0 &&
        this.b.x == 3 && this.b.y == 4, true);
    this.a.swapPos(b);
    t.checkExpect(this.a.x == 3 && this.a.y == 4 && 
        this.b.x == 0 && this.b.y == 0, true);
  }
} 
