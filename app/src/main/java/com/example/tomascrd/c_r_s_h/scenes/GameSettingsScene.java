package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.ButtonComponent;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.components.TextButtonComponent;
import com.example.tomascrd.c_r_s_h.components.VisualTimerComponent;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.GameEngine;

/**
 * Represents a scene with this game's settings
 *
 * @author Tomás Cardenal López
 */
public class GameSettingsScene extends SceneCrsh {

    /**
     * Options text painter
     */
    private Paint pOptionsText;
    /**
     * Keep joystick velocity toggle button for player one
     */
    private TextButtonComponent btnKeepJoystickVelocityP1;
    /**
     * Keep joystick velocity toggle button for player two
     */
    private TextButtonComponent btnKeepJoystickVelocityP2;
    /**
     * Button for choosing the speed of the game
     */
    private ButtonComponent btnSpeedSlow;
    /**
     * Button for choosing the speed of the game
     */
    private ButtonComponent btnSpeedMid;
    /**
     * Button for choosing the speed of the game
     */
    private ButtonComponent btnSpeedFast;
    /**
     * Button for choosing the speed of the game
     */
    private ButtonComponent btnSpeedOHNO;
    /**
     * Constant id for GameSettingsScene
     */
    private static final int GAMESETTINGS_ID = 7;


    /**
     * Starts an options menu
     *
     * @param context        the application context
     * @param screenWidth    this screen's width
     * @param screenHeight   this screen's height
     * @param engineCallback callback to access gameEngine data
     */
    public GameSettingsScene(Context context, int screenWidth, int screenHeight, GameEngine engineCallback) {
        super(context, GAMESETTINGS_ID, screenWidth, screenHeight);
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
        pOptionsText.setTextSize((float) ((screenHeight / GameConstants.MENUSCREEN_COLUMNS) * 1.5));

        //Buttons
        String joystick1Value = engineCallback.optionsManager.isKeepJoystickVelocityP1() ? context.getString(R.string.btnKeepJoystickOn) : context.getString(R.string.btnKeepJoystickOff);
        btnKeepJoystickVelocityP1 = new TextButtonComponent(context, Typeface.createFromAsset(getContext().getAssets(), GameConstants.FONT_AWESOME), joystick1Value,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 6,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 2,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 7,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 3,
                Color.TRANSPARENT, 0,
                getContext().getString(R.string.optKeepJoystickP1),
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN),
                TextButtonComponent.TEXT_ALIGN.ALIGN_LEFT,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS / 3,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS);

