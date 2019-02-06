package com.example.tomascrd.c_r_s_h;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Represents the main game
 *
 * @author Tomás Cardenal López
 */
public class MainGameScene extends SceneCrsh{

    private int SCREEN_COLUMNS = 32;
    private int SCREEN_ROWS = 16;
    private Paint pTiles;

    /**
     * Starts a new main game
     * @param context the application context
     * @param id this scene's id (0 is recommended by default for the main menu)
     * @param screenWidth this screen's width
     * @param screenHeight this screen's height
     */
    public MainGameScene(Context context, int id, int screenWidth, int screenHeight) {
        super(context, id, screenWidth, screenHeight);
        pTiles = new Paint();

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
        int reference = screenHeight/SCREEN_COLUMNS;
        c.drawColor(Color.WHITE);
        for(int i=2;i<=SCREEN_COLUMNS-3;i++){
            for(int j=2;j<=SCREEN_ROWS-4;j++){
                if(!(i%2==0 && j==2)){
                    pTiles.setColor(pTiles.getColor()==Color.RED?Color.GREEN:Color.RED);
                }
                c.drawRect(new Rect(screenWidth/SCREEN_COLUMNS*i,screenHeight/SCREEN_ROWS*j,screenWidth/SCREEN_COLUMNS*i+reference,screenHeight/SCREEN_ROWS*j+reference),pTiles);
            }
        }
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

    /**
     * Parses the map
     */
    public void mapParser(){

    }
}
