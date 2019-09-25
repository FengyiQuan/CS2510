import tester.Tester;

// produces the sequence 0, 1, 1, 2, 3, 5, 8, 13...
// as a reminder:
// fib(0) = 0
// fib(1) = 1
// fib(n) = fib(n-1) + fib(n-2)
class Fib {
  int n_2;
  int n_1;
  int index;

  Fib(int n_2, int n_1, int in) {

    this.n_2 = n_2;
    this.n_1 = n_1;
    this.index = in;
  }

  // a new fibonacci sequence, starting from 0
  Fib() {
    this.n_2 = 0;
    this.n_1 = 1;
    this.index = 0;
  }

  // return the next element in the fibonacci sequence
  int getNext() {
    if (this.index == 0) {
      this.index = this.index + 1;
      return 0;
    }
    else if (this.index == 1) {
      this.index = this.index + 1;
      return 1;
    }
    else {
      this.index = this.index + 1;
      int answer = this.n_2 + this.n_1;
      this.n_2 = this.n_1;
      this.n_1 = answer;
      return answer;

    }
  }
}

class ExamplesFib {
  Fib fib;

  // sets fib to a new fibonacci sequence
  void initializeData() {
    this.fib = new Fib();
  }

  void testFib(Tester t) {
    this.initializeData();
    t.checkExpect(this.fib.getNext(), 0);
    t.checkExpect(this.fib.getNext(), 1);
    t.checkExpect(this.fib.getNext(), 1);
    t.checkExpect(this.fib.getNext(), 2);
    t.checkExpect(this.fib.getNext(), 3);
    t.checkExpect(this.fib.getNext(), 5);
    t.checkExpect(this.fib.getNext(), 8);
    t.checkExpect(this.fib.getNext(), 13);
    this.initializeData();
    t.checkExpect(this.fib.getNext(), 0);
    t.checkExpect(this.fib.getNext(), 1);
    t.checkExpect(this.fib.getNext(), 1);
    t.checkExpect(this.fib.getNext(), 2);
    t.checkExpect(this.fib.getNext(), 3);
    t.checkExpect(this.fib.getNext(), 5);
    t.checkExpect(this.fib.getNext(), 8);
    t.checkExpect(this.fib.getNext(), 13);
  }
}