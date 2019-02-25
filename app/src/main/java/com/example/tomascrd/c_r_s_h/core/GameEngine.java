package com.example.tomascrd.c_r_s_h.core;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.scenes.CreditScene;
import com.example.tomascrd.c_r_s_h.scenes.GameSettingsScene;
import com.example.tomascrd.c_r_s_h.scenes.MainGameScene;
import com.example.tomascrd.c_r_s_h.scenes.MainMenuScene;
import com.example.tomascrd.c_r_s_h.scenes.MapCreatorScene;
import com.example.tomascrd.c_r_s_h.scenes.NewGameScene;
import com.example.tomascrd.c_r_s_h.scenes.OptionsScene;
import com.example.tomascrd.c_r_s_h.scenes.RecordsScene;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.scenes.TutorialScene;

import java.io.IOException;

/**
 * Main engine of this game, concentrates and controls the activities of different scenes. Contains the main game thread
 *
 * @author Tomás Cardenal López
 */
public class GameEngine extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * This application's context
     */
    private Context context;
    /**
     * Abstract interface to control the drawing surface
     */
    private SurfaceHolder surfaceHolder;
    /**
     * Screen width
     */
    private int screenWidth;
    /**
     * Screen height
     */
    private int screenHeight;
    /**
     * Game thread instance
     */
    private GameThread thread;
    /**
     * Game thread control flag
     */
    private boolean gameWorking;
    /**
     * Current scene to draw and control
     */
    private SceneCrsh currentScene;
    /**
     * Media player for the music
     */
    public MediaPlayer mediaPlayer;
    /**
     * Audio manager for the music
     */
    public AudioManager audioManager;
    /**
     * Stores the current system volume
     */
    private int currentVolume;
    /**
     * Options manager to access the values of the user selected options
     */
    public OptionsManager optionsManager;
    /**
     * Indicates if a saved scene should be loaded, or if a new scene should be instantiated
     */
    public boolean loadSavedScene;
    /**
     * Stores the savedScene
     */
    public SceneCrsh savedScene;
    /**
     * Test for creating the main game scene on load
     */
    public MainGameScene mainGameScene;
    /**
     * Loader class for assets
     */
    public AssetLoader loader;
    /**
     * Current map Id to be loaded
     */
    public int currentMapID;

    /**
     * Starts a gameEngine within the given context
     *
     * @param context the application context
     */
    public GameEngine(Context context) {
        //Initialize engine variables, options, callback, and thread
        super(context);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.context = context;
        this.currentMapID = 666;
        optionsManager = new OptionsManager(context);
        loader = new AssetLoader(context);
        thread = new GameThread();
        this.loadSavedScene = false;
        setFocusable(true);
    }

    /**
     * Gets the callback from the touch events and manages the scene control
     *
     * @param event the thrown event
     * @return true if the function executes correctly until the end
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (surfaceHolder) {
            int newScene = currentScene.onTouchEvent(event);
            if (newScene != currentScene.getId() && newScene != -1) {
                //If the scene changed, set the new scene
                switch (newScene) {
                    case 0: //MainMenuScene
                        if (loadSavedScene && savedScene != null && savedScene instanceof MainMenuScene) {
                            currentScene = savedScene;
                        } else {
                            currentScene = new MainMenuScene(context, newScene, screenWidth, screenHeight);
                        }
                        break;
                    case 1: //NewGameScene
                        if (loadSavedScene && savedScene != null && savedScene instanceof NewGameScene) {
                            currentScene = savedScene;
                        } else {
                            currentScene = new NewGameScene(context, newScene, screenWidth, screenHeight);
                        }
                        break;
                    case 2: //OptionsScene
                        if (loadSavedScene && savedScene != null && savedScene instanceof OptionsScene) {
                            currentScene = savedScene;
                        } else {
                            currentScene = new OptionsScene(context, newScene, screenWidth, screenHeight, this);
                        }
                        break;
                    case 3: //CreditScene
                        if (loadSavedScene && savedScene != null && savedScene instanceof CreditScene) {
                            currentScene = savedScene;
                        } else {
                            currentScene = new CreditScene(context, newScene, screenWidth, screenHeight);
                        }
                        break;
                    case 4: //RecordsScene
                        if (loadSavedScene && savedScene != null && savedScene instanceof RecordsScene) {
                            currentScene = savedScene;
                        } else {
                            currentScene = new RecordsScene(context, newScene, screenWidth, screenHeight);
                        }
                        break;
                    case 5: //TutorialScene
                        if (loadSavedScene && savedScene != null && savedScene instanceof TutorialScene) {
                            currentScene = savedScene;
                        } else {
                            currentScene = new TutorialScene(context, newScene, screenWidth, screenHeight);
                        }
                        break;
                    case 6: //MapCreatorScene
                        if (loadSavedScene && savedScene != null && savedScene instanceof MapCreatorScene) {
                            currentScene = savedScene;
                        } else {
                            currentScene = new MapCreatorScene(context, newScene, screenWidth, screenHeight, this);
                        }
                        break;
                    case 7://GameSettingsScene
                        if (loadSavedScene && savedScene != null && savedScene instanceof GameSettingsScene) {
                            currentScene = savedScene;
                        } else {
                            currentScene = new GameSettingsScene(context, newScene, screenWidth, screenHeight, this);
                        }
                        break;
                    case 97: //MainGameScene GAMEMODE.MODE_NRML_COM
                        if (loadSavedScene && savedScene != null && savedScene instanceof MainGameScene && ((MainGameScene) savedScene).gameMode == MainGameScene.GAMEMODE.MODE_NRML_COM) {
                            currentScene = savedScene;
                        } else {
                            mainGameScene.setGameMode(MainGameScene.GAMEMODE.MODE_NRML_COM);
                            mainGameScene.setMapLoadID(currentMapID);
                            mainGameScene.reloadMap();
                            currentScene = mainGameScene;
                        }
                        if (!loader.loadedTiles) {
                            loader.loadTiles();
                        }
                        break;
                    case 98: //MainGameScene GAMEMODE.MODE_CRSH_COM
                        if (loadSavedScene && savedScene != null && savedScene instanceof MainGameScene && ((MainGameScene) savedScene).gameMode == MainGameScene.GAMEMODE.MODE_CRSH_COM) {
                            currentScene = savedScene;
                        } else {
                            mainGameScene.setGameMode(MainGameScene.GAMEMODE.MODE_CRSH_COM);
                            mainGameScene.setMapLoadID(currentMapID);
                            mainGameScene.reloadMap();
                            currentScene = mainGameScene;
                        }
                        if (!loader.loadedTiles) {
                            loader.loadTiles();
                        }
                        break;
                    case 99: //MainGameScene GAMEMODE.MODE_NRML_2P
                        if (loadSavedScene && savedScene != null && savedScene instanceof MainGameScene && ((MainGameScene) savedScene).gameMode == MainGameScene.GAMEMODE.MODE_NRML_2P) {
                            currentScene = savedScene;
                        } else {
                            mainGameScene.setGameMode(MainGameScene.GAMEMODE.MODE_NRML_2P);
                            mainGameScene.setMapLoadID(currentMapID);
                            mainGameScene.reloadMap();
                            currentScene = mainGameScene;
                        }
                        break;
                    case 100: //MainGameScene, GAMEMODE.MODE_CRSH_2P
                        if (loadSavedScene && savedScene != null && savedScene instanceof MainGameScene && ((MainGameScene) savedScene).gameMode == MainGameScene.GAMEMODE.MODE_CRSH_2P) {
                            currentScene = savedScene;
                        } else {
                            mainGameScene.setGameMode(MainGameScene.GAMEMODE.MODE_CRSH_2P);
                            mainGameScene.setMapLoadID(currentMapID);
                            mainGameScene.reloadMap();
                            currentScene = mainGameScene;
                        }
                        if (!loader.loadedTiles) {
                            loader.loadTiles();
                        }
                        break;
                }
            }
        }
        return true;
    }


    /**
     * Controls the actions when this surface is created
     *
     * @param holder the holder then this event happens
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        optionsManager.loadOptions();
        //Audio managing
        updateAudioObjects();
        updateVolume();
        updateMusicPlayer();
    }

    /**
     * Controls the actions when this surface changes
     *
     * @param holder the holder when this event happens
     * @param format the format
     * @param width  screen width
     * @param height screen height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        //Reset the width and height
        screenWidth = width;
        screenHeight = height;
        //Reload the options
        if (optionsManager == null) {
            optionsManager = new OptionsManager(context);
        }
        //Reload the assets
        if (loader == null) {
            loader = new AssetLoader(context);
        }
        optionsManager.loadOptions();
        //Reload the mainMenuScene if the current scene is not on memory anymore
        if (currentScene == null) {
            currentScene = new MainMenuScene(context, 0, screenWidth, screenHeight);
        }
        //Audio managing
        updateAudioObjects();
        updateVolume();
        updateMusicPlayer();
        //
        if (mainGameScene == null) {
            mainGameScene = new MainGameScene(context, 99, screenWidth, screenHeight, this, MainGameScene.GAMEMODE.MODE_NRML_2P, currentMapID);
        }
        //Starting the thread
        thread.setWorking(true);
        if (thread.getState() == Thread.State.NEW) {
            thread.start();
        }
        if (thread.getState() == Thread.State.TERMINATED) {
            thread = new GameThread();
            thread.start();
        }
        thread.setSurfaceSize(width, height);
    }

    /**
     * Controls the actions when this surface is destroyed
     *
     * @param holder the holder when this event happens
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //End the thread
        thread.setWorking(false);
        try {
            thread.join();
            mediaPlayer.pause();
            optionsManager.saveOptions();
        } catch (InterruptedException e) {
            Log.i("SurfaceDestroyed Error", e.getLocalizedMessage());
        }
    }

    /**
     * Updates the mediaPlayer and audioManager objects
     */
    public void updateAudioObjects() {
        if (audioManager == null) {
            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        }
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.crshmaintheme);
            try {
                mediaPlayer.prepare();
            } catch (IllegalStateException | IOException e) {
                Log.e("mediaPlayer error", "" + e.getLocalizedMessage());
            }
        }
    }

    /**
     * Checks for the system volume and updates the music volume accordingly
     */
    public void updateVolume() {
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mediaPlayer.setVolume(currentVolume / 3, currentVolume / 3);
    }

    /**
     * Updates the mediaPlayer values for the music loop
     */
    public void updateMusicPlayer() {
        if (!mediaPlayer.isPlaying() && optionsManager.isPlayMusic()) {
            mediaPlayer.start();
        } else if (mediaPlayer.isPlaying() && !optionsManager.isPlayMusic()) {
            mediaPlayer.pause();
        }
    }

    /**
     * Updates the Game Scene to the currentMapId
     *
     * @param nullifyMap whether the GameScene should be totally reloaded or not
     */
    public void updateGameScene(boolean nullifyMap) {
        if (mainGameScene == null) {
            mainGameScene = new MainGameScene(context, 99, screenWidth, screenHeight, this, MainGameScene.GAMEMODE.MODE_NRML_2P, currentMapID);
        }
        if (nullifyMap) {

        }
    }

    /**
     * Main thread of the game
     *
     * @author Tomás Cardenal
     */
    public class GameThread extends Thread {

        /**
         * Indicates whether the canvas is locked
         */
        private boolean canvasLocked = false;

        /**
         * Runs the actions of the thread
         */
        @Override
        public void run() {
            //Variables for FPS control
            long timeRef = System.nanoTime();
            long timeSleep = 0;
            //Thread loop
            while (gameWorking) {
                //Retake the volume reference if it changed
                updateVolume();
                Canvas c = null;
                try {
                    if (!surfaceHolder.getSurface().isValid()) {
                        continue;
                    }
                    if (!canvasLocked) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            c = surfaceHolder.lockHardwareCanvas();
                            Log.i("CanvasLocked", "Hardware canvas");
                        } else {
                            c = surfaceHolder.lockCanvas();
                            Log.i("CanvasLocked", "canvas");
                        }
                        canvasLocked = true;
                    }
                    if (c != null) {
                        synchronized (surfaceHolder) {
                            //Call the currentScene working functions
                            currentScene.updatePhysics();
                            currentScene.draw(c);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    canvasLocked = false;
                } finally {
                    if (c != null && canvasLocked) {
                        surfaceHolder.unlockCanvasAndPost(c);
                        canvasLocked = false;
                    }
                }
            }
            //FPS control
            timeRef += GameConstants.TIME_FRAGMENT;
            timeSleep = timeRef - System.nanoTime();

            if (timeSleep > 0) {
                try {
                    Thread.sleep(timeSleep / 1000000);
                } catch (InterruptedException e) {
                    Log.e("GameEngine error", e.getLocalizedMessage());
                }
            }

        }

        /**
         * Sets this thread's working flag
         *
         * @param work the new working state
         */
        protected void setWorking(boolean work) {
            gameWorking = work;
        }

        /**
         * Sets the surface size when there's size or orientation changes.
         *
         * @param width
         * @param height
         */
        public void setSurfaceSize(int width, int height) {
            synchronized (surfaceHolder) {

            }
        }
    }
}
