package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.ButtonComponent;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.core.GameConstants;

/**
 * Represents the new game menu
 *
 * @author Tomás Cardenal López
 */
public class NewGameScene extends SceneCrsh {

    /**
     * Button for starting a new game
     */
    private ButtonComponent btnStartGame;
    /**
     * Button for choosing P1 vs COM
     */
    private ButtonComponent btnVsCOM;
    /**
     * Button for choosing P1 vs P2
     */
    private ButtonComponent btnPvP;
    /**
     * Button for choosing Normal mode
     */
    private ButtonComponent btnNormalMode;
    /**
     * Button for choosing CRSH mode
     */
    private ButtonComponent btnCRSHmode;
    /**
     * Button for game settings
     */
    private ButtonComponent btnGameSettings;

    /**
     * Starts a new game menu
     *
     * @param context      the application context
     * @param id           this scene's id (0 is recommended by default for the main menu)
     * @param screenWidth  this screen's width
     * @param screenHeight this screen's height
     */
    public NewGameScene(Context context, int id, int screenWidth, int screenHeight) {
        super(context, id, screenWidth, screenHeight);
        //Title text
        pTitleText = new Paint();
        pTitleText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        pTitleText.setColor(Color.BLACK);
        pTitleText.setTextAlign(Paint.Align.CENTER);
        pTitleText.setTextSize((float) ((screenHeight / GameConstants.MENUSCREEN_COLUMNS) * 2));

        //Gradient paint
        this.gradientPaint = new Paint();
        this.gradientPaint.setShader(new LinearGradient(0, 0, screenWidth, screenHeight, Color.GREEN, Color.CYAN, Shader.TileMode.CLAMP));

        //Buttons
        Typeface homespun = Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN);

        backBtn.setSceneId(0);

        btnVsCOM = new ButtonComponent(context, homespun, context.getString(R.string.btnVsCom),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 2,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 2,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 8,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 3, Color.YELLOW, 150, true, 99);
        btnVsCOM.setClickEffectParameters(Color.YELLOW, Color.DKGRAY, 255, 50);

        btnPvP = new ButtonComponent(context, homespun, context.getString(R.string.btnPvP),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 10,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 2,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 16,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 3, Color.GREEN, 150, true, 99);
        btnPvP.setClickEffectParameters(Color.GREEN, Color.DKGRAY, 255, 50);

        btnNormalMode = new ButtonComponent(context, homespun, context.getString(R.string.btnNormalMode),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 2,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 4,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 8,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 5, Color.CYAN, 150, true, 99);
        btnNormalMode.setClickEffectParameters(Color.BLUE, Color.DKGRAY, 255, 50);

        btnCRSHmode = new ButtonComponent(context, homespun, context.getString(R.string.btnCrshMode),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 10,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 4,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 16,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 5, Color.MAGENTA, 150, true, 100);
        btnCRSHmode.setClickEffectParameters(Color.RED, Color.DKGRAY, 255, 50);

        btnStartGame = new ButtonComponent(context, homespun, context.getString(R.string.btnStartGame),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 6,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 6,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 12,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 7, Color.BLUE, 150, true, 99);

        btnGameSettings = new ButtonComponent(context, Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME), context.getString(R.string.btnGameSettings),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 14,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 6,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 16,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 7, Color.GRAY, 100, true, 7);
        btnGameSettings.setClickEffectParameters(Color.GRAY,Color.DKGRAY, 190,50);

        btnNormalMode.setHeldDown(true);
        btnPvP.setHeldDown(false);
    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics() {

    }

    /**
     * Draws the a new game scene
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //General background
        c.drawPaint(gradientPaint);
        //Test text
        c.drawText(context.getString(R.string.titleNewGame), screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9, screenHeight / GameConstants.MENUSCREEN_ROWS, pTitleText);
        //Buttons
        backBtn.draw(c);
        btnVsCOM.draw(c);
        btnPvP.draw(c);
        btnNormalMode.draw(c);
        btnCRSHmode.draw(c);
        btnStartGame.draw(c);
        btnGameSettings.draw(c);
    }

    /**
     * Controls the events on the touchscreen
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                if(isClick(btnGameSettings,event)){
                    btnGameSettings.setHeldDown(true);
                }
                break;

            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (isClick(btnNormalMode, event)) {
                    toggleModeButtons(false);
                } else if (isClick(btnCRSHmode, event)) {
                    toggleModeButtons(true);
                }
                if (isClick(btnVsCOM, event)) {
                    togglePlayerModeButtons(true);
                } else if (isClick(btnPvP, event)) {
                    togglePlayerModeButtons(false);
                }
                if (isClick(backBtn, event)) {
                    return backBtn.getSceneId();
                }
                if (isClick(btnGameSettings, event)) {
                    return btnGameSettings.getSceneId();
                }
                if (isClick(btnStartGame, event)) {
                    return btnStartGame.getSceneId();
                }
            case MotionEvent.ACTION_MOVE: // Any finger moves
                if (!isClickByAny(btnGameSettings, event) && btnGameSettings.isHeldDown()) {
                    btnGameSettings.setHeldDown(false);
                }
                if (isClickByAny(btnGameSettings, event) && !btnGameSettings.isHeldDown()) {
                    btnGameSettings.setHeldDown(true);
                }
                break;
            default:
                Log.i("Other", "Undefined action: " + action);
        }
        return this.id;
    }

    /**
     * Toggles the mode buttons and the mode itself to be launched
     *
     * @param crshSelected if CRSH mode is selected
     */
    public void toggleModeButtons(boolean crshSelected) {
        if (crshSelected) {
            btnCRSHmode.setHeldDown(true);
            btnNormalMode.setHeldDown(false);
            btnStartGame.setColor(Color.RED);
            if (btnVsCOM.isHeldDown()) {
                btnStartGame.setSceneId(98);
            } else {
                btnStartGame.setSceneId(100);
            }
        } else {
            btnNormalMode.setHeldDown(true);
            btnCRSHmode.setHeldDown(false);
            btnStartGame.setColor(Color.BLUE);
            if (btnVsCOM.isHeldDown()) {
                btnStartGame.setSceneId(97);
            } else {
                btnStartGame.setSceneId(99);
            }
        }
    }

    /**
     * Toggles the player mode buttons and the playerMode itself to be launched
     *
     * @param vsComSelected if vsCom is selected
     */
    public void togglePlayerModeButtons(boolean vsComSelected) {
        if (vsComSelected) {
            btnVsCOM.setHeldDown(true);
            btnPvP.setHeldDown(false);
            if (btnNormalMode.isHeldDown()) {
                btnStartGame.setSceneId(97);
            } else {
                btnStartGame.setSceneId(98);
            }
        } else {
            btnPvP.setHeldDown(true);
            btnVsCOM.setHeldDown(false);
            if (btnNormalMode.isHeldDown()) {
                btnStartGame.setSceneId(99);
            } else {
                btnStartGame.setSceneId(100);
            }
        }
    }


}
