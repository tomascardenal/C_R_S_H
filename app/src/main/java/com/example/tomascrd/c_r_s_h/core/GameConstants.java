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
    public static int TIME_FRAGMENT = TPS/FPS;
    /**
     * Columns on a game screen
     */
    public static int GAMESCREEN_COLUMNS = 32;
    /**
     * Rows on a game screen
     */
    public static int GAMESCREEN_ROWS = 18;
    /**
     * Columns on a menu screen
     */
    public static int MENUSCREEN_COLUMNS = 18;
    /**
     * Rows on a menu screen
     */
    public static int MENUSCREEN_ROWS = 8;
}
