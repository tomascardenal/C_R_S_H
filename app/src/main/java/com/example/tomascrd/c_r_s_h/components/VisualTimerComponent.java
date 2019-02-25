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
     * The area to draw this timer to
     */
    private Rect area;
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
     */
    public VisualTimerComponent(Context c, MainGameScene gameCallback, Rect area) {
        this.context = c;
        this.gameCallback = gameCallback;
        this.area = area;
        this.leftTime = new Rect(area.left, area.top, area.right, area.bottom);
        this.pastTime = new Rect(area.left, area.top, area.left, area.bottom);
        this.speed = 1;


        this.borderPaint = new Paint();
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(gameCallback.tileSizeReference / 10);
        this.leftPaint = new Paint();
        this.leftPaint.setShader(new LinearGradient(this.area.left, this.area.top + this.area.height() / 2, this.area.right, this.area.bottom - this.area.height() / 2, Color.GREEN, Color.RED, Shader.TileMode.CLAMP));

        this.pastPaint = new Paint();
        this.pastPaint.setShader(new LinearGradient(this.area.left, this.area.top + this.area.height() / 2, this.area.right, this.area.bottom - this.area.height() / 2, Color.BLACK, Color.WHITE, Shader.TileMode.CLAMP));
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
    }

    /**
     * Sets the timer speed
     *
     * @speed the new speed
     */
    public void setTimerSpeed(int speed) {
        this.speed = speed;
    }
}