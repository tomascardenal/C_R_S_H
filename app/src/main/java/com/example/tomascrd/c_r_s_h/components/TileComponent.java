package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Represents a tile in the game
 *
 * @author Tomás Cardenal López
 */
public class TileComponent extends DrawableComponent {

    /**
     * Painter for test maps
     */
    public Paint testPaint;
    /**
     * This tile's type
     */
    protected TILE_TYPE tileType;
    /**
     * This tile's collision rectangle
     */
    protected Rect collisionRect = null;
    /**
     * This tile's bitmap
     */
    protected Bitmap tileImage;
    /**
     * This tile's Rect right bottom x coordinate
     */
    protected int xBottom;
    /**
     * This tile's Rect right bottom y coordinate
     */
    protected int yBottom;

    /**
     * Initializes a tile to it's parameters
     *
     * @param context  The application's context
     * @param drawable The tile's drawable, -1 for no drawable
     * @param xPos     The tile's xPos, used as the Rect's top left x coordinate
     * @param yPos     The tile's yPos, used as the Rect's top left y coordinate
     * @param xBottom  The Rect's bottom right x coordinate
     * @param yBottom  The Rect's bottom right y coordinate
     * @param tileType The tile's type
     */
    public TileComponent(Context context, int drawable, int xPos, int yPos, int xBottom, int yBottom, TILE_TYPE tileType) {
        this.context = context;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xBottom = xBottom;
        this.yBottom = yBottom;
        this.tileType = tileType;
        this.collisionRect = new Rect(xPos, yPos, xBottom, yBottom);
        this.testPaint = new Paint();
        setPainter();

    }

    /**
     * Initializes a tile to it's parameters
     *
     * @param context       The application's context
     * @param drawable      The tile's drawable, -1 for no drawable
     * @param collisionRect The tile's collisionRect
     * @param tileType      The tile's type
     */
    public TileComponent(Context context, int drawable, Rect collisionRect, TILE_TYPE tileType) {
        this.context = context;
        this.tileType = tileType;
        this.collisionRect = collisionRect;
        this.xPos = collisionRect.left;
        this.yPos = collisionRect.top;
        this.xBottom = collisionRect.right;
        this.yBottom = collisionRect.bottom;
        this.testPaint = new Paint();
        setPainter();
    }

    /**
     * Converts integers to TILE_TYPE
     *
     * @param tileType the TILE_TYPE to convert
     * @return the corresponding integer
     * @see TILE_TYPE
     */
    public static int tileTypeToInt(TILE_TYPE tileType) {
        switch (tileType) {
            case TILE_BORDER:
                return 0;
            case TILE_PATH:
                return 1;
            case TILE_BREAKONE:
                return 2;
            case TILE_BREAKTWO:
                return 3;
            default:
                return -1;
        }
    }

    /**
     * Converts TILE_TYPEs to integer
     *
     * @param tileType the integer to convert
     * @return the corresponding TILE_TYPE
     * @see TILE_TYPE
     */
    public static TILE_TYPE intToTileType(int tileType) {
        switch (tileType) {
            case 0:
                return TILE_TYPE.TILE_BORDER;
            case 1:
                return TILE_TYPE.TILE_PATH;
            case 2:
                return TILE_TYPE.TILE_BREAKONE;
            case 3:
                return TILE_TYPE.TILE_BREAKTWO;
            default:
                return null;
        }
    }

    /**
     * Sets the painter color (for test maps only)
     */
    public void setPainter() {
        switch (this.tileType) {
            case TILE_BORDER:
                testPaint.setColor(Color.BLACK);
                break;
            case TILE_PATH:
                testPaint.setColor(Color.YELLOW);
                break;
            case TILE_BREAKONE:
                testPaint.setColor(Color.LTGRAY);
                break;
            case TILE_BREAKTWO:
                testPaint.setColor(Color.GRAY);
        }
    }

    public Rect getCollisionRect() {
        return this.collisionRect;
    }

    /**
     * Sets the drawable as a Bitmap using BMPFactory
     *
     * @param context
     * @param drawable
     */
    public void setTileImage(Context context, int drawable) {
        //BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        //bmpOptions.inJustDecodeBounds = true;
        this.tileImage = BitmapFactory.decodeResource(context.getResources(), drawable);
    }

    /**
     * Gets this tile's bitmap
     *
     * @return this bitmap
     */
    public Bitmap getTileImage() {
        return this.tileImage;
    }

    public boolean getCollision(int x, int y, int width, int height) {
        return this.collisionRect.intersects(x, y, x + width, y + height);
    }

    /**
     * Gets this tile's type
     *
     * @return this type
     */
    public TILE_TYPE getTileType() {
        return tileType;
    }

    /**
     * Indicates the new type for a tile
     *
     * @param tileType the new type
     */
    public void setTileType(TILE_TYPE tileType) {
        this.tileType = tileType;
    }

    /**
     * Enumerates the various types of tile
     */
    public enum TILE_TYPE {
        TILE_BORDER,    //0, border tile
        TILE_PATH,      //1, path tile
        TILE_BREAKONE,  //2, one hit breakable tile
        TILE_BREAKTWO,  //3, two hit breakable tile
    }
}