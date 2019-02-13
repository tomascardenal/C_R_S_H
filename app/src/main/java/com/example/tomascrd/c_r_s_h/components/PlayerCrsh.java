package com.example.tomascrd.c_r_s_h.components;

import android.graphics.Color;
import android.util.Log;

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
     * Velocity saver for bounceback
     */
    private float restoreXVelocity;

    /**
     * Velocity saver for bounceback
     */
    private float restoreYVelocity;

    /**
     * Initializes a player to it's parameters, with a given CircleComponent
     *
     * @param mapCallback     Callback to this MapComponent for accessing the tiles
     * @param playerName      The player's name
     * @param playerId        The player's id
     * @param onAttack        The player's mode
     * @param playerCollision The player's collision circle
     * @see CircleComponent
     */
    public PlayerCrsh(MapComponent mapCallback, String playerName, int playerId, boolean onAttack, CircleComponent playerCollision) {
        this.mapCallback = mapCallback;
        this.playerName = playerName;
        this.playerId = playerId;
        this.onAttack = onAttack;
        this.playerCollision = playerCollision;
        this.playerLifes = 3;
        switch (playerId) {
            case 0:
                this.playerCollision.setColor(Color.RED);
                break;
            case 1:
                this.playerCollision.setColor(Color.BLUE);
                break;
            case 2:
                this.playerCollision.setColor(Color.GREEN);
        }
        this.xVelocity = 0;
        this.yVelocity = 0;
        setMapPosition();
    }

    /**
     * Initializes a player to it's parameters, indicating the coordinates and radius of the CircleComponent
     *
     * @param mapCallback Callback to this MapComponent for accessing the tiles
     * @param playerName  The player's name
     * @param playerId    The player's id
     * @param onAttack    The player's mode
     * @param yPos        The CircleComponent's yPos
     * @param xPos        The CircleComponent's yPos
     * @param radius      The CircleComponent's radius
     * @see CircleComponent
     */
    public PlayerCrsh(MapComponent mapCallback, String playerName, int playerId, boolean onAttack, float xPos, float yPos, int radius) {
        this.mapCallback = mapCallback;
        this.playerName = playerName;
        this.playerId = playerId;
        this.onAttack = onAttack;
        this.playerCollision = new CircleComponent(xPos, yPos, radius);
        this.playerLifes = 3;
        this.xVelocity = 0;
        this.yVelocity = 0;
        setMapPosition();
    }

    /**
     * Initializes a player to it's parameters, with a given CircleComponent and the indicated lives (within the accepted limits)
     *
     * @param mapCallback     Callback to this MapComponent for accessing the tiles
     * @param playerName      The player's name
     * @param playerId        The player's id
     * @param onAttack        The player's mode
     * @param playerCollision The player's collision circle
     * @param lives           The player's lives
     * @see CircleComponent
     */
    public PlayerCrsh(MapComponent mapCallback, String playerName, int playerId, boolean onAttack, CircleComponent playerCollision, int lives) {
        this(mapCallback, playerName, playerId, onAttack, playerCollision);
        this.setPlayerLifes(lives);
    }

    /**
     * Initializes a player to it's parameters, indicating the coordinates, radius of the CircleComponent and the indicated lives (within the accepted limits)
     *
     * @param mapCallback Callback to this MapComponent for accessing the tiles
     * @param playerName  The player's name
     * @param playerId    The player's id
     * @param onAttack    The player's mode
     * @param yPos        The CircleComponent's yPos
     * @param xPos        The CircleComponent's yPos
     * @param radius      The CircleComponent's radius
     * @param lives       The player's lives
     * @see CircleComponent
     */
    public PlayerCrsh(MapComponent mapCallback, String playerName, int playerId, boolean onAttack, float xPos, float yPos, int radius, int lives) {
        this(mapCallback, playerName, playerId, onAttack, xPos, yPos, radius);
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
        if (xVelocity <= 3 && xVelocity >= -3) {
            this.xVelocity = xVelocity;
        } else if (xVelocity > 3) {
            this.xVelocity = 3;
        } else if (xVelocity < -3) {
            this.xVelocity = -3;
        }
    }

    /**
     * Sets this player's yVelocity
     *
     * @param yVelocity the yVelocity to set
     */
    public void setyVelocity(float yVelocity) {
        if (yVelocity <= 3 && yVelocity >= -3) {
            this.yVelocity = yVelocity;
        } else if (yVelocity > 3) {
            this.yVelocity = 3;
        } else if (yVelocity < -3) {
            this.yVelocity = -3;
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

    public void setMapPosition() {

        double mapX = this.playerCollision.xPos - (mapCallback.screenWidth - mapCallback.xPos) / 2;
        double mapY = this.playerCollision.yPos - (mapCallback.screenHeight - mapCallback.height) / 2;
        columnPosition = (int) (mapX / this.mapCallback.getReference());
        rowPosition = (int) (mapY / this.mapCallback.getReference()) - 1;
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
        Log.i("PLAYER POSITION :", "ROW:" + rowPosition + " COLUMN:" + columnPosition);
    }

    /**
     * Moves this player's position the distance indicated on it's velocity
     */
    public void move() {
        CircleComponent xMovedCircle = new CircleComponent(this.playerCollision.xPos + xVelocity, this.playerCollision.yPos, this.playerCollision.radius);
        CircleComponent yMovedCircle = new CircleComponent(this.playerCollision.xPos, this.playerCollision.yPos + yVelocity, this.playerCollision.radius);
        TileComponent currentTile;
        if(bounceBackX){
            xVelocity = restoreXVelocity;
        }
        if(bounceBackY){
            yVelocity = restoreYVelocity;
        }
        for (int i = 0; i < surroundingTiles.length; i++) {
            currentTile = surroundingTiles[i];
            if (xMovedCircle.collision(currentTile.collisionRect)) {
                switch (currentTile.tileType) {
                    case TILE_BORDER:
                        Log.i("COLLISION", "on X");
                        xVelocity = 0;
                        break;
                    case TILE_BREAKONE:
                        currentTile.tileType = TileComponent.TILE_TYPE.TILE_PATH;
                        currentTile.setPainter();
                        restoreXVelocity = xVelocity;
                        xVelocity = (float) -0.5;
                        bounceBackX = true;
                        break;
                    case TILE_BREAKTWO:
                        currentTile.tileType = TileComponent.TILE_TYPE.TILE_BREAKONE;
                        currentTile.setPainter();
                        restoreXVelocity = xVelocity;
                        xVelocity = (float) -1;
                        bounceBackX = true;
                        break;
                    default:
                        bounceBackX = false;
                }
            }
            if (yMovedCircle.collision(currentTile.collisionRect)) {
                switch (currentTile.tileType) {
                    case TILE_BORDER:
                        Log.i("COLLISION", "on Y");
                        yVelocity = 0;
                        break;
                    case TILE_BREAKONE:
                        currentTile.tileType = TileComponent.TILE_TYPE.TILE_PATH;
                        currentTile.setPainter();
                        restoreYVelocity = yVelocity;
                        yVelocity = (float) -0.5;
                        bounceBackY = true;
                        break;
                    case TILE_BREAKTWO:
                        currentTile.tileType = TileComponent.TILE_TYPE.TILE_BREAKONE;
                        currentTile.setPainter();
                        restoreYVelocity = yVelocity;
                        yVelocity = (float) -1;
                        bounceBackY = true;
                        break;
                    default:
                        bounceBackY = false;
                }
            }
        }
        if (xVelocity != 0 || yVelocity != 0) {
            playerCollision.move(xVelocity, yVelocity);
        }
        setMapPosition();
    }
}
