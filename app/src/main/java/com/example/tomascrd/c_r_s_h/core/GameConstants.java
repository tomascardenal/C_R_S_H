package com.example.tomascrd.c_r_s_h.core;

/**
 * Constants of common use in the whole game
 *
 * @author Tomás Cardenal López
 */
public final class GameConstants {
    /**
     * FPS to run the game
     */
    public final static int FPS = 60;
    /**
     * Ticks in a second for nanoTime() function
     */
    public final static int TPS = 1000000000;
    /**
     * Time fragment to repeat
     */
    public final static int TIME_FRAGMENT = TPS / FPS;
    /**
     * Value to multiply the acceleration from the Joystick on attack mode
     */
    public final static int ACCELERATION_MULTIPLIER_ONATTACK = 8;
    /**
     * Value to multiply the acceleration from the Joystick on defense mode
     */
    public final static int ACCELERATION_MULTIPLIER_ONDEFENSE = 12;
    /**
     * Small bounceback acceleration divisor
     */
    public final static int BOUNCEBACK_SMALL_DIVISOR = 3;
    /**
     * Large bounceback acceleration
     */
    public final static int BOUNCEBACK_BIG_DIVISOR = 2;
    /**
     * Small bounceback number of cycles
     */
    public final static int BOUNCEBACK_SMALL_CYCLES = 20;
    /**
     * Large bounceback number of cycles
     */
    public final static int BOUNCEBACK_BIG_CYCLES = 40;
    /**
     * Bounceback slowdown shift value
     */
    public final static float BOUNCEBACK_SLOWDOWN_SHIFT = (float) 0.2;
    /**
     * Rows on a game screen
     */
    public final static int GAMESCREEN_ROWS = 18;
    /**
     * Columns on a game screen
     */
    public final static int GAMESCREEN_COLUMNS = 32;
    /**
     * Rows on the map area
     */
    public final static int MAPAREA_ROWS = 16;
    /**
     * Columns on the map area
     */
    public final static int MAPAREA_COLUMNS = 26;
    /**
     * Rows on a menu screen
     */
    public final static int MENUSCREEN_ROWS = 8;
    /**
     * Columns on a menu screen
     */
    public final static int MENUSCREEN_COLUMNS = 18;
    /**
     * Reference name for mapfiles
     */
    public final static String MAPFILE_NAME = "map.crsh";
    /**
     * Reference for the SharedPreferences
     */
    public final static String PREFERENCES_NAME = "options.crsh";
    /**
     * Reference for the music option
     */
    public final static String PREFERENCES_MUSIC = "music";
    /**
     * Reference for the sound effects option
     */
    public final static String PREFERENCES_SOUNDEFFECTS = "soundeffects";
    /**
     * Reference for the vibrate option
     */
    public final static String PREFERENCES_VIBRATE = "vibrate";
    /**
     * Font awesome path
     */
    public final static String FONT_AWESOME = "fa-solid-900.ttf";
    /**
     * Homespun font path
     */
    public final static String FONT_HOMESPUN = "homespun.ttf";
    /**
     * KarmaFuture font path
     */
    public final static String FONT_KARMAFUTURE = "KarmaFuture.ttf";
}
