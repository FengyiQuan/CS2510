
// Represents a credit line account
public class Credit extends Account {

  int creditLine; // Maximum amount accessible
  double interest; // The interest rate charged

  public Credit(int accountNum, int balance, String name, int creditLine, double interest) {
    super(accountNum, balance, name);
    this.creditLine = creditLine;
    this.interest = interest;
  }

  int withdraw(int amount) {
    if (amount > this.balance) {
      throw new RuntimeException("Over credit limit");

    }
    else {
      this.balance = this.balance - amount;
      return this.balance;
    }
  }
}
