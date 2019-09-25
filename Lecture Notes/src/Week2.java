import tester.Tester;

// a beverage is one of Coffee Tea or Milk
interface IBeverage {
	//compute the coupon price of this IBeverage
	double couponPrice(double coupon);
	//create a new IBeverage that is twice the size of this IBeverage
	IBeverage upgradeSize();
}

// a class to represent coffee
class Coffee implements IBeverage {
	String name;
	boolean isEspresso;
	double price;
	Origin origin;
	int size; //ounces

	Coffee(String name, boolean isEspresso, double price, Origin origin, int size) {
		this.name = name;
		this.isEspresso = isEspresso;
		this.price = price;
		this.origin = origin;
		this.size = size;
	}


	/* fields:
	 * this.name ... String
	 * this.isEspresso ... boolean
	 * this.price ... double
	 * this.origin ... Origin
	 * this.size ... int
	 * methods:
	 * this.couponPrice(double) ... double
	 * this.upgradeSize() ... IBeverage
	 */
	
	//compute the price of this Coffee after the coupon price
	public double couponPrice(double coupon) {
		return this.price - (this.price * coupon)/100;
	}
	
	//make a new Coffee that is double the size of this Coffee
	public IBeverage upgradeSize() {
		return new Coffee(this.name, this.isEspresso, this.price * 2, this.origin, this.size * 2);
	}
}

class Tea implements IBeverage {
	String name;
	String type;
	double price;
	int size; //ounces

	Tea(String name, String type, double price, int size) {
		this.name = name;
		this.type = type;
		this.price = price;
		this.size = size;
	}


	/* fields:
	 * this.name ... String
	 * this.type ... String
	 * this.price ... double
	 * this.size ... String
	 * methods:
	 * this.couponPrice(double) ... double
	 * this.upgradeSize() ... IBeverage
	 * 
	 */
	
	//compute the price of this Tea after the coupon
	public double couponPrice(double coupon) {
		return this.price - (this.price * coupon)/100;
	}

	//make a new Tea that is twice the size of this Tea
	public IBeverage upgradeSize() {
		return new Tea(this.name, this.type, this.price * 2, this.size * 2);
	}

}

class Milk implements IBeverage {
	int percent;
	boolean steamed;
	int size; //ounces

	Milk(int p, boolean s, int size) {
		this.percent = p;
		this.steamed = s;
		this.size = size;
	}


	/* fields:
	 * this.percent ... int
	 * this.steamed ... boolean
	 * this.size ... int
	 * methods:
	 * this.couponPrice(double) ... double
	 * this.upgradeSize() ... IBeverage
	 */

	//price of this Milk is 0 so no change after coupon
	public double couponPrice(double coupon) {
		return 0;
	}

	//make a new Milk that is twice the size of this one
	public IBeverage upgradeSize() {
		return new Milk(this.percent, this.steamed, this.size * 2);
	}
}

class Origin {
	String location;
	boolean isFairTrade;

	Origin(String l, boolean ift) {
		this.location = l;
		this.isFairTrade = ift;
	}

	/* fields:
	 * this.location ... String
	 * this.isFairTrade ... boolean
	 */
}

class Blend implements IBeverage {
	IBeverage first;
	IBeverage second;

	Blend(IBeverage f, IBeverage s) {
		this.first = f;
		this.second = s;
	}

	/* fields:
	 * this.first ... IBeverage
	 * this.second ... IBeverage
	 * methods:
	 * this.couponPrice(double) ... double
	 * this.upgradeSize() ... IBeverage
	 * methods for fields:
	 * this.first.couponPrice(double) ... double
	 * this.second.couponPrice(double) ... double
	 * this.first.upgradeSize() ... IBeverage
	 * this.second.upgradeSize() ... IBeverage
	 */
	
	//compute the price of this Blend after the coupon
	public double couponPrice(double coupon) {
		return this.first.couponPrice(coupon) + this.second.couponPrice(coupon);
	}

	//make a new Blend that is twice the size of this one
	public IBeverage upgradeSize() {
		return new Blend(this.first.upgradeSize(), this.second.upgradeSize());
	}
}


class Examples {
	Origin brazil = new Origin("Brazil", false);
	Coffee morningCoffee = new Coffee("morning coffee", false, 10.0, this.brazil, 12);
	IBeverage afternoonTea = new Tea("earl grey", "black", 5.0, 16);
	IBeverage chai = new Tea("chai", "masala", 4.0, 8);
	IBeverage chai2 = new Tea("chai", "masala", 8.0, 16);
	Coffee espresso = new Coffee("espresso", true, 3.0, this.brazil, 4);
	Coffee espresso2 = new Coffee("espresso", true, 6.0, this.brazil, 8);
	IBeverage latte = new Blend(this.espresso, new Milk(2, true, 4));	
	IBeverage doubleMilk = new Blend(new Milk(1, false, 4), new Milk(1, false, 4));
	IBeverage dirtyChai = new Blend(this.doubleMilk, new Blend(this.espresso, this.chai));

	//test for coupon price of coffee
	boolean testCoffee(Tester t) {
		return t.checkExpect(this.morningCoffee.couponPrice(20), 8.0) &&
				t.checkExpect(this.afternoonTea.couponPrice(50), 2.5) &&
				t.checkExpect(new Milk(2, true, 3).couponPrice(30), 0.0) &&
				t.checkExpect(this.latte.couponPrice(10), 2.7);
	}
	
	//test for upgrading size
	boolean testUpgrade(Tester t) {
		return t.checkExpect(this.espresso.upgradeSize(), this.espresso2) &&
				t.checkExpect(this.afternoonTea.upgradeSize(), new Tea("earl grey", "black", 10.0, 32)) &&
				t.checkExpect(new Milk(2, false, 2).upgradeSize(), new Milk(2, false, 4)) &&
				t.checkExpect(this.dirtyChai.upgradeSize(), 
						new Blend(new Blend(new Milk(1, false, 8), new Milk(1, false, 8)),
								new Blend(this.espresso2, this.chai2)));
	}
}





