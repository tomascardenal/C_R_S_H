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

/**
 * Represents a Save Menu
 */
public class SaveMenuComponent extends DrawableComponent {
    /**
     * Button for options
     */
    private ButtonComponent btnOptions;
    /**
     * Button for unpausing
     */
    private ButtonComponent btnUnpause;
    /**
     * Button for going back to the main menu
     */
    private ButtonComponent btnExitToMenu;
    /**
     * Button for saving a map
     */
    private ButtonComponent btnSaveMap;
    /**
     * Button for loading a map
     */
    private ButtonComponent btnLoadMap;
    /**
     * Button for confirming an action
     */
    private ButtonComponent btnConfirmYes;
    /**
     * Button for unconfirming an action
     */
    private ButtonComponent btnConfirmNo;
    /**
     * Indicates whether changes were made to the map and should be confirmed
     */
    private boolean confirmChanges;
    /**
     * Indicates whether the user is being shown the confirm menu
     */
    private boolean isConfirming;
    /**
     * Indicates whether it should quit the Map Creator after confirming
     */
    public boolean quitAfterConfirm;
    /**
     * Indicates whether a new map should be loaded after confirming
     */
    public boolean loadAfterConfirm;
    /**
     * The current state of the game to be saved if necessary
     */
    private SceneCrsh gameSceneState;
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
    public SaveMenuComponent(Context context, float xRight, float yTop, float width, float height, SceneCrsh gameSceneState) {
        //Initializing variables
        this.context = context;
        this.gameSceneState = gameSceneState;
        this.xPos = xRight;
        this.yPos = yTop;
        this.width = width;
        this.height = height;
        this.setConfirmChanges(false);
        this.isConfirming = false;
        this.quitAfterConfirm = false;

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
        pText.setTextSize(gameSceneState.tileSizeReference * 1.5f);

        //Buttons
        Typeface fontawesome = Typeface.createFromAsset(this.context.getAssets(), GameConstants.FONT_AWESOME);

        this.btnUnpause = new ButtonComponent(context, fontawesome,
                context.getString(R.string.btnUnpause),
                (int) (borderRect.exactCenterX() - gameSceneState.tileSizeReference * 2), (int) borderRect.exactCenterY() + gameSceneState.tileSizeReference,
                (int) (borderRect.exactCenterX() + gameSceneState.tileSizeReference * 2), (int) (borderRect.exactCenterY() + gameSceneState.tileSizeReference * 3),
                Color.TRANSPARENT, 0,
                false, -1);

        this.btnOptions = new ButtonComponent(context, fontawesome,
                context.getString(R.string.btnOptionsOnPause),
                btnUnpause.btnRect.left - btnUnpause.btnRect.width(), btnUnpause.btnRect.top + btnUnpause.btnRect.height(), btnUnpause.btnRect.left, btnUnpause.btnRect.bottom + btnUnpause.btnRect.height(), Color.TRANSPARENT, 0,
                false, -1);

        this.btnExitToMenu = new ButtonComponent(context, fontawesome,
                context.getString(R.string.btnEndGame),
                btnUnpause.btnRect.right, btnUnpause.btnRect.bottom, btnUnpause.btnRect.right + btnUnpause.btnRect.width(), btnUnpause.btnRect.bottom + btnUnpause.btnRect.height(), Color.TRANSPARENT, 0,
                false, -1);

        this.btnSaveMap = new ButtonComponent(context, fontawesome,
                context.getString(R.string.btnSaveMap),
                btnOptions.btnRect.left, btnOptions.btnRect.top - btnOptions.btnRect.height() * 2, btnOptions.btnRect.left + btnOptions.btnRect.width(), btnOptions.btnRect.top - btnOptions.btnRect.height(), Color.TRANSPARENT, 0,
                false, -1);

        this.btnLoadMap = new ButtonComponent(context, fontawesome,
                context.getString(R.string.btnLoadMap),
                btnExitToMenu.btnRect.left, btnExitToMenu.btnRect.top - btnExitToMenu.btnRect.height() * 2, btnExitToMenu.btnRect.left + btnExitToMenu.btnRect.width(), btnExitToMenu.btnRect.top - btnExitToMenu.btnRect.height(), Color.TRANSPARENT, 0,
                false, -1);

        this.btnConfirmYes = new ButtonComponent(context, fontawesome,
                context.getString(R.string.btnConfirmNo),
                btnOptions.btnRect.left, btnOptions.btnRect.top - btnOptions.btnRect.height() * 2, btnOptions.btnRect.left + btnOptions.btnRect.width(), btnOptions.btnRect.top - btnOptions.btnRect.height(), Color.TRANSPARENT, 0,
                false, -1);

        this.btnConfirmNo = new ButtonComponent(context, fontawesome,
                context.getString(R.string.btnConfirmYes),
                btnExitToMenu.btnRect.left, btnExitToMenu.btnRect.top - btnExitToMenu.btnRect.height() * 2, btnExitToMenu.btnRect.left + btnExitToMenu.btnRect.width(), btnExitToMenu.btnRect.top - btnExitToMenu.btnRect.height(), Color.TRANSPARENT, 0,
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
        String pauseText;
        if (isConfirming) {
            pauseText = context.getString(R.string.confirmTitle);
        } else {
            pauseText = context.getString(R.string.savemenuTitle);
        }
        c.drawText(pauseText, borderRect.exactCenterX(), this.borderRect.height() / GameConstants.GAMESCREEN_ROWS * 6, pText);

        //Button
        if (isConfirming) {
            btnConfirmYes.draw(c);
            btnConfirmNo.draw(c);
        } else {
            btnOptions.draw(c);
            btnUnpause.draw(c);
            btnExitToMenu.draw(c);
            btnLoadMap.draw(c);
            btnSaveMap.draw(c);
        }
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
     * Returns btnExitToMenu, for click events
     *
     * @return the exit to menu button
     */
    public ButtonComponent getBtnExitToMenu() {
        return btnExitToMenu;
    }

    /**
     * Returns btnSaveMap, for click events
     *
     * @return the save map button
     */
    public ButtonComponent getBtnSaveMap() {
        return btnSaveMap;
    }

    /**
     * Returns btnLoadMap, for click events
     *
     * @return the load map button
     */
    public ButtonComponent getBtnLoadMap() {
        return btnLoadMap;
    }

    /**
     * Returns btnConfirmYes, for click events
     *
     * @return the confirmYes button
     */
    public ButtonComponent getBtnConfirmYes() {
        return btnConfirmYes;
    }

    /**
     * Returns btnConfirmNo, for click events
     *
     * @return the confirmNo button
     */
    public ButtonComponent getBtnConfirmNo() {
        return btnConfirmNo;
    }

    /**
     * Determines if the current saveMenu is ready to confirm changes
     *
     * @return the value of the field
     */
    public boolean isConfirmChanges() {
        return confirmChanges;
    }

    /**
     * Sets the value that determines if changes should be confirmed
     *
     * @param confirmChanges the new value to set
     */
    public void setConfirmChanges(boolean confirmChanges) {
        this.confirmChanges = confirmChanges;
    }

    /**
     * Indicates whether the user is being shown the confirm menu
     *
     * @return the current value
     */
    public boolean isConfirming() {
        return isConfirming;
    }

    /**
     * Sets the value that controls if the user is being shown the confirm menu
     *
     * @param confirming the new value of isConfirming
     */
    public void setConfirming(boolean confirming) {
        isConfirming = confirming;
    }
}