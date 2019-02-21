package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.tomascrd.c_r_s_h.R;

/**
 * Represents a tile in the game
 *
 * @author Tomás Cardenal López
 */
public class TileComponent extends DrawableComponent {

    /**
     * Enumerates the various types of tile
     */
    public enum TILE_TYPE {
        TILE_BORDER,    //0, border tile
        TILE_PATH,      //1, path tile
        TILE_BREAKONE,  //2, one hit breakable tile
        TILE_BREAKTWO,  //3, two hit breakable tile
    }

    /**
     * Painter for the rects
     */
    public Paint rectPaint;
    /**
     * This tile's type
     */
    protected TILE_TYPE tileType;
    /**
     * This tile's collision rectangle
     */
    protected Rect collisionRect = null;
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
        this.height = collisionRect.height();
        this.width = collisionRect.width();
        this.rectPaint = new Paint();
        setPainter();
    }

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
        this(context, drawable, new Rect(xPos, yPos, xBottom, yBottom), tileType);
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

    @Override
    public void draw(Canvas c) {

    }

    /**
     * Sets the painter color (for test maps only)
     */
    public void setPainter() {
        switch (this.tileType) {
            case TILE_BORDER:
                rectPaint.setColor(Color.BLACK);
                break;
            case TILE_PATH:
                rectPaint.setColor(Color.YELLOW);
                break;
            case TILE_BREAKONE:
                rectPaint.setColor(Color.LTGRAY);
                break;
            case TILE_BREAKTWO:
                rectPaint.setColor(Color.GRAY);
        }
    }

    /**
     * Fetches the collision rectangle of this tile
     *
     * @return this tile's collision rectangle
     */
    public Rect getCollisionRect() {
        return this.collisionRect;
    }


    /**
     * Indicates if there's a collision between a rect on the given parameters
     *
     * @param x      top right x coordinate
     * @param y      top right y coordinate
     * @param width  the rect's width
     * @param height the rect's height
     * @return
     */
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


}