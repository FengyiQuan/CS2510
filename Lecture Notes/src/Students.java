import tester.*; 

class CourseTitle {
  String dept;
  int courseNum;
  String name;

  CourseTitle (String dept, int courseNum, String name) {
    this.dept = dept;
    this.courseNum = courseNum;    
    this.name = name;
  }
  // Jason had failed to test ಠ_ಠ
  // and thusly, had a bug. (We fixed it)
  boolean isUpperDivision() { 
    return this.courseNum >= 2000;
  }
}

class Professor { 
  String first;
  String last;

  Professor (String first, String last) { 
    this.first = first;
    this.last = last;
  }

  public boolean sameProfessor(Professor that) { 
    return this.first.equals(that.first) 
        && this.last.equals(that.last);
  }
}

class Course {
  CourseTitle title; 
  Professor prof;
  int openSeats; 
  int startTime;
  // int section;

  Course(CourseTitle title, Professor prof, int openSeats, int startTime) {
    this.title = title;
    this.prof = prof;
    this.openSeats = openSeats; 
    this.startTime = startTime;
  }
  
  public boolean isUpperDivision() { 
    return this.title.isUpperDivision();
  }
  
  public boolean startsAfter9() { 
    return this.startTime > 9;
  }
  
  public boolean hasSeats() { 
    return this.openSeats > 0;
  }
  
}

interface ICoursePred { 
  boolean apply(Course c); 
}

class AndPred implements ICoursePred { 
  ICoursePred left;
  ICoursePred right; 
  
  AndPred(ICoursePred left,ICoursePred right){
    this.left = left;
    this.right = right;
  }
  
  public boolean apply (Course c) {
    return this.left.apply(c) && this.right.apply(c);
  }
  
}

class HasAsProfessor implements ICoursePred { 
  Professor prof;
  
  HasAsProfessor(Professor prof) { 
    this.prof = prof;
  }
  
  public boolean apply (Course c) {
    return c.prof.sameProfessor(this.prof);
  }
}

class IsUpperDivision implements ICoursePred {
  
  public boolean apply (Course c) { 
    return c.isUpperDivision();
  }
}

class StartsAfter9 implements ICoursePred { 
  
  public boolean apply (Course c) { 
    return c.startsAfter9();
  }
  
}

interface CompCourse { 
  boolean compare(Course c1, Course c2);  
}

class StartsEarliest implements CompCourse { 
  
   public boolean compare(Course c1, Course c2) {
     return c1.startTime < c2.startTime;
   }
}

interface ILoCourse {
 // public ILoCourse filter 
  
 // returns a list of the courses with open seats
 ILoCourse filterHasSeats(); 
 
 ILoCourse filter(ICoursePred pred);
 // comparator abbrv. comp
 ILoCourse sort(CompCourse comp);
 // helper
 // acc: sorted list 
 ILoCourse sortHelp(CompCourse comp, ILoCourse acc);
 
 ILoCourse insert(CompCourse comp, Course insertElem);
 
 // e.g. mySortedList.insert(the,things);
 
}

class MtLoCourse implements ILoCourse { 
 
  public ILoCourse insert(CompCourse comp, Course elem) {
    return new ConsLoCourse(elem,this);
  }
  
  public ILoCourse sort(CompCourse comp) { 
    return this;
  }
  // BTW acc is a sorted list
  public ILoCourse sortHelp(CompCourse comp, ILoCourse acc) {
    return acc;
  }
  
  public ILoCourse filterHasSeats() { 
    return this;
  }
  
  public ILoCourse filter(ICoursePred pred) { 
    return this;
  }
 
}

class ConsLoCourse implements ILoCourse { 
  Course course;
  ILoCourse rest;

  
  ConsLoCourse (Course course, ILoCourse rest) { 
    this.course = course;
    this.rest = rest;
  }
  
