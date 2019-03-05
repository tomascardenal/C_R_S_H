package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.tomascrd.c_r_s_h.structs.TileTypes;

/**
 * Represents a tile in the game
 *
 * @author Tomás Cardenal López
 */
public class TileComponent extends DrawableComponent {

    /**
     * This tile's type
     */
    protected TileTypes.eTileType tileType;
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
    public TileComponent(Context context, int drawable, Rect collisionRect, TileTypes.eTileType tileType) {
        this.context = context;
        this.tileType = tileType;
        this.collisionRect = collisionRect;
        this.xPos = collisionRect.left;
        this.yPos = collisionRect.top;
        this.xBottom = collisionRect.right;
        this.yBottom = collisionRect.bottom;
        this.height = collisionRect.height();
        this.width = collisionRect.width();
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
    public TileComponent(Context context, int drawable, int xPos, int yPos, int xBottom, int yBottom, TileTypes.eTileType tileType) {
        this(context, drawable, new Rect(xPos, yPos, xBottom, yBottom), tileType);
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
     * @return true if there's collision
     */
    public boolean getCollision(int x, int y, int width, int height) {
        return this.collisionRect.intersects(x, y, x + width, y + height);
    }

    /**
     * Gets this tile's type
     *
     * @return this type
     */
    public TileTypes.eTileType getTileType() {
        return tileType;
    }

    /**
     * Indicates the new type for a tile
     *
     * @param tileType the new type
     */
    public void setTileType(TileTypes.eTileType tileType) {
        this.tileType = tileType;
    }

}