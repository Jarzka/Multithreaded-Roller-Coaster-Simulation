package org.voimala.rollercoaster.rollercoasterelements.track;

import java.util.ArrayList;
import java.util.List;

import org.voimala.rollercoaster.rollercoasterelements.cars.CarStateName;
import org.voimala.rollercoaster.rollercoasterelements.cars.CarThread;

public class Track {
    private ArrayList<CarThread> carsOnTrack = new ArrayList<>();
    
    public final void addCars(final ArrayList<CarThread> cars) {
        carsOnTrack.addAll(cars);
    }
    
    /**
     * Finds the car that has the lowest "moving stopped" timestamp. This means that the car
     * has been waiting in the queue longer than any other car. 
     * @param cars The list of cars that is used in the search process.
     */
    public final CarThread getFirstCarInQueue(final List<CarThread> cars) {
        CarThread firstCarInQueue = cars.get(0);
        
        for (CarThread car : cars) {
            if (car.getTimestampMovingStopped() < firstCarInQueue.getTimestampMovingStopped()) {
                firstCarInQueue = car;
            }
        }
        
        return firstCarInQueue;
    }
    
    /**
     * Finds the car on the track that has the lowest "moving stopped" timestamp. This means that the car
     * has been waiting in the queue longer than any other car. 
     */
    public final CarThread getFirstCarInQueue() {
        CarThread firstCarInQueue = carsOnTrack.get(0);
        
        for (CarThread car : carsOnTrack) {
            if (car.getTimestampMovingStopped() < firstCarInQueue.getTimestampMovingStopped()) {
                firstCarInQueue = car;
            }
        }
        
        return firstCarInQueue;
    }
    
    /**
     * @return If all cars are on ride or full of passengers, returns null.
     */
    public final CarThread getFirstFreeCarInQueue() {
        ArrayList<CarThread> freeCars = getFreeCars();
        
        if (freeCars.isEmpty()) {
            return null;
        }
        
        CarThread firstFreeCar = getFirstCarInQueue(freeCars);
        return firstFreeCar;
    }
    
    private ArrayList<CarThread> getFreeCars() {
        ArrayList<CarThread> freeCars = new ArrayList<>();
        
        for (CarThread car : carsOnTrack) {
            if (car.getCurrentStateName() == CarStateName.CAR_STATE_NAME_STOPPED
                    && car.hasFreeSeats()) {
                freeCars.add(car);
            }
        }
        
        return freeCars;
    }

    public final List<CarThread> getCars() {
        return carsOnTrack;
    }

}
