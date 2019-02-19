package com.example.tomascrd.c_r_s_h.components;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Represents a circle component on the game with the ability to move and be painted
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
        this.width = radius * 2;
        this.height = this.width;
        this.circlePaint = new Paint();
    }

    /**
     * Initializes a circle on the given PointF center, and radius
     *
     * @param center the circle's center point
     * @param radius the circle's radius
     */
    public CircleComponent(PointF center, int radius) {
        this(center.x, center.y, radius);
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
        //Differences between the circle center positions
        double xDif = otherCircle.xPos - this.xPos;
        double yDif = otherCircle.yPos - this.yPos;
        //Squaring the distances
        double distanceSquared = xDif * xDif + yDif * yDif;
        //If the square distance is bigger than the square of the radiuses, there's a collision
        return distanceSquared < (otherCircle.radius + this.radius) * (otherCircle.radius + this.radius);
    }

    /**
     * Determines a collision between this circle and a rectangle
     *
     * @param rect the rectangle
     * @return whether there's been a collision
     */
    public boolean collision(Rect rect) {
        //Distance between the centers of the rectangle and the center of this circle
        double distanceX = Math.abs(this.xPos - rect.centerX());
        double distanceY = Math.abs(this.yPos - rect.centerY());
        //If the distance between X coordinates is bigger than the half-width of the rectangle plus this radius, there's no collision
        if (distanceX > (rect.width() / 2 + this.radius)) {
            return false;
        }
        //If the distance between Y coordinates is bigger than the half-width of the rectangle plus this radius, there's no collision
        if (distanceY > (rect.height() / 2 + this.radius)) {
            return false;
        }
        //If the distance between X coordinates is smaller or equal to the half-width of the rectangle, there's a collision
        if (distanceX <= (rect.width() / 2)) {
            return true;
        }
        //If the distance between Y coordinates is smaller or equal to the half-width of the rectangle, there's a collision
        if (distanceY >= (rect.height() / 2)) {
            return true;
        }
        //Checking corners
        double cornerDistanceSquared = Math.pow(distanceX - rect.width() / 2, 2) + Math.pow(distanceY - rect.height() / 2, 2);
        //If the cornerSquared is smaller or equal to this radius^2
        return cornerDistanceSquared <= Math.pow(this.radius, 2);
    }

    /**
     * Moves this circle component
     *
     * @param distanceX the distance to moveX on the X axis
     * @param distanceY the distance to moveX on the Y axis
     */
    public void move(float distanceX, float distanceY) {
        this.xPos += distanceX;
        this.yPos += distanceY;
    }

    /**
     * Resets this circle's position
     *
     * @param xPos the x coordinate for the new position
     * @param yPos the y coordinate for the new position
     */
    public void resetPosition(float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * Sets this paint's alpha value
     *
     * @param alpha the new alpha value
     */
    public void setDrawingAlpha(int alpha) {
        this.circlePaint.setAlpha(alpha);
    }
}
