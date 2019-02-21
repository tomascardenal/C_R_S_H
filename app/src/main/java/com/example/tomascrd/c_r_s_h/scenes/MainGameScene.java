package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.ButtonComponent;
import com.example.tomascrd.c_r_s_h.components.CircleComponent;
import com.example.tomascrd.c_r_s_h.components.GamepadComponent;
import com.example.tomascrd.c_r_s_h.components.JoystickComponent;
import com.example.tomascrd.c_r_s_h.components.MapComponent;
import com.example.tomascrd.c_r_s_h.components.PauseMenuComponent;
import com.example.tomascrd.c_r_s_h.components.PlayerCrsh;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.GameEngine;

/**
 * Represents the main game
 *
 * @author Tomás Cardenal López
 */
public class MainGameScene extends SceneCrsh implements SensorEventListener {


    public enum GAMEMODE {
        MODE_NORMAL, MODE_CRSH
    }

    /**
     * Map to load on the main game scene
     */
    private MapComponent mapLoad;
    /**
     * Player 1
     */
    private PlayerCrsh playerOne;
    /**
     * Player 2
     */
    private PlayerCrsh playerTwo;
    /**
     * COM Player
     */
    private PlayerCrsh playerCom;
    /**
     * Joystick for player one
     */
    private JoystickComponent joystickOne;
    /**
     * Joystick for player one
     */
    private JoystickComponent joystickTwo;
    /**
     * Gamepad for player one
     */
    @Deprecated
    private GamepadComponent padOne;
    /**
     * Gamepad for player two
     */
    @Deprecated
    private GamepadComponent padTwo;
    /**
     * Controls if the players are moving
     */
    @Deprecated
    private boolean[] playerMoved;
    /**
     * Vibrator for the game
     */
    private Vibrator vibrator;
    /**
     * Callback to access the game engine
     */
    private GameEngine engineCallback;
    /**
     * Gradient for left side attacking
     */
    private LinearGradient gradientLeftAttack;
    /**
     * Gradient for right side attacking
     */
    private LinearGradient gradientRightAttack;
    /**
     * Reference for the tile size, indicates how much the side of the tile measures
     */
    public int tileSizeReference;
    /**
     * Indicates whether the game is paused or not
     */
    private boolean onPause;
    /**
     * Pause menu
     */
    private PauseMenuComponent pauseMenu;
    /**
     * Pause button
     */
    private ButtonComponent btnPause;
    /**
     * Sensor manager for the accelerometer
     */
    private SensorManager sensorManager;
    /**
     * Accelerometer sensor
     */
    private Sensor sensor;
    /**
     * This instance's game mode
     */
    public GAMEMODE gameMode;

    /**
     * Starts a new main game
     *
     * @param context        the application context
     * @param id             this scene's id (0 is recommended by default for the main menu)
     * @param screenWidth    this screen's width
     * @param screenHeight   this screen's height
     * @param engineCallback callback to this game's engine
     */
    public MainGameScene(Context context, int id, int screenWidth, int screenHeight, GameEngine engineCallback, GAMEMODE gameMode) {
        //Initialize variables
        super(context, id, screenWidth, screenHeight);
        this.engineCallback = engineCallback;
        this.onPause = false;
        this.gameMode = gameMode;

        //Initialize map
        this.mapLoad = new MapComponent(666, context, screenWidth, screenHeight);
        this.mapLoad.loadTileArray();
        this.tileSizeReference = mapLoad.getReference();

        //Initialize players
        PointF playerOneCenter = new PointF(mapLoad.tileArray[2][2].getCollisionRect().exactCenterX(), mapLoad.tileArray[2][2].getCollisionRect().exactCenterY());
        this.playerOne = new PlayerCrsh(this, mapLoad, "TestP1", 1, false, new CircleComponent(playerOneCenter, mapLoad.getReference() / 2));
        PointF playerTwoCenter = new PointF(mapLoad.tileArray[mapLoad.tileArray.length - 3][mapLoad.tileArray[mapLoad.tileArray.length - 3].length - 3].getCollisionRect().exactCenterX(), mapLoad.tileArray[mapLoad.tileArray.length - 3][mapLoad.tileArray[mapLoad.tileArray.length - 3].length - 3].getCollisionRect().exactCenterY());
        this.playerTwo = new PlayerCrsh(this, mapLoad, "testP2", 2, true, new CircleComponent(playerTwoCenter, mapLoad.getReference() / 2));

        //Initialize joysticks
        int joystickRadius = (int) Math.floor(mapLoad.getReference() * 1.5);
        this.joystickOne = new JoystickComponent(context, joystickRadius, Color.GRAY, Color.CYAN);
        this.joystickTwo = new JoystickComponent(context, joystickRadius, Color.GRAY, Color.MAGENTA);


        //Initialize accelerometer
        if (gameMode == GAMEMODE.MODE_CRSH) {
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
        }

        //Initialize vibrator
        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        //Gradient background
        this.gradientPaint = new Paint();
        int[] leftgradientColors = {Color.MAGENTA, Color.GREEN};
        int[] rightgradientColors = {Color.GREEN, Color.MAGENTA};
        float[] positions = {0, screenWidth / 2};
        gradientLeftAttack = new LinearGradient(0, screenHeight, screenWidth, screenHeight, leftgradientColors, positions, Shader.TileMode.CLAMP);
        gradientRightAttack = new LinearGradient(0, screenWidth, screenWidth, screenHeight, rightgradientColors, positions, Shader.TileMode.CLAMP);
        setGradients();

        //Pause button
        btnPause = new ButtonComponent(context,
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME), context.getString(R.string.btnPause),
                screenWidth - screenWidth / 16, 0, screenWidth, screenWidth / 16, Color.TRANSPARENT, 0, false, 0);

