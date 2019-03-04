package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.AssetLoader;
import com.example.tomascrd.c_r_s_h.structs.TileTypes;

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
    private TileTypes.eTileType[][] dataArray;
    /**
     * Tile size reference
     */
    private int reference;
    /**
     * Height offset reference
     */
    private int hReference;
    /**
     * Real mapArea height reference
     */
    public float mapAreaHeight;
    /**
     * Real mapArea width reference
     */
    public float mapAreaWidth;
    /**
     * X left coordinate for this map
     */
    public float xLeft;
    /**
     * X top coordinate for this map
     */
    public float yTop;
    /**
     * Loader for assets
     */
    private AssetLoader loader;


    /**
     * Starts a map on this ID and with the indicated reference
     *
     * @param mapID        The id for the map to load, -1 for test grid
     * @param context      The application context
     * @param screenWidth  The screen width
     * @param screenHeight The screen height
     * @param loader       Loader for assets
     */
    public MapComponent(int mapID, Context context, int screenWidth, int screenHeight, AssetLoader loader) {
        //Initialize variables
        this.context = context;
        this.mapID = mapID;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.loader = loader;

        //Take a size reference from the width
        this.reference = screenWidth / GameConstants.GAMESCREEN_COLUMNS;
        //Width of the map
        this.width = this.reference * GameConstants.GAMESCREEN_COLUMNS;
        //Height of the map
        this.height = this.reference * GameConstants.GAMESCREEN_ROWS;
        //Width of the mapArea
        this.mapAreaWidth = this.reference * GameConstants.MAPAREA_COLUMNS;
        //Height of the mapArea
        this.mapAreaHeight = this.reference * GameConstants.MAPAREA_ROWS;
        //Height reference for adjusting, just the screen's height minus the map height between 2
        this.hReference = (int) (screenHeight - height) / 2;
        //Determine topx and topy positions
        this.xPos = screenWidth - (screenWidth - (reference * GameConstants.MAPAREA_COLUMNS));
        this.yPos = hReference;
        this.xLeft = 3 * reference;
        this.yTop = reference + hReference;
        if (!loader.areTilesLoaded()) {
            loader.loadTiles();
        }
        loader.scaleTileBitmaps(this.reference, this.reference);
        //Load map - test map for the moment
        if (mapID == 666) {
            createTestMap();
        } else if (mapID == -20) {
            loadEmptyMap();
        } else if (mapID == -10) {
            createDefaultMap();
        } else {
            loadMap(mapID);
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
                    switch (currentTile.tileType) {
                        case TILE_BORDER:
                            c.drawBitmap(loader.tileBorder, currentTile.xPos, currentTile.yPos, null);
                            break;
                        case TILE_PATH:
                            if (i == 2) {//If on the first row of grass
                                if (j == 4) {//If on upper left corner
                                    c.drawBitmap(loader.tileGrassUpLeft, currentTile.xPos, currentTile.yPos, null);
                                } else if (j == GameConstants.GAMESCREEN_COLUMNS - 5) {//If on top right corner
                                    c.drawBitmap(loader.tileGrassUpRight, currentTile.xPos, currentTile.yPos, null);
                                } else {//If just on upper border
                                    c.drawBitmap(loader.tileGrassUp, currentTile.xPos, currentTile.yPos, null);
                                }
                            } else if (i == GameConstants.GAMESCREEN_ROWS - 3) {//If on the last row of grass
                                if (j == 4) {//If on upper left corner
                                    c.drawBitmap(loader.tileGrassDownLeft, currentTile.xPos, currentTile.yPos, null);
                                } else if (j == GameConstants.GAMESCREEN_COLUMNS - 5) {//If on top right corner
                                    c.drawBitmap(loader.tileGrassDownRight, currentTile.xPos, currentTile.yPos, null);
                                } else {//If just on upper border
                                    c.drawBitmap(loader.tileGrassDown, currentTile.xPos, currentTile.yPos, null);
                                }
                            } else if (j == 4) {//If on the left border
                                c.drawBitmap(loader.tileGrassLeft, currentTile.xPos, currentTile.yPos, null);
                            } else if (j == GameConstants.GAMESCREEN_COLUMNS - 5) {//If on the right border
                                c.drawBitmap(loader.tileGrassRight, currentTile.xPos, currentTile.yPos, null);
                            } else {
                                c.drawBitmap(loader.tileGrassMid, currentTile.xPos, currentTile.yPos, null);
                            }
                            break;
                        case TILE_BREAKONE:
                            c.drawBitmap(loader.tileBreakOne, currentTile.xPos, currentTile.yPos, null);
                            break;
                        case TILE_BREAKTWO:
                            c.drawBitmap(loader.tileBreakTwo, currentTile.xPos, currentTile.yPos, null);
                            break;
                    }

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
     * Updates the dataArray on the given parameters
     *
     * @param tileType the new tileType
     * @param i        the first index
     * @param j        the second index
     */
    public void updateDataArray(TileTypes.eTileType tileType, int i, int j) {
        if (i < 0 || i > dataArray.length - 1 || j < 0 || j > dataArray[i].length - 1) {
            throw new IllegalArgumentException("Out of dataArray bounds");
        }
        dataArray[i][j] = tileType;
    }

    /**
     * Creates and stores an in dataArray an array for a test map
     */
    private void createTestMap() {
        this.mapID = 666;
        TileTypes.eTileType[][] testArray = new TileTypes.eTileType[GameConstants.MAPAREA_ROWS][GameConstants.MAPAREA_COLUMNS];
        for (int i = 0; i < testArray.length; i++) {
            for (int j = 0; j < testArray[i].length; j++) {
                if (i == 0 || i == testArray.length - 1 || j == 0 || j == testArray[i].length - 1) {
                    testArray[i][j] = TileTypes.intToTileType(0);
                } else if (i % 3 != 0) {
                    testArray[i][j] = TileTypes.intToTileType(1);
                } else if (j % 3 == 2) {
                    testArray[i][j] = TileTypes.intToTileType(2);
                } else {
                    testArray[i][j] = TileTypes.intToTileType(1);
                }
            }
        }
        dataArray = testArray;
    }

    /**
     * Creates and stores an in dataArray a Default Map
     */
    private void createDefaultMap() {
        this.mapID = -10;
        TileTypes.eTileType[][] defaultArray = new TileTypes.eTileType[GameConstants.MAPAREA_ROWS][GameConstants.MAPAREA_COLUMNS];
        for (int i = 0; i < defaultArray.length; i++) {
            for (int j = 0; j < defaultArray[i].length; j++) {
                if (i == 0 || i == defaultArray.length - 1 || j == 0 || j == defaultArray[i].length - 1) {
                    defaultArray[i][j] = TileTypes.intToTileType(0);
                } else if (((j >= 5 && j < 7) || (j >= 19 && j < 21)) && ((i >= 5 && i < 7) || (i >= 9 && i < 11))) {
                    defaultArray[i][j] = TileTypes.intToTileType(0);
                } else if ((i >= 3 && i < 7 || i >= 9 && i < 13) && (j >= 3 && j < 7 || j >= 19 && j < 23)) {
                    defaultArray[i][j] = TileTypes.intToTileType(2);
                } else if ((j >= 9 && j < 17) && (i == 3 || i == 12)) {
                    defaultArray[i][j] = TileTypes.intToTileType(3);
                } else {
                    defaultArray[i][j] = TileTypes.intToTileType(1);
                }
            }
            dataArray = defaultArray;
        }
    }

    /**
     * Creates and stores in dataArray an array for an empty map, with only borders
     */
    private void loadEmptyMap() {
        TileTypes.eTileType[][] emptyArray = new TileTypes.eTileType[GameConstants.MAPAREA_ROWS][GameConstants.MAPAREA_COLUMNS];
        for (int i = 0; i < emptyArray.length; i++) {
            for (int j = 0; j < emptyArray[i].length; j++) {
                if (i == 0 || i == emptyArray.length - 1 || j == 0 || j == emptyArray[i].length - 1) {
                    emptyArray[i][j] = TileTypes.intToTileType(0);
                } else {
                    emptyArray[i][j] = TileTypes.intToTileType(1);
                }
            }
        }
        dataArray = emptyArray;
    }

    /**
     * Loads a map from files with the corresponding mapID
     *
     * @param mapID the mapID to load
     * @return a boolean depicting if the loading was successful
     */
    public boolean loadMap(int mapID) {
        //Initialize dataArray
        this.mapID = mapID;
        this.dataArray = new TileTypes.eTileType[GameConstants.MAPAREA_ROWS][GameConstants.MAPAREA_COLUMNS];
        //Open the input stream
        try (FileInputStream fis = context.openFileInput(mapID + GameConstants.MAPFILE_NAME)) {
            DataInputStream input = new DataInputStream(fis);
            for (int i = 0; i < dataArray.length; i++) {
                for (int j = 0; j < dataArray[i].length; j++) {
                    dataArray[i][j] = TileTypes.intToTileType(input.readInt());
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
                        output.writeInt(TileTypes.tileTypeToInt(dataArray[i][j]));
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

    /**
     * Returns the xPosition of the map (top right corner)
     *
     * @return the xPosition
     */
    public float getX() {
        return this.xPos;
    }

    /**
     * Returns the yPosition of the map (top right corner)
     *
     * @return the yPosition
     */
    public float getY() {
        return this.yPos;
    }

}
