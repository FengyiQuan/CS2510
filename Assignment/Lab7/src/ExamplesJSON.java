import tester.Tester;

class ExamplesJSON {
  JSON blank = new JSONBlank();
  JSON num1 = new JSONNumber(1);
  JSON num2 = new JSONNumber(2);
  JSON boolT = new JSONBool(true);
  JSON boolF = new JSONBool(false);
  JSON a = new JSONString("a");
  JSON b = new JSONString("b");

  IList<JSON> mt = new MtList<JSON>();
  IList<JSON> lblank = new ConsList<JSON>(this.blank, this.mt);
  IList<JSON> l1 = new ConsList<JSON>(this.num1, this.lblank);
  IList<JSON> l2 = new ConsList<JSON>(this.num2, this.l1);

  JSON list1 = new JSONList(l2);

  IList<Integer> limt = new MtList<Integer>();
  IList<Integer> l = new ConsList<Integer>(2,
      new ConsList<Integer>(1, new ConsList<Integer>(0, this.limt)));

  boolean testJSONToNumber(Tester t) {
    return t.checkExpect(list1.accept(new JSONToNumber()), 3);
  }
}