package com.example.tomascrd.c_r_s_h.components;

import android.graphics.PointF;

import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.Utils;
import com.example.tomascrd.c_r_s_h.scenes.MainGameScene;

/**
 * Represents a computer player for the game C_R_S_H
 *
 * @author Tomás Cardenal López
 */
public class PlayerComCrsh extends PlayerCrsh {

    /**
     * Current movement cycles
     */
    private int currentCycles;

    /**
     * Initializes a COM player to it's parameters, with a given CircleComponent
     *
     * @param gameCallback    Callback to the scene calling this player
     * @param mapCallback     Callback to the MapComponent for accessing the tiles
     * @param onAttack        The player's mode
     * @param playerCollision The player's collision circle
     * @see CircleComponent
     */
    public PlayerComCrsh(MainGameScene gameCallback, MapComponent mapCallback, boolean onAttack, CircleComponent playerCollision) {
        super(gameCallback, mapCallback, "COM", 0, onAttack, playerCollision);
        this.currentCycles = 0;
    }

    /**
     * Determines this COM player movements, and then calls the move function on PlayerCrsh to execute the actual movement
     */
    @Override
    public void move() {
        //If theres no more cycles to run on the previous velocity
        if (currentCycles == 0) {
            //Draw the human position for a reference
            PointF currentHumanPosition = gameCallback.getHumanPosition();
            //Draw a new random number of cycles to move
            currentCycles = Utils.getRandom(GameConstants.COM_MIN_CYCLES, GameConstants.COM_MAX_CYCLES);
            //Draw new random velocities
            this.xVelocity = Utils.getRandom(GameConstants.COM_MIN_VELOCITY, GameConstants.COM_MAX_VELOCITY);
            this.yVelocity = Utils.getRandom(GameConstants.COM_MIN_VELOCITY, GameConstants.COM_MAX_VELOCITY);

            if (onAttack) {
                //If attacking, and the human is on a lesser x, go on negative x velocity, same with the y coordinates
                if (currentHumanPosition.x < this.xPos) {
                    this.reverseXVelocity();
                }
                if (currentHumanPosition.y < this.yPos) {
                    this.reverseYVelocity();
                }
            } else {
                //If defending, and the human is on a greater x, go on negative x velocity, same with the y coordinates
                if (currentHumanPosition.x > this.xPos) {
                    this.reverseXVelocity();
                }
                if (currentHumanPosition.y > this.yPos) {
                    this.reverseYVelocity();
                }
            }
        } else if (currentCycles > 0) {
            //If going against borders, reverse the velocity
            if (againstBorderXPositive) {
                this.xVelocity = Utils.getRandom(GameConstants.COM_MIN_VELOCITY, GameConstants.COM_MAX_VELOCITY);
                this.reverseXVelocity();
            }
            if (againstBorderXNegative) {
                this.xVelocity = Utils.getRandom(GameConstants.COM_MIN_VELOCITY, GameConstants.COM_MAX_VELOCITY);
            }
            if (againstBorderYPositive) {
                this.yVelocity = Utils.getRandom(GameConstants.COM_MIN_VELOCITY, GameConstants.COM_MAX_VELOCITY);
                this.reverseYVelocity();
            }
            if (againstBorderYNegative) {
                this.yVelocity = Utils.getRandom(GameConstants.COM_MIN_VELOCITY, GameConstants.COM_MAX_VELOCITY);
            }
            //If there's still velocity, decrement a cycle, else, go back to zero and start again
            if (this.xVelocity != 0 || this.yVelocity != 0) {
                currentCycles--;
            } else {
                currentCycles = 0;
            }
        }
        super.move();
    }
}
