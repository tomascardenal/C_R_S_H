package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;

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
     * X and Y coordinates
     */
    protected int xPos;
    protected int yPos;

    /**
     * Draws the components on screen
     * @param c the canvas to draw
     */
    public void draw(Canvas c){
        //Override this method on inheritance
    }
}
