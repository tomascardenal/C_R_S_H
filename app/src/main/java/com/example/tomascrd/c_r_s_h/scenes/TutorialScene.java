package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.ButtonComponent;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.GameEngine;
import com.example.tomascrd.c_r_s_h.structs.eGameMode;

/**
 * Represents the tutorial for the game
 *
 * @author Tomás Cardenal López
 */
public class TutorialScene extends MainGameScene {

    /**
     * Enumerates the tutorial stages
     */
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
     * Max number of remaining cycles to tapToContinue state
     */
    private static final int MAX_TAPTOCONTINUE = 180;
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
     * Paint for map Alpha
     */
    private Paint pMapAlpha;
    /**
     * Button to start the tutorial
     */
    public ButtonComponent btnStartTutorial;
    /**
     * Paint for tutorial text
     */
    private Paint pTutorialText;
    /**
     * The cycles until tap to continue
     */
    private int cyclesTapToContinue;
    /**
     * Whether tap to continue is on
     */
    private boolean tapToContinue;
    /**
     * Index of text to show
     */
    private int currentTextIndex;
    /**
     * String references for map stage of the tutorial
     */
    private static final int[] mapStageTextRef = {R.string.tutorialMapOne, R.string.tutorialMapTwo};

    /**
     * Starts a tutorial screen
     *
     * @param context        the application context
     * @param screenWidth    this screen's width
     * @param screenHeight   this screen's height
     * @param engineCallback callback to access gameEngine data
     */
    public TutorialScene(Context context, int screenWidth, int screenHeight, GameEngine engineCallback) {
        //Initialize variables
        super(context, screenWidth, screenHeight, engineCallback, eGameMode.MODE_TUTORIAL, -10);
        restartTutorialVariables();

        pMapAlpha = new Paint();
        pMapAlpha.setColor(Color.LTGRAY);
        pMapAlpha.setAlpha(120);

        mapAlphaRect = new Rect((int) this.mapLoad.xLeft, (int) this.mapLoad.yTop, (int) this.mapLoad.xLeft + (int) this.mapLoad.mapAreaWidth, (int) this.mapLoad.yTop + (int) this.mapLoad.mapAreaHeight);
        //Title text
        pTitleText = new Paint();
        pTitleText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        pTitleText.setColor(Color.BLACK);
        pTitleText.setTextAlign(Paint.Align.CENTER);
        pTitleText.setTextSize((float) ((screenHeight / GameConstants.MENUSCREEN_COLUMNS) * 2));

        //Buttons
        Typeface homespun = Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN);
        this.btnStartTutorial = new ButtonComponent(context, homespun, context.getString(R.string.btnStartTutorial),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 6,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 6,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 12,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 7, Color.BLUE, 150, true, 99);

