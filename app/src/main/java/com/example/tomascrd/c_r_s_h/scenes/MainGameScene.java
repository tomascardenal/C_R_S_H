package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.ButtonComponent;
import com.example.tomascrd.c_r_s_h.components.CircleComponent;
import com.example.tomascrd.c_r_s_h.components.GamepadComponent;
import com.example.tomascrd.c_r_s_h.components.JoystickComponent;
import com.example.tomascrd.c_r_s_h.components.LifeComponent;
import com.example.tomascrd.c_r_s_h.components.MapComponent;
import com.example.tomascrd.c_r_s_h.components.PauseMenuComponent;
import com.example.tomascrd.c_r_s_h.components.PlayerComCrsh;
import com.example.tomascrd.c_r_s_h.components.PlayerCrsh;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.components.VisualTimerComponent;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.GameEngine;
import com.example.tomascrd.c_r_s_h.core.Utils;

/**
 * Represents the main game
 *
 * @author Tomás Cardenal López
 */
public class MainGameScene extends SceneCrsh implements SensorEventListener {


    /**
     * Enumerates the possible types of game mode
     */
    public enum GAMEMODE {
        MODE_NRML_2P, MODE_CRSH_2P, MODE_NRML_COM, MODE_CRSH_COM
    }

    /**
     * Constant id for MainGameScene on Normal/COM mode
     */
    public static final int NRML_COM_ID = 97;
    /**
     * Constant id for MainGameScene on CRSH/COM mode
     */
    public static final int CRSH_COM_ID = 98;
    /**
     * Constant id for MainGameScene on Normal/2players mode
     */
    public static final int NRML_2P_ID = 99;
    /**
     * Constant id for MainGameScene on CRSH/2players mode
     */
    public static final int CRSH_2P_ID = 100;
    /**
     * Map to load on the main game scene
     */
    private MapComponent mapLoad;
    /**
     * Timer for turn switching
     */
    private VisualTimerComponent timer;
    /**
     * Life indicator for player one
     */
    public LifeComponent lifeOne;
    /**
     * Life indicator for player two
     */
    public LifeComponent lifeTwo;
    /**
     * Player 1 or side player
     */
    private PlayerCrsh playerOne;
    /**
     * Player 2 or side player
     */
    private PlayerCrsh playerTwo;
    /**
     * COM Player
     */
    private PlayerComCrsh playerCom;
    /**
     * Joystick for player one
     */
    private JoystickComponent joystickOne;
    /**
     * Joystick for player one
     */
    private JoystickComponent joystickTwo;
    /**
     * Gamepad for player one
     */
    @Deprecated
    private GamepadComponent padOne;
    /**
     * Gamepad for player two
     */
    @Deprecated
    private GamepadComponent padTwo;
    /**
     * Controls if the players are moving
     */
    @Deprecated
    private boolean[] playerMoved;
    /**
     * Vibrator for the game
     */
    private Vibrator vibrator;
    /**
     * Callback to access the game engine
     */
    private GameEngine engineCallback;
    /**
     * Indicates whether the game is paused or not
     */
    private boolean onPause;
    /**
     * Pause menu
     */
    private PauseMenuComponent pauseMenu;
    /**
     * Pause button
     */
    private ButtonComponent btnPause;
    /**
     * Sensor manager for the accelerometer
     */
    private SensorManager sensorManager;
    /**
     * Accelerometer sensor
     */
    private Sensor sensor;
    /**
     * This instance's game mode
     */
    public GAMEMODE gameMode;
    /**
     * The current loaded map's ID number
     */
    private int mapLoadID;
    /**
     * Rect for indicating the player one mode
     */
    private Rect modeOne;
    /**
     * Rect for indicating the player two mode
     */
    private Rect modeTwo;
    /**
     * Paint for mode one
     */
    private Paint paintModeOne;
    /**
     * Paint for mode two
     */
    private Paint paintModeTwo;
    /**
     * Paint for score text
     */
    private Paint paintScores;
    /**
     * Bitmap for indicator one (left side)
     */
    private Bitmap indicatorImageOne;
    /**
     * Bitmap for indicator two (right side)
     */
    private Bitmap indicatorImageTwo;
    /**
     * Counter for looping on indicator attack
     */
    private int indicatorLoop = 0;
    /**
     * Stores the previous COM score
     */
    private int previousCOMScore;
    /**
     * Score for the com isn't displayed, this is a string representing the changes in it's score with random chars
     */
    private String comScoreMimic;

