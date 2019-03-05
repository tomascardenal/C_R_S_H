package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.components.TextButtonComponent;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.GameEngine;
import com.example.tomascrd.c_r_s_h.structs.eTextAlignment;

/**
 * Represents the options menu
 *
 * @author Tomás Cardenal López
 */
public class OptionsScene extends SceneCrsh {

    /**
     * Options text painter
     */
    private Paint pOptionsText;
    /**
     * Music toggle button
     */
    private TextButtonComponent btnMusic;
    /**
     * Vibration toggle button
     */
    private TextButtonComponent btnVibrate;
    /**
     * Sound effects toggle button
     */
    private TextButtonComponent btnEffects;
    /**
     * Constant id for OptionsScene
     */
    public static final int OPTIONS_ID = 2;


    /**
     * Starts an options menu
     *
     * @param context        the application context
     * @param screenWidth    this screen's width
     * @param screenHeight   this screen's height
     * @param engineCallback callback to access gameEngine data
     */
    public OptionsScene(Context context, int screenWidth, int screenHeight, GameEngine engineCallback) {
        super(context, OPTIONS_ID, screenWidth, screenHeight);
        this.engineCallback = engineCallback;

        //Title text
        pTitleText = new Paint();
        pTitleText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        pTitleText.setColor(Color.BLACK);
        pTitleText.setTextAlign(Paint.Align.CENTER);
        pTitleText.setTextSize((float) ((screenHeight / GameConstants.MENUSCREEN_COLUMNS) * 2));

        //Options text
        pOptionsText = new Paint();
        pOptionsText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        pOptionsText.setColor(Color.BLACK);
        pOptionsText.setTextAlign(Paint.Align.CENTER);
        pOptionsText.setTextSize((float) ((screenHeight / GameConstants.MENUSCREEN_COLUMNS) * 1));

        //Buttons
        String musicValue = engineCallback.optionsManager.isPlayMusic() ? getContext().getString(R.string.btnMusicOn) : getContext().getString(R.string.btnMusicOff);
        btnMusic = new TextButtonComponent(context, Typeface.createFromAsset(getContext().getAssets(), GameConstants.FONT_AWESOME), musicValue,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 4,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 3,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 5,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 4,
                Color.TRANSPARENT, 0,
                getContext().getString(R.string.optMusic),
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN),
                eTextAlignment.ALIGN_LEFT,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS / 3,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS);

        String vibrateValue = engineCallback.optionsManager.isDoVibrate() ? getContext().getString(R.string.btnToggleOn) : getContext().getString(R.string.btnToggleOff);
        btnVibrate = new TextButtonComponent(context, Typeface.createFromAsset(getContext().getAssets(), GameConstants.FONT_AWESOME), vibrateValue,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 10,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 3,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 11,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 4,
                Color.TRANSPARENT, 0,
                getContext().getString(R.string.optVibrate),
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN),
                eTextAlignment.ALIGN_LEFT,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS / 3,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 7);

        String effectsValue = engineCallback.optionsManager.isPlaySoundEffects() ? context.getString(R.string.btnPlayOn) : context.getString(R.string.btnPlayOff);
        btnEffects = new TextButtonComponent(context, Typeface.createFromAsset(getContext().getAssets(), GameConstants.FONT_AWESOME), effectsValue,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 16,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 3,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 17,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 4,
                Color.TRANSPARENT, 0,
                getContext().getString(R.string.optSoundEffects),
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN),
                eTextAlignment.ALIGN_LEFT,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS / 3,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 13);
    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics() {
        super.updatePhysics();
    }

    /**
     * Draws the options menu
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //General background
        super.draw(c);
        //Title text
        c.drawText(context.getString(R.string.btnOptions), screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9, screenHeight / GameConstants.MENUSCREEN_ROWS, pTitleText);
        //Options text
        //Buttons
        backBtn.draw(c);
        btnMusic.draw(c);
        btnVibrate.draw(c);
        btnEffects.draw(c);
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
                break;

            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (isClick(backBtn, event)) {
                    if (engineCallback.loadSavedScene) {
                        return engineCallback.savedScene.getId();
                    } else {
                        return 0;
                    }
                }
                if (isClick(btnMusic, event)) {
                    toggleMusic();
                }
                if (isClick(btnVibrate, event)) {
                    toggleVibration();
                }
                if (isClick(btnEffects, event)) {
                    toggleEffects();
                }
            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
        }
        return this.id;
    }

    /**
     * Toggles the music theme on or off
     */
    public void toggleMusic() {
        if (btnMusic.getText().equals(context.getString(R.string.btnMusicOn))) {
            btnMusic.setText(context.getString(R.string.btnMusicOff));
            engineCallback.optionsManager.setPlayMusic(false);
        } else {
            btnMusic.setText(context.getString(R.string.btnMusicOn));
            engineCallback.optionsManager.setPlayMusic(true);
        }
        engineCallback.updateMusicPlayer();
    }

    /**
     * Toggles the vibration on or off
     */
    public void toggleVibration() {
        if (btnVibrate.getText().equals(context.getString(R.string.btnToggleOn))) {
            btnVibrate.setText(context.getString(R.string.btnToggleOff));
            engineCallback.optionsManager.setDoVibrate(false);
        } else {
            btnVibrate.setText(context.getString(R.string.btnToggleOn));
            engineCallback.optionsManager.setDoVibrate(true);
        }
    }

    /**
     * Toggles the sound effects on or off
     */
    public void toggleEffects() {
        if (btnEffects.getText().equals(context.getString(R.string.btnPlayOn))) {
            btnEffects.setText(context.getString(R.string.btnPlayOff));
            engineCallback.optionsManager.setPlaySoundEffects(false);
        } else {
            btnEffects.setText(context.getString(R.string.btnPlayOn));
            engineCallback.optionsManager.setPlaySoundEffects(true);
        }
    }
}

