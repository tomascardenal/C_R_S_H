package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
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
     * The event's pointer id
     */
    private int pointerId;

    /**
     * Initializes a new Joystick
     *
     * @param context     the context
     * @param maxRadius   the maximum radius
     * @param areaColor   the color of the joystick's area
     * @param handleColor the color of the joystick's handle
     */
    public JoystickComponent(Context context, int maxRadius, int areaColor, int handleColor) {
        this.context = context;
        this.maxRadius = maxRadius;

        //Outer circle
        this.joystickArea = new CircleComponent(0, 0, maxRadius);
        this.joystickArea.setColor(areaColor);
        this.joystickArea.setDrawingAlpha(150);

        //Inner circle
        this.joystickHandle = new CircleComponent(0, 0, maxRadius / 3);
        this.joystickHandle.setColor(handleColor);
        this.joystickHandle.setDrawingAlpha(200);
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
     *
     * @param xHandle the new x coordinate for the handle
     * @param yHandle the new y coordinate for the handle
     */
    public void moveHandle(float xHandle, float yHandle) {
        //Calculate the displacement between the new coordinates
        float movement = (float) Math.sqrt(Math.pow(xHandle - xPos, 2) + Math.pow(yHandle - yPos, 2));
        //Ratio for constraining the movement
        float hypotenuseRatio = maxRadius / movement;
        //If the movement doesn't exceed the ratio, just reset it on the given new coordinates
        if (movement < maxRadius) {
            this.joystickHandle.resetPosition(xHandle, yHandle);
            //If the movement exceeds the ratio, constrain it on the ratio
        } else {
            this.joystickHandle.resetPosition(xPos + (xHandle - xPos) * hypotenuseRatio, yPos + (yHandle - yPos) * hypotenuseRatio);
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
     * Deactivates the joystick
     */
    public void deactivate() {
        this.active = false;
    }

    /**
     * Activates the joystick with the event's parameters
     *
     * @param e the event which activated the joystick
     */
    public void activateJoystick(MotionEvent e) {
        if (!active) {
            active = true;
            this.pointerId = e.getPointerId(e.getActionIndex());
            resetPosition(e.getX(e.getActionIndex()), e.getY(e.getActionIndex()));
        }
    }

    /**
     * Moves the handle of the joystick on the correspondent pointer id
     *
     * @param e           the event triggering the movement
     * @param limit       the limit for the area of move events
     * @param joystickOne whether this is the joystick number one or two
     */
    public void onMoveEvent(MotionEvent e, float limit, boolean joystickOne) {
        if (joystickOne) {
            for (int i = 0; i < e.getPointerCount(); i++) {
                if (e.getPointerId(i) == this.getPointerId()) {
                    if (e.getX(i) < limit) {
                        moveHandle(limit, e.getY(i));
                    } else {
                        moveHandle(e.getX(i), e.getY(i));
                    }
                }
            }
        } else {
            for (int i = 0; i < e.getPointerCount(); i++) {
                if (e.getPointerId(i) == this.getPointerId()) {
                    if (e.getX(i) > limit) {
                        moveHandle(limit, e.getY(i));
                    } else {
                        moveHandle(e.getX(i), e.getY(i));
                    }
                }
            }
        }
    }

    /**
     * Returns a PointF with x and y values for the displacement between 0 and 1
     *
     * @return the PointF representing the displacement
     */
    public PointF getDisplacement() {
        float xDisplacement = (this.joystickHandle.xPos - xPos) / maxRadius;
        float yDisplacement = (this.joystickHandle.yPos - yPos) / maxRadius;
        return new PointF(xDisplacement, yDisplacement);
    }

    /**
     * Gives the current value of the pointerId
     *
     * @return this pointer id
     */
    public int getPointerId() {
        return this.pointerId;
    }
}
