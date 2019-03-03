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
import com.example.tomascrd.c_r_s_h.structs.eGameMode;
import com.example.tomascrd.c_r_s_h.structs.eSoundEffect;
import com.example.tomascrd.c_r_s_h.structs.eTimerSpeed;

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
     * Speed for game timer
     */
    private eTimerSpeed currentSpeed;
    /**
     * Collection of sound effects
     */
    private MediaPlayer[] effectsPool;


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
                            currentScene = new MainMenuScene(context, screenWidth, screenHeight, this);
                        }
                        break;
                    case 1: //NewGameScene
                        if (loadSavedScene && savedScene != null && savedScene instanceof NewGameScene) {
                            currentScene = savedScene;
                        } else {
                            currentScene = new NewGameScene(context, screenWidth, screenHeight, this);
                        }
                        break;
                    case 2: //OptionsScene
                        if (loadSavedScene && savedScene != null && savedScene instanceof OptionsScene) {
                            currentScene = savedScene;
                        } else {
                            currentScene = new OptionsScene(context, screenWidth, screenHeight, this);
                        }
                        break;
                    case 3: //CreditScene
                        if (loadSavedScene && savedScene != null && savedScene instanceof CreditScene) {
                            currentScene = savedScene;
                        } else {
                            currentScene = new CreditScene(context, screenWidth, screenHeight, this);
                        }
                        break;
                    case 4: //RecordsScene
                        if (loadSavedScene && savedScene != null && savedScene instanceof RecordsScene) {
                            currentScene = savedScene;
                        } else {
                            currentScene = new RecordsScene(context, screenWidth, screenHeight, this);
                        }
                        break;
                    case 5: //TutorialScene
                        if (loadSavedScene && savedScene != null && savedScene instanceof TutorialScene) {
                            currentScene = savedScene;
                        } else {
                            currentScene = new TutorialScene(context, screenWidth, screenHeight, this);
                        }
                        break;
                    case 6: //MapCreatorScene
                        if (loadSavedScene && savedScene != null && savedScene instanceof MapCreatorScene) {
                            currentScene = savedScene;
                        } else {
                            currentScene = new MapCreatorScene(context, screenWidth, screenHeight, this);
                        }
                        break;
                    case 7://GameSettingsScene
                        if (loadSavedScene && savedScene != null && savedScene instanceof GameSettingsScene) {
                            currentScene = savedScene;
                        } else {
                            currentScene = new GameSettingsScene(context, screenWidth, screenHeight, this);
                        }
                        break;
                    case 97: //MainGameScene eGameMode.MODE_NRML_COM
                        if (loadSavedScene && savedScene != null && savedScene instanceof MainGameScene && ((MainGameScene) savedScene).gameMode == eGameMode.MODE_NRML_COM) {
                            currentScene = savedScene;
                        } else {
                            mainGameScene.setGameMode(eGameMode.MODE_NRML_COM);
                            mainGameScene.setMapLoadID(currentMapID);
                            mainGameScene.reloadMap();
                            currentScene = mainGameScene;
                        }
                        if (!loader.loadedTiles) {
                            loader.loadTiles();
                        }
                        break;
                    case 98: //MainGameScene eGameMode.MODE_CRSH_COM
                        if (loadSavedScene && savedScene != null && savedScene instanceof MainGameScene && ((MainGameScene) savedScene).gameMode == eGameMode.MODE_CRSH_COM) {
                            currentScene = savedScene;
                        } else {
                            mainGameScene.setGameMode(eGameMode.MODE_CRSH_COM);
                            mainGameScene.setMapLoadID(currentMapID);
                            mainGameScene.reloadMap();
                            currentScene = mainGameScene;
                        }
                        if (!loader.loadedTiles) {
                            loader.loadTiles();
                        }
                        break;
                    case 99: //MainGameScene eGameMode.MODE_NRML_2P
                        if (loadSavedScene && savedScene != null && savedScene instanceof MainGameScene && ((MainGameScene) savedScene).gameMode == eGameMode.MODE_NRML_2P) {
                            currentScene = savedScene;
                        } else {
                            mainGameScene.setGameMode(eGameMode.MODE_NRML_2P);
                            mainGameScene.setMapLoadID(currentMapID);
                            mainGameScene.reloadMap();
                            currentScene = mainGameScene;
                        }
                        break;
                    case 100: //MainGameScene, eGameMode.MODE_CRSH_2P
                        if (loadSavedScene && savedScene != null && savedScene instanceof MainGameScene && ((MainGameScene) savedScene).gameMode == eGameMode.MODE_CRSH_2P) {
                            currentScene = savedScene;
                        } else {
                            mainGameScene.setGameMode(eGameMode.MODE_CRSH_2P);
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
        optionsManager.loadMapList();
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
            optionsManager.loadOptions();
            optionsManager.loadMapList();
        }
        //Reload the assets
        if (loader == null) {
            loader = new AssetLoader(context);
        }
        //Reload the mainMenuScene if the current scene is not on memory anymore
        if (currentScene == null) {
            currentScene = new MainMenuScene(context, screenWidth, screenHeight, this);
        }
        //Audio managing
        updateAudioObjects();
        updateVolume();
        updateMusicPlayer();
        //
        if (mainGameScene == null) {
            mainGameScene = new MainGameScene(context, screenWidth, screenHeight, this, eGameMode.MODE_NRML_COM, currentMapID);
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
            for (MediaPlayer mp : effectsPool) {
                mp.pause();
                mp.seekTo(0);
            }
            optionsManager.saveOptions();
            optionsManager.saveMapList();
        } catch (InterruptedException e) {
            Log.i("CrshDebug", "SurfaceDestroyed Error " + e.getLocalizedMessage());
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
                Log.e("CrshDebug", "mediaPlayer error" + e.getLocalizedMessage());
            }
        }

        if (effectsPool == null) {
            effectsPool = new MediaPlayer[4];
            effectsPool[0] = MediaPlayer.create(context, R.raw.effect_death);
            effectsPool[1] = MediaPlayer.create(context, R.raw.effect_timer_end);
            effectsPool[2] = MediaPlayer.create(context, R.raw.effect_hit);
            effectsPool[3] = MediaPlayer.create(context, R.raw.effect_bump);
            try {
                for (int i = 0; i < effectsPool.length; i++) {
                    effectsPool[i].prepare();
                    effectsPool[i].setLooping(false);
                }
            } catch (IllegalStateException | IOException e) {
                Log.e("CrshDebug", "effectsPool error" + e.getLocalizedMessage());
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
     * Plays a sound effect
     *
     * @param soundEffect the eSoundEffect value corresponding to the effect to play
     */
    public void playSoundEffect(eSoundEffect soundEffect) {
        if (optionsManager.isPlaySoundEffects()) {
            switch (soundEffect) {
                case EFFECT_DEATH:
                    if (effectsPool[0].isPlaying()) {
                        effectsPool[0].pause();
                        effectsPool[0].seekTo(0);
                    }
                    effectsPool[0].start();
                    break;
                case EFFECT_TIMER_END:
                    if (effectsPool[1].isPlaying()) {
                        effectsPool[1].pause();
                        effectsPool[1].seekTo(0);
                    }
                    effectsPool[1].start();
                    break;
                case EFFECT_HIT:
                    if (effectsPool[2].isPlaying()) {
                        effectsPool[2].pause();
                        effectsPool[2].seekTo(0);
                    }
                    effectsPool[2].start();
                    break;
                case EFFECT_BUMP:
                    if (effectsPool[3].isPlaying()) {
                        effectsPool[3].pause();
                        effectsPool[3].seekTo(0);
                    }
                    effectsPool[3].start();
                    break;
            }
        }
    }

    /**
     * Gets the timer speed
     *
     * @return the new timer speed
     */
    public eTimerSpeed getCurrentSpeed() {
        if (this.currentSpeed == null) {
            currentSpeed = optionsManager.getTimerSpeed();
        }
        return currentSpeed;
    }

    public void setCurrentSpeed(eTimerSpeed currentSpeed) {
        this.currentSpeed = currentSpeed;
        this.optionsManager.setTimerSpeed(currentSpeed);
        if (this.mainGameScene != null) {
            this.mainGameScene.setTimerSpeed(currentSpeed);
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
                        } else {
                            c = surfaceHolder.lockCanvas();
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
