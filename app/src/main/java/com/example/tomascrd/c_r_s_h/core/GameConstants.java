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
    public static int FPS = 60;
    /**
     * Ticks in a second for nanoTime() function
     */
    public static int TPS = 1000000000;
    /**
     * Time fragment to repeat
     */
    public static int TIME_FRAGMENT = TPS / FPS;
    /**
     * Rows on a game screen
     */
    public static int GAMESCREEN_ROWS = 18;
    /**
     * Columns on a game screen
     */
    public static int GAMESCREEN_COLUMNS = 32;
    /**
     * Rows on the map area
     */
    public static int MAPAREA_ROWS = 16;
    /**
     * Columns on the map area
     */
    public static int MAPAREA_COLUMNS = 26;
    /**
     * Rows on a menu screen
     */
    public static int MENUSCREEN_ROWS = 8;
    /**
     * Columns on a menu screen
     */
    public static int MENUSCREEN_COLUMNS = 18;
    /**
     * Reference name for mapfiles
     */
    public static String MAPFILE_NAME = "map.crsh";
}
