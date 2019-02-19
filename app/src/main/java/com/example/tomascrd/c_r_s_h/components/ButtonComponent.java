package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

//TODO Implement animated button with different rect border drawings

/**
 * A button for the menus and interface for CRSH
 *
 * @author Tomás Cardenal López
 */
public class ButtonComponent extends DrawableComponent {

    /**
     * Right coordinate for this button's Rect
     */
    protected int xRight;
    /**
     * Bottom coordinate for this button's Rect
     */
    protected int yBottom;
    /**
     * Rect to draw this button
     */
    protected Rect btnRect;
    /**
     * Text to show on this button
     */
    private String text;
    /**
     * Paint object for the button
     */
    protected Paint pButton;
    /**
     * Paint object for the text
     */
    protected Paint pText;
    /**
     * Paint object for the clicking effect
     */
    protected Paint pClick;
    /**
     * Checks if the button is being held down
     */
    protected boolean heldDown;
    /**
     * Boolean which indicates if the clickeffect should be on or not
     */
    protected boolean clickEffect;
    /**
     * Stroke width for click effect
     */
    private float strokeWidth;

    /**
     * Creates a Button with the given parameters and gray background color
     *
     * @param context     the context
     * @param font        the font to use on this button
     * @param text        the text within the button
     * @param xPos        the button's x position
     * @param yPos        the button's y position
     * @param xRight      the button's bottom right corner x position
     * @param yBottom     the button's bottom right corner y position
     * @param clickEffect if this button should have the click effect or not
     */
    public ButtonComponent(Context context, Typeface font, String text, int xPos, int yPos, int xRight, int yBottom, boolean clickEffect) {
        //Initialize parameters
        this.context = context;
        this.setText(text);
        this.xPos = xPos;
        this.yPos = yPos;
        this.xRight = xRight;
        this.yBottom = yBottom;
        this.width = xRight - xPos;
        this.height = yBottom - yPos;
        this.btnRect = new Rect(xPos, yPos, xRight, yBottom);
        this.clickEffect = clickEffect;

        //Painter for the button
        pButton = new Paint();
        pButton.setColor(Color.GRAY);

        //Painter for the text
        pText = new Paint();
        if (font != null) {
            pText.setTypeface(font);
        } else {
            pText.setTypeface(Typeface.DEFAULT_BOLD);
        }
        pText.setColor(Color.BLACK);
        pText.setTextAlign(Paint.Align.CENTER);
        pText.setTextSize(btnRect.height() / 2);

        //Clickeffect
        if (clickEffect) {
            this.strokeWidth = this.btnRect.height() / 20;
            pClick = new Paint();
            pClick.setColor(Color.DKGRAY);
            pClick.setStyle(Paint.Style.STROKE);
            pClick.setStrokeWidth(this.strokeWidth);
        }
    }

    /**
     * Creates a Button with the given parameters, a background color and alpha must be given
     *
     * @param context     the context
     * @param font        the font to use on this button
     * @param text        the text within the button
     * @param xPos        the button's x position
     * @param yPos        the button's y position
     * @param xRight      the button's bottom right corner x position
     * @param yBottom     the button's bottom right corner y position
     * @param background  the button's background color
     * @param alpha       the button's background alpha
     * @param clickEffect if this button should have the click effect or not
     */
    public ButtonComponent(Context context, Typeface font, String text, int xPos, int yPos, int xRight, int yBottom, int background, int alpha, boolean clickEffect) {
        this(context, font, text, xPos, yPos, xRight, yBottom, clickEffect);
        //Reset color and alpha
        pButton.setColor(background);
        pButton.setAlpha(alpha);
    }

    /**
     * Draws the button on the screen
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        if (isHeldDown() && clickEffect) {
            c.drawRect(btnRect.left - this.strokeWidth, btnRect.top - this.strokeWidth, btnRect.right + this.strokeWidth, btnRect.bottom + this.strokeWidth, pClick);
        }
        c.drawRect(btnRect, pButton);
        c.drawText(getText(), btnRect.centerX(), btnRect.centerY() + height / 6, pText);
    }

    /**
     * Returns the texts on this button
     *
     * @return the text on the button
     */
    public String getText() {
        return text;
    }

    /**
     * Sets a new text on this button
     *
     * @param text the new text to show
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Checks if the button state is heldDown
     *
     * @return
     */
    public boolean isHeldDown() {
        return heldDown;
    }

    /**
     * Set the holdDown state of this button
     *
     * @param heldDown the new state
     */
    public void setHeldDown(boolean heldDown) {
        this.heldDown = heldDown;
    }
}
