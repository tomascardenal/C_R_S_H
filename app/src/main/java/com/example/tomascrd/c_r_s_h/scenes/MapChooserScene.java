package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.ButtonComponent;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.GameEngine;
import com.example.tomascrd.c_r_s_h.structs.MapReference;

import java.util.ArrayList;

/**
 * Scene for choosing a map to play
 *
 * @author Tomás Cardenal López
 */
public class MapChooserScene extends SceneCrsh {

    /**
     * Callback to the game's engine
     */
    private GameEngine engineCallback;
    /**
     * Button for confirming an action
     */
    private ButtonComponent btnConfirmYes;
    /**
     * Button for unconfirming an action
     */
    private ButtonComponent btnConfirmNo;
    /**
     * Painter for mapNames
     */
    private Paint pMapNames;
    /**
     * Index in the list of the current map being shown
     */
    private int currentIndex;
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
     * Constant id for MapChooserScene
     */
    public static final int MAPCHOOSER_ID = 8;
    /**
     * List of mapReferences
     */
    private ArrayList<MapReference> mapReferences;


    /**
     * Starts a new map chooser scene
     *
     * @param context        the scene's context
     * @param screenWidth    the screen width
     * @param screenHeight   the screen height
     * @param engineCallback callback to the game's engine
     */
    public MapChooserScene(Context context, int screenWidth, int screenHeight, GameEngine engineCallback) {
        super(context, MAPCHOOSER_ID, screenWidth, screenHeight);
        this.engineCallback = engineCallback;
        this.backBtn.setSceneId(1);

        mapReferences = new ArrayList<>();
        mapReferences.add(new MapReference(-10, context.getString(R.string.defaultMapName)));
        for (MapReference ref : engineCallback.optionsManager.getMapReferences()) {
            mapReferences.add(ref);
        }

        //Title text
        pTitleText = new Paint();
        pTitleText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        pTitleText.setColor(Color.BLACK);
        pTitleText.setTextAlign(Paint.Align.CENTER);
        pTitleText.setTextSize((float) ((screenHeight / GameConstants.MENUSCREEN_COLUMNS) * 2));

        //Buttons
        Typeface fontawesome = Typeface.createFromAsset(this.context.getAssets(), GameConstants.FONT_AWESOME);
        this.btnStartMap = new ButtonComponent(context, fontawesome, context.getString(R.string.btnListStart),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 4,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 2,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 5,
                Color.TRANSPARENT, 0, false, -1);

        this.btnEndMap = new ButtonComponent(context, fontawesome, context.getString(R.string.btnListEnd),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 16,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 4,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 17,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 5,
                Color.TRANSPARENT, 0, false, -1);

        this.btnPreviousMap = new ButtonComponent(context, fontawesome, context.getString(R.string.btnListPrevious),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 3,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 4,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 4,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 5,
                Color.TRANSPARENT, 0, false, -1);

        this.btnNextMap = new ButtonComponent(context, fontawesome, context.getString(R.string.btnListNext),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 14,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 4,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 15,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 5,
                Color.TRANSPARENT, 0, false, -1);

        this.btnConfirmYes = new ButtonComponent(context, fontawesome, context.getString(R.string.btnConfirmYes),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 6,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 2,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 7,
                Color.TRANSPARENT, 0, false, -1);

        this.btnConfirmNo = new ButtonComponent(context, fontawesome, context.getString(R.string.btnConfirmNo),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 16,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 6,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 17,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 7,
                Color.TRANSPARENT, 0, false, -1);

        pMapNames = new Paint();
        pMapNames.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        pMapNames.setTextSize(btnEndMap.height * 0.5f);
        pMapNames.setTextAlign(Paint.Align.CENTER);

    }

    /**
     * Controls the events on the touchscreen
     *
     * @param event the touchscreen event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                break;
            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up

                if (isClick(btnStartMap, event)) {
                    if (currentIndex > 0) {
                        currentIndex = 0;
                    }
                }
                if (isClick(btnEndMap, event)) {
                    if (currentIndex < mapReferences.size() - 1) {
                        currentIndex = mapReferences.size() - 1;
                    }
                }
                if (isClick(btnPreviousMap, event)) {
                    if (currentIndex > 0) {
                        currentIndex--;
                    }
                }
                if (isClick(btnNextMap, event)) {
                    if (currentIndex < mapReferences.size() - 1) {
                        currentIndex++;
                    }
                }
                if (isClick(btnConfirmYes, event)) {
                    Log.i("CrshDebug", "confirmed for id " + mapReferences.get(currentIndex).mapId);
                    engineCallback.setCurrentMapID(mapReferences.get(currentIndex).mapId);
                    Toast.makeText(context, String.format(context.getString(R.string.toastLoadedGameMap), mapReferences.get(currentIndex).mapName), Toast.LENGTH_LONG).show();
                    return backBtn.getSceneId();
                }
                if (isClick(btnConfirmNo, event)) {
                    return backBtn.getSceneId();
                }
                if (isClick(backBtn, event)) {
                    return backBtn.getSceneId();
                }
                break;
            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
        }
        return this.id;
    }

    /**
     * Draws the a new game scene
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //General background
        super.draw(c);
        //Test text
        c.drawText(context.getString(R.string.titleMapChooser), screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9, screenHeight / GameConstants.MENUSCREEN_ROWS, pTitleText);
        //Buttons
        backBtn.draw(c);
        btnConfirmNo.draw(c);
        btnConfirmYes.draw(c);
        if (currentIndex < mapReferences.size() - 1) {
            btnEndMap.draw(c);
            btnNextMap.draw(c);
        }
        if (currentIndex > 0) {
            btnPreviousMap.draw(c);
            btnStartMap.draw(c);
        }
        c.drawText(mapReferences.get(currentIndex).mapName, screenWidth / 2, screenHeight / GameConstants.MENUSCREEN_ROWS * 5 - pMapNames.getTextSize() / 2, pMapNames);

    }
}
