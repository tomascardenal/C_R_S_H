package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tomascrd.c_r_s_h.R;

/**
 * Loads data from assets
 */
public class AssetLoader {
    /**
     * Border tile bitmap
     */
    public Bitmap tileBorder;
    /**
     * Path tile bitmap
     */
    public Bitmap tilePath;
    /**
     * Breakone tile bitmap
     */
    public Bitmap tileBreakOne;
    /**
     * Breaktwo tile bitmap
     */
    public Bitmap tileBreakTwo;

    /**
     * Initializes a new asset loader
     *
     * @param context the application context
     */
    public AssetLoader(Context context) {
        this.tileBorder = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_border);
        this.tilePath = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_path);
        this.tileBreakOne = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_breakone);
        this.tileBreakTwo = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_breaktwo);
    }

    /**
     * Scales the bitmaps and prepares them to be drawn on screen
     *
     * @param width
     * @param height
     */
    public void scaleBitmaps(int width, int height) {
        this.tileBorder = Bitmap.createScaledBitmap(this.tileBorder, width, height, true);
        this.tilePath = Bitmap.createScaledBitmap(this.tilePath, width, height, true);
        this.tileBreakOne = Bitmap.createScaledBitmap(this.tileBreakOne, width, height, true);
        this.tileBreakTwo = Bitmap.createScaledBitmap(this.tileBreakTwo, width, height, true);
    }
}
