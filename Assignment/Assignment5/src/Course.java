import tester.Tester;

// IList
interface IList<T> {
  // accept a function to this IList
  <R> R accept(IListVisitor<T, R> ilv);

}

// empty list
class MtList<T> implements IList<T> {

  // accept a function to this MtList
  public <R> R accept(IListVisitor<T, R> ilv) {
    return ilv.visitMt(this);
  }

}

// cons list
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T f, IList<T> r) {
    this.first = f;
    this.rest = r;
  }

  // accept a function to this ConsList
  public <R> R accept(IListVisitor<T, R> ilv) {
    return ilv.visitCons(this);
  }

}

interface IFunc<A, R> {
  // asks a question about A
  R apply(A arg);
}

interface IPred<X> extends IFunc<X, Boolean> {
}

// IList visitor pattern
interface IListVisitor<A, R> extends IFunc<IList<A>, R> {
  // visit empty list
  R visitMt(MtList<A> mt);

  // visit cons list
  R visitCons(ConsList<A> cons);
}

// ormap function object
class OrMap<T> implements IListVisitor<T, Boolean> {
  IPred<T> pre;

  OrMap(IPred<T> pre) {
    this.pre = pre;
  }

  // apply this function to the IList
  public Boolean apply(IList<T> arg) {
    return arg.accept(this);
  }

  // empty case
  public Boolean visitMt(MtList<T> mt) {
    return false;
  }

  // cons case
  public Boolean visitCons(ConsList<T> cons) {
    return this.pre.apply(cons.first) || cons.rest.accept(this);
  }
}

// to represent Course
class Course {
  String name;
  IList<Course> prereqs;

  Course(String name, IList<Course> prereqs) {
    this.name = name;
    this.prereqs = prereqs;
  }

  // computes the length of the longest path from this course to a course with no
  // prerequisites
  int getDeepestPathLength() {
    return new DeepestPathLength().apply(this);
  }

  // determines if a course has a prereq (either an immediate one or a prereq of a
  // prereq) whose name is the same as a given string
  boolean hasPrereq(String given) {
    return new HasPrereq(given).apply(this);
  }
}

// count the deepest path length by given a course
class DeepestPathLength implements IFunc<Course, Integer> {
  // count the deepest path length by given a course
  public Integer apply(Course arg) {
    return new DeepestIList().apply(arg.prereqs);
  }
}

// count the deepest path length by given a list of course
class DeepestIList implements IListVisitor<Course, Integer> {

  // apply DeepestIList function to the given IList of Course
  public Integer apply(IList<Course> arg) {
    return arg.accept(this);
  }

  // empty case
  public Integer visitMt(MtList<Course> mt) {
    return 0;
  }

  // cons case
  public Integer visitCons(ConsList<Course> cons) {
    return Math.max(1 + new DeepestPathLength().apply(cons.first), cons.rest.accept(this));
  }
}

// determine if a course has the same name prereqs
class HasPrereq implements IPred<Course> {
  String givenName;

  HasPrereq(String g) {
    this.givenName = g;
  }

  // apply HasPrereq function to the given Course
  public Boolean apply(Course arg) {
    return arg.prereqs.accept(new OrMap<Course>(new HasPrereqHelp(this.givenName)));
  }
}

// determine if a course has the same name prereqs including itself
class HasPrereqHelp implements IPred<Course> {
  String givenName;

  HasPrereqHelp(String g) {
    this.givenName = g;
  }

  // apply HasPrereqHelp to the given Course
  public Boolean apply(Course arg) {
    return arg.name.equals(this.givenName) || (new HasPrereq(this.givenName).apply(arg));
  }
}

class ExamplesCourse {
  IList<Course> mt = new MtList<Course>();
  Course c2 = new Course("B", this.mt);
  Course c1 = new Course("A", new ConsList<Course>(this.c2, this.mt));

  Course c3 = new Course("C", new ConsList<Course>(this.c1, this.mt));

  Course c4 = new Course("D", new ConsList<Course>(this.c2, this.mt));
  Course c5 = new Course("E",
      new ConsList<Course>(this.c3, new ConsList<Course>(this.c4, this.mt)));

  boolean testDeepestPathLength(Tester t) {
    return t.checkExpect(this.c5.getDeepestPathLength(), 3);
  }
}
