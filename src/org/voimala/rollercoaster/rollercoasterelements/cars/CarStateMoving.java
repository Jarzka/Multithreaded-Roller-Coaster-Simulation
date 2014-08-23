package org.voimala.rollercoaster.rollercoasterelements.cars;

public class CarStateMoving extends CarState {
    private long rideStartTimestamp = System.currentTimeMillis();

    public CarStateMoving(final CarThread owner) {
        super(owner);
    }

    @Override
    public final void updateState() {
        checkRideDurationTime();
    }

    private void checkRideDurationTime() {
        try {
            Thread.sleep(owner.getRideDurationMs());
        } catch (InterruptedException e) {
            // Continue execution.
        }
        
        System.out.println("Car" + " " + owner.getId() + " " + "has reached it's destination" + ".");
        owner.removePassengers();
        owner.changeState(new CarStateStopped(owner));
    }

    @Override
    public final CarStateName getStateName() {
        return CarStateName.CAR_STATE_NAME_MOVING; 
    }

}