    /**
     * Starts a new main game
     *
     * @param context        the application context
     * @param screenWidth    this screen's width
     * @param screenHeight   this screen's height
     * @param engineCallback callback to this game's engine
     * @param mapLoadID      id of the map to be loaded
     */
    public MainGameScene(Context context, int screenWidth, int screenHeight, GameEngine engineCallback, GAMEMODE gameMode, int mapLoadID) {
        //TODO Set player indicators (with maybe a player sprite)
        //Initialize variables
        super(context, -1, screenWidth, screenHeight);
        this.engineCallback = engineCallback;
        this.onPause = false;
        this.gameMode = gameMode;
        this.setId();
        this.setMapLoadID(mapLoadID);
        this.previousCOMScore = -1;

        //Initialize map
        this.mapLoad = new MapComponent(this.getMapLoadID(), context, screenWidth, screenHeight, engineCallback.loader);
        this.mapLoad.loadTileArray();
        this.tileSizeReference = mapLoad.getReference();

        //Initialize players
        PointF playerOneCenter = new PointF(mapLoad.tileArray[2][2].getCollisionRect().exactCenterX(), mapLoad.tileArray[2][2].getCollisionRect().exactCenterY());
        this.playerOne = new PlayerCrsh(this, mapLoad, "TestP1", 1, true, new CircleComponent(playerOneCenter, mapLoad.getReference() / 2));
        PointF playerTwoCenter = new PointF(mapLoad.tileArray[mapLoad.tileArray.length - 3][mapLoad.tileArray[mapLoad.tileArray.length - 3].length - 3].getCollisionRect().exactCenterX(), mapLoad.tileArray[mapLoad.tileArray.length - 3][mapLoad.tileArray[mapLoad.tileArray.length - 3].length - 3].getCollisionRect().exactCenterY());
        if (this.gameMode == GAMEMODE.MODE_CRSH_COM || this.gameMode == GAMEMODE.MODE_NRML_COM) {
            this.playerCom = new PlayerComCrsh(this, mapLoad, true, new CircleComponent(playerTwoCenter, mapLoad.getReference() / 2));
        } else {
            this.playerTwo = new PlayerCrsh(this, mapLoad, "testP2", 2, false, new CircleComponent(playerTwoCenter, mapLoad.getReference() / 2));
        }

        //Initialize joysticks
        int joystickRadius = (int) Math.floor(mapLoad.getReference() * 1.5);
        this.joystickOne = new JoystickComponent(context, joystickRadius, Color.GRAY, Color.CYAN);
        if (this.gameMode == GAMEMODE.MODE_CRSH_2P || this.gameMode == GAMEMODE.MODE_NRML_2P) {
            this.joystickTwo = new JoystickComponent(context, joystickRadius, Color.GRAY, Color.MAGENTA);
        }

        //Initialize accelerometer
        this.updateSensors();

        //Initialize vibrator
        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);


        //Timer
        int left = this.mapLoad.tileArray[GameConstants.MAPAREA_ROWS - 1][0].getCollisionRect().left;
        int top = this.mapLoad.tileArray[GameConstants.MAPAREA_ROWS - 1][0].getCollisionRect().top;
        int right = this.mapLoad.tileArray[GameConstants.MAPAREA_ROWS - 1][GameConstants.MAPAREA_COLUMNS - 1].getCollisionRect().right;
        int bottom = this.mapLoad.tileArray[GameConstants.MAPAREA_ROWS - 1][GameConstants.MAPAREA_COLUMNS - 1].getCollisionRect().bottom;
        this.timer = new VisualTimerComponent(this.context, this, new Rect(left, top, right, bottom), engineCallback.optionsManager.getTimerSpeed());

        //Life Component player one (Right side)
        left = this.mapLoad.tileArray[0][0].getCollisionRect().left;
        top = this.mapLoad.tileArray[0][0].getCollisionRect().top;
        right = this.mapLoad.tileArray[0][12].getCollisionRect().right;
        bottom = this.mapLoad.tileArray[0][12].getCollisionRect().bottom;
        this.lifeOne = new LifeComponent(this.context, this, new Rect(left, top, right, bottom), playerOne.getPlayerLifes(), false, 1);

        //Life Component player two (Left side)
        left = this.mapLoad.tileArray[0][13].getCollisionRect().left;
        top = this.mapLoad.tileArray[0][13].getCollisionRect().top;
        right = this.mapLoad.tileArray[0][GameConstants.MAPAREA_COLUMNS - 1].getCollisionRect().right;
        bottom = this.mapLoad.tileArray[0][GameConstants.MAPAREA_COLUMNS - 1].getCollisionRect().bottom;
        if (this.gameMode == GAMEMODE.MODE_CRSH_COM || this.gameMode == GAMEMODE.MODE_NRML_COM) {
            this.lifeTwo = new LifeComponent(this.context, this, new Rect(left, top, right, bottom), playerCom.getPlayerLifes(), true, 0);
        } else {
            this.lifeTwo = new LifeComponent(this.context, this, new Rect(left, top, right, bottom), playerTwo.getPlayerLifes(), true, 2);
        }


        //Scores
        this.paintScores = new Paint();
        this.paintScores.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        this.paintScores.setTextSize(this.mapLoad.getReference() * 0.80f);
        this.paintScores.setColor(Color.WHITE);

        //Mode one rect
        left = this.mapLoad.tileArray[1][0].getCollisionRect().left;
        top = this.mapLoad.tileArray[1][0].getCollisionRect().top;
        right = this.mapLoad.tileArray[GameConstants.MAPAREA_ROWS - 2][0].getCollisionRect().right;
        bottom = this.mapLoad.tileArray[GameConstants.MAPAREA_ROWS - 2][0].getCollisionRect().bottom;
        this.modeOne = new Rect(left, top, right, bottom);
        this.paintModeOne = new Paint();
        this.paintModeOne.setShader(new LinearGradient(this.modeOne.left + this.modeOne.width() / 2, this.modeOne.top, this.modeOne.right - this.modeOne.width() / 2, this.modeOne.bottom, Color.BLACK, Color.BLUE, Shader.TileMode.CLAMP));

