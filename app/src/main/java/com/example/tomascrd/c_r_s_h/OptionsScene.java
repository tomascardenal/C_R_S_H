package com.example.tomascrd.c_r_s_h;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Represents the options menu
 *
 * @author Tomás Cardenal López
 */
public class OptionsScene extends SceneCrsh{

    /**
     * Paint object for text
     */
    Paint pText;


    /**
     * Starts an options menu
     * @param context the application context
     * @param id this scene's id (0 is recommended by default for the main menu)
     * @param screenWidth this screen's width
     * @param screenHeight this screen's height
     */
    public OptionsScene(Context context, int id, int screenWidth, int screenHeight) {
        super(context, id, screenWidth, screenHeight);
        //Title text
        pText = new Paint();
        pText.setTypeface(Typeface.createFromAsset(context.getAssets(),"KarmaFuture.ttf"));
        pText.setColor(Color.BLACK);
        pText.setTextAlign(Paint.Align.CENTER);
        pText.setTextSize((float) ((screenHeight/GameConstants.MENUSCREEN_COLUMNS)*2.5));
    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics(){

    }

    /**
     * Draws the options menu
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c){
        //General background
        c.drawColor(Color.GREEN);

        //Test text
        c.drawText(context.getString(R.string.btnOptions),screenWidth/GameConstants.MENUSCREEN_COLUMNS*9,screenHeight/GameConstants.MENUSCREEN_ROWS*2,pText);

        backBtn.draw(c);

    }

    /**
     * Controls the events on the touchscreen
     * @param event the touch event
     * @return the pointerId;
     */
    public int onTouchEvent (MotionEvent event){
        int pointerIndex = event.getActionIndex();
        int pointerID = event.getPointerId(pointerIndex);
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                break;

            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if(isClick(backBtn,event)){
                    return 0;
                }
            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
            default:  Log.i("Other", "Undefined action: "+action);
        }
        return this.id;
    }
}

