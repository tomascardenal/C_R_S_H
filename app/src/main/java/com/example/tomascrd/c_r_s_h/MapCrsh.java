package com.example.tomascrd.c_r_s_h;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Represents a map from the game
 *
 * @author Tomás Cardenal López
 */
public class MapCrsh {

    private int SCREEN_COLUMNS = 32;
    private int SCREEN_ROWS = 16;
    private Paint pTiles;
    private MainGameScene gameRef;
    public int mapID;

    /**
     * Starts a map on this ID and with the indicated reference
     * @param mapID The id for the map to load, -1 for test grid
     * @param gameRef
     */
    public MapCrsh(int mapID,MainGameScene gameRef){
        this.gameRef = gameRef;
        this.mapID = mapID;
    }

    /**
     * Draws the map on the canvas
     * @param c the canvas
     */
    public void draw(Canvas c){
        if(this.mapID==-1){
            pTiles = new Paint();
            int reference = gameRef.screenWidth/SCREEN_COLUMNS;
            boolean redFirst = true;
            pTiles.setColor(Color.GREEN);
            //Grid test (IT WORKS, on my phone at least)
            for(int i=1;i<SCREEN_ROWS-1;i++){
                for(int j=2;j<SCREEN_COLUMNS-2;j++){
                    pTiles.setColor(pTiles.getColor()==Color.GREEN?Color.RED:Color.GREEN);
                    c.drawRect(j*reference,i*reference,(j+1)*reference,(i+1)*reference,pTiles);
                }
                if(redFirst){
                    pTiles.setColor(Color.RED);
                }else{
                    pTiles.setColor(Color.GREEN);
                }
                redFirst = !redFirst;
            }
        }
    }
}
