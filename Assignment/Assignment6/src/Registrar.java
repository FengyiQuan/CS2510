import tester.Tester;

// IList
interface IList<T> {
  // maps a function onto every member of the list
  <Y> IList<Y> map(IFunc<T, Y> fun);

  // determine is there any member of the list satisfying by the given predicate
  boolean ormap(IPred<T> pred);

  // determine every member of the list satisfying by the given predicate
  boolean andmap(IPred<T> pred);

  // combines the items in this IList according to the given function
  <Y> Y foldr(IFunc2<T, Y, Y> fun, Y base);
}

// MtList
class MtList<T> implements IList<T> {

  // maps a function onto every member of the list
  public <Y> IList<Y> map(IFunc<T, Y> fun) {
    return new MtList<Y>();
  }

  // determine is there any member of the list satisfying by the given predicate
  public boolean ormap(IPred<T> pred) {
    return false;
  }

  // determine every member of the list satisfying by the given predicate
  public boolean andmap(IPred<T> pred) {
    return true;
  }

  // combines the items in this IList according to the given function
  public <Y> Y foldr(IFunc2<T, Y, Y> fun, Y base) {
    return base;
  }
}

// ConsList
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T f, IList<T> r) {
    this.first = f;
    this.rest = r;
  }

  // maps a function onto every member of the list
  public <Y> IList<Y> map(IFunc<T, Y> fun) {
    return new ConsList<Y>(fun.apply(this.first), this.rest.map(fun));
  }

  // determine is there any member of the list satisfying by the given predicate
  public boolean ormap(IPred<T> pred) {
    return pred.apply(this.first) || this.rest.ormap(pred);
  }

  // determine every member of the list satisfying by the given predicate
  public boolean andmap(IPred<T> pred) {
    return pred.apply(this.first) && this.rest.andmap(pred);
  }

  // combines the items in this IList according to the given function
  public <Y> Y foldr(IFunc2<T, Y, Y> fun, Y base) {
    return fun.apply(this.first, this.rest.foldr(fun, base));
  }
}

// represents IFunc
interface IFunc<X, Y> {
  Y apply(X x);
}

// Effect: update students courses list by adding one course
class UpdateOneStu implements IFunc<Student, Void> {
  Course c;

  UpdateOneStu(Course c) {
    this.c = c;
  }

  // Effect: update students courses list by adding one course
  public Void apply(Student x) {
    x.courses = new ConsList<Course>(this.c, x.courses);
    return null;
  }
}

// to represent IPred
interface IPred<T> {
  boolean apply(T t);
}

// determine if all courses are overlap with the given courses
class Overlap implements IPred<IList<Course>> {
  IList<Course> other;

  Overlap(IList<Course> other) {
    this.other = other;
  }

  // determine if all courses are overlap with the given courses
  public boolean apply(IList<Course> t) {
    return t.ormap(new AnySameCourse(other));
  }

}

// determine if any course in a list is same as the given course
class AnySameCourse implements IPred<Course> {
  IList<Course> other;

  AnySameCourse(IList<Course> other) {
    this.other = other;
  }

  // determine if any course in a list is same as the given course
  public boolean apply(Course t) {
    return other.ormap(new SameCourse(t));
  }
}

// determine if two courses are the same
class SameCourse implements IPred<Course> {
  Course other;

  SameCourse(Course other) {
    this.other = other;
  }

  // determine if two courses are the same
  public boolean apply(Course t) {
    return t.sameCourse(other);
  }

}

// check if two list of students are the same (order doesn't matter)
class AllInOther implements IPred<Student> {
  IList<Student> stus;

  AllInOther(IList<Student> stus) {
    this.stus = stus;
  }

  // check if two list of students are the same (order doesn't matter)
  public boolean apply(Student t) {
    return this.stus.ormap(new AnySameStu(t));
  }

}

// determine if any student in a list is the same as the given student
class AnySameStu implements IPred<Student> {
  Student s;

  AnySameStu(Student s) {
    this.s = s;

  }

  // determine if any student in a list is the same as the given student
  public boolean apply(Student t) {
    return t.sameStudent(this.s);
  }
}

