
interface JSONVistor<T> extends IFunc<JSON, T> {
  T visitBlank(JSONBlank b);

  T visitNumber(JSONNumber num);

  T visitBool(JSONBool bool);

  T visitString(JSONString string);
  
  T visitList(JSONList list);
  
}

class JSONToNumber implements JSONVistor<Integer> {
  public Integer apply(JSON input) {
    return input.accept(this);
  }

  public Integer visitBlank(JSONBlank j) {
    return 0;
  }

  public Integer visitNumber(JSONNumber j) {
    return j.number;
  }

  public Integer visitBool(JSONBool j) {
    if (j.bool) {
      return 1;
    }
    else {
      return 0;
    }
  }

  public Integer visitString(JSONString j) {
    return j.str.length();
  }

  public Integer visitList(JSONList list) {
    return list.values.foldr(new OneToInt(), 0);
  }



}