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
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.components.TextButtonComponent;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.GameEngine;

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
     * Starts an options menu
     *
     * @param context        the application context
     * @param id             this scene's id (0 is recommended by default for the main menu)
     * @param screenWidth    this screen's width
     * @param screenHeight   this screen's height
     * @param engineCallback callback to access gameEngine data
     */
    public GameSettingsScene(Context context, int id, int screenWidth, int screenHeight, GameEngine engineCallback) {
        super(context, id, screenWidth, screenHeight);
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

        //Gradient paint
        this.gradientPaint = new Paint();
        this.gradientPaint.setShader(new LinearGradient(0, 0, screenWidth, screenHeight, Color.GREEN, Color.CYAN, Shader.TileMode.CLAMP));

        //Buttons
        String joystick1Value = engineCallback.optionsManager.isKeepJoystickVelocityP1() ? context.getString(R.string.btnKeepJoystickOn) : context.getString(R.string.btnKeepJoystickOff);
        btnKeepJoystickVelocityP1 = new TextButtonComponent(context, Typeface.createFromAsset(getContext().getAssets(), GameConstants.FONT_AWESOME), joystick1Value,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 11,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 2,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 12,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 3,
                Color.TRANSPARENT, 0,
                getContext().getString(R.string.optKeepJoystickP1),
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN),
                TextButtonComponent.TEXT_ALIGN.ALIGN_LEFT,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS / 3,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 6);

        String joystick2Value = engineCallback.optionsManager.isKeepJoystickVelocityP2() ? context.getString(R.string.btnKeepJoystickOn) : context.getString(R.string.btnKeepJoystickOff);
        btnKeepJoystickVelocityP2 = new TextButtonComponent(context, Typeface.createFromAsset(getContext().getAssets(), GameConstants.FONT_AWESOME), joystick2Value,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 11,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 4,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 12,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 5,
                Color.TRANSPARENT, 0,
                getContext().getString(R.string.optKeepJoystickP2),
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN),
                TextButtonComponent.TEXT_ALIGN.ALIGN_LEFT,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS / 3,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 6);
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
        c.drawPaint(gradientPaint);
        //Title text
        c.drawText(context.getString(R.string.btnOptions), screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9, screenHeight / GameConstants.MENUSCREEN_ROWS, pTitleText);
        //Buttons
        backBtn.draw(c);
        btnKeepJoystickVelocityP1.draw(c);
        btnKeepJoystickVelocityP2.draw(c);
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
}

