package com.example.tomascrd.c_r_s_h.core;

/**
 * Options manager for the game
 *
 * @author Tomás Cardenal López
 */
public class OptionsManager {

    /**
     * Determines if the music should be played or not
     */
    private boolean playMusic;
    /**
     * Determines if the sound effects should be played or not
     */
    private boolean playSoundEffects;
    /**
     * Determines if the phone should vibrate
     */
    private boolean doVibrate;

    /**
     * Determines if the play music option is on
     * @return whether the music option is on or not
     */
    public boolean isPlayMusic() {
        return playMusic;
    }

    /**
     * Sets the value of the play music option
     * @param playMusic the new value of the option
     */
    public void setPlayMusic(boolean playMusic) {
        this.playMusic = playMusic;
    }

    /**
     * Determines if the sound effects option is on
     * @return whether the sound effects is on or not
     */
    public boolean isPlaySoundEffects() {
        return playSoundEffects;
    }

    /**
     * Sets the value of the sound effects option
     * @param playSoundEffects the new value of the option
     */
    public void setPlaySoundEffects(boolean playSoundEffects) {
        this.playSoundEffects = playSoundEffects;
    }

    /**
     * Determines if the vibrate option is on or not
     * @return whether the vibrate option is on or not
     */
    public boolean isDoVibrate() {
        return doVibrate;
    }

    /**
     * Sets the value of the vibrate option
     * @param doVibrate the new value of the option
     */
    public void setDoVibrate(boolean doVibrate) {
        this.doVibrate = doVibrate;
    }
}
