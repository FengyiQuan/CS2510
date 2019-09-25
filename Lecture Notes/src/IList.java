import tester.Tester;

interface IPred<T> {
	//asks a question about T
	boolean apply(T t);
}

interface IFunc<X, Y> {
	//applies an operation to the given 
	Y apply(X x);
}


class PaintingToTitle implements IFunc<Painting, String> {

	//returns the title of the given Painting
	public String apply(Painting x) {
		return x.title;
	}	
}

class StringLength implements IFunc<String, Integer> {

	//computes the length of the given string
	public Integer apply(String x) {
		return x.length();
	}	
}

interface IFunc2<X, Y, Z> {
	//applies an operation to the given x and y
	Z apply(X x, Y y);
}

class SumLengths implements IFunc2<String, Integer, Integer> {

	//adds the length of the string x to the integer y
	public Integer apply(String x, Integer y) {
		return x.length() + y;
	}
}



////////////////////////////////////////////////////////////////////////
interface IShapeVisitor<R> extends IFunc<IShape, R>{
	//applies an operation to the given Circle
	R visit(Circle c);
	//applies an operation to the given Rect
	R visit(Rect r);
	//applies an operation to the given Combo
	R visit(Combo c);
}

class ShapeArea implements IShapeVisitor<Double> {

	//computes the area of the given Circle
	public Double visit(Circle c) {
		return c.radius * c.radius * Math.PI;
	}

	//computes the area of the given Rect
	public Double visit(Rect r) {
		return r.height * r.width * 1.0;
	}
	
	//computes the area of the given Combo
	public Double visit(Combo c) {
		return c.top.accept(this) + c.bot.accept(this);
	}


	//the given IShape accepts this IShapeVisitor
	public Double apply(IShape x) {
		return x.accept(this);
	}
}

class Grow implements IShapeVisitor<IShape> {
	int increment;
	
	Grow(int inc) {
		this.increment = inc;
	}
	
	//the given IShape accepts this IShapeVisitor
	public IShape apply(IShape x) {
		return x.accept(this);
	}

	//grows the given Circle by this.increment
	public IShape visit(Circle c) {
		return new Circle(c.center, c.radius + this.increment, c.color);
	}

	//grows the given Rect by this.increment
	public IShape visit(Rect r) {
		return new Rect(r.nw, r.width + this.increment, r.height + this.increment, r.color);
	}

	//grows the given Combo by this.increment
	public IShape visit(Combo c) {
		return new Combo(c.top.accept(this), c.bot.accept(this));
	}
}

interface IList<T> {
	//filters this IList by the given predicate
	IList<T> filter(IPred<T> pred); 
	//maps a function onto every member of the list
	<Y> IList<Y> map(IFunc<T, Y> fun);
	//combine the items in this IList according to the given function
	<Y> Y foldr(IFunc2<T, Y, Y> fun, Y base);
}

class MtList<T> implements IList<T> {

	//filters this MtList by the given predicate
	public IList<T> filter(IPred<T> pred) {
		return this;
	}

	//maps the given function onto this empty list
	public <Y> IList<Y> map(IFunc<T, Y> fun) {
		return new MtList<Y>();
	}

	//combines the items in this MtList
	public <Y> Y foldr(IFunc2<T, Y, Y> fun, Y base) {
		return base;
	}

}

class ConsList<T> implements IList<T> {
	T first;
	IList<T> rest;

	ConsList(T first, IList<T> rest) {
		this.first = first;
		this.rest = rest;
	}

	//filters this ConsList by the given predicate
	public IList<T> filter(IPred<T> pred) {
		if (pred.apply(this.first)) {
			return new ConsList<T>(this.first, this.rest.filter(pred));
		}
		else {
			return this.rest.filter(pred);
		}
	}

	//maps the given function onto this ConsList
	public <Y> IList<Y> map(IFunc<T, Y> fun) {
		return new ConsList<Y>(fun.apply(this.first), this.rest.map(fun));
	}

