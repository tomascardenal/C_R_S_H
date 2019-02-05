package com.example.tomascrd.c_r_s_h;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * A button for the menus and interface for CRSH
 *
 * @author Tomás Cardenal
 */
public class ButtonCrsh extends DrawableComponent {

    /**
     * Width and height of the button
     */
    protected float width;
    protected float height;

    /**
     * Right and bottom coordinates of the Rect for this button
     */
    protected float xRight;
    protected float yBottom;

    /**
     * Rect to draw this button
     */
    protected Rect btnRect;

    /**
     * Text to show on this button
     */
    protected String text;

    /**
     * Painter for the button
     */
    protected Paint pButton;

    /**
     * Painter for the text
     */
    protected Paint pText;

    /**
     * Creates a Button with the given parameters
     * @param context the context
     * @param text the text within the button
     * @param xPos the button's x position
     * @param yPos the button's y position
     * @param xRight the button's bottom right corner x position
     * @param yBottom the button's bottom right corner y position
     */
    public ButtonCrsh (Context context, String text,float xPos, float yPos, float xRight, float yBottom){
        this.context = context;
        this.text = text;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xRight = xRight;
        this.yBottom = yBottom;
        this.btnRect = new Rect((int)xPos,(int)yPos,(int)xRight,(int)yBottom);
        pButton = new Paint();
        pButton.setColor(Color.GRAY);
        pText = new Paint();
        pText.setColor(Color.BLACK);
        pText.setTextAlign(Paint.Align.CENTER);
        pText.setTextSize(yBottom-yPos/2);
    }

    /**
     * Draws the components on the screen
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c){
        c.drawRect(btnRect,pButton);
        c.drawText(text,btnRect.centerX(),btnRect.centerY()+height/2, pText);
    }
}
