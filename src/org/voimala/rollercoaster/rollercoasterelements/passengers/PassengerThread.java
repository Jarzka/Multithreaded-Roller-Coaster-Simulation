package org.voimala.rollercoaster.rollercoasterelements.passengers;

import java.util.ArrayList;

import org.voimala.rollercoaster.rollercoasterelements.cars.CarThread;
import org.voimala.rollercoaster.rollercoasterelements.track.Track;

public class PassengerThread extends Thread {
    
    private Track track = null;
    private CarThread currentCar = null; /** The passenger knows the car he is traveling in. */
    private long napTimeMaxMs = 0;
    private PassengerState currentState = new PassengerStateWaitingToGetSeated(this);
    private int id = 0;
    private static int idCounter = 0;
    /** All waiting times are stored in this array. */
    private ArrayList<Long> waitingTimesInMs = new ArrayList<>();
    
    public PassengerThread(final long napTimeMaxInSeconds, final Track track) {
        super("PassengerThread");
        this.napTimeMaxMs = napTimeMaxInSeconds * 1000;
        this.track = track;
        this.id = idCounter++;
    }
    
    public final void run() {
        while (!isInterrupted()) {
            currentState.updateState();
        }
        
        System.out.println("Passenger thread" + " " + id + " " + "terminated" + ".");
    }
    
    public final void changeState(final PassengerState newState) {
        currentState = newState;
    }
    
    /**
     * @return The maximum nap time in milliseconds.
     */
    public final long getNapTimeMax() {
        return napTimeMaxMs;
    }
    
    public final void nap() {
        System.out.println("Passenger" + " " + getId() + " " + "takes a nap" + ".");
        changeState(new PassengerStateNapping(this));
    }
    
    /**
     * @return Returns null if the passenger is not currently traveling in any car.
     */
    public final CarThread getCurrentCar() {
        return currentCar;
    }
    
    public final void setCurrentCar(final CarThread car) {
        this.currentCar = car;
    }
    
    /**
     * Return type should be int but for some reason Java forces to use long.
     */
    public final long getId() {
        return this.id;
    }
    
    public final Track getTrack() {
        return track;
    }
    
    /** Adds the given waiting time to the waiting times array. */
    public final synchronized void addWaitingTime(final long time) {
        waitingTimesInMs.add(time);
    }
    
    public final synchronized ArrayList<Long> getWaitingTimes() {
        return waitingTimesInMs;
    }

    public final boolean isSittingInCar() {
        return currentCar != null;
    }
    
}