        //Mode two rect
        left = this.mapLoad.tileArray[1][GameConstants.MAPAREA_COLUMNS - 1].getCollisionRect().left;
        top = this.mapLoad.tileArray[1][GameConstants.MAPAREA_COLUMNS - 1].getCollisionRect().top;
        right = this.mapLoad.tileArray[GameConstants.MAPAREA_ROWS - 2][GameConstants.MAPAREA_COLUMNS - 1].getCollisionRect().right;
        bottom = this.mapLoad.tileArray[GameConstants.MAPAREA_ROWS - 2][GameConstants.MAPAREA_COLUMNS - 1].getCollisionRect().bottom;
        this.modeTwo = new Rect(left, top, right, bottom);
        this.paintModeTwo = new Paint();
        if (this.gameMode == GAMEMODE.MODE_CRSH_COM || this.gameMode == GAMEMODE.MODE_NRML_COM) {
            this.paintModeTwo.setShader(new LinearGradient(this.modeTwo.left + this.modeTwo.width() / 2, this.modeTwo.top, this.modeTwo.right - this.modeTwo.width() / 2, this.modeTwo.bottom, Color.GRAY, Color.BLACK, Shader.TileMode.CLAMP));
        } else {
            this.paintModeTwo.setShader(new LinearGradient(this.modeTwo.left + this.modeTwo.width() / 2, this.modeTwo.top, this.modeTwo.right - this.modeTwo.width() / 2, this.modeTwo.bottom, Color.RED, Color.BLACK, Shader.TileMode.CLAMP));

        }


        //Bitmaps for attack
        if (!engineCallback.loader.areIndicatorsLoaded()) {
            engineCallback.loader.loadIndicators(this.modeOne.width(), this.modeOne.height() / 2);
        }
        setAttackIndicator();