	//combines the items in this ConsList according to the given function
	public <Y> Y foldr(IFunc2<T, Y, Y> fun, Y base) {
		return fun.apply(this.first, this.rest.foldr(fun, base));
		//return this.rest.foldr(fun, fun.apply(this.first, base)); this is foldl
	}

}

class Examples {
	Artist daVinci = new Artist("Da Vinci", 1452);
	Artist daVinci2 = new Artist("Da Vinci", 1452);
	Artist monet = new Artist("Monet", 1840);
	Painting mona = new Painting(this.daVinci, "Mona Lisa", 10, 1503);

	IList<Painting> listMt = new MtList<Painting>();
	IList<Painting> listOne = new ConsList<Painting>(this.mona, this.listMt);

	IList<String> listTwo = new ConsList<String>("a", new MtList<String>());
	IList<String> strings = new ConsList<String>("bb", this.listTwo);

	IList<Integer> ints = new ConsList<Integer>(1, new MtList<Integer>());
	
	IList<IShape> shapes = new ConsList<IShape>(new Circle(new CartPt(3,4), 5, "blue"), new MtList<IShape>());
	IList<IShape> shapes2 = new ConsList<IShape>(new Rect(new CartPt(6,8), 2, 4, "red"), this.shapes);
	
	boolean testMap(Tester t) {
		return t.checkExpect(this.listOne.map(new PaintingToTitle()), 
				new ConsList<String>("Mona Lisa", new MtList<String>())) &&
				t.checkExpect(this.listTwo.map(new StringLength()), this.ints) &&
				t.checkExpect(this.shapes.map(new ShapeArea()), 
						new ConsList<Double>(25*Math.PI, new MtList<Double>())) &&
				t.checkExpect(new MtList<IShape>().map(new ShapeArea()), new MtList<Double>()) &&
				t.checkExpect(this.shapes2.map(new Grow(2)), 
						new ConsList<IShape>(new Rect(new CartPt(6,8), 4, 6, "red"),
								new ConsList<IShape>(new Circle(new CartPt(3,4), 7, "blue"), 
										new MtList<IShape>())));
	}
	
	boolean testFold(Tester t) {
		return t.checkExpect(this.strings.foldr(new SumLengths(), 0), 3) &&
				t.checkExpect(new MtList<String>().foldr(new SumLengths(), 0), 0) &&
				t.checkExpect(new SumLengths().apply("aa", 3), 5);
	}
	//add tests for all other methods
}



//to represent a geometric shape
interface IShape {
	//accepts a visitor to this IShape
	<R> R accept(IShapeVisitor<R> isd);
}

//to represent a circle
class Circle implements IShape {
	CartPt center;
	int radius;
	String color;

	Circle(CartPt center, int radius, String color) {
		this.center = center;
		this.radius = radius;
		this.color = color;
	}

	//accepts a visitor to this Circle
	public <R> R accept(IShapeVisitor<R> isd) {
		return isd.visit(this);
	}
}


//to represent a rectangle
class Rect implements IShape {
	CartPt nw;
	int width;
	int height;
	String color;

	Rect(CartPt nw, int width, int height, String color) {
		this.nw = nw;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	//accepts a visitor to this Rect
	public <R> R accept(IShapeVisitor<R> isd) {
		return isd.visit(this);
	}
}

class Combo implements IShape {
	IShape top;
	IShape bot;
	
	Combo(IShape top, IShape bot) {
		this.top = top;
		this.bot = bot;
	}

	//accepts a visitor to this Combo
	public <R> R accept(IShapeVisitor<R> isd) {
		return isd.visit(this);
	}
}

//to represent a Cartesian point
class CartPt {
	int x;
	int y;

	CartPt(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/* TEMPLATE
  FIELDS
  ... this.x ...          -- int
  ... this.y ...          -- int
  METHODS
  ... this.distTo0() ...        -- double
  ... this.distTo(CartPt) ...   -- double
	 */

	// to compute the distance form this point to the origin
	public double distTo0(){
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	// to compute the distance form this point to the given point
	public double distTo(CartPt pt){
		return Math.sqrt((this.x - pt.x) * (this.x - pt.x) + 
				(this.y - pt.y) * (this.y - pt.y));
	}
}
