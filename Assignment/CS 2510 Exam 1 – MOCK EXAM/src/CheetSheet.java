// reverse && append
interface ILoString {
  ILoString reverse();

  ILoString append(ILoString that);

  ILoString sortByPrice();

  ILoString insertBySomething(String b);
}

class MtLoString implements ILoString {

  public ILoString reverse() {
    return this;
  }

  public ILoString append(ILoString that) {
    return that;
  }

  public ILoString sortByPrice() {
    return this;
  }

  public ILoString insertBySomething(String b) {
    return new ConsLoString(b, this);
  }
}

class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String f, ILoString r) {
    this.first = f;
    this.rest = r;
  }

  public ILoString reverse() {
    return this.rest.reverse().append(new ConsLoString(first, new MtLoString()));
  }

  public ILoString append(ILoString that) {
    return new ConsLoString(this.first, this.rest.append(that));
  }

  public ILoString sortByPrice() {
    return this.rest.sortByPrice().insertBySomething(this.first);
  }

  public ILoString insertBySomething(String b) {
    if (this.first.compareTo("abc") > 0) {// a 在 b 前面输出 -1
      return new ConsLoString(this.first, this.rest.insertBySomething(b));
    }
    else {
      return new ConsLoString(b, this);
    }
  }
} 

class Utils {
  int checkRange(int val, int min, int max, String msg) {
    if (val >= min && val <= max) {
      return val;
    }
    else {
      throw new IllegalArgumentException(msg);
    }
  }
}