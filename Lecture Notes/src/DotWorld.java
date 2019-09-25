import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;
import java.util.Random;

// The procedure for writing a World program is like F1 
// What doesn't? 
// What changes? <That's the data defn>
// What do we want to interact with, change with? 

class Dot { 
  int RADIUS = 5;
  Color c;
  int x;
  int y;

  Dot(){
    this(Color.RED,0,0);
  }

  Dot(Color c, int x, int y) { 
    this.c = c;
    this.x = x;
    this.y = y;
  }

  WorldScene draw(WorldScene ws) { 
    return ws.placeImageXY(new CircleImage(this.RADIUS,"solid",this.c), this.x, this.y);
  }

  //create a new dot that is shifted on the x-axis
  Dot upDot() { 
    Random r = new Random(); 
    return new Dot(this.c,this.x + r.nextInt(3),this.y + r.nextInt(2));
  }
}

// We just want a Dot to move across the screen. 
// The javalib library has a class that extends the World (which is an abstr class)  
// This means there will be some things you have to implement
// A World is an abstract class; groups together some common functionality
// The one method you have to implement is makeScene().
// 

class Dots extends World {
  Dot dot;

  Dots() { 
    this(new Dot());
  }
  
  Dots(Dot dot) { 
    this.dot = dot;
  }

  public WorldScene makeScene () { 
    // return this.dot.draw(new WorldScene(600,400)); // should probably be constants, eh?
    return this.dot.draw(this.getEmptyScene()); 
  }

  public World onTick() { 
    return new Dots(this.dot.upDot());
  }
}

// The notional idea is that we have a thing that gets a scene, and goes again

class ExamplesMyWorldProgramCATS {

  boolean testBigBang(Tester t) {
    // test-level constants go *inside* the test
    Dots w = new Dots();
    // Window sizes
    // These can be different from the size of the scene. 
    int worldWidth = 600;
    int worldHeight = 400;
    double tickRate = .04;
    // Set the "registers". 
    // Kick off the loop.
    return w.bigBang(worldWidth, worldHeight, tickRate);
  }
}