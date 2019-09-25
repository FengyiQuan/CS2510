import java.util.*;
import tester.Tester;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
public class PermutationCode {
  // The original list of characters to be encoded
  ArrayList<Character> alphabet = new ArrayList<Character>(
      Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
          'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code = new ArrayList<Character>(26);

  // A random number generator
  Random rand = new Random();

  // Create a new instance of the encoder/decoder with a new permutation code
  PermutationCode() {
    this.code = this.initEncoder();
    this.rand = new Random();
  }

  // Create a new instance of the encoder/decoder with the given code
  PermutationCode(ArrayList<Character> code) {
    this.code = code;
  }

  // constructor for test
  PermutationCode(ArrayList<Character> code, Random n) {
    this.code = code;
    this.rand = n;
  }

  /*
   * fields:
   * this.code ... ArrayList<Character>
   * this.rand ... Random
   * 
   * methods:
   * this.initEncoder() ... ArrayList<Character>
   * this.encode(String) ... String
   * this.decode(String) ... String
   */

  // Initialize the encoding permutation of the characters
  ArrayList<Character> initEncoder() {
    ArrayList<Character> temp = new ArrayList<Character>(this.alphabet);
    ArrayList<Character> result = new ArrayList<Character>();
    for (int i = 0; i < this.alphabet.size(); i++) {
      result.add(temp.remove(rand.nextInt(temp.size())));
    }
    return result;
  }

  // produce an encoded String from the given String
  String encode(String source) {
    Utils u = new Utils();
    ArrayList<Character> result = new ArrayList<Character>();
    // get a array list of all characters in a string
    for (Character c : source.toCharArray()) {
      result.add(c);
    }
    return u.foldr(result, new Encode(this.alphabet, this.code), "");
  }

  // produce a decoded String from the given String
  String decode(String code) {
    Utils u = new Utils();
    ArrayList<Character> result = new ArrayList<Character>();
    // get a array list of all characters in a decoded string
    for (Character c : code.toCharArray()) {
      result.add(c);
    }
    return u.foldr(result, new Encode(this.code, this.alphabet), "");
  }
}

interface IFunc2<X, Y, Z> {
  Z apply(X x, Y y);
}

// produce an encoded String from the given Character
class Encode implements IFunc2<Character, String, String> {
  ArrayList<Character> original;
  ArrayList<Character> coder;

  Encode(ArrayList<Character> original, ArrayList<Character> coder) {
    this.original = original;
    this.coder = coder;
  }

  public String apply(Character x, String y) {
    return coder.get(original.indexOf(x)).toString().concat(y);
  }
}

class Utils {
  // combines the items in the list from right to left
  <U, T> U foldr(ArrayList<T> alist, IFunc2<T, U, U> fun, U base) {
    for (int i = alist.size() - 1; i > -1; i--) {
      base = fun.apply(alist.get(i), base);
    }
    return base;
  }
}

class ExamplesCode {
  String source;
  String code;
  ArrayList<Character> alphabet;
  ArrayList<Character> result;

  void iniData() {
    this.source = "cabbed";
    this.code = "abeedc";
    this.alphabet = new ArrayList<Character>(Arrays.asList('b', 'e', 'a', 'c', 'd', 'f', 'g', 'h',
        'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));
    this.result = new ArrayList<Character>(Arrays.asList('r', 'n', 'h', 'o', 'y', 'q', 't', 'u',
        'l', 'v', 'a', 'x', 'g', 'i', 'k', 'c', 'e', 'j', 'z', 's', 'f', 'm', 'd', 'w', 'b', 'p'));
  }

  void testInitEncoder(Tester t) {
    this.iniData();
    t.checkExpect(new PermutationCode(this.alphabet, new Random(1)).initEncoder(), this.result);
  }

  void testEncode(Tester t) {
    this.iniData();
    t.checkExpect(new PermutationCode(new PermutationCode().alphabet).encode("abc"), "abc");
    t.checkExpect(new PermutationCode(this.alphabet).encode(this.source), this.code);
  }

  void testDecode(Tester t) {
    this.iniData();
    t.checkExpect(new PermutationCode(new PermutationCode().alphabet).decode("abc"), "abc");
    t.checkExpect(new PermutationCode(this.alphabet).decode(this.code), this.source);
  }
}
