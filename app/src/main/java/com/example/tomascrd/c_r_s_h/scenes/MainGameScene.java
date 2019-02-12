package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.components.CircleComponent;
import com.example.tomascrd.c_r_s_h.components.GamepadComponent;
import com.example.tomascrd.c_r_s_h.components.MapComponent;
import com.example.tomascrd.c_r_s_h.components.PlayerCrsh;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;

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
     * Player 1 controls
     */
    private GamepadComponent padOne;
    /**
     * Player 2 controls
     */
    private GamepadComponent padTwo;

    /**
     * Starts a new main game
     *
     * @param context      the application context
     * @param id           this scene's id (0 is recommended by default for the main menu)
     * @param screenWidth  this screen's width
     * @param screenHeight this screen's height
     */
    public MainGameScene(Context context, int id, int screenWidth, int screenHeight) {
        super(context, id, screenWidth, screenHeight);
        mapLoad = new MapComponent(666, context, screenWidth, screenHeight);
        mapLoad.loadTileArray();
        PointF playerCenter = new PointF(mapLoad.tileArray[1][1].getCollisionRect().centerX(), mapLoad.tileArray[1][1].getCollisionRect().centerY());
        playerOne = new PlayerCrsh("TestP1", 1, false, new CircleComponent(playerCenter, mapLoad.getReference() / 2));
        padOne = new GamepadComponent(context, 1, screenHeight, screenWidth, mapLoad.getReference());
        padTwo = new GamepadComponent(context, 2, screenHeight, screenWidth, mapLoad.getReference());
    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics() {
        playerOne.move();
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
        //Grid test (IT WORKS, on my phone at least)
        mapLoad.draw(c);
        playerOne.playerCollision.draw(c);
        padOne.draw(c);
        padTwo.draw(c);
        backBtn.draw(c);
    }

    /**
     * Controls the events on the touchscreen
     *
     * @param event the touch event
     * @return the pointerId;
     */
    public int onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointerID = event.getPointerId(pointerIndex);
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                gamePadDown(0, event);
                break;

            case MotionEvent.ACTION_UP:                     // Last finger up
                playerOne.setVelocity(0,0);
                break;
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                gamePadUp(0,event);
                if (isClick(backBtn, event)) {
                    return 0;
                }
                break;
            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
            default:
                Log.i("Other", "Undefined action: " + action);
        }
        return this.id;
    }

    /**
     * Routine for gamepad touch events
     * @param player The player controlling this gamepad
     * @param event  The event which launched this action
     */
    public void gamePadDown(int player, MotionEvent event) {
        GamepadComponent[] gamepads = {padOne, padTwo};
        PlayerCrsh[] players = {playerOne, playerTwo};
        if (isClick(gamepads[player].btnUp, event)) {
            players[player].setxVelocity((float) -0.5);
        }
        if (isClick(gamepads[player].btnDown, event)) {
            players[player].setxVelocity((float) 0.5);
        }
        if (isClick(gamepads[player].btnLeft, event)) {
            players[player].setyVelocity((float) 0.5);
        }
        if (isClick(gamepads[player].btnRight, event)) {
            players[player].setyVelocity((float) -0.5);
        }
    }

    /**
     * Routine for gamepad touch events
     * @param player The player controlling this gamepad
     * @param event  The event which launched this action
     */
    public void gamePadUp(int player, MotionEvent event) {
        GamepadComponent[] gamepads = {padOne, padTwo};
        PlayerCrsh[] players = {playerOne, playerTwo};
        if (isClick(gamepads[player].btnUp, event)) {
            players[player].setxVelocity((float) 0);
        }
        if (isClick(gamepads[player].btnDown, event)) {
            players[player].setxVelocity((float) 0);
        }
        if (isClick(gamepads[player].btnLeft, event)) {
            players[player].setyVelocity((float) 0);
        }
        if (isClick(gamepads[player].btnRight, event)) {
            players[player].setyVelocity((float) 0);
        }
    }
}
