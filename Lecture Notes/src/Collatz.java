import tester.*;

/* Mathematical Enrichment Time!
 * 
 * collatz(n) = { n / 2  if n is even
 *                3n + 1 if n is odd
 *                
 * collatz(collatz(collatz(n)))
 * 
 * 64
 * 32
 * 16
 * 8
 * 4
 * 2
 * 1
 * 
 * is f a total function? 
 * f(n) = { 1         if n is 1
 *          f(n / 2)  if n is even
 *          f(3n + 1) if n is odd and not 1
 * 
 * Fermat's Last Theorem:
 * For all a,b,c in N+, 
 *   for no n > 2 in N 
 *     does a^n + b^n = c^n.
 * True! This took ~400 years to prove.
 * 
 * Euler's conjecture: 
 * For no w,x,y,z in N
 *  does x^4 + y^4 + z^4 = w^4
 * 
 *    2,682,440^4 
 *   15,365,639^4 
 * + 18,796,760^4 
 * = 20,615,673^4
 *
 * For more!
 * wired.com/2014/09/when-extrapolation-fails-us
 * 
 */

class CollatzUtils {
  
  int collatz (int n) {
    if (n % 2 == 0) { // if n is even 
      return n / 2;
    }
    else { 
      return (3 * n) + 1;
    }
  }

  boolean repeatedCollatz (int n) {
    for (; n != 1; n = collatz(n)) { }
    // while (n != 1) { n = collatz(n); }
    return true;
  }
  // Which loop to use where? 
  // A matter of taste! Usually what makes code simpler. 
}

class CollatzExamples {
  CollatzUtils u = new CollatzUtils();
  boolean val = this.u.repeatedCollatz(150);

  
}










