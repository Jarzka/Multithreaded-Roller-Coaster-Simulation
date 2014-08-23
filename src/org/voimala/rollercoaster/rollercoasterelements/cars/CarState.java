package org.voimala.rollercoaster.rollercoasterelements.cars;

public abstract class CarState {
    
    protected CarThread owner = null;
    
    public CarState(final CarThread owner) {
        this.owner = owner;
    }
    
    public abstract void updateState();
    public abstract CarStateName getStateName();
    
}
