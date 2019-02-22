package com.example.tomascrd.c_r_s_h.components;

import android.graphics.PointF;

import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.Utils;
import com.example.tomascrd.c_r_s_h.scenes.MainGameScene;

import java.util.Random;

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
     * @param playerName      The player's name
     * @param playerId        The player's id
     * @param onAttack        The player's mode
     * @param playerCollision The player's collision circle
     * @see CircleComponent
     */
    public PlayerComCrsh(MainGameScene gameCallback, MapComponent mapCallback, String playerName, int playerId, boolean onAttack, CircleComponent playerCollision) {
        super(gameCallback, mapCallback, playerName, playerId, onAttack, playerCollision);
    }

    /**
     * Initializes a COM player to it's parameters, indicating the coordinates and radius of the CircleComponent
     *
     * @param gameCallback Callback to the scene calling this player
     * @param mapCallback  Callback to this MapComponent for accessing the tiles
     * @param playerName   The player's name
     * @param playerId     The player's id
     * @param onAttack     The player's mode
     * @param xPos         The CircleComponent's yPos
     * @param yPos         The CircleComponent's yPos
     * @param radius       The CircleComponent's radius
     * @see CircleComponent
     */
    public PlayerComCrsh(MainGameScene gameCallback, MapComponent mapCallback, String playerName, int playerId, boolean onAttack, float xPos, float yPos, int radius) {
        super(gameCallback, mapCallback, playerName, playerId, onAttack, xPos, yPos, radius);
    }

    /**
     * Initializes a player to it's parameters, with a given CircleComponent and the indicated lives (within the accepted limits)
     *
     * @param gameCallback    Callback to the scene calling this player
     * @param mapCallback     Callback to this MapComponent for accessing the tiles
     * @param playerName      The player's name
     * @param playerId        The player's id
     * @param onAttack        The player's mode
     * @param playerCollision The player's collision circle
     * @param lives           The player's lives
     * @see CircleComponent
     */
    public PlayerComCrsh(MainGameScene gameCallback, MapComponent mapCallback, String playerName, int playerId, boolean onAttack, CircleComponent playerCollision, int lives) {
        super(gameCallback, mapCallback, playerName, playerId, onAttack, playerCollision, lives);
    }

    /**
     * Initializes a player to it's parameters, indicating the coordinates, radius of the CircleComponent and the indicated lives (within the accepted limits)
     *
     * @param gameCallback Callback to the scene calling this player
     * @param mapCallback  Callback to this MapComponent for accessing the tiles
     * @param playerName   The player's name
     * @param playerId     The player's id
     * @param onAttack     The player's mode
     * @param xPos         The CircleComponent's yPos
     * @param yPos         The CircleComponent's yPos
     * @param radius       The CircleComponent's radius
     * @param lives        The player's lives
     * @see CircleComponent
     */
    public PlayerComCrsh(MainGameScene gameCallback, MapComponent mapCallback, String playerName, int playerId, boolean onAttack, float xPos, float yPos, int radius, int lives) {
        super(gameCallback, mapCallback, playerName, playerId, onAttack, xPos, yPos, radius, lives);
    }

    /**
     * Determines this COM player movements, and then calls the move function on PlayerCrsh to execute the actual movement
     */
    @Override
    public void move() {
        if (currentCycles == 0) {
            PointF currentHumanPosition = gameCallback.getHumanPosition();
            currentCycles = Utils.getRandom(GameConstants.COM_MIN_CYCLES, GameConstants.COM_MAX_CYCLES);
            this.setxVelocity(Utils.getRandom(GameConstants.COM_MIN_CYCLES, GameConstants.COM_MAX_CYCLES));
            this.setyVelocity(Utils.getRandom(GameConstants.COM_MIN_CYCLES, GameConstants.COM_MAX_CYCLES));
            if (onAttack) {
                if (currentHumanPosition.x < this.xPos) {
                    this.reverseXVelocity();
                }
                if (currentHumanPosition.y < this.yPos) {
                    this.reverseYVelocity();
                }
            } else {
                if (currentHumanPosition.x > this.xPos) {
                    this.reverseXVelocity();
                }
                if (currentHumanPosition.y > this.yPos) {
                    this.reverseYVelocity();
                }
            }
        } else {
            currentCycles--;
        }
        super.move();
    }
}
