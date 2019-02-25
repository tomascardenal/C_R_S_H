package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.core.GameConstants;

/**
 * Represents the records scene
 *
 * @author Tomás Cardenal López
 */
public class RecordsScene extends SceneCrsh {

    /**
     * Starts a records screen
     *
     * @param context      the application context
     * @param id           this scene's id (0 is recommended by default for the main menu)
     * @param screenWidth  this screen's width
     * @param screenHeight this screen's height
     */
    public RecordsScene(Context context, int id, int screenWidth, int screenHeight) {
        super(context, id, screenWidth, screenHeight);

        //Title text
        pTitleText = new Paint();
        pTitleText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        pTitleText.setColor(Color.BLACK);
        pTitleText.setTextAlign(Paint.Align.CENTER);
        pTitleText.setTextSize((float) ((screenHeight / GameConstants.MENUSCREEN_COLUMNS) * 2));

        //Gradient paint
        this.gradientPaint = new Paint();
        this.gradientPaint.setShader(new LinearGradient(0, 0, screenWidth, screenHeight, Color.GREEN, Color.CYAN, Shader.TileMode.CLAMP));

    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics() {

    }

    /**
     * Draws the records
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //General background
        c.drawPaint(gradientPaint);
        //Title text
        c.drawText(context.getString(R.string.btnRecords), screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9, screenHeight / GameConstants.MENUSCREEN_ROWS, pTitleText);
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
            default:
                Log.i("Other", "Undefined action: " + action);
        }
        return this.id;
    }
}

