import java.util.Random;
import tester.*;

/* Pseudorandom number generators!
 *
 * This sounds scary, but it's actually a great thing!  
 * 
 * If we give the constructor some input to begin with, then we get a
 * particular, deterministic "random" sequence of numbers. 
 * 
 * Every time I run this program, this rand object gives me the same 
 * numbers in the same order. 
 *
 * This is great for being able to write tests about them! 
 * 
 */ 

class RandExamples {
    Random rand; 
  
    void testRandom (Tester t) { 
	rand = new Random(15); // <- seed value (basis of our random sequence) !
	t.checkExpect(rand.nextInt(), -1159716814);
	t.checkExpect(rand.nextInt(), -898526952);
	t.checkExpect(rand.nextInt(), 453225476);
	t.checkExpect(rand.nextInt(), 1796952534);
	t.checkExpect(rand.nextInt(), -383587520);
    }

}
