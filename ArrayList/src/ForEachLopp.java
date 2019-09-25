import java.util.ArrayList;

class ForEachLopp {

  // maps the function onto every member of the list
  <T, U> ArrayList<U> map(ArrayList<T> alist, IFunc<T, U> fun) {
    ArrayList<U> result = new ArrayList<U>();
    for (T t : alist) {
      result.add(fun.apply(t));
    }
    return result;
  }

  <T> boolean ormap(ArrayList<T> alist, IPred<T> pred) {
    boolean result = false;
    for (T t : alist) {
      result = pred.apply(t) || result;
    }
    return result;
  }

  <T> boolean andmap(ArrayList<T> alist, IPred<T> pred) {
    boolean result = true;
    for (T t : alist) {
      result = pred.apply(t) && result;
    }
    return result;
  }

  // combines the items in the list from right to left
  <T, U> U foldr(ArrayList<T> alist, IFunc2<T, U, U> fun, U base) {
    ArrayList<T> reversed = new ArrayList<T>();
    for (T t : alist) {
      reversed.add(0, t);
    }
    return this.foldl(reversed, fun, base);
  }

  // combines the items in the list from left to right
  <T, U> U foldl(ArrayList<T> alist, IFunc2<T, U, U> fun, U base) {
    for (T t : alist) {
      base = fun.apply(t, base);
    }
    return base;
  }

  <T> void removeExcept(ArrayList<T> alist, IPred<T> pred) {
    ArrayList<T> temp = new ArrayList<T>();

    for (T t : alist) {
      if (pred.apply(t)) {
        temp.add(t);
      }
    }
    alist.clear();
    for (T t : temp) {
      alist.add(t);
    }
  }
}