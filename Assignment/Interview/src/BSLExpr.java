// The following is code to represent an approximation of a subsection of Beginning Student Language
// https://docs.racket-lang.org/htdp-langs/beginner.html

// an expression in BSL
interface BSLExpr {
  // get all called function name
  ILoString getAllCalled();
}

// a number
class BSLInt implements BSLExpr {
  int num;

  BSLInt(int num) {
    this.num = num;
  }

  // get empty if it is a int
  public ILoString getAllCalled() {
    return new MtLoString();
  }
}

// a boolean
class BSLBool implements BSLExpr {
  boolean bool;

  BSLBool(boolean bool) {
    this.bool = bool;
  }

  // get empty if it is a boolean
  public ILoString getAllCalled() {
    return new MtLoString();
  }
}

// a string
class BSLString implements BSLExpr {
  String str;

  BSLString(String str) {
    this.str = str;
  }

  // get empty if it is a string
  public ILoString getAllCalled() {
    // TODO Auto-generated method stub
    return new MtLoString();
  }
}

// a variable (or name)
class BSLVariable implements BSLExpr {
  String variable;

  BSLVariable(String variable) {
    this.variable = variable;
  }

  // return its name in string form
  String getName() {
    return this.variable;
  }

  // get empty if it is a variable
  public ILoString getAllCalled() {
    return new MtLoString();
  }
}

// a function application
class BSLApplication implements BSLExpr {
  BSLVariable name;
  ILoExp loe;

  BSLApplication(BSLVariable n, ILoExp l) {
    this.name = n;
    this.loe = l;
  }

  // get all called function name
  public ILoString getAllCalled() {
    return new ConsLoString(this.name.getName(), this.loe.getAllFunctionCalled());
  }
}
