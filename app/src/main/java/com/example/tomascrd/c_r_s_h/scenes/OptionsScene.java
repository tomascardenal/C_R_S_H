package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.ButtonComponent;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.components.TextButtonComponent;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.GameEngine;

/**
 * Represents the options menu
 *
 * @author Tomás Cardenal López
 */
public class OptionsScene extends SceneCrsh {

    /**
     * Title text painter
     */
    Paint pTitleText;
    /**
     * Options text painter
     */
    Paint pOptionsText;
    /**
     * Callback to access gameEngine data
     */
    GameEngine engineCallback;
    /**
     * Music toggle button
     */
    TextButtonComponent btnMusic;

    /**
     * Starts an options menu
     *
     * @param context        the application context
     * @param id             this scene's id (0 is recommended by default for the main menu)
     * @param screenWidth    this screen's width
     * @param screenHeight   this screen's height
     * @param engineCallback callback to access gameEngine data
     */
    public OptionsScene(Context context, int id, int screenWidth, int screenHeight, GameEngine engineCallback) {
        super(context, id, screenWidth, screenHeight);
        this.engineCallback = engineCallback;

        //Title text
        pTitleText = new Paint();
        pTitleText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_KARMAFUTURE));
        pTitleText.setColor(Color.BLACK);
        pTitleText.setTextAlign(Paint.Align.CENTER);
        pTitleText.setTextSize((float) ((screenHeight / GameConstants.MENUSCREEN_COLUMNS) * 2.5));

        //Options text
        pOptionsText = new Paint();
        pOptionsText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        pOptionsText.setColor(Color.BLACK);
        pOptionsText.setTextAlign(Paint.Align.CENTER);
        pOptionsText.setTextSize((float) ((screenHeight / GameConstants.MENUSCREEN_COLUMNS) * 1));

        //Buttons
        btnMusic = new TextButtonComponent(context, Typeface.createFromAsset(getContext().getAssets(), GameConstants.FONT_AWESOME), getContext().getString(R.string.btnMusicOn),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 6,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 3,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 7,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 4,
                Color.TRANSPARENT,
                getContext().getString(R.string.optMusic),
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN),
                TextButtonComponent.TEXT_ALIGN.ALIGN_LEFT);
    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics() {

    }

    /**
     * Draws the options menu
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //General background
        c.drawColor(Color.GREEN);

        c.drawText(context.getString(R.string.btnOptions), screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9, screenHeight / GameConstants.MENUSCREEN_ROWS * 2, pTitleText);
        backBtn.draw(c);
        btnMusic.draw(c);

    }

    /**
     * Controls the events on the touchscreen
     *
     * @param event the touch event
     * @return the pointerId;
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
                    return 0;
                }
                if (isClick(btnMusic, event)) {
                    toggleMusic();
                }
            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
            default:
                Log.i("Other", "Undefined action: " + action);
        }
        return this.id;
    }

    /**
     * Toggles the music theme on or off
     */
    public void toggleMusic() {
        if (btnMusic.getText().equals(context.getString(R.string.btnMusicOn))) {
            btnMusic.setText(context.getString(R.string.btnMusicOff));
            if (engineCallback.mediaPlayer.isPlaying()) {
                engineCallback.mediaPlayer.pause();
            }
        } else {
            btnMusic.setText(context.getString(R.string.btnMusicOn));
            if (!engineCallback.mediaPlayer.isPlaying()) {
                engineCallback.mediaPlayer.start();
            }
        }
    }
}

