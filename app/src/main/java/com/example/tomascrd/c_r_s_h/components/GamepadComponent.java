package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.core.GameConstants;

/**
 * Represents a gamepad for the game
 *
 * @author Tomás Cardenal López
 */
public class GamepadComponent extends DrawableComponent {

    /**
     * Up button
     */
    public ButtonComponent btnUp;
    /**
     * Down button
     */
    public ButtonComponent btnDown;
    /**
     * Left button
     */
    public ButtonComponent btnLeft;
    /**
     * Right button
     */
    public ButtonComponent btnRight;
    /**
     * The playerId to use this gamepad
     */
    public int playerId;
    /**
     * The screen width
     */
    public int screenWidth;
    /**
     * The screen height
     */
    public int screenHeight;
    /**
     * The grid reference
     */
    public int gridReference;

    /**
     * Initializes a gamepad for the given playerId and map
     *
     * @param context       the application context
     * @param playerId      the playerId
     * @param screenHeight  the screen height
     * @param screenWidth   the screen width
     * @param gridReference the grid size reference
     */
    public GamepadComponent(Context context, int playerId, int screenHeight, int screenWidth, int gridReference) {
        this.context = context;
        this.playerId = playerId;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.gridReference = gridReference;
        if (playerId == 1) {
            btnLeft = new ButtonComponent(
                    context,
                    Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME),
                    context.getString(R.string.btnLeft1),
                    screenWidth - gridReference * 2, screenHeight - gridReference, screenWidth-gridReference, screenHeight,
                    Color.TRANSPARENT);
            btnDown = new ButtonComponent(
                    context,
                    Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME),
                    context.getString(R.string.btnDown1),
                    screenWidth - gridReference, screenHeight - gridReference * 2, screenWidth, screenHeight - gridReference,
                    Color.TRANSPARENT);
            btnRight = new ButtonComponent(
                    context,
                    Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME),
                    context.getString(R.string.btnRight1),
                    screenWidth - gridReference * 2, screenHeight - gridReference * 3, screenWidth-gridReference, screenHeight - gridReference * 2,
                    Color.TRANSPARENT);
            btnUp = new ButtonComponent(
                    context,
                    Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME),
                    context.getString(R.string.btnUp1),
                    screenWidth - gridReference * 3, screenHeight - gridReference * 2, screenWidth - gridReference*2, screenHeight - gridReference,
                    Color.TRANSPARENT);
        } else if (playerId == 2) {
            btnLeft = new ButtonComponent(
                    context,
                    Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME),
                    context.getString(R.string.btnLeft2),
                    gridReference,0,gridReference*2,gridReference,
                    Color.TRANSPARENT);
            btnDown = new ButtonComponent(
                    context,
                    Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME),
                    context.getString(R.string.btnDown2),
                    0,gridReference,gridReference,gridReference*2,
                    Color.TRANSPARENT);
            btnRight = new ButtonComponent(
                    context,
                    Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME),
                    context.getString(R.string.btnRight2),
                    gridReference,gridReference*2,gridReference*2,gridReference*3,
                    Color.TRANSPARENT);
            btnUp = new ButtonComponent(
                    context,
                    Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME),
                    context.getString(R.string.btnUp2),
                    gridReference*2,gridReference,gridReference*3,gridReference*2,
                    Color.TRANSPARENT);
        }

    }

    /**
     * Draws the gamepad
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        btnLeft.draw(c);
        btnDown.draw(c);
        btnRight.draw(c);
        btnUp.draw(c);
    }
}
