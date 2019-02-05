package com.example.tomascrd.c_r_s_h;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class TileCrsh extends DrawableComponent {

    /**
     *  Enumerates the various types of tile
     */
    public static enum TILE_TYPE {
        TILE_PATH,
        TILE_NORMAL,
        TILE_STEEL,
        TILE_CRACKEDSTEEL,
        TILE_UNBREAKABLE
    };

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
     * Initializes a tile to it's parameters
     * @param context the context for this tile
     * @param drawable the drawable resource
     * @param xPos the tile's X position
     * @param yPos the tile's Y position
     * @param tileType this tile's type
     */
    public TileCrsh(Context context, int drawable, float xPos, float yPos, TILE_TYPE tileType){
        this.context = context;
        this.xPos = xPos;
        this.yPos = yPos;
        this.tileType = tileType;
    }

    /**
     * Sets the drawable as a Bitmap using BMPFactory
     * @param context
     * @param drawable
     */
    public void setTileImage(Context context, int drawable){
        //BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        //bmpOptions.inJustDecodeBounds = true;
        this.tileImage = BitmapFactory.decodeResource(context.getResources(), drawable);
    }

    /**
     * Gets this tile's bitmap
     * @return this bitmap
     */
    public Bitmap getTileImage(){
        return this.tileImage;
    }
    public boolean getCollision(int x, int y, int width, int height){
        return this.collisionRect.intersects(x,y,x+width,y+height);
    }

    /**
     * Gets this tile's type
     * @return this type
     */
    public TILE_TYPE getTileType() {
        return tileType;
    }

    /**
     * Indicates the new type for a tile
     * @param tileType the new type
     */
    public void setTileType(TILE_TYPE tileType) {
        this.tileType = tileType;
    }



}
