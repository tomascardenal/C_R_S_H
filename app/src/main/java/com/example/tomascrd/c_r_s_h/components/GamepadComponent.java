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
 * @deprecated When the JoystickComponent was implemented on 17/02/2019
 */
@Deprecated
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
                    screenWidth - gridReference * 2, screenHeight - gridReference * 2, screenWidth, screenHeight,
                    Color.TRANSPARENT, 255,false);
            btnRight = new ButtonComponent(
                    context,
                    Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME),
                    context.getString(R.string.btnRight1),
                    screenWidth - gridReference * 2, screenHeight - gridReference * 4, screenWidth, screenHeight - gridReference * 2,
                    Color.TRANSPARENT, 255,false);
            btnUp = new ButtonComponent(
                    context,
                    Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME),
                    context.getString(R.string.btnUp1),
                    screenWidth - gridReference * 2, screenHeight - gridReference * 6, screenWidth, screenHeight - gridReference * 4,
                    Color.TRANSPARENT, 255,false);
            btnDown = new ButtonComponent(
                    context,
                    Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME),
                    context.getString(R.string.btnDown1),
                    screenWidth - gridReference * 2, screenHeight - gridReference * 8, screenWidth, screenHeight - gridReference * 6,
                    Color.TRANSPARENT, 255,false);
        } else if (playerId == 2) {
            btnLeft = new ButtonComponent(
                    context,
                    Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME),
                    context.getString(R.string.btnLeft2),
                    0, 0, gridReference * 2, gridReference * 2,
                    Color.TRANSPARENT, 255,false);
            btnRight = new ButtonComponent(
                    context,
                    Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME),
                    context.getString(R.string.btnRight2),
                    0, gridReference * 2, gridReference * 2, gridReference * 4,
                    Color.TRANSPARENT, 255,false);
            btnUp = new ButtonComponent(
                    context,
                    Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME),
                    context.getString(R.string.btnUp2),
                    0, gridReference * 4, gridReference * 2, gridReference * 6,
                    Color.TRANSPARENT, 255,false);

            btnDown = new ButtonComponent(
                    context,
                    Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME),
                    context.getString(R.string.btnDown2),
                    0, gridReference * 6, gridReference * 2, gridReference * 8,
                    Color.TRANSPARENT, 255,false);
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
