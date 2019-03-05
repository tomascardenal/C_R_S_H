package com.example.tomascrd.c_r_s_h.components;

import android.graphics.Bitmap;
import android.graphics.PointF;

/**
 * Represents a background component with the ability to scroll
 *
 * @author Tomás Cardenal López
 */
public class BackgroundComponent {

    /**
     * This backgrounds' position
     */
    public PointF position;
    /**
     * This backgrounds' image
     */
    public Bitmap image;

    /**
     * Initializes a background for the indicated screen width and height
     *
     * @param image        the image
     * @param screenWidth  the screen width
     * @param screenHeight the screen height
     */
    public BackgroundComponent(Bitmap image, int screenWidth, int screenHeight) {
        this.image = image;
        this.position = new PointF(screenWidth - image.getWidth(), screenHeight - image.getHeight());
    }

    /**
     * Moves this backgrounds' x position the indicated velocity
     *
     * @param velocity the velocity at which this background moves
     */
    public void moveX(int velocity) {
        position.x += velocity;
    }
}