        //Tutorial text
        pTutorialText = new Paint();
        pTutorialText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        pTutorialText.setColor(Color.BLACK);
        pTutorialText.setTextAlign(Paint.Align.CENTER);
        pTutorialText.setTextSize((float) (screenHeight / GameConstants.MENUSCREEN_COLUMNS));
        pTutorialText.setAlpha(255);
    }

    /**
     * Restarts the tutorial variables
     */
    private void restartTutorialVariables() {
        this.onPause = false;
        this.stageIndex = 0;
        this.currentTextIndex = 0;
        this.currentStage = eTutorialStage.STAGE_WAITINGROOM;
        this.animating = false;
        resetTapToContinue();
    }

    /**
     * Resets the tap to continue cycle
     */
    private void resetTapToContinue() {
        this.tapToContinue = false;
        this.cyclesTapToContinue = MAX_TAPTOCONTINUE;
    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics() {
        if (!tapToContinue && currentStage != eTutorialStage.STAGE_WAITINGROOM) {
            cyclesTapToContinue--;
            if (cyclesTapToContinue <= 0) {
                tapToContinue = true;
            }
        } else {

        }
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
        if (!onPause) {
            switch (this.currentStage) {
                case STAGE_WAITINGROOM:
                    drawStageWaitingRoom(c);
                    break;
                case STAGE_INTRO:
                    drawStageIntro(c);
                    break;
                case STAGE_MAP:
                    drawStageMap(c);
                    break;
                case STAGE_JOYSTICK:
                    break;
                case STAGE_INDICATORS:
                    break;
                case STAGE_POWERUPS:
                    break;
                case STAGE_MORE:
                    break;
            }
            if (tapToContinue && currentStage != eTutorialStage.STAGE_WAITINGROOM) {
                c.drawText(context.getString(R.string.tutorialTapScreen), screenWidth / 2, screenHeight - pTutorialText.getTextSize(), pTutorialText);
            }
        } else {
            pauseMenu.draw(c);
        }
    }

    /**
     * Draws the screen during the waiting room stage
     *
     * @param c
     */
    private void drawStageWaitingRoom(Canvas c) {
        c.drawText(context.getString(R.string.btnTutorial), screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9, screenHeight / GameConstants.MENUSCREEN_ROWS, pTitleText);
        //Buttons
        backBtn.draw(c);
        btnStartTutorial.draw(c);
    }

    /**
     * Draws the screen during the tutorial intro stage
     *
     * @param c
     */
    private void drawStageIntro(Canvas c) {
        //Buttons
        btnPause.draw(c);
        String[] introLines = context.getString(R.string.tutorialIntro).split("\n");
        int row = 1;
        //Draw all the lines shifted by a row
        for (String line : introLines) {
            float x = screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9;
            float y = pTutorialText.getTextSize() * 1.75f * row;
            c.drawText(line, x, y, pTutorialText);
            row++;
        }
    }

    /**
     * Draws the screen during the map tutorial stage
     *
     * @param c
     */
    private void drawStageMap(Canvas c) {
        //Buttons
        btnPause.draw(c);
        mapLoad.draw(c);
        c.drawRect(mapAlphaRect, pMapAlpha);
        String[] introLines = context.getString(mapStageTextRef[currentTextIndex]).split("\n");
        int row = 2;
        for (String line : introLines) {
            float x = screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9;
            float y = pTutorialText.getTextSize() * 1.75f * row;
            c.drawText(line, x, y, pTutorialText);
            row++;
        }
    }

    /**
     * Controls the events on the sending them to the corresponding function depending on the stage
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        switch (this.currentStage) {
            case STAGE_WAITINGROOM:
                return waitingRoomTouchEvent(event);
            case STAGE_INTRO:
                return introTouchEvent(event);
            case STAGE_MAP:
                return mapTouchEvent(event);
            case STAGE_JOYSTICK:
                return joystickTouchEvent(event);
            case STAGE_INDICATORS:
                return indicatorsTouchEvent(event);
            case STAGE_POWERUPS:
                return powerUpsTouchEvent(event);
            case STAGE_MORE:
                return moreTouchEvent(event);
        }
        return this.id;
    }

    /**
     * Controls the events on the tutorial waiting room
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int waitingRoomTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                break;

            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (isClick(backBtn, event)) {
                    return backBtn.getSceneId();
                }
                if (isClick(btnStartTutorial, event)) {
                    this.currentStage = eTutorialStage.STAGE_INTRO;
                }
            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
        }
        return this.id;
    }

    /**
     * Controls the events during the tutorial intro stage
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int introTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                break;

            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (!onPause) {
                    if (isClick(btnPause, event)) {
                        onPause = true;
                    }
                    if (tapToContinue) {
                        resetTapToContinue();
                        currentStage = eTutorialStage.STAGE_MAP;
                    }
                } else {
                    int pauseResult = onPauseMenu(event);
                    if (pauseResult != -1) {
                        if (pauseResult == 0) {
                            restartTutorialVariables();
                        }
                        return pauseResult;
                    }
                }
            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
        }
        return this.id;
    }

    /**
     * Controls the events during the map tutorial stage
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int mapTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                break;

            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (!onPause) {
                    if (isClick(btnPause, event)) {
                        onPause = true;
                    }
                    if (tapToContinue) {
                        resetTapToContinue();
                        if (currentTextIndex < mapStageTextRef.length - 1) {
                            currentTextIndex++;
                        } else {
                            currentTextIndex = 0;
                            //FIXME this is just a test
                            currentStage = eTutorialStage.STAGE_WAITINGROOM;
                        }
                    }
                } else {
                    int pauseResult = onPauseMenu(event);
                    if (pauseResult != -1) {
                        if (pauseResult == 0) {
                            restartTutorialVariables();
                        }
                        return pauseResult;
                    }
                }
            case MotionEvent.ACTION_MOVE: // Any finger moves
                break;
        }
        return this.id;
    }

    /**
     * Controls the events during the joystick tutorial stage
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int joystickTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                break;

            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (!onPause) {
                    if (isClick(btnPause, event)) {
                        onPause = true;
                    }
                } else {
                    int pauseResult = onPauseMenu(event);
                    if (pauseResult != -1) {
                        if (pauseResult == 0) {
                            restartTutorialVariables();
                        }
                        return pauseResult;
                    }
                }
            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
        }
        return this.id;
    }

    /**
     * Controls the events during the indicators tutorial stage
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int indicatorsTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                break;
            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (!onPause) {
                    if (isClick(btnPause, event)) {
                        onPause = true;
                    }
                } else {
                    int pauseResult = onPauseMenu(event);
                    if (pauseResult != -1) {
                        if (pauseResult == 0) {
                            restartTutorialVariables();
                        }
                        return pauseResult;
                    }
                }
            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
        }
        return this.id;
    }

    /**
     * Controls the events during the powerups tutorial stage
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int powerUpsTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                break;

            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (!onPause) {
                    if (isClick(btnPause, event)) {
                        onPause = true;
                    }
                } else {
                    int pauseResult = onPauseMenu(event);
                    if (pauseResult != -1) {
                        if (pauseResult == 0) {
                            restartTutorialVariables();
                        }
                        return pauseResult;
                    }
                }
            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
        }
        return this.id;
    }

    /**
     * Controls the events during the more info tutorial stage
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int moreTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                break;

            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (!onPause) {
                    if (isClick(btnPause, event)) {
                        onPause = true;
                    }
                } else {
                    int pauseResult = onPauseMenu(event);
                    if (pauseResult != -1) {
                        if (pauseResult == 0) {
                            restartTutorialVariables();
                        }
                        return pauseResult;
                    }
                }
            case MotionEvent.ACTION_MOVE: // Any finger moves
                break;
        }
        return this.id;
    }
}

