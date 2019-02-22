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
 * A scene representing a map creator
 *
 * @author Tomás Cardenal López
 */
public class MapCreatorScene extends SceneCrsh {
    /**
     * Callback to access the game engine
     */
    private GameEngine engineCallback;
    /**
     * Map to load on the creator scene
     */
    private MapComponent creatorMap;
    /**
     * Indicates whether the scene is paused or not
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
     * Starts a new Map Creator
     *
     * @param context        the scene's context
     * @param id             the scene's id
     * @param screenWidth    the screen width
     * @param screenHeight   the screen height
     * @param engineCallback callback to the GameEngine
     */
    public MapCreatorScene(Context context, int id, int screenWidth, int screenHeight, GameEngine engineCallback) {
        super(context, id, screenWidth, screenHeight);
        this.engineCallback = engineCallback;
        //Initialize map
        this.creatorMap = new MapComponent(0, context, screenWidth, screenHeight, engineCallback.loader);
        this.creatorMap.loadTileArray();
        this.tileSizeReference = this.creatorMap.getReference();

        //Gradient background
        this.gradientPaint = new Paint();
        int[] leftgradientColors = {Color.GREEN, Color.BLUE, Color.CYAN};
        float[] positions = {0, screenWidth / 2, screenWidth};
        LinearGradient gradientBackground = new LinearGradient(0, screenHeight, screenWidth, screenHeight, leftgradientColors, positions, Shader.TileMode.CLAMP);
        this.gradientPaint.setShader(gradientBackground);

        //Pause button
        btnPause = new ButtonComponent(context,
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME), context.getString(R.string.btnPause),
                screenWidth - screenWidth / 16, 0, screenWidth, screenWidth / 16, Color.TRANSPARENT, 0, false, 0);

        //Pause menu
        this.pauseMenu = new PauseMenuComponent(this.context, this.creatorMap.xLeft, this.creatorMap.yTop, this.creatorMap.mapAreaWidth, this.creatorMap.mapAreaHeight, this);
    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics() {
        if (!onPause) {

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
            creatorMap.draw(c);
            //Draw the pause button
            btnPause.draw(c);
        } else {
            pauseMenu.draw(c);
        }

    }

    /**
     * Controls the events on the touchscreen and sends it to the corresponding touchManager
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                if (!onPause) {

                }
                break;
            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (!onPause) {

                } else {

                }
                break;
            case MotionEvent.ACTION_MOVE: // Any finger moves
                if (!onPause) {

                }
                break;
            default:
                Log.i("Other", "Undefined action: " + action);
        }
        return this.id;
    }

    /**
     * Sets the onPause value of the game
     *
     * @param onPause the new onPause value
     */
    public void setOnPause(boolean onPause) {
        this.onPause = onPause;
    }


}
