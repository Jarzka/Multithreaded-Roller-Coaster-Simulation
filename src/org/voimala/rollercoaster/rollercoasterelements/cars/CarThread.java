package org.voimala.rollercoaster.rollercoasterelements.cars;
import java.util.ArrayList;

import org.voimala.rollercoaster.concurrent.Semaphore;
import org.voimala.rollercoaster.rollercoasterelements.passengers.PassengerThread;
import org.voimala.rollercoaster.rollercoasterelements.track.Track;


public class CarThread extends Thread {
    
    private Track ownerTrack = null;
    private ArrayList<PassengerThread> passengers = new ArrayList<>();
    private CarState currentState = new CarStateStopped(this);
    private int capacity = 0;
    private long rideDurationMs = 0;
    private long timestampMovingStoppedInMs = 0;
    private Semaphore semaphore;
    private int id = 0;
    private static int idCounter = 0;
    
    public CarThread(final int capacity, final Track ownerTrack, final long rideDurationInSeconds) {
        super("CarThread");
        this.capacity = capacity;
        this.semaphore = new Semaphore(capacity);
        this.ownerTrack = ownerTrack;
        this.rideDurationMs = rideDurationInSeconds * 1000;
        timestampMovingStoppedInMs = System.currentTimeMillis();
        this.id = idCounter++;
    }
    
    public final void run() {
        while (!isInterrupted()) {
            currentState.updateState();
        }
        
        System.out.println("Car thread" + " " + id + " " + "terminated" + ".");
    }
    
    public final boolean askPermissionToEnter(final PassengerThread passenger) {
        if (currentState.getStateName() == CarStateName.CAR_STATE_NAME_STOPPED) {
            try {
                semaphore.acquirePermit();
                System.out.println("Adding passenger" + " " + passenger.getId()
                        + " " + "to car" + " " + getId() + ".");
                passengers.add(passenger);
                passenger.setCurrentCar(this);
                printCarCapacity();
                return true;
            } catch (InterruptedException e) {
                // Continue execution.
            }
        }
        
        return false;
    }

    private void printCarCapacity() {
        System.out.println("Car" + " " + id + " " + "current capacity" + " "
                + passengers.size() + "/" + capacity);
    }
    
    public final void removePassenger(final PassengerThread passenger) {
        passengers.remove(passenger);
        passenger.setCurrentCar(null);
        semaphore.releasePermit();
        System.out.println("Removing passenger" + " " + passenger.getId()
                + " " + "from car" + " " + getId() + ".");
        printCarCapacity();
    }
    
    public final int getNumberOfPassengers() {
        return passengers.size();
    }
    
    public final int getCapacity() {
        return capacity;
    }

    public final void changeState(final CarState newState) {
        if (newState.getStateName() == CarStateName.CAR_STATE_NAME_STOPPED) {
            timestampMovingStoppedInMs = System.currentTimeMillis();
        }
        
        currentState = newState;
    }

    public final long getRideDurationMs() {
        return rideDurationMs;
    }

    public final Track getOwnerTrack() {
        return ownerTrack;
    }

    public final CarStateName getCurrentStateName() {
        return currentState.getStateName();
    }

    public final long getTimestampMovingStopped() {
        return timestampMovingStoppedInMs;
    }
    
    /**
     * Return type should be int but for some reason Java forces to use long.
     */
    public final long getId() {
        return this.id;
    }

    public final void removePassengers() {
        System.out.println("Removing all passengers from car" + " " + id);
        for (int i = 0; i < passengers.size(); i++) {
            removePassenger(passengers.get(0));
            i--;
        }
    }
    
    public final boolean hasFreeSeats() {
        return getNumberOfPassengers() < capacity;
    }
    
}
