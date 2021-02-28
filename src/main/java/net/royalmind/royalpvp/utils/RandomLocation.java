package net.royalmind.royalpvp.utils;

import java.util.Random;

public class RandomLocation {

    private static final Random RANDOM = new Random();

    public static int randomBetween(final int minimum, final int maximum) {
        return RANDOM.nextInt((maximum - minimum) + 1) + minimum;
    }

    public static float randomBetween(final float minimum, final float maximum) {
        return minimum + RANDOM.nextFloat() * (maximum - minimum);
    }

}
