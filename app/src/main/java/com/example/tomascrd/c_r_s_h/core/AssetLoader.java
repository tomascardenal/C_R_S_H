package com.example.tomascrd.c_r_s_h.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tomascrd.c_r_s_h.R;

/**
 * Loads data from assets
 */
public class AssetLoader {
    /**
     * Application context
     */
    private Context context;
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
     * Grass tiles
     */
    public Bitmap tileGrassMid;
    /**
     * Grass tiles, upper side
     */
    public Bitmap tileGrassUp;
    /**
     * Grass tiles, down side
     */
    public Bitmap tileGrassDown;
    /**
     * Grass tiles, left side
     */
    public Bitmap tileGrassLeft;
    /**
     * Grass tiles, right side
     */
    public Bitmap tileGrassRight;
    /**
     * Grass tiles, up left corner
     */
    public Bitmap tileGrassUpLeft;
    /**
     * Grass tiles, up right corner
     */
    public Bitmap tileGrassUpRight;
    /**
     * Grass tiles, down left corner
     */
    public Bitmap tileGrassDownLeft;
    /**
     * Grass tiles, down right corner
     */
    public Bitmap tileGrassDownRight;
    /**
     * Defense indicator
     */
    public Bitmap indicatorDefenseBitmap;
    /**
     * Attack indicators
     */
    public Bitmap[] indicatorAttackBitmaps;
    /**
     * Indicates if the tiles were loaded
     */
    public boolean loadedTiles;
    /**
     * Indicates if the attack/defense indicators were loaded
     */
    private boolean indicatorsLoaded;
    /**
     * Indicates the previous loaded sky hour
     */
    public int previousHour;


    /**
     * Initializes a new asset loader
     *
     * @param context the application context
     */
    public AssetLoader(Context context) {
        this.context = context;
        this.loadedTiles = false;
        this.indicatorsLoaded = false;
        this.previousHour = -1;
    }

    /**
     * Scales the bitmaps and prepares them to be drawn on screen
     *
     * @param width  the width to scale to
     * @param height the height to scale to
     */
    public void scaleTileBitmaps(int width, int height) {
        this.tileBorder = Bitmap.createScaledBitmap(this.tileBorder, width, height, true);
        this.tilePath = Bitmap.createScaledBitmap(this.tilePath, width, height, true);
        this.tileBreakOne = Bitmap.createScaledBitmap(this.tileBreakOne, width, height, true);
        this.tileBreakTwo = Bitmap.createScaledBitmap(this.tileBreakTwo, width, height, true);
        this.tileGrassMid = Bitmap.createScaledBitmap(this.tileGrassMid, width, height, true);
        this.tileGrassUp = Bitmap.createScaledBitmap(this.tileGrassUp, width, height, true);
        this.tileGrassDown = Bitmap.createScaledBitmap(this.tileGrassDown, width, height, true);
        this.tileGrassLeft = Bitmap.createScaledBitmap(this.tileGrassLeft, width, height, true);
        this.tileGrassRight = Bitmap.createScaledBitmap(this.tileGrassRight, width, height, true);
        this.tileGrassUpLeft = Bitmap.createScaledBitmap(this.tileGrassUpLeft, width, height, true);
        this.tileGrassUpRight = Bitmap.createScaledBitmap(this.tileGrassUpRight, width, height, true);
        this.tileGrassDownLeft = Bitmap.createScaledBitmap(this.tileGrassDownLeft, width, height, true);
        this.tileGrassDownRight = Bitmap.createScaledBitmap(this.tileGrassDownRight, width, height, true);
    }


    /**
     * Loads the bitmaps for the tiles
     */
    public void loadTiles() {
        this.tileBorder = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_border);
        this.tilePath = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_path);
        this.tileBreakOne = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_breakone);
        this.tileBreakTwo = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_breaktwo);
        this.tileGrassMid = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_grass_genial);
        this.tileGrassUp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_grass_up);
        this.tileGrassDown = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_grass_down);
        this.tileGrassLeft = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_grass_left);
        this.tileGrassRight = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_grass_right);
        this.tileGrassUpLeft = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_grass_upleft);
        this.tileGrassUpRight = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_grass_upright);
        this.tileGrassDownLeft = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_grass_downleft);
        this.tileGrassDownRight = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tile_grass_downright);
        loadedTiles = true;
    }

    /**
     * Loads and scales the bitmaps for the indicators
     *
     * @param width  the width to scale to
     * @param height the height to scale to
     */
    public void loadIndicators(int width, int height) {
        this.indicatorAttackBitmaps = new Bitmap[3];
        this.indicatorDefenseBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.indicator_defense), width, height, true);
        this.indicatorAttackBitmaps[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.indicator_attack1), width, height, true);
        this.indicatorAttackBitmaps[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.indicator_attack2), width, height, true);
        this.indicatorAttackBitmaps[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.indicator_attack3), width, height, true);
        indicatorsLoaded = true;
    }

    /**
     * Informs if the indicators were already loaded
     *
     * @return the value of the boolean
     */
    public boolean areIndicatorsLoaded() {
        return indicatorsLoaded;
    }
}
