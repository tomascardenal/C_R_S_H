package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

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
public class MapComponent extends DrawableComponent {

    /**
     * This map's ID
     */
    public int mapID;
    /**
     * Array of tiles in the map
     */
    public TileComponent[][] tileArray;
    /**
     * The screen's width
     */
    public int screenWidth;
    /**
     * The screen's height
     */
    public int screenHeight;
    /**
     * Array of tile types
     */
    private TileComponent.TILE_TYPE[][] dataArray;
    /**
     * Tile size reference
     */
    private int reference;
    /**
     * Height offset reference
     */
    private int hReference;

    /**
     * Starts a map on this ID and with the indicated reference
     *
     * @param mapID        The id for the map to load, -1 for test grid
     * @param context      The application context
     * @param screenWidth  The screen width
     * @param screenHeight The screen height
     */
    public MapComponent(int mapID, Context context, int screenWidth, int screenHeight) {
        //Initialize variables
        this.context = context;
        this.mapID = mapID;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        //Take a size reference from the width
        this.reference = screenWidth / GameConstants.GAMESCREEN_COLUMNS;
        //Width of the map
        this.width = this.reference * GameConstants.GAMESCREEN_COLUMNS;
        //Height of the map
        this.height = this.reference * GameConstants.GAMESCREEN_ROWS;
        //Height reference for adjusting, just the screen's height minus the map height between 2
        this.hReference = (int) (screenHeight - this.height) / 2;

        //Determine topx and topy positions
        this.xPos = screenWidth - (screenWidth - (reference * GameConstants.MAPAREA_COLUMNS));
        this.yPos = hReference;

        //Load map - test map for the moment
        if (mapID == 666) {
            if (!loadMap(666)) {
                dataArray = testMap();
                saveMap();
            }
        }
    }

    /**
     * Draws the map on the canvas
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //Test grids
        if (this.mapID == -1) {
            drawTestGrid(c);
        } else {
            //If data is not loaded, load it!
            if (dataArray == null) {
                loadMap(mapID);
            }
            //Draw every tile on the map
            TileComponent currentTile;
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
        tileArray = new TileComponent[GameConstants.MAPAREA_ROWS][GameConstants.MAPAREA_COLUMNS];
        //Loop the size of the map and load tiles on the corresponding coordinates to be drawn later
        for (int i = 1; i < GameConstants.GAMESCREEN_ROWS - 1; i++) {
            for (int j = 3; j < GameConstants.GAMESCREEN_COLUMNS - 3; j++) {
                tileArray[i - 1][j - 3] = new TileComponent(context, -1,
                        j * getReference(), i * getReference() + gethReference(), (j + 1) * getReference(), (i + 1) * getReference() + gethReference(), dataArray[i - 1][j - 3]
                );
            }
        }
    }

    /**
     * Creates an array for a test map
     *
     * @return the test map created
     */
    private TileComponent.TILE_TYPE[][] testMap() {
        this.mapID = 666;
        TileComponent.TILE_TYPE[][] testArray = new TileComponent.TILE_TYPE[GameConstants.MAPAREA_ROWS][GameConstants.MAPAREA_COLUMNS];
        for (int i = 0; i < testArray.length; i++) {
            for (int j = 0; j < testArray[i].length; j++) {
                if (i == 0 || i == testArray.length - 1 || j == 0 || j == testArray[i].length - 1) {
                    testArray[i][j] = TileComponent.intToTileType(0);
                } else if (i % 3 != 0) {
                    testArray[i][j] = TileComponent.intToTileType(1);
                } else if (j % 3 == 2) {
                    testArray[i][j] = TileComponent.intToTileType(2);
                } else {
                    testArray[i][j] = TileComponent.intToTileType(1);
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
        //Initialize dataArray
        this.dataArray = new TileComponent.TILE_TYPE[GameConstants.MAPAREA_ROWS][GameConstants.MAPAREA_COLUMNS];
        //Open the input stream
        try (FileInputStream fis = context.openFileInput(mapID + GameConstants.MAPFILE_NAME)) {
            DataInputStream input = new DataInputStream(fis);
            for (int i = 0; i < dataArray.length; i++) {
                for (int j = 0; j < dataArray[i].length; j++) {
                    dataArray[i][j] = TileComponent.intToTileType(input.readInt());
                }
            }
        } catch (IOException e) {
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
        //If this map is not a test grid nor is it null
        if (this.mapID != -1 && dataArray != null) {
            //Open output stream
            try (FileOutputStream fos = context.openFileOutput(mapID + GameConstants.MAPFILE_NAME, Context.MODE_PRIVATE)) {
                DataOutputStream output = new DataOutputStream(fos);
                for (int i = 0; i < dataArray.length; i++) {
                    for (int j = 0; j < dataArray[i].length; j++) {
                        output.writeInt(TileComponent.tileTypeToInt(dataArray[i][j]));
                    }
                }
            } catch (IOException e) {
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
