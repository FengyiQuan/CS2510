import tester.Tester;


interface IAT {
	//count the ancestors in this IAT
	int countAnc();
	//count all of the persons in this tree
	int countAncHelp();
	//is this IAT well-formed?
	boolean wellFormed();
	//is this IAT is born before the given year
	boolean bornBefore(int year);
	//list the names in the tree
	ILoString names();
	//helps to list the names in this tree
	//accumulator: keeps track of names on the dad's side of the tree
	ILoString namesAcc(ILoString acc);
	//list the names in this IAT, but uses append rather than an accumulator
	ILoString names2();
	//check if any ancestor in this tree has the same name as a descendant
	boolean hasJunior();
	//helps check if this IAT has an ancestor with the same name as a descendant
	//accumulator: keeps track of the names of people seen so far in this tree
	boolean hasJuniorAcc(ILoString acc);
}

class Unknown implements IAT {

	//count the ancestors in this Unknown
	public int countAnc() {
		return 0;
	}

	//count all of the persons in this tree
	public int countAncHelp() {
		return 0;
	}

	//is this Unknown well-formed?
	public boolean wellFormed() {
		return true;
	}

	//is this Unknown born before the given year?
	public boolean bornBefore(int year) {
		return true;
	}

	//list the names in this Unknown
	public ILoString names() {
		return new MtLoString();
	}

	//helps to list the names in this Unknown
	//accumulator: keeps track of the names encountered on the dad's side
	public ILoString namesAcc(ILoString acc) {
		return acc;
	}

	//list the names in this Unknown, but uses append rather than an accumulator
	public ILoString names2() {
		return new MtLoString();
	}

	//check if any ancestor in this Unknown has the same name as a descendant
	public boolean hasJunior() {
		return false;
	}

	//helps check if this Unknown has an ancestor with the same name as a descendant
	//accumulator: keeps track of the names of people seen so far in this tree
	public boolean hasJuniorAcc(ILoString acc) {
		return false;
	}

}

class Person implements IAT {
	String name;
	int yob;
	boolean isMale;
	IAT mom;
	IAT dad;
	Person(String name, int yob, boolean isMale, IAT mom, IAT dad) {
		this.name = name;
		this.yob = yob;
		this.isMale = isMale;
		this.mom = mom;
		this.dad = dad;
	}


	//count all of the ancestors in this Person tree
	public int countAnc() {
		return this.mom.countAncHelp() + this.dad.countAncHelp();
	}

	//count all of the Persons in this Person tree
	public int countAncHelp() {
		return 1 + this.mom.countAncHelp() + this.dad.countAncHelp();
	}

	//is this Person tree well-formed?
	public boolean wellFormed() {
		return this.mom.bornBefore(this.yob) &&
				this.dad.bornBefore(this.yob) &&
				this.mom.wellFormed() &&
				this.dad.wellFormed();
	}

	//is this Person born before the given year?
	public boolean bornBefore(int year) {
		return this.yob < year;
	}

	//list the names in this Person tree
	public ILoString names() {
		return this.namesAcc(new MtLoString());
	}

	//helps to list the names in this person tree
	//accumulator: keeps track of the names on the dad's side
	public ILoString namesAcc(ILoString acc) {
		return new ConsLoString(this.name, this.mom.namesAcc(this.dad.namesAcc(acc)));
	}

	//list the names in this Person, but uses append rather than an accumulator
	public ILoString names2() {
		return new ConsLoString(this.name, this.mom.names().append(this.dad.names()));
	}

	//check if any ancestor in this Person has the same name as a descendant
	public boolean hasJunior() {
		return this.hasJuniorAcc(new MtLoString());
	}

	//helps check if this IAT has an ancestor with the same name as a descendant
	//accumulator: keeps track of the names of people seen so far in this tree
	public boolean hasJuniorAcc(ILoString acc) {
		if (acc.contains(this.name)) {
			return true;
		}
		else {
			return this.mom.hasJuniorAcc(new ConsLoString(this.name, acc)) ||
					this.dad.hasJuniorAcc(new ConsLoString(this.name, acc));
		}
	}
}

interface ILoString {
	//append this ILoString to the given one
	ILoString append(ILoString that);
	//reverse the order of the Strings in this ILoString
	ILoString reverse();
	//helps to reverse this ILoString
	//accumulator: keeps track of the reversed list so far
	ILoString reverseAcc(ILoString acc);
	//does this list contain the given?
	boolean contains(String s);
	//get the longest String in this ILoString
	String longest();
	//helps find the longest string in this ILoString
	//accumulator: keeps track of the longest string seen so far
	String longestAcc(String acc);
}

class ConsLoString implements ILoString {
	String first;
	ILoString rest;

	ConsLoString(String first, ILoString rest) {
		this.first = first;
		this.rest = rest;
	}

	//append this ConsLoString to the given one
	public ILoString append(ILoString that) {
		return new ConsLoString(this.first, this.rest.append(that));
	}

	//reverse the order of the Strings in this ConsLoString
	public ILoString reverse() {
		//return this.rest.reverse().append(new ConsLoString(this.first, new MtLoString()));
		return this.reverseAcc(new MtLoString());
	}

