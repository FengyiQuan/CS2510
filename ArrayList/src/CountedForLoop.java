import java.util.ArrayList;

public class CountedForLoop {
  // maps the function onto every member of the list
  <T, U> ArrayList<U> map(ArrayList<T> alist, IFunc<T, U> fun) {
    ArrayList<U> result = new ArrayList<U>();
    for (int i = 0; i < alist.size(); i++) {
      result.add(fun.apply(alist.get(i)));
    }
    return result;
  }

  <T> boolean ormap(ArrayList<T> alist, IPred<T> pred) {
    boolean result = false;
    for (int i = 0; i < alist.size(); i++) {
      result = pred.apply(alist.get(i)) || result;
    }
    return result;
  }

  <T> boolean andmap(ArrayList<T> alist, IPred<T> pred) {
    boolean result = true;
    for (int i = 0; i < alist.size(); i++) {
      result = pred.apply(alist.get(i)) && result;
    }
    return result;
  }

  // build a list by given a function
  <U> ArrayList<U> buildList(int n, IFunc<Integer, U> func) {
    ArrayList<U> result = new ArrayList<U>();
    for (int i = 0; i < n; i = i + 1) {
      result.add(func.apply(i));
    }
    return result;
  }

  // combines the items in the list from right to left
  <U, T> U foldr(ArrayList<T> alist, IFunc2<T, U, U> fun, U base) {
    for (int i = alist.size() - 1; i > -1; i--) {
      base = fun.apply(alist.get(i), base);
    }
    return base;
  }

  // interleaves the two given lists of the same length
  <T> ArrayList<T> interleave(ArrayList<T> a, ArrayList<T> b) {
    ArrayList<T> result = new ArrayList<T>();
    for (int i = 0; i < a.size(); i++) {
      result.add(a.get(i));
      result.add(b.get(i));
    }
    return result;
  }
}
