import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;

class Utils {

  // produce a new ArrayList<T> containing all the items of the given list that
  // pass the predicate
  <T> ArrayList<T> filter(ArrayList<T> arr, IPred<T> pred) {
    ArrayList<T> result = new ArrayList<T>();
    for (T t : arr) {
      if (pred.apply(t))
        result.add(t);
    }
    return result;
  }

  // modifies the given list to remove everything that fails the predicate
  <T> void removeExcept(ArrayList<T> arr, IPred<T> pred) {
    for (int i = arr.size() - 1; i >= 0; i--) {
      if (!(pred.apply(arr.get(i)))) {
        arr.remove(i);
      }
    }
  }

}