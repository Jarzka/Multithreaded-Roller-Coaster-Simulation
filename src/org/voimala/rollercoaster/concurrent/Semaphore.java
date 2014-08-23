package org.voimala.rollercoaster.concurrent;

/* Java 5 comes with a built-in Semaphore so one does not have to implement it.
 * I still wanted to implement this simple semaphore for learning purposes.
 */
public class Semaphore {
    private int numPermits = 0;
    
    public Semaphore(final int numPermits) {
        if (numPermits < 0) {
            this.numPermits = 0;
        } else {
            this.numPermits = numPermits; 
        }
    }
    
    public final void acquirePermit() throws InterruptedException {
        synchronized (this) {
            try {
                while (numPermits < 1) {
                    wait(); // Causes the current thread to wait until another thread invokes the notify() method
                }
                numPermits--;
            } catch (InterruptedException e) {
                notify();
                throw e;
            }
        }
    }
    
    public final synchronized void releasePermit() {
        numPermits++;
        notify(); // Wakes up a single thread that is waiting on this object's monitor
    }
}
