import tester.Tester;

class ExamplesBSL {
  ILoVar mtVar = new MtLoVar();
  ILoVar aAndbVar = new ConsLoVar(new BSLVariable("a"),
      new ConsLoVar(new BSLVariable("b"), this.mtVar));
  ILoVar xAndyVar = new ConsLoVar(new BSLVariable("x"),
      new ConsLoVar(new BSLVariable("y"), this.mtVar));

  ILoExp mtExp = new MtLoExp();
  ILoExp twoAndThree = new ConsLoExp(new BSLInt(2), new ConsLoExp(new BSLInt(3), this.mtExp));
  ILoExp threeAndFour = new ConsLoExp(new BSLInt(3), new ConsLoExp(new BSLInt(4), this.mtExp));

  BSLExpr tru23 = new BSLApplication(new BSLVariable("tru"), this.twoAndThree);
  BSLExpr posn34 = new BSLApplication(new BSLVariable("make-posn"), this.threeAndFour);

  ILoExp p34 = new ConsLoExp(this.posn34, this.mtExp);
  BSLExpr get3 = new BSLApplication(new BSLVariable("posn-x"), this.p34);
  ILoExp falseAnd3 = new ConsLoExp(new BSLBool(false), new ConsLoExp(this.get3, this.mtExp));
  BSLExpr return3 = new BSLApplication(new BSLVariable("fals"), this.falseAnd3);

  BSLDefinition zero = new BSLConstant(new BSLVariable("ZERO"), new BSLInt(0));
  BSLDefinition tru = new BSLFunction(new BSLVariable("tru"), this.aAndbVar, new BSLVariable("a"));
  BSLDefinition fals = new BSLFunction(new BSLVariable("fals"), this.aAndbVar,
      new BSLVariable("b"));
  BSLDefinition posn = new BSLStruct(new BSLVariable("posn"), this.xAndyVar);

  ILoDef mtDef = new MtLoDef();
  ILoDef myListDef = new ConsLoDef(this.zero,
      new ConsLoDef(this.tru, new ConsLoDef(this.fals, new ConsLoDef(this.posn, this.mtDef))));
  ILoExp myListExp = new ConsLoExp(this.tru23, new ConsLoExp(this.return3, this.mtExp));

  Program myProgram = new Program(this.myListDef, this.myListExp);

  ILoString mts = new MtLoString();

  void testAllFunctionsDefined(Tester t) {
    t.checkExpect(this.myProgram.allFunctionsDefined(), true);
  }

  void testGetAllCalled(Tester t) {
    t.checkExpect(this.tru23.getAllCalled(), new ConsLoString("tru", this.mts));
    t.checkExpect(this.get3.getAllCalled(),
        new ConsLoString("posn-x", new ConsLoString("make-posn", this.mts)));
    t.checkExpect(this.return3.getAllCalled(), new ConsLoString("fals",
        new ConsLoString("posn-x", new ConsLoString("make-posn", this.mts))));
  }

  void testGetFunctionName(Tester t) {
    t.checkExpect(this.zero.getFunctionName(), this.mts);
    t.checkExpect(this.posn.getFunctionName(),
        new ConsLoString("make-posn", new ConsLoString("posn?",
            new ConsLoString("posn-x", new ConsLoString("posn-y", this.mts)))));

  }

  void testGetAllFunctionName(Tester t) {
    t.checkExpect(this.mtDef.getAllFunctionName(), this.mts);
    t.checkExpect(this.myListDef.getAllFunctionName(),
        new ConsLoString("tru",
            new ConsLoString("fals", new ConsLoString("make-posn", new ConsLoString("posn?",
                new ConsLoString("posn-x", new ConsLoString("posn-y", this.mts)))))));
  }

  void testGetAllFunctionCalled(Tester t) {
    t.checkExpect(this.mtExp.getAllFunctionCalled(), this.mts);
    t.checkExpect(this.p34.getAllFunctionCalled(), new ConsLoString("make-posn", this.mts));
  }

  void testGetAllSelector(Tester t) {
    t.checkExpect(this.mtVar.getAllSelector("a"), this.mts);
    t.checkExpect(this.xAndyVar.getAllSelector("a"),
        new ConsLoString("a-x", new ConsLoString("a-y", this.mts)));
  }

  void testAppend(Tester t) {
    t.checkExpect(this.mts.append(this.mts), this.mts);
    t.checkExpect(this.mts.append(new ConsLoString("a", this.mts)),
        new ConsLoString("a", this.mts));
    t.checkExpect(new ConsLoString("a", this.mts).append(this.mts),
        new ConsLoString("a", this.mts));
    t.checkExpect(new ConsLoString("a", this.mts).append(new ConsLoString("b", this.mts)),
        new ConsLoString("a", new ConsLoString("b", this.mts)));
  }

  void testAreAllDefine(Tester t) {
    t.checkExpect(this.mts.areAllDefine(this.mts), true);
    t.checkExpect(this.mts.areAllDefine(new ConsLoString("a", this.mts)), true);
    t.checkExpect(new ConsLoString("a", this.mts).areAllDefine(this.mts), false);
  }

  void testIsDefined(Tester t) {
    t.checkExpect(this.mts.isDefined("a"), false);
    t.checkExpect(new ConsLoString("a", this.mts).isDefined("a"), true);
  }
}