package org.voimala.rollercoaster.rollercoasterelements.passengers;

import org.voimala.rollercoaster.rollercoasterelements.cars.CarStateName;
import org.voimala.rollercoaster.rollercoasterelements.cars.CarThread;

public class PassengerStateWaitingForRide extends PassengerState {
    public PassengerStateWaitingForRide(final PassengerThread owner) {
        super(owner);
    }

    @Override
    public final void updateState() {
        changeStateIfCarHasStartedRiding();
    }
    
    private void changeStateIfCarHasStartedRiding() {
        if (owner.isSittingInCar()) {
            if (owner.getCurrentCar().getCurrentStateName() != CarStateName.CAR_STATE_NAME_STOPPED) {
                System.out.println("Passenger" + " " + owner.getId() + " " + "is on ride" + ".");
                owner.changeState(new PassengerStateOnRide(owner));
            }
        }
    }

    @Override
    public final PassengerStateName getStateName() {
        return PassengerStateName.PASSENGER_STATE_NAME_WAITING_FOR_RIDE;
    }

}
