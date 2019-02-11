package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.tomascrd.c_r_s_h.scenes.MainGameScene;
import com.example.tomascrd.c_r_s_h.core.GameConstants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents a map from the game
 *
 * @author Tomás Cardenal López
 */
public class MapCrsh {

    private Paint pTiles;
    private MainGameScene gameRef;
    public int mapID;
    private TileCrsh.TILE_TYPE[][] mapArray;
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
        this.reference = gameRef.screenWidth/GameConstants.GAMESCREEN_COLUMNS;
        this.hReference = (gameRef.screenHeight-reference*GameConstants.GAMESCREEN_ROWS)/2;
        if(mapID==666){
            if(!loadMap(666)){
                mapArray = testMap();
                saveMap();
            }
        }
    }

    /**
     * Draws the map on the canvas
     * @param c the canvas to draw
     */
    public void draw(Canvas c){
        if(this.mapID==-1){
            drawTestGrid(c);
        }else{
            if(mapArray==null){
                loadMap(mapID);
            }
            TileCrsh currentTile;
            for(int i=1;i<GameConstants.GAMESCREEN_ROWS-1;i++){
                for(int j=2;j<GameConstants.GAMESCREEN_COLUMNS-2;j++){
                    currentTile = new TileCrsh(gameRef.context,-1,j*reference,i*reference+hReference,(j+1)*reference,(i+1)*reference+hReference,mapArray[i-1][j-2]);
                    c.drawRect(currentTile.collisionRect,currentTile.testPaint);
                }
            }
        }
    }

    /**
     * Draws a test grid
     * @param c the canvas to draw
     */
    private void drawTestGrid(Canvas c){
        boolean redFirst = true;
        //Grid test (IT WORKS, ON EVERY PHONE) Adjust settings to start drawing wherever we want to
        for(int i=1;i<GameConstants.GAMESCREEN_ROWS-1;i++){
            for(int j=2;j<GameConstants.GAMESCREEN_COLUMNS-2;j++){
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

    /**
     * Returns an array for a test map
     * @return the test map
     */
    private TileCrsh.TILE_TYPE[][] testMap(){
        Log.i("Loading test map","Loading test  map");
        this.mapID = 666;
        TileCrsh.TILE_TYPE[][] testArray = new TileCrsh.TILE_TYPE[GameConstants.MAPAREA_ROWS][GameConstants.MAPAREA_COLUMNS];
        for(int i=0;i<testArray.length;i++){
            for(int j=0;j<testArray[i].length;j++){
                if(i==0||i==testArray.length-1||j==0||j==testArray[i].length-1){
                    testArray[i][j]=TileCrsh.intToTileType(0);
                }else if(i%3!=0){
                    testArray[i][j]=TileCrsh.intToTileType(1);
                }else if(j%3==0){
                    testArray[i][j]=TileCrsh.intToTileType(2);
                }else{
                    testArray[i][j]=TileCrsh.intToTileType(1);
                }
            }
        }
        return testArray;
    }

    /**
     * Loads a map from files with the corresponding mapID
     * @param mapID the mapID to load
     */
    public boolean loadMap(int mapID){
        this.mapArray = new TileCrsh.TILE_TYPE[GameConstants.MAPAREA_ROWS][GameConstants.MAPAREA_COLUMNS];
        try(FileInputStream fis = gameRef.context.openFileInput(mapID+GameConstants.MAPFILE_NAME)){
            DataInputStream input = new DataInputStream(fis);
            for(int i=0;i<mapArray.length;i++){
                for(int j=0;j<mapArray[i].length;j++){
                    mapArray[i][j] = TileCrsh.intToTileType(input.readInt());
                    Log.i("mapValue",mapArray[i][j]+"");
                }
            }
        }catch(IOException e){
            Log.e("LoadMap error",e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    /**
     * Saves a map into local files with the mapID of the current instance
     */
    public void saveMap(){
        Log.i("Saving map","Saving map");
        if(this.mapID!=-1&&mapArray!=null){
            try(FileOutputStream fos = gameRef.context.openFileOutput(mapID+GameConstants.MAPFILE_NAME, Context.MODE_PRIVATE)){
                DataOutputStream output = new DataOutputStream(fos);
                for(int i=0;i<mapArray.length;i++){
                    for(int j=0;j<mapArray[i].length;j++){
                        output.writeInt(TileCrsh.tileTypeToInt(mapArray[i][j]));
                        Log.i("mapValue",mapArray[i][j]+"");
                    }
                }
            }catch (IOException e){
                Log.e("SaveMap error",e.getLocalizedMessage());
            }
        }
    }
}
