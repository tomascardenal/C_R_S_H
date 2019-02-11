package com.example.tomascrd.c_r_s_h.components;


/**
 * Represents a power up object inside the game
 *
 * @author Tomás Cardenal López
 */
public class PowerUpCrsh extends DrawableComponent {
    /**
     * Enumerates the various types of power ups
     */
    public enum POWERUP_TYPE {
        POWERUP_1UP,
        POWERUP_3UP,
        POWERUP_TIME_REDUCTION,
        POWERUP_ONE_HIT_WEAPON,
        POWERUP_FIRE_WEAPON,
        POWERUP_BLOCK,
        POWERUP_INVINCIBLE,
        POWERUP_SLOWDOWN
    }

}
