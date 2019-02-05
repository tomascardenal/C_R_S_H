package com.example.tomascrd.c_r_s_h;

import android.graphics.Bitmap;
import android.graphics.PointF;

public class ParallaxBackground {
    public PointF position;
    public Bitmap image;

    public ParallaxBackground(Bitmap image, float x, float y) {
        this.image = image;
        this.position = new PointF(x, y);
    }

    public ParallaxBackground(Bitmap image, int screenWidth) {
        this(image, screenWidth - image.getWidth(), 0);
    }

    public void move(int velocity) {
        position.x += velocity;
    }


}
