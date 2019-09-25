import tester.Tester;

class Polynomial {
  ILoMonomial monomials;

  Polynomial(ILoMonomial monomials) {
    if (!monomials.checkRepet()) {
      this.monomials = monomials;
    }
    else {
      throw new IllegalArgumentException("Monomials given to it have the same degree.");
    }
  }

  /*
   * fields:
   * this.monomials ... ILoMonomial
   * 
   * methods:
   * this.samePolynomial(Polynomial other) ... boolean
   * 
   * methods for fields:
   * this.monomials.checkRepet() ... boolean
   * this.monomials.anySameDegree(int degree) ... boolean
   * this.monomials.sameMonomials(ILoMonomial other) ... boolean
   * this.monomials.sameInGivenMt(MtLoMonomial other) ... boolean
   * this.monomials.sameInGivenCons(ConsLoMonomial other) ... boolean
   * this.monomials.sameMonomial(Monomial given) ... boolean
   * this.monomials.allzeroCoefficient() ... boolean
   */

  // determines if two Polynomials represent the same polynomial
  boolean samePolynomial(Polynomial other) {
    return this.monomials.sameMonomials(other.monomials)
        && other.monomials.sameMonomials(this.monomials);
  }
}

interface ILoMonomial {

  // check if ILoMonomial has repeated elements
  boolean checkRepet();

  // check if any element degree is same as given
  boolean anySameDegree(int degree);

  // check if two ILoMonomial are same
  boolean sameMonomials(ILoMonomial other);

  // check if ILoMonomial is the same as given MtLoMonomial
  boolean sameInGivenMt(MtLoMonomial other);

  // check if ILoMonomial is the same as given ConsLoMonomial
  boolean sameInGivenCons(ConsLoMonomial other);

  // check if any monomial in ILoMonomial is same as given one
  boolean sameMonomial(Monomial given);

  // check if degrees of all monomials in ILoMonomial are 0
  boolean allzeroCoefficient();
}

class MtLoMonomial implements ILoMonomial {

  /*
   * methods:
   * this.checkRepet() ... boolean
   * this.anySameDegree(int degree) ... boolean
   * this.sameMonomials(ILoMonomial other) ... boolean
   * this.sameInGivenMt(MtLoMonomial other) ... boolean
   * this.sameInGivenCons(ConsLoMonomial other) ... boolean
   * this.sameMonomial(Monomial given) ... boolean
   * this.allzeroCoefficient() ... boolean
   */

  // check if it has same degree monomials in an empty list
  public boolean checkRepet() {
    return false;
  }

  // check if there is any element degree is same as given in a MtLoMonomial
  public boolean anySameDegree(int degree) {
    return false;
  }

  // check if MtLoMonomial is the same as given ILoMonomial
  public boolean sameMonomials(ILoMonomial other) {
    return other.sameInGivenMt(this);
  }

  // check if MtLoMonomial is the same as given MtLoMonomial
  public boolean sameInGivenMt(MtLoMonomial other) {
    return true;
  }

  // check if MtLoMonomial is the same as given ConsLoMonomial
  public boolean sameInGivenCons(ConsLoMonomial other) {
    return other.allzeroCoefficient();
  }

  // check if there is same monomial in the MtLoMonomial
  public boolean sameMonomial(Monomial given) {
    return false;
  }

  // check if degrees of all monomials in MtLoMonomial are 0
  public boolean allzeroCoefficient() {
    return true;
  }
}

class ConsLoMonomial implements ILoMonomial {
  Monomial first;
  ILoMonomial rest;

