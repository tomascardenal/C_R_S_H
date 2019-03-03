package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.JoystickComponent;
import com.example.tomascrd.c_r_s_h.components.MapComponent;
import com.example.tomascrd.c_r_s_h.components.PlayerCrsh;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.GameEngine;

/**
 * Represents the tutorial for the game
 *
 * @author Tomás Cardenal López
 */
public class TutorialScene extends SceneCrsh {


    /**
     * Constant id for TutorialScene
     */
    public static final int TUTORIAL_ID = 5;
    /**
     * Determines if there's an animation
     */
    private boolean animating;
    /**
     * Map to load during the tutorial
     */
    public MapComponent mapLoad;
    /**
     * Player one on tutorial
     */
    public PlayerCrsh playerTutorialOne;
    /**
     * Player two on tutorial
     */
    public PlayerCrsh playerTutorialTwo;
    /**
     * Joystick
     */
    public JoystickComponent joystick;

    /**
     * Starts a tutorial screen
     *
     * @param context        the application context
     * @param screenWidth    this screen's width
     * @param screenHeight   this screen's height
     * @param engineCallback callback to access gameEngine data
     */
    public TutorialScene(Context context, int screenWidth, int screenHeight, GameEngine engineCallback) {
        //TODO tutorial screen
        super(context, TUTORIAL_ID, screenWidth, screenHeight);
        this.engineCallback = engineCallback;
        //Title text
        pTitleText = new Paint();
        pTitleText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        pTitleText.setColor(Color.BLACK);
        pTitleText.setTextAlign(Paint.Align.CENTER);
        pTitleText.setTextSize((float) ((screenHeight / GameConstants.MENUSCREEN_COLUMNS) * 2));

        //TODO Set player indicators (with maybe a player sprite)
        //Initialize variables
        this.engineCallback = engineCallback;

    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics() {
        super.updatePhysics();
    }

    /**
     * Draws the tutorial
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //General background
        super.draw(c);
        //Title text
        c.drawText(context.getString(R.string.btnTutorial), screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9, screenHeight / GameConstants.MENUSCREEN_ROWS, pTitleText);
        //Buttons
        backBtn.draw(c);

    }

    /**
     * Controls the events on the touchscreen
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int onTouchEvent(MotionEvent event) {
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
        }
        return this.id;
    }
}

