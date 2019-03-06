package com.example.tomascrd.c_r_s_h.core;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

/**
 * Main activity for this Android application. Most of the code in this activity hides normal screen controls to use the screen at it's full extent for the game
 *
 * @author Tomás Cardenal López
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The gameEngine object to run this game and to be set as ContentView
     */
    private GameEngine gameEngine;
    /**
     * Stores the state of the music
     */
    private boolean previousMusicState;

    /**
     * Controls the actions when this activity gets created
     *
     * @param savedInstanceState the last saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        resetUIVisibility(decorView);
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            //Visibility changes?, go back!
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                resetUIVisibility(decorView);
            }
        });
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        gameEngine = new GameEngine(this);
        gameEngine.setKeepScreenOn(true);
        setContentView(gameEngine);
    }

    /**
     * Controls the actions when this activity resumes
     */
    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        resetUIVisibility(decorView);
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            //Visibility changes?, go back!
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                resetUIVisibility(decorView);
            }
        });
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (gameEngine == null) {
            gameEngine = new GameEngine(this);
        } else {
            gameEngine.setGameWorking(true);

        }
        gameEngine.optionsManager.setPlayMusic(previousMusicState);
        gameEngine.updateAudioObjects();
        gameEngine.updateMusicPlayer();

        gameEngine.setKeepScreenOn(true);
        setContentView(gameEngine);
    }

    /**
     * Controls the actions when this activity is paused
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (gameEngine != null) {
            Log.i("CrshDebug", "GameEngine!=null on onPause");
            previousMusicState = gameEngine.optionsManager.isPlayMusic();
            gameEngine.optionsManager.setPlayMusic(false);
            gameEngine.updateMusicPlayer();
            gameEngine.stopAndNullifyAllMusic();
            gameEngine.setGameWorking(false);
            gameEngine.pauseGameIfOn();
        }
    }

    /**
     * Resets the UIVisibility
     */
    private void resetUIVisibility(View decorView) {
        int opciones = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(opciones);
    }
}
