package com.example.tomascrd.c_r_s_h.core;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
     * Controls the actions when this activity gets created
     *
     * @param savedInstanceState the last saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int opciones = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(opciones);
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
        int opciones = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(opciones);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (gameEngine == null) {
            gameEngine = new GameEngine(this);
        }
        gameEngine.setKeepScreenOn(true);
        setContentView(gameEngine);
    }
}
