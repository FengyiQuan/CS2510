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
  
  public boolean hasSeats() { 
    return this.openSeats > 0;
  }
  
  public boolean startsAfter9() { 
    return this.startTime > 9;
  }
}

interface ILoCourse {
 // returns a list of the courses with open seats
 ILoCourse filterHasSeats(); 
 
}

class MtLoCourse implements ILoCourse { 
  
  public ILoCourse filterHasSeats() { 
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
  
  Course fundies21 = new Course(this.fundies2Title,this.razzaq,4,12);
  Course fundies22 = new Course(this.fundies2Title,this.razzaq,0,16);
  Course pl = new Course(this.plTitle,this.hemann,0,15);
  Course swDev = new Course(this.plTitle,this.hemann,4,18);
  Course lpTopics = new Course(this.lpTopicsTitle,this.hemann,10,9);
  Course design = new Course(this.designTitle,this.foobar,2,10);
  Course mlTopics = new Course(this.mlTopicsTitle,this.hemann,10,9);
  
  ILoCourse courseList = 
      new ConsLoCourse(this.fundies21,
          new ConsLoCourse(this.fundies22,
              new ConsLoCourse(this.pl,
                  new ConsLoCourse(this.swDev,
                      new ConsLoCourse(this.lpTopics,
                          new ConsLoCourse(this.design,
                              new ConsLoCourse(this.mlTopics,
                                  new MtLoCourse ()))))))); 
  
  ILoCourse filteredList = this.courseList.filterHasSeats();
}
