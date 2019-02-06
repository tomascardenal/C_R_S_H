package com.example.tomascrd.c_r_s_h;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

/**
 * This game's main menu
 *
 * @author Tomás Cardenal López
 */
public class MainMenuScene extends SceneCrsh {

    private final int SCREEN_COLUMNS = 18;
    private final int SCREEN_ROWS = 8;
    private final int[] mipmapBackgrounds = {R.mipmap.paramount1, R.mipmap.paramount2, R.mipmap.paramount3};
    Paint pText;
    ParallaxBackground parallaxBackgrounds[];
    ButtonCrsh btnNewGame, btnOptions, btnRecords, btnCredits, btnTutorial;


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
        pText = new Paint();
        pText.setTypeface(Typeface.createFromAsset(context.getAssets(), "KarmaFuture.ttf"));
        pText.setColor(Color.BLACK);
        pText.setTextAlign(Paint.Align.CENTER);
        pText.setTextSize((float) ((screenHeight / SCREEN_COLUMNS) * 2.5));

        //Parallax Backgrounds
        parallaxBackgrounds = new ParallaxBackground[3];
        for (int i = 0; i < parallaxBackgrounds.length; i++) {
            parallaxBackgrounds[i] = new ParallaxBackground(BitmapFactory.decodeResource(context.getResources(), mipmapBackgrounds[i]), screenWidth, screenHeight);
        }

        //Initialize buttons
        btnNewGame = new ButtonCrsh(context,
                Typeface.createFromAsset(context.getAssets(), "homespun.ttf"),
                context.getString(R.string.btnPlay),
                screenWidth / SCREEN_COLUMNS * 7,
                screenHeight / SCREEN_ROWS * 3,
                screenWidth / SCREEN_COLUMNS * 11,
                screenHeight / SCREEN_ROWS * 4);

        btnOptions = new ButtonCrsh(context,
                Typeface.createFromAsset(context.getAssets(), "homespun.ttf"),
                context.getString(R.string.btnOptions),
                screenWidth / SCREEN_COLUMNS,
                screenHeight / SCREEN_ROWS * 5,
                screenWidth / SCREEN_COLUMNS * 4,
                screenHeight / SCREEN_ROWS * 6);

        btnCredits = new ButtonCrsh(context,
                Typeface.createFromAsset(context.getAssets(), "homespun.ttf"),
                context.getString(R.string.btnCredits),
                screenWidth / SCREEN_COLUMNS * 5,
                screenHeight / SCREEN_ROWS * 5,
                screenWidth / SCREEN_COLUMNS * 8,
                screenHeight / SCREEN_ROWS * 6);

        btnRecords = new ButtonCrsh(context,
                Typeface.createFromAsset(context.getAssets(), "homespun.ttf"),
                context.getString(R.string.btnRecords),
                screenWidth / SCREEN_COLUMNS * 10,
                screenHeight / SCREEN_ROWS * 5,
                screenWidth / SCREEN_COLUMNS * 13,
                screenHeight / SCREEN_ROWS * 6);

        btnTutorial = new ButtonCrsh(context,
                Typeface.createFromAsset(context.getAssets(), "homespun.ttf"),
                context.getString(R.string.btnTutorial),
                screenWidth / SCREEN_COLUMNS * 14,
                screenHeight / SCREEN_ROWS * 5,
                screenWidth / SCREEN_COLUMNS * 17,
                screenHeight / SCREEN_ROWS * 6);

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
     * Draws the menu
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //General background
        c.drawColor(Color.BLUE);

        //Parallax background FIXME This are placeholder images
        for (int i = 0; i < parallaxBackgrounds.length; i++) {
            c.drawBitmap(parallaxBackgrounds[i].image, parallaxBackgrounds[i].position.x, parallaxBackgrounds[i].position.y, null);
            c.drawBitmap(parallaxBackgrounds[i].image, parallaxBackgrounds[i].position.x - parallaxBackgrounds[i].image.getWidth(), parallaxBackgrounds[i].position.y, null);
        }

        //Title
        c.drawText("C_R_S_H", screenWidth / SCREEN_COLUMNS * 9, screenHeight / SCREEN_ROWS * 2, pText);

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
        int pointerIndex = event.getActionIndex();
        int pointerID = event.getPointerId(pointerIndex);
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
