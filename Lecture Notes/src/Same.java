import tester.*;

interface IShape {
	//is this the same shape as that one?
	boolean sameShape(IShape that);
	// is this shape a Circle?
	//is this IShape the same Circle as that Circle?
	boolean sameCircle(Circle that);
	//is this IShape the same Rect as that Rect?
	boolean sameRect(Rect that);
	//is this IShape the same Square as that Square
	boolean sameSquare(Square that);
	//is this IShape the same Combo as that Combo
	boolean sameCombo(Combo that);
}

abstract class AShape implements IShape {
	int x, y;
	
	AShape(int x, int y) {
		this.x = x;
		this.y = y;
	}
	//is this Shape the same as that Rect?
	public boolean sameRect(Rect that) {return false;}
	//is this Shape the same as that Square?
	public boolean sameSquare(Square that) {return false;}
	//is this Shape the same as that Combo?
	public boolean sameCombo(Combo that) { return false;}
	//is this Shape the same as that Circle?
	public boolean sameCircle(Circle that) { return false;}
	
	public abstract boolean sameShape(IShape that);
}
class Circle extends AShape {
	int x, y;
	int radius;
	Circle(int x, int y, int radius) {
		super(x, y);
		this.radius = radius;
	}
	
	//Is this Circle the same as that one?
	public boolean sameCircle(Circle that) {
		/* Template:
		 * Fields:
		 * this.x, this.y, this.radius
		 *
		 * Fields of parameters:
		 * that.x, that.y, that.radius
		 */
		return this.x == that.x &&
				this.y == that.y &&
				this.radius == that.radius;
	}

	public boolean sameShape(IShape that) {
		return that.sameCircle(this);
	}
}

class Rect extends AShape {
	int x, y;
	int w, h;
	Rect(int x, int y, int w, int h) {
		super(x, y);
		this.w = w;
		this.h = h;
	}

	
	//is this Rect the same as that one?
	public boolean sameRect(Rect that) {
		/* Template:
		 * Fields:
		 * this.x, this.y, this.w, this.h
		 *
		 * Fields of parameters:
		 * that.x, that.y, that.w, that.h
		 */
		return this.x == that.x &&
				this.y == that.y &&
				this.w == that.w &&
				this.h == that.h;
	}

	// in Rect
	public boolean sameShape(IShape that) {
		return that.sameRect(this);
	}

}

class Square extends Rect {
	Square(int x, int y, int s) { 
		super(x, y, s, s); 
	}

	public boolean sameRect(Rect that) {return false;}

	public boolean sameSquare(Square that) {
		return this.x == that.x &&
				this.y == that.y &&
				this.w == that.w; // No need to check the h field, too...
	}


	public boolean sameShape(IShape that) {
		return that.sameSquare(this);
	}
}

class Combo implements IShape {
	IShape top;
	IShape bot;
	
	Combo(IShape t, IShape b) {
		this.top = t;
		this.bot = b;
	}
	
	public boolean sameCircle(Circle that) { return false;}
	public boolean sameSquare(Square that) {return false;}
	public boolean sameRect(Rect that) { return false;}
	
	//is this combo the same as that one?
	public boolean sameCombo(Combo that) { 
		return this.top.sameShape(that.top) &&
				this.bot.sameShape(that.bot);
	}
	
	public boolean sameShape(IShape that) {
		return that.sameCombo(this);
	}
}


class ExamplesShapes { 
	//In test method in an Examples class
	Circle c1 = new Circle(3, 4, 5);
	Circle c2 = new Circle(4, 5, 6);
	Circle c3 = new Circle(3, 4, 5);
	Rect r1 = new Rect(3, 4, 5, 5);
	Rect r2 = new Rect(4, 5, 6, 7);
	Rect r3 = new Rect(3, 4, 5, 5);
	Square s1 = new Square(3, 4, 5);
	Square s2 = new Square(4, 5, 6);
	Square s3 = new Square(3, 4, 5);

	boolean testSameness(Tester t) {
		return t.checkExpect(c1.sameCircle(c2), false)
				&& t.checkExpect(c2.sameCircle(c1), false)
				&& t.checkExpect(c1.sameCircle(c3), true)
				&& t.checkExpect(c3.sameCircle(c1), true)

				&& t.checkExpect(r1.sameRect(r2), false)
				&& t.checkExpect(r2.sameRect(r1), false)
				&& t.checkExpect(r1.sameRect(r3), true)
				&& t.checkExpect(r3.sameRect(r1), true)
				&& t.checkExpect(s1.sameShape(r2), false)
				&& t.checkExpect(r2.sameShape(s1), false) 

				&& t.checkExpect(s1.sameShape(r1), false) 
				&& t.checkExpect(r1.sameShape(s1), false);
	}
}