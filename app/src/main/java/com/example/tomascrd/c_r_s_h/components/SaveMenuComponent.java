package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.Log;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.GameEngine;
import com.example.tomascrd.c_r_s_h.scenes.MapCreatorScene;

import java.util.ArrayList;

/**
 * Represents a Save Menu for the map editor
 *
 * @author Tomás Cardenal López
 */
public class SaveMenuComponent extends DrawableComponent {
    /**
     * Button for newMaps
     */
    private ButtonComponent btnNewMap;
    /**
     * Button for unpausing
     */
    private ButtonComponent btnUnpause;
    /**
     * Button for going back to the main menu
     */
    private ButtonComponent btnExitToMenu;
    /**
     * Button for saving a map
     */
    private ButtonComponent btnSaveMap;
    /**
     * Button for loading a map
     */
    private ButtonComponent btnLoadMap;
    /**
     * Button for naming a map
     */
    private ButtonComponent btnNameMap;
    /**
     * Button for confirming an action
     */
    private ButtonComponent btnConfirmYes;
    /**
     * Button for unconfirming an action
     */
    private ButtonComponent btnConfirmNo;
    /**
     * Button for confirming on keyboard
     */
    private ButtonComponent btnKeyConfirmYes;
    /**
     * Button for canceling on keyboard
     */
    private ButtonComponent btnKeyConfirmNo;
    /**
     * Button for next map in the list
     */
    private ButtonComponent btnNextMap;
    /**
     * Button for previous map in the list
     */
    private ButtonComponent btnPreviousMap;
    /**
     * Button for the final map of the list
     */
    private ButtonComponent btnEndMap;
    /**
     * Button for the starting map of the list
     */
    private ButtonComponent btnStartMap;
    /**
     * Indicates whether changes were made to the map and should be confirmed
     */
    private boolean confirmChanges;
    /**
     * Indicates whether the user is being shown the confirm menu
     */
    private boolean isConfirming;
    /**
     * Indicates whether there should be a new empty map created after confirming
     */
    private boolean newAfterConfirm;
    /**
     * Indicates whether it should quit the Map Creator after confirming
     */
    private boolean quitAfterConfirm;
    /**
     * Indicates whether a new map should be loaded after confirming
     */
    private boolean loadAfterConfirm;
    /**
     * Determines whether this Save menu is on a keyboard view
     */
    private boolean onKeyboard;
    /**
     * Indicates whether the player is loading a map
     */
    private boolean onLoadMap;
    /**
     * The current state of the game to be saved if necessary
     */
    private MapCreatorScene creatorCallback;
    /**
     * Callback to game engine
     */
    private GameEngine engineCallback;
    /**
     * Rectangle for this border
     */
    private Rect borderRect;
    /**
     * Painter for background
     */
    private Paint backgroundPaint;
    /**
     * Painter for border
     */
    private Paint borderPaint;
    /**
     * Painter for info
     */
    private Paint infoPaint;
    /**
     * Keyboard component
     */
    public KeyboardComponent keyboard;
    /**
     * List of map names
     */
    public ArrayList<String> mapNames;
    /**
     * Map id of the current map
     */
    public int currentMapID;
    /**
     * Map name of the current map
     */
    public String currentMapName;
    /**
     * Map index showing on load
     */
    public int currentLoadIndex;

