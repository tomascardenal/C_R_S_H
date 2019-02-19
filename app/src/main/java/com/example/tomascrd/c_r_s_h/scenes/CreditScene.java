package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.core.GameConstants;

/**
 * Scene which contains this game's credits
 *
 * @author Tomás Cardenal López
 */
public class CreditScene extends SceneCrsh {

    /**
     * Credits text painter
     */
    private Paint pCreditsText;
    /**
     * Reference to credit strings on strings.xml files
     */
    private static int[] creditStrings = {R.string.creditsFonts, R.string.creditsMusic, R.string.creditsSoundEffects};
    /**
     * Index of the current credit string to show
     */
    private int creditIndex;
    /**
     * Dynamic alpha value for the credit strings
     */
    private int textAlpha;
    /**
     * Counter to stay on max alpha value;
     */
    private int maxAlphaCounter;
    /**
     * Indicates if the alpha should be incrementing or decrementing
     */
    private boolean incrementAlpha;
    /**
     * Indicates if the credit animation ended
     */
    private boolean rotateCredit;
    /**
     * Maximum number of cycles to stay with text at max alpha
     */
    private static final int MAX_ALPHA_CYCLES = 150;
    /**
     * Alpha incremental and decremental
     */
    private static final int ALPHA_SHIFT = 5;

    /**
     * Starts a credits scene
     *
     * @param context      the application context
     * @param id           this scene's id (0 is recommended by default for the main menu)
     * @param screenWidth  this screen's width
     * @param screenHeight this screen's height
     */
    public CreditScene(Context context, int id, int screenWidth, int screenHeight) {
        super(context, id, screenWidth, screenHeight);

        //Alpha effect variables
        this.textAlpha = 0;
        this.creditIndex = 0;
        this.maxAlphaCounter = 0;
        this.incrementAlpha = true;
        this.rotateCredit = false;

        //Title text
        pTitleText = new Paint();
        pTitleText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_KARMAFUTURE));
        pTitleText.setColor(Color.BLACK);
        pTitleText.setTextAlign(Paint.Align.CENTER);
        pTitleText.setTextSize((float) ((screenHeight / GameConstants.MENUSCREEN_COLUMNS) * 2));

        //Credits text
        pCreditsText = new Paint();
        pCreditsText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        pCreditsText.setColor(Color.BLACK);
        pCreditsText.setTextAlign(Paint.Align.CENTER);
        pCreditsText.setTextSize((float) (screenHeight / GameConstants.MENUSCREEN_COLUMNS));
        pCreditsText.setAlpha(this.textAlpha);

        //Gradient paint
        this.gradientPaint = new Paint();
        this.gradientPaint.setShader(new LinearGradient(0, 0, screenWidth, screenHeight, Color.GREEN, Color.CYAN, Shader.TileMode.CLAMP));

        //Gradient paint
        this.gradientPaint = new Paint();
        this.gradientPaint.setShader(new LinearGradient(0, 0, screenWidth, screenHeight, Color.GREEN, Color.CYAN, Shader.TileMode.CLAMP));

    }

    /**
     * Updates the physics of the elements on the screen, in this scene being the alpha effect and the credits to show
     */
    @Override
    public void updatePhysics() {
        //Credit variables
        if (rotateCredit) {
            rotateCredit = false;
            creditIndex++;
            if (creditIndex > creditStrings.length - 1) {
                creditIndex = 0;
            }
        }

        //Alpha variables
        if (incrementAlpha) {
            if (textAlpha < 255) {
                textAlpha += ALPHA_SHIFT;
            } else {
                if (maxAlphaCounter < MAX_ALPHA_CYCLES) {
                    textAlpha = 255;
                    maxAlphaCounter++;
                } else {
                    maxAlphaCounter = 0;
                    incrementAlpha = false;
                    textAlpha = 250;
                }
            }
        } else {
            if (textAlpha > 0) {
                textAlpha -= ALPHA_SHIFT;
            } else {
                textAlpha = 5;
                incrementAlpha = true;
                rotateCredit = true;
            }
        }

    }

    /**
     * Draws the credits scene
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //General background
        c.drawPaint(gradientPaint);
        //Title text
        c.drawText(context.getString(R.string.btnCredits), screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9, screenHeight / GameConstants.MENUSCREEN_ROWS, pTitleText);
        //Credits text with alpha effect
        pCreditsText.setAlpha(textAlpha);
        String[] creditLines = context.getString(creditStrings[creditIndex]).split("\n");
        int row = 3;
        //Draw all the lines shifted by a row
        for (String line : creditLines) {
            c.drawText(line, screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9, screenHeight / GameConstants.MENUSCREEN_ROWS * row, pCreditsText);
            row++;
        }

        backBtn.draw(c);

    }

    /**
     * Controls the events on the touchscreen
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                break;

            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (isClick(backBtn, event)) {
                    return 0;
                }
            case MotionEvent.ACTION_MOVE: // Any finger moves

                break;
            default:
                Log.i("Other", "Undefined action: " + action);
        }
        return this.id;
    }
}

