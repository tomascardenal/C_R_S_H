package com.example.tomascrd.c_r_s_h.components;

import android.graphics.Color;
import android.util.Log;

import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.scenes.MainGameScene;

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
     * The player's id (0 for com player, 1 for player one, 2 for player two)
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
     * This player's x axis velocity
     */
    private float xVelocity;
    /**
     * This player's y axis velocity
     */
    private float yVelocity;
    /**
     * Callback to access the map
     */
    private MapComponent mapCallback;
    /**
     * Callback to access the game scene
     */
    private MainGameScene gameCallback;
    /**
     * The player columnPosition
     */
    private int columnPosition;
    /**
     * The player rowPosition
     */
    private int rowPosition;
    /**
     * The surrounding tiles
     */
    private TileComponent[] surroundingTiles;
    /**
     * Determines if this player bounced back on X axis
     */
    private boolean bounceBackX;
    /**
     * Determines if this player bounced back on Y axis
     */
    private boolean bounceBackY;
    /**
     * Accelleration multiplier for the joystick
     */
    private int joystickMultiplier;

    /**
     * Initializes a player to it's parameters, with a given CircleComponent
     *
     * @param gameCallback    Callback to the scene calling this player
     * @param mapCallback     Callback to the MapComponent for accessing the tiles
     * @param playerName      The player's name
     * @param playerId        The player's id
     * @param onAttack        The player's mode
     * @param playerCollision The player's collision circle
     * @see CircleComponent
     */
    public PlayerCrsh(MainGameScene gameCallback, MapComponent mapCallback, String playerName, int playerId, boolean onAttack, CircleComponent playerCollision) {
        this.gameCallback = gameCallback;
        this.mapCallback = mapCallback;
        this.playerName = playerName;
        this.playerId = playerId;
        this.onAttack = onAttack;
        this.playerCollision = playerCollision;
        this.playerLifes = 3;
        switch (playerId) {
            case 0:
                this.playerCollision.setColor(Color.MAGENTA);
                break;
            case 1:
                this.playerCollision.setColor(Color.BLUE);
                break;
            case 2:
                this.playerCollision.setColor(Color.RED);
        }
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.setJoystickMultiplier();
        this.setMapPosition();
    }

    /**
     * Initializes a player to it's parameters, indicating the coordinates and radius of the CircleComponent
     *
     * @param gameCallback Callback to the scene calling this player
     * @param mapCallback  Callback to this MapComponent for accessing the tiles
     * @param playerName   The player's name
     * @param playerId     The player's id
     * @param onAttack     The player's mode
     * @param yPos         The CircleComponent's yPos
     * @param xPos         The CircleComponent's yPos
     * @param radius       The CircleComponent's radius
     * @see CircleComponent
     */
    public PlayerCrsh(MainGameScene gameCallback, MapComponent mapCallback, String playerName, int playerId, boolean onAttack, float xPos, float yPos, int radius) {
        this.gameCallback = gameCallback;
        this.mapCallback = mapCallback;
        this.playerName = playerName;
        this.playerId = playerId;
        this.onAttack = onAttack;
        this.playerCollision = new CircleComponent(xPos, yPos, radius);
        this.playerLifes = 3;
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.setJoystickMultiplier();
        this.setMapPosition();
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
    public PlayerCrsh(MainGameScene gameCallback, MapComponent mapCallback, String playerName, int playerId, boolean onAttack, CircleComponent playerCollision, int lives) {
        this(gameCallback, mapCallback, playerName, playerId, onAttack, playerCollision);
        this.setPlayerLifes(lives);
    }

    /**
     * Initializes a player to it's parameters, indicating the coordinates, radius of the CircleComponent and the indicated lives (within the accepted limits)
     *
     * @param gameCallback Callback to the scene calling this player
     * @param mapCallback  Callback to this MapComponent for accessing the tiles
     * @param playerName   The player's name
     * @param playerId     The player's id
     * @param onAttack     The player's mode
     * @param yPos         The CircleComponent's yPos
     * @param xPos         The CircleComponent's yPos
     * @param radius       The CircleComponent's radius
     * @param lives        The player's lives
     * @see CircleComponent
     */
    public PlayerCrsh(MainGameScene gameCallback, MapComponent mapCallback, String playerName, int playerId, boolean onAttack, float xPos, float yPos, int radius, int lives) {
        this(gameCallback, mapCallback, playerName, playerId, onAttack, xPos, yPos, radius);
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

    /**
     * Sets this player's velocity on both axis
     *
     * @param xVelocity the xVelocity to set
     * @param yVelocity the yVelocity to set
     */
    public void setVelocity(float xVelocity, float yVelocity) {
        setxVelocity(xVelocity);
        setyVelocity(yVelocity);
    }

    /**
     * Sets this player's xVelocity
     *
     * @param xVelocity the xVelocity to set
     */
    public void setxVelocity(float xVelocity) {
        if (xVelocity <= GameConstants.ACCELERATION_MULTIPLIER_ONDEFENSE && xVelocity >= GameConstants.ACCELERATION_MULTIPLIER_ONDEFENSE * -1) {
            this.xVelocity = xVelocity;
        } else if (xVelocity > GameConstants.ACCELERATION_MULTIPLIER_ONDEFENSE) {
            this.xVelocity = GameConstants.ACCELERATION_MULTIPLIER_ONDEFENSE;
        } else if (xVelocity < GameConstants.ACCELERATION_MULTIPLIER_ONDEFENSE * -1) {
            this.xVelocity = GameConstants.ACCELERATION_MULTIPLIER_ONDEFENSE * 1;
        }
    }

    /**
     * Sets this player's yVelocity
     *
     * @param yVelocity the yVelocity to set
     */
    public void setyVelocity(float yVelocity) {
        if (yVelocity <= GameConstants.ACCELERATION_MULTIPLIER_ONDEFENSE && yVelocity >= GameConstants.ACCELERATION_MULTIPLIER_ONDEFENSE * -1) {
            this.yVelocity = yVelocity;
        } else if (yVelocity > GameConstants.ACCELERATION_MULTIPLIER_ONDEFENSE) {
            this.yVelocity = GameConstants.ACCELERATION_MULTIPLIER_ONDEFENSE;
        } else if (yVelocity < GameConstants.ACCELERATION_MULTIPLIER_ONDEFENSE * -1) {
            this.yVelocity = GameConstants.ACCELERATION_MULTIPLIER_ONDEFENSE * 1;
        }
    }

    /**
     * Reverses this player's xVelocity
     */
    public void reverseXVelocity() {
        this.xVelocity = this.xVelocity * -1;
    }

    /**
     * Reverses this player's yVelocity
     */
    public void reverseYVelocity() {
        this.yVelocity = this.yVelocity * -1;
    }

    /**
     * Sets this player's position in the tiles of the map, and takes an array of the surrounding tiles
     */
    public void setMapPosition() {
        double mapX = this.playerCollision.xPos - (mapCallback.screenWidth - mapCallback.xPos) / 2;
        double mapY = this.playerCollision.yPos - (mapCallback.screenHeight - mapCallback.height) / 2;
        boolean reposition = false;
        columnPosition = (int) (mapX / this.mapCallback.getReference());
        rowPosition = (int) (mapY / this.mapCallback.getReference()) - 1;
        //Checking for out of bounds positions. Border tiles are ALWAYS border tiles
        if (columnPosition <= 0) {
            columnPosition = 1;
            reposition = true;
        } else if (columnPosition >= (GameConstants.MAPAREA_COLUMNS-1)) {
            columnPosition = GameConstants.MAPAREA_COLUMNS-2;
            reposition = true;
        }
        if (rowPosition <= 0) {
            rowPosition = 1;
            reposition = true;
        } else if (rowPosition >= (GameConstants.MAPAREA_ROWS-1))
        {
            rowPosition = GameConstants.MAPAREA_ROWS-2;
            reposition = true;
        }
        TileComponent[] surroundingTiles = {
                mapCallback.tileArray[rowPosition - 1][columnPosition - 1],
                mapCallback.tileArray[rowPosition - 1][columnPosition],
                mapCallback.tileArray[rowPosition - 1][columnPosition + 1],
                mapCallback.tileArray[rowPosition][columnPosition - 1],
                mapCallback.tileArray[rowPosition][columnPosition + 1],
                mapCallback.tileArray[rowPosition + 1][columnPosition - 1],
                mapCallback.tileArray[rowPosition + 1][columnPosition],
                mapCallback.tileArray[rowPosition + 1][columnPosition + 1]
        };
        this.surroundingTiles = surroundingTiles;
        if(reposition){
            TileComponent reposTile = mapCallback.tileArray[rowPosition][columnPosition];
            this.playerCollision.resetPosition(reposTile.collisionRect.exactCenterX(),reposTile.collisionRect.exactCenterY());
        }
        Log.i("PLAYER "+this.playerId+" GRID POSITION ", "ROW:" + rowPosition + " COLUMN:" + columnPosition);
    }

    /**
     * Moves this player's position the distance indicated on it's velocity
     */
    public void move() {
        CircleComponent xMovedCircle = new CircleComponent(this.playerCollision.xPos + xVelocity, this.playerCollision.yPos, this.playerCollision.radius);
        CircleComponent yMovedCircle = new CircleComponent(this.playerCollision.xPos, this.playerCollision.yPos + yVelocity, this.playerCollision.radius);
        TileComponent currentTile;
        bounceBackX = false;
        bounceBackY = false;

        for (int i = 0; i < surroundingTiles.length; i++) {
            currentTile = surroundingTiles[i];
            if (xMovedCircle.collision(currentTile.collisionRect)) {
                switch (currentTile.tileType) {
                    case TILE_BORDER:
                        Log.i("COLLISION", "BORDER TILE on X");
                        xVelocity = 0;
                        break;
                    case TILE_BREAKONE:
                        Log.i("COLLISION", "BREAKONE TILE on X");
                        currentTile.tileType = TileComponent.TILE_TYPE.TILE_PATH;
                        currentTile.setPainter();
                        bounceBackX = true;
                        playerCollision.move(xVelocity > 0 ? (float) -2.5 : (float) 2.5, 0);
                        break;
                    case TILE_BREAKTWO:
                        Log.i("COLLISION", "BREAKTWO TILE on Y");
                        currentTile.tileType = TileComponent.TILE_TYPE.TILE_BREAKONE;
                        currentTile.setPainter();
                        bounceBackX = true;
                        playerCollision.move(xVelocity > 0 ? (float) -5 : (float) 5, 0);
                        break;
                }
            }
            if (yMovedCircle.collision(currentTile.collisionRect)) {
                switch (currentTile.tileType) {
                    case TILE_BORDER:
                        Log.i("COLLISION", "BORDER TILE on Y");
                        yVelocity = 0;
                        break;
                    case TILE_BREAKONE:
                        Log.i("COLLISION", "BREAKONE TILE on Y");
                        currentTile.tileType = TileComponent.TILE_TYPE.TILE_PATH;
                        currentTile.setPainter();
                        bounceBackY = true;
                        playerCollision.move(0, yVelocity > 0 ? (float) -2.5 : (float) 2.5);
                        break;
                    case TILE_BREAKTWO:
                        Log.i("COLLISION", "BREAKTWO TILE on Y");
                        currentTile.tileType = TileComponent.TILE_TYPE.TILE_BREAKONE;
                        currentTile.setPainter();
                        bounceBackY = true;
                        playerCollision.move(0, yVelocity > 0 ? (float) -5 : (float) 5);
                        move();
                        break;
                }
            }
        }
        if (bounceBackX || bounceBackY) {
            gameCallback.doShortVibration();
        }
        if ((xVelocity != 0 || yVelocity != 0) && (!bounceBackX || !bounceBackY)) {
            playerCollision.move(xVelocity, yVelocity);
        }
        setMapPosition();
    }


    /**
     * Determines if this player is bouncing back
     *
     * @return whether the player is bounding back
     */
    public boolean onBounceBack() {
        return bounceBackX || bounceBackY;
    }

    /**
     * Gets the value of the joystick acceleration multiplier
     *
     * @return the current joystick acceleration multiplier
     */
    public int getJoystickMultiplier() {
        return joystickMultiplier;
    }

    /**
     * Sets the value of the joystick acceleration multiplier based on the GameConstants
     */
    public void setJoystickMultiplier() {
        this.joystickMultiplier = this.onAttack ? GameConstants.ACCELERATION_MULTIPLIER_ONATTACK : GameConstants.ACCELERATION_MULTIPLIER_ONDEFENSE;
    }
}
