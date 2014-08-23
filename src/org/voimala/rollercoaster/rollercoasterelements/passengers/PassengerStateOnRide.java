package org.voimala.rollercoaster.rollercoasterelements.passengers;

public class PassengerStateOnRide extends PassengerState {
    
    public PassengerStateOnRide(final PassengerThread owner) {
        super(owner);
    }

    @Override
    public final void updateState() {
        if (carHasReachedDestination()) {
            owner.nap();
        }
    }

    private boolean carHasReachedDestination() {
        /* When the ride has ended, the car will remove the passenger from it's container and
         * set it's current car to null. */
        return owner.getCurrentCar() == null;
    }

    @Override
    public final PassengerStateName getStateName() {
        return PassengerStateName.PASSENGER_STATE_NAME_ON_RIDE;
    }
    
}
