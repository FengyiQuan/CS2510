/*
(define-struct book[pages title author])
(define-struct pamphlet[title author])
A Text is one of:
-(make-book Number String String)
-(make-pamphlet String String)
*/

interface IText {}

//a book is an IText
class Book implements IText {
	int pages;
	String title;
	String author;
	/*
	double numwithdecimals;
	boolean truefalse;
	long reallybignumber;
	char oneletter;
	Integer pagess;
	Double doubles; //etc
	*/
	//constructor vvv
	Book(int pages, String title, String author) {
		this.pages = pages;
		this.title = title;
		this.author = author;
	}
}

//a pamphlet is an IText
class Pamphlet implements IText {
	String title;
	String author;
	Pamphlet(String title, String author) {
		this.title = title;
		this.author = author;
	}
}

class ExamplesBooks {
	IText book1 = new Book(420, "book1", "ozzy osbourne");
	IText pamphlet1 = new Pamphlet("pamphlet1", "Jason");
}














