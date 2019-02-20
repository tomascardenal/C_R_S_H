package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Represents a drawable component for a SurfaceView GUI, and gameplay
 *
 * @author Tomás Cardenal López
 */
public abstract class DrawableComponent {

    /**
     * This component's context
     */
    protected Context context;
    /**
     * X coordinate
     */
    protected float xPos;
    /**
     * Y coordinate
     */
    protected float yPos;
    /**
     * Component height
     */
    public float height;
    /**
     * Component width
     */
    public float width;
    /**
     * Paint object for the text
     */
    protected Paint pText;

    /**
     * Draws the components on screen
     *
     * @param c the canvas to draw
     */
    public void draw(Canvas c) {
        //Override this method on inheritance
    }
}
