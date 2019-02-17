package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

/**
 * A joystick controller for the game
 *
 * @author Tomás Cardenal López
 */
public class JoystickComponent extends DrawableComponent {

    /**
     * The circle for this joystick's area
     */
    private CircleComponent joystickArea;
    /**
     * The circle for this joystick's handle
     */
    private CircleComponent joystickHandle;
    /**
     * Whether the joystick is currently active or not
     */
    private boolean active;
    /**
     * This joystick's maximum radius
     */
    private int maxRadius;
    /**
     * This joystick's threshold
     */
    private float threshold;

    /**
     * Initializes a new Joystick
     * @param context the context
     * @param maxRadius the maximum radius
     * @param areaColor the color of the joystick's area
     * @param handleColor the color of the joystick's handle
     */
    public JoystickComponent(Context context, int maxRadius, int areaColor, int handleColor) {
        this.context = context;
        this.maxRadius = maxRadius;

        this.joystickArea = new CircleComponent(0, 0, maxRadius);
        this.joystickArea.setColor(areaColor);
        this.joystickArea.setDrawingAlpha(150);

        this.joystickHandle = new CircleComponent(0, 0, maxRadius / 3);
        this.joystickHandle.setColor(handleColor);
        this.joystickHandle.setDrawingAlpha(200);

        this.threshold = maxRadius/3;
    }


    /**
     * Draws this joystick
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        if (active) {
            this.joystickHandle.draw(c);
            this.joystickArea.draw(c);
        }
    }

    /**
     * Resets this joystick's center positions to draw
     *
     * @param xPos the new x position
     * @param yPos the new y position
     */
    public void resetPosition(float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.joystickArea.resetPosition(this.xPos, this.yPos);
        this.joystickHandle.resetPosition(this.xPos, this.yPos);
    }

    /**
     * Moves this joystick's handle and constrains it
     * @param xHandle the new x coordinate for the handle
     * @param yHandle the new y coordinate for the handle
     */
    public void moveHandle(float xHandle, float yHandle){
        float movement = (float) Math.sqrt(Math.pow(xHandle-xPos,2)+Math.pow(yHandle-yPos,2));
        float hypotenuseRatio = maxRadius / movement;
        float xDisplacement = (this.joystickHandle.xPos-xPos)/maxRadius;
        float yDisplacement = (this.joystickHandle.yPos-yPos)/maxRadius;
        Log.i("Handle moving: ","X: "+ xDisplacement+ " Y: "+yDisplacement);
        if(movement<maxRadius){
            this.joystickHandle.resetPosition(xHandle,yHandle);
        }else{
            this.joystickHandle.resetPosition(xPos + (xHandle - xPos) * hypotenuseRatio,yPos + (yHandle - yPos) * hypotenuseRatio);
        }
    }

    /**
     * Gives back a boolean indicating this joystick's status
     *
     * @return whether the joystick is currently active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets this joystick's activity
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gives back the value of the maximum radius
     *
     * @return the maximum radius
     */
    public int getMaxRadius() {
        return maxRadius;
    }

    /**
     * Sets the value of the maximum radius
     *
     * @param maxRadius the new maximum radius
     */
    public void setMaxRadius(int maxRadius) {
        this.maxRadius = maxRadius;
    }

    public void onTouchEvent(MotionEvent e) {
        if (!active) {
            active = true;
            resetPosition(e.getX(e.getActionIndex()), e.getY(e.getActionIndex()));
        } else {
            moveHandle(e.getX(e.getActionIndex()), e.getY(e.getActionIndex()));
        }
    }

    /**
     * Returns a PointF with x and y values for the displacement between 0 and 1
     * @return the PointF representing the displacement
     */
    public PointF getDisplacement(){
        return new PointF((this.joystickHandle.xPos-xPos)/maxRadius,(this.joystickHandle.yPos-yPos)/maxRadius);
    }
}
