// a json value
interface JSON {
  <R> R accept(JSONVistor<R> v);
}

// no value
class JSONBlank implements JSON {

  public <R> R accept(JSONVistor<R> v) {
    return v.visitBlank(this);
  }
}

// a number
class JSONNumber implements JSON {
  int number;

  JSONNumber(int number) {
    this.number = number;
  }

  public <R> R accept(JSONVistor<R> v) {
    return v.visitNumber(this);
  }
}

// a boolean
class JSONBool implements JSON {
  boolean bool;

  JSONBool(boolean bool) {
    this.bool = bool;
  }

  public <R> R accept(JSONVistor<R> v) {
    return v.visitBool(this);
  }
}

// a string
class JSONString implements JSON {
  String str;

  JSONString(String str) {
    this.str = str;
  }

  public <R> R accept(JSONVistor<R> v) {
    return v.visitString(this);
  }
}

//a list of JSON values
class JSONList implements JSON {
  IList<JSON> values;

  JSONList(IList<JSON> values) {
    this.values = values;
  }

  public <R> R accept(JSONVistor<R> v) {
    return v.visitList(this);
  }
}