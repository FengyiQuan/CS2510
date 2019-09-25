// to represent a resource in the PR Crisis game
interface IResource {
}

// to represent a denial resource
class Denial implements IResource {
  String subject;
  int believability;

  Denial(String subject, int believability) {
    this.subject = subject;
    this.believability = believability;
  }
}

// to represent a bribe resource
class Bribe implements IResource {
  String target;
  int value;

  Bribe(String target, int value) {
    this.target = target;
    this.value = value;
  }
}

// to represent a apology resource
class Apology implements IResource {
  String excuse;
  boolean reusable;

  Apology(String excuse, boolean reusable) {
    this.excuse = excuse;
    this.reusable = reusable;
  }
}

// to represent a action in the PR Crisis game
interface IAction {
}

// to represent a purchase action
class Purchase implements IAction {
  int cost;
  IResource item;

  Purchase(int cost, IResource item) {
    this.cost = cost;
    this.item = item;
  }
}

// to represent a swap action
class Swap implements IAction {
  IResource consumed;
  IResource received;

  Swap(IResource consumed, IResource received) {
    this.consumed = consumed;
    this.received = received;
  }
}

class ExamplesGame {
  ExamplesGame() {
  }

  /*
   * iDidntKnow is a resource whose subject is "knowledge" 
   * and believability is 51
   * witness is a resource whose target is "innocent witness"
   * and value is 49
   * iWontDoItAgain is a resource whose excuse is "I won't do it again"
   * and is not reusable
   * iDidntDo is a resource whose subject is "action"
   * and believability is 50
   * police is a resource whose target is "innocent evidence"
   * and value is 51
   * notMyDuty is a resource whose excuse is "It's not my duty"
   * and is reusable
   * purchase1 is an action which causes player to spend 40
   * and get iDidntKnow resource
   * purchase2 is an action which causes player to spend 80
   * and get notMyDuty resource
   * swap1 is an action which causes player to comsume witness resource
   * and get iWontDoItAgain resource
   * swap2 is an action which causes player to comsume iDidntDo resource 
   * and get ploice resource
   */

  IResource iDidntKnow = new Denial("knowledge", 51);
  IResource witness = new Bribe("innocent witness", 49);
  IResource iWontDoItAgain = new Apology("I won't do it again", false);
  IResource iDidntDo = new Denial("action", 50);
  IResource police = new Bribe("innocent evidence", 51);
  IResource notMyDuty = new Apology("It's not my duty", true);
  IAction purchase1 = new Purchase(40, this.iDidntKnow);
  IAction purchase2 = new Purchase(80, this.notMyDuty);
  IAction swap1 = new Swap(this.witness, this.iWontDoItAgain);
  IAction swap2 = new Swap(this.iDidntDo, this.police);
}