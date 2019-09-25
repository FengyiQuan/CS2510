// a program
class Program {
  ILoDef lod;
  ILoExp loe;

  Program(ILoDef lod, ILoExp loe) {
    this.lod = lod;
    this.loe = loe;
  }

  // indicating whether or not all of the functions being called in all of the
  // top-level expressions are defined
  boolean allFunctionsDefined() {
    return this.loe.getAllFunctionCalled().areAllDefine(this.lod.getAllFunctionName());
  }
}