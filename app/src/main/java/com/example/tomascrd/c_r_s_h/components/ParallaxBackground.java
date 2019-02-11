package com.example.tomascrd.c_r_s_h.components;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;

/**
 * Represents a scrolling background for a horizontal parallax effect
 *
 * @author Tomás Cardenal López
 */
public class ParallaxBackground {
    /**
     * This background's position
     */
    public PointF position;
    /**
     * This background's image
     */
    public Bitmap image;

    /**
     * Initializes a background on the indicated positions
     *
     * @param image the image
     * @param x     the x coordinate
     * @param y     the y coordinate
     */
    public ParallaxBackground(Bitmap image, float x, float y) {
        this.image = image;
        this.position = new PointF(x, y);
    }

    /**
     * Initializes a background for the indicated screen width
     *
     * @param image       the image
     * @param screenWidth the screen width
     */
    public ParallaxBackground(Bitmap image, int screenWidth) {
        this(image, screenWidth - image.getWidth(), 0);
    }


    /**
     * Initializes a background for the indicated screen width and height
     *
     * @param image        the image
     * @param screenWidth  the screen width
     * @param screenHeight the screen height
     */
    public ParallaxBackground(Bitmap image, int screenWidth, int screenHeight) {
        this.image = image;
        this.position = new PointF(screenWidth - image.getWidth(), screenHeight - image.getHeight());
    }

    /**
     * Moves this backgrounds' x position the indicated velocity
     *
     * @param velocity
     */
    public void move(int velocity) {
        position.x += velocity;
    }

    public void reverseBitmap(boolean horizontally) {
        Matrix matrix = new Matrix();
        if (horizontally) {
            matrix.preScale(-1, 1);
        } else {
            matrix.preScale(1, -1);
        }
        this.image = Bitmap.createBitmap(this.image, 0, 0, image.getWidth(), image.getHeight(), matrix, false);
    }

}
