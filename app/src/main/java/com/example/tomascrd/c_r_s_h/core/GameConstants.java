package com.example.tomascrd.c_r_s_h.core;

import com.example.tomascrd.c_r_s_h.components.TileComponent;

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
     * COM movement algorithm maximum cycles
     */
    public final static int COM_MAX_CYCLES = 180;
    /**
     * COM movement algorithm minimum cycles
     */
    public final static int COM_MIN_CYCLES = 30;
    /**
     * Maximum COM velocity
     */
    public final static int COM_MAX_VELOCITY = 10;
    /**
     * Minimum COM velocity
     */
    public final static int COM_MIN_VELOCITY = 5;
    /**
     * Accelerometer min read value
     */
    public final static float ACCELEROMETER_MIN_THRESHOLD = 0.1f;
    /**
     * Accelerometer max read value
     */
    public final static float ACCELEROMETER_MAX_THRESHOLD = 1.0f;
    /**
     * Accelerometer mode multiplier
     */
    public final static float ACCELEROMETER_MULTIPLIER = 12;
    /**
     * Value to multiply the acceleration from the Joystick on attack mode
     */
    public final static int ACCELERATION_MULTIPLIER_ONATTACK = 10;
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
    public final static int BOUNCEBACK_SMALL_CYCLES = 6;
    /**
     * Large bounceback number of cycles
     */
    public final static int BOUNCEBACK_BIG_CYCLES = 12;
    /**
     * Take hit animation number of cycles
     */
    public final static int TAKEHIT_CYCLES = 40;
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
     * Reference name for the map list
     */
    public final static String MAPLIST_FILE_NAME = "maplist.crsh";
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
     * Reference for the keepJoystickVelocityP1 option
     */
    public final static String PREFERENCES_KEEPJOYSTICKVELOCITY_P1 = "joystickVelocity1";
    /**
     * Reference for the keepJoystickVelocityP2 option
     */
    public final static String PREFERENCES_KEEPJOYSTICKVELOCITY_P2 = "joystickVelocity2";
    /**
     * Reference for the timerSpeed option
     */
    public final static String PREFERENCES_TIMERSPEED = "timerSpeed";
    /**
     * Reference for the number of saved maps
     */
    public final static String PREFERENCES_MAPCOUNT = "mapCount";
    /**
     * Array containing the values of the tile_types
     */
    public final static TileComponent.TILE_TYPE[] TILE_TYPES = TileComponent.TILE_TYPE.values();
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
    /**
     * Link to Fontawesome.com
     */
    public final static String LINK_FONTAWESOME = "https://fontawesome.com/";
    /**
     * Link to Homespun font source
     */
    public final static String LINK_HOMESPUN = "https://www.1001freefonts.com/homespun.font";
    /**
     * Link to KarmaFuture font source
     */
    public final static String LINK_KARMAFUTURE = "https://www.dafont.com/karma-future.font";
}
