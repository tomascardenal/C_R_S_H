package com.example.tomascrd.c_r_s_h.components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.Log;

import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.scenes.MainGameScene;

/**
 * Represents a player in the game
 *
 * @author Tomás Cardenal López
 */
public class PlayerCrsh extends DrawableComponent {

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
    private boolean onAttack;
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
     * This player's spawning point
     */
    private PointF spawnPoint;
    /**
     * This player's start radius
     */
    private int startRadius;
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
     * Determines if this player bounced back with a small bounce
     */
    private boolean bounceBackSmall;
    /**
     * Determines if this player bounced back with a big bounce
     */
    private boolean bounceBackBig;
    /**
     * The current bounceBackSmall cycle, if on bounceBackSmall
     */
    private int bounceBackCycle;
    /**
     * Accelleration multiplier for the joystick
     */
    private int joystickMultiplier;
    /**
     * Triggers the hit animation and makes the player immune
     */
    private boolean takingHit;
    /**
     * Counter for the hit animation
     */
    private int takehitCounter;

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
        //Initialize variables
        this.gameCallback = gameCallback;
        this.mapCallback = mapCallback;
        this.playerName = playerName;
        this.playerId = playerId;
        this.setOnAttack(onAttack);
        this.playerCollision = playerCollision;
        this.spawnPoint = new PointF(playerCollision.xPos, playerCollision.yPos);
        this.startRadius = this.playerCollision.radius;
        this.playerLifes = 3;
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.bounceBackCycle = 0;
        this.bounceBackBig = false;
        this.bounceBackSmall = false;
        this.takingHit = false;
        this.takehitCounter = 0;

        //Set the color of the player depending of it's ID
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
        //Set the joystick multiplier and position on the map
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
        this(gameCallback, mapCallback, playerName, playerId, onAttack, new CircleComponent(xPos, yPos, radius));
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

    public void respawn() {
        this.playerLifes = 3;
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.bounceBackCycle = 0;
        this.bounceBackBig = false;
        this.bounceBackSmall = false;
        this.takingHit = false;
        this.takehitCounter = 0;
        this.playerCollision.resetPosition(this.spawnPoint.x, this.spawnPoint.y);
        this.playerCollision.radius = startRadius;
        setJoystickMultiplier();
        setMapPosition();
    }

