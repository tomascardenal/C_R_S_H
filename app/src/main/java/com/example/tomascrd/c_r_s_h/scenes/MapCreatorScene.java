package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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
import com.example.tomascrd.c_r_s_h.structs.MapReference;
import com.example.tomascrd.c_r_s_h.structs.TileTypes;

/**
 * A scene representing a map creator
 *
 * @author Tomás Cardenal López
 */
public class MapCreatorScene extends SceneCrsh {
    /**
     * Button for unconfirming an action
     */
    private ButtonComponent btnConfirmNo;
    /**
     * Button for confirming an action
     */
    private ButtonComponent btnConfirmYes;
    /**
     * Callback to access the game engine
     */
    private GameEngine engineCallback;
    /**
     * Map to load on the creator scene
     */
    private MapComponent creatorMap;
    /**
     * Indicates whether the scene is paused or not
     */
    private boolean onPause;
    /**
     * Indicates if the map was already saved or loaded
     */
    private boolean mapWasSavedOrLoaded;
    /**
     * Controls whether the scene is showing the creator or confirming the deletion of a map
     */
    private boolean onConfirmingDeletion;
    /**
     * Pause menu
     */
    private SaveMenuComponent saveMenu;
    /**
     * Pause button
     */
    private ButtonComponent btnPause;
    /**
     * Button for deleting the current map
     */
    private ButtonComponent btnDeleteMap;
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

        //Initialize variables
        super(context, MAPCREATOR_ID, screenWidth, screenHeight);
        this.mapWasSavedOrLoaded = false;
        this.onConfirmingDeletion = false;
        this.engineCallback = engineCallback;
        newId = engineCallback.optionsManager.getMapNames().size();

        pTitleText = new Paint();
        pTitleText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        pTitleText.setColor(Color.BLACK);
        pTitleText.setTextAlign(Paint.Align.CENTER);
        pTitleText.setTextSize((float) ((screenHeight / GameConstants.MENUSCREEN_COLUMNS) * 2));

        //Initialize map
        getNewEmptyMap(true);
        this.tileSizeReference = this.creatorMap.getReference();

