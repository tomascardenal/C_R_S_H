package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.ButtonComponent;
import com.example.tomascrd.c_r_s_h.components.MapComponent;
import com.example.tomascrd.c_r_s_h.components.SaveMenuComponent;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.components.TileComponent;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.GameEngine;
import com.example.tomascrd.c_r_s_h.core.Utils;

/**
 * A scene representing a map creator
 *
 * @author Tomás Cardenal López
 */
public class MapCreatorScene extends SceneCrsh {
    /**
     * Callback to access the game engine
     */
    private GameEngine engineCallback;
    /**
     * Set of utils
     */
    private Utils utils;
    /**
     * Map to load on the creator scene
     */
    private MapComponent creatorMap;
    /**
     * Indicates whether the scene is paused or not
     */
    private boolean onPause;
    /**
     * Pause menu
     */
    private SaveMenuComponent saveMenu;
    /**
     * Pause button
     */
    private ButtonComponent btnPause;
    /**
     * Id for the new map
     */
    private int newId;
    /**
     * The current map name
     */
    public String currentMapName;
    /**
     * Constant id for MapCreatorScene
     */
    public static final int MAPCREATOR_ID = 6;


    /**
     * Starts a new Map Creator
     *
     * @param context        the scene's context
     * @param screenWidth    the screen width
     * @param screenHeight   the screen height
     * @param engineCallback callback to the GameEngine
     */
    public MapCreatorScene(Context context, int screenWidth, int screenHeight, GameEngine engineCallback) {
        super(context, MAPCREATOR_ID, screenWidth, screenHeight);
        this.engineCallback = engineCallback;
        this.utils = new Utils(context);

        //TODO this is only a test id, ask for id from user
        newId = 1337;
        //Initialize map
        this.creatorMap = new MapComponent(0, context, screenWidth, screenHeight, engineCallback.loader);
        this.creatorMap.loadTileArray();
        this.tileSizeReference = this.creatorMap.getReference();

        //Pause button
        btnPause = new ButtonComponent(context,
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME), context.getString(R.string.btnPause),
                screenWidth - screenWidth / 16, 0, screenWidth, screenWidth / 16, Color.TRANSPARENT, 0, false, 0);

        //Pause menu
        this.saveMenu = new SaveMenuComponent(this.context, this.creatorMap.xLeft, this.creatorMap.yTop, this.creatorMap.mapAreaWidth, this.creatorMap.mapAreaHeight, this);
    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics() {
        super.updatePhysics();
    }

    /**
     * Draws the main game
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //General background
        super.draw(c);
        if (!onPause) {
            //Draw map
            creatorMap.draw(c);
            //Draw the pause button
            btnPause.draw(c);
        } else {
            saveMenu.draw(c);
        }

    }

    /**
     * Controls the events on the touchscreen and sends it to the corresponding touchManager
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                if (!onPause) {

                }
                break;
            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (!onPause) {
                    if (isClick(btnPause, event)) {
                        onPause = true;
                    }
                    iterateTiles(event);
                } else {
                    int saveResult = onSaveMenu(event);
                    if (saveResult != -1) {
                        return saveResult;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE: // Any finger moves
                if (!onPause) {

                }
                break;
            default:
                Log.i("Other", "Undefined action: " + action);
        }
        return this.id;
    }

    /**
     * Controls the actions of the SaveMenu
     *
     * @param event the event launching from the saveMenu
     * @return a new scene id to change to, or -1 if no scene change was requested
     */
    public int onSaveMenu(MotionEvent event) {
        if (saveMenu.isConfirming()) {
            if (isClick(saveMenu.getBtnConfirmYes(), event)) {
                saveAMap();
            } else if (isClick(saveMenu.getBtnConfirmNo(), event)) {
                saveMenu.setConfirming(false);
            }
            if (saveMenu.isQuitAfterConfirm()) {
                saveMenu.setQuitAfterConfirm(false);
                return 0;
            }
            if (saveMenu.isLoadAfterConfirm()) {
                saveMenu.setLoadAfterConfirm(false);
                loadAMap();
            }
        } else if (saveMenu.isOnKeyboard()) {
            saveMenu.keyboard.onClickEvent(event);
            if (isClick(saveMenu.getBtnKeyConfirmYes(), event)) {
                currentMapName = saveMenu.keyboard.getUserInput();
                Toast.makeText(this.context, context.getString(R.string.toastChangedNameTo) + currentMapName, Toast.LENGTH_SHORT).show();
                saveMenu.setOnKeyboard(false);
            } else if (isClick(saveMenu.getBtnKeyConfirmNo(), event)) {
                saveMenu.setOnKeyboard(false);
            }
        } else {
            if (isClick(saveMenu.getBtnSaveMap(), event)) {
                saveAMap();
            }
            if (isClick(saveMenu.getBtnLoadMap(), event)) {
                if (saveMenu.isConfirmChanges()) {
                    saveMenu.setConfirming(true);
                    saveMenu.setLoadAfterConfirm(true);
                } else {
                    loadAMap();
                }
            }
            if (isClick(saveMenu.getBtnUnpause(), event)) {
                onPause = false;
            }
            if (isClick(saveMenu.getBtnExitToMenu(), event)) {
                if (saveMenu.isConfirmChanges()) {
                    saveMenu.setConfirming(true);
                    saveMenu.setQuitAfterConfirm(true);
                } else {
                    return 0;
                }
            }
            if (isClick(saveMenu.getBtnNameMap(), event)) {
                saveMenu.setOnKeyboard(true);
            }
        }
        return -1;
    }

