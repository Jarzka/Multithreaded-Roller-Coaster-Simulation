package org.voimala.rollercoaster.rollercoasterelements.cars;

public class CarStateStopped extends CarState {

    public CarStateStopped(final CarThread owner) {
        super(owner);
    }

    @Override
    public final void updateState() {
        if (isCarFullOfPassengers() && isThisCarFirstInQueue()) {
            System.out.println("Car" + " " + owner.getId() + " " + "is full and starts ride" + ".");
            owner.changeState(new CarStateMoving(owner));
        }
    }

    private boolean isCarFullOfPassengers() {
        return owner.getNumberOfPassengers() == owner.getCapacity();
    }
    
    private boolean isThisCarFirstInQueue() {
        return owner.getOwnerTrack().getFirstCarInQueue() == owner;
    }

    @Override
    public final CarStateName getStateName() {
        return CarStateName.CAR_STATE_NAME_STOPPED;
    }

}
