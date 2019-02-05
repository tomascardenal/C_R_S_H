package com.example.tomascrd.c_r_s_h;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

/**
 * This game's main menu
 *
 * @author Tomás Cardenal
 */
public class MainMenuScene extends SceneCrsh {

    private volatile boolean runParallax;


    /**
     * Starts a main menu
     * @param context the application context
     * @param id this scene's id (0 is recommended by default for the main menu)
     * @param screenWidth this screen's width
     * @param screenHeight this screen's height
     */
    public MainMenuScene(Context context, int id, int screenWidth, int screenHeight) {
        super(context, id, screenWidth, screenHeight);
    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics(){

    }

    /**
     * Draws the menu
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c){
        //General background
        c.drawColor(Color.BLUE);

        //Parallax background TODO get inspirational with
        //http://gamecodeschool.com/android/coding-a-parallax-scrolling-background-for-android/

        //Menu components


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
                break;

            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
            default:  Log.i("Other", "Undefined action: "+action);
        }
        return this.id;
    }

    protected class ParallaxThread extends Thread{
        @Override
        public void run() {
            while(runParallax){
                long startFrameTime = System.currentTimeMillis();

                updatePhysics();

            }
        }
    }
}
