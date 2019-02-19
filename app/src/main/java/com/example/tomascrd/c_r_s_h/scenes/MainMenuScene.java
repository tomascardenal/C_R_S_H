package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.ButtonComponent;
import com.example.tomascrd.c_r_s_h.components.BackgroundComponent;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.core.GameConstants;

/**
 * This game's main menu
 *
 * @author Tomás Cardenal López
 */
public class MainMenuScene extends SceneCrsh {

    /**
     * Reference to backgrounds for parallax
     */
    private final int[] mipmapBackgrounds = {R.mipmap.paramount1, R.mipmap.paramount2, R.mipmap.paramount3};
    /**
     * Array to store the backgrounds
     */
    BackgroundComponent parallaxBackgrounds[];
    /**
     * Button for new game scene
     */
    ButtonComponent btnNewGame;
    /**
     * Button for options scene
     */
    ButtonComponent btnOptions;
    /**
     * Button for records scene
     */
    ButtonComponent btnRecords;
    /**
     * Button for credits scene
     */
    ButtonComponent btnCredits;
    /**
     * Button for tutorial scene
     */
    ButtonComponent btnTutorial;


    /**
     * Starts a main menu
     *
     * @param context      the application context
     * @param id           this scene's id (0 is recommended by default for the main menu)
     * @param screenWidth  this screen's width
     * @param screenHeight this screen's height
     */
    public MainMenuScene(Context context, int id, int screenWidth, int screenHeight) {
        super(context, id, screenWidth, screenHeight);
        //Title text
        pTitleText = new Paint();
        pTitleText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_KARMAFUTURE));
        pTitleText.setColor(Color.BLACK);
        pTitleText.setTextAlign(Paint.Align.CENTER);
        pTitleText.setTextSize((float) ((screenHeight / GameConstants.MENUSCREEN_COLUMNS) * 2.5));

        //Gradient paint
        int[] gradientColors = {Color.BLUE, Color.YELLOW, Color.CYAN};
        float[] positions = {0, screenWidth / 2, screenWidth};
        this.gradientPaint = new Paint();
        this.gradientPaint.setShader(new LinearGradient(0, 0, screenWidth, screenHeight, gradientColors, positions, Shader.TileMode.CLAMP));

        //Parallax Backgrounds
        parallaxBackgrounds = new BackgroundComponent[3];
        for (int i = 0; i < parallaxBackgrounds.length; i++) {
            parallaxBackgrounds[i] = new BackgroundComponent(BitmapFactory.decodeResource(context.getResources(), mipmapBackgrounds[i]), screenWidth, screenHeight);
        }

        //Initialize buttons
        btnNewGame = new ButtonComponent(context,
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN),
                context.getString(R.string.btnPlay),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 7,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 3,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 11,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 4, Color.GRAY, 150);

        btnOptions = new ButtonComponent(context,
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN),
                context.getString(R.string.btnOptions),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 5,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 4,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 6, Color.GRAY, 150);

        btnCredits = new ButtonComponent(context,
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN),
                context.getString(R.string.btnCredits),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 5,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 5,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 8,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 6, Color.GRAY, 150);

        btnRecords = new ButtonComponent(context,
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN),
                context.getString(R.string.btnRecords),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 10,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 5,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 13,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 6, Color.GRAY, 150);

        btnTutorial = new ButtonComponent(context,
                Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN),
                context.getString(R.string.btnTutorial),
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 14,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 5,
                screenWidth / GameConstants.MENUSCREEN_COLUMNS * 17,
                screenHeight / GameConstants.MENUSCREEN_ROWS * 6, Color.GRAY, 150);

    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics() {
        for (int i = 0; i < parallaxBackgrounds.length; i++) {
            parallaxBackgrounds[i].move(i + 1);
            if (parallaxBackgrounds[i].position.x > screenWidth) {
                parallaxBackgrounds[i].position.x = screenWidth - parallaxBackgrounds[i].image.getWidth();
            }
        }
    }

    /**
     * Draws the main menu
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //General background
        c.drawPaint(gradientPaint);

        //Parallax background FIXME This are placeholder images
        for (int i = 0; i < parallaxBackgrounds.length; i++) {
            c.drawBitmap(parallaxBackgrounds[i].image, parallaxBackgrounds[i].position.x, parallaxBackgrounds[i].position.y, null);
            c.drawBitmap(parallaxBackgrounds[i].image, parallaxBackgrounds[i].position.x - parallaxBackgrounds[i].image.getWidth(), parallaxBackgrounds[i].position.y, null);
        }

        //Title text
        c.drawText(context.getString(R.string.app_name), screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9, screenHeight / GameConstants.MENUSCREEN_ROWS * 2, pTitleText);

        //Menu buttons
        btnNewGame.draw(c);
        btnOptions.draw(c);
        btnCredits.draw(c);
        btnRecords.draw(c);
        btnTutorial.draw(c);
    }

    /**
     * Controls the events on the touchscreen
     *
     * @param event the touch event
     * @return the pointerId;
     */
    public int onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                break;

            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (isClick(btnNewGame, event)) {
                    return 1;
                } else if (isClick(btnOptions, event)) {
                    return 2;
                } else if (isClick(btnCredits, event)) {
                    return 3;
                } else if (isClick(btnRecords, event)) {
                    return 4;
                } else if (isClick(btnTutorial, event)) {
                    return 5;
                }
                break;
            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
            default:
                Log.i("Other", "Undefined action: " + action);
        }
        return this.id;
    }
}
