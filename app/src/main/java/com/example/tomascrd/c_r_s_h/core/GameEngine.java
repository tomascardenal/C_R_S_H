package com.example.tomascrd.c_r_s_h.core;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.scenes.CreditScene;
import com.example.tomascrd.c_r_s_h.scenes.MainGameScene;
import com.example.tomascrd.c_r_s_h.scenes.MainMenuScene;
import com.example.tomascrd.c_r_s_h.scenes.NewGameScene;
import com.example.tomascrd.c_r_s_h.scenes.OptionsScene;
import com.example.tomascrd.c_r_s_h.scenes.RecordsScene;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.scenes.TutorialScene;

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
     * Starts a gameEngine within the given context
     *
     * @param context the application context
     */
    public GameEngine(Context context) {
        super(context);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.context = context;
        thread = new GameThread();
        setFocusable(true);
    }

    /**
     * Controls touch events on the screen
     *
     * @param event the thrown event
     * @return true if the function executes correctly until the end
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (surfaceHolder) {
            int newScene = currentScene.onTouchEvent(event);
            if (newScene != currentScene.getId()) {
                switch (newScene) {
                    case 0:
                        currentScene = new MainMenuScene(context, newScene, screenWidth, screenHeight);
                        break;
                    case 1:
                        currentScene = new NewGameScene(context, newScene, screenWidth, screenHeight);
                        break;
                    case 2:
                        currentScene = new OptionsScene(context, newScene, screenWidth, screenHeight, this);
                        break;
                    case 3:
                        currentScene = new CreditScene(context, newScene, screenWidth, screenHeight);
                        break;
                    case 4:
                        currentScene = new RecordsScene(context, newScene, screenWidth, screenHeight);
                        break;
                    case 5:
                        currentScene = new TutorialScene(context, newScene, screenWidth, screenHeight);
                        break;
                    case 99:
                        currentScene = new MainGameScene(context, newScene, screenWidth, screenHeight);
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
        //Audio managing
        if (audioManager == null) {
            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        }
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.crshtheme);
        }
        int v = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mediaPlayer.setVolume(v / 3, v / 3);
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            Log.i("Started music: ", "new GameEngine");
        }
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
        screenWidth = width;
        screenHeight = height;
        currentScene = new MainMenuScene(context, 0, screenWidth, screenHeight);
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
        thread.setWorking(false);
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.i("SurfaceDestroyed Error", e.getLocalizedMessage());
        }
    }

    /**
     * Main thread of the game
     *
     * @author Tomás Cardenal
     */
    public class GameThread extends Thread {

        private boolean canvasLocked = false;

        /**
         * Runs the actions of the thread
         */
        @Override
        public void run() {
            long timeRef = System.nanoTime();
            long timeSleep = 0;
            while (gameWorking) {
                Canvas c = null;
                try {
                    if (!surfaceHolder.getSurface().isValid()) {
                        continue;
                    }
                    if (!canvasLocked) {
                        c = surfaceHolder.lockCanvas();
                        canvasLocked = true;
                    }
                    if (c != null) {
                        synchronized (surfaceHolder) {
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
