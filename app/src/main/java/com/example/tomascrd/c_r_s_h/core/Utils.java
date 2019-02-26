package com.example.tomascrd.c_r_s_h.core;

import android.content.Context;

import java.util.Random;

/**
 * Utilities and functions
 *
 * @author Tomás Cardenal López
 */
public class Utils {
    /**
     * The application's context
     */
    private Context context;

    /**
     * Starts a utils object on the given context
     *
     * @param context
     */
    public Utils(Context context) {
        this.context = context;
    }

    /**
     * Generates a new pseudorandom integer value between the given values
     *
     * @param min the minimum bound, inclusive
     * @param max the maximum bound, inclusive
     * @return the pseudorandom number, or gets
     */
    public static int getRandom(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("The max value can't be less or equal than the min value");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
