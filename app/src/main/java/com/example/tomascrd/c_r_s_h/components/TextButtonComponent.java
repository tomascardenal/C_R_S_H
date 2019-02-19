package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

/**
 * A ButtonComponent with text attached to it's side, defined as "sideText", mainly used with FontAwesome icons
 *
 * @author Tomás Cardenal López
 */
public class TextButtonComponent extends ButtonComponent {

    /**
     * The possible text alignments
     */
    public enum TEXT_ALIGN {
        ALIGN_RIGHT, ALIGN_LEFT
    }

    /**
     * This button's sideText alignment
     */
    protected TEXT_ALIGN textAlign;
    /**
     * This buttons sideText
     */
    private String sideText;
    /**
     * The sideText font
     */
    protected Typeface sideFont;
    /**
     * The sideText painter
     */
    private Paint sideTextPaint;
    /**
     * The border painter
     */
    private Paint borderPaint;
    /**
     * This component's xPadding, creates a padding on the border
     */
    private int xPadding;
    /**
     * This component's border x Coordinate, right X for align left, left X for align right
     */
    private int borderRectX;

    /**
     * Creates a TextButton with the given parameters and the given background color
     *
     * @param context     the context
     * @param font        the font to use on this button
     * @param text        the text within the button
     * @param xLeft       the TextButton's ButtonComponent left x position
     * @param yTop        the TextButton's ButtonComponent top y position
     * @param xRight      the TextButton's ButtonComponent right x position
     * @param yBottom     the TextButton's ButtonComponent bottom y position
     * @param background  the TextButton's ButtonComponent background color
     * @param alpha       the Textbutton's ButtonComponent alpha
     * @param sideText    the TextButton's sideText
     * @param sideFont    the sideText font
     * @param textAlign   the TextButton's sideText alignment
     * @param xPadding    the TextButton's total width
     * @param borderRectX the TextButton's borderRectX
     */
    public TextButtonComponent(Context context, Typeface font, String text, int xLeft, int yTop, int xRight, int yBottom, int background, int alpha, String sideText, Typeface sideFont, TEXT_ALIGN textAlign, int xPadding, int borderRectX) {
        super(context, font, text, xLeft, yTop, xRight, yBottom, background, alpha);
        this.setSideText(sideText);
        this.sideFont = sideFont;
        this.textAlign = textAlign;
        this.xPadding = xPadding;
        this.borderRectX = borderRectX;

        //Side text painter
        this.sideTextPaint = new Paint();
        this.sideTextPaint.setTextSize(btnRect.height() / 2);
        this.sideTextPaint.setTypeface(sideFont);

        //Outer border painter
        this.borderPaint = new Paint();
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(10);
        this.borderPaint.setAlpha(150);

    }

    /**
     * Creates a TextButton with the given parameters and gray background color with max alpha
     *
     * @param context     the context
     * @param font        the font to use on this button
     * @param text        the text within the button
     * @param xLeft       the TextButton's ButtonComponent left x position
     * @param yTop        the TextButton's ButtonComponent top y position
     * @param xRight      the TextButton's ButtonComponent right x position
     * @param yBottom     the TextButton's ButtonCompoonent bottom y position
     * @param sideText    the TextButton's sideText
     * @param sideFont    the sideText font
     * @param textAlign   the TextButton's sideText alignment
     * @param xPadding    the TextButton's x padding
     * @param borderRectX the TextButton's border right coordinate
     */
    public TextButtonComponent(Context context, Typeface font, String text, int xLeft, int yTop, int xRight, int yBottom, String sideText, Typeface sideFont, TEXT_ALIGN textAlign, int xPadding, int borderRectX) {
        this(context, font, text, xLeft, yTop, xRight, yBottom, Color.GRAY, 255, sideText, sideFont, textAlign, xPadding, borderRectX);
    }

    /**
     * Calls the ButtonComponent draw function and then draws the text
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //Draw the button component
        super.draw(c);
        //Draw the text aligned as indicated
        int textX, textY;
        Rect borderRect;
        if (textAlign == TEXT_ALIGN.ALIGN_LEFT) {
            textX = (int) borderRectX;
            textY = (int) (btnRect.exactCenterY() + height / 6);
            borderRect = new Rect(borderRectX - xPadding, btnRect.top, btnRect.right + xPadding, btnRect.bottom);
        } else {
            textX = (int) (btnRect.exactCenterX() + (btnRect.width() / 2));
            textY = (int) (btnRect.exactCenterY() + height / 6);
            borderRect = new Rect(this.btnRect.left - xPadding, this.btnRect.top, borderRectX + xPadding, (int) (btnRect.centerY() - height / 2));
        }
        c.drawText(getSideText(), textX, textY, sideTextPaint);
        c.drawRect(borderRect, borderPaint);
    }

    /**
     * This buttons sideText
     *
     * @return the sideText
     */
    public String getSideText() {
        return sideText;
    }

    /**
     * Sets this button sideText
     *
     * @param sideText the new sideText
     */
    public void setSideText(String sideText) {
        this.sideText = sideText;
    }
}
