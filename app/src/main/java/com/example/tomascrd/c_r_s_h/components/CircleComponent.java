package com.example.tomascrd.c_r_s_h.components;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Represents a circular component on the game
 *
 * @author Tomás Cardenal López
 */
public class CircleComponent extends DrawableComponent {

    /**
     * The radius of this circle
     */
    public int radius;
    /**
     * The painter for this circle
     */
    private Paint circlePaint;

    /**
     * Initializes a circle on the indicated parameters
     *
     * @param xPos   the circle's x Position
     * @param yPos   the circle's y Position
     * @param radius the circle's radius
     */
    public CircleComponent(float xPos, float yPos, int radius) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = radius;
        this.circlePaint = new Paint();
    }

    /**
     * Draws the circle
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        c.drawCircle(xPos, yPos, radius, circlePaint);
    }

    /**
     * Sets the color for this circle's painter
     *
     * @param color the color
     */
    public void setColor(int color) {
        this.circlePaint.setColor(color);
    }

    /**
     * Determines a collision between this and another circle
     *
     * @param otherCircle the other circle
     * @return whether there's been a collision
     */
    public boolean collision(CircleComponent otherCircle) {
        double xDif = otherCircle.xPos - this.xPos;
        double yDif = otherCircle.yPos - this.yPos;
        double distanceSquared = xDif * xDif + yDif * yDif;
        boolean collision = distanceSquared < (otherCircle.radius + this.radius) * (otherCircle.radius + this.radius);
        return collision;
    }

    /**
     * Determines a collision between this and a rectangle
     *
     * @param rect the rectangle
     * @return whethet there's been a collision
     */
    public boolean collision(Rect rect) {
        double distanceX = Math.abs(this.xPos - rect.centerX());
        double distanceY = Math.abs(this.yPos - rect.centerY());
        if (distanceX > (rect.width() / 2 + this.radius)) {
            return false;
        }
        if (distanceY > (rect.height() / 2 + this.radius)) {
            return false;
        }
        if (distanceX <= (rect.width() / 2)) {
            return true;
        }
        if (distanceY >= (rect.height() / 2)) {
            return true;
        }
        double cornerDistanceSquared = Math.pow(distanceX - rect.width() / 2, 2) + Math.pow(distanceY - rect.height() / 2, 2);
        return cornerDistanceSquared <= Math.pow(this.radius, 2);
    }
}