  public ILoCourse insert(CompCourse comp, Course elem) { 
    /* course here
     * rest
     * elem
     * comparator
     */
    // true means >, false means <
    if (comp.compare(elem,this.course)) {
      return new ConsLoCourse(elem, this);
    }
    else {
      return new ConsLoCourse(this.course, this.rest.insert(comp, elem));
    }
  }
  // acc is a sorted list 
  public ILoCourse sortHelp(CompCourse comp, ILoCourse acc) { 
    /* course 
     * rest 
     * comp
     * acc 
     */
    return this.rest.sortHelp(comp, acc.insert(comp, this.course));
  }
  
  public ILoCourse sort(CompCourse comp) { 
   return this.sortHelp(comp, new MtLoCourse());
  }
  
  
  
  public ILoCourse filter(ICoursePred pred) { 
    if (pred.apply(this.course)) {
      return new ConsLoCourse(this.course, this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
      
  }
  
  
  public ILoCourse filterHasSeats() { 
    if (course.hasSeats()) {
      return new ConsLoCourse(this.course, this.rest.filterHasSeats());
    }
    else { 
      return this.rest.filterHasSeats();
    }
  }
}


class ExamplesCourses { 
  Professor hemann = new Professor("Jason", "Hemann");
  Professor razzaq = new Professor("Leena", "Razzaq");
  Professor foobar = new Professor("Foo", "Bar");
  
  CourseTitle fundies2Title = new CourseTitle("CCIS", 2510, "Fundies II");
  CourseTitle plTitle = new CourseTitle("CCIS", 5500, "Programming Languages");
  CourseTitle swDevTitle = new CourseTitle("CCIS", 4500, "Software Dev");
  CourseTitle designTitle = new CourseTitle("CAMD", 1000, "Graphical Design");
  CourseTitle lpTopicsTitle = new CourseTitle("CCIS", 8888, "Special Topics in Logic Programming");
  CourseTitle mlTopicsTitle = new CourseTitle("CCIS", 8888, "Special Topics in Machine Learning");
  
  
  
  Course fundies21 = new Course(this.fundies2Title,this.razzaq,4,21);
  Course fundies22 = new Course(this.fundies2Title,this.razzaq,0,16);
  Course pl = new Course(this.plTitle,this.hemann,0,15);
  Course swDev = new Course(this.plTitle,this.hemann,4,18);
  Course lpTopics = new Course(this.lpTopicsTitle,this.hemann,10,9);
  Course design = new Course(this.designTitle,this.foobar,2,10);
  Course mlTopics = new Course(this.mlTopicsTitle,this.hemann,10,9);
  
  ILoCourse openCourseList = 
      new ConsLoCourse(this.fundies21, 
          new ConsLoCourse(this.swDev, 
              new ConsLoCourse(this.lpTopics, 
                  new ConsLoCourse(this.design, 
                      new ConsLoCourse(this.mlTopics, 
                          new MtLoCourse ()))))); 
                      
  
  ILoCourse courseList = 
      new ConsLoCourse(this.fundies21,
          new ConsLoCourse(this.fundies22,
              new ConsLoCourse(this.pl,
                  new ConsLoCourse(this.swDev,
                      new ConsLoCourse(this.lpTopics,
                          new ConsLoCourse(this.design,
                              new ConsLoCourse(this.mlTopics,
                                  new MtLoCourse ()))))))); 
  
  ILoCourse maybeNextSem = this.courseList.filter(new AndPred(new IsUpperDivision(), new HasAsProfessor(this.hemann)));
 
  
  ILoCourse filteredList = this.courseList.filterHasSeats();
  
  boolean testFilterHasSeats (Tester t) { 
    return t.checkExpect(this.courseList.filterHasSeats(), this.openCourseList); 
  }
  
  ICoursePred startsAfter9 = new StartsAfter9();
  
  ILoCourse newFilteredList = this.courseList.filter(new IsUpperDivision());
  ILoCourse extraFilteredList = this.newFilteredList.filter(this.startsAfter9);
  ILoCourse superFilteredList = this.extraFilteredList.filter(new HasAsProfessor(this.razzaq));
  
  CompCourse comparator = new StartsEarliest();
  ILoCourse superDuperFilteredList = superFilteredList.sort(comparator); 
  
  
  
  
  
}