	//helps to reverse this ConsLoString
	//accumulator: keeps track of the reversed list so far
	public ILoString reverseAcc(ILoString acc) {
		return this.rest.reverseAcc(new ConsLoString(this.first, acc));
	}

	//does this ConsLoString contain the given String?
	public boolean contains(String s) {
		return this.first.equals(s) || this.rest.contains(s);
	}

	//find the longest String in this ConsLoString
	public String longest() {
		return this.rest.longestAcc(this.first);
	}

	//helps find the longest string in this ConsLoString
	//accumulator: keeps track of the longest string seen so far
	public String longestAcc(String acc) {
		if (this.first.length() > acc.length()) {
			return this.rest.longestAcc(this.first);
		}
		else {
			return this.rest.longestAcc(acc);
		}
	}

}

class MtLoString implements ILoString {

	//append this MtLoString to the given one
	public ILoString append(ILoString that) {
		return that;
	}

	//reverse the order of the Strings in this MtLoString
	public ILoString reverse() {
		return this;
	}

	//helps to reverse this MtLoString
	//accumulator: keeps track of the reversed list so far
	public ILoString reverseAcc(ILoString acc) {
		return acc;
	}

	//does this MtLoString contain the given String?
	public boolean contains(String s) {
		return false;
	}

	//finds the longest string in this MtLoString
	public String longest() {
		throw new RuntimeException("empty list can't have a longest word");
	}

	//helps find the longest string in this MtLoString
	//accumulator: keeps track of the longest string seen so far
	public String longestAcc(String acc) {
		return acc;
	}

}

class Examples {
	IAT enid = new Person("Enid", 1904, false, new Unknown(), new Unknown());
	IAT edward = new Person("Edward", 1902, true, new Unknown(), new Unknown());
	IAT emma = new Person("Emma", 1906, false, new Unknown(), new Unknown());
	IAT eustace = new Person("Eustace", 1907, true, new Unknown(), new Unknown());

	IAT david = new Person("David", 1925, true, new Unknown(), this.edward);
	IAT daisy = new Person("Daisy", 1927, false, new Unknown(), new Unknown());
	IAT dana = new Person("Dana", 1933, false, new Unknown(), new Unknown());
	IAT darcy = new Person("Darcy", 1930, false, this.emma, this.eustace);
	IAT darren = new Person("Darren", 1935, true, this.enid, new Unknown());
	IAT dixon = new Person("Dixon", 1936, true, new Unknown(), new Unknown());

	IAT clyde = new Person("Clyde", 1955, true, this.daisy, this.david);
	IAT candace = new Person("Candace", 1960, false, this.dana, this.darren);
	IAT cameron = new Person("Cameron", 1959, true, new Unknown(), this.dixon);
	IAT claire = new Person("Claire", 1956, false, this.darcy, new Unknown());

	IAT bill = new Person("Bill", 1980, true, this.candace, this.clyde);
	IAT bree = new Person("Bree", 1981, false, this.claire, this.cameron);

	IAT andrew = new Person("Andrew", 2001, true, this.bree, this.bill);
	IAT byron = new Person("Byron", 1980, true, this.candace, this.andrew);

	ILoString mt = new MtLoString();
	ILoString alos1 = new ConsLoString("Edward", 
			new ConsLoString("Anne", new MtLoString()));
	ILoString alos2 = new ConsLoString("Byron", new MtLoString());

	boolean testIAT(Tester t) {
		return t.checkExpect(this.clyde.countAnc(), 3) &&
				t.checkExpect(new Unknown().countAnc(), 0) &&
				t.checkExpect(new Unknown().wellFormed(), true) &&
				t.checkExpect(this.andrew.wellFormed(), true) &&
				t.checkExpect(this.byron.wellFormed(), false) &&
				t.checkExpect(this.andrew.bornBefore(2000), false) &&
				t.checkExpect(this.andrew.bornBefore(2005), true) &&
				t.checkExpect(new Unknown().bornBefore(2000), true) &&
				t.checkExpect(this.darcy.names(), new ConsLoString("Darcy", new ConsLoString("Emma",
						new ConsLoString("Eustace", new MtLoString())))) &&
				t.checkExpect(this.darcy.names2(), new ConsLoString("Darcy", new ConsLoString("Emma",
						new ConsLoString("Eustace", new MtLoString())))) &&
				t.checkExpect(this.alos1.append(this.alos2),  new ConsLoString("Edward", 
						new ConsLoString("Anne", alos2 ))) &&
				t.checkExpect(this.emma.namesAcc(new ConsLoString("Eustace", new MtLoString())), 
						new ConsLoString("Emma",
						new ConsLoString("Eustace", new MtLoString())))
				// need more tests! some test cases are missing
				;
	}
	
	//test accumulator methods
	boolean testAcc(Tester t) {
		return t.checkExpect(this.alos1.reverse(), new ConsLoString("Anne", 
			new ConsLoString("Edward", new MtLoString()))) &&
				t.checkExpect(this.andrew.hasJunior(), false);
		//need more tests, including tests for all of the helpers
	}

}