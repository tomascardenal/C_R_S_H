package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.ButtonComponent;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.GameEngine;
import com.example.tomascrd.c_r_s_h.structs.PowerUps;
import com.example.tomascrd.c_r_s_h.structs.eGameMode;

/**
 * Represents the tutorial for the game
 *
 * @author Tomás Cardenal López
 */
public class TutorialScene extends MainGameScene {
    //FIXME adjust tutorial strings to fit the screen accordingly to the stage

    /**
     * Enumerates the tutorial stages
     */
    private enum eTutorialStage {
        STAGE_WAITINGROOM,
        STAGE_INTRO,
        STAGE_MAP,
        STAGE_INDICATORS,
        STAGE_JOYSTICK,
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
    private static final int MAX_TAPTOCONTINUE = 40;
    /**
     * Constant id for TutorialScene
     */
    public static final int TUTORIAL_ID = 5;
    /**
     * Current stage index
     */
    private int stageIndex;
    /**
     * Button to go to the next stage (only avaiable on some stages)
     */
    private ButtonComponent btnNextStage;
    /**
     * Current stage enum value
     */
    protected eTutorialStage currentStage;
    /**
     * Determines if there's an interactive test going on
     */
    protected boolean onInteractiveTest;
    /**
     * Rect to draw over the map with an alpha
     */
    protected Rect mapAlphaRect;
    /**
     * Rect to draw over the map minus the borders
     */
    protected Rect reducedAlphaRect;
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
     * String references for indicators stage of the tutorial
     */
    private static final int[] indicatorsStageTextRef = {R.string.tutorialIndicatorsOne, R.string.tutorialIndicatorsTwo, R.string.tutorialIndicatorsThree, R.string.tutorialIndicatorsFour};
    /**
     * String references for powerups stage of the tutorial
     */
    private static final int[] powerupsStageTextRef = {R.string.tutorialPowerUpsOne, R.string.tutorialPowerUpsTwo};

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
        this.reducedAlphaRect = new Rect(this.mapAlphaRect.left + tileSizeReference, this.mapAlphaRect.top + tileSizeReference, this.mapAlphaRect.right - tileSizeReference, this.mapAlphaRect.bottom - tileSizeReference);
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