    /**
     * Initializes a new PauseMenu
     *
     * @param context         the current application context
     * @param xRight          the xRight coordinate
     * @param yTop            the yTop coordinate
     * @param width           the pauseMenu's width
     * @param height          the pauseMenu's height
     * @param creatorCallback the current MapCreatorScene state
     */
    public SaveMenuComponent(Context context, float xRight, float yTop, float width, float height, GameEngine engineCallback, MapCreatorScene creatorCallback) {
        //Initializing variables
        this.context = context;
        this.creatorCallback = creatorCallback;
        this.xPos = xRight;
        this.yPos = yTop;
        this.width = width;
        this.height = height;
        this.confirmChanges = false;
        this.isConfirming = false;
        this.newAfterConfirm = false;
        this.quitAfterConfirm = false;
        this.onKeyboard = false;
        this.setOnLoadMap(false);
        this.currentLoadIndex = 0;
        this.engineCallback = engineCallback;

        //Border rectangle
        borderRect = new Rect(
                (int) xPos + creatorCallback.tileSizeReference / 2,
                (int) yPos + creatorCallback.tileSizeReference / 2,
                (int) (xPos + width) - creatorCallback.tileSizeReference / 2,
                (int) (yPos + height) - creatorCallback.tileSizeReference / 2);

        //Background paint
        this.backgroundPaint = new Paint();
        this.backgroundPaint.setShader(new LinearGradient(xPos + (borderRect.height() / 2), yPos + (borderRect.height() / 2), width - (borderRect.height() / 2), height - (borderRect.height() / 2), Color.GRAY, Color.DKGRAY, Shader.TileMode.CLAMP));

        //Border paint
        this.borderPaint = new Paint();
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(creatorCallback.tileSizeReference * 1.2f);

        //Text painter
        pText = new Paint();
        pText.setTypeface(Typeface.createFromAsset(this.context.getAssets(), GameConstants.FONT_HOMESPUN));
        pText.setColor(Color.BLACK);
        pText.setTextAlign(Paint.Align.CENTER);
        pText.setTextSize(creatorCallback.tileSizeReference);

        infoPaint = new Paint();
        infoPaint.setTypeface(Typeface.createFromAsset(this.context.getAssets(), GameConstants.FONT_HOMESPUN));
        infoPaint.setColor(Color.BLACK);
        infoPaint.setTextAlign(Paint.Align.CENTER);
        infoPaint.setTextSize(creatorCallback.tileSizeReference * 0.75f);

        //Buttons
        Typeface fontawesome = Typeface.createFromAsset(this.context.getAssets(), GameConstants.FONT_AWESOME);

        this.btnUnpause = new ButtonComponent(context, fontawesome, context.getString(R.string.btnUnpause),
                (int) (borderRect.exactCenterX() - creatorCallback.tileSizeReference * 2),
                (int) borderRect.exactCenterY() + creatorCallback.tileSizeReference * 3,
                (int) (borderRect.exactCenterX() + creatorCallback.tileSizeReference * 2),
                (int) (borderRect.exactCenterY() + creatorCallback.tileSizeReference * 5),
                Color.TRANSPARENT, 0, false, -1);

        this.btnNewMap = new ButtonComponent(context, fontawesome, context.getString(R.string.btnNewMap),
                btnUnpause.btnRect.left - btnUnpause.btnRect.width(),
                btnUnpause.btnRect.top,
                btnUnpause.btnRect.left,
                btnUnpause.btnRect.bottom,
                Color.TRANSPARENT, 0, false, -1);

        this.btnExitToMenu = new ButtonComponent(context, fontawesome, context.getString(R.string.btnEndGame),
                btnUnpause.btnRect.right,
                btnUnpause.btnRect.top,
                btnUnpause.btnRect.right + btnUnpause.btnRect.width(),
                btnUnpause.btnRect.bottom,
                Color.TRANSPARENT, 0, false, -1);

        this.btnSaveMap = new ButtonComponent(context, fontawesome, context.getString(R.string.btnSaveMap),
                btnNewMap.btnRect.left,
                btnNewMap.btnRect.top - btnNewMap.btnRect.height() * 2,
                btnNewMap.btnRect.left + btnNewMap.btnRect.width(),
                btnNewMap.btnRect.top - btnNewMap.btnRect.height(),
                Color.TRANSPARENT, 0, false, -1);

        this.btnNameMap = new ButtonComponent(context, fontawesome, context.getString(R.string.btnEditName),
                btnUnpause.btnRect.left,
                btnUnpause.btnRect.top - btnUnpause.btnRect.height() * 2,
                btnUnpause.btnRect.right,
                btnUnpause.btnRect.bottom - btnUnpause.btnRect.height() * 2,
                Color.TRANSPARENT, 0, false, -1);

        this.btnLoadMap = new ButtonComponent(context, fontawesome, context.getString(R.string.btnLoadMap),
                btnExitToMenu.btnRect.left,
                btnExitToMenu.btnRect.top - btnExitToMenu.btnRect.height() * 2,
                btnExitToMenu.btnRect.left + btnExitToMenu.btnRect.width(),
                btnExitToMenu.btnRect.top - btnExitToMenu.btnRect.height(),
                Color.TRANSPARENT, 0, false, -1);

        this.btnConfirmYes = new ButtonComponent(context, fontawesome, context.getString(R.string.btnConfirmYes),
                btnNewMap.btnRect.left,
                btnNewMap.btnRect.top - btnNewMap.btnRect.height() * 2,
                btnNewMap.btnRect.left + btnNewMap.btnRect.width(),
                btnNewMap.btnRect.top - btnNewMap.btnRect.height(),
                Color.TRANSPARENT, 0, false, -1);

        this.btnConfirmNo = new ButtonComponent(context, fontawesome, context.getString(R.string.btnConfirmNo),
                btnExitToMenu.btnRect.left,
                btnExitToMenu.btnRect.top - btnExitToMenu.btnRect.height() * 2,
                btnExitToMenu.btnRect.left + btnExitToMenu.btnRect.width(),
                btnExitToMenu.btnRect.top - btnExitToMenu.btnRect.height(),
                Color.TRANSPARENT, 0, false, -1);

        //KEYBOARD BUILDING
        int offset = (int) this.borderPaint.getStrokeWidth() / 4;
        Rect r = new Rect(this.borderRect.left + offset, this.borderRect.top + offset,
                this.borderRect.right - offset, this.borderRect.bottom - offset);
        keyboard = new KeyboardComponent(context, r, (int) this.borderPaint.getStrokeWidth() / 4);

        this.btnKeyConfirmYes = new ButtonComponent(context, fontawesome, context.getString(R.string.btnConfirmNameYes),
                r.left,
                r.centerY() - btnConfirmYes.btnRect.height() * 2 - (int) this.borderPaint.getStrokeWidth() / 4,
                r.left + btnConfirmYes.btnRect.width(),
                r.centerY() - btnConfirmYes.btnRect.height() - (int) this.borderPaint.getStrokeWidth() / 4,
                Color.TRANSPARENT, 0, false, -1);

        this.btnKeyConfirmNo = new ButtonComponent(context, fontawesome, context.getString(R.string.btnConfirmNameNo),
                r.right - btnConfirmNo.btnRect.width(),
                r.centerY() - btnConfirmYes.btnRect.height() * 2 - (int) this.borderPaint.getStrokeWidth() / 4,
                r.right,
                r.centerY() - btnConfirmYes.btnRect.height() - (int) this.borderPaint.getStrokeWidth() / 4,
                Color.TRANSPARENT, 0, false, -1);

        this.btnStartMap = new ButtonComponent(context, fontawesome, context.getString(R.string.btnListStart),
                r.left + (int) this.borderPaint.getStrokeWidth(),
                r.centerY(),
                r.left + (int) this.borderPaint.getStrokeWidth() + btnConfirmYes.btnRect.width() / 2,
                r.centerY() + btnConfirmYes.btnRect.height(),
                Color.TRANSPARENT, 0, false, -1);

        this.btnEndMap = new ButtonComponent(context, fontawesome, context.getString(R.string.btnListEnd),
                r.right - btnConfirmNo.btnRect.width() / 2 - (int) this.borderPaint.getStrokeWidth(),
                r.centerY(),
                r.right - (int) this.borderPaint.getStrokeWidth(),
                r.centerY() + btnConfirmYes.btnRect.height(),
                Color.TRANSPARENT, 0, false, -1);

        this.btnPreviousMap = new ButtonComponent(context, fontawesome, context.getString(R.string.btnListPrevious),
                r.left + getBtnStartMap().btnRect.width() * 2,
                r.centerY(),
                r.left + getBtnStartMap().btnRect.width() * 3,
                r.centerY() + btnConfirmYes.btnRect.height(),
                Color.TRANSPARENT, 0, false, -1);

        this.btnNextMap = new ButtonComponent(context, fontawesome, context.getString(R.string.btnListNext),
                r.right - getBtnEndMap().btnRect.width() * 3,
                r.centerY(),
                r.right - getBtnEndMap().btnRect.width() * 2,
                r.centerY() + btnConfirmYes.btnRect.height(),
                Color.TRANSPARENT, 0, false, -1);

        if (engineCallback.optionsManager.loadMapList()) {
            Log.i("CrshDebug", "maps were loaded");
        } else {
            Log.i("CrshDebug", "maps were NOT loaded");
        }
        mapNames = engineCallback.optionsManager.getMapNames();
        currentMapID = mapNames.size();
        currentMapName = creatorCallback.currentMapName;
    }

