package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
     */
    public TextButtonComponent(Context context, Typeface font, String text, int xPos, int yPos, int xRight, int yBottom, String sideText, Typeface sideFont, TEXT_ALIGN textAlign) {
        super(context, font, text, xPos, yPos, xRight, yBottom);
        this.setSideText(sideText);
        this.sideFont = sideFont;
        this.textAlign = textAlign;
        sideTextPaint = new Paint();
        sideTextPaint.setTextSize(btnRect.height() / 2);
        sideTextPaint.setTypeface(sideFont);

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
     */
    public TextButtonComponent(Context context, Typeface font, String text, int xPos, int yPos, int xRight, int yBottom, int background, String sideText, Typeface sideFont, TEXT_ALIGN textAlign) {
        super(context, font, text, xPos, yPos, xRight, yBottom, background);
        this.setSideText(sideText);
        this.sideFont = sideFont;
        this.textAlign = textAlign;
        sideTextPaint = new Paint();
        sideTextPaint.setTextSize(btnRect.height() / 2);
        sideTextPaint.setTypeface(sideFont);
    }

    /**
     * Calls the ButtonComponent draw function and then draws the text
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        super.draw(c);
        if (textAlign == TEXT_ALIGN.ALIGN_LEFT) {
            c.drawText(getSideText(), btnRect.exactCenterX() - (btnRect.width() / 2) - sideTextPaint.measureText(getSideText()), btnRect.centerY() + height / 6, sideTextPaint);
        } else {
            c.drawText(getSideText(), btnRect.exactCenterX() + (btnRect.width() / 2), btnRect.centerY() + height / 6, sideTextPaint);
        }
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
