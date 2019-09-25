interface ITransit {
    <R> R accept(ITransitVisitor<R> visitor);
}

class Train implements ITransit {
    int cars;
    int lengthPerCar;

    Train(int cars, int lengthPerCar) {
        this.cars = cars;
        this.lengthPerCar = lengthPerCar;
    }

    public <R> R accept(ITransitVisitor<R> visitor) {
        return visitor.visitTrain(this);
    }
}

class Bus implements ITransit {
    int length;

    Bus(int length) {
        this.length = length;
    }

    public <R> R accept(ITransitVisitor<R> visitor) {
        return visitor.visitBus(this);
    }
}

interface IFunc<A, R> {
    R apply(A arg);
}

interface ITransitVisitor<R> extends IFunc<ITransit, R> {
    R visitTrain(Train train);
    R visitBus(Bus bus);
}

class Length implements ITransitVisitor<Integer> {
    public Integer apply(ITransit arg) {
        return arg.accept(this);
    }

    public Integer visitTrain(Train train) {
        return train.cars * train.lengthPerCar;
    }

    public Integer visitBus(Bus bus) {
        return bus.length;
    }
}

class ExamplesTrains {
    ITransit longBoi = new Train(5, 30);
    ITransit smolBoi = new Bus(20);
    Length length = new Length();

    boolean testLength(Tester t) {
        return t.checkExpect(length.apply(longBoi), 150) &&
                t.checkExpect(length.apply(smolBoi), 20);
    }
}