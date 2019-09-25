
// Represents a savings account
public class Savings extends Account{

    double interest; // The interest rate

    public Savings(int accountNum, int balance, String name, double interest){
        super(accountNum, balance, name);
        this.interest = interest;
    }
    
    int withdraw(int amount) {
      if (amount > this.balance) {
        throw new RuntimeException("Over credit limit");

      }
      else {
        int currbalance = this.balance;
        this.balance = this.balance - amount;
        return currbalance - amount;
      }
    }
}
