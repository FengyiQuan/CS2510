interface IBook {
  // consumes the number that represents today in the library date-recording
  // system and produces the number of days this book is overdue
  int daysOverdue(int today);

  boolean isOverdue();

  // computes the fine for this book, if the book is returned on the given day
  int computeFine();
}

abstract class ABook implements IBook {
  String title;
  int dayTaken;

  ABook(String title, int dayTaken) {
    this.title = title;
    this.dayTaken = dayTaken;
  }

  public int daysOverdue(int today) {
    return this.dayTaken - today;
  }

  public boolean isOverdue() {
    return this.dayTaken > 14;
  }

  public int computeFine() {
    if (this.isOverdue()) {
      return (this.dayTaken - 14) * 10;
    }
    else {
      return 0;
    }
  }
}

class Book extends ABook {
  String author;

  Book(String title, String author, int dayTaken) {
    super(title, dayTaken);
    this.author = author;
  }
}

class RefBook extends ABook {
  RefBook(String title, int dayTaken) {
    super(title, dayTaken);
  }
}

class AudioBook extends ABook {
  String author;

  AudioBook(String title, String author, int dayTaken) {
    super(title, dayTaken);
    this.author = author;
  }

  public boolean isOverdue() {
    return this.dayTaken > 2;
  }

  public int computeFine() {
    if (this.isOverdue()) {
      return (this.dayTaken - 2) * 10;
    }
    else {
      return 0;
    }
  }
}