    /**
     * Draws this player's components on screen
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        if (playerLifes > 0) {
            if (takingHit && takehitCounter < GameConstants.TAKEHIT_CYCLES) {
                takehitCounter++;
                if (takehitCounter % 2 == 0) {
                    playerCollision.setDrawingAlpha(40);
                } else {
                    playerCollision.setDrawingAlpha(255);
                }
            } else if (takingHit && takehitCounter >= GameConstants.TAKEHIT_CYCLES) {
                takehitCounter = 0;
                playerCollision.setDrawingAlpha(255);
                takingHit = false;
            }
        } else if (playerLifes == 0 && playerCollision.radius > 0) {
            playerCollision.radius -= 0.2;
            takingHit = true;
        } else if (playerLifes == 0 && playerCollision.radius <= 0) {
            respawn();
        }
        playerCollision.draw(c);
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
            this.xVelocity = GameConstants.ACCELERATION_MULTIPLIER_ONDEFENSE * -1;
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
            this.yVelocity = GameConstants.ACCELERATION_MULTIPLIER_ONDEFENSE * -1;
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
        //Calculate the position inside of the map
        double mapX = this.playerCollision.xPos - (mapCallback.screenWidth - mapCallback.xPos) / 2;
        double mapY = this.playerCollision.yPos - (mapCallback.screenHeight - mapCallback.height) / 2;
        //Deduce the column and row position from the mapX and mapY coordinates
        columnPosition = (int) (mapX / this.mapCallback.getReference());
        rowPosition = (int) (mapY / this.mapCallback.getReference()) - 1;
        //Indicates if it should reposition in the end of this function
        boolean reposition = false;
        //Checking for out of bounds positions. Border tiles are ALWAYS border tiles
        if (columnPosition <= 0) {
            columnPosition = 1;
            reposition = true;
        } else if (columnPosition >= (GameConstants.MAPAREA_COLUMNS - 1)) {
            columnPosition = GameConstants.MAPAREA_COLUMNS - 2;
            reposition = true;
        }
        if (rowPosition <= 0) {
            rowPosition = 1;
            reposition = true;
        } else if (rowPosition >= (GameConstants.MAPAREA_ROWS - 1)) {
            rowPosition = GameConstants.MAPAREA_ROWS - 2;
            reposition = true;
        }
        //Get the surrounding Tiles and assign them
        TileComponent[] surroundingTiles = {
                mapCallback.tileArray[rowPosition - 1][columnPosition - 1], //Tile on upper left side
                mapCallback.tileArray[rowPosition - 1][columnPosition],     //Tile on top side
                mapCallback.tileArray[rowPosition - 1][columnPosition + 1], //Tile on upper right side
                mapCallback.tileArray[rowPosition][columnPosition - 1],     //Tile on left side
                mapCallback.tileArray[rowPosition][columnPosition + 1],     //Tile on right side
                mapCallback.tileArray[rowPosition + 1][columnPosition - 1], //Tile on bottom left side
                mapCallback.tileArray[rowPosition + 1][columnPosition],     //Tile on bottom side
                mapCallback.tileArray[rowPosition + 1][columnPosition + 1]  //Tile on bottom right side
        };
        this.surroundingTiles = surroundingTiles;
        //Reposition the player if it went out of bounds
        if (reposition) {
            TileComponent reposTile = mapCallback.tileArray[rowPosition][columnPosition];
            this.playerCollision.resetPosition(reposTile.collisionRect.exactCenterX(), reposTile.collisionRect.exactCenterY());
        }
        Log.i("PLAYER " + this.playerId + " GRID POSITION ", "ROW:" + rowPosition + " COLUMN:" + columnPosition);
    }

    /**
     * Moves this player's position the distance indicated on it's velocity
     */
    public void move() {
        //Checking collisions
        checkTileCollisions();
        checkPlayerCollision();
        //If velocity is not 0 on both edges and there's no bounceback at all
        if ((xVelocity != 0 || yVelocity != 0) && (!bounceBackSmall) && (!bounceBackBig)) {
            playerCollision.move(xVelocity, yVelocity);
            //If there's bounceback
        } else if (bounceBackSmall || bounceBackBig) {
            //Cycles for the bounceback
            int cycles = bounceBackSmall ? GameConstants.BOUNCEBACK_SMALL_CYCLES : GameConstants.BOUNCEBACK_BIG_CYCLES;
            //If it's the first cycle, vibrate
            if (bounceBackCycle == 0) {
                gameCallback.doShortVibration();
                bounceBackCycle++;
                //If it's still cycling
            } else if (bounceBackCycle < cycles) {
                bounceBackCycle++;
                //Start slowdown
                if (bounceBackCycle > cycles / 2) {
                    if (this.xVelocity - GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT > 0) {
                        this.xVelocity -= GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT;
                    }
                    if (this.yVelocity - GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT > 0) {
                        this.yVelocity -= GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT;
                    }
                } else if (this.xVelocity - GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT <= 0 && this.yVelocity - GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT <= 0) {
                    bounceBackCycle = cycles;
                }
                //Ending slowdown and bounceback
            } else {
                boolean stopX = false;
                boolean stopY = false;
                if (this.xVelocity - GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT > 0) {
                    this.xVelocity -= GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT;
                } else {
                    stopX = true;
                }
                if (this.yVelocity - GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT > 0) {
                    this.yVelocity -= GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT;
                } else {
                    stopY = true;
                }
                if (stopX && stopY) {
                    bounceBackCycle = 0;
                    bounceBackBig = false;
                    bounceBackSmall = false;
                    this.xVelocity = 0;
                    this.yVelocity = 0;
                }
            }
        }
        setMapPosition();
    }

