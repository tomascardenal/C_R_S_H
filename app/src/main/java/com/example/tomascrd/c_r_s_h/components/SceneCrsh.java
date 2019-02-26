package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.GameEngine;

import java.util.Calendar;

/**
 * Represents the general idea of a screen of the game
 *
 * @author Tomás Cardenal López
 */
public class SceneCrsh {
    /**
     * This scene's context
     */
    public Context context;
    /**
     * A back button, all scenes will have this button to fall back onto the main menu
     */
    public ButtonComponent backBtn;
    /**
     * This scene's id
     */
    public int id;
    /**
     * The screen width
     */
    public int screenWidth;
    /**
     * The screen height
     */
    public int screenHeight;
    /**
     * If avaliable, this scene's background image
     */
    public Bitmap backImage;
    /**
     * Callback to access the game engine, not used in default constructor
     */
    protected GameEngine engineCallback;
    /**
     * Paint for gradient backgrounds
     */
    protected Paint gradientPaint;
    /**
     * Title text painter
     */
    protected Paint pTitleText;
    /**
     * Reference for the tile size, indicates how much the side of the tile measures
     */
    public int tileSizeReference;
    /**
     * Previous hour to see if we change gradients
     */
    private int previousHour;
    /**
     * Gradient during the day
     */
    private LinearGradient gradientDay;
    /**
     * Gradient during the night
     */
    private LinearGradient gradientNight;
    /**
     * Gradient during sunrise
     */
    private LinearGradient gradientSunrise;
    /**
     * Gradient during nightfall
     */
    private LinearGradient gradientNightFall;


    /**
     * Starts a new scene
     *
     * @param context      the scene's context
     * @param id           the scene's id
     * @param screenWidth  the screen width
     * @param screenHeight the screen height
     */
    public SceneCrsh(Context context, int id, int screenWidth, int screenHeight) {
        this.context = context;
        this.id = id;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        //Back button
        if (backBtn == null) {
            backBtn = new ButtonComponent(context,
                    Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME), context.getString(R.string.btnBack),
                    screenWidth - screenWidth / 16, 0, screenWidth, screenWidth / 16, Color.TRANSPARENT, 0, false, 0);
        }

        this.previousHour = -1;

        this.gradientPaint = new Paint();

        int[] gradientColors = {Color.BLUE, Color.YELLOW, Color.CYAN};
        float[] positions = {0, screenWidth / 2, screenWidth};
        gradientSunrise = new LinearGradient(0, 0, screenWidth, screenHeight, gradientColors, positions, Shader.TileMode.CLAMP);

        int[] gradientColors2 = {Color.rgb(17,	30,	108), Color.DKGRAY, Color.CYAN};
        float[] positions2 = {0, screenWidth / 2, screenWidth};
        gradientNight = new LinearGradient(0, 0, screenWidth, screenHeight, gradientColors2, positions2, Shader.TileMode.CLAMP);

        int[] gradientColors3 = {Color.rgb(0,	178,	255), Color.YELLOW, Color.CYAN};
        float[] positions3 = {0, screenWidth / 2, screenWidth};
        gradientDay = new LinearGradient(0, 0, screenWidth, screenHeight, gradientColors3, positions3, Shader.TileMode.CLAMP);

        int[] gradientColors4 = {Color.BLUE, Color.RED, Color.CYAN};
        float[] positions4 = {0, screenWidth / 2, screenWidth};
        gradientNightFall = new LinearGradient(0, 0, screenWidth, screenHeight, gradientColors4, positions4, Shader.TileMode.CLAMP);

        setSkyBackground();
    }

    /**
     * Sets the sky on the background depending on the the system hour
     */
    public void setSkyBackground() {

        Calendar now = Calendar.getInstance();
        int currentHour = now.get(Calendar.HOUR_OF_DAY);
        if (currentHour <= 6 || currentHour >= 21) {
            if (!(previousHour <= 6) && !(previousHour >= 21) || previousHour == -1) {
                this.gradientPaint.setShader(gradientNight);
                previousHour = currentHour;
            }
        } else if (currentHour > 8 && currentHour < 20) {
            if (!(previousHour > 8) && !(previousHour < 20) || previousHour == -1) {
                this.gradientPaint.setShader(gradientDay);
                previousHour = currentHour;

            }
        } else if (currentHour >= 7 && currentHour <= 8) {
            if (!(previousHour == 7) && !(previousHour == 8) || previousHour == -1) {
                this.gradientPaint.setShader(gradientSunrise);
                previousHour = currentHour;

            }
        } else if (currentHour == 20) {
            if (!(previousHour == 20) || previousHour == -1) {
                this.gradientPaint.setShader(gradientNightFall);
                previousHour = currentHour;
            }
        }
        previousHour = currentHour;
    }

    /**
     * Controls touchscreen events and the actions to take if necessary
     *
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
        setSkyBackground();
    }

    /**
     * Draws the components
     *
     * @param c the canvas to draw
     */
    public void draw(Canvas c) {
        //General background
        c.drawPaint(gradientPaint);
    }

    /**
     * Determines if a rectangle on a button is clicked by the ActionIndex of the event
     *
     * @param btn   the button
     * @param event the motion event detected
     * @return true if the button was clicked
     */
    public boolean isClick(ButtonComponent btn, MotionEvent event) {
        return btn.btnRect.contains((int) event.getX(event.getActionIndex()), (int) event.getY(event.getActionIndex()));
    }

    /**
     * Determines if a rectangle is clicked by the ActionIndex of the event
     *
     * @param rect  the rect
     * @param event the motion event detected
     * @return true if the rect was clicked
     */
    public boolean isClick(Rect rect, MotionEvent event) {
        return rect.contains((int) event.getX(event.getActionIndex()), (int) event.getY(event.getActionIndex()));
    }

    /**
     * Determines if a rectangle on a button is clicked by any of the pointers acting on the event
     *
     * @param btn   the button
     * @param event the event
     * @return true if the button was clicked or touched by any of the pointers
     */
    public boolean isClickByAny(ButtonComponent btn, MotionEvent event) {
        for (int i = 0; i < event.getPointerCount(); i++) {
            if (btn.btnRect.contains((int) event.getX(i), (int) event.getY(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gives back this context
     *
     * @return the context
     */
    public Context getContext() {
        return this.context;
    }

    /**
     * Sets this scene's context
     *
     * @param context the context to set
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Gets this scene's id
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gives this scene an id
     *
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets this screen's width
     *
     * @return the width
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Sets this screen's width
     *
     * @param screenWidth the new width
     */
    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    /**
     * Gets this screens height
     *
     * @return the height
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Sets this screen's height
     *
     * @param screenHeight the new height
     */
    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    /**
     * Gets the background image from this scene
     *
     * @return the background image
     */
    public Bitmap getBackImage() {
        return backImage;
    }

    /**
     * Sets a new background image for this scene
     *
     * @param backImage the new background image
     */
    public void setBackImage(Bitmap backImage) {
        this.backImage = backImage;
    }
}
