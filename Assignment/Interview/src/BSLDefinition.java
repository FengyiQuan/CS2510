// a definition in BSL
interface BSLDefinition {
  // get a function name by given definition
  ILoString getFunctionName();
}

// a constant definition
class BSLConstant implements BSLDefinition {
  BSLVariable name;
  BSLExpr definition;

  BSLConstant(BSLVariable name, BSLExpr definition) {
    this.name = name;
    this.definition = definition;
  }

  // return empty list of string if it is a constant
  public ILoString getFunctionName() {
    return new MtLoString();
  }
}

// a structure definition
class BSLStruct implements BSLDefinition {
  BSLVariable name;
  ILoVar fields;

  BSLStruct(BSLVariable name, ILoVar fields) {
    this.name = name;
    this.fields = fields;
  }

  // get all make-structname, structname?, or structname-structfield for its
  // struct
  public ILoString getFunctionName() {
    return new ConsLoString("make-" + this.name.getName(),
        new ConsLoString(this.name.getName() + "?", new MtLoString()))
            .append(this.fields.getAllSelector(this.name.getName()));
  }
}

// a function definition
class BSLFunction implements BSLDefinition {
  BSLVariable name;
  ILoVar lov;
  BSLExpr exp;

  BSLFunction(BSLVariable name, ILoVar lov, BSLExpr exp) {
    this.name = name;
    this.lov = lov;
    this.exp = exp;
  }

  // get its name of a function
  public ILoString getFunctionName() {
    return new ConsLoString(this.name.getName(), new MtLoString());
  }
}
