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
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Represents a map from the game
 *
 * @author Tomás Cardenal López
 */
public class MapCrsh extends DrawableComponent {

    public int mapID;
    public TileCrsh[][] tileArray;
    public int screenWidth;
    public int screenHeight;
    private int reference;
    private int hReference;
    private TileCrsh.TILE_TYPE[][] dataArray;

    /**
     * Starts a map on this ID and with the indicated reference
     *
     * @param mapID        The id for the map to load, -1 for test grid
     * @param context      The application context
     * @param screenWidth  The screen width
     * @param screenHeight The screen height
     */
    public MapCrsh(int mapID, Context context, int screenWidth, int screenHeight) {
        this.context = context;
        this.mapID = mapID;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.reference = screenWidth / GameConstants.GAMESCREEN_COLUMNS;
        this.hReference = (screenHeight - getReference() * GameConstants.GAMESCREEN_ROWS) / 2;
        this.xPos = screenWidth - (screenWidth - (reference * GameConstants.MAPAREA_COLUMNS));
        this.yPos = hReference;
        if (mapID == 666) {
            //if (!loadMap(666)) {
            dataArray = testMap();
            saveMap();
            //}
        }
    }

    /**
     * Draws the map on the canvas
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        if (this.mapID == -1) {
            drawTestGrid(c);
        } else {
            if (dataArray == null) {
                loadMap(mapID);
            }
            TileCrsh currentTile;
            for (int i = 1; i < GameConstants.GAMESCREEN_ROWS - 1; i++) {
                for (int j = 3; j < GameConstants.GAMESCREEN_COLUMNS - 3; j++) {
                    currentTile = tileArray[i - 1][j - 3];
                    c.drawRect(currentTile.collisionRect, currentTile.testPaint);
                }
            }
        }
    }

    /**
     * Draws a test grid
     *
     * @param c the canvas to draw
     */
    private void drawTestGrid(Canvas c) {
        Paint pTiles = new Paint();
        pTiles.setColor(Color.GREEN);
        boolean redFirst = true;
        //Grid test (IT WORKS, ON EVERY PHONE) Adjust settings to start drawing wherever we want to
        for (int i = 1; i < GameConstants.GAMESCREEN_ROWS - 1; i++) {
            for (int j = 3; j < GameConstants.GAMESCREEN_COLUMNS - 3; j++) {
                pTiles.setColor(pTiles.getColor() == Color.GREEN ? Color.RED : Color.GREEN);
                c.drawRect(j * getReference(), i * getReference() + gethReference(), (j + 1) * getReference(), (i + 1) * getReference() + gethReference(), pTiles);
            }
            if (redFirst) {
                pTiles.setColor(Color.RED);
            } else {
                pTiles.setColor(Color.GREEN);
            }
            redFirst = !redFirst;
        }
    }

    /**
     * Loads tiles into an array for their management
     */
    public void loadTileArray() {
        tileArray = new TileCrsh[GameConstants.MAPAREA_ROWS][GameConstants.MAPAREA_COLUMNS];
        for (int i = 1; i < GameConstants.GAMESCREEN_ROWS - 1; i++) {
            for (int j = 3; j < GameConstants.GAMESCREEN_COLUMNS - 3; j++) {
                tileArray[i - 1][j - 3] = new TileCrsh(context, -1,
                        j * getReference(), i * getReference() + gethReference(), (j + 1) * getReference(), (i + 1) * getReference() + gethReference(), dataArray[i - 1][j - 3]
                );
            }
        }
    }

    /**
     * Returns an array for a test map
     *
     * @return the test map
     */
    private TileCrsh.TILE_TYPE[][] testMap() {
        Log.i("Loading test map", "Loading test  map");
        this.mapID = 666;
        TileCrsh.TILE_TYPE[][] testArray = new TileCrsh.TILE_TYPE[GameConstants.MAPAREA_ROWS][GameConstants.MAPAREA_COLUMNS];
        for (int i = 0; i < testArray.length; i++) {
            for (int j = 0; j < testArray[i].length; j++) {
                if (i == 0 || i == testArray.length - 1 || j == 0 || j == testArray[i].length - 1) {
                    testArray[i][j] = TileCrsh.intToTileType(0);
                } else if (i % 3 != 0) {
                    testArray[i][j] = TileCrsh.intToTileType(1);
                } else if (j % 3 == 2) {
                    testArray[i][j] = TileCrsh.intToTileType(2);
                } else {
                    testArray[i][j] = TileCrsh.intToTileType(1);
                }
            }
        }
        return testArray;
    }

    /**
     * Loads a map from files with the corresponding mapID
     *
     * @param mapID the mapID to load
     * @return a boolean depicting if the loading was successful
     */
    public boolean loadMap(int mapID) {
        this.dataArray = new TileCrsh.TILE_TYPE[GameConstants.MAPAREA_ROWS][GameConstants.MAPAREA_COLUMNS];
        try (FileInputStream fis = context.openFileInput(mapID + GameConstants.MAPFILE_NAME)) {
            DataInputStream input = new DataInputStream(fis);
            for (int i = 0; i < dataArray.length; i++) {
                for (int j = 0; j < dataArray[i].length; j++) {
                    dataArray[i][j] = TileCrsh.intToTileType(input.readInt());
                    Log.i("mapValue", dataArray[i][j] + "");
                }
            }
        } catch (IOException e) {
            Log.e("LoadMap error", e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    /**
     * Saves a map into local files with the mapID of the current instance
     *
     * @return a boolean depicting if the saving was successful
     */
    public boolean saveMap() {
        Log.i("Saving map", "Saving map");
        if (this.mapID != -1 && dataArray != null) {
            try (FileOutputStream fos = context.openFileOutput(mapID + GameConstants.MAPFILE_NAME, Context.MODE_PRIVATE)) {
                DataOutputStream output = new DataOutputStream(fos);
                for (int i = 0; i < dataArray.length; i++) {
                    for (int j = 0; j < dataArray[i].length; j++) {
                        output.writeInt(TileCrsh.tileTypeToInt(dataArray[i][j]));
                        Log.i("mapValue", dataArray[i][j] + "");
                    }
                }
            } catch (IOException e) {
                Log.e("SaveMap error", e.getLocalizedMessage());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns this instance's tile width reference
     *
     * @return the reference value
     */
    public int getReference() {
        return reference;
    }

    /**
     * Returns this map's height offset
     *
     * @return the height offset value
     */
    public int gethReference() {
        return hReference;
    }

}
