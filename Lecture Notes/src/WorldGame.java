import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color.*;


class Circs extends World { 
  // Circ dot;
  
  Circs() {
    
  }
  
  public WorldScene makeScene() { 
    return new WorldScene(600, 400);    
  }
  
  
}

class ExamplesWorldProgram { 
  // data stuff here? 

  boolean testBigBang (Tester t) { 
    // data stuff here? 
    Circs d = new Circs();
    int worldWid = 600;
    int worldHi = 400;
    double tickRate = .05;
    return d.bigBang(worldWid,worldHi,tickRate);
  }
}