// to represents IFunc2
interface IFunc2<X, Y, Z> {
  Z apply(X x, Y y);
}

// counts the number of courses teaching by given professor in a list of courses
class CountInstructorCourse implements IFunc2<Course, Integer, Integer> {
  Instructor ins;

  CountInstructorCourse(Instructor ins) {
    this.ins = ins;
  }

  // counts the number of courses teaching by given professor in a list of courses
  public Integer apply(Course x, Integer y) {
    if (x.prof.sameProf(ins)) {
      return 1 + y;
    }
    else {
      return y;
    }
  }
}

// to represent a Course
class Course {
  String name;
  Instructor prof;
  IList<Student> students;

  Course(String n, Instructor p, IList<Student> s) {
    this.name = n;
    this.prof = p;
    this.prof.updateCourseByProf(this);
    this.students = s;
    this.students.map(new UpdateOneStu(this));
  }

  Course(String n, Instructor p) {
    this.name = n;
    this.prof = p;
    this.prof.updateCourseByProf(this);
    this.students = new MtList<Student>();
  }

  // Effect: update students list by adding one given student
  void addNewStu(Student s) {
    this.students = new ConsList<Student>(s, this.students);
  }

  // determine if two courses are the same
  boolean sameCourse(Course c) {
    return this.name.equals(c.name) && this.prof.sameProf(c.prof)
        && this.students.andmap(new AllInOther(c.students))
        && c.students.andmap(new AllInOther(this.students));
  }
}

// to represent Instructor
class Instructor {
  String name;
  IList<Course> courses;

  Instructor(String n) {
    this.name = n;
    this.courses = new MtList<Course>();
  }

  // Effect: update the courses to add one given course
  void updateCourseByProf(Course c) {
    this.courses = new ConsList<Course>(c, this.courses);
  }

  // determine if two Instructors are the same
  boolean sameProf(Instructor i) {
    return this.name.equals(i.name);
  }

  // determines whether the given Student is in more than one of this Instructor’s
  // Courses
  boolean dejavu(Student c) {
    return c.courses.foldr(new CountInstructorCourse(this), 0) > 1;
  }
}

// to represent a Student
class Student {
  String name;
  int id;
  IList<Course> courses;

  Student(String n, int id) {
    this.name = n;
    this.id = id;
    this.courses = new MtList<Course>();
  }

  // Effect: enrolls a Student in the given Course
  void enroll(Course c) {
    this.courses = new ConsList<Course>(c, this.courses);
    c.addNewStu(this);
  }

  // determines whether the given Student is in any of the same classes as this
  // Student
  boolean classmates(Student c) {
    return new Overlap(this.courses).apply(c.courses);
  }

  // determine whether two students are the same
  boolean sameStudent(Student c) {
    return this.name.equals(c.name) && this.id == c.id;
  }

}

class ExampleCourse {

  Instructor i1;
  Instructor i2;
  Student s1;
  Student s2;
  Student s3;
  Student s4;
  Student s5;
  Course c1;
  Course c2;
  Course c3;
  Course c4;

  void initdata() {

    this.i1 = new Instructor("ia");
    this.i2 = new Instructor("ib");

    this.s1 = new Student("a", 001);
    this.s2 = new Student("b", 002);
    this.s3 = new Student("c", 003);
    this.s4 = new Student("d", 004);
    this.s5 = new Student("e", 005);

    this.c1 = new Course("math", i1);
    this.c2 = new Course("biology", i1);
    this.c3 = new Course("java", i2);
    this.c4 = new Course("racket", i2);
  }

  void testEnroll(Tester t) {
    this.initdata();

    t.checkExpect(c1.prof.name, "ia");
    t.checkExpect(this.s5.courses, new ConsList<Course>(this.c4, new MtList<Course>()));
    this.s5.enroll(this.c1);
    t.checkExpect(this.s5.courses,
        new ConsList<Course>(c1, new ConsList<Course>(c4, new MtList<Course>())));
  }

  void testClassmates(Tester t) {
    this.initdata();
  }

  void testDejavu(Tester t) {
    this.initdata();
  }

}