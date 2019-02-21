package com.example.tomascrd.c_r_s_h.core;

import android.content.Context;
import android.content.SharedPreferences;

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
     * Determines if the player 1 keeps the velocity after the joystick gets deactivated or not
     */
    private boolean keepJoystickVelocityP1;
    /**
     * Determines if the player 2 keeps the velocity after the joystick gets deactivated or not
     */
    private boolean keepJoystickVelocityP2;
    /**
     * SharedPreferences to store the options
     */
    private SharedPreferences preferences;
    /**
     * The application's Context
     */
    private Context context;

    /**
     * Default constructor, initalizes a new OptionsManager with the given Context
     *
     * @param context The context for this OptionsManager
     */
    public OptionsManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(GameConstants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        loadOptions();
    }

    /**
     * Loads the values of the options if they are saved, or sets them to true if they are not saved
     */
    public void loadOptions() {
        playMusic = preferences.getBoolean(GameConstants.PREFERENCES_MUSIC, true);
        playSoundEffects = preferences.getBoolean(GameConstants.PREFERENCES_SOUNDEFFECTS, true);
        doVibrate = preferences.getBoolean(GameConstants.PREFERENCES_VIBRATE, true);
        keepJoystickVelocityP1 = preferences.getBoolean(GameConstants.PREFERENCES_KEEPJOYSTICKVELOCITY_P1, true);
        keepJoystickVelocityP2 = preferences.getBoolean(GameConstants.PREFERENCES_KEEPJOYSTICKVELOCITY_P2, true);
    }

    /**
     * Saves the values of the options
     */
    public void saveOptions() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(GameConstants.PREFERENCES_MUSIC, playMusic);
        editor.putBoolean(GameConstants.PREFERENCES_SOUNDEFFECTS, playSoundEffects);
        editor.putBoolean(GameConstants.PREFERENCES_VIBRATE, doVibrate);
        editor.putBoolean(GameConstants.PREFERENCES_KEEPJOYSTICKVELOCITY_P1, keepJoystickVelocityP1);
        editor.putBoolean(GameConstants.PREFERENCES_KEEPJOYSTICKVELOCITY_P1, keepJoystickVelocityP2);
        editor.commit();
    }

    /**
     * Determines if the play music option is on
     *
     * @return whether the music option is on or not
     */
    public boolean isPlayMusic() {
        return playMusic;
    }

    /**
     * Sets the value of the play music option
     *
     * @param playMusic the new value of the option
     */
    public void setPlayMusic(boolean playMusic) {
        this.playMusic = playMusic;
    }

    /**
     * Determines if the sound effects option is on
     *
     * @return whether the sound effects is on or not
     */
    public boolean isPlaySoundEffects() {
        return playSoundEffects;
    }

    /**
     * Sets the value of the sound effects option
     *
     * @param playSoundEffects the new value of the option
     */
    public void setPlaySoundEffects(boolean playSoundEffects) {
        this.playSoundEffects = playSoundEffects;
    }

    /**
     * Determines if the vibrate option is on or not
     *
     * @return whether the vibrate option is on or not
     */
    public boolean isDoVibrate() {
        return doVibrate;
    }

    /**
     * Sets the value of the vibrate option
     *
     * @param doVibrate the new value of the option
     */
    public void setDoVibrate(boolean doVibrate) {
        this.doVibrate = doVibrate;
    }

    /**
     * Determines if the player one keeps the velocity after the joystick gets deactivated or not
     *
     * @return whether the option is on or not
     */
    public boolean isKeepJoystickVelocityP1() {
        return this.keepJoystickVelocityP1;
    }

    /**
     * Sets the value of the keepJoystickVelocityP1 option
     *
     * @param keepJoystickVelocityP1 the new value of the option
     */
    public void setKeepJoystickVelocityP1(boolean keepJoystickVelocityP1) {
        this.keepJoystickVelocityP1 = keepJoystickVelocityP1;
    }

    /**
     * Determines if the player two keeps the velocity after the joystick gets deactivated or not
     *
     * @return whether the option is on or not
     */
    public boolean isKeepJoystickVelocityP2() {
        return this.keepJoystickVelocityP2;
    }

    /**
     * Sets the value of the keepJoystickVelocityP1 option
     *
     * @param keepJoystickVelocityP2 the new value of the option
     */
    public void setKeepJoystickVelocityP2(boolean keepJoystickVelocityP2) {
        this.keepJoystickVelocityP2 = keepJoystickVelocityP2;
    }
}