    /**
     * Draws the pause menu on the canvas
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //Draw background
        c.drawRect(borderRect, backgroundPaint);

        //Draw title pause text
        String pauseText;
        if (isConfirming) {
            pauseText = context.getString(R.string.confirmTitle);
        } else if (onKeyboard) {
            pauseText = context.getString(R.string.titleInsertName);
        } else if (isOnLoadMap()) {
            pauseText = context.getString(R.string.titleSelectMap);
        } else {
            pauseText = context.getString(R.string.savemenuTitle);
        }
        c.drawText(pauseText, borderRect.exactCenterX(), this.borderRect.height() / GameConstants.GAMESCREEN_ROWS * 5, pText);

        //Buttons
        if (isConfirming) { //When confirming
            btnConfirmYes.draw(c);
            btnConfirmNo.draw(c);
        }
        if (onKeyboard) { //When on keyboard
            btnKeyConfirmYes.draw(c);
            btnKeyConfirmNo.draw(c);
            keyboard.draw(c);
        } else if (isOnLoadMap()) { //When loading a map
            btnKeyConfirmYes.draw(c);
            btnKeyConfirmNo.draw(c);
            if (currentLoadIndex < mapNames.size() - 1) {
                getBtnEndMap().draw(c);
                getBtnNextMap().draw(c);
            }
            if (currentLoadIndex > 0) {
                getBtnStartMap().draw(c);
                getBtnPreviousMap().draw(c);
            }
            float f = pText.getTextSize();
            pText.setTextSize(f / 1.5f);
            if (mapNames.size() > 0) {
                c.drawText(("#" + engineCallback.optionsManager.getMapId(mapNames.get(currentLoadIndex)) + "-" + mapNames.get(currentLoadIndex)), creatorCallback.screenWidth / 2, getBtnEndMap().btnRect.top + (getBtnNextMap().btnRect.height() / 3) * 2, pText);
            } else {
                c.drawText(context.getString(R.string.infoNoMaps), creatorCallback.screenWidth / 2, (getBtnNextMap().btnRect.top + (getBtnNextMap().btnRect.height() / 3) * 2), pText);
            }
            pText.setTextSize(f);
        } else if (!isConfirming && !onKeyboard && !isOnLoadMap()) { //Main save menu
            c.drawText(currentMapName, borderRect.exactCenterX(), this.borderRect.height() / GameConstants.GAMESCREEN_ROWS * 6, infoPaint);
            c.drawText(context.getString(R.string.infoSave), borderRect.exactCenterX(), this.borderRect.height() / GameConstants.GAMESCREEN_ROWS * 8, infoPaint);
            btnSaveMap.draw(c);
            btnNameMap.draw(c);
            btnLoadMap.draw(c);
            c.drawText(context.getString(R.string.infoSave2), borderRect.exactCenterX(), this.borderRect.height() / GameConstants.GAMESCREEN_ROWS * 13, infoPaint);
            btnNewMap.draw(c);
            btnUnpause.draw(c);
            btnExitToMenu.draw(c);
        }
        //Draw border
        c.drawRect(borderRect, borderPaint);
    }

    /**
     * Returns btnNewMap, for click events
     *
     * @return the options button
     */
    public ButtonComponent getBtnNewMap() {
        return btnNewMap;
    }

