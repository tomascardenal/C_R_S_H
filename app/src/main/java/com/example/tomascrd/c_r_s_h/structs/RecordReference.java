package com.example.tomascrd.c_r_s_h.structs;

/**
 * Represents a reference for the records list
 *
 * @author Tomás Cardenal López
 */
public class RecordReference implements Comparable {

    /**
     * The score of the record
     */
    public int highScore;
    /**
     * The name of the player
     */
    public String playerName;

    /**
     * Starts a map reference on this parameters
     *
     * @param highScore  the score of the record
     * @param playerName the name of the player
     */
    public RecordReference(int highScore, String playerName) {
        this.highScore = highScore;
        this.playerName = playerName;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param record the record to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Object record) {
        int compareScore = ((RecordReference) record).highScore;
        /* For Descending order*/
        return compareScore - this.highScore;
    }
}
