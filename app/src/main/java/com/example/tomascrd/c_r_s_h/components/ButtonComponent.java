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
     * Paint object for the border on the clicking effect
     */
    protected Paint pClickBorder;
    /**
     * Paint object for the shadow on the clicking effect
     */
    protected Paint pClickShadow;
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
     * Alpha value
     */
    private int alpha;
    /**
     * Id to return when changing scenes linked by this button, should be -1 if no scene change is planned
     */
    private int sceneId;

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
     * @param sceneId     the id to return when changing scenes, -1 if no scene change is planned for this button
     */
    public ButtonComponent(Context context, Typeface font, String text, int xPos, int yPos, int xRight, int yBottom, boolean clickEffect, int sceneId) {
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
        this.sceneId = sceneId;

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
            pClickShadow = new Paint();
            pClickShadow.setColor(Color.GRAY);

            this.strokeWidth = this.btnRect.height() / 20;
            pClickBorder = new Paint();
            pClickBorder.setColor(Color.rgb(169, 169, 169));
            pClickBorder.setStyle(Paint.Style.STROKE);
            pClickBorder.setStrokeWidth(this.strokeWidth);

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
     * @param sceneId     the id to return when changing scenes, -1 if no scene change is planned for this button
     */
    public ButtonComponent(Context context, Typeface font, String text, int xPos, int yPos, int xRight, int yBottom, int background, int alpha, boolean clickEffect, int sceneId) {
        this(context, font, text, xPos, yPos, xRight, yBottom, clickEffect, sceneId);
        //Reset color and alpha
        this.alpha = alpha;
        pButton.setColor(background);
        pButton.setAlpha(this.alpha);
        if (clickEffect) {
            pClickShadow.setAlpha((int) this.alpha / 2);
        }
    }

    /**
     * Draws the button on the screen
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        if (isHeldDown() && clickEffect) {
            c.drawRect(btnRect.left + this.strokeWidth * 2, btnRect.top + this.strokeWidth * 2, btnRect.right + this.strokeWidth * 2, btnRect.bottom + this.strokeWidth * 2, pClickShadow);
            c.drawRect(btnRect, pButton);
            c.drawRect(btnRect.left + (this.strokeWidth / 2), btnRect.top + (this.strokeWidth / 2), btnRect.right - (this.strokeWidth / 2), btnRect.bottom - (this.strokeWidth / 2), pClickBorder);
            c.drawText(getText(), btnRect.centerX(), btnRect.centerY() + height / 6, pText);
            pButton.setAlpha(255);
        } else {
            pButton.setAlpha(this.alpha);
            c.drawRect(btnRect, pButton);
            c.drawText(getText(), btnRect.centerX(), btnRect.centerY() + height / 6, pText);
        }

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
     * @return the button state
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

    /**
     * Returns the value of the sceneId to change to
     *
     * @return the value of the sceneId
     */
    public int getSceneId() {
        return this.sceneId;
    }

    /**
     * Sets the value of the sceneId to change to, -1 if it shouldn't change
     *
     * @param sceneId the new value of the sceneId of this button
     */
    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }
}
