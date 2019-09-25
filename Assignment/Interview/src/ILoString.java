// list of String
interface ILoString {
  // append two list
  ILoString append(ILoString that);

  // determine if all function name are defined
  boolean areAllDefine(ILoString defined);

  // determine if given function name are defined in a list of String
  boolean isDefined(String called);
}

class MtLoString implements ILoString {

  // append two list
  public ILoString append(ILoString that) {
    return that;
  }

  // determine if all function name are defined
  public boolean areAllDefine(ILoString defined) {
    return true;
  }

  // determine if given function name are defined in a list of String
  public boolean isDefined(String called) {
    return false;
  }
}

class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  // append two list
  public ILoString append(ILoString that) {
    return new ConsLoString(this.first, this.rest.append(that));
  }

  // determine if all function name are defined
  public boolean areAllDefine(ILoString defined) {
    return defined.isDefined(this.first) && this.rest.areAllDefine(defined);
  }

  // determine if given function name are defined in a list of String
  public boolean isDefined(String called) {
    return this.first.equals(called) || this.rest.isDefined(called);
  }
}