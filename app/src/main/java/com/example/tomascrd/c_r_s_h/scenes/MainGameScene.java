package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.components.CircleComponent;
import com.example.tomascrd.c_r_s_h.components.MapCrsh;
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
    private MapCrsh mapLoad;
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
     * Starts a new main game
     *
     * @param context      the application context
     * @param id           this scene's id (0 is recommended by default for the main menu)
     * @param screenWidth  this screen's width
     * @param screenHeight this screen's height
     */
    public MainGameScene(Context context, int id, int screenWidth, int screenHeight) {
        super(context, id, screenWidth, screenHeight);
        mapLoad = new MapCrsh(666, this);
        mapLoad.loadTileArray();
        PointF playerCenter = new PointF(mapLoad.tileArray[1][1].getCollisionRect().centerX(), mapLoad.tileArray[1][1].getCollisionRect().centerY());
        playerOne = new PlayerCrsh("TestP1", 1, false, new CircleComponent(playerCenter, mapLoad.getReference() / 2));
    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics() {
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
                break;

            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (isClick(backBtn, event)) {
                    return 0;
                }
            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
            default:
                Log.i("Other", "Undefined action: " + action);
        }
        return this.id;
    }
}
