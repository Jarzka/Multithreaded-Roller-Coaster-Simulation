package org.voimala.rollercoaster.programbody;

import java.util.ArrayList;

import org.voimala.rollercoaster.exceptions.RollerCoasterException;
import org.voimala.rollercoaster.rollercoasterelements.cars.CarThread;
import org.voimala.rollercoaster.rollercoasterelements.passengers.PassengerThread;
import org.voimala.rollercoaster.rollercoasterelements.track.Track;

public class RollerCoasterApp {
    
    private ArrayList<PassengerThread> passengers = new ArrayList<>();
    private Track track = new Track();
    private static RollerCoasterApp instanceOfThis = null;
    
    private int commandLineCarCapacity = 4;
    private long commandLineCarRideDuration = 6;
    private int commandLineNumberOfPassengers = 10;
    private long commandLinePassengerMaxNapDuration = 5;
    private long commandLineSimulationRunTimeInSeconds = 90;
    private int commandLineNumberOfCars = 3;
    
    public final void run(final String[] args) {
        initializeEverything(args);
        runSimulation();
    }

    private void initializeEverything(final String[] args) {
        readCommandLineArguments(args);
        initializeRollerCoaster();
    }

    private void readCommandLineArguments(final String[] args) {
        printCommandLineArguments(args);
        
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
            case "-C":
                setCarCapacity(Integer.parseInt(args[i + 1]));
                break;
            case "-T":
                setCarRideDuration(Integer.parseInt(args[i + 1]));
                break;
            case "-n":
                setNumberOfPassengers(Integer.parseInt(args[i + 1]));
                break;
            case "-W":
                setPassengerMaxNapDuration(Integer.parseInt(args[i + 1]));
                break;
            case "-R":
                setSimulationRunDuration(Integer.parseInt(args[i + 1]));
                break;
            case "-m":
                setNumberOfCars(Integer.parseInt(args[i + 1]));
                break;
            default:
                continue;
            }
        }
        
        System.out.println("Command line arguments read successfully.");
    }

    private void setCarCapacity(final int capacity) {
        if (capacity < 1) {
            throw new RollerCoasterException("Capacity must be greater than 0.");
        }
        
        commandLineCarCapacity = capacity;
        
    }

    private void setCarRideDuration(final int duration) {
        if (duration < 1) {
            throw new RollerCoasterException("Car ride duration must be greater than 0.");
        }
        
        commandLineCarRideDuration = duration;
        
    }

    private void setNumberOfPassengers(final int amount) {
        if (amount < 1) {
            throw new RollerCoasterException("Number of passengers must be greater than 0.");
        }
        
        commandLineNumberOfPassengers = amount;
        
    }

    private void setPassengerMaxNapDuration(final int duration) {
        if (duration < 1) {
            throw new RollerCoasterException("Passengers' max nap duration must be greater than 0.");
        }
        
        commandLinePassengerMaxNapDuration = duration;
        
    }

    private void setSimulationRunDuration(final int duration) {
        if (duration < 1) {
            throw new RollerCoasterException("Simulation run duration must be greater than 0.");
        }
        
        commandLineSimulationRunTimeInSeconds = duration;
        
    }
    
    private void setNumberOfCars(final int amount) {
        if (amount < 1) {
            throw new RollerCoasterException("Number of cars must be greater than 0.");
        }
        
        commandLineNumberOfCars = amount;
        
    }

    private void printCommandLineArguments(final String[] args) {
        System.out.println("Running the application with the following command line arguments" + ": ");
        for (String argument : args) {
            System.out.print(argument + " ");
        }
        
        System.out.print("\n");
    }
    
    private void initializeRollerCoaster() {
        createCars();
        createPassengers();
        
        System.out.println("Rollercoaster objects initialized successfully.");
    }
    
    private void createPassengers() {
        for (int i = 0; i < commandLineNumberOfPassengers; i++) {
            passengers.add(new PassengerThread(commandLinePassengerMaxNapDuration, track));
        }
    }

    private void createCars() {
        ArrayList<CarThread> cars = new ArrayList<>();
        for (int i = 0; i < commandLineNumberOfCars; i++) {
            cars.add(new CarThread(commandLineCarCapacity, track, commandLineCarRideDuration));
        }
        
        track.addCars(cars);
    }
    
    private void runSimulation() {
        System.out.println("Starting simulation...");
        long simulationStartedMs = System.currentTimeMillis();
        
        startThreads();
        
        try {
            Thread.sleep(commandLineSimulationRunTimeInSeconds * 1000);
        } catch (InterruptedException e) {
            // Continue execution.
        }
        
        handleSimulationEnd();
    }

    private void handleSimulationEnd() {
        System.out.println("----> Average time to get seated" + ": " + getAveragePassengerWaitingTime() + "ms" + ".");
        
        stopThreads();
        
        System.out.println("Exiting...");
    }

    private void startThreads() {
        startCarThreads();
        startPassengerThreads();
    }

    private void startCarThreads() {
        ArrayList<CarThread> cars = (ArrayList<CarThread>) track.getCars();
        
        for (CarThread car : cars) {
            System.out.println("Starting car thread" + ": " + car.getId());
            car.start();
        }
    }
    
    private void startPassengerThreads() {
        for (PassengerThread passenger : passengers) {
            System.out.println("Starting passenger thread" + ": " + passenger.getId());
            passenger.start();
        }
    }
    
    private double getAveragePassengerWaitingTime() {
        ArrayList<Long> waitingTimes = new ArrayList<>();
        
        // Add all waiting times to the array
        for (PassengerThread passenger : passengers) {
            waitingTimes.addAll(passenger.getWaitingTimes());
        }
        
        // Compute the average waiting time
        double waitingTimesSum = 0;
        for (Long time : waitingTimes) {
            waitingTimesSum += time;
        }
        
        double averageWaitingTime = waitingTimesSum / waitingTimes.size();
        
        return averageWaitingTime;
        
    }

    private void stopThreads() {
        stopCarThreads();
        stopPassengerThreads();
    }
    
    private void stopCarThreads() {
        ArrayList<CarThread> cars = (ArrayList<CarThread>) track.getCars();
        
        for (CarThread car : cars) {
            car.interrupt();
        }
    }

    private void stopPassengerThreads() {
        for (PassengerThread passenger : passengers) {
            passenger.interrupt();
        }
    }

    public static RollerCoasterApp getInstance() {
        if (instanceOfThis == null) {
            instanceOfThis = new RollerCoasterApp();
        }
        
        return instanceOfThis;
    }
    
}