    /**
     * Returns btnUnpause, for click events
     *
     * @return the unpause button
     */
    public ButtonComponent getBtnUnpause() {
        return btnUnpause;
    }

    /**
     * Returns btnExitToMenu, for click events
     *
     * @return the exit to menu button
     */
    public ButtonComponent getBtnExitToMenu() {
        return btnExitToMenu;
    }

    /**
     * Returns btnSaveMap, for click events
     *
     * @return the save map button
     */
    public ButtonComponent getBtnSaveMap() {
        return btnSaveMap;
    }

    /**
     * Returns btnLoadMap, for click events
     *
     * @return the load map button
     */
    public ButtonComponent getBtnLoadMap() {
        return btnLoadMap;
    }

    /**
     * Returns btnConfirmYes, for click events
     *
     * @return the confirmYes button
     */
    public ButtonComponent getBtnConfirmYes() {
        return btnConfirmYes;
    }

    /**
     * Returns btnConfirmNo, for click events
     *
     * @return the confirmNo button
     */
    public ButtonComponent getBtnConfirmNo() {
        return btnConfirmNo;
    }

    /**
     * Returns btnNameMap, for click events
     *
     * @return the btnNameMap button
     */
    public ButtonComponent getBtnNameMap() {
        return btnNameMap;
    }