        this.btnNextStage = new ButtonComponent(context, Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME), context.getString(R.string.btnNextStage),
                screenWidth - screenWidth / 16, screenHeight - screenWidth / 16, screenWidth, screenHeight, Color.TRANSPARENT, 0, false, -1);

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
        this.onInteractiveTest = false;
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
        if (!tapToContinue && !onPause) {
            if (currentStage != eTutorialStage.STAGE_WAITINGROOM && !onInteractiveTest) {
                cyclesTapToContinue--;
                if (cyclesTapToContinue <= 0) {
                    tapToContinue = true;
                }
                if (currentStage == eTutorialStage.STAGE_INDICATORS) {
                    setAttackIndicator();
                }
            } else if (onInteractiveTest && !onPause) {
                if (joystickOne.isActive() && !playerOne.onBounceBack()) {
                    PointF joystickReference = joystickOne.getDisplacement();
                    playerOne.setVelocity(
                            joystickReference.x * playerOne.getJoystickMultiplier(),
                            joystickReference.y * playerOne.getJoystickMultiplier());

                }
                playerOne.move();
                playerCom.move();
                timer.updateTimer();
                setAttackIndicator();
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
                case STAGE_INDICATORS:
                    drawStageIndicators(c);
                    break;
                case STAGE_JOYSTICK:
                    drawStageJoystick(c);
                    break;
                case STAGE_POWERUPS:
                    drawStagePowerups(c);
                    break;
                case STAGE_MORE:
                    break;
            }
            if (tapToContinue && currentStage != eTutorialStage.STAGE_WAITINGROOM && !onInteractiveTest) {
                if (currentStage == eTutorialStage.STAGE_INDICATORS) {
                    pTutorialText.setColor(Color.WHITE);
                    c.drawText(context.getString(R.string.tutorialTapScreen), screenWidth / 2, screenHeight - pTutorialText.getTextSize(), pTutorialText);
                    pTutorialText.setColor(Color.BLACK);
                } else {
                    c.drawText(context.getString(R.string.tutorialTapScreen), screenWidth / 2, screenHeight - pTutorialText.getTextSize(), pTutorialText);
                }
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
     * @param c the canvas to draw
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
     * @param c the canvas to draw
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
     * Draws the screen during the indicators tutorial stage
     *
     * @param c the canvas to draw
     */
    private void drawStageIndicators(Canvas c) {
        //Buttons
        btnPause.draw(c);
        mapLoad.draw(c);
        //Draw map
        mapLoad.draw(c);
        //Timer
        timer.draw(c);
        //Life
        lifeOne.draw(c);
        lifeTwo.draw(c);
        //
        c.drawRect(modeOne, paintModeOne);
        c.drawBitmap(indicatorImageOne, modeOne.left, modeOne.top, null);
        c.drawRect(modeTwo, paintModeTwo);
        c.drawBitmap(indicatorImageTwo, modeTwo.left, modeTwo.top, null);
        //Draw player One
        playerOne.draw(c);
        //Draw player COM
        playerCom.draw(c);
        c.drawRect(reducedAlphaRect, pMapAlpha);
        String[] indicatorsLines = context.getString(indicatorsStageTextRef[currentTextIndex]).split("\n");
        int row = 2;
        for (String line : indicatorsLines) {
            float x = screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9;
            float y = pTutorialText.getTextSize() * 1.75f * row;
            c.drawText(line, x, y, pTutorialText);
            row++;
        }
    }

    /**
     * Draws the screen during the joystick tutorial stage
     *
     * @param c the canvas to draw
     */
    private void drawStageJoystick(Canvas c) {
        //Buttons
        btnPause.draw(c);
        if (!onInteractiveTest) {
            mapLoad.draw(c);
        }
        if (onInteractiveTest) {
            //Draw map
            mapLoad.draw(c);
            //Timer
            timer.draw(c);
            //Life
            lifeOne.draw(c);
            lifeTwo.draw(c);
            //
            c.drawRect(modeOne, paintModeOne);
            c.drawBitmap(indicatorImageOne, modeOne.left, modeOne.top, null);
            c.drawRect(modeTwo, paintModeTwo);
            c.drawBitmap(indicatorImageTwo, modeTwo.left, modeTwo.top, null);
            //Draw player One
            playerOne.draw(c);
            //Draw player COM
            playerCom.draw(c);
            //Draw the joysticks
            if (playerOne.getPlayerLifes() > 0) {
                joystickOne.draw(c);
            }
            btnNextStage.draw(c);
        } else {
            c.drawRect(mapAlphaRect, pMapAlpha);
            String[] introLines = context.getString(R.string.tutorialJoystick).split("\n");
            int row = 2;
            for (String line : introLines) {
                float x = screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9;
                float y = pTutorialText.getTextSize() * 1.75f * row;
                c.drawText(line, x, y, pTutorialText);
                row++;
            }
        }
    }

    /**
     * Draws the screen during the powerups tutorial stage
     *
     * @param c the canvas to draw
     */
    private void drawStagePowerups(Canvas c) {
        //Buttons
        btnPause.draw(c);

        String[] indicatorsLines = context.getString(powerupsStageTextRef[currentTextIndex]).split("\n");
        int row = 2;
        if (currentTextIndex == 1) {
            row = 3;
        }
        for (String line : indicatorsLines) {
            float x = screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9;
            float y = pTutorialText.getTextSize() * 1.75f * row;
            if (row >= 5 && row <= 7 || currentTextIndex == 1) {
                x = screenWidth / GameConstants.MENUSCREEN_COLUMNS;
                pTutorialText.setTextAlign(Paint.Align.LEFT);
            }
            c.drawText(line, x, y, pTutorialText);

            if (currentTextIndex == 0) {
                if (row == 5) {
                    c.drawBitmap(engineCallback.loader.getPowerUpBitmap(PowerUps.ePowerUp.POWERUP_TIMER_STOP), screenWidth - tileSizeReference * 2, y - pTutorialText.getTextSize(), null);
                } else if (row == 6) {
                    c.drawBitmap(engineCallback.loader.getPowerUpBitmap(PowerUps.ePowerUp.POWERUP_NO_BOUNCEBACK), screenWidth - tileSizeReference * 2, y - pTutorialText.getTextSize(), null);
                } else if (row == 7) {
                    c.drawBitmap(engineCallback.loader.getPowerUpBitmap(PowerUps.ePowerUp.POWERUP_INVINCIBLE), screenWidth - tileSizeReference * 2, y - pTutorialText.getTextSize(), null);
                }
            } else if (currentTextIndex == 1) {
                if (row == 3) {
                    c.drawBitmap(engineCallback.loader.getPowerUpBitmap(PowerUps.ePowerUp.POWERUP_SLOW_OPPONENT), screenWidth - tileSizeReference * 2, y - pTutorialText.getTextSize(), null);
                } else if (row == 5) {
                    c.drawBitmap(engineCallback.loader.getPowerUpBitmap(PowerUps.ePowerUp.POWERUP_SLOW_MYSELF), screenWidth - tileSizeReference * 2, y - pTutorialText.getTextSize(), null);
                }
            }
            row++;
        }
        pTutorialText.setTextAlign(Paint.Align.CENTER);

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
            case STAGE_INDICATORS:
                return indicatorsTouchEvent(event);
            case STAGE_JOYSTICK:
                return joystickTouchEvent(event);
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
                            currentStage = eTutorialStage.STAGE_INDICATORS;
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
                    if (tapToContinue) {
                        resetTapToContinue();
                        if (currentTextIndex < indicatorsStageTextRef.length - 1) {
                            currentTextIndex++;
                        } else {
                            currentTextIndex = 0;
                            currentStage = eTutorialStage.STAGE_JOYSTICK;
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
                if (!onPause && onInteractiveTest) {
                    //If there's a finger down on the player areas, activate the joystick for that player
                    if (playerOne.getPlayerLifes() > 0) {
                        joystickOne.activateJoystick(event);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (!onPause && onInteractiveTest) {
                    //Joystick up
                    if (event.getPointerId(event.getActionIndex()) == joystickOne.getPointerId()) {
                        joystickOne.deactivate();
                        //If it's not bouncing back and the option is off, don't keep velocity
                        if (!playerOne.onBounceBack() && !engineCallback.optionsManager.isKeepJoystickVelocityP1()) {
                            playerOne.setVelocity(0, 0);
                        }
                    }
                    if (isClick(btnNextStage, event)) {
                        //FIXME this is just a test
                        onInteractiveTest = false;
                        resetTapToContinue();
                        this.currentStage = eTutorialStage.STAGE_POWERUPS;
                    }
                } else if (!onPause && tapToContinue) {
                    onInteractiveTest = true;
                    tapToContinue = false;
                } else if (onPause) {
                    int pauseResult = onPauseMenu(event);
                    if (pauseResult != -1) {
                        if (pauseResult == 0) {
                            restartTutorialVariables();
                        }
                        return pauseResult;
                    }
                }
                if (isClick(btnPause, event)) {
                    onPause = true;
                }

                break;
            case MotionEvent.ACTION_MOVE: // Any finger moves
                //Joystick moving
                if (!onPause && onInteractiveTest) {
                    if (playerOne.getPlayerLifes() > 0) {
                        joystickOne.onMoveEvent(event);
                    }
                }
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
                    if (tapToContinue) {
                        resetTapToContinue();
                        if (currentTextIndex < powerupsStageTextRef.length - 1) {
                            currentTextIndex++;
                        } else {
                            currentTextIndex = 0;
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

