package org.voimala.rollercoaster.rollercoasterelements.passengers;

public abstract class PassengerState {
    
    protected PassengerThread owner = null;
    
    public PassengerState(final PassengerThread owner) {
        this.owner = owner;
    }
    
    public abstract void updateState();
    public abstract PassengerStateName getStateName();
    
}
