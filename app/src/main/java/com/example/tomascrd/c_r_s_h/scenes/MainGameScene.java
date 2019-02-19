package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.components.CircleComponent;
import com.example.tomascrd.c_r_s_h.components.GamepadComponent;
import com.example.tomascrd.c_r_s_h.components.JoystickComponent;
import com.example.tomascrd.c_r_s_h.components.MapComponent;
import com.example.tomascrd.c_r_s_h.components.PlayerCrsh;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.GameEngine;

/**
 * Represents the main game
 *
 * @author Tomás Cardenal López
 */
public class MainGameScene extends SceneCrsh {

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
     * Starts a new main game
     *
     * @param context        the application context
     * @param id             this scene's id (0 is recommended by default for the main menu)
     * @param screenWidth    this screen's width
     * @param screenHeight   this screen's height
     * @param engineCallback callback to this game's engine
     */
    public MainGameScene(Context context, int id, int screenWidth, int screenHeight, GameEngine engineCallback) {
        super(context, id, screenWidth, screenHeight);
        this.engineCallback = engineCallback;

        //Initialize map
        this.mapLoad = new MapComponent(666, context, screenWidth, screenHeight);
        this.mapLoad.loadTileArray();

        //Initialize players
        PointF playerOneCenter = new PointF(mapLoad.tileArray[2][2].getCollisionRect().exactCenterX(), mapLoad.tileArray[2][2].getCollisionRect().exactCenterY());
        this.playerOne = new PlayerCrsh(this, mapLoad, "TestP1", 1, false, new CircleComponent(playerOneCenter, mapLoad.getReference() / 2));
        PointF playerTwoCenter = new PointF(mapLoad.tileArray[mapLoad.tileArray.length - 3][mapLoad.tileArray[mapLoad.tileArray.length - 3].length - 3].getCollisionRect().exactCenterX(), mapLoad.tileArray[mapLoad.tileArray.length - 3][mapLoad.tileArray[mapLoad.tileArray.length - 3].length - 3].getCollisionRect().exactCenterY());
        this.playerTwo = new PlayerCrsh(this, mapLoad, "testP2", 2, true, new CircleComponent(playerTwoCenter, mapLoad.getReference() / 2));

        //Initialize joysticks
        int joystickRadius = (int) Math.floor(mapLoad.getReference() * 1.5);
        this.joystickOne = new JoystickComponent(context, joystickRadius, Color.GRAY, Color.CYAN);
        this.joystickTwo = new JoystickComponent(context, joystickRadius, Color.GRAY, Color.MAGENTA);

        //Initialize vibrator
        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);


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

        playerOne.move();
        playerTwo.move();
    }

    /**
     * Draws the main game
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //General background
        c.drawColor(Color.WHITE);
        //Draw map
        mapLoad.draw(c);
        //Draw player One
        playerOne.playerCollision.draw(c);
        //Draw player Two
        playerTwo.playerCollision.draw(c);
        //Draw the back button TODO change this for an ingame pause menu
        backBtn.draw(c);
        //Draw the joysticks
        joystickOne.draw(c);
        joystickTwo.draw(c);
    }

    /**
     * Controls the events on the touchscreen
     *
     * @param event the touch event
     * @return the pointerId;
     */
    public int onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        boolean playerOneArea = event.getX(event.getActionIndex()) > screenWidth / 2 + 100;
        boolean playerTwoArea = event.getX(event.getActionIndex()) < screenWidth / 2 - 100;
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                if (playerOneArea) {
                    joystickOne.activateJoystick(event);
                }
                if (playerTwoArea) {
                    joystickTwo.activateJoystick(event);
                }
                break;
            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (event.getPointerId(event.getActionIndex()) == joystickOne.getPointerId() && playerOneArea) {
                    joystickOne.deactivate();
                }
                if (event.getPointerId(event.getActionIndex()) == joystickTwo.getPointerId() && playerTwoArea) {
                    joystickTwo.deactivate();
                }
                if (isClick(backBtn, event)) {
                    return 0;
                }
                break;
            case MotionEvent.ACTION_MOVE: // Any finger moves
                joystickOne.onMoveEvent(event);
                joystickTwo.onMoveEvent(event);
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
}