        //Pause button
        btnPause = new ButtonComponent(context,
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME), context.getString(R.string.btnPause),
                screenWidth - screenWidth / 16, 0, screenWidth, screenWidth / 16, Color.TRANSPARENT, 0, false, 0);

        //Pause menu
        this.pauseMenu = new PauseMenuComponent(this.context, this.mapLoad.xLeft, this.mapLoad.yTop, this.mapLoad.mapAreaWidth, this.mapLoad.mapAreaHeight, this);
    }

    /**
     * Sets the gameMode and updates the sensors
     *
     * @param gameMode the new game mode
     */
    public void setGameMode(GAMEMODE gameMode) {
        this.gameMode = gameMode;
        this.setId();
        reloadMap();
        updateSensors();
    }

    /**
     * Updates the sensors
     */
    private void updateSensors() {
        if ((gameMode == GAMEMODE.MODE_CRSH_2P || gameMode == GAMEMODE.MODE_CRSH_COM) && (sensor == null || sensorManager == null)) {
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
        } else if ((gameMode == GAMEMODE.MODE_NRML_2P || gameMode == GAMEMODE.MODE_NRML_COM && (sensor != null || sensorManager != null))) {
            if (sensorManager != null && sensor != null) {
                sensorManager.unregisterListener(this, sensor);
            }
            sensorManager = null;
            sensor = null;
        }
    }

    /**
     * Reload the map and reset the values for the players
     */
    public void reloadMap() {
        this.mapLoad = null;
        this.playerOne = null;
        this.playerTwo = null;
        this.playerCom = null;
        this.joystickOne = null;
        this.joystickTwo = null;
        this.onPause = false;
        this.previousCOMScore = -1;

        //Initialize map
        this.mapLoad = new MapComponent(this.getMapLoadID(), context, screenWidth, screenHeight, engineCallback.loader);
        this.mapLoad.loadTileArray();
        this.tileSizeReference = mapLoad.getReference();

        //Joysticks
        int joystickRadius = (int) Math.floor(mapLoad.getReference() * 1.5);
        this.joystickOne = new JoystickComponent(context, joystickRadius, Color.GRAY, Color.CYAN);
        if (this.gameMode == GAMEMODE.MODE_CRSH_2P || this.gameMode == GAMEMODE.MODE_NRML_2P) {
            this.joystickTwo = new JoystickComponent(context, joystickRadius, Color.GRAY, Color.MAGENTA);
        }

        //Timer
        if (timer == null) {
            int left = this.mapLoad.tileArray[GameConstants.MAPAREA_ROWS - 1][0].getCollisionRect().left;
            int top = this.mapLoad.tileArray[GameConstants.MAPAREA_ROWS - 1][0].getCollisionRect().top;
            int right = this.mapLoad.tileArray[GameConstants.MAPAREA_ROWS - 1][GameConstants.MAPAREA_COLUMNS - 1].getCollisionRect().right;
            int bottom = this.mapLoad.tileArray[GameConstants.MAPAREA_ROWS - 1][GameConstants.MAPAREA_COLUMNS - 1].getCollisionRect().bottom;
            this.timer = new VisualTimerComponent(this.context, this, new Rect(left, top, right, bottom), engineCallback.getCurrentSpeed());
        } else {
            this.timer.resetTimer();
        }

        //Player One
        PointF playerOneCenter = new PointF(mapLoad.tileArray[2][2].getCollisionRect().exactCenterX(), mapLoad.tileArray[2][2].getCollisionRect().exactCenterY());
        this.playerOne = new PlayerCrsh(this, mapLoad, "TestP1", 1, false, new CircleComponent(playerOneCenter, mapLoad.getReference() / 2));
        this.playerOne.respawn();

        //Player Two or COM
        PointF playerTwoCenter = new PointF(mapLoad.tileArray[mapLoad.tileArray.length - 3][mapLoad.tileArray[mapLoad.tileArray.length - 3].length - 3].getCollisionRect().exactCenterX(), mapLoad.tileArray[mapLoad.tileArray.length - 3][mapLoad.tileArray[mapLoad.tileArray.length - 3].length - 3].getCollisionRect().exactCenterY());

        if (this.gameMode == GAMEMODE.MODE_CRSH_COM || this.gameMode == GAMEMODE.MODE_NRML_COM) {
            this.playerCom = new PlayerComCrsh(this, mapLoad, true, new CircleComponent(playerTwoCenter, mapLoad.getReference() / 2));
            this.playerCom.respawn();
        } else {
            this.playerTwo = new PlayerCrsh(this, mapLoad, "testP2", 2, true, new CircleComponent(playerTwoCenter, mapLoad.getReference() / 2));
            this.playerTwo.respawn();
        }

        //Gradients
        if (this.gameMode == GAMEMODE.MODE_CRSH_COM || this.gameMode == GAMEMODE.MODE_NRML_COM) {
            this.paintModeTwo.setShader(new LinearGradient(this.modeTwo.left + this.modeTwo.width() / 2, this.modeTwo.top, this.modeTwo.right - this.modeTwo.width() / 2, this.modeTwo.bottom, Color.GRAY, Color.BLACK, Shader.TileMode.CLAMP));
        } else {
            this.paintModeTwo.setShader(new LinearGradient(this.modeTwo.left + this.modeTwo.width() / 2, this.modeTwo.top, this.modeTwo.right - this.modeTwo.width() / 2, this.modeTwo.bottom, Color.RED, Color.BLACK, Shader.TileMode.CLAMP));

        }
        setAttackIndicator();
    }

    /**
     * Sets the timerComponent speed
     *
     * @param timerSpeed the new speed
     */
    public void setTimerSpeed(VisualTimerComponent.TIMER_SPEED timerSpeed) {
        this.timer.setTimerSpeed(timerSpeed);
    }

    /**
     * Sets the rect indicating the attack and defense modes
     */
    public void setAttackIndicator() {

        if (gameMode == GAMEMODE.MODE_CRSH_2P || gameMode == GAMEMODE.MODE_NRML_2P) {
            if (playerOne.isOnAttack() && !playerTwo.isOnAttack()) {
                indicatorImageOne = engineCallback.loader.indicatorAttackBitmaps[indicatorLoop];
                indicatorImageTwo = engineCallback.loader.indicatorDefenseBitmap;
            } else if (playerTwo.isOnAttack() && !playerOne.isOnAttack()) {
                indicatorImageOne = engineCallback.loader.indicatorDefenseBitmap;
                indicatorImageTwo = engineCallback.loader.indicatorAttackBitmaps[indicatorLoop];
            }
        } else {
            if (playerOne.isOnAttack() && !playerCom.isOnAttack()) {
                indicatorImageOne = engineCallback.loader.indicatorAttackBitmaps[indicatorLoop];
                indicatorImageTwo = engineCallback.loader.indicatorDefenseBitmap;
            } else if (playerCom.isOnAttack() && !playerOne.isOnAttack()) {
                indicatorImageOne = engineCallback.loader.indicatorDefenseBitmap;
                indicatorImageTwo = engineCallback.loader.indicatorAttackBitmaps[indicatorLoop];
            }
        }
        if (indicatorLoop < engineCallback.loader.indicatorAttackBitmaps.length - 1) {
            indicatorLoop++;
        } else {
            indicatorLoop = 0;
        }

    }

    /**
     * Triggers a 200ms vibration
     */
    public void doShortVibration() {
        if (engineCallback.optionsManager.isDoVibrate()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            {
                this.vibrator.vibrate(200);
            }
        }
    }

    /**
     * Triggers a 500ms vibration
     */
    public void doLongVibration() {
        if (engineCallback.optionsManager.isDoVibrate()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            {
                this.vibrator.vibrate(500);
            }
        }
    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics() {
        super.updatePhysics();
        if (!onPause) {
            //If the joystick is active and there's no bounceback on the player, take the values of the joystick
            if (joystickOne.isActive() && !playerOne.onBounceBack()) {
                PointF joystickReference = joystickOne.getDisplacement();
                playerOne.setVelocity(
                        joystickReference.x * playerOne.getJoystickMultiplier(),
                        joystickReference.y * playerOne.getJoystickMultiplier());

            }
            if (gameMode == GAMEMODE.MODE_CRSH_2P || gameMode == GAMEMODE.MODE_NRML_2P) {
                if (joystickTwo.isActive() && !playerTwo.onBounceBack()) {
                    PointF joystickReference = joystickTwo.getDisplacement();
                    playerTwo.setVelocity(
                            joystickReference.x * playerTwo.getJoystickMultiplier(),
                            joystickReference.y * playerTwo.getJoystickMultiplier());
                }
            }

            //Move the players, if there's bounceback the movement would be the one generated by it
            if (playerOne.getPlayerLifes() > 0) {
                playerOne.move();
            }
            if (gameMode == GAMEMODE.MODE_CRSH_2P || gameMode == GAMEMODE.MODE_NRML_2P) {
                if (playerTwo.getPlayerLifes() > 0) {
                    playerTwo.move();
                }
            } else if (gameMode == GAMEMODE.MODE_CRSH_COM || gameMode == GAMEMODE.MODE_NRML_COM) {
                if (playerCom.getPlayerLifes() > 0) {
                    playerCom.move();
                }
            }
            timer.updateTimer();
            setAttackIndicator();
        }
    }

    /**
     * Draws the main game
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //General background
        super.draw(c);
        if (!onPause) {
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
            c.drawBitmap(indicatorImageTwo, modeTwo.left, modeTwo.top + modeTwo.height() / 2, null);
            //Draw player One
            playerOne.draw(c);
            if (this.gameMode == GAMEMODE.MODE_NRML_2P || this.gameMode == GAMEMODE.MODE_CRSH_2P) {
                String pOneScore = String.format("%04d", playerOne.getPlayerScore());
                String pTwoScore = String.format("%04d", playerTwo.getPlayerScore());
                c.drawText(pOneScore, screenWidth / GameConstants.GAMESCREEN_COLUMNS / 2, screenHeight / GameConstants.GAMESCREEN_ROWS * 2.5f, paintScores);
                c.drawText(pTwoScore, (screenWidth - screenWidth / GameConstants.GAMESCREEN_COLUMNS / 2) - paintScores.measureText(pTwoScore), screenHeight / GameConstants.GAMESCREEN_ROWS * 2.5f, paintScores);
                //Draw player Two
                playerTwo.draw(c);
            } else {
                String pOneScore = String.format("%04d", playerOne.getPlayerScore());
                if (previousCOMScore == -1) {
                    previousCOMScore = playerCom.getPlayerScore();
                    comScoreMimic = String.format("%c%c%c%c", Utils.getRandom(32, 50), Utils.getRandom(32, 50), Utils.getRandom(32, 50), Utils.getRandom(32, 50));
                } else if (previousCOMScore != playerCom.getPlayerScore()) {
                    previousCOMScore = playerCom.getPlayerScore();
                    comScoreMimic = String.format("%c%c%c%c", Utils.getRandom(32, 50), Utils.getRandom(32, 50), Utils.getRandom(32, 50), Utils.getRandom(32, 50));
                }

                c.drawText(pOneScore, screenWidth / GameConstants.GAMESCREEN_COLUMNS / 2, screenHeight / GameConstants.GAMESCREEN_ROWS * 2.5f, paintScores);
                c.drawText(comScoreMimic, (screenWidth - screenWidth / GameConstants.GAMESCREEN_COLUMNS / 2) - paintScores.measureText(comScoreMimic), screenHeight / GameConstants.GAMESCREEN_ROWS * 2.5f, paintScores);
                //Draw player COM
                playerCom.draw(c);
            }
            //Draw the back button
            btnPause.draw(c);
            //Draw the joysticks
            if (playerOne.getPlayerLifes() > 0) {
                joystickOne.draw(c);
            }
            if (this.gameMode == GAMEMODE.MODE_NRML_2P || this.gameMode == GAMEMODE.MODE_CRSH_2P) {
                if (playerTwo.getPlayerLifes() > 0) {
                    joystickTwo.draw(c);
                }
            }
        } else {
            pauseMenu.draw(c);
        }

    }

    /**
     * Controls the events on the touchscreen and sends it to the corresponding touchManager
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int onTouchEvent(MotionEvent event) {
        switch (gameMode) {
            case MODE_NRML_COM:
                return touchManagerNRMLVsCOM(event);
            case MODE_CRSH_COM:
                return touchManagerCRSHVsCOM(event);
            case MODE_NRML_2P:
                return touchManagerNRMLTwoPlayers(event);
            case MODE_CRSH_2P:
                return touchManagerCRSHTwoPlayers(event);
        }
        return this.id;
    }

    /**
     * Controls the events on the touchscreen for GAMEMODE.MODE_CRSH_2P
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int touchManagerCRSHTwoPlayers(MotionEvent event) {
        int action = event.getActionMasked();
        boolean playerOneArea = event.getX(event.getActionIndex()) < screenWidth / 2 - 100;
        boolean playerTwoArea = event.getX(event.getActionIndex()) > screenWidth / 2 + 100;
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                if (!onPause) {
                    //If there's a finger down on the player areas and it's not attacking, activate the joystick for that player
                    if (playerOneArea && playerOne.getPlayerLifes() > 0 && !playerOne.isOnAttack()) {
                        joystickOne.activateJoystick(event);
                    }
                    if (playerTwoArea && playerTwo.getPlayerLifes() > 0 && !playerTwo.isOnAttack()) {
                        joystickTwo.activateJoystick(event);
                    }
                    //Control unwanted swipes onto the pause button
                    if (isClick(btnPause, event)) {
                        btnPause.pointerId = event.getPointerId(event.getActionIndex());
                    }
                }
                break;
            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (!onPause) {
                    //Joystick up
                    if (event.getPointerId(event.getActionIndex()) == joystickOne.getPointerId() && playerOneArea && !playerOne.isOnAttack()) {
                        joystickOne.deactivate();
                        //If it's not bouncing back and the option is off, don't keep velocity
                        if (!playerOne.onBounceBack() && !engineCallback.optionsManager.isKeepJoystickVelocityP1()) {
                            playerOne.setVelocity(0, 0);
                        }
                    }
                    if (event.getPointerId(event.getActionIndex()) == joystickTwo.getPointerId() && playerTwoArea && !playerTwo.isOnAttack()) {
                        joystickTwo.deactivate();
                        //If it's not bouncing back and the option is off, don't keep velocity
                        if (!playerTwo.onBounceBack() && !engineCallback.optionsManager.isKeepJoystickVelocityP2()) {
                            playerTwo.setVelocity(0, 0);
                        }
                    }
                    if (isClick(btnPause, event) && event.getPointerId(event.getActionIndex()) == btnPause.pointerId) {
                        onPause = true;
                    }
                } else {
                    int pauseResult = onPauseMenu(event);
                    if (pauseResult != -1) {
                        return pauseResult;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE: // Any finger moves
                //Joystick moving
                if (!onPause) {
                    if (playerOne.getPlayerLifes() > 0 && !playerOne.isOnAttack()) {
                        joystickOne.onMoveEvent(event, screenWidth / 2 - 100, true);
                    }
                    if (playerTwo.getPlayerLifes() > 0 && !playerTwo.isOnAttack()) {
                        joystickTwo.onMoveEvent(event, screenWidth / 2 + 100, false);
                    }
                }
                break;
            default:
                Log.i("Other", "Undefined action: " + action);
        }
        return this.id;
    }

    /**
     * Controls the events on the touchscreen for GAMEMODE.MODE_NRML_2P
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int touchManagerNRMLTwoPlayers(MotionEvent event) {

        int action = event.getActionMasked();
        boolean playerOneArea = event.getX(event.getActionIndex()) < screenWidth / 2 - 100;
        boolean playerTwoArea = event.getX(event.getActionIndex()) > screenWidth / 2 + 100;
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                if (!onPause) {
                    //If there's a finger down on the player areas, activate the joystick for that player
                    if (playerOneArea && playerOne.getPlayerLifes() > 0) {
                        joystickOne.activateJoystick(event);
                    }
                    if (playerTwoArea && playerTwo.getPlayerLifes() > 0) {
                        joystickTwo.activateJoystick(event);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (!onPause) {
                    //Joystick up
                    if (event.getPointerId(event.getActionIndex()) == joystickOne.getPointerId()) {
                        joystickOne.deactivate();
                        //If it's not bouncing back and the option is off, don't keep velocity
                        if (!playerOne.onBounceBack() && !engineCallback.optionsManager.isKeepJoystickVelocityP1()) {
                            playerOne.setVelocity(0, 0);
                        }
                    }
                    if (event.getPointerId(event.getActionIndex()) == joystickTwo.getPointerId()) {
                        joystickTwo.deactivate();
                        //If it's not bouncing back and the option is off, don't keep velocity
                        if (!playerTwo.onBounceBack() && !engineCallback.optionsManager.isKeepJoystickVelocityP2()) {
                            playerTwo.setVelocity(0, 0);
                        }
                    }
                } else {
                    int pauseResult = onPauseMenu(event);
                    if (pauseResult != -1) {
                        return pauseResult;
                    }
                }
                if (isClick(btnPause, event)) {
                    onPause = true;
                }

                break;
            case MotionEvent.ACTION_MOVE: // Any finger moves
                //Joystick moving
                if (!onPause) {
                    if (playerOne.getPlayerLifes() > 0) {
                        joystickOne.onMoveEvent(event, screenWidth / 2 - 100, false);
                    }
                    if (playerTwo.getPlayerLifes() > 0) {
                        joystickTwo.onMoveEvent(event, screenWidth / 2 + 100, true);
                    }
                }
                break;
            default:
                Log.i("Other", "Undefined action: " + action);
        }
        return this.id;
    }


    /**
     * Controls the events on the touchscreen for GAMEMODE.MODE_CRSH_COM
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int touchManagerCRSHVsCOM(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                if (!onPause) {
                    //If there's a finger down on the player areas and it's not attacking, activate the joystick for that player
                    if (playerOne.getPlayerLifes() > 0 && !playerOne.isOnAttack()) {
                        joystickOne.activateJoystick(event);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (!onPause) {
                    //Joystick up
                    if (event.getPointerId(event.getActionIndex()) == joystickOne.getPointerId() && !playerOne.isOnAttack()) {
                        joystickOne.deactivate();
                        //If it's not bouncing back and the option is off, don't keep velocity
                        if (!playerOne.onBounceBack() && !engineCallback.optionsManager.isKeepJoystickVelocityP1()) {
                            playerOne.setVelocity(0, 0);
                        }
                    }
                    if (isClick(btnPause, event)) {
                        onPause = true;
                    }
                } else {
                    int pauseResult = onPauseMenu(event);
                    if (pauseResult != -1) {
                        return pauseResult;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE: // Any finger moves
                //Joystick moving
                if (!onPause) {
                    if (playerOne.getPlayerLifes() > 0 && !playerOne.isOnAttack()) {
                        joystickOne.onMoveEvent(event);
                    }
                }
                break;
            default:
                Log.i("Other", "Undefined action: " + action);
        }
        return this.id;
    }

    /**
     * Controls the events on the touchscreen for GAMEMODE.MODE_NRML_COM
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int touchManagerNRMLVsCOM(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                if (!onPause) {
                    //If there's a finger down on the player areas, activate the joystick for that player
                    if (playerOne.getPlayerLifes() > 0) {
                        joystickOne.activateJoystick(event);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (!onPause) {
                    //Joystick up
                    if (event.getPointerId(event.getActionIndex()) == joystickOne.getPointerId()) {
                        joystickOne.deactivate();
                        //If it's not bouncing back and the option is off, don't keep velocity
                        if (!playerOne.onBounceBack() && !engineCallback.optionsManager.isKeepJoystickVelocityP1()) {
                            playerOne.setVelocity(0, 0);
                        }
                    }
                } else {
                    int pauseResult = onPauseMenu(event);
                    if (pauseResult != -1) {
                        return pauseResult;
                    }
                }
                if (isClick(btnPause, event)) {
                    onPause = true;
                }

                break;
            case MotionEvent.ACTION_MOVE: // Any finger moves
                //Joystick moving
                if (!onPause) {
                    if (playerOne.getPlayerLifes() > 0) {
                        joystickOne.onMoveEvent(event);
                    }
                }
                break;
            default:
                Log.i("Other", "Undefined action: " + action);
        }
        return this.id;
    }

    /**
     * Controls the actions of the PauseMenu
     *
     * @param event the event launching from the pauseMenu
     * @return a new scene id to change to, or -1 if no scene change was requested
     */
    public int onPauseMenu(MotionEvent event) {
        if (pauseMenu.isConfirming()) {
            if (isClick(pauseMenu.getBtnConfirmYes(), event)) {
                pauseMenu.setConfirming(false);
                engineCallback.loadSavedScene = false;
                this.reloadMap();
                return 1;
            } else if (isClick(pauseMenu.getBtnConfirmNo(), event)) {
                pauseMenu.setConfirming(false);
            }
        } else {
            if (isClick(pauseMenu.getBtnUnpause(), event)) {
                onPause = false;
            }
            if (isClick(pauseMenu.getBtnOptions(), event)) {
                engineCallback.loadSavedScene = true;
                engineCallback.savedScene = this;
                return 2;
            }
            if (isClick(pauseMenu.getBtnEndGame(), event)) {
                pauseMenu.setConfirming(true);
            }
        }
        return -1;
    }

    /**
     * Routine for gamepad touch events
     *
     * @param player The player controlling this gamepad
     * @param event  The event which launched this action
     * @deprecated When the JoystickComponent was implemented on 17/02/2019
     */
    @Deprecated
    public void gamePadDown(int player, MotionEvent event) {
        GamepadComponent[] gamepads = {padOne, padTwo};
        PlayerCrsh[] players = {playerOne, playerTwo};
        playerMoved[player] = false;
        if (isClick(gamepads[player].btnUp, event)) {
            players[player].setxVelocity((float) -5);
            playerMoved[player] = true;
        } else if (isClick(gamepads[player].btnDown, event)) {
            players[player].setxVelocity((float) 5);
            playerMoved[player] = true;
        }
        if (isClick(gamepads[player].btnLeft, event)) {
            players[player].setyVelocity((float) 5);
            playerMoved[player] = true;
        } else if (isClick(gamepads[player].btnRight, event)) {
            players[player].setyVelocity((float) -5);
            playerMoved[player] = true;
        }
    }

    /**
     * Routine for gamepad touch events
     *
     * @param player The player controlling this gamepad
     * @param event  The event which launched this action
     * @deprecated When the JoystickComponent was implemented on 17/02/2019
     */
    @Deprecated
    public void gamePadUp(int player, MotionEvent event) {
        GamepadComponent[] gamepads = {padOne, padTwo};
        PlayerCrsh[] players = {playerOne, playerTwo};
        if (isClick(gamepads[player].btnUp, event) || isClick(gamepads[player].btnDown, event)) {
            players[player].setxVelocity((float) 0);
        }
        if (isClick(gamepads[player].btnLeft, event) || isClick(gamepads[player].btnRight, event)) {
            players[player].setyVelocity((float) 0);
        }
        if (event.getActionIndex() == 0) {
            playerMoved[player] = false;
        }
    }

    /**
     * Gets the opponent's collision CircleComponent
     *
     * @param playerID the player id asking for the opponent collision
     * @return the opponent's collision CircleComponent, null if there's no playerCom initialized and the playerID doesn't correspond to playerOne, playerTwo or playerCom
     */
    public CircleComponent getOpponentCollisionComponent(int playerID) {
        if (gameMode == GAMEMODE.MODE_CRSH_2P || gameMode == GAMEMODE.MODE_NRML_2P) {
            if (playerID == 1) {
                return playerTwo.playerCollision;
            } else if (playerID == 2) {
                return playerOne.playerCollision;
            }
        }
        if (gameMode == GAMEMODE.MODE_CRSH_COM || gameMode == GAMEMODE.MODE_NRML_COM) {
            if (playerID == 1) {
                return playerCom.playerCollision;
            } else if (playerID == 0) {
                return playerOne.playerCollision;
            }
        }
        return null;
    }

    /**
     * Makes a player hit the opponent player
     *
     * @param playerID
     */
    public void hitOpponent(int playerID) {
        boolean tookHit = false;
        if (gameMode == GAMEMODE.MODE_CRSH_2P || gameMode == GAMEMODE.MODE_NRML_2P) {
            if (playerID == 1 && !playerOne.isTakingHit() && !playerTwo.isTakingHit()) {
                playerTwo.takeHit();
                lifeTwo.loseALife();
                tookHit = true;
            } else if (playerID == 2 && !playerOne.isTakingHit() && !playerTwo.isTakingHit()) {
                playerOne.takeHit();
                lifeOne.loseALife();
                tookHit = true;
            }
            if (tookHit) {
                playerOne.togglePlayerMode();
                playerTwo.togglePlayerMode();
                timer.resetTimer();
                if (gameMode == GAMEMODE.MODE_CRSH_2P) {
                    if (playerOne.isOnAttack()) {
                        deactivateJoystick(1);
                    } else if (playerTwo.isOnAttack()) {
                        deactivateJoystick(2);
                    }
                }
            }
        } else if (gameMode == GAMEMODE.MODE_CRSH_COM || gameMode == GAMEMODE.MODE_NRML_COM) {
            if (playerID == 1 && !playerCom.isTakingHit() && !playerOne.isTakingHit()) {
                playerCom.takeHit();
                lifeTwo.loseALife();
                tookHit = true;
            } else if (playerID == 0 && !playerOne.isTakingHit() && !playerCom.isTakingHit()) {
                playerOne.takeHit();
                lifeOne.loseALife();
                tookHit = true;
            }
            if (tookHit) {
                timer.resetTimer();
                playerOne.togglePlayerMode();
                playerCom.togglePlayerMode();
                if (gameMode == GAMEMODE.MODE_CRSH_COM) {
                    if (playerOne.isOnAttack()) {
                        deactivateJoystick(1);
                    }
                }
            }
        }
    }

    public void togglePlayerModes() {
        if (gameMode == GAMEMODE.MODE_NRML_2P || gameMode == GAMEMODE.MODE_CRSH_2P) {
            playerOne.togglePlayerMode();
            playerTwo.togglePlayerMode();
        } else if (gameMode == GAMEMODE.MODE_NRML_COM || gameMode == GAMEMODE.MODE_CRSH_COM) {
            playerOne.togglePlayerMode();
            playerCom.togglePlayerMode();
        }
    }

    /**
     * Sets the onPause value of the game
     *
     * @param onPause the new onPause value
     */
    public void setOnPause(boolean onPause) {
        this.onPause = onPause;
    }

    /**
     * Gives back the current onPause value
     *
     * @return the current onPause value
     */
    public boolean isOnPause() {
        return this.onPause;
    }

    /**
     * Detects the changes on the registered sensors
     *
     * @param event the event calling the function
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i("SensorChanged ", "gameMode is " + gameMode);
        if ((gameMode == GAMEMODE.MODE_CRSH_COM && playerOne.isOnAttack()) ||
                gameMode == GAMEMODE.MODE_CRSH_2P) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float xAccel = event.values[0];
                float yAccel = event.values[1];
                if (Math.abs(xAccel) < GameConstants.ACCELEROMETER_MIN_THRESHOLD) {
                    xAccel = 0;
                } else if (Math.abs(xAccel) > GameConstants.ACCELEROMETER_MAX_THRESHOLD) {
                    if (xAccel < 0) {
                        xAccel = GameConstants.ACCELEROMETER_MAX_THRESHOLD * -1;
                    } else {
                        xAccel = GameConstants.ACCELEROMETER_MAX_THRESHOLD;
                    }
                }
                if (Math.abs(yAccel) < GameConstants.ACCELEROMETER_MIN_THRESHOLD) {
                    yAccel = 0;
                } else if (Math.abs(yAccel) > GameConstants.ACCELEROMETER_MAX_THRESHOLD) {
                    if (yAccel < 0) {
                        yAccel = GameConstants.ACCELEROMETER_MAX_THRESHOLD * -1;
                    } else {
                        yAccel = GameConstants.ACCELEROMETER_MAX_THRESHOLD;
                    }
                }
                if (playerOne.isOnAttack() && !playerOne.onBounceBack()) {
                    playerOne.setVelocity(yAccel * GameConstants.ACCELEROMETER_MULTIPLIER, xAccel * GameConstants.ACCELEROMETER_MULTIPLIER);
                } else if (playerTwo != null && gameMode == GAMEMODE.MODE_CRSH_2P && playerTwo.isOnAttack() && !playerTwo.onBounceBack()) {
                    playerTwo.setVelocity(yAccel * GameConstants.ACCELEROMETER_MULTIPLIER, xAccel * GameConstants.ACCELEROMETER_MULTIPLIER);
                }
            }
        }
    }


    /**
     * Sets this id depending on the map mode
     */
    public void setId() {
        switch (this.gameMode) {
            case MODE_NRML_COM:
                this.id = NRML_COM_ID;
                break;
            case MODE_CRSH_COM:
                this.id = CRSH_COM_ID;
                break;
            case MODE_NRML_2P:
                this.id = NRML_2P_ID;
                break;
            case MODE_CRSH_2P:
                this.id = CRSH_2P_ID;
                break;
        }
    }

    /**
     * Detects changes on accuracy of the sensor
     *
     * @param sensor   The sensor sending the event
     * @param accuracy The new accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Deactivates the indicated joystick
     *
     * @param playerId the playerID for the joystick to deactivate
     */
    public void deactivateJoystick(int playerId) {
        if (playerId == 1) {
            joystickOne.deactivate();
        }
        if (playerId == 2) {
            joystickTwo.deactivate();
        }
    }

    /**
     * Returns whether the indicated player's joystick is being used
     *
     * @param playerId the joystick
     * @return the value of the joystick activity
     */
    public boolean isJoystickActive(int playerId) {
        return playerId == 1 ? joystickOne.isActive() : joystickTwo.isActive();
    }

    /**
     * Gets the human player position on COM matches
     *
     * @return the human player position as a PointF
     */
    public PointF getHumanPosition() {
        return new PointF(playerOne.getXPosition(), playerOne.getYPosition());
    }

    /**
     * Gets the value of the current loaded map's ID number
     *
     * @return the value
     */
    public int getMapLoadID() {
        return mapLoadID;
    }

    /**
     * Sets the value of the mapId
     *
     * @param mapLoadID the new mapID
     */
    public void setMapLoadID(int mapLoadID) {
        this.mapLoadID = mapLoadID;
    }
}