        String joystick2Value = engineCallback.optionsManager.isKeepJoystickVelocityP2() ? context.getString(R.string.btnKeepJoystickOn) : context.getString(R.string.btnKeepJoystickOff);
        btnKeepJoystickVelocityP2 = new TextButtonComponent(context, Typeface.createFromAsset(getContext().getAssets(), GameConstants.FONT_AWESOME), joystick2Value,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 16,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 2,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 17,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 3,
                Color.TRANSPARENT, 0,
                getContext().getString(R.string.optKeepJoystickP2),
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN),
                TextButtonComponent.TEXT_ALIGN.ALIGN_LEFT,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS / 3,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 11);

        btnSpeedSlow = new ButtonComponent(context, Typeface.createFromAsset(getContext().getAssets(), GameConstants.FONT_HOMESPUN), context.getString(R.string.btnSpeedSlow),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 2,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 6,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 4,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 7, Color.GREEN, 150,
                true, -1);
        btnSpeedSlow.setClickEffectParameters(Color.BLACK, Color.DKGRAY, 255, 50);
        if (engineCallback.getCurrentSpeed() == VisualTimerComponent.TIMER_SPEED.TIMER_SLOW) {
            btnSpeedSlow.setHeldDown(true);
        }

        btnSpeedMid = new ButtonComponent(context, Typeface.createFromAsset(getContext().getAssets(), GameConstants.FONT_HOMESPUN), context.getString(R.string.btnSpeedMid),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 6,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 6,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 8,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 7, Color.YELLOW, 150,
                true, -1);
        btnSpeedMid.setClickEffectParameters(Color.BLACK, Color.DKGRAY, 255, 50);
        if (engineCallback.getCurrentSpeed() == VisualTimerComponent.TIMER_SPEED.TIMER_MID) {
            btnSpeedMid.setHeldDown(true);
        }

        btnSpeedFast = new ButtonComponent(context, Typeface.createFromAsset(getContext().getAssets(), GameConstants.FONT_HOMESPUN), context.getString(R.string.btnSpeedFast),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 10,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 6,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 12,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 7, Color.MAGENTA, 150,
                true, -1);
        btnSpeedFast.setClickEffectParameters(Color.BLACK, Color.DKGRAY, 255, 50);
        if (engineCallback.getCurrentSpeed() == VisualTimerComponent.TIMER_SPEED.TIMER_FAST) {
            btnSpeedFast.setHeldDown(true);
        }

        btnSpeedOHNO = new ButtonComponent(context, Typeface.createFromAsset(getContext().getAssets(), GameConstants.FONT_HOMESPUN), context.getString(R.string.btnSpeedOhno),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 14,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 6,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 16,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 7, Color.RED, 150,
                true, -1);
        btnSpeedOHNO.setClickEffectParameters(Color.BLACK, Color.DKGRAY, 255, 50);
        if (engineCallback.getCurrentSpeed() == VisualTimerComponent.TIMER_SPEED.TIMER_OHNO) {
            btnSpeedOHNO.setHeldDown(true);
        }
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
        //Buttons
        backBtn.draw(c);
        btnKeepJoystickVelocityP1.draw(c);
        btnKeepJoystickVelocityP2.draw(c);

        c.drawText(context.getString(R.string.optSpeed), screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9, screenHeight / GameConstants.MENUSCREEN_ROWS * 5, pOptionsText);
        btnSpeedSlow.draw(c);
        btnSpeedMid.draw(c);
        btnSpeedFast.draw(c);
        btnSpeedOHNO.draw(c);

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
                        return 99;
                    } else {
                        return 1;
                    }
                }
                if (isClick(btnKeepJoystickVelocityP1, event)) {
                    toggleJoystickVelocityModeP1();
                }
                if (isClick(btnKeepJoystickVelocityP2, event)) {
                    toggleJoystickVelocityModeP2();
                }
                if (isClick(btnSpeedSlow, event)) {
                    toggleSpeedButtons(VisualTimerComponent.TIMER_SPEED.TIMER_SLOW);
                } else if (isClick(btnSpeedMid, event)) {
                    toggleSpeedButtons(VisualTimerComponent.TIMER_SPEED.TIMER_MID);
                } else if (isClick(btnSpeedFast, event)) {
                    toggleSpeedButtons(VisualTimerComponent.TIMER_SPEED.TIMER_FAST);
                } else if (isClick(btnSpeedOHNO, event)) {
                    toggleSpeedButtons(VisualTimerComponent.TIMER_SPEED.TIMER_OHNO);
                }
            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
            default:
                Log.i("Other", "Undefined action: " + action);
        }
        return this.id;
    }


    /**
     * Toggles the keep joystick velocity value on or off for player 1
     */
    public void toggleJoystickVelocityModeP1() {
        if (btnKeepJoystickVelocityP1.getText().equals(context.getString(R.string.btnKeepJoystickOn))) {
            btnKeepJoystickVelocityP1.setText(context.getString(R.string.btnKeepJoystickOff));
            engineCallback.optionsManager.setKeepJoystickVelocityP1(false);
        } else {
            btnKeepJoystickVelocityP1.setText(context.getString(R.string.btnKeepJoystickOn));
            engineCallback.optionsManager.setKeepJoystickVelocityP1(true);
        }
    }

    /**
     * Toggles the keep joystick velocity value on or off for player 2
     */
    public void toggleJoystickVelocityModeP2() {
        if (btnKeepJoystickVelocityP2.getText().equals(context.getString(R.string.btnKeepJoystickOn))) {
            btnKeepJoystickVelocityP2.setText(context.getString(R.string.btnKeepJoystickOff));
            engineCallback.optionsManager.setKeepJoystickVelocityP2(false);
        } else {
            btnKeepJoystickVelocityP2.setText(context.getString(R.string.btnKeepJoystickOn));
            engineCallback.optionsManager.setKeepJoystickVelocityP2(true);
        }
    }

    public void toggleSpeedButtons(VisualTimerComponent.TIMER_SPEED speed) {
        switch (speed) {
            case TIMER_SLOW:
                btnSpeedSlow.setHeldDown(true);
                btnSpeedMid.setHeldDown(false);
                btnSpeedFast.setHeldDown(false);
                btnSpeedOHNO.setHeldDown(false);
                break;
            case TIMER_MID:
                btnSpeedSlow.setHeldDown(false);
                btnSpeedMid.setHeldDown(true);
                btnSpeedFast.setHeldDown(false);
                btnSpeedOHNO.setHeldDown(false);
                break;
            case TIMER_FAST:
                btnSpeedSlow.setHeldDown(false);
                btnSpeedMid.setHeldDown(false);
                btnSpeedFast.setHeldDown(true);
                btnSpeedOHNO.setHeldDown(false);
                break;
            case TIMER_OHNO:
                btnSpeedSlow.setHeldDown(false);
                btnSpeedMid.setHeldDown(false);
                btnSpeedFast.setHeldDown(false);
                btnSpeedOHNO.setHeldDown(true);
                break;
        }
        engineCallback.setCurrentSpeed(speed);
    }
}

