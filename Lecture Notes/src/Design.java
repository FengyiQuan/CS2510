import tester.Tester;

interface IDocument {
	//list all of the sources from this IDocument
	ILoString sources();
}

class Book implements IDocument {
	String fName;
	String lName;
	String title;
	String publisher;
	ILoDocument bib;

	Book(String f, String l, String t, String p, ILoDocument bib) {
		this.fName = f;
		this.lName = l;
		this.title = t;
		this.publisher = p;
		this.bib = bib;
	}

	//list all of the sources from this Book
	public ILoString sources() {
		return new ConsLoString(this.lName + ", " + this.fName + ". " + "\"" + this.title + "\"" + ".", 
				this.bib.sources());
	}

}


class Wiki implements IDocument {
	String author;
	String title;
	String url;
	ILoDocument bib;

	Wiki(String a, String t, String u, ILoDocument bib) {
		this.author = a;
		this.title = t;
		this.url = u;
		this.bib = bib;
	}

	//list all of the sources from this Wiki
	public ILoString sources() {
		return this.bib.sources();
	}

}

interface ILoDocument {
	//list all of the sources from this ILoDocument
	ILoString sources();
}

class MtLoDocument implements ILoDocument {

	//list all of the sources from this MtLoDocument
	public ILoString sources() {
		return new MtLoString();
	}

}

class ConsLoDocument implements ILoDocument {
	IDocument first;
	ILoDocument rest;

	//constructor 
	ConsLoDocument(IDocument first, ILoDocument rest) {
		this.first = first;
		this.rest = rest;
	}

	//list all of the sources from this ConsLoDocument
	public ILoString sources() {
		return this.first.sources().append(this.rest.sources());
	}


}


interface ILoString {
	//append this ILoString to the given one
	ILoString append(ILoString that);
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
}

class MtLoString implements ILoString {

	//append this MtLoString to the given one
	public ILoString append(ILoString that) {
		return that;
	}

}


interface ILoNum {
	//check if this ILoNum satisfies the 3 requirements
	boolean satisfies();
	//helps to check if this ILoNum satisfies the 3 reqs
	//accumulators: keep track of whether each req has been satisfied so far
	boolean satisfiesAcc(boolean even, boolean posOdd, boolean bet5and10);
}

class MtLoNum implements ILoNum {

	//check if this MtLoNum satisfies the 3 requirements
	public boolean satisfies() {
		return false;
	}

	//helps to check if this MtLoNum satisfies the 3 reqs
	//accumulators: keep track of whether each req has been satisfied so far
	public boolean satisfiesAcc(boolean even, boolean posOdd, boolean bet5and10) {
		return even && posOdd && bet5and10;
	}

}

class ConsLoNum implements ILoNum {
	int first;
	ILoNum rest;

	//constructor
	ConsLoNum(int f, ILoNum r) {
		this.first = f;
		this.rest = r;
	}

	//check if this ConsLoNum satisfies the 3 requirements
	public boolean satisfies() {
		return this.satisfiesAcc(false, false, false);
	}

	//helps to check if this ConsLoNum satisfies the 3 reqs
	//accumulators: keep track of whether each req has been satisfied so far
	public boolean satisfiesAcc(boolean even, boolean posOdd, boolean bet5and10) {
		return (even && posOdd && bet5and10) ||
				this.rest.satisfiesAcc(even || this.first % 2 == 0, 
				posOdd || (this.first % 2 == 1 && this.first > 0), 
				bet5and10 || (this.first >= 5 && this.first <= 10));
	}

}


class Examples {
	//add Examples and tests
}