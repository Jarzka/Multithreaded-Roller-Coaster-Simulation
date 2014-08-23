package org.voimala.rollercoaster.rollercoasterelements.passengers;

import org.voimala.rollercoaster.rollercoasterelements.cars.CarThread;

public class PassengerStateWaitingToGetSeated extends PassengerState {
    private long waitingTimeToGetSeatedStartedTimestamp = System.currentTimeMillis();
    public PassengerStateWaitingToGetSeated(final PassengerThread owner) {
        super(owner);
    }

    @Override
    public final void updateState() {
        askPermissionToEnterCar();
    }

    private void askPermissionToEnterCar() {
        CarThread car = owner.getTrack().getFirstFreeCarInQueue();
        if (car != null) {
            System.out.println("Passenger" + " " + owner.getId() + " "
                    + "asks permission to enter car" + " " + car.getId() + ".");
            if (car.askPermissionToEnter(owner)) {
                enterCar(car);
            }
        }
    }

    private void enterCar(final CarThread car) {
        handleWaitingTime();
        owner.setCurrentCar(car);        
        owner.changeState(new PassengerStateWaitingForRide(owner));
    }

    private void handleWaitingTime() {
        long waitingTime = System.currentTimeMillis() - waitingTimeToGetSeatedStartedTimestamp;
        System.out.println("Passenger" + " " + owner.getId() + " "
                + "waited" + " " + waitingTime + "ms" + " " + "to get seated" + ".");
        owner.addWaitingTime(waitingTime);
    }
    
    @Override
    public final PassengerStateName getStateName() {
        return PassengerStateName.PASSENGER_STATE_NAME_WAITING_TO_GET_SEATED;
    }
    
}
