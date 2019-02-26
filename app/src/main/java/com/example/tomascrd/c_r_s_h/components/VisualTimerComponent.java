package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.Log;

import com.example.tomascrd.c_r_s_h.scenes.MainGameScene;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A game component to time a turn switch and implement it's drawing on screen
 * This timer is dependent on the size of two rects which is inset and offset in the update function (to be called by a timer or a thread)
 *
 * @author Tomás Cardenal López
 */
public class VisualTimerComponent extends DrawableComponent {

    /**
     * Describes the possible timer speeds to set
     */
    public enum TIMER_SPEED {
        TIMER_SLOW,
        TIMER_MID,
        TIMER_FAST,
        TIMER_OHNO
    }

    /**
     * The area to draw this timer to
     */
    private Rect area;
    /**
     * The border rectangle
     */
    private Rect borderRect;
    /**
     * The area to show the time left
     */
    private Rect leftTime;
    /**
     * The area to show the past time
     */
    private Rect pastTime;
    /**
     * Paint for leftTime
     */
    private Paint leftPaint;
    /**
     * Paint for pastTime
     */
    private Paint pastPaint;
    /**
     * Paint for the border¿¿
     */
    private Paint borderPaint;
    /**
     * Game callback
     */
    private MainGameScene gameCallback;
    /**
     * Timer speed
     */
    private int speed;

    /**
     * Instances a new VisualTimerComponent
     *
     * @param c            The context for the Timer
     * @param gameCallback The current game instance
     * @param area         The area to draw this timer to
     * @param speed        The initial speed of this timer
     */
    public VisualTimerComponent(Context c, MainGameScene gameCallback, Rect area, TIMER_SPEED speed) {
        this.context = c;
        this.gameCallback = gameCallback;
        this.area = area;

        this.leftTime = new Rect(area.left, area.top, area.right, area.bottom);
        this.pastTime = new Rect(area.left, area.top, area.left, area.bottom);
        setTimerSpeed(speed);

        this.borderPaint = new Paint();
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setColor(Color.DKGRAY);
        this.borderPaint.setStrokeWidth(gameCallback.tileSizeReference / 10);

        this.borderRect = new Rect(
                (int) (area.left + borderPaint.getStrokeWidth() / 2),
                (int) (area.top + borderPaint.getStrokeWidth() / 2),
                (int) (area.right - borderPaint.getStrokeWidth() / 2),
                (int) (area.bottom - borderPaint.getStrokeWidth() / 2)
        );


        this.leftPaint = new Paint();
        this.leftPaint.setShader(new LinearGradient(this.area.left, this.area.top + this.area.height() / 2, this.area.right, this.area.bottom - this.area.height() / 2, Color.GREEN, Color.RED, Shader.TileMode.CLAMP));

        this.pastPaint = new Paint();
        this.pastPaint.setShader(new LinearGradient(this.area.left, this.area.top + this.area.height() / 2, this.area.right, this.area.bottom - this.area.height() / 2, Color.BLACK, Color.WHITE, Shader.TileMode.CLAMP));
    }

    /**
     * Transforms a TIMER_SPEED into an int
     *
     * @param timerSpeed the TIMER_SPEED to parse
     * @return the int corresponding to the TIMER_SPEED
     */
    public static int timerSpeedToInt(TIMER_SPEED timerSpeed) {
        switch (timerSpeed) {
            case TIMER_SLOW:
                return 0;
            case TIMER_MID:
                return 1;
            case TIMER_FAST:
                return 2;
            case TIMER_OHNO:
                return 3;
        }
        return -1;
    }

    /**
     * Transforms an int into a TIMER_SPEED
     *
     * @param timerSpeed the int to parse
     * @return the TIMER_SPEED corresponding to the int
     */
    public static TIMER_SPEED intToTimerSpeed(int timerSpeed) {
        switch (timerSpeed) {
            case 0:
                return TIMER_SPEED.TIMER_SLOW;
            case 1:
                return TIMER_SPEED.TIMER_MID;
            case 2:
                return TIMER_SPEED.TIMER_FAST;
            case 3:
                return TIMER_SPEED.TIMER_OHNO;
        }
        return null;
    }

    /**
     * Updates the timer
     */
    public void updateTimer() {
        if (!gameCallback.isOnPause()) {
            if (leftTime.left < leftTime.right) {
                leftTime.inset(speed, 0);
                leftTime.offset(speed, 0);
                pastTime.inset(-speed, 0);
                pastTime.offset(speed, 0);
            } else {
                resetTimer();
                gameCallback.togglePlayerModes();
            }
        }
    }

    /**
     * Resets the timer
     */
    public void resetTimer() {
        pastTime = new Rect(area.left, area.top, area.left, area.bottom);
        leftTime = new Rect(area.left, area.top, area.right, area.bottom);
    }


    /**
     * Draws the timer on screen
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        c.drawRect(pastTime, pastPaint);
        c.drawRect(leftTime, leftPaint);
        c.drawRect(borderRect, borderPaint);
    }

    /**
     * Sets the timer speed
     *
     * @speed the new speed
     */
    public void setTimerSpeed(TIMER_SPEED speed) {
        switch (speed) {
            case TIMER_SLOW:
                this.speed = 1;
                break;
            case TIMER_MID:
                this.speed = 2;
                break;
            case TIMER_FAST:
                this.speed = 3;
                break;
            case TIMER_OHNO:
                this.speed = 5;
                break;
        }
    }
}
