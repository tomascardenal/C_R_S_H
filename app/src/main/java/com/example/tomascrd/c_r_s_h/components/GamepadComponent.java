package com.example.tomascrd.c_r_s_h.components;

import android.graphics.Canvas;

/**
 * Represents a gamepad for the game
 *
 * @author Tomás Cardenal López
 */
public class GamepadComponent extends DrawableComponent {

    /**
     * Up button
     */
    public ButtonComponent upBtn;
    /**
     * Down button
     */
    public ButtonComponent downBtn;
    /**
     * Left button
     */
    public ButtonComponent leftBtn;
    /**
     * Right button
     */
    public ButtonComponent rightBtn;
    /**
     * The playerId to use this gamepad
     */
    public int playerId;

    /**
     * Initializes a gamepad for the given playerId
     * @param playerId the playerId
     */
    public GamepadComponent(int playerId){

    }

    /**
     * Draws the gamepad
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {

    }
}
