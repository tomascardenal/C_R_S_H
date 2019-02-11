package com.example.tomascrd.c_r_s_h.components;

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
     * Initializes a player to it's parameters
     *
     * @param playerName      The player's name
     * @param playerId        The player's id
     * @param onAttack        The player's mode
     * @param playerCollision The player's collision circle
     */
    public PlayerCrsh(String playerName, int playerId, boolean onAttack, CircleComponent playerCollision) {
        this.playerName = playerName;
        this.playerId = playerId;
        this.onAttack = onAttack;
        this.playerCollision = playerCollision;
        this.playerLifes = 3;
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