    /**
     * Returns btnKeyConfirmYes, for click events
     *
     * @return the btnKeyConfirmYes button
     */
    public ButtonComponent getBtnKeyConfirmYes() {
        return btnKeyConfirmYes;
    }

    /**
     * Returns btnKeyConfirmNo, for click events
     *
     * @return the btnKeyConfirmNo button
     */
    public ButtonComponent getBtnKeyConfirmNo() {
        return btnKeyConfirmNo;
    }

    /**
     * Determines if the current saveMenu is ready to confirm changes
     *
     * @return the value of the field
     */
    public boolean isConfirmChanges() {
        return confirmChanges;
    }

    /**
     * Sets the value that determines if changes should be confirmed
     *
     * @param confirmChanges the new value to set
     */
    public void setConfirmChanges(boolean confirmChanges) {
        this.confirmChanges = confirmChanges;
    }

    /**
     * Indicates whether the user is being shown the confirm menu
     *
     * @return the current value
     */
    public boolean isConfirming() {
        return isConfirming;
    }

    /**
     * Sets the value that controls if the user is being shown the confirm menu
     *
     * @param confirming the new value of isConfirming
     */
    public void setConfirming(boolean confirming) {
        isConfirming = confirming;
    }

    /**
     * Tells if this save menu should quit after confirming changes
     *
     * @return the value of the boolean
     */
    public boolean isQuitAfterConfirm() {
        return quitAfterConfirm;
    }

    /**
     * Sets this save menu for quitting after confirming or not
     *
     * @param quitAfterConfirm the action value
     */
    public void setQuitAfterConfirm(boolean quitAfterConfirm) {
        this.quitAfterConfirm = quitAfterConfirm;
    }

    /**
     * Tells if this save menu should prompt a new map after confirming changes
     *
     * @return the value of the boolean
     */
    public boolean isNewAfterConfirm() {
        return newAfterConfirm;
    }

    /**
     * Sets this save menu for a new map after confirming or not
     *
     * @param newAfterConfirm the action value
     */
    public void setNewAfterConfirm(boolean newAfterConfirm) {
        this.newAfterConfirm = newAfterConfirm;
    }

    /**
     * Tells if this save menu should load a map after confirming
     *
     * @return the value of the boolean
     */
    public boolean isLoadAfterConfirm() {
        return loadAfterConfirm;
    }

    /**
     * Sets this save menu for loading a map after confirming or not
     *
     * @param loadAfterConfirm the action value
     */
    public void setLoadAfterConfirm(boolean loadAfterConfirm) {
        this.loadAfterConfirm = loadAfterConfirm;
    }

    /**
     * Tells if this save menu should be showing a keyboard
     *
     * @return the value of the boolean
     */
    public boolean isOnKeyboard() {
        return onKeyboard;
    }

    /**
     * Sets this save menu for showing a keyboard or not
     *
     * @param onKeyboard the action value
     */
    public void setOnKeyboard(boolean onKeyboard) {
        this.onKeyboard = onKeyboard;
    }

    /**
     * Gets the value indicating if the save menu is loading a map
     *
     * @return if the save menu is loading a map
     */
    public boolean isOnLoadMap() {
        return onLoadMap;
    }

    /**
     * Sets the value for the save menu to prompt a user for the map list to load
     *
     * @param onLoadMap the new value of the variable
     */
    public void setOnLoadMap(boolean onLoadMap) {
        this.onLoadMap = onLoadMap;
    }

    /**
     * Gets the nextMap button
     *
     * @return the nextMap button
     */
    public ButtonComponent getBtnNextMap() {
        return btnNextMap;
    }

    /**
     * Gets the previousMap button
     *
     * @return the nextMap button
     */
    public ButtonComponent getBtnPreviousMap() {
        return btnPreviousMap;
    }

    /**
     * Gets the endMap button
     *
     * @return the nextMap button
     */
    public ButtonComponent getBtnEndMap() {
        return btnEndMap;
    }

    /**
     * Gets the startMap button
     *
     * @return the nextMap button
     */
    public ButtonComponent getBtnStartMap() {
        return btnStartMap;
    }
}