        //Pause button
        btnPause = new ButtonComponent(context,
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME), context.getString(R.string.btnEditorTools),
                screenWidth - screenWidth / 16, 0, screenWidth, screenWidth / 16, Color.TRANSPARENT, 0, false, 0);

        this.btnDeleteMap = new ButtonComponent(context,
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME), context.getString(R.string.btnEmptyRecords),
                0, 0, screenWidth / 16, screenWidth / 16, Color.TRANSPARENT, 0, false, -1);

        this.btnConfirmYes = new ButtonComponent(context, Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME), context.getString(R.string.btnConfirmYes),
                (int) (screenWidth / 2 - btnDeleteMap.width * 4),
                (int) screenHeight / 2,
                (int) (screenWidth / 2 - btnDeleteMap.width * 3),
                (int) (screenHeight / 2 + btnDeleteMap.height),
                Color.TRANSPARENT, 0,
                false, -1);

        this.btnConfirmNo = new ButtonComponent(context, Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME),
                context.getString(R.string.btnConfirmNo),
                (int) (screenWidth / 2 + btnDeleteMap.width * 3),
                (int) screenHeight / 2,
                (int) (screenWidth / 2 + btnDeleteMap.width * 4),
                (int) (screenHeight / 2 + btnDeleteMap.height),
                Color.TRANSPARENT, 0,
                false, -1);

        //Pause menu
        this.saveMenu = new SaveMenuComponent(this.context, this.creatorMap.xLeft, this.creatorMap.yTop, this.creatorMap.mapAreaWidth, this.creatorMap.mapAreaHeight, this.engineCallback, this);
        this.saveMenu.currentMapID = newId;
        this.saveMenu.currentMapName = currentMapName;
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
            if (onConfirmingDeletion) {
                c.drawText(context.getString(R.string.titleDeleteMap), screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9, screenHeight / GameConstants.MENUSCREEN_ROWS, pTitleText);
                btnConfirmNo.draw(c);
                btnConfirmYes.draw(c);
            } else {
                //Draw map
                creatorMap.draw(c);
                //Draw the pause button
                btnPause.draw(c);
                //Draw the delete map button
                if (mapWasSavedOrLoaded) {
                    btnDeleteMap.draw(c);
                }
            }
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

                } else if (saveMenu.isOnKeyboard()) {
                    onSaveMenu(event);
                }
                break;
            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (!onPause) {
                    if (onConfirmingDeletion) {
                        if (isClick(btnConfirmNo, event)) {
                            onConfirmingDeletion = false;
                        }
                        if (isClick(btnConfirmYes, event)) {
                            onConfirmingDeletion = false;
                            deleteAMap();
                        }
                    } else {
                        if (mapWasSavedOrLoaded && isClick(btnDeleteMap, event)) {
                            onConfirmingDeletion = true;
                        }
                        if (isClick(btnPause, event)) {
                            onPause = true;
                        }
                        iterateTiles(event);
                    }
                } else {
                    int saveResult = onSaveMenu(event);
                    if (saveResult != -1) {
                        return saveResult;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE: // Any finger moves
                if (!onPause) {

                } else if (saveMenu.isOnKeyboard()) {
                    onSaveMenu(event);
                }
                break;
        }
        return this.id;
    }

    /**
     * Controls the actions for after confirming a save
     *
     * @return -1 if no scene change was made, the scene id if a change was made
     */
    public int afterConfirmActions() {
        if (saveMenu.isQuitAfterConfirm()) { //Quitting after confirming
            saveMenu.setQuitAfterConfirm(false);
            return 0;
        }
        if (saveMenu.isLoadAfterConfirm()) { //Loading after confirming
            saveMenu.setLoadAfterConfirm(false);
            saveMenu.setOnLoadMap(true);
            return -1;
        }
        if (saveMenu.isNewAfterConfirm()) { //New map after confirming
            saveMenu.setNewAfterConfirm(false);
            getNewEmptyMap(false);
            return -1;
        }
        return -1;
    }

    /**
     * Controls the actions for the load map screen
     *
     * @param event The motion event launching this loadMap screen
     */
    public void loadMapActions(MotionEvent event) {
        if (isClick(saveMenu.getBtnKeyConfirmNo(), event)) {
            saveMenu.setOnLoadMap(false);
        }
        if (isClick(saveMenu.getBtnKeyConfirmYes(), event)) {
            loadAMap();
            saveMenu.setOnLoadMap(false);
        }
        if (isClick(saveMenu.getBtnStartMap(), event)) {
            if (saveMenu.currentLoadIndex > 0) {
                saveMenu.currentLoadIndex = 0;
            }
        }
        if (isClick(saveMenu.getBtnEndMap(), event)) {
            if (saveMenu.currentLoadIndex < saveMenu.mapNames.size() - 1) {
                saveMenu.currentLoadIndex = saveMenu.mapNames.size() - 1;
            }
        }
        if (isClick(saveMenu.getBtnPreviousMap(), event)) {
            if (saveMenu.currentLoadIndex > 0) {
                saveMenu.currentLoadIndex--;
            }
        }
        if (isClick(saveMenu.getBtnNextMap(), event)) {
            if (saveMenu.currentLoadIndex < saveMenu.mapNames.size() - 1) {
                saveMenu.currentLoadIndex++;
            }
        }
    }

    /**
     * Controls the actions on the keyboard
     *
     * @param event The motion event launching this loadMap screen
     */
    public void onKeyboardActions(MotionEvent event) {
        saveMenu.keyboard.onClickEvent(event);
        if (isClick(saveMenu.getBtnKeyConfirmYes(), event)) {
            currentMapName = saveMenu.keyboard.getUserInput();
            saveMenu.currentMapName = saveMenu.keyboard.getUserInput();
            engineCallback.optionsManager.changeNameIfExists(new MapReference(newId, currentMapName));
            Toast.makeText(this.context, context.getString(R.string.toastChangedNameTo) + currentMapName, Toast.LENGTH_SHORT).show();
            saveMenu.setOnKeyboard(false);
        } else if (isClick(saveMenu.getBtnKeyConfirmNo(), event)) {
            saveMenu.setOnKeyboard(false);
        }
    }

    /**
     * Controls the actions of the SaveMenu
     *
     * @param event the event launching from the saveMenu
     * @return a new scene id to change to, or -1 if no scene change was requested
     */
    public int onSaveMenu(MotionEvent event) {
        if (saveMenu.isConfirming()) {
            if (isClick(saveMenu.getBtnConfirmYes(), event)) {//If confirming and clicked yes
                saveMenu.setConfirming(false);
                saveAMap();//Save the map
                if (afterConfirmActions() == 0) {
                    return 0;
                }
            } else if (isClick(saveMenu.getBtnConfirmNo(), event)) { //If confirming and clicked no
                saveMenu.setConfirming(false);
                if (afterConfirmActions() == 0) {
                    return 0;
                }
            }
        } else if (saveMenu.isOnLoadMap()) {
            loadMapActions(event);
        } else if (saveMenu.isOnKeyboard()) {
            onKeyboardActions(event);
        } else {
            if (isClick(saveMenu.getBtnSaveMap(), event)) {
                saveAMap();
            }
            if (isClick(saveMenu.getBtnLoadMap(), event)) {
                if (saveMenu.isConfirmChanges()) {
                    saveMenu.setConfirming(true);
                    saveMenu.setLoadAfterConfirm(true);
                } else {
                    saveMenu.setOnLoadMap(true);
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
            if (isClick(saveMenu.getBtnNewMap(), event)) {
                if (saveMenu.isConfirmChanges()) {
                    saveMenu.setConfirming(true);
                    saveMenu.setNewAfterConfirm(true);
                } else {
                    getNewEmptyMap(false);
                }
            }
        }
        return -1;
    }

    /**
     * Deletes a map from the collection
     *
     * @return true if done, false if not done
     */
    public boolean deleteAMap() {
        boolean didIt = false;
        if (creatorMap.mapID != -20 && this.newId != -20) {
            didIt = context.deleteFile(newId + GameConstants.MAPFILE_NAME);
            if (didIt) {
                engineCallback.optionsManager.removeMap(new MapReference(newId, currentMapName));
                saveMenu.mapNames.clear();
                saveMenu.mapNames = engineCallback.optionsManager.getMapNames();
                getNewEmptyMap(false);
                Toast.makeText(this.context, context.getString(R.string.toastMapDeleted), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.context, context.getString(R.string.toastMapNotDeleted), Toast.LENGTH_SHORT).show();
            }
        }
        return didIt;
    }


    /**
     * Saves a map and prompts the user with a toast informing of the result
     *
     * @return whether the map was saved or not
     */
    public boolean saveAMap() {
        mapWasSavedOrLoaded = true;
        creatorMap.mapID = newId;
        saveMenu.currentMapID = newId;
        engineCallback.optionsManager.addMap(new MapReference(newId, currentMapName));
        saveMenu.mapNames.clear();
        saveMenu.mapNames = engineCallback.optionsManager.getMapNames();
        boolean didIt = creatorMap.saveMap();
        if (didIt) {
            Toast.makeText(this.context, context.getString(R.string.toastMapSaved), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.context, context.getString(R.string.toastMapNotSaved), Toast.LENGTH_SHORT).show();

        }
        saveMenu.setConfirming(false);
        saveMenu.setConfirmChanges(false);
        return didIt;
    }

    /**
     * Safely loads a map and prompts the user with a toast informing of the result
     *
     * @return whether the map was loaded or not
     */
    public boolean loadAMap() {
        mapWasSavedOrLoaded = true;
        newId = engineCallback.optionsManager.getMapId(saveMenu.mapNames.get(saveMenu.currentLoadIndex));
        saveMenu.currentMapID = newId;
        boolean didIt = creatorMap.loadMap(newId);
        currentMapName = engineCallback.optionsManager.getMapNameByID(newId);
        saveMenu.currentMapName = currentMapName;
        if (didIt) {
            Toast.makeText(this.context, context.getString(R.string.toastMapLoaded), Toast.LENGTH_SHORT).show();
            creatorMap.loadTileArray();
        } else {
            Toast.makeText(this.context, context.getString(R.string.toastMapNotLoaded), Toast.LENGTH_SHORT).show();
        }
        return didIt;
    }

    /**
     * Generates a new empty map
     *
     * @param onStart whether the creator is being initialized or not
     */
    private void getNewEmptyMap(boolean onStart) {
        this.creatorMap = new MapComponent(-20, context, screenWidth, screenHeight, engineCallback.loader);
        this.creatorMap.loadTileArray();
        this.currentMapName = context.getString(R.string.unnamedMap);
        this.newId = engineCallback.optionsManager.getMapNames().size();
        this.creatorMap.mapID = this.newId;
        this.mapWasSavedOrLoaded = false;
        this.onConfirmingDeletion = false;
        if (!onStart) {
            this.saveMenu.currentMapID = newId;
            this.saveMenu.currentMapName = currentMapName;
            Toast.makeText(this.context, context.getString(R.string.toastNewMap) + currentMapName, Toast.LENGTH_SHORT).show();
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
        int spawnTwoY = creatorMap.tileArray.length - 3;
        int spawnTwoX = creatorMap.tileArray[creatorMap.tileArray.length - 3].length - 3;
        if (touchPoint.x < creatorMap.tileArray[0].length - 1 && touchPoint.y < creatorMap.tileArray.length - 1
                && touchPoint.x > 0 && touchPoint.y > 0 && !(touchPoint.x == 2 && touchPoint.y == 2) && !(touchPoint.x == spawnTwoX && touchPoint.y == spawnTwoY)) {
            TileComponent editTile = creatorMap.tileArray[touchPoint.y][touchPoint.x];
            int currentType = TileTypes.tileTypeToInt(editTile.getTileType());
            if (currentType < GameConstants.E_TILE_TYPES.length - 1) {
                currentType++;
            } else {
                currentType = 0;
            }
            editTile.setTileType(TileTypes.intToTileType(currentType));
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
