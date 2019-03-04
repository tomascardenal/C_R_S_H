package com.example.tomascrd.c_r_s_h.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.structs.PowerUps;

/**
 * Loads data from assets
 *
 * @author Tomás Cardenal López
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
     * Bitmaps for the powerups
     */
    private Bitmap[] powerUpBitmaps;
    /**
     * Indicates if the powerups were loaded
     */
    private boolean loadedPowerUps;
    /**
     * Indicates if the tiles were loaded
     */
    private boolean loadedTiles;
    /**
     * Indicates if the attack/defense indicators were loaded
     */
    private boolean indicatorsLoaded;
    /**
     * Indicates if the player images were loaded or not
     */
    private boolean playersLoaded;
    /**
     * Indicates the previous loaded sky hour
     */
    public int previousHour;
    /**
     * Defense player bitmaps
     */
    public Bitmap[] defensePlayerBitmap;
    /**
     * Attack player bitmaps
     */
    public Bitmap[] attackPlayerBitmap;


    /**
     * Initializes a new asset loader
     *
     * @param context the application context
     */
    public AssetLoader(Context context) {
        this.context = context;
        this.loadedTiles = false;
        this.indicatorsLoaded = false;
        this.playersLoaded = false;
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
     * Loads and scales the bitmaps for the players
     *
     * @param width  the width to scale to
     * @param height the height to scale to
     */
    public void loadPlayerImages(int width, int height) {
        this.attackPlayerBitmap = new Bitmap[3];
        this.attackPlayerBitmap[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pl_com_attack), width, height, true);
        this.attackPlayerBitmap[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pl_blue_attack), width, height, true);
        this.attackPlayerBitmap[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pl_red_attack), width, height, true);
        this.defensePlayerBitmap = new Bitmap[3];
        this.defensePlayerBitmap[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pl_com_defense), width, height, true);
        this.defensePlayerBitmap[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pl_blue_defense), width, height, true);
        this.defensePlayerBitmap[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pl_red_defense), width, height, true);
        playersLoaded = true;
    }

    /**
     * Loads and scales the powerup bitmaps with the given parameters
     *
     * @param width  the width of the bitmaps
     * @param height the height of the bitmaps
     */
    public void loadPowerUps(int width, int height) {
        this.powerUpBitmaps = new Bitmap[5];
        this.powerUpBitmaps[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pwer_timer_speed_up), width, height, true);
        this.powerUpBitmaps[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pwer_no_bounceback), width, height, true);
        this.powerUpBitmaps[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pwer_invincible), width, height, true);
        this.powerUpBitmaps[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pwer_slow_opponent), width, height, true);
        this.powerUpBitmaps[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pwer_slow_myself), width, height, true);
        loadedPowerUps = true;
    }

    /**
     * Gets a bitmap for a powerup corresponding to a value of the powerup enumerate
     *
     * @param powerUp a powerup from the enumerate
     * @return a bitmap corresponding with the parameter
     * @throws NullPointerException if the bitmaps aren't loaded yet
     * @see PowerUps.ePowerUp
     */
    public Bitmap getPowerUpBitmap(PowerUps.ePowerUp powerUp) {
        if (!loadedPowerUps) {
            throw new NullPointerException("You need to load the images first and give a width and height, call loadPowerUps(int,int)");
        }
        switch (powerUp) {
            case POWERUP_TIMER_STOP:
                return this.powerUpBitmaps[0];
            case POWERUP_NO_BOUNCEBACK:
                return this.powerUpBitmaps[1];
            case POWERUP_INVINCIBLE:
                return this.powerUpBitmaps[2];
            case POWERUP_SLOW_OPPONENT:
                return this.powerUpBitmaps[3];
            case POWERUP_SLOW_MYSELF:
                return this.powerUpBitmaps[4];
        }
        return null;
    }

    /**
     * Informs if the indicators were already loaded
     *
     * @return the value of the boolean
     */
    public boolean areIndicatorsLoaded() {
        return indicatorsLoaded;
    }

    /**
     * Informs if the player images were already loaded
     *
     * @return the value of the boolean
     */
    public boolean arePlayersLoaded() {
        return playersLoaded;
    }

    /**
     * Informs if the powerup images were already loaded
     *
     * @return the value of the boolean
     */
    public boolean arePowerUpsLoaded() {
        return loadedPowerUps;
    }

    /**
     * Informs if the tile images were already loaded
     *
     * @return the value of the boolean
     */
    public boolean areTilesLoaded() {
        return loadedTiles;
    }
}
