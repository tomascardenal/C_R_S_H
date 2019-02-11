package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

/**
 * A button for the menus and interface for CRSH
 *
 * @author Tomás Cardenal López
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
    protected int xRight;
    protected int yBottom;

    /**
     * Rect to draw this button
     */
    protected Rect btnRect;

    /**
     * Text to show on this button
     */
    protected String text;

    /**
     * Paint object for the button
     */
    protected Paint pButton;

    /**
     * Paint object for the text
     */
    protected Paint pText;

    /**
     * Creates a Button with the given parameters and gray background color
     *
     * @param context the context
     * @param text    the text within the button
     * @param xPos    the button's x position
     * @param yPos    the button's y position
     * @param xRight  the button's bottom right corner x position
     * @param yBottom the button's bottom right corner y position
     */
    public ButtonCrsh(Context context, Typeface font, String text, int xPos, int yPos, int xRight, int yBottom) {
        this.context = context;
        this.text = text;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xRight = xRight;
        this.yBottom = yBottom;
        this.width = xRight - xPos;
        this.height = yBottom - yPos;
        this.btnRect = new Rect(xPos, yPos, xRight, yBottom);
        pButton = new Paint();
        pButton.setColor(Color.GRAY);
        pText = new Paint();
        if (font != null) {
            pText.setTypeface(font);
        } else {
            pText.setTypeface(Typeface.DEFAULT_BOLD);
        }
        pText.setColor(Color.BLACK);
        pText.setTextAlign(Paint.Align.CENTER);
        pText.setTextSize(btnRect.height() / 2);
    }

    /**
     * Creates a Button with the given parameters, a background color must be given
     *
     * @param context    the context
     * @param text       the text within the button
     * @param xPos       the button's x position
     * @param yPos       the button's y position
     * @param xRight     the button's bottom right corner x position
     * @param yBottom    the button's bottom right corner y position
     * @param background the button's background color
     */
    public ButtonCrsh(Context context, Typeface font, String text, int xPos, int yPos, int xRight, int yBottom, int background) {
        this(context, font, text, xPos, yPos, xRight, yBottom);
        pButton.setColor(background);
    }

    /**
     * Draws the button on the screen
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        c.drawRect(btnRect, pButton);
        c.drawText(text, btnRect.centerX(), btnRect.centerY() + height / 6, pText);
    }
}
