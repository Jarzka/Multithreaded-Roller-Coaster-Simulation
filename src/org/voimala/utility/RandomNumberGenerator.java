package org.voimala.utility;

import java.util.Random;

public class RandomNumberGenerator {
    /**
     * @param min Inclusive min value
     * @param max Exclusive max value
     */
    public static int random(final int min, final int max) {
        Random generator = new Random();
        return generator.nextInt(max - min) + min;
    }
}