  ConsLoMonomial(Monomial first, ILoMonomial rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * fields:
   * this.first ... Monomial
   * this.rest ... ILoMonomial
   * 
   * methods:
   * this.checkRepet() ... boolean
   * this.anySameDegree(int degree) ... boolean
   * this.sameMonomials(ILoMonomial other) ... boolean
   * this.sameInGivenMt(MtLoMonomial other) ... boolean
   * this.sameInGivenCons(ConsLoMonomial other) ... boolean
   * this.sameMonomial(Monomial given) ... boolean
   * this.allzeroCoefficient() ... boolean
   * 
   * methods for fields:
   * this.first.sameMonomial(Monomial other) ... boolean
   * this.first.areSameDegree(ILoMonomial given) ... boolean
   * this.first.isTwoSame(int degree) ... boolean
   * this.first.zeroCoefficient() ... boolean
   * this.first.anySameMonomial(ILoMonomial other) ... boolean
   * 
   * this.rest.checkRepet() ... boolean
   * this.rest.anySameDegree(int degree) ... boolean
   * this.rest.sameMonomials(ILoMonomial other) ... boolean
   * this.rest.sameInGivenMt(MtLoMonomial other) ... boolean
   * this.rest.sameInGivenCons(ConsLoMonomial other) ... boolean
   * this.rest.sameMonomial(Monomial given) ... boolean
   * this.rest.allzeroCoefficient() ... boolean
   */

  // check if it has same degree monomials in a list
  public boolean checkRepet() {
    return this.first.areSameDegree(this.rest) || this.rest.checkRepet();
  }

  // check if there is any element degree is same as given in a ConsLoMonomial
  public boolean anySameDegree(int degree) {
    return this.first.isTwoSame(degree) || this.rest.anySameDegree(degree);
  }

  // check if ConsLoMonomial is the same as given ILoMonomial
  public boolean sameMonomials(ILoMonomial other) {
    return other.sameInGivenCons(this);
  }

  // check if ConsLoMonomial is the same as given MtLoMonomial
  public boolean sameInGivenMt(MtLoMonomial other) {
    return this.first.zeroCoefficient();
  }

  // check if ConsLoMonomial is the same as given ConsLoMonomial
  public boolean sameInGivenCons(ConsLoMonomial other) {
    if (this.first.zeroCoefficient()) {
      return true;
    }
    else {
      return this.first.sameMonomial(other.first) || this.first.anySameMonomial(other.rest);
    }
  }

  // check if there is same monomial in the ConsLoMonomial
  public boolean sameMonomial(Monomial given) {
    return this.first.sameMonomial(given) || this.rest.sameMonomial(given);
  }

  // check if degrees of all monomials in ConsLoMonomial are 0
  public boolean allzeroCoefficient() {
    return this.first.zeroCoefficient() && this.rest.allzeroCoefficient();
  }
}

class Monomial {
  int degree;
  int coefficient;

  Monomial(int d, int c) {
    if (d >= 0) {
      this.degree = d;
    }
    else {
      throw new IllegalArgumentException("Degree should be non-negative.");
    }
    this.coefficient = c;
  }

  /*
   * fields:
   * this.coefficient ... int
   * this.degree ... int
   * 
   * methods:
   * this.sameMonomial(Monomial other) ... boolean
   * this.areSameDegree(ILoMonomial given) ... boolean
   * this.isTwoSame(int degree) ... boolean
   * this.zeroCoefficient() ... boolean
   * this.anySameMonomial(ILoMonomial other) ... boolean
   */

  // check if given monomial is the same as this
  boolean sameMonomial(Monomial other) {
    return this.coefficient == other.coefficient && this.degree == other.degree;
  }

  // check if there is any same degree monomial as this monomial in ILoMonomial
  boolean areSameDegree(ILoMonomial given) {
    return given.anySameDegree(this.degree);
  }

  // check if this degree is the same as given int
  boolean isTwoSame(int degree) {
    return this.degree == degree;
  }

  // check if this degree is 0
  boolean zeroCoefficient() {
    return this.coefficient == 0;
  }

  // check if there is any the same monomial as this monomial in ILoMonomial
  boolean anySameMonomial(ILoMonomial other) {
    return other.sameMonomial(this);
  }
}

class ExamplesPolynomial {
  Monomial monomial0_n1 = new Monomial(0, -1);
  Monomial monomial0_0 = new Monomial(0, 0);
  Monomial monomial0_1 = new Monomial(0, 1);
  Monomial monomial1_1 = new Monomial(1, 1);
  Monomial monomial1_1_1 = new Monomial(1, 1);
  Monomial monomial1_2 = new Monomial(1, 2);
  Monomial monomial2_0 = new Monomial(2, 0);
  Monomial monomial2_2 = new Monomial(2, 2);

  ILoMonomial mt = new MtLoMonomial();
  ILoMonomial list1 = new ConsLoMonomial(this.monomial0_0, this.mt);
  ILoMonomial list2 = new ConsLoMonomial(this.monomial1_1_1,
      new ConsLoMonomial(this.monomial1_1, this.mt));
  ILoMonomial list3 = new ConsLoMonomial(this.monomial0_n1, this.mt);
  ILoMonomial list4 = new ConsLoMonomial(this.monomial1_1, this.mt);

