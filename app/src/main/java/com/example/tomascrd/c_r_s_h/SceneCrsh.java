package com.example.tomascrd.c_r_s_h;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Represents the general idea of a screen of the game
 *
 * @author Tomás Cardenal
 */
public class SceneCrsh {
    /**
     * This scene's context
     */
    protected Context context;
    /**
     * A back button, all scenes will have this button to fall back onto the main menu
     */
    ButtonCrsh backBtn;
    /**
     * This scene's id
     */
    protected int id;
    /**
     * The screen width and height
     */
    protected int screenWidth;
    protected int screenHeight;
    /**
     * If avaliable, this scene's background image
     */
    protected Bitmap backImage;


    /**
     * Starts a new scene
     * @param context the scene's context
     * @param id the scene's id
     * @param screenWidth the screen width
     * @param screenHeight the screen height
     */
    public SceneCrsh(Context context, int id, int screenWidth, int screenHeight) {
        this.context = context;
        this.id = id;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        if(backBtn==null){
            backBtn = new ButtonCrsh(context, null, "<=",screenWidth-screenWidth/16,0,screenWidth,screenWidth/16);
        }
    }

    /**
     * Controls touchscreen events and the actions to take if necessary
     * @param event the touchscreen event
     * @return this scene's id or the corresponding new id
     */
    public int onTouchEvent(MotionEvent event) {

        return getId();
    }

    /**
     * Updates the physics of the components on the screen
     */
    public void updatePhysics() {

    }

    /**
     * Draws the components
     * @param c the canvas to draw
     */
    public void draw(Canvas c) {

    }

    /**
     * Determines if a rectangle on a button is clicked
     * @param btn the button
     * @param event the motion event detected
     * @return true if the button was clicked
     */
    public boolean isClick(ButtonCrsh btn, MotionEvent event) {
        return btn.btnRect.contains((int) event.getX(), (int) event.getY());
    }

    /**
     * Gives back this context
     * @return the context
     */
    public Context getContext() {
        return this.context;
    }

    /**
     * Sets this scene's context
     * @param context the context to set
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Gets this scene's id
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gives this scene an id
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets this screen's width
     * @return the width
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Sets this screen's width
     * @param screenWidth the new width
     */
    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    /**
     * Gets this screens height
     * @return the height
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Sets this screen's height
     * @param screenHeight the new height
     */
    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    /**
     * Gets the background image from this scene
     * @return the background image
     */
    public Bitmap getBackImage() {
        return backImage;
    }

    /**
     * Sets a new background image for this scene
     * @param backImage the new background image
     */
    public void setBackImage(Bitmap backImage) {
        this.backImage = backImage;
    }
}
