package com.example.tomascrd.c_r_s_h;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

/**
 * This game's main menu
 *
 * @author Tomás Cardenal López
 */
public class MainMenuScene extends SceneCrsh {

    private int SCREEN_COLUMNS = 18;
    private int SCREEN_ROWS = 8;
    Paint pText;
    ParallaxBackground bg1,bg2,bg3;
    ButtonCrsh btnNewGame, btnOptions, btnRecords, btnCredits, btnTutorial;


    /**
     * Starts a main menu
     * @param context the application context
     * @param id this scene's id (0 is recommended by default for the main menu)
     * @param screenWidth this screen's width
     * @param screenHeight this screen's height
     */
    public MainMenuScene(Context context, int id, int screenWidth, int screenHeight) {
        super(context, id, screenWidth, screenHeight);
        //Title text
        pText = new Paint();
        pText.setTypeface(Typeface.createFromAsset(context.getAssets(),"KarmaFuture.ttf"));
        pText.setColor(Color.BLACK);
        pText.setTextAlign(Paint.Align.CENTER);
        pText.setTextSize((float) ((screenHeight/SCREEN_COLUMNS)*2.5));

        //Parallax Backgrounds
        bg1 = new ParallaxBackground(BitmapFactory.decodeResource(context.getResources(),R.drawable.paramount1),screenWidth);
        bg2= new ParallaxBackground(BitmapFactory.decodeResource(context.getResources(),R.drawable.paramount2),screenWidth);
        bg3 = new ParallaxBackground(BitmapFactory.decodeResource(context.getResources(),R.drawable.paramount3),screenWidth);
    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics(){
        bg1.move(1);
        bg2.move(2);
        bg3.move(3);
        //Log.i("img position", bg3.position.x+" Screen width = "+screenWidth);

        if(bg1.position.x>=0){
            bg1.position.x = screenWidth-bg1.image.getWidth();
        }
        if(bg2.position.x>=0){
            bg2.position.x = screenWidth-bg2.image.getWidth();
        }
        if(bg3.position.x>=0){
            bg3.position.x = screenWidth-bg3.image.getWidth();
        }
    }

    /**
     * Draws the menu
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c){
        //General background
        c.drawColor(Color.BLUE);

        //Parallax background FIXME This are placeholder images
        c.drawBitmap(bg3.image,bg3.position.x,bg3.position.y,null);
        c.drawBitmap(bg2.image,bg2.position.x,bg2.position.y,null);
        c.drawBitmap(bg1.image,bg1.position.x,bg1.position.y,null);


        //Title
        c.drawText("C_R_S_H",screenWidth/SCREEN_COLUMNS*9,screenHeight/SCREEN_ROWS*2,pText);


        //Menu buttons
        if(btnNewGame==null){
            btnNewGame = new ButtonCrsh(context,
                    Typeface.createFromAsset(context.getAssets(),"homespun.ttf"),
                    "Jugar",
                    screenWidth/SCREEN_COLUMNS*7,
                    screenHeight/SCREEN_ROWS*3,
                    screenWidth/SCREEN_COLUMNS*11,
                    screenHeight/SCREEN_ROWS*4);
        }
        btnNewGame.draw(c);

        if(btnOptions==null){
            btnOptions = new ButtonCrsh(context,
                    Typeface.createFromAsset(context.getAssets(),"homespun.ttf"),
                    "Opciones",
                    screenWidth/SCREEN_COLUMNS,
                    screenHeight/SCREEN_ROWS*5,
                    screenWidth/SCREEN_COLUMNS*4,
                    screenHeight/SCREEN_ROWS*6);
        }
        btnOptions.draw(c);

        if(btnCredits==null){
            btnCredits = new ButtonCrsh(context,
                    Typeface.createFromAsset(context.getAssets(),"homespun.ttf"),
                    "Créditos",
                    screenWidth/SCREEN_COLUMNS*5,
                    screenHeight/SCREEN_ROWS*5,
                     screenWidth/SCREEN_COLUMNS*8,
                    screenHeight/SCREEN_ROWS*6);
        }
        btnCredits.draw(c);

        if(btnRecords==null){
            btnRecords = new ButtonCrsh(context,
                    Typeface.createFromAsset(context.getAssets(),"homespun.ttf"),
                    "Récords",
                    screenWidth/SCREEN_COLUMNS*10,
                    screenHeight/SCREEN_ROWS*5,
                    screenWidth/SCREEN_COLUMNS*13,
                    screenHeight/SCREEN_ROWS*6);
        }
        btnRecords.draw(c);

        if(btnTutorial==null){
            btnTutorial = new ButtonCrsh(context,
                    Typeface.createFromAsset(context.getAssets(),"homespun.ttf"),
                    "Tutorial",
                    screenWidth/SCREEN_COLUMNS*14,
                    screenHeight/SCREEN_ROWS*5,
                    screenWidth/SCREEN_COLUMNS*17,
                    screenHeight/SCREEN_ROWS*6);
        }
        btnTutorial.draw(c);
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

                if(isClick(btnNewGame,event)){
                    return 1;
                }else if(isClick(btnOptions,event)){
                    return 2;
                }else if(isClick(btnCredits,event)){
                    return 3;
                }else if(isClick(btnRecords,event)){
                    return 4;
                }else if(isClick(btnTutorial,event)){
                    return 5;
                }
                break;
            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
            default:  Log.i("Other", "Undefined action: "+action);
        }
        return this.id;
    }
}
