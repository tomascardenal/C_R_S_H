package com.example.tomascrd.c_r_s_h.components;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.scenes.MainGameScene;
import com.example.tomascrd.c_r_s_h.structs.PowerUps;
import com.example.tomascrd.c_r_s_h.structs.TileTypes;
import com.example.tomascrd.c_r_s_h.structs.eGameMode;
import com.example.tomascrd.c_r_s_h.structs.eSoundEffect;

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
    protected boolean onAttack;
    /**
     * The player's collision circle
     */
    public CircleComponent playerCollision;
    /**
     * The player's lifes
     */
    protected int playerLifes;
    /**
     * The player's score
     */
    private int playerScore;
    /**
     * This player's x axis velocity
     */
    protected float xVelocity;
    /**
     * This player's y axis velocity
     */
    protected float yVelocity;
    /**
     * This player's spawning point
     */
    protected PointF spawnPoint;
    /**
     * This player's start radius
     */
    protected int startRadius;
    /**
     * Callback to access the map
     */
    protected MapComponent mapCallback;
    /**
     * Callback to access the game scene
     */
    protected MainGameScene gameCallback;
    /**
     * The player columnPosition
     */
    protected int columnPosition;
    /**
     * The player rowPosition
     */
    protected int rowPosition;
    /**
     * The surrounding tiles
     */
    protected TileComponent[] surroundingTiles;
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
    protected int bounceBackCycle;
    /**
     * Accelleration multiplier for the joystick
     */
    protected int joystickMultiplier;
    /**
     * Triggers the hit animation and makes the player immune
     */
    protected boolean takingHit;
    /**
     * Counter for the hit animation
     */
    protected int takehitCounter;
    /**
     * Indicates if this player is colliding against a border tile on the X coordinate with positive X velocity
     */
    protected boolean againstBorderXPositive;
    /**
     * Indicates if this player is colliding against a border tile on the Y coordinate with positive Y velocity
     */
    protected boolean againstBorderYPositive;
    /**
     * Indicates if this player is colliding against a border tile on the X coordinate with negative X velocity
     */
    protected boolean againstBorderXNegative;
    /**
     * Indicates if this player is colliding against a border tile on the Y coordinate with negative Y velocity
     */
    protected boolean againstBorderYNegative;
    /**
     * Rect for scaling the bitmap
     */
    public Rect imageRect;
    /**
     * Index for the next powerup
     */
    private int powerUpIndex;
    /**
     * Current powerup Cycle
     */
    private int currentPowerUpCycle;
    /**
     * Current powerup loaded
     */
    private PowerUps.ePowerUp currentPowerUp;
    /**
     * Different stages of power up, determined by points
     */
    private static final int[] POWER_UP_AT = {500, 1000, 2000, 3500, 5000, 7000, 9000};
    /**
     * Indicates if the timer stop power up is on
     */
    private boolean powerTimerStop;
    /**
     * Indicates if the no bounceback power up is on
     */
    private boolean powerNoBounceback;
    /**
     * Indicates if the invincible power up is on
     */
    private boolean powerInvincible;
    /**
     * Indicates if the slow opponent power up is on
     */
    private boolean powerSlowOpponent;
    /**
     * Indicates if the opponent is slowing you down with a powerup
     */
    private boolean slowedByOpponent;
    /**
     * Indicates if the slow myself power up is on
     */
    private boolean powerSlowMyself;

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
        this.playerLifes = GameConstants.MAX_PLAYER_LIVES;
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.bounceBackCycle = 0;
        this.setBounceBackBig(false);
        this.setBounceBackSmall(false);
        this.takingHit = false;
        this.powerInvincible = false;
        this.powerNoBounceback = false;
        this.powerTimerStop = false;
        this.powerSlowOpponent = false;
        this.powerSlowMyself = false;
        this.setSlowedByOpponent(false);
        this.powerUpIndex = 0;
        this.currentPowerUp = null;
        this.currentPowerUpCycle = 0;
        this.takehitCounter = 0;
        this.playerScore = 0;
        this.imageRect = new Rect(
                (int) playerCollision.xPos - startRadius,
                (int) playerCollision.yPos - startRadius,
                (int) playerCollision.xPos + startRadius,
                (int) playerCollision.yPos + startRadius);

        //Set the color of the player depending of it's ID
        switch (playerId) {
            case 0:
                this.playerCollision.setColor(Color.BLACK);
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
     * Respawns the player on the starting position
     */
    public void respawn() {
        this.playerLifes = GameConstants.MAX_PLAYER_LIVES;
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.bounceBackCycle = 0;
        this.powerUpIndex = 0;
        this.setBounceBackBig(false);
        this.setBounceBackSmall(false);
        this.takingHit = false;
        this.takehitCounter = 0;
        this.playerCollision.resetPosition(this.spawnPoint.x, this.spawnPoint.y);
        this.playerCollision.radius = startRadius;
        if (this.playerId == 1) {
            gameCallback.lifeOne.resetLife();
        } else {
            gameCallback.lifeTwo.resetLife();
        }
        deactivatePowerUps();
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
            if (powerInvincible) {
                playerCollision.setDrawingAlpha(100);
            } else if (takingHit && takehitCounter < GameConstants.TAKEHIT_CYCLES) {
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
            gameCallback.setOnEndGame(true, this.playerId);
        }
        playerCollision.draw(c);
        if (playerLifes > 0 && !takingHit && !powerInvincible) {
            Bitmap bmpPlayer = gameCallback.getPlayerBMP(playerId, onAttack);
            c.drawBitmap(bmpPlayer, playerCollision.xPos - playerCollision.radius, playerCollision.yPos - playerCollision.radius, null);
        }
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
        if (this.playerLifes < GameConstants.MAX_PLAYER_LIVES) {
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
     * Routine for the powerUps
     */
    public void powerUpRoutine() {
        if (gameCallback.gameMode != eGameMode.MODE_TUTORIAL) {
            if (getCurrentPowerUp() == null) {
                if (playerScore >= POWER_UP_AT[powerUpIndex]) {
                    currentPowerUp = PowerUps.getRandomPowerUp(powerUpIndex);
                    powerUpIndex++;
                    activatePowerUp(getCurrentPowerUp());
                }
            } else if (currentPowerUpCycle < GameConstants.POWERUP_CYCLES) {
                currentPowerUpCycle++;
            } else if (currentPowerUpCycle >= GameConstants.POWERUP_CYCLES) {
                deactivatePowerUps();
            }
        }
    }

    /**
     * Deactivates all powerups
     */
    public void deactivatePowerUps() {
        powerTimerStop = false;
        powerSlowMyself = false;
        powerSlowOpponent = false;
        powerInvincible = false;
        powerNoBounceback = false;
        currentPowerUp = null;
        currentPowerUpCycle = 0;
    }

    /**
     * Activates the booleans and parameters for the given powerUp
     *
     * @param powerUp the given powerUp
     */
    public void activatePowerUp(PowerUps.ePowerUp powerUp) {
        if (powerUp == null) {
            deactivatePowerUps();
        } else {
            switch (powerUp) {
                case POWERUP_TIMER_STOP:
                    powerTimerStop = true;
                    break;
                case POWERUP_NO_BOUNCEBACK:
                    powerNoBounceback = true;
                    break;
                case POWERUP_INVINCIBLE:
                    powerInvincible = true;
                    break;
                case POWERUP_SLOW_OPPONENT:
                    powerSlowOpponent = true;
                    break;
                case POWERUP_SLOW_MYSELF:
                    powerSlowMyself = true;
                    break;
            }
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
        Log.i("CrshDebug", "Grid position p" + this.playerId + " row:" + rowPosition + " col:" + columnPosition);
    }

    /**
     * Moves this player's position the distance indicated on it's velocity
     */
    public void move() {
        //Checking collisions
        checkTileCollisions();
        checkPlayerCollision();
        powerUpRoutine();
        //If velocity is not 0 on both edges and there's no bounceback at all
        if ((xVelocity != 0 || yVelocity != 0) && (!isBounceBackSmall()) && (!isBounceBackBig())) {
            float realX, realY;
            if (slowedByOpponent || powerSlowMyself) {
                realX = xVelocity / 3 * 2;
                realY = yVelocity / 3 * 2;
            } else {
                realX = xVelocity;
                realY = yVelocity;
            }
            playerCollision.move(realX, realY);
            //If there's bounceback
        } else if (isBounceBackSmall() || isBounceBackBig()) {
            bounceBackRoutine();
        }
        setMapPosition();
    }

    /**
     * Bounceback effect algorithm
     */
    private void bounceBackRoutine() {
        //Cycles for the bounceback
        int cycles = isBounceBackSmall() ? GameConstants.BOUNCEBACK_SMALL_CYCLES : GameConstants.BOUNCEBACK_BIG_CYCLES;
        //If it's the first cycle, vibrate
        if (bounceBackCycle == 0) {
            gameCallback.playSoundEffect(eSoundEffect.EFFECT_BUMP);
            gameCallback.doShortVibration();
            bounceBackCycle++;
            //If it's still cycling
        } else if (bounceBackCycle < cycles) {
            bounceBackCycle++;
            //Start slowdown
            if (bounceBackCycle > cycles / 2) {
                if (this.xVelocity > 0) {
                    if (this.xVelocity - GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT > 0) {
                        this.xVelocity -= GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT;
                    }
                } else {
                    if (this.xVelocity + GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT < 0) {
                        this.xVelocity += GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT;
                    }
                }
                if (this.yVelocity > 0) {
                    if (this.yVelocity - GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT > 0) {
                        this.yVelocity -= GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT;
                    }
                } else {
                    if (this.yVelocity + GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT < 0) {
                        this.yVelocity += GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT;
                    }
                }
            } else if (this.xVelocity - GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT <= 0 || this.xVelocity + GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT >= 0
                    && this.yVelocity - GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT <= 0 || this.yVelocity + GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT >= 0) {
                bounceBackCycle = cycles;
            }
            //Ending slowdown and bounceback
        } else {
            boolean stopX = false;
            boolean stopY = false;
            if (this.xVelocity - GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT > 0.5) {
                this.xVelocity -= GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT;
            } else {
                stopX = true;
            }
            if (this.yVelocity - GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT > 0.5) {
                this.yVelocity -= GameConstants.BOUNCEBACK_SLOWDOWN_SHIFT;
            } else {
                stopY = true;
            }
            if (stopX && stopY) {
                bounceBackCycle = 0;
                setBounceBackBig(false);
                setBounceBackSmall(false);
                this.xVelocity = 0;
                this.yVelocity = 0;
            }
        }
        playerCollision.move(xVelocity, yVelocity);
    }


    /**
     * Checks for tile collisions
     */
    private void checkTileCollisions() {
        //Declaring current tile
        TileComponent currentTile;
        againstBorderXPositive = false;
        againstBorderXNegative = false;
        againstBorderYPositive = false;
        againstBorderYNegative = false;
        boolean twentyFivePoints = false;
        boolean fiftyPoints = false;
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
                        Log.i("CrshDebug", "Collision border on X for p" + playerId);
                        if (xVelocity > 0) {
                            againstBorderXPositive = true;
                        } else if (xVelocity < 0) {
                            againstBorderXNegative = true;
                        }
                        xVelocity = 0;
                        break;
                    case TILE_BREAKONE:
                        Log.i("CrshDebug", "Collision breakone on X for p" + playerId);
                        currentTile.tileType = TileTypes.eTileType.TILE_PATH;
                        setBounceBackSmall(true);
                        xVelocity = xVelocity / GameConstants.BOUNCEBACK_SMALL_DIVISOR;
                        twentyFivePoints = true;
                        reverseXVelocity();
                        break;
                    case TILE_BREAKTWO:
                        Log.i("CrshDebug", "Collision breaktwo on X for p" + playerId);
                        currentTile.tileType = TileTypes.eTileType.TILE_BREAKONE;
                        setBounceBackBig(true);
                        fiftyPoints = true;
                        xVelocity = xVelocity / GameConstants.BOUNCEBACK_BIG_DIVISOR;
                        reverseXVelocity();
                        break;
                }
            }
            //Collision on y edge
            if (yMovedCircle.collision(currentTile.collisionRect)) {
                switch (currentTile.tileType) {
                    case TILE_BORDER:
                        Log.i("CrshDebug", "Collision border on Y for p" + playerId);
                        if (yVelocity > 0) {
                            againstBorderYPositive = true;
                        } else if (yVelocity < 0) {
                            againstBorderYNegative = true;
                        }
                        yVelocity = 0;
                        break;
                    case TILE_BREAKONE:
                        Log.i("CrshDebug", "Collision breakone on Y for p" + playerId);
                        currentTile.tileType = TileTypes.eTileType.TILE_PATH;
                        setBounceBackSmall(true);
                        yVelocity = yVelocity / GameConstants.BOUNCEBACK_SMALL_DIVISOR;
                        twentyFivePoints = true;
                        reverseYVelocity();
                        break;
                    case TILE_BREAKTWO:
                        Log.i("CrshDebug", "Collision breaktwo on Y for p" + playerId);
                        currentTile.tileType = TileTypes.eTileType.TILE_BREAKONE;
                        setBounceBackBig(true);
                        yVelocity = yVelocity / GameConstants.BOUNCEBACK_BIG_DIVISOR;
                        fiftyPoints = true;
                        reverseYVelocity();
                        break;
                }
            }
        }
        if (twentyFivePoints) {
            addPlayerScore(25);
        }
        if (fiftyPoints) {
            addPlayerScore(50);
        }
    }

    /**
     * Detects collisions between players
     */
    public void checkPlayerCollision() {
        CircleComponent movedCircle = new CircleComponent(this.playerCollision.xPos + xVelocity, this.playerCollision.yPos + yVelocity, this.playerCollision.radius);
        CircleComponent opponentCircle = gameCallback.getOpponentCollisionComponent(this.playerId);
        if (movedCircle.collision(opponentCircle)) {
            Log.i("CrshDebug", "Collision between players");
            if (this.isOnAttack()) {
                if (!gameCallback.isOpponentOnHit(playerId)) {
                    if (playerLifes > 2) {
                        addPlayerScore(5);
                    } else if (playerLifes > 1) {
                        addPlayerScore(10);
                    } else {
                        addPlayerScore(20);
                    }
                }
                gameCallback.hitOpponent(this.playerId);
            }
        }
    }


    /**
     * Determines if this player is bouncing back
     *
     * @return whether the player is bounding back
     */
    public boolean onBounceBack() {
        return isBounceBackSmall() || isBounceBackBig();
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
        if (!powerInvincible) {
            removeALife();
            if (playerLifes > 0) {
                takingHit = true;
                gameCallback.playSoundEffect(eSoundEffect.EFFECT_HIT);
            } else {
                gameCallback.playSoundEffect(eSoundEffect.EFFECT_DEATH);
            }
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
        gameCallback.setAttackIndicator();
    }

    /**
     * Gets the value of the current xPosition
     *
     * @return the current value of xPos
     */
    public float getXPosition() {
        return this.playerCollision.xPos;
    }

    /**
     * Gets the value of the current yPosition
     *
     * @return the current value of yPos
     */
    public float getYPosition() {
        return this.playerCollision.yPos;
    }

    /**
     * Gets the value of the player's score
     *
     * @return the current player score
     */
    public int getPlayerScore() {
        return playerScore;
    }

    /**
     * Adds a value to the player's score checking the limit
     *
     * @param points the score to add
     */
    public void addPlayerScore(int points) {
        this.playerScore += points;
        if (this.playerScore > 9999) {
            this.playerScore = 9999;
        }
    }

    /**
     * Checks if the player is on a small bounceback
     *
     * @return the current bounceback value
     */
    public boolean isBounceBackSmall() {
        return bounceBackSmall;
    }

    /**
     * Sets the player for a small bounceback, checking for powerUps
     *
     * @return the new bounceback value
     */
    public void setBounceBackSmall(boolean bounceBackSmall) {
        if (powerNoBounceback) {
            this.bounceBackSmall = false;
        } else {
            this.bounceBackSmall = bounceBackSmall;
        }
    }

    /**
     * Checks if the player is on a big bounceback
     *
     * @return the current bounceback value
     */
    public boolean isBounceBackBig() {
        return bounceBackBig;
    }

    /**
     * Sets the player for a big bounceback, checking for powerUps
     *
     * @return the new bounceback value
     */
    public void setBounceBackBig(boolean bounceBackBig) {
        if (powerNoBounceback) {
            this.bounceBackBig = false;
        } else {
            this.bounceBackBig = bounceBackBig;
        }
    }

    /**
     * Gets the value of the current power up
     *
     * @return the current power up, null if no powerup is on
     */
    public PowerUps.ePowerUp getCurrentPowerUp() {
        return currentPowerUp;
    }

    /**
     * Gets the slowedbyopponent value to see if the opponent is slowing this player down
     *
     * @return the value of the boolean
     */
    public boolean isSlowedByOpponent() {
        return slowedByOpponent;
    }

    /**
     * Sets a new value for the opponent slowing this player down
     *
     * @param slowedByOpponent the new value of the boolean
     */
    public void setSlowedByOpponent(boolean slowedByOpponent) {
        this.slowedByOpponent = slowedByOpponent;
    }
}
