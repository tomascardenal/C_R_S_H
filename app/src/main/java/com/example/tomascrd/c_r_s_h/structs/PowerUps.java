package com.example.tomascrd.c_r_s_h.structs;

import com.example.tomascrd.c_r_s_h.core.Utils;

/**
 * Class to manage the powerups
 *
 * @author Tomás Cardenal López
 */
public class PowerUps {
    /**
     * Enumerates the various types of power ups
     */
    public enum ePowerUp {
        POWERUP_TIMER_STOP,
        POWERUP_NO_BOUNCEBACK,
        POWERUP_INVINCIBLE,
        POWERUP_SLOW_OPPONENT,
        POWERUP_SLOW_MYSELF
    }

    /**
     * Gets a random powerup value depending on the stage of the powerUpIndex
     *
     * @param powerUpIndex the stage of the powerUpIndex
     * @return a random power up seeded by the powerUpIndex
     */
    public static ePowerUp getRandomPowerUp(int powerUpIndex) {
        int rand;
        switch (powerUpIndex) {
            case 0:
                rand = Utils.getRandom(0, 1);
                if (rand == 0) {
                    return ePowerUp.POWERUP_SLOW_OPPONENT;
                } else {
                    return ePowerUp.POWERUP_SLOW_MYSELF;
                }
            case 1:
                rand = Utils.getRandom(0, 1);
                if (rand == 0) {
                    return ePowerUp.POWERUP_NO_BOUNCEBACK;
                } else {
                    return ePowerUp.POWERUP_TIMER_STOP;
                }
            case 2:
                rand = Utils.getRandom(0, 80);
                if (rand < 20) {
                    return ePowerUp.POWERUP_SLOW_MYSELF;
                } else if (rand < 40) {
                    return ePowerUp.POWERUP_SLOW_OPPONENT;
                } else if (rand < 60) {
                    return ePowerUp.POWERUP_TIMER_STOP;
                } else {
                    return ePowerUp.POWERUP_NO_BOUNCEBACK;
                }
            case 3:
                rand = Utils.getRandom(0, 4);
                if (rand == 0) {
                    return ePowerUp.POWERUP_SLOW_MYSELF;
                } else if (rand == 1) {
                    return ePowerUp.POWERUP_SLOW_OPPONENT;
                } else if (rand == 2) {
                    return ePowerUp.POWERUP_TIMER_STOP;
                } else if (rand == 3) {
                    return ePowerUp.POWERUP_NO_BOUNCEBACK;
                } else {
                    return ePowerUp.POWERUP_INVINCIBLE;
                }
            case 4:
                rand = Utils.getRandom(0, 100);
                if (rand < 75) {
                    return ePowerUp.POWERUP_SLOW_MYSELF;
                } else {
                    return ePowerUp.POWERUP_SLOW_OPPONENT;
                }
            case 5:
            case 6:
                rand = Utils.getRandom(0, 100);
                if (rand < 15) {
                    return ePowerUp.POWERUP_SLOW_MYSELF;
                } else if (rand < 30) {
                    return ePowerUp.POWERUP_SLOW_OPPONENT;
                } else if (rand < 50) {
                    return ePowerUp.POWERUP_TIMER_STOP;
                } else if (rand < 90) {
                    return ePowerUp.POWERUP_NO_BOUNCEBACK;
                } else {
                    return ePowerUp.POWERUP_INVINCIBLE;
                }
            default:
                return null;
        }
    }
}
