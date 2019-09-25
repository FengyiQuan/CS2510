import java.util.Arrays;

import tester.Tester;

import java.util.ArrayList;

class Utils {
  int sum(ArrayList<Integer> arr) {
    int sum = 0;
    for (Integer i : arr) {
      sum = sum + i;
    }
    return sum;
  }

  boolean positivePartialSums(ArrayList<Integer> arr) {
    boolean result = true;
    int sum = 0;
    for (Integer i : arr) {
      sum = sum + i;
      result = result && (sum >= 0);
    }
    return result;
  }

  boolean isSorted(ArrayList<String> arr) {
    boolean result = true;
    for (int i = 0; i <= arr.size() - 2; i++) {
      result = result && arr.get(i).compareTo(arr.get(i + 1)) <= 0;
    }
    return result;
  }

  boolean containsSequence(ArrayList<Integer> source, ArrayList<Integer> sequence) {
    boolean result = false;

    for (int i = 0; i <= source.size() - sequence.size(); i++) {
      boolean anySame = true;
      for (int j = 0; j < sequence.size(); j++) {
        anySame = anySame && source.get(j + i).equals(sequence.get(j));
      }
      result = anySame || result;
    }
    return result;
  }

}

class testForLoop {
  ArrayList<Integer> source = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
  ArrayList<Integer> sequence = new ArrayList<Integer>(Arrays.asList(4, 5));

  void test(Tester t) {
    t.checkExpect(new Utils().containsSequence(source, sequence), true);
  }
}