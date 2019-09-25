interface ILoDef {
  // get all function name by given a list of definition
  ILoString getAllFunctionName();
}

class MtLoDef implements ILoDef {
  // get all function name by given a list of definition
  public ILoString getAllFunctionName() {
    return new MtLoString();
  }

}

class ConsLoDef implements ILoDef {
  BSLDefinition first;
  ILoDef rest;

  ConsLoDef(BSLDefinition f, ILoDef r) {
    this.first = f;
    this.rest = r;
  }

  // get all function name by given a list of definition
  public ILoString getAllFunctionName() {
    return this.first.getFunctionName().append(this.rest.getAllFunctionName());
  }
}
