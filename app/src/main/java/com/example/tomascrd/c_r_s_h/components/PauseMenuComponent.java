package com.example.tomascrd.c_r_s_h.components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;

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
     * Rectangle for this background
     */
    private Rect backgroundRect;
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
     * @param xRight         the xRight coordinate
     * @param yTop           the yTop coordinate
     * @param width          the pauseMenu's width
     * @param height         the pauseMenu's height
     * @param gameSceneState the current GameScene state
     */
    public PauseMenuComponent(float xRight, float yTop, float width, float height, MainGameScene gameSceneState) {
        //Initializing variables
        this.gameSceneState = gameSceneState;
        this.xPos = xRight;
        this.yPos = yTop;
        this.width = width;
        this.height = height;

        //Background rectangle and paint
        backgroundRect = new Rect((int) xPos, (int) yPos, (int) (xPos + width), (int) (yPos + height));
        this.backgroundPaint = new Paint();
        this.backgroundPaint.setShader(new LinearGradient(0, 0, width, height, Color.GREEN, Color.CYAN, Shader.TileMode.CLAMP));

        //Border rectangle and paint
        borderRect = new Rect(
                (int) xPos + gameSceneState.tileSizeReference,
                (int) yPos + gameSceneState.tileSizeReference,
                (int) (xPos + width) - gameSceneState.tileSizeReference,
                (int) (yPos + height) - gameSceneState.tileSizeReference);
        this.borderPaint = new Paint();
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(gameSceneState.tileSizeReference);
    }

    @Override
    public void draw(Canvas c) {
        c.drawPaint(backgroundPaint);
        c.drawRect(borderRect, borderPaint);
    }
}
