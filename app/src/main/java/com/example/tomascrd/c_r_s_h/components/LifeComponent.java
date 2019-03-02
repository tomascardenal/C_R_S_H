package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;

import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.scenes.MainGameScene;

/**
 * Represents a visual component representing the life of the player
 *
 * @author Tomás Cardenal López
 */
public class LifeComponent extends DrawableComponent {

    /**
     * The area to draw this component into
     */
    private Rect area;
    /**
     * The area to draw the life left
     */
    private Rect leftRect;
    /**
     * The area to draw the life lost
     */
    private Rect lostRect;
    /**
     * The border for this component
     */
    private Rect borderRect;
    /**
     * Paint for the area of left life
     */
    private Paint leftPaint;
    /**
     * Paint for the border
     */
    private Paint borderPaint;
    /**
     * Paint for the area of lost life
     */
    private Paint lostPaint;
    /**
     * Paint for player text
     */
    private Paint playerPaint;
    /**
     * Number of lives to divide this into
     */
    private int initialLifes;
    /**
     * Number of remaining lifes
     */
    private int lifesLeft;
    /**
     * Check for losing life animation
     */
    private boolean losingLife;
    /**
     * Callback to the game
     */
    private MainGameScene gameCallback;
    /**
     * Counter for one life effect
     */
    private int oneLifeCounter;
    /**
     * Indicates whether this Life Component is on the right or the left side of the screen
     */
    private boolean rightSide;

    /**
     * Represents a visual element to show the life of the player
     *
     * @param c            The current context
     * @param gameCallback Callback to this main game
     * @param area         The area to draw this component into
     * @param lives        The number of lifes of this life component
     * @param rightSide    Whether this Life Component is on the right or the left side of the screen
     */
    public LifeComponent(Context c, MainGameScene gameCallback, Rect area, int lives, boolean rightSide, int player) {
        this.context = c;
        this.gameCallback = gameCallback;
        this.area = area;
        this.initialLifes = lives;
        this.lifesLeft = lives;
        this.losingLife = false;

        this.rightSide = rightSide;

        this.leftPaint = new Paint();

        this.lostPaint = new Paint();
        this.lostPaint.setColor(Color.BLACK);

        this.playerPaint = new Paint();
        this.playerPaint.setColor(Color.WHITE);
        this.playerPaint.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));

        this.borderPaint = new Paint();
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setColor(Color.DKGRAY);
        this.borderPaint.setStrokeWidth(gameCallback.tileSizeReference / 5);

        resetLife();

        this.borderRect = new Rect(
                (int) (area.left + borderPaint.getStrokeWidth() / 2),
                (int) (area.top + borderPaint.getStrokeWidth() / 2),
                (int) (area.right - borderPaint.getStrokeWidth() / 2),
                (int) (area.bottom - borderPaint.getStrokeWidth() / 2)
        );
    }

    /**
     * Makes this player lose a life on this lifecomponent
     */
    public void loseALife() {
        lifesLeft--;
        if (lifesLeft > 0 && !losingLife) {
            losingLife = true;
        }
    }

    /**
     * Resets the lifecomponent
     */
    public void resetLife() {
        this.leftRect = new Rect(area.left, area.top, area.right, area.bottom);
        if (rightSide) {
            this.lostRect = new Rect(area.left, area.top, area.left, area.bottom);
        } else {
            this.lostRect = new Rect(area.right, area.top, area.right, area.bottom);
        }
        this.leftPaint.setColor(Color.BLUE);
        this.lostPaint.setColor(Color.BLACK);
        this.lifesLeft = this.initialLifes;
        oneLifeCounter = 0;
        this.losingLife = false;
    }

    /**
     * Draws the lifecomponent on screen
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        if (losingLife) {
            losingCycle();
        }
        c.drawRect(lostRect, lostPaint);
        c.drawRect(leftRect, leftPaint);
        c.drawRect(borderRect, borderPaint);
    }

    /**
     * Starts the cycle for representing losing a life
     */
    private void losingCycle() {
        lostPaint.setColor(Color.BLACK);
        if (rightSide) {
            if (lifesLeft == 3) {
                if (leftRect.width() > area.width() / 3 * 2) {
                    rightInset();
                }
            } else if (lifesLeft == 2) {
                if (leftRect.width() > area.width() / 3) {
                    rightInset();
                    leftPaint.setColor(Color.YELLOW);
                }
            } else if (lifesLeft == 1) {
                if (leftRect.width() > 0) {
                    rightInset();
                }
            }
        } else {
            if (lifesLeft == 3) {
                if (leftRect.width() > area.width() / 3 * 2) {
                    leftInset();
                }
            } else if (lifesLeft == 2) {
                if (leftRect.width() > area.width() / 3) {
                    leftInset();
                    leftPaint.setColor(Color.YELLOW);
                }
            } else if (lifesLeft == 1) {
                if (leftRect.width() > 0) {
                    leftInset();
                }
            }
        }
        if (lifesLeft == 1) {
            oneLifeCounter++;
            if (oneLifeCounter % 15 == 0) {
                lostPaint.setColor(lostPaint.getColor() == Color.BLACK ? Color.RED : Color.BLACK);
            }
        }
    }


    private void rightInset() {
        leftRect.inset(3, 0);
        leftRect.offset(3, 0);
        lostRect.inset(-3, 0);
        lostRect.offset(3, 0);
    }

    private void leftInset() {
        leftRect.inset(3, 0);
        leftRect.offset(-3, 0);
        lostRect.inset(-3, 0);
        lostRect.offset(-3, 0);
    }
}
