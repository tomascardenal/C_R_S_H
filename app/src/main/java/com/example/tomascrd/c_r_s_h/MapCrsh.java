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
    private int SCREEN_ROWS = 18;
    private Paint pTiles;
    private MainGameScene gameRef;
    public int mapID;
    int reference, hReference;
    /**
     * Starts a map on this ID and with the indicated reference
     * @param mapID The id for the map to load, -1 for test grid
     * @param gameRef
     */
    public MapCrsh(int mapID,MainGameScene gameRef){
        this.gameRef = gameRef;
        this.mapID = mapID;
        this.pTiles = new Paint();
        this.pTiles.setColor(Color.GREEN);
        this.reference = gameRef.screenWidth/SCREEN_COLUMNS;
        this.hReference = (gameRef.screenHeight-reference*SCREEN_ROWS)/2;
    }

    /**
     * Draws the map on the canvas
     * @param c the canvas
     */
    public void draw(Canvas c){
        if(this.mapID==-1){
            boolean redFirst = true;
            //Grid test (IT WORKS, ON EVERY PHONE) Adjust settings to start drawing wherever we want to
            for(int i=1;i<SCREEN_ROWS-1;i++){
                for(int j=2;j<SCREEN_COLUMNS-2;j++){
                    pTiles.setColor(pTiles.getColor()==Color.GREEN?Color.RED:Color.GREEN);
                    c.drawRect(j*reference,i*reference+hReference,(j+1)*reference,(i+1)*reference+hReference,pTiles);
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
