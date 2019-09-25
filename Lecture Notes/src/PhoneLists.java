import tester.Tester;

class Person {
	String name;
	int phone;
	Person(String name, int phone) {
		this.name = name;
		this.phone = phone;
	}
	// Returns true when the given person has the same name and phone number as this person
	boolean samePerson(Person that) {
		return this.name.equals(that.name) && this.phone == that.phone;
	}

	// Returns true when this person has the same name as the given
	boolean sameName(String that) {
		return this.name.equals(that);
	}
	
	//Effect: this person's number is changed to the given one
	void changeNum(int newNum) {
		this.phone = newNum;
	}

}

interface ILoPerson {
	// Returns true if this list contains a person with the given name
	boolean contains(String name);
	int findNum(String name);
	//Effect: changes the number of the person with the given name
	void changePhone(String name, int newNum);
	
}

class MtLoPerson implements ILoPerson {
	// Returns true if this empty list contains a person with the given name
	public boolean contains(String name) { return false; }

	//the person with the given name is not in the empty list
	public int findNum(String name) {
		return -1;
	}

	//Effect: changes the number of the person with the given name 
	public void changePhone(String name, int newNum) {
		return ;
	}

}

class ConsLoPerson implements ILoPerson {
	Person first;
	ILoPerson rest;

	ConsLoPerson(Person first, ILoPerson rest) {
		this.first = first;
		this.rest = rest;
	}

	// Returns true if this non-empty list contains a person with the given name
	public boolean contains(String name) {
		return this.first.sameName(name) || this.rest.contains(name);
	}

	//find the number of the person with the given name
	public int findNum(String name) {
		if (this.first.sameName(name)) {
			return this.first.phone;
		}
		else {
			return this.rest.findNum(name);
		}
	}

	//Effect: changes the number of the person with the given name
	public void changePhone(String name, int newNum) {
		if (this.first.sameName(name)) {
			this.first.changeNum(newNum);
		}
		else {
			this.rest.changePhone(name, newNum);
		}
	}

}

interface IFunc<T, R> {
	//apply an operation to the given item
	R apply(T t);
}

class ChangePhone implements IFunc<Person, Void> {
	int num;
	
	ChangePhone(int num) {
		this.num = num;
	}
	
	//Effect: changes the given person's number to this.num
	public Void apply(Person t) {
		t.changeNum(this.num);
		return null;
	}
}

interface IPred<T> {
	//ask a question about the given item
	boolean apply(T t);
}

class PersonByName implements IPred<Person> {
	String name;
	
	PersonByName(String name) {
		this.name = name;
	}
	
	//does the given person have the same name as this.name?
	public boolean apply(Person t) {
		return t.name.equals(this.name);
	}
	
}

interface IList<T> {
	T find(IPred<T> whichOne);
	//Effect: changes the item that matches the predicate in the list according to the given function
	Void changeItem(IPred<T> pred, IFunc<T, Void> fun);
	
}

class MtList<T> implements IList<T> {

	//there are no more items to check so have not found it.
	public T find(IPred<T> whichOne) {
		return null;
	}

	//haven't found the item to change so do nothing
	public Void changeItem(IPred<T> pred, IFunc<T, Void> fun) {
		return null;
	}	
}

class ConsList<T> implements IList<T> {
	T first;
	IList<T> rest;
	
	ConsList(T first, IList<T> rest) {
		this.first = first;
		this.rest = rest;
	}

	//find the first item in this ConsList that passes the predicate
	public T find(IPred<T> whichOne) {
		if (whichOne.apply(this.first)) {
			return this.first;
		}
		else {
			return this.rest.find(whichOne);
		}
	}

	//Effect: the item in this list that passes the given predicate is changed using the given function
	public Void changeItem(IPred<T> pred, IFunc<T, Void> fun) {
		if (pred.apply(this.first)) {
			return fun.apply(this.first);
		}
		else {
			return this.rest.changeItem(pred, fun);
		}
	}
	
}


class Examples {
	Person anne = new Person("Anne", 1234);
	Person bob = new Person("Bob", 3456);
	Person clyde = new Person("Clyde", 6789);
	Person dana = new Person("Dana", 1357);
	Person eric = new Person("Eric", 12469);
	Person frank = new Person("Frank", 7294);
	Person gail = new Person("Gail", 9345);
	Person henry = new Person("Henry", 8602);
	Person irene = new Person("Irene", 91302);
	Person jenny = new Person("Jenny", 8675309);

