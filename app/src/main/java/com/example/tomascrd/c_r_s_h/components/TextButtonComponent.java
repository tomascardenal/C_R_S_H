package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
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
     * Creates a TextButton with the given parameters and gray background color
     *
     * @param context   the context
     * @param font      the font to use on this button
     * @param text      the text within the button
     * @param xPos      the TextButton's x position
     * @param yPos      the TextButton's y position
     * @param xRight    the TextButton's bottom right corner x position
     * @param yBottom   the TextButton's bottom right corner y position
     * @param sideText  the TextButton's sideText
     * @param sideFont  the sideText font
     * @param textAlign the TextButton's sideText alignment
     * @param xPadding  the TextButton's x padding
     * @param borderRectX the TextButton's border right coordinate
     */
    public TextButtonComponent(Context context, Typeface font, String text, int xPos, int yPos, int xRight, int yBottom, String sideText, Typeface sideFont, TEXT_ALIGN textAlign, int xPadding, int borderRectX) {
        super(context, font, text, xPos, yPos, xRight, yBottom);
        this.setSideText(sideText);
        this.sideFont = sideFont;
        this.textAlign = textAlign;

        this.sideTextPaint = new Paint();
        this.sideTextPaint.setTextSize(btnRect.height() / 2);
        this.sideTextPaint.setTypeface(sideFont);

        this.borderPaint = new Paint();
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(10);

        this.xPadding = xPadding;
        this.borderRectX = borderRectX;
    }

    /**
     * Creates a TextButton with the given parameters and the given background color
     *
     * @param context    the context
     * @param font       the font to use on this button
     * @param text       the text within the button
     * @param xPos       the TextButton's x position
     * @param yPos       the TextButton's y position
     * @param xRight     the TextButton's bottom right corner x position
     * @param yBottom    the TextButton's bottom right corner y position
     * @param background the TextButton's button part background color
     * @param sideText   the TextButton's sideText
     * @param sideFont   the sideText font
     * @param textAlign  the TextButton's sideText alignment
     * @param xPadding   the TextButton's total width
     * @param borderRectX the TextButton's borderRectX
     */
    public TextButtonComponent(Context context, Typeface font, String text, int xPos, int yPos, int xRight, int yBottom, int background, String sideText, Typeface sideFont, TEXT_ALIGN textAlign, int xPadding, int borderRectX) {
        super(context, font, text, xPos, yPos, xRight, yBottom, background);
        this.setSideText(sideText);
        this.sideFont = sideFont;
        this.textAlign = textAlign;

        sideTextPaint = new Paint();
        sideTextPaint.setTextSize(btnRect.height() / 2);
        sideTextPaint.setTypeface(sideFont);

        this.borderPaint = new Paint();
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(10);

        this.xPadding = xPadding;
        this.borderRectX = borderRectX;
    }

    /**
     * Calls the ButtonComponent draw function and then draws the text
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        super.draw(c);
        int textX, textY, halfBtnWidth, halfBtnHeight;
        Rect borderRect;
        if (textAlign == TEXT_ALIGN.ALIGN_LEFT) {
            textX = (int) (btnRect.exactCenterX() - (btnRect.width() / 2) - sideTextPaint.measureText(getSideText()));
            textY = (int) (btnRect.exactCenterY() + height / 6);
            borderRect = new Rect(borderRectX-xPadding, btnRect.top, btnRect.right + xPadding, btnRect.bottom);
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