    /**
     * Checks for tile collisions
     */
    private void checkTileCollisions() {
        //Declaring current tile
        TileComponent currentTile;

        //The circle moved in both components
        CircleComponent xMovedCircle = new CircleComponent(this.playerCollision.xPos + xVelocity, this.playerCollision.yPos, this.playerCollision.radius);
        CircleComponent yMovedCircle = new CircleComponent(this.playerCollision.xPos, this.playerCollision.yPos + yVelocity, this.playerCollision.radius);

        //Checking surrounding tiles
        for (int i = 0; i < surroundingTiles.length; i++) {
            currentTile = surroundingTiles[i];
            //Collision on x edge
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
                        bounceBackSmall = true;
                        xVelocity = xVelocity / GameConstants.BOUNCEBACK_SMALL_DIVISOR;
                        reverseXVelocity();
                        break;
                    case TILE_BREAKTWO:
                        Log.i("COLLISION", "BREAKTWO TILE on Y");
                        currentTile.tileType = TileComponent.TILE_TYPE.TILE_BREAKONE;
                        currentTile.setPainter();
                        bounceBackBig = true;
                        xVelocity = xVelocity / GameConstants.BOUNCEBACK_BIG_DIVISOR;
                        reverseXVelocity();
                        break;
                }
            }
            //Collision on y edge
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
                        bounceBackSmall = true;
                        yVelocity = yVelocity / GameConstants.BOUNCEBACK_SMALL_DIVISOR;
                        reverseYVelocity();
                        break;
                    case TILE_BREAKTWO:
                        Log.i("COLLISION", "BREAKTWO TILE on Y");
                        currentTile.tileType = TileComponent.TILE_TYPE.TILE_BREAKONE;
                        currentTile.setPainter();
                        bounceBackBig = true;
                        yVelocity = yVelocity / GameConstants.BOUNCEBACK_BIG_DIVISOR;
                        reverseYVelocity();
                        break;
                }
            }
        }
        //Move if there's no bounceback
        if (bounceBackSmall || bounceBackBig) {
            playerCollision.move(xVelocity, yVelocity);
        }
    }

    /**
     * Detects collisions between players
     */
    public void checkPlayerCollision() {
        CircleComponent movedCircle = new CircleComponent(this.playerCollision.xPos + xVelocity, this.playerCollision.yPos + yVelocity, this.playerCollision.radius);
        CircleComponent opponentCircle = gameCallback.getOpponentCollisionComponent(this.playerId);
        if (movedCircle.collision(opponentCircle)) {
            Log.i("Players collided!", "YEAH");
            if (this.isOnAttack()) {
                gameCallback.hitOpponent(this.playerId);
            } else {

            }
        }
    }


    /**
     * Determines if this player is bouncing back
     *
     * @return whether the player is bounding back
     */
    public boolean onBounceBack() {
        return bounceBackSmall || bounceBackBig;
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
        this.joystickMultiplier = this.isOnAttack() ? GameConstants.ACCELERATION_MULTIPLIER_ONATTACK : GameConstants.ACCELERATION_MULTIPLIER_ONDEFENSE;
    }

    /**
     * Makes this player take a hit and triggers the animation
     */
    public void takeHit() {
        playerLifes--;
        if (playerLifes > 0) {
            takingHit = true;
        }
    }

    /**
     * Returns the value which indicates if this player is taking a hit and should be immune or not
     *
     * @return the value of takingHit
     */
    public boolean isTakingHit() {
        return this.takingHit;
    }

    /**
     * Returns the player mode (true for attacking, false for defense)
     *
     * @return the player mode
     */
    public boolean isOnAttack() {
        return onAttack;
    }

    /**
     * Sets the attack mode
     *
     * @param onAttack true for attack mode
     */
    public void setOnAttack(boolean onAttack) {
        this.onAttack = onAttack;
        setJoystickMultiplier();
    }

    /**
     * Toggles this player's attackMode
     */
    public void togglePlayerMode() {
        this.onAttack = !onAttack;
        setJoystickMultiplier();
    }
}