  Polynomial p = new Polynomial(mt);
  Polynomial p1 = new Polynomial(this.list4);
  Polynomial p1_1 = new Polynomial(this.list4);
  Polynomial p2 = new Polynomial(this.list1);

  // test for Polynomial
  boolean testPolynomial(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Monomials given to it have the same degree."), "Polynomial",
        this.list2) && t.checkExpect(this.p1.samePolynomial(this.p1_1), true)
        && t.checkExpect(this.p.samePolynomial(this.p2), true)
        && t.checkExpect(this.p2.samePolynomial(this.p), true)
        && t.checkExpect(this.p1.samePolynomial(this.p2), false);
  }

  // test for MtLoMonomial
  boolean testMtLoMonomial(Tester t) {
    return t.checkExpect(this.mt.checkRepet(), false)
        && t.checkExpect(this.mt.anySameDegree(0), false)
        && t.checkExpect(this.mt.anySameDegree(2), false)
        && t.checkExpect(this.mt.sameMonomials(this.mt), true)
        && t.checkExpect(this.mt.sameMonomials(this.list2), false)
        && t.checkExpect(this.mt.sameInGivenMt(new MtLoMonomial()), true)
        && t.checkExpect(this.mt.sameInGivenCons(new ConsLoMonomial(this.monomial0_0, this.mt)),
            true)
        && t.checkExpect(this.mt.sameInGivenCons(new ConsLoMonomial(this.monomial1_1, this.mt)),
            false)
        && t.checkExpect(this.mt.sameMonomial(this.monomial0_0), false)
        && t.checkExpect(this.mt.sameMonomial(this.monomial1_1), false)
        && t.checkExpect(this.mt.allzeroCoefficient(), true);
  }

  // test for ConsLoMonomial
  boolean testConsLoMonomial(Tester t) {
    return t.checkExpect(this.list2.checkRepet(), true)
        && t.checkExpect(this.list1.checkRepet(), false)
        && t.checkExpect(this.list1.anySameDegree(3), false)
        && t.checkExpect(this.list1.anySameDegree(0), true)
        && t.checkExpect(this.list1.sameMonomials(this.mt), true)
        && t.checkExpect(this.list1.sameMonomials(this.list2), false)
        && t.checkExpect(this.list2.sameInGivenMt(new MtLoMonomial()), false)
        && t.checkExpect(this.list1.sameInGivenMt(new MtLoMonomial()), true)
        && t.checkExpect(this.list4.sameInGivenCons(
            new ConsLoMonomial(this.monomial1_1_1, new ConsLoMonomial(this.monomial1_1, this.mt))),
            true)
        && t.checkExpect(this.list4.sameInGivenCons(new ConsLoMonomial(this.monomial1_1, this.mt)),
            true)
        && t.checkExpect(this.list1.sameMonomial(monomial1_1_1), false)
        && t.checkExpect(this.list4.sameMonomial(monomial1_1_1), true)
        && t.checkExpect(this.list1.allzeroCoefficient(), true)
        && t.checkExpect(this.list4.allzeroCoefficient(), false);
  }

  // test for Monomial
  boolean testMonomial(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Degree should be non-negative."), "Monomial", -1, 0)
        && t.checkExpect(this.monomial0_1.sameMonomial(this.monomial0_1), true)
        && t.checkExpect(this.monomial1_1.sameMonomial(this.monomial0_1), false)
        && t.checkExpect(this.monomial0_1.areSameDegree(this.list3), true)
        && t.checkExpect(this.monomial2_0.areSameDegree(this.list1), false)
        && t.checkExpect(this.monomial0_0.isTwoSame(0), true)
        && t.checkExpect(this.monomial0_0.isTwoSame(3), false)
        && t.checkExpect(this.monomial0_0.zeroCoefficient(), true)
        && t.checkExpect(this.monomial1_1.zeroCoefficient(), false)
        && t.checkExpect(this.monomial0_0.anySameMonomial(this.list1), true)
        && t.checkExpect(this.monomial2_2.anySameMonomial(this.list1), false);
  }
}