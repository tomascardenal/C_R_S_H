package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.scenes.MainGameScene;

/**
 * Represents the pause menu
 *
 * @author Tomás Cardenal López
 */
public class PauseMenuComponent extends DrawableComponent {
    /**
     * Button for options
     */
    private ButtonComponent btnOptions;
    /**
     * Button for unpausing
     */
    private ButtonComponent btnUnpause;
    /**
     * Button for going back to the newGameMenu
     */
    private ButtonComponent btnEndGame;
    /**
     * The current state of the game to be saved if necessary
     */
    private MainGameScene gameSceneState;
    /**
     * Rectangle for this border
     */
    private Rect borderRect;
    /**
     * Painter for background
     */
    private Paint backgroundPaint;
    /**
     * Painter for border
     */
    private Paint borderPaint;

    /**
     * Initializes a new PauseMenu
     *
     * @param context        the current application context
     * @param xRight         the xRight coordinate
     * @param yTop           the yTop coordinate
     * @param width          the pauseMenu's width
     * @param height         the pauseMenu's height
     * @param gameSceneState the current GameScene state
     */
    public PauseMenuComponent(Context context, float xRight, float yTop, float width, float height, MainGameScene gameSceneState) {
        //Initializing variables
        this.context = context;
        this.gameSceneState = gameSceneState;
        this.xPos = xRight;
        this.yPos = yTop;
        this.width = width;
        this.height = height;


        //Border rectangle
        borderRect = new Rect(
                (int) xPos + gameSceneState.tileSizeReference / 2,
                (int) yPos + gameSceneState.tileSizeReference / 2,
                (int) (xPos + width) - gameSceneState.tileSizeReference / 2,
                (int) (yPos + height) - gameSceneState.tileSizeReference / 2);

        //Background paint
        this.backgroundPaint = new Paint();
        this.backgroundPaint.setShader(new LinearGradient(xPos + (borderRect.height() / 2), yPos + (borderRect.height() / 2), width - (borderRect.height() / 2), height - (borderRect.height() / 2), Color.GRAY, Color.DKGRAY, Shader.TileMode.CLAMP));

        //Border paint
        this.borderPaint = new Paint();
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(gameSceneState.tileSizeReference);

        //Text painter
        pText = new Paint();
        pText.setTypeface(Typeface.createFromAsset(this.context.getAssets(), GameConstants.FONT_HOMESPUN));
        pText.setColor(Color.BLACK);
        pText.setTextAlign(Paint.Align.CENTER);
        pText.setTextSize(gameSceneState.tileSizeReference * 2);

        //Buttons

        this.btnUnpause = new ButtonComponent(context, Typeface.createFromAsset(this.context.getAssets(), GameConstants.FONT_AWESOME),
                context.getString(R.string.btnUnpause),
                (int) (borderRect.exactCenterX() - gameSceneState.tileSizeReference * 2), (int) borderRect.exactCenterY(),
                (int) (borderRect.exactCenterX() + gameSceneState.tileSizeReference * 2), (int) (borderRect.exactCenterY() + gameSceneState.tileSizeReference * 2),
                Color.TRANSPARENT, 0,
                false, -1);

        this.btnOptions = new ButtonComponent(context, Typeface.createFromAsset(this.context.getAssets(), GameConstants.FONT_AWESOME),
                context.getString(R.string.btnOptionsOnPause),
                btnUnpause.btnRect.left - btnUnpause.btnRect.width(), btnUnpause.btnRect.top, btnUnpause.btnRect.left, btnUnpause.btnRect.bottom, Color.TRANSPARENT, 0,
                false, -1);


        this.btnEndGame = new ButtonComponent(context, Typeface.createFromAsset(this.context.getAssets(), GameConstants.FONT_AWESOME),
                context.getString(R.string.btnEndGame),
                btnUnpause.btnRect.right, btnUnpause.btnRect.bottom - btnUnpause.btnRect.height(), btnUnpause.btnRect.right + btnUnpause.btnRect.width(), btnUnpause.btnRect.bottom, Color.TRANSPARENT, 0,
                false, -1);

    }

    /**
     * Draws the pause menu on the canvas
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //Draw background and border
        c.drawRect(borderRect, backgroundPaint);
        c.drawRect(borderRect, borderPaint);

        //Draw title pause text
        String pauseText = context.getString(R.string.pauseTitle);
        c.drawText(pauseText, borderRect.exactCenterX(), this.borderRect.height() / GameConstants.GAMESCREEN_ROWS * 8, pText);

        //Button
        btnOptions.draw(c);
        btnUnpause.draw(c);
        btnEndGame.draw(c);

    }

    /**
     * Returns btnOptions, for click events
     *
     * @return the options button
     */
    public ButtonComponent getBtnOptions() {
        return btnOptions;
    }

    /**
     * Returns btnUnpause, for click events
     *
     * @return the unpause button
     */
    public ButtonComponent getBtnUnpause() {
        return btnUnpause;
    }

    /**
     * Returns btnEndGame, for click events
     *
     * @return the end game button
     */
    public ButtonComponent getBtnEndGame() {
        return btnEndGame;
    }
}
