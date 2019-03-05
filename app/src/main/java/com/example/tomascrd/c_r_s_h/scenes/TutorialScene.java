package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.ButtonComponent;
import com.example.tomascrd.c_r_s_h.components.CircleComponent;
import com.example.tomascrd.c_r_s_h.components.JoystickComponent;
import com.example.tomascrd.c_r_s_h.components.LifeComponent;
import com.example.tomascrd.c_r_s_h.components.MapComponent;
import com.example.tomascrd.c_r_s_h.components.PauseMenuComponent;
import com.example.tomascrd.c_r_s_h.components.PlayerComCrsh;
import com.example.tomascrd.c_r_s_h.components.PlayerCrsh;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.GameEngine;
import com.example.tomascrd.c_r_s_h.structs.eGameMode;

/**
 * Represents the tutorial for the game
 *
 * @author Tomás Cardenal López
 */
public class TutorialScene extends MainGameScene {


    private enum eTutorialStage {
        STAGE_WAITINGROOM,
        STAGE_INTRO,
        STAGE_MAP,
        STAGE_JOYSTICK,
        STAGE_INDICATORS,
        STAGE_POWERUPS,
        STAGE_MORE,
    }

    /**
     * Maximum number of stage index
     */
    private static final int MAX_STAGES = 7;
    /**
     * Constant id for TutorialScene
     */
    public static final int TUTORIAL_ID = 5;
    /**
     * Current stage index
     */
    private int stageIndex;
    /**
     * Current stage enum value
     */
    protected eTutorialStage currentStage;
    /**
     * Determines if there's an animation
     */
    protected boolean animating;
    /**
     * Rect to draw over the map with an alpha
     */
    protected Rect mapAlphaRect;
    /**
     * Button to start the tutorial
     */
    public ButtonComponent btnStartTutorial;

    /**
     * Starts a tutorial screen
     *
     * @param context        the application context
     * @param screenWidth    this screen's width
     * @param screenHeight   this screen's height
     * @param engineCallback callback to access gameEngine data
     */
    public TutorialScene(Context context, int screenWidth, int screenHeight, GameEngine engineCallback) {
        super(context, screenWidth, screenHeight, engineCallback, eGameMode.MODE_TUTORIAL, -20);
        this.onPause = false;
        this.engineCallback = engineCallback;
        this.stageIndex = 0;
        this.currentStage = eTutorialStage.STAGE_WAITINGROOM;
        this.animating = false;

        //Title text
        pTitleText = new Paint();
        pTitleText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        pTitleText.setColor(Color.BLACK);
        pTitleText.setTextAlign(Paint.Align.CENTER);
        pTitleText.setTextSize((float) ((screenHeight / GameConstants.MENUSCREEN_COLUMNS) * 2));
        //Initialize variables
        this.engineCallback = engineCallback;
        Typeface homespun = Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN);
        this.btnStartTutorial = new ButtonComponent(context, homespun, context.getString(R.string.btnStartGame),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 6,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 6,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 12,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 7, Color.BLUE, 150, true, 99);

        this.pauseMenu = new PauseMenuComponent(this.context, this.mapLoad.xLeft, this.mapLoad.yTop, this.mapLoad.mapAreaWidth, this.mapLoad.mapAreaHeight, this);
    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics() {

    }

    /**
     * Draws the tutorial
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //General background
        super.draw(c);
        //Title text
        c.drawText(context.getString(R.string.btnTutorial), screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9, screenHeight / GameConstants.MENUSCREEN_ROWS, pTitleText);
        //Buttons
        backBtn.draw(c);
    }

    /**
     * Controls the events on the touchscreen
     *
     * @param event the touch event
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
                if (isClick(backBtn, event)) {
                    return 0;
                }
            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
        }
        return this.id;
    }
}