        //Pause menu
        this.pauseMenu = new PauseMenuComponent(this.context, this.mapLoad.xLeft, this.mapLoad.yTop, this.mapLoad.mapAreaWidth, this.mapLoad.mapAreaHeight, this);
    }

    /**
     * Sets the gradient of the main game scene depending on the attack and defense modes
     */
    public void setGradients() {
        if (playerOne.isOnAttack() && !playerTwo.isOnAttack()) {
            gradientPaint.setShader(gradientRightAttack);
        } else if (playerTwo.isOnAttack() && !playerOne.isOnAttack()) {
            gradientPaint.setShader(gradientLeftAttack);
        }
    }

    /**
     * Triggers a 200ms vibration
     */
    public void doShortVibration() {
        if (engineCallback.optionsManager.isDoVibrate()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            {
                this.vibrator.vibrate(200);
            }
        }
    }

    /**
     * Triggers a 500ms vibration
     */
    public void doLongVibration() {
        if (engineCallback.optionsManager.isDoVibrate()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            {
                this.vibrator.vibrate(500);
            }
        }
    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics() {
        if (!onPause) {
            //If the joystick is active and there's no bounceback on the player, take the values of the joystick
            if (joystickOne.isActive() && !playerOne.onBounceBack()) {
                PointF joystickReference = joystickOne.getDisplacement();
                playerOne.setVelocity(
                        joystickReference.x * playerOne.getJoystickMultiplier(),
                        joystickReference.y * playerOne.getJoystickMultiplier());

            }
            if (joystickTwo.isActive() && !playerTwo.onBounceBack()) {
                PointF joystickReference = joystickTwo.getDisplacement();
                playerTwo.setVelocity(
                        joystickReference.x * playerTwo.getJoystickMultiplier(),
                        joystickReference.y * playerTwo.getJoystickMultiplier());

            }
            //Move the players, if there's bounceback the movement would be the one generated by it
            if (playerOne.getPlayerLifes() > 0) {
                playerOne.move();
            }
            if (playerTwo.getPlayerLifes() > 0) {
                playerTwo.move();
            }
        }
    }

    /**
     * Draws the main game
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //General background
        c.drawPaint(gradientPaint);
        if (!onPause) {
            //Draw map
            mapLoad.draw(c);
            //Draw player One
            playerOne.draw(c);
            //Draw player Two
            playerTwo.draw(c);
            //Draw the back button TODO change this for an ingame pause menu
            btnPause.draw(c);
            //Draw the joysticks
            if (playerOne.getPlayerLifes() > 0) {
                joystickOne.draw(c);
            }
            if (playerTwo.getPlayerLifes() > 0) {
                joystickTwo.draw(c);
            }
        } else {
            pauseMenu.draw(c);
        }

    }

    /**
     * Controls the events on the touchscreen
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        boolean playerOneArea = event.getX(event.getActionIndex()) > screenWidth / 2 + 100;
        boolean playerTwoArea = event.getX(event.getActionIndex()) < screenWidth / 2 - 100;
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                if (!onPause) {
                    //If there's a finger down on the player areas, activate the joystick for that player
                    if (playerOneArea && playerOne.getPlayerLifes() > 0) {
                        joystickOne.activateJoystick(event);
                    }
                    if (playerTwoArea && playerTwo.getPlayerLifes() > 0) {
                        joystickTwo.activateJoystick(event);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (!onPause) {
                    //Joystick up
                    if (event.getPointerId(event.getActionIndex()) == joystickOne.getPointerId() && playerOneArea) {
                        joystickOne.deactivate();
                    }
                    if (event.getPointerId(event.getActionIndex()) == joystickTwo.getPointerId() && playerTwoArea) {
                        joystickTwo.deactivate();
                    }
                } else {
                    if (isClick(pauseMenu.getBtnUnpause(), event)) {
                        onPause = false;
                    }
                    if (isClick(pauseMenu.getBtnOptions(), event)) {
                        engineCallback.loadSavedScene = true;
                        engineCallback.savedScene = this;
                        return 2;
                    }
                    if (isClick(pauseMenu.getBtnEndGame(), event)) {
                        engineCallback.loadSavedScene = false;
                        return 1;
                    }
                }
                if (isClick(btnPause, event)) {
                    onPause = true;
                }

                break;
            case MotionEvent.ACTION_MOVE: // Any finger moves
                //Joystick moving
                if (!onPause) {
                    if (playerOne.getPlayerLifes() > 0) {
                        joystickOne.onMoveEvent(event);
                    }
                    if (playerTwo.getPlayerLifes() > 0) {
                        joystickTwo.onMoveEvent(event);
                    }
                }
                break;
            default:
                Log.i("Other", "Undefined action: " + action);
        }
        return this.id;
    }

    /**
     * Routine for gamepad touch events
     *
     * @param player The player controlling this gamepad
     * @param event  The event which launched this action
     * @deprecated When the JoystickComponent was implemented on 17/02/2019
     */
    @Deprecated
    public void gamePadDown(int player, MotionEvent event) {
        GamepadComponent[] gamepads = {padOne, padTwo};
        PlayerCrsh[] players = {playerOne, playerTwo};
        playerMoved[player] = false;
        if (isClick(gamepads[player].btnUp, event)) {
            players[player].setxVelocity((float) -5);
            playerMoved[player] = true;
        } else if (isClick(gamepads[player].btnDown, event)) {
            players[player].setxVelocity((float) 5);
            playerMoved[player] = true;
        }
        if (isClick(gamepads[player].btnLeft, event)) {
            players[player].setyVelocity((float) 5);
            playerMoved[player] = true;
        } else if (isClick(gamepads[player].btnRight, event)) {
            players[player].setyVelocity((float) -5);
            playerMoved[player] = true;
        }
    }

    /**
     * Routine for gamepad touch events
     *
     * @param player The player controlling this gamepad
     * @param event  The event which launched this action
     * @deprecated When the JoystickComponent was implemented on 17/02/2019
     */
    @Deprecated
    public void gamePadUp(int player, MotionEvent event) {
        GamepadComponent[] gamepads = {padOne, padTwo};
        PlayerCrsh[] players = {playerOne, playerTwo};
        if (isClick(gamepads[player].btnUp, event) || isClick(gamepads[player].btnDown, event)) {
            players[player].setxVelocity((float) 0);
        }
        if (isClick(gamepads[player].btnLeft, event) || isClick(gamepads[player].btnRight, event)) {
            players[player].setyVelocity((float) 0);
        }
        if (event.getActionIndex() == 0) {
            playerMoved[player] = false;
        }
    }

    /**
     * Gets the opponent's collision CircleComponent
     *
     * @param playerID the player id asking for the opponent collision
     * @return the opponent's collision CircleComponent, null if there's no playerCom initialized and the playerID doesn't correspond to playerOne or playerTwo
     */
    public CircleComponent getOpponentCollisionComponent(int playerID) {
        if (playerCom == null) {
            if (playerID == 1) {
                return playerTwo.playerCollision;
            } else if (playerID == 2) {
                return playerOne.playerCollision;
            }
        } else {
            return playerCom.playerCollision;
        }
        return null;
    }

    public void hitOpponent(int playerID) {
        boolean tookHit = false;
        if (playerCom == null) {
            if (playerID == 1 && !playerOne.isTakingHit() && !playerTwo.isTakingHit()) {
                playerTwo.takeHit();
                tookHit = true;
            } else if (playerID == 2 && !playerOne.isTakingHit() && !playerTwo.isTakingHit()) {
                playerOne.takeHit();
                tookHit = true;
            }
            if (tookHit) {
                playerOne.togglePlayerMode();
                playerTwo.togglePlayerMode();
            }
        } else {
            if (!playerCom.isTakingHit()) {
                playerCom.takeHit();
            }
        }
    }

    public void setOnPause(boolean onPause) {
        this.onPause = onPause;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
