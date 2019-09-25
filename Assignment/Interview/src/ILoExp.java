// list of BSL expression
interface ILoExp {
  // get all function name being called by given a list of expression
  ILoString getAllFunctionCalled();
}

class MtLoExp implements ILoExp {

  // get all function name being called by given a empty list of expression
  public ILoString getAllFunctionCalled() {
    return new MtLoString();
  }
}

class ConsLoExp implements ILoExp {
  BSLExpr first;
  ILoExp rest;

  ConsLoExp(BSLExpr f, ILoExp r) {
    this.first = f;
    this.rest = r;
  }

  // get all function name being called by given a list of expression
  public ILoString getAllFunctionCalled() {
    return this.first.getAllCalled().append(this.rest.getAllFunctionCalled());
  }
}
