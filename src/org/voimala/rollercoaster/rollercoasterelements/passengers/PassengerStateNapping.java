package org.voimala.rollercoaster.rollercoasterelements.passengers;

import org.voimala.utility.RandomNumberGenerator;

public class PassengerStateNapping extends PassengerState {
    private long nappingStartedTimestamp = System.currentTimeMillis();
    private long nappingEndTimestamp = 0;
    
    public PassengerStateNapping(final PassengerThread owner) {
        super(owner);
        calculateNappingEndTimestamp();
    }

    private void calculateNappingEndTimestamp() {
        /* Set the current time as starting point and add a random number between 0 and
         * maximum napping time. */
        int randomValue = RandomNumberGenerator.random(0, (int) (owner.getNapTimeMax() + 1));
        
        nappingEndTimestamp = nappingStartedTimestamp + randomValue;
    }

    @Override
    public final void updateState() {
        checkIfNappingHasEnded(); // TODO Sleep instead of loop
    }

    private void checkIfNappingHasEnded() {
        if (System.currentTimeMillis() >= nappingEndTimestamp) { // Napping ends
            System.out.println("Passenger" + " " + owner.getId() + " " + "stops napping" + ".");
            owner.changeState(new PassengerStateWaitingToGetSeated(owner));
        }
    }

    @Override
    public final PassengerStateName getStateName() {
        return PassengerStateName.PASSENGER_STATE_NAME_NAPPING;
    }
    
}
