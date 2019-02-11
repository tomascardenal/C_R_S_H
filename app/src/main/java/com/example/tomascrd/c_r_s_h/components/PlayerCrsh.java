package com.example.tomascrd.c_r_s_h.components;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Represents a player in the game
 *
 * @author Tomás Cardenal López
 */
public class PlayerCrsh {

    /**
     * The player's name
     */
    public String playerName;
    /**
     * The player's id (either 0 or 1)
     */
    public int playerId;
    /**
     * The player mode (true for attacking, false for defense)
     */
    public boolean onAttack;
    /**
     * The player's collision circle
     */
    public CircleComponent playerCollision;
    /**
     * The player's lifes
     */
    private int playerLifes;


    /**
     * Initializes a player to it's parameters, with a given CircleComponent
     *
     * @param playerName      The player's name
     * @param playerId        The player's id
     * @param onAttack        The player's mode
     * @param playerCollision The player's collision circle
     * @see CircleComponent
     */
    public PlayerCrsh(String playerName, int playerId, boolean onAttack, CircleComponent playerCollision) {
        this.playerName = playerName;
        this.playerId = playerId;
        this.onAttack = onAttack;
        this.playerCollision = playerCollision;
        this.playerLifes = 3;
    }

    /**
     * Initializes a player to it's parameters, indicating the coordinates and radius of the CircleComponent
     *
     * @param playerName The player's name
     * @param playerId   The player's id
     * @param onAttack   The player's mode
     * @param yPos       The CircleComponent's yPos
     * @param xPos       The CircleComponent's yPos
     * @param radius     The CircleComponent's radius
     * @see CircleComponent
     */
    public PlayerCrsh(String playerName, int playerId, boolean onAttack, float xPos, float yPos, int radius) {
        this.playerName = playerName;
        this.playerId = playerId;
        this.onAttack = onAttack;
        this.playerCollision = new CircleComponent(xPos, yPos, radius);
        this.playerLifes = 3;
    }

    /**
     * Initializes a player to it's parameters, with a given CircleComponent and the indicated lives (within the accepted limits)
     *
     * @param playerName      The player's name
     * @param playerId        The player's id
     * @param onAttack        The player's mode
     * @param playerCollision The player's collision circle
     * @param lives           The player's lives
     * @see CircleComponent
     */
    public PlayerCrsh(String playerName, int playerId, boolean onAttack, CircleComponent playerCollision, int lives) {
        this(playerName, playerId, onAttack, playerCollision);
        this.setPlayerLifes(lives);
    }

    /**
     * Initializes a player to it's parameters, indicating the coordinates, radius of the CircleComponent and the indicated lives (within the accepted limits)
     *
     * @param playerName The player's name
     * @param playerId   The player's id
     * @param onAttack   The player's mode
     * @param yPos       The CircleComponent's yPos
     * @param xPos       The CircleComponent's yPos
     * @param radius     The CircleComponent's radius
     * @param lives      The player's lives
     * @see CircleComponent
     */
    public PlayerCrsh(String playerName, int playerId, boolean onAttack, float xPos, float yPos, int radius, int lives) {
        this(playerName, playerId, onAttack, xPos, yPos, radius);
        this.setPlayerLifes(lives);
    }

    /**
     * Gets this player's number of lifes
     *
     * @return the player's number of lifes
     */
    public int getPlayerLifes() {
        return this.playerLifes;
    }

    /**
     * Sets the number of lifes, always staying between 3 and 9
     *
     * @param playerLifes the player lifes to set
     */
    public void setPlayerLifes(int playerLifes) {
        if (playerLifes < 3) {
            this.playerLifes = 3;
        } else if (playerLifes > 9) {
            this.playerLifes = 9;
        } else {
            this.playerLifes = playerLifes;
        }
    }

    /**
     * Removes, if possible, a life from this player
     */
    public void removeALife() {
        if (this.playerLifes > 0) {
            this.playerLifes--;
        }
    }

    /**
     * Adds, if possible, a life to this player
     */
    public void addALife() {
        if (this.playerLifes < 9) {
            this.playerLifes++;
        }
    }
}