    /**
     * Saves a map and prompts the user with a toast informing of the result
     */
    public void saveAMap() {
        creatorMap.mapID = newId;
        boolean didIt = creatorMap.saveMap();
        if (didIt) {
            Toast.makeText(this.context, context.getString(R.string.toastMapSaved), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.context, context.getString(R.string.toastMapNotSaved), Toast.LENGTH_SHORT).show();

        }
        saveMenu.setConfirming(false);
        saveMenu.setConfirmChanges(false);
    }

    /**
     * Safely loads a map and prompts the user with a toast informing of the result
     */
    public void loadAMap() {
        boolean didIt = creatorMap.loadMap(newId);
        if (didIt) {
            Toast.makeText(this.context, context.getString(R.string.toastMapLoaded), Toast.LENGTH_SHORT).show();
            creatorMap.loadTileArray();
        } else {
            Toast.makeText(this.context, context.getString(R.string.toastMapNotLoaded), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Determines the row and column of the touched tile
     *
     * @return a point containing the column and row position of the Tile
     */
    public Point determineTouchTile(MotionEvent event) {
        int columnPosition;
        int rowPosition;
        //Calculate the position inside of the map
        double touchX = event.getX(event.getActionIndex()) - (creatorMap.screenWidth - creatorMap.getX()) / 2;
        double touchY = event.getY(event.getActionIndex()) - (creatorMap.screenHeight - creatorMap.height) / 2;
        //Deduce the column and row position from the touchX and touchY coordinates
        columnPosition = (int) (touchX / this.creatorMap.getReference());
        rowPosition = (int) (touchY / this.creatorMap.getReference()) - 1;
        return new Point(columnPosition, rowPosition);
    }

    /**
     * Iterates over the tiles if the touch event touched a valid tile to change
     *
     * @param event the motionEvent launching this
     */
    private void iterateTiles(MotionEvent event) {
        Point touchPoint = determineTouchTile(event);
        if (touchPoint.x < creatorMap.tileArray[0].length - 1 && touchPoint.y < creatorMap.tileArray.length - 1
                && touchPoint.x > 0 && touchPoint.y > 0) {
            TileComponent editTile = creatorMap.tileArray[touchPoint.y][touchPoint.x];
            int currentType = TileComponent.tileTypeToInt(editTile.getTileType());
            if (currentType < GameConstants.TILE_TYPES.length - 1) {
                currentType++;
            } else {
                currentType = 0;
            }
            editTile.setTileType(TileComponent.intToTileType(currentType));
            creatorMap.tileArray[touchPoint.y][touchPoint.x] = editTile;
            creatorMap.updateDataArray(editTile.getTileType(), touchPoint.y, touchPoint.x);
            saveMenu.setConfirmChanges(true);
        }
    }

    /**
     * Sets the onPause value of the game
     *
     * @param onPause the new onPause value
     */
    public void setOnPause(boolean onPause) {
        this.onPause = onPause;
    }


}
