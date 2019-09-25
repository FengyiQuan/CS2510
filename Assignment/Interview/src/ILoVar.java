// a list of variables
interface ILoVar {
  // get all selector function name
  ILoString getAllSelector(String structName);
}

// an empty list of variables
class MtLoVar implements ILoVar {

  // get all selector function name if there is an empty list of variables
  public ILoString getAllSelector(String structName) {
    return new MtLoString();
  }
}

// a non-empty list of variables
class ConsLoVar implements ILoVar {
  BSLVariable first;
  ILoVar rest;

  ConsLoVar(BSLVariable first, ILoVar rest) {
    this.first = first;
    this.rest = rest;
  }

  // get all selector function name by a list of variable
  public ILoString getAllSelector(String structName) {
    return new ConsLoString(structName + "-" + this.first.getName(),
        this.rest.getAllSelector(structName));
  }
}
