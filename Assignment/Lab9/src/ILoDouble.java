interface ILoDouble {
  double likelihood();
}

class MtLoDouble implements ILoDouble {

  public double likelihood() {
    return 1.0;
  }

}

class ConsLoDouble implements ILoDouble {
  double first;
  ILoDouble rest;

  ConsLoDouble(Double f, ILoDouble r) {
    this.first = f;
    this.rest = r;
  }

  public double likelihood() {
    return this.first * this.rest.likelihood();
  }
}