	ILoPerson friends, family, work;
	IList<Person> friends2, family2, work2;

	void initData() {
		this.anne = new Person("Anne", 1234);
		this.bob = new Person("Bob", 3456);
		this.clyde = new Person("Clyde", 6789);
		this.dana = new Person("Dana", 1357);
		this.eric = new Person("Eric", 12469);
		this.frank = new Person("Frank", 7294);
		this.gail = new Person("Gail", 9345);
		this.henry = new Person("Henry", 8602);
		this.irene = new Person("Irene", 91302);
		this.jenny = new Person("Jenny", 8675309);
		this.friends =
				new ConsLoPerson(this.anne, new ConsLoPerson(this.clyde,
						new ConsLoPerson(this.gail, new ConsLoPerson(this.frank,
								new ConsLoPerson(this.jenny, new MtLoPerson())))));
		this.family =
				new ConsLoPerson(this.anne, new ConsLoPerson(this.dana,
						new ConsLoPerson(this.frank, new MtLoPerson())));
		this.work =
				new ConsLoPerson(this.bob, new ConsLoPerson(this.clyde,
						new ConsLoPerson(this.dana, new ConsLoPerson(this.eric,
								new ConsLoPerson(this.henry, new ConsLoPerson(this.irene,
										new MtLoPerson()))))));
	}
	
	void initGenData() {
		this.anne = new Person("Anne", 1234);
		this.bob = new Person("Bob", 3456);
		this.clyde = new Person("Clyde", 6789);
		this.dana = new Person("Dana", 1357);
		this.eric = new Person("Eric", 12469);
		this.frank = new Person("Frank", 7294);
		this.gail = new Person("Gail", 9345);
		this.henry = new Person("Henry", 8602);
		this.irene = new Person("Irene", 91302);
		this.jenny = new Person("Jenny", 8675309);
		this.friends2 =
				new ConsList<Person>(this.anne, new ConsList<Person>(this.clyde,
						new ConsList<Person>(this.gail, new ConsList<Person>(this.frank,
								new ConsList<Person>(this.jenny, new MtList<Person>())))));
		this.family2 =
				new ConsList<Person>(this.anne, new ConsList<Person>(this.dana,
						new ConsList<Person>(this.frank, new MtList<Person>())));
		this.work2 =
				new ConsList<Person>(this.bob, new ConsList<Person>(this.clyde,
						new ConsList<Person>(this.dana, new ConsList<Person>(this.eric,
								new ConsList<Person>(this.henry, new ConsList<Person>(this.irene,
										new MtList<Person>()))))));
	}
	
	void testPhoneLists(Tester t) {
		this.initData();
		t.checkExpect(this.friends.findNum("Clyde"), 6789);
		this.friends.changePhone("Clyde", 7890);
		t.checkExpect(this.friends.findNum("Clyde"), 7890);
		t.checkExpect(this.work.findNum("Clyde"), 7890);
	}
	
	void testGenericPhoneLists(Tester t) {
		this.initGenData();
		t.checkExpect(this.work2.find(new PersonByName("Henry")), this.henry);
		t.checkExpect(this.friends2.find(new PersonByName("Henry")), null);
		t.checkExpect(this.friends2.find(new PersonByName("Clyde")).phone, 6789);
		this.friends2.changeItem(new PersonByName("Clyde"), new ChangePhone(7777));
		t.checkExpect(this.friends2.find(new PersonByName("Clyde")).phone, 7777);
	}
}








class Counter {
	int val;
	Counter() {
		this(0);
	}
	Counter(int initialVal) {
		this.val = initialVal;
	}
	int get() {
		int ans = this.val;
		this.val = this.val + 1;
		return ans;
	}
}
class ExamplesC {
	void testCounter(Tester t) {
		Counter c1 = new Counter();
		Counter c2 = new Counter(5);
		Counter c3 = c1;
		// What should these tests be?
		t.checkExpect(c1.get(), 0);             // Test 1
		t.checkExpect(c2.get(), 5);             // Test 2
		t.checkExpect(c3.get(), 1);             // Test 3
		t.checkExpect(c1.get() == c3.get(), false); // Test 4
		t.checkExpect(c1.get() == c1.get(), false); // Test 5
		t.checkExpect(c2.get() == c1.get(), true); // Test 6
		t.checkExpect(c2.get() == c1.get(), true); // Test 7
		t.checkExpect(c1.get() == c1.get(), false); // Test 8
		t.checkExpect(c2.get() == c1.get(), false); // Test 9
	}
